package com.github.lucasaugustoss.simulator;

import java.util.Arrays;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.messages.MessageStorage;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.data.properties.stats.StatType;

public class Damage {
    public int amount;
    public int trueAmount;
    public Object source;
    public DamageSource sourceType;

    public Damage(int amount, Object source, DamageSource sourceType) {
        this.amount = amount;
        this.trueAmount = amount;
        this.source = source;
        this.sourceType = sourceType;
    }


    private static int calcDamage(Move move, Pokemon user, Pokemon target, int hit, DamageSource damageSource, boolean confusionDamage, boolean effectivenessMessage) {
        if (target.getAbility().shouldActivate(AbilityActivation.TryDamage) &&
            !(boolean) target.getAbility().activate(target, user, null, null, new Damage(0, null, DamageSource.Move), hit, null, null, 0, true, AbilityActivation.TryDamage)) {
            return 0;
        }

        double critChance = 1.0/24.0;

        int critStage = move.getCritRatio() - 1;
        if (user.getAbility().shouldActivate(AbilityActivation.CritRatioCalc)) {
            critStage += (int) user.getAbility().activate(user, null, null, null, null, hit, null, null, 0, true, AbilityActivation.CritRatioCalc);
        }
        for (StatusCondition condition : user.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.CritRatioCalc)) {
                critStage += (int) condition.activate(user, null, null, null, true, StatusActivation.CritRatioCalc);
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
            criticalHit = (boolean) target.getAbility().activate(target, user, move, null, null, hit, null, null, 0, true, AbilityActivation.TryCritUser);
        }


        double power = move.getPower(confusionDamage, confusionDamage, hit);

        Stat statA = move.getCategory() == Category.Physical ? user.getStat(StatName.Atk) : user.getStat(StatName.SpA);
        if (move.primaryShouldActivate(MoveEffectActivation.CallAttackingStat)) {
            statA = (Stat) move.activatePrimary(user, target, null, null, hit, statA, true, MoveEffectActivation.CallAttackingStat);
        }
        for (FieldCondition condition : Battle.generalField) {
            if (condition.shouldActivate(FieldActivation.CallAttackingStat)) {
                statA = (Stat) condition.activate(user, target, move, null, null, statA, 0, false, true, FieldActivation.CallAttackingStat);
            }
        }
        Stat statD = move.getCategory() == Category.Physical ? target.getStat(StatName.Def) : target.getStat(StatName.SpD);
        if (move.primaryShouldActivate(MoveEffectActivation.CallDefendingStat)) {
            statD = (Stat) move.activatePrimary(user, target, null, null, hit, statD, true, MoveEffectActivation.CallDefendingStat);
        }

        int A = statA.getEffectiveValue(target, move, criticalHit, StatType.Offensive);
        int D = statD.getEffectiveValue(user, move, criticalHit, StatType.Defensive);


        // base
        int damage = (int) Math.floor(Math.floor(Math.floor(2*user.getLevel()/5 + 2) * power * A/D)/50 + 2);

        // clima/terreno
        if (Battle.getWeather(move).shouldActivate(FieldActivation.DamageCalcAtk)) {
            damage *= (double) Battle.getWeather(move).activate(user, target, move, null, null, null, 0, false, true, FieldActivation.DamageCalcAtk);
        }
        if (Battle.getTerrain().shouldActivate(user, FieldActivation.DamageCalcAtk)) {
            damage *= (double) Battle.getTerrain().activate(user, target, move, null, null, null, 0, false, true, FieldActivation.DamageCalcAtk);
        }
        if (Battle.getTerrain().shouldActivate(target, FieldActivation.DamageCalcDef)) {
            damage *= (double) Battle.getTerrain().activate(user, target, move, null, null, null, 0, false, true, FieldActivation.DamageCalcDef);
        }

        // crítico
        if (criticalHit) {
            damage *= 1.5;

            if (user.getAbility().shouldActivate(AbilityActivation.Crit)) {
                damage *= (double) user.getAbility().activate(user, target, move, null, new Damage(damage, move, damageSource), hit, null, null, 0, true, AbilityActivation.Crit);
            }
        }

        // valor aleatório
        damage = (int) (damage * (85 + Math.floor(Math.random()*16))/100);

        // STAB
        boolean isSTAB = false;

        if (user.getAbility().shouldActivate(AbilityActivation.CallSTAB) &&
            (boolean) user.getAbility().activate(user, target, move, null, new Damage(damage, move, damageSource), hit, null, null, 0, true, AbilityActivation.CallSTAB)) {
            isSTAB = true;
        }
        if (!move.getType(false, false).compare(Data.get().getType("typeless")) &&
            (
                move.getType(false, false).compare(user.getType(0)) ||
                move.getType(false, false).compare(user.getType(1)) ||
                move.getType(false, false).compare(user.getType(2))
            )) {
            isSTAB = true;
        }

        if (isSTAB) {
            double stabMultiplier = 1.5;
            if (user.getAbility().shouldActivate(AbilityActivation.STABCalc)) {
                stabMultiplier = (double) user.getAbility().activate(user, target, move, null, new Damage(damage, move, damageSource), hit, null, null, 0, true, AbilityActivation.STABCalc);
            }

            damage *= stabMultiplier;
        }

        // eficácia de tipo
        if (!move.getType(false, false).compare(Data.get().getType("typeless"))) {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= superEffective(move, target);
            effectivenessMultiplier /= notVeryEffective(move, target);

            if (effectivenessMessage) {
                String key = "";
                if (effectivenessMultiplier > 1) {
                    key = "super effective";
                } else if (effectivenessMultiplier < 1) {
                    key = "not very effective";
                }

                if (!key.isEmpty()) {
                    MessageHandler.add("effectiveness", key, Map.of(
                        "Pokemon", target.getName(true, false)
                    ));
                }
            }

            damage *= effectivenessMultiplier;
        }

        // queimadura
        if (user.getNonVolatileStatus().compare(Data.get().getStatusCondition("burn")) &&
            !confusionDamage &&
            move.getCategory() == Category.Physical &&
            !move.compare(Data.get().getMove("facade")) &&
            !user.getAbility().compare(Data.get().getAbility("guts"))) {
            damage *= 0.5;
        }

        // geladura
        if (target.getNonVolatileStatus().compare(Data.get().getStatusCondition("frostbite")) &&
            !confusionDamage &&
            move.getCategory() == Category.Physical &&
            !target.getAbility().compare(Data.get().getAbility("marvel_scale"))) {
            damage *= 1.5;
        }

        // outros
        if (move.primaryShouldActivate(MoveEffectActivation.DamageCalc)) {
            damage *= (double) move.activatePrimary(user, target, null, new Damage(damage, move, damageSource), hit, null, true, MoveEffectActivation.DamageCalc);
        }

        if (user.getAbility().shouldActivate(AbilityActivation.UserDamageCalc)) {
            damage *= (double) user.getAbility().activate(user, target, move, null, null, hit, null, null, 0, true, AbilityActivation.UserDamageCalc);
        }
        if (target.getAbility().shouldActivate(move, AbilityActivation.OpponentDamageCalc)) {
            damage *= (double) target.getAbility().activate(target, user, move, null, null, hit, null, null, 0, true, AbilityActivation.OpponentDamageCalc);
        }

        for (StatusCondition condition : user.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.DamageCalc)) {
                damage *= (double) condition.activate(user, target, move, new Damage(damage, move, damageSource), true, StatusActivation.DamageCalc);
            }
        }

        for (StatusCondition condition : target.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentDamageCalc)) {
                damage *= (double) condition.activate(target, user, move, new Damage(damage, move, damageSource), true, StatusActivation.OpponentDamageCalc);
            }
        }

        for (FieldCondition condition : Battle.teamFields.get(target.getTeam())) {
            if (condition.shouldActivate(FieldActivation.DamageCalcDef)) {
                damage *= (double) condition.activate(user, target, move, null, null, null, 0, criticalHit, true, FieldActivation.DamageCalcDef);
            }
        }

        if (criticalHit) {
            MessageHandler.add("modify_health", "crit", null);
        }

        return Math.max(damage, 1);
    }

    public static Damage directDamage(Pokemon user, Pokemon target, Move move, boolean confusionDamage) {
        Damage damage = new Damage(0, move, DamageSource.Move);
        Damage totalDamage = new Damage(0, move, DamageSource.Move);
        boolean substituteProtected = false;
        boolean endured = false;

        if (Arrays.asList(target.getAbility().getConditions()).contains(AbilityActivation.BeforeHit)) {
            target.getAbility().activate(target, user, move, null, damage, 0, null, null, 0, true, AbilityActivation.BeforeHit);
        }

        int fixedDamage = -1;
        if (move.primaryShouldActivate(MoveEffectActivation.FixedDamage)) {
            fixedDamage = (int) move.activatePrimary(user, target, null, damage, 0, null, true, MoveEffectActivation.FixedDamage);
        }

        if (move.primaryShouldActivate(MoveEffectActivation.BeforeMove)) {
            move.activatePrimary(user, target, null, damage, 0, null, true, MoveEffectActivation.BeforeMove);
        }

        if ((
                !move.hasInherentProperty(InherentProperty.Charges) || (
                    user.getVolatileStatus(Data.get().getStatusCondition("charging_turn")) != null ||
                    user.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn")) != null
                )
            ) && (
                !move.hasInherentProperty(InherentProperty.Recharges) || user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn")) == null
            )) {
            int maxHits = move.getHits()[move.getHits().length - 1];

            if (move.primaryShouldActivate(MoveEffectActivation.CallHits)) {
                maxHits = (int) move.activatePrimary(user, target, null, damage, 0, null, true, MoveEffectActivation.CallHits);
            }
            if (!confusionDamage && user.getAbility().shouldActivate(move, AbilityActivation.CallHits)) {
                maxHits *= (int) user.getAbility().activate(user, target, move, null, damage, 0, null, null, 0, true, AbilityActivation.CallHits);
            }
            int hitRoll = move.getHits().length > 1 ? (int) (Math.random()*20) : -1;

            int i = 0;
            while (i < maxHits && !Battle.faintCheck(target, null, false) && !Battle.faintCheck(user, null, false)) {
                if (move.multiHitIsAccuracy() && i != 0 &&
                    !Battle.accuracyCheck(move, user, target)) {
                    break;
                } else if (hitRoll > -1) {
                                                    // 2 hits: 7/10 (0-6)
                    if (i >= 2 && hitRoll < 7  ||   // 3 hits: 7/20 (7-13)
                        i >= 3 && hitRoll < 14 ||   // 4 hits: 3/20 (14-17)
                        i >= 4 && hitRoll < 17) {   // 5 hits: 3/20 (17-20)
                        break;
                    }
                }

                if (move.getCategory() != Category.Status &&
                    (
                        !move.primaryShouldActivate(MoveEffectActivation.DelayedTurnEnd) ||
                        move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)
                    )) {
                    damage.trueAmount = fixedDamage != -1 ? fixedDamage : calcDamage(move, user, target, i, DamageSource.Move, confusionDamage, i == 0);
                    damage.amount = damage.trueAmount;

                    if (damage.trueAmount > 0) {
                        if (move.primaryShouldActivate(MoveEffectActivation.FinalDamage)) {
                            damage.trueAmount = (int) move.activatePrimary(user, target, null, damage, i, null, true, MoveEffectActivation.FinalDamage);
                            damage.amount = damage.trueAmount;
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
                                    endured = (boolean) target.getAbility().activate(target, user, move, null, damage, 0, null, null, 0, true, AbilityActivation.DeductHP);
                                }
                            }
                            if (!endured) {
                                if (target.getItem().shouldActivate(ItemActivation.DeductHP)) {
                                    endured = (boolean) target.getItem().activate(target, target, user, move, damage, ItemActivation.DeductHP);
                                }
                            }

                            if (move.primaryShouldActivate(MoveEffectActivation.BeforeDamage)) {
                                move.activatePrimary(user, target, null, damage, 0, null, true, MoveEffectActivation.BeforeDamage);
                            }

                            int minHP = 0;
                            if (endured) {
                                minHP = 1;
                            }

                            damage.amount = damage(target, user, damage.amount, minHP, true);
                            totalDamage.amount += damage.amount;
                            totalDamage.trueAmount += damage.trueAmount;

                            if (user.getTeam() != target.getTeam()) {
                                user.addDamageDealt(damage.amount);
                            }

                            if (!Battle.faintCheck(target, null, false) &&
                                target.getItem().shouldActivate(ItemActivation.Pinch)) {
                                target.getItem().activate(target, target, user, move, damage, ItemActivation.Pinch);
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
                        target.getAbility().activate(target, user, move, null, damage, 0, null, null, 0, true, AbilityActivation.PostHitMessage);
                    }

                    if (!confusionDamage && move.getCategory() != Category.Status &&
                        target.getAbility().shouldActivate(move, AbilityActivation.HitUser)) {
                        target.getAbility().activate(target, user, move, null, damage, 0, null, null, 0, true, AbilityActivation.HitUser);
                    }

                    if (!confusionDamage && !Battle.faintCheck(target, null, false)) {
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
                        user.getAbility().activate(user, target, move, null, damage, 0, null, null, 0, true, AbilityActivation.HitTarget);
                    }

                    if (move.primaryShouldActivate(MoveEffectActivation.AfterHit)) {
                        move.activatePrimary(user, target, null, damage, i, null, true, MoveEffectActivation.AfterHit);
                    }
                    if (move.secondaryShouldActivate(MoveEffectActivation.AfterHit)) {
                        move.activateSecondary(user, target, null, damage, i, null, true, MoveEffectActivation.AfterHit);
                    }
                }

                for (int j = 0; j < user.getVolatileStatusList().size(); j++) {
                    StatusCondition condition = user.getVolatileStatusList().get(j);
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.UseMove)) {
                        condition.activate(user, target, move, damage, true, StatusActivation.UseMove);
                    }
                }

                substituteProtected = user.getVolatileStatus(Data.get().getStatusCondition("substitute")) != null;
                i++;
            }

            if (maxHits > 1) {
                String key = "hit " + (i == 1 ? "one" : "multi");

                MessageHandler.add("modify_health", key, Map.of(
                    "Number", String.valueOf(i)
                ));
            }

            if (!confusionDamage &&
                Battle.faintCheck(target, move, true) &&
                !Battle.battleIsOver()) {
                if (user.getAbility().shouldActivate(AbilityActivation.FaintTarget)) {
                    user.getAbility().activate(user, target, move, null, damage, 0, null, null, 0, true, AbilityActivation.FaintTarget);
                }
            }
        } else if (move.hasInherentProperty(InherentProperty.Recharges) && user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn")) != null) {
            MessageHandler.add("move", "recharge", Map.of(
                "Pokemon", user.getName(true, false)
            ));
        }

        if (move.primaryShouldActivate(MoveEffectActivation.Recoil)) {
            move.activatePrimary(user, target, null, totalDamage, 0, null, true, MoveEffectActivation.Recoil);
        }

        if (!Battle.battleIsOver()) {
            boolean charging = user.getVolatileStatus(Data.get().getStatusCondition("charging_turn")) != null ||
                               user.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn")) != null;

            if (move.primaryShouldActivate(MoveEffectActivation.AfterMove)) {
                move.activatePrimary(user, target, null, damage, 0, null, true, MoveEffectActivation.AfterMove);
            }
            if (charging &&
                move.primaryShouldActivate(MoveEffectActivation.AfterMoveCharged)) {
                move.activatePrimary(user, target, null, damage, 0, null, true, MoveEffectActivation.AfterMoveCharged);
            }

            if (move.secondaryShouldActivate(MoveEffectActivation.AfterMove)) {
                move.activateSecondary(user, target, null, damage, 0, null, true, MoveEffectActivation.AfterMove);
            }
            if (charging &&
                move.secondaryShouldActivate(MoveEffectActivation.AfterMoveCharged)) {
                move.activateSecondary(user, target, null, damage, 0, null, true, MoveEffectActivation.AfterMoveCharged);
            }
        }


        return damage;
    }

    public static Damage indirectDamage(Pokemon target, Pokemon causer, int damage, int drainAmount, DamageSource damageSource, Object source, MessageStorage message, boolean dividers) {
        if (!(source != null && source instanceof Move && ((Move) source).compare(Data.get().getMove("struggle")))) {
            if (target.getAbility().shouldActivate(AbilityActivation.TryDamage) &&
                !(boolean) target.getAbility().activate(target, causer, null, null, new Damage(damage, source, damageSource), 0, null, null, 0, true, AbilityActivation.TryDamage)) {
                return new Damage(0, source, damageSource);
            }
        }

        if (message != null) {
            MessageHandler.add(message);
        } else {
            String sourceName = "";
            if (source instanceof Move move) {
                sourceName = move.getName();
            } else if (source instanceof Ability ability) {
                sourceName = ability.getName();
            } else if (source instanceof Item item) {
                sourceName = item.getName();
            } else if (source instanceof StatusCondition status) {
                sourceName = status.getName();
            } else if (source instanceof FieldCondition field) {
                sourceName = field.getName();
            }

            MessageHandler.add("modify_health", "indirect damage", Map.of(
                "Pokemon", target.getName(true, false),
                "Source", sourceName
            ));
        }

        int finalDamage = damage(target, causer, damage, 0, false);

        if (drainAmount != 0) {
            if (causer.getCurrentHP() < causer.getHP()) {
                heal(causer, null, finalDamage*drainAmount, true, false);
            }
        }

        return new Damage(finalDamage, source, damageSource);
    }

    private static int damage(Pokemon target, Pokemon causer, int damage, int minHP, boolean direct) {
        int trueDamage = damage;
        int remainingHP = target.getCurrentHP() - damage;

        if (remainingHP < minHP) {
            damage = target.getCurrentHP() - minHP;
            target.setCurrentHP(minHP);
        } else {
            target.setCurrentHP(remainingHP);
        }

        MessageHandler.add("modify_health", "damage", Map.of(
            "Pokemon", target.getName(true, false),
            "Number", String.valueOf(trueDamage)
        ));

        if (!direct) {
            Battle.faintCheck(target, null, true);
        }

        target.setDamagedThisTurn(true, causer);

        return damage;
    }

    public static boolean heal(Pokemon target, Move healingMove, int healedDamage, boolean showMessages, boolean zPowered) {
        if (healedDamage > 0) {
            if (target.getCurrentHP() == target.getHP()) {
                if (showMessages) {
                    if (!zPowered) {
                        MessageHandler.add("modify_health", "full health", Map.of(
                            "Pokemon", target.getName(true, false)
                        ));
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

                    MessageHandler.add("modify_health", key, Map.of(
                        "Pokemon", target.getName(true, false),
                        "Number", String.valueOf(healedDamage)
                    ));
                }
                return true;
            }
        }
        return false;
    }

    public static double superEffective(Move move, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(Data.get().getType("typeless")) &&
                !targetType.isSuppressed()) {
                for (TypeTemplate weakness : targetType.getSuperEffective(move, false)) {
                    Type[] moveTypes;
                    if (move.primaryShouldActivate(MoveEffectActivation.EffectivenessCalc)) {
                        moveTypes = (Type[]) move.activatePrimary(move.getUser(), target, null, null, 0, null, true, MoveEffectActivation.EffectivenessCalc);
                    } else {
                        moveTypes = new Type[] {move.getType(false, false)};
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

    public static double superEffective(TypeTemplate type, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(Data.get().getType("typeless")) &&
                !targetType.isSuppressed()) {
                for (TypeTemplate weakness : targetType.getSuperEffective(null, false)) {
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
            if (!targetType.compare(Data.get().getType("typeless")) &&
                !targetType.isSuppressed()) {
                for (TypeTemplate resistance : targetType.getNotVeryEffective(move, false)) {
                    Type[] moveTypes;
                    if (move.primaryShouldActivate(MoveEffectActivation.EffectivenessCalc)) {
                        moveTypes = (Type[]) move.activatePrimary(move.getUser(), target, null, null, 0, null, true, MoveEffectActivation.EffectivenessCalc);
                    } else {
                        moveTypes = new Type[] {move.getType(false, false)};
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

    public static double notVeryEffective(TypeTemplate type, Pokemon target) {
        int typeCount = 0;
        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(Data.get().getType("typeless")) &&
                !targetType.isSuppressed()) {
                for (TypeTemplate resistance : targetType.getNotVeryEffective(null, false)) {
                    if (type.compare(resistance)) {
                        typeCount++;
                    }
                }
            }
        }

        return Math.pow(2, typeCount);
    }

    public static boolean ineffective(Move move, Pokemon target) {
        if (move.getType(false, false).compare(Data.get().getType("ground")) && !target.isGrounded(move)) {
            return true;
        }

        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(Data.get().getType("typeless")) &&
                !targetType.isSuppressed()) {
                for (TypeTemplate immunity : targetType.getIneffective(move, false)) {
                    for (Type type : move.getTypeList()) {
                        boolean immunityIgnored = false;

                        if (type.compare(Data.get().getType("ground")) && target.isGrounded(move)) {
                            immunityIgnored = true;
                        }

                        if (type.compare(immunity) &&
                            !immunityIgnored) {
                            return true;
                        }
                    }
                }

                for (Object immunity : targetType.getAdditionalImmunities()) {
                    if (immunity instanceof MoveTemplate m && m.compare(move)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean ineffective(TypeTemplate type, Pokemon target) {
        if (type.compare(Data.get().getType("ground")) && !target.isGrounded(null)) {
            return true;
        }

        for (Type targetType : target.getTypes()) {
            if (!targetType.compare(Data.get().getType("typeless")) &&
                !targetType.isSuppressed()) {
                for (TypeTemplate immunity : targetType.getIneffective(null, false)) {
                    boolean immunityIgnored = false;

                    if (type.compare(Data.get().getType("ground")) && target.isGrounded(null)) {
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
                if (!targetType.compare(Data.get().getType("typeless")) &&
                    !targetType.isSuppressed()) {
                    for (Object immunity : targetType.getAdditionalImmunities()) {
                        if (immunity == MoveType.Powder && move.isMoveType(MoveType.Powder)) {
                            return true;
                        }
                        if (immunity == TemporaryProperty.PranksterBoosted && move.getTemporaryProperties().contains(TemporaryProperty.PranksterBoosted)) {
                            return true;
                        }
                    }
                }
            }
        }

        if (move.primaryShouldActivate(MoveEffectActivation.TestImmunities)) {
            boolean immune = !((boolean) move.activatePrimary(move.getUser(), target, null, null, 0, null, false, MoveEffectActivation.TestImmunities));
            if (immune) {
                return true;
            }
        }

        return false;
    }
}
