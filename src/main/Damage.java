package main;

import java.util.Arrays;

import data.activationConditions.AbilityActivation;
import data.activationConditions.FieldActivation;
import data.activationConditions.ItemActivation;
import data.activationConditions.MoveEffectActivation;
import data.activationConditions.StatusActivation;
import data.classes.FieldCondition;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.Stat;
import data.classes.StatusCondition;
import data.classes.Type;
import data.messages.list.GeneralMessages;
import data.objects.AbilityList;
import data.objects.MoveList;
import data.objects.StatusConditionList;
import data.objects.TypeList;
import data.properties.moves.Category;
import data.properties.moves.InherentProperty;
import data.properties.moves.MoveType;
import data.properties.moves.TemporaryProperty;
import data.properties.stats.StatName;
import data.properties.stats.StatType;

public class Damage {
    public static int calcDamage(Move move, Pokemon user, Pokemon target, int hit, boolean confusionDamage, boolean effectivenessMessage) {
        double critChance = 1.0/24.0;

        int critStage = move.getCritRatio() - 1;
        if (user.getAbility().shouldActivate(AbilityActivation.CritRatioCalc)) {
            critStage += (int) user.getAbility().activate(user, null, null, null, 0, null, null, 0, AbilityActivation.CritRatioCalc);
        }
        for (StatusCondition condition : user.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.CritRatioCalc)) {
                critStage += (int) condition.activate(user, null, null, 0, true, StatusActivation.CritRatioCalc);
            }
        }

        if (critStage == 1) {
            critChance = 1.0/8.0;
        } else if (critStage == 2) {
            critChance = 1.0/2.0;
        } else if (critStage >= 3) {
            critChance = 1.0;
        }

        boolean criticalHit = confusionDamage ? false : Math.random() < critChance;
        if (target.getAbility().shouldActivate(move, AbilityActivation.TryCritUser)) {
            criticalHit = (boolean) target.getAbility().activate(target, user, move, null, 0, null, null, 0, AbilityActivation.TryCritUser);
        }


        double power = move.getPower(confusionDamage, confusionDamage, hit);

        Stat statA = move.getCategory() == Category.Physical ? user.getStat(StatName.Atk) : user.getStat(StatName.SpA);
        if (Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallAttackingStat)) {
            statA = (Stat) move.activatePrimaryEffect(user, target, null, 0, hit, true, MoveEffectActivation.CallAttackingStat);
        }
        Stat statD = move.getCategory() == Category.Physical ? target.getStat(StatName.Def) : target.getStat(StatName.SpD);
        if (Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallDefendingStat)) {
            statD = (Stat) move.activatePrimaryEffect(user, target, null, 0, hit, true, MoveEffectActivation.CallDefendingStat);
        }

        int A = statA.getEffectiveValue(target, move, criticalHit, StatType.Offensive);
        int D = statD.getEffectiveValue(user, move, criticalHit, StatType.Defensive);


        // Base
        int damage = (int) Math.floor(Math.floor(Math.floor(2*user.getLevel()/5 + 2) * power * A/D)/50 + 2);

        // Clima/Terreno
        if (Battle.getWeather().shouldActivate(FieldActivation.DamageCalcAtk)) {
            damage *= (double) Battle.getWeather().activate(user, target, move, null, null, null, 0, false, true, FieldActivation.DamageCalcAtk);
        }
        if (Battle.getTerrain().shouldActivate(user, FieldActivation.DamageCalcAtk)) {
            damage *= (double) Battle.getTerrain().activate(user, target, move, null, null, null, 0, false, true, FieldActivation.DamageCalcAtk);
        }
        if (Battle.getTerrain().shouldActivate(target, FieldActivation.DamageCalcDef)) {
            damage *= (double) Battle.getTerrain().activate(user, target, move, null, null, null, 0, false, true, FieldActivation.DamageCalcDef);
        }

        // Crítico
        if (criticalHit) {
            damage *= 1.5;

            if (user.getAbility().shouldActivate(AbilityActivation.Crit)) {
                damage *= (double) user.getAbility().activate(user, target, move, null, damage, null, null, 0, AbilityActivation.Crit);
            }
        }

        // Valor aleatório
        damage = (int) (damage * (85 + Math.floor(Math.random()*16))/100);

        // STAB
        boolean isSTAB = false;

        if (user.getAbility().shouldActivate(AbilityActivation.CallSTAB) &&
            (boolean) user.getAbility().activate(user, target, move, null, damage, null, null, 0, AbilityActivation.CallSTAB)) {
            isSTAB = true;
        }
        if (!move.getType(false).compare(TypeList.typeless) &&
            (
                move.getType(false).compare(user.getType(0)) ||
                move.getType(false).compare(user.getType(1)) ||
                move.getType(false).compare(user.getType(2))
            )) {
            isSTAB = true;
        }

        if (isSTAB) {
            double stabMultiplier = 1.5;
            if (user.getAbility().shouldActivate(AbilityActivation.STABCalc)) {
                stabMultiplier = (double) user.getAbility().activate(user, target, move, null, damage, null, null, 0, AbilityActivation.STABCalc);
            }

            damage *= stabMultiplier;
        }

        // Eficácia de tipo
        if (!move.getType(false).compare(TypeList.typeless)) {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= superEffective(move, target);
            effectivenessMultiplier /= notVeryEffective(move, target);

            if (effectivenessMessage) {
                if (effectivenessMultiplier > 1) {
                    System.out.println("It's super effective!");
                } else if (effectivenessMultiplier < 1) {
                    System.out.println("It's not very effective...");
                }
            }

            damage *= effectivenessMultiplier;
        }

        // Queimadura
        if (user.getNonVolatileStatus().compare(StatusConditionList.burn) &&
            !confusionDamage &&
            move.getCategory() == Category.Physical &&
            !move.compare(MoveList.facade) &&
            !user.getAbility().compare(AbilityList.guts)) {
            damage *= 0.5;
        }

        // Geladura
        if (target.getNonVolatileStatus().compare(StatusConditionList.frostbite) &&
            !confusionDamage &&
            move.getCategory() == Category.Physical &&
            !target.getAbility().compare(AbilityList.marvel_scale)) {
            damage *= 1.5;
        }

        // Outros
        if (Arrays.asList(move.getConditions()).contains(MoveEffectActivation.DamageCalc)) {
            damage *= (double) move.activatePrimaryEffect(user, target, null, damage, hit, true, MoveEffectActivation.DamageCalc);
        }

        if (user.getAbility().shouldActivate(AbilityActivation.UserDamageCalc)) {
            damage *= (double) user.getAbility().activate(user, target, move, null, 0, null, null, 0, AbilityActivation.UserDamageCalc);
        }
        if (target.getAbility().shouldActivate(move, AbilityActivation.OpponentDamageCalc)) {
            damage *= (double) target.getAbility().activate(target, user, move, null, 0, null, null, 0, AbilityActivation.OpponentDamageCalc);
        }

        for (StatusCondition condition : user.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.DamageCalc)) {
                damage *= (double) condition.activate(user, target, move, damage, true, StatusActivation.DamageCalc);
            }
        }

        for (StatusCondition condition : target.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentDamageCalc)) {
                damage *= (double) condition.activate(target, user, move, damage, true, StatusActivation.OpponentDamageCalc);
            }
        }

        for (FieldCondition condition : Battle.teamFields.get(target.getTeam())) {
            if (condition.shouldActivate(FieldActivation.DamageCalcDef)) {
                damage *= (double) condition.activate(user, target, move, null, null, null, 0, criticalHit, true, FieldActivation.DamageCalcDef);
            }
        }

        if (criticalHit) {
            System.out.println("A critical hit!");
        }

        return Math.max(damage, 1);
    }

    public static int directDamage(Pokemon user, Pokemon target, Move move, boolean confusionDamage) {
        int trueDamage = 0;
        int damage = 0;
        boolean substituteProtected = false;
        boolean endured = false;

        if (Arrays.asList(target.getAbility().getConditions()).contains(AbilityActivation.BeforeHit)) {
            target.getAbility().activate(target, user, move, null, damage, null, null, 0, AbilityActivation.BeforeHit);
        }

        if (move.getPrimaryEffect() != null &&
            Arrays.asList(move.getConditions()).contains(MoveEffectActivation.BeforeMove)) {
            move.activatePrimaryEffect(user, target, null, damage, 0, true, MoveEffectActivation.BeforeMove);
        }

        if ((
                !move.hasInherentProperty(InherentProperty.Charges) || (
                    user.getVolatileStatus(StatusConditionList.charging_turn) != null ||
                    user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn) != null
                )
            ) && (
                !move.hasInherentProperty(InherentProperty.Recharges) || user.getVolatileStatus(StatusConditionList.recharging_turn) == null
            )) {
            int hits;

            if (move.getPrimaryEffect() != null &&
                Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallHits)) {
                hits = (int) move.activatePrimaryEffect(user, target, null, damage, 0, true, MoveEffectActivation.CallHits);
            } else if (move.getHits().length > 1) {
                int hitRoll = (int) (Math.random()*20);

                if (hitRoll < 7) { // 7/20
                    hits = 2;
                } else if (hitRoll < 14) { // 7/20
                    hits = 3;
                } else if (hitRoll < 17) { // 3/20
                    hits = 4;
                } else { // 3/20
                    hits = 5;
                }
            } else {
                hits = move.getHits()[0];
            }

            int i = 0;
            while (i < hits && !Battle.faintCheck(target, false) && !Battle.faintCheck(user, false)) {
                if (move.getCategory() != Category.Status &&
                    (
                        !Arrays.asList(move.getConditions()).contains(MoveEffectActivation.DelayedTurnEnd) ||
                        move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)
                    )) {
                    if (move.getPrimaryEffect() == null ||
                        !Arrays.asList(move.getConditions()).contains(MoveEffectActivation.FixedDamage)) {
                        trueDamage = calcDamage(move, user, target, i, confusionDamage, i == 0);
                    } else {
                        trueDamage = (int) move.activatePrimaryEffect(user, target, null, damage, i, true, MoveEffectActivation.FixedDamage);
                    }
                    damage = trueDamage;

                    if (trueDamage > 0) {
                        if (move.getPrimaryEffect() != null &&
                            Arrays.asList(move.getConditions()).contains(MoveEffectActivation.FinalDamage)) {
                            trueDamage = (int) move.activatePrimaryEffect(user, target, null, damage, i, true, MoveEffectActivation.FinalDamage);
                            damage = trueDamage;
                        }

                        if (!confusionDamage) {
                            for (StatusCondition condition : target.getVolatileStatusList()) {
                                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.BeforeHit)) {
                                    substituteProtected = (boolean) condition.activate(target, user, move, damage, true, StatusActivation.BeforeHit);
                                }
                            }
                        }

                        if (!substituteProtected) {
                            for (StatusCondition condition : target.getVolatileStatusList()) {
                                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.DeductHP)) {
                                    endured = (boolean) condition.activate(target, user, move, damage, true, StatusActivation.DeductHP);
                                }
                            }
                            if (!endured) {
                                if (target.getAbility().shouldActivate(move, AbilityActivation.DeductHP)) {
                                    endured = (boolean) target.getAbility().activate(target, user, move, null, damage, null, null, 0, AbilityActivation.DeductHP);
                                }
                            }
                            if (!endured) {
                                if (target.getItem().shouldActivate(ItemActivation.DeductHP)) {
                                    endured = (boolean) target.getItem().activate(target, target, user, move, damage, ItemActivation.DeductHP);
                                }
                            }

                            int minHP = 0;
                            if (endured) {
                                minHP = 1;
                            }

                            damage = damage(target, user, damage, minHP, true);

                            if (user.getTeam() != target.getTeam()) {
                                user.addDamageDealt(damage);
                            }

                            if (target.getItem().isConsumed() &&
                                target.getItem().shouldActivate(ItemActivation.Consumed)) {
                                target.getItem().activate(target, target, user, move, damage, ItemActivation.Consumed);
                            }
                        }
                    }
                }

                if (!substituteProtected) {
                    for (StatusCondition condition : target.getVolatileStatusList()) {
                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.PostHitMessage)) {
                            condition.activate(target, user, move, damage, true, StatusActivation.PostHitMessage);
                        }
                    }

                    if (target.getAbility().shouldActivate(move, AbilityActivation.PostHitMessage)) {
                        target.getAbility().activate(target, user, move, null, damage, null, null, 0, AbilityActivation.PostHitMessage);
                    }

                    if (move.getSecondaryEffect() != null &&
                        Arrays.asList(move.getConditions()).contains(MoveEffectActivation.AfterMoveMultiHit)) {
                        move.activateSecondaryEffect(user, target, null, damage, i, true, MoveEffectActivation.AfterMoveMultiHit);
                    }

                    if (!confusionDamage && move.getCategory() != Category.Status &&
                        target.getAbility().shouldActivate(move, AbilityActivation.HitUser)) {
                        target.getAbility().activate(target, user, move, null, damage, null, null, 0, AbilityActivation.HitUser);
                    }

                    if (!confusionDamage && !Battle.faintCheck(target, false)) {
                        if (Arrays.asList(target.getNonVolatileStatus().getActivation()).contains(StatusActivation.Hit)) {
                            target.getNonVolatileStatus().activate(target, user, move, damage, true, StatusActivation.Hit);
                        }
                        for (StatusCondition condition : target.getVolatileStatusList()) {
                            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.Hit)) {
                                condition.activate(target, user, move, damage, true, StatusActivation.Hit);
                            }
                        }
                    }

                    if (!confusionDamage &&
                        user.getAbility().shouldActivate(AbilityActivation.HitTarget)) {
                        user.getAbility().activate(user, target, move, null, damage, null, null, 0, AbilityActivation.HitTarget);
                    }
                }

                for (int j = 0; j < user.getVolatileStatusList().size(); j++) {
                    StatusCondition condition = user.getVolatileStatusList().get(j);
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.UseMove)) {
                        condition.activate(user, target, move, damage, true, StatusActivation.UseMove);
                    }
                }

                substituteProtected = user.getVolatileStatus(StatusConditionList.substitute) != null;
                i++;
            }

            if (hits > 1) {
                System.out.print("Hit " + i + " time");
                if (i != 1) {
                    System.out.print("s");
                }
                System.out.println("!");
            }

            Battle.faintCheck(user, true);

            if (!confusionDamage &&
                Battle.faintCheck(target, true) &&
                !Battle.battleOverCheck()) {
                if (user.getAbility().shouldActivate(AbilityActivation.FaintTarget)) {
                    System.out.println();
                    user.getAbility().activate(user, target, move, null, damage, null, null, 0, AbilityActivation.FaintTarget);
                }
            }
        } else if (move.hasInherentProperty(InherentProperty.Recharges) && user.getVolatileStatus(StatusConditionList.recharging_turn) != null) {
            System.out.println(user.getName(true, true) + " must recharge!");
        }

        if (!Battle.battleOverCheck()) {
            if (move.getPrimaryEffect() != null &&
                Arrays.asList(move.getConditions()).contains(MoveEffectActivation.AfterMove)) {
                move.activatePrimaryEffect(user, target, null, damage, 0, true, MoveEffectActivation.AfterMove);
            }
            if (move.getSecondaryEffect() != null &&
                Arrays.asList(move.getConditions()).contains(MoveEffectActivation.AfterMove)) {
                move.activateSecondaryEffect(user, target, null, damage, 0, true, MoveEffectActivation.AfterMove);
            }

            if (!Battle.faintCheck(target, false) &&
                target.getItem().shouldActivate(ItemActivation.Pinch)) {
                target.getItem().activate(target, target, user, move, damage, ItemActivation.Pinch);
            }
        }


        return damage;
    }

    public static int indirectDamage(Pokemon target, Pokemon causer, int damage, Object source, String message, boolean dividers) {
        if (!(source != null && source instanceof Move && ((Move) source).compare(MoveList.struggle))) {
            if (target.getAbility().shouldActivate(AbilityActivation.TryIndirectDamage) &&
                !(boolean) target.getAbility().activate(target, causer, null, null, 0, null, null, 0, AbilityActivation.TryIndirectDamage)) {
                return 0;
            }
        }

        if (message != null && !message.isEmpty()) {
            if (dividers) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            System.out.println(message);
        }

        int finalDamage = damage(target, causer, damage, 0, false);

        if (source != null &&
            source instanceof StatusCondition &&
            ((StatusCondition) source).compare(StatusConditionList.seed)) {
            if (causer.getCurrentHP() < causer.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(causer, null, finalDamage, true, false);
            }
        }

        if (message != null && !message.isEmpty() && dividers) {
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
        }

        return finalDamage;
    }

    public static int damage(Pokemon target, Pokemon causer, int damage, int minHP, boolean direct) {
        int trueDamage = damage;
        int remainingHP = target.getCurrentHP() - damage;

        if (remainingHP < minHP) {
            damage = target.getCurrentHP() - minHP;
            target.setCurrentHP(minHP);
        } else {
            target.setCurrentHP(remainingHP);
        }

        System.out.println(target.getName(true, true) + " took " + trueDamage + " damage!");
        if (!direct) {
            Battle.faintCheck(target, true);
        }

        target.setDamagedThisTurn(true, causer);

        return damage;
    }

    public static boolean heal(Pokemon target, Move healingMove, int healedDamage, boolean showMessages, boolean zPowered) {
        if (healedDamage > 0) {
            if (target.getCurrentHP() == target.getHP()) {
                if (showMessages) {
                    if (!zPowered) {
                        GeneralMessages.modify_health.print("full health", target);
                    }
                }
                return false;
            } else {
                if (healedDamage > target.getHP() - target.getCurrentHP()) {
                    target.setCurrentHP(target.getHP());
                } else {
                    target.setCurrentHP(target.getCurrentHP() + healedDamage);
                }
                if (showMessages) {
                    String key = "heal";
                    if (zPowered) {
                        key += " Z";
                    }
                    GeneralMessages.modify_health.print(key, target, healedDamage);
                }
                return true;
            }
        }
        return false;
    }

    public static double superEffective(Move move, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(TypeList.typeless) &&
                !targetType.isSuppressed()) {
                for (Type weakness : targetType.getSuperEffective(move, false)) {
                    Type[] moveTypes;
                    if (move.getPrimaryEffect() != null &&
                        Arrays.asList(move.getConditions()).contains(MoveEffectActivation.EffectivenessCalc)) {
                        moveTypes = (Type[]) move.activatePrimaryEffect(move.getUser(), target, null, 0, 0, true, MoveEffectActivation.EffectivenessCalc);
                    } else {
                        moveTypes = new Type[] {move.getType(false)};
                    }

                    for (Type type : moveTypes) {
                        if (type.compare(weakness)) {
                            typeCount++;
                        }
                    }
                }
            }
        }

        return Math.pow(2, typeCount);
    }

    public static double superEffective(Type type, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(TypeList.typeless) &&
                !targetType.isSuppressed()) {
                for (Type weakness : targetType.getSuperEffective(null, false)) {
                    if (type.compare(weakness)) {
                        typeCount++;
                    }
                }
            }
        }

        return Math.pow(2, typeCount);
    }

    public static double notVeryEffective(Move move, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(TypeList.typeless) &&
                !targetType.isSuppressed()) {
                for (Type resistance : targetType.getNotVeryEffective(move, false)) {
                    Type[] moveTypes;
                    if (move.getPrimaryEffect() != null &&
                        Arrays.asList(move.getConditions()).contains(MoveEffectActivation.EffectivenessCalc)) {
                        moveTypes = (Type[]) move.activatePrimaryEffect(move.getUser(), target, null, 0, 0, true, MoveEffectActivation.EffectivenessCalc);
                    } else {
                        moveTypes = new Type[] {move.getType(false)};
                    }

                    for (Type type : moveTypes) {
                        if (type.compare(resistance)) {
                            typeCount++;
                        }
                    }
                }
            }
        }

        return Math.pow(2, typeCount);
    }

    public static double notVeryEffective(Type type, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(TypeList.typeless) &&
                !targetType.isSuppressed()) {
                for (Type resistance : targetType.getNotVeryEffective(null, false)) {
                    if (type.compare(resistance)) {
                        typeCount++;
                    }
                }
            }
        }

        return Math.pow(2, typeCount);
    }

    public static boolean ineffective(Move move, Pokemon target) {
        if (move.getType(false).compare(TypeList.ground) && !target.isGrounded(move)) {
            return true;
        }

        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(TypeList.typeless) &&
                !targetType.isSuppressed()) {
                for (Type immunity : targetType.getIneffective(move, false)) {
                    for (Type type : move.getTypeList()) {
                        boolean immunityIgnored = false;

                        if (type.compare(TypeList.ground) && target.isGrounded(move)) {
                            immunityIgnored = true;
                        }

                        if (type.compare(immunity) &&
                            !immunityIgnored) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean ineffective(Type type, Pokemon target) {
        if (type.compare(TypeList.ground) && !target.isGrounded(null)) {
            return true;
        }

        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(TypeList.typeless) &&
                !targetType.isSuppressed()) {
                for (Type immunity : targetType.getIneffective(null, false)) {
                    boolean immunityIgnored = false;

                    if (type.compare(TypeList.ground) && target.isGrounded(null)) {
                        immunityIgnored = true;
                    }

                    if (type.compare(immunity) &&
                        !immunityIgnored) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean ineffectiveStatus(Move move, Pokemon target) {
        if (move.getUser() != target) {
            for (Type targetType : target.getTypes()) {
                if (!targetType.compare(TypeList.typeless) &&
                    !targetType.isSuppressed()) {
                    for (Object immunity : targetType.getAdditionalImmunities()) {
                        if (immunity == MoveType.Powder && Arrays.asList(move.getMoveTypes()).contains(MoveType.Powder)) {
                            return true;
                        }
                        if (immunity == TemporaryProperty.PranksterBoosted && move.getTemporaryProperties().contains(TemporaryProperty.PranksterBoosted)) {
                            return true;
                        }
                    }
                }
            }
        }

        if (move.getPrimaryEffect() != null &&
            Arrays.asList(move.getConditions()).contains(MoveEffectActivation.TestImmunities)) {
            boolean immune = !((boolean) move.activatePrimaryEffect(move.getUser(), target, null, 0, 0, false, MoveEffectActivation.TestImmunities));
            if (immune) {
                return true;
            }
        }

        return false;
    }
}
