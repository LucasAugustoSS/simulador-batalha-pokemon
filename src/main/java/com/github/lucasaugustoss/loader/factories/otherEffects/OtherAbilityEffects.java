package com.github.lucasaugustoss.loader.factories.otherEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.AbilityEffectFunction;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.oldObjects.MoveList;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.actions.Action;

public class OtherAbilityEffects {
    public static final AbilityEffectFunction air_lock =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                thisAbility.getMessages().print("start", Map.of(
                    "Pokemon", self.getName(true, false)
                ));

                for (Pokemon activePokemon : Battle.orderPokemon(self, opponent)) {
                    Pokemon opponentPokemon = null;
                    if (activePokemon == self) {
                        opponentPokemon = opponent;
                    } else {
                        opponentPokemon = self;
                    }

                    if (activePokemon.getAbility().shouldActivate(AbilityActivation.WeatherChange)) {
                        activePokemon.getAbility().activate(activePokemon, opponentPokemon, null, null, null, null, null, 0, AbilityActivation.WeatherChange);
                    }
                }
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.CallWeather) {
                if (!Battle.faintCheck(self, false) &&
                    (self == Battle.yourActivePokemon || self == Battle.opponentActivePokemon)) { // garante que não vai afetar as abilities em SwitchOut
                    return Data.get().getFieldCondition("clear").cause(null, null, null);
                }
                return Battle.getTrueWeather();
            }

            if ((condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) &&
                (
                    !Battle.opponentActivePokemon.getAbility().compare(Data.get().getAbility("air_lock")) ||
                    !Battle.opponentActivePokemon.getAbility().shouldActivate(null)
                )) {
                if (condition == AbilityActivation.SwitchOut) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else if (condition == AbilityActivation.FaintUser) {
                    System.out.println();
                }

                if (!self.getAbility().compare(Data.get().getAbility("delta_stream"))) { // garante que não vai aparecer quando o Rayquaza Mega Evoluir
                    thisAbility.getMessages().print("end", Map.of(
                        "Pokemon", self.getName(true, false)
                    ));
                }

                for (Pokemon activePokemon : Battle.orderPokemon(self, opponent)) {
                    Pokemon opponentPokemon = null;
                    if (activePokemon == self) {
                        opponentPokemon = opponent;
                    } else {
                        opponentPokemon = self;
                    }

                    if (activePokemon.getAbility().shouldActivate(AbilityActivation.WeatherChange)) {
                        activePokemon.getAbility().activate(activePokemon, opponentPokemon, null, null, null, null, null, 0, AbilityActivation.WeatherChange);
                    }
                }
                if (condition == AbilityActivation.SwitchOut) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            return null;
        };

    public static final AbilityEffectFunction antithesis =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.CallUserSuperEffective || condition == AbilityActivation.CallOpponentSuperEffective) {
                List<TypeTemplate> newWeaknesses = new ArrayList<>();

                for (TypeTemplate type2 : Data.get().getTypeList().values()) {
                    for (TypeTemplate type2Weakness : type2.getSuperEffective()) {
                        if (type2Weakness.compare(type)) {
                            newWeaknesses.add(type2);
                            break;
                        }
                    }
                }

                return newWeaknesses.toArray(new TypeTemplate[0]);
            }

            if (condition == AbilityActivation.CallUserNotVeryEffective || condition == AbilityActivation.CallOpponentNotVeryEffective) {
                List<TypeTemplate> newResistances = new ArrayList<>();

                for (TypeTemplate type2 : Data.get().getTypeList().values()) {
                    for (TypeTemplate type2Resistance : type2.getNotVeryEffective()) {
                        if (type2Resistance.compare(type)) {
                            newResistances.add(type2);
                            break;
                        }
                    }
                }

                return newResistances.toArray(new TypeTemplate[0]);
            }

            if (condition == AbilityActivation.CallUserIneffective || condition == AbilityActivation.CallOpponentIneffective) {
                List<TypeTemplate> newImmunities = new ArrayList<>();

                for (TypeTemplate type2 : Data.get().getTypeList().values()) {
                    for (TypeTemplate type2Immunity : type2.getIneffective()) {
                        if (type2Immunity.compare(type)) {
                            newImmunities.add(type2);
                            break;
                        }
                    }
                }

                return newImmunities.toArray(new TypeTemplate[0]);
            }

            return null;
        };

    public static final AbilityEffectFunction block_stat_drops =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (statChangeStages < 0) {
                thisAbility.getMessages().print("block stat drop", Map.of(
                    "Pokemon", self.getName(true, false)
                ));
                return true;
            }
            return false;
        };

    public static final AbilityEffectFunction darkest_day =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.UserPowerCalc) {
                if (!move.compare(MoveList.dynamax_cannon) && !move.compare(MoveList.eternabeam)) {
                    if (move.getPower(false, true, 0) >= 150) {
                        return 1.0;
                    }

                    if (move.getPower(false, true, 0)*1.1 > 150) {
                        return 150.0/move.getPower(false, true, 0);
                    }

                    return 1.1;
                }
                return 1.0;
            }

            // mudanças de Dynamax Cannon e Eternabeam são feitas nos respectivos movimentos

            if (condition == AbilityActivation.OpponentDamageCalc) {
                if (move.compare(MoveList.behemoth_blade) || move.compare(MoveList.behemoth_bash) || move.compare(MoveList.dynamax_cannon)) {
                    return 1.5;
                }
                return 1.0;
            }

            if (condition == AbilityActivation.CallMove) {
                if (move != null &&
                    move.getCategory() == Category.Status) {
                    Move maxGuard = new Move(MoveList.max_guard, move, self);

                    return maxGuard;
                }
                return move;
            }

            if (condition == AbilityActivation.TurnEnd) {
                thisAbility.setCounter(thisAbility.getCounter() + 1);

                if (thisAbility.getCounter() > 3) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    System.out.println(self.getName(true, true) + " has returned to its usual form!");
                    self.changeForm("");
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            // ativa ao trocar de forma
            if (condition == AbilityActivation.Removed) {
                if (self.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) != null) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                thisAbility.getMessages().print("end", null);

                if (self.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) != null) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            return null;
        };

    public static final AbilityEffectFunction disable_permanent =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            thisAbility.setActive(false);
            return null;
        };

    public static final AbilityEffectFunction download =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            int opponentDef = opponent.getStat(StatName.Def).getValue();
            int opponentDefStages = opponent.getStat(StatName.Def).getStages(null, null);
            double valDef = 1 + Math.abs(opponentDefStages)*0.5;
            opponentDef = (int) (opponentDefStages >= 0 ? opponentDef*valDef : opponentDef/valDef);

            int opponentSpD = opponent.getStat(StatName.SpD).getValue();
            int opponentSpDStages = opponent.getStat(StatName.SpD).getStages(null, null);
            double valSpD = 1 + Math.abs(opponentSpDStages)*0.5;
            opponentSpD = (int) (opponentSpDStages >= 0 ? opponentSpD*valSpD : opponentSpD/valSpD);

            if (condition == AbilityActivation.Entry) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                System.out.println();
            }

            if (opponentDef < opponentSpD) {
                self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
            } else {
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
            }

            if (condition == AbilityActivation.Entry) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        };

    public static final AbilityEffectFunction flash_fire =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.TryHitUser) {
                if (move.getType(false, false).compare(Data.get().getType("fire")) && move.targetsOpponent()) {
                    if (!thisAbility.persistentIsActive()) {
                        thisAbility.getMessages().print("activate", Map.of(
                            "Pokemon", self.getName(true, false)
                        ));

                        thisAbility.setPersistentActive(true);
                    } else {
                        thisAbility.getMessages().print("block move", Map.of(
                            "Pokemon", self.getName(true, false)
                        ));
                    }
                    return false;
                }
                return true;
            }

            if (condition == AbilityActivation.AttackCalc || condition == AbilityActivation.SpecialAttackCalc) {
                if (thisAbility.persistentIsActive() &&
                    move.getType(false, false).compare(Data.get().getType("fire"))) {
                    return 1.5;
                }
                return 1.0;
            }

            return null;
        };

    public static final AbilityEffectFunction forecast =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (Battle.getWeather().compare(Data.get().getFieldCondition("sun")) || Battle.getWeather().compare(Data.get().getFieldCondition("desolate_land"))) {
                if (!self.compareWithForm(Data.get().getPokemon("castform_sunny"))) {
                    self.changeForm("Sunny");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                    System.out.println(self.getName(true, true) + " transformed into its Sunny Form!");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else if (Battle.getWeather().compare(Data.get().getFieldCondition("rain")) || Battle.getWeather().compare(Data.get().getFieldCondition("primordial_sea"))) {
                if (!self.compareWithForm(Data.get().getPokemon("castform_rainy"))) {
                    self.changeForm("Rainy");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                    System.out.println(self.getName(true, true) + " transformed into its Rainy Form!");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else if (Battle.getWeather().compare(Data.get().getFieldCondition("snow"))) {
                if (!self.compareWithForm(Data.get().getPokemon("castform_snowy"))) {
                    self.changeForm("Snowy");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                    System.out.println(self.getName(true, true) + " transformed into its Snowy Form!");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else {
                if (!self.compareWithForm(Data.get().getPokemon("castform"))) {
                    self.changeForm("Normal");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                    System.out.println(self.getName(true, true) + " transformed into its Normal Form!");
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            return null;
        };

    public static final AbilityEffectFunction frisk =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (!opponent.getItem().compare(Data.get().getItem("none"))) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " frisked " + opponent.getName(true, false) + " and found its " + opponent.getItem().getName() + "!");
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        };

    public static final AbilityEffectFunction block_forced_switch =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            thisAbility.getMessages().print("block forced switch", Map.of(
                "Pokemon", self.getName(true, false)
            ));

            return false;
        };

    public static final AbilityEffectFunction illusion =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            Pokemon disguise = null;
            for (Pokemon teamMember : Battle.teams[self.getTeam()]) {
                if (teamMember != null &&
                    (teamMember == self || !Battle.faintCheck(teamMember, false))) {
                    disguise = teamMember;
                }
            }
            if (disguise == self) {
                disguise = null;
            }

            if (condition == AbilityActivation.CallUserData) {
                if (disguise != null) {
                    return disguise;
                } else {
                    return self;
                }
            }

            if (condition == AbilityActivation.HitUser || condition == AbilityActivation.Removed) {
                if (disguise != null) {
                    thisAbility.setActive(false);
                    System.out.println(self.getName(true, true) + "'s Illusion wore off!");
                }
            }

            return null;
        };

    public static final AbilityEffectFunction protean =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.UseMove &&
                !thisAbility.persistentIsActive()) {
                Type newType = move.getType(false, false);
                self.setTypes(new Type[] {newType});

                thisAbility.getMessages().print("change type", Map.of(
                    "Pokemon", self.getName(true, false),
                    "Type", newType.getName()
                ));

                thisAbility.setPersistentActive(true);
            }
            if (condition == AbilityActivation.SwitchOut) {
                thisAbility.setPersistentActive(false);
            }
            return null;
        };

    public static final AbilityEffectFunction magic_bounce =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (move.getCategory() == Category.Status && move.targetsOpponent() &&
                !move.hasInherentProperty(InherentProperty.NotReflectable) &&
                !move.getTemporaryProperties().contains(TemporaryProperty.Reflected)) {

                thisAbility.getMessages().print("block move", Map.of(
                    "Pokemon", self.getName(true, false),
                    "Move", move.getName()
                ));

                Action moveAction = Battle.findAction(move, opponent);
                Move copiedMove = new Move(move, self);
                for (TemporaryProperty property : move.getTemporaryProperties()) {
                    copiedMove.addProperty(property);
                }
                copiedMove.addProperty(TemporaryProperty.Reflected);

                Battle.addAction(new Action(copiedMove, self, opponent), moveAction);

                return false;
            }
            return true;
        };

    public static final AbilityEffectFunction magician =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean opponentItemRemovable = !opponent.getItem().heldByValidUser(true) || !opponent.getItem().isTetheredToValidUser();

            if (!opponent.getItem().compare(Data.get().getItem("none")) &&
                opponent.getItem().getType() != ItemType.ZCrystal &&
                opponentItemRemovable &&
                self.getItem().compare(Data.get().getItem("none")) &&
                move.targetsOpponent() &&
                // Fling, Natural Gift, Future Sight e Doom Desire não ativam
                !move.compare(MoveList.fling) &&
                !move.compare(MoveList.future_sight) &&
                !move.compare(MoveList.doom_desire)) {
                System.out.println(self.getName(true, true) + "'s Magician stole " + opponent.getName(true, false) + "'s " + opponent.getItem().getName() + "!");
                self.giveItem(opponent.takeItem());
            }
            return null;
        };

    public static final AbilityEffectFunction power_construct =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (self.getCurrentHP() < self.getHP()/2.0) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println("You sense the presence of many!");
                self.changeForm("Complete");
                System.out.println(self.getName(true, true) + " transformed into its Complete Forme!");
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                thisAbility.setActive(false);
            }
            return null;
        };

    public static final AbilityEffectFunction prankster =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (move.getCategory() == Category.Status) {
                move.addProperty(TemporaryProperty.PranksterBoosted);
                return 1;
            }
            return 0;
        };

    public static final AbilityEffectFunction block_secondary_effect =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (move.getSecondaryEffectTarget() == EffectTarget.Target) {
                return true;
            }
            return false;
        };

    public static final AbilityEffectFunction slow_start =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                thisAbility.getMessages().print("start", Map.of(
                    "Pokemon", self.getName(true, false)
                ));

                // cause é null para não aparecer Slow Start na mensagem
                self.getStat(StatName.Atk).change(-2, null, true, true, false);
                self.getStat(StatName.Spe).change(-2, null, true, true, false);

                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.TurnEnd) {
                if (self.justSwitchedIn()) {
                    return null;
                }

                thisAbility.setCounter(thisAbility.getCounter() + 1);

                if (thisAbility.getCounter() >= 5) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                    thisAbility.getMessages().print("end", Map.of(
                        "Pokemon", self.getName(true, false)
                    ));

                    // cause é null para não aparecer Slow Start na mensagem
                    self.getStat(StatName.Atk).change(2, null, true, true, false);
                    self.getStat(StatName.Spe).change(2, null, true, true, false);

                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                    thisAbility.setActive(false);
                }
            }

            if (condition == AbilityActivation.Removed) {
                thisAbility.getMessages().print("remove", Map.of(
                    "Pokemon", self.getName(true, false)
                ));

                // cause é null para não aparecer Slow Start na mensagem
                self.getStat(StatName.Atk).change(2, null, true, true, false);
                self.getStat(StatName.Spe).change(2, null, true, true, false);
            }

            return null;
        };

    public static final AbilityEffectFunction sturdy =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.DeductHP) {
                if (damage.amount >= self.getHP() &&
                    self.getCurrentHP() == self.getHP()) {
                    thisAbility.setPersistentActive(true);
                    return true;
                }
                return false;
            }

            if (condition == AbilityActivation.PostHitMessage) {
                if (thisAbility.persistentIsActive() &&
                    !Battle.faintCheck(self, false)) {
                    System.out.println(self.getName(true, true) + " endured the hit due to Sturdy!");
                    thisAbility.setPersistentActive(false);
                }
            }

            return null;
        };

    public static final AbilityEffectFunction synchronize =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (opponent.getNonVolatileStatus().compare(Data.get().getStatusCondition("none")) && (
                    statusCondition.compare(Data.get().getStatusCondition("burn")) ||
                    statusCondition.compare(Data.get().getStatusCondition("paralysis")) ||
                    statusCondition.compare(Data.get().getStatusCondition("poison")) ||
                    statusCondition.compare(Data.get().getStatusCondition("bad_poison")) ||
                    statusCondition.compare(Data.get().getStatusCondition("frostbite"))
                )) {
                StatusCondition synchronizeStatus = new StatusCondition(
                    statusCondition, null, statusCondition.getCounter(), self, null
                );

                synchronizeStatus.apply(opponent, thisAbility, null, true, false);
            }
            return null;
        };

    public static final AbilityEffectFunction tera_shell =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.CallUserSuperEffective) {
                if (self.getCurrentHP() == self.getHP()) {
                    return new TypeTemplate[0];
                }
                return type.getSuperEffective(null, true);
            }

            if (condition == AbilityActivation.CallUserNotVeryEffective) {
                if (self.getCurrentHP() == self.getHP()) {
                    List<TypeTemplate> newResistances = new ArrayList<>();

                    for (TypeTemplate type2 : Data.get().getTypeList().values()) {
                        if (!type2.compare(Data.get().getType("typeless"))) {
                            newResistances.add(type2);
                        }
                    }
                    return newResistances.toArray(new TypeTemplate[0]);
                }
                return type.getNotVeryEffective(null, true);
            }

            if (condition == AbilityActivation.BeforeHit) {
                if (move.getCategory() != Category.Status &&
                    self.getCurrentHP() == self.getHP()) {
                    System.out.println(self.getName(true, true) + "'s Tera Shell is gleaming! It's distorting type matchups!");
                }
            }

            return null;
        };

    public static final AbilityEffectFunction tera_shift =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            System.out.println(self.getName(true, true) + " transformed into its Terastal Form!");
            self.changeForm("Terastal");
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

            return null;
        };

    public static final AbilityEffectFunction ultimate_weapon =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean sameTypeAsUser = false;
            Type ultimateType = null;
            for (Type userType : self.getTypes()) {
                if (move.getType(false, false).compare(userType)) {
                    sameTypeAsUser = true;
                    ultimateType = move.getType(false, false);
                    break;
                }
            }

            if (condition == AbilityActivation.ChangeOpponentNotVeryEffective) {
                if (sameTypeAsUser) {
                    for (TypeTemplate resistance : type.getNotVeryEffective(null, true)) {
                        if (ultimateType.compare(resistance)) {
                            List<TypeTemplate> newResistances = new ArrayList<>(Arrays.asList(type.getNotVeryEffective(null, true)));
                            newResistances.remove(resistance);
                            return newResistances.toArray(new TypeTemplate[0]);
                        }
                    }
                }

                return type.getNotVeryEffective(null, true);
            }

            if (condition == AbilityActivation.ChangeOpponentIneffective) {
                if (sameTypeAsUser) {
                    for (TypeTemplate immunity : type.getIneffective(null, true)) {
                        if (ultimateType.compare(immunity)) {
                            List<TypeTemplate> newImmunities = new ArrayList<>(Arrays.asList(type.getIneffective(null, true)));
                            newImmunities.remove(immunity);
                            return newImmunities.toArray(new TypeTemplate[0]);
                        }
                    }
                }

                return type.getIneffective(null, true);
            }

            return null;
        };

    public static final AbilityEffectFunction unburden =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (!thisAbility.persistentIsActive()) {
                if (condition == AbilityActivation.ItemConsumed) {
                    thisAbility.setPersistentActive(true);
                }
                if (condition == AbilityActivation.SpeedCalc) {
                    return 1.0;
                }
            } else {
                if (condition == AbilityActivation.ItemGained) {
                    thisAbility.setPersistentActive(false);
                }
                if (condition == AbilityActivation.SpeedCalc) {
                    return 2.0;
                }
            }
            return null;
        };

    public static final AbilityEffectFunction unseen_fist =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.OpponentTryProtect) {
                if (move.makesContact(false)) {
                    return false;
                }
                return true;
            }

            if (condition == AbilityActivation.UserDamageCalc) {
                boolean opponentProtected = false;
                boolean affected = false;

                if (opponent.getVolatileStatus(Data.get().getStatusCondition("protection")) != null) {
                    opponentProtected = true;
                    affected = move.targetsOpponent();
                }

                if (!opponentProtected || !affected) {
                    for (FieldCondition fieldCondition : Battle.teamFields.get(opponent.getTeam())) {
                        if (fieldCondition.compare(Data.get().getFieldCondition("quick_guard"))) {
                            opponentProtected = true;
                            affected = move.targetsOpponent() && move.getPriority() > 0;
                        } else if (fieldCondition.compare(Data.get().getFieldCondition("wide_guard"))) {
                            opponentProtected = true;
                            affected = move.getMoveTarget() == MoveTarget.AllOpponents || move.getMoveTarget() == MoveTarget.AllAdjacent;
                        }

                        if (opponentProtected && affected) {
                            break;
                        }
                    }
                }

                if (opponentProtected && affected) {
                    return 0.5;
                }
                return 1.0;
            }

            return null;
        };

    public static final AbilityEffectFunction zero_to_hero =
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.SwitchOut &&
                self.compareWithForm(Data.get().getPokemon("palafin"))) {
                self.changeForm("Hero");
            }

            if (condition == AbilityActivation.Entry &&
                self.compareWithForm(Data.get().getPokemon("palafin_hero"))) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + " underwent a heroic transformation!");
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                thisAbility.setActive(false);
            }

            return null;
        };
}
