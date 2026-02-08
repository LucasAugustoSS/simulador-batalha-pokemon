package com.github.lucasaugustoss.data.objects.oldObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.lists.AllTypes;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;

public class AbilityList {
    public static final Ability none = new Ability(
        "None",
        null,
        new AbilityActivation[0],
        true, true, true
    );



    public static final Ability absolute_zero = new Ability( // fanmade
        "Absolute Zero",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.ice)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability adaptability = new Ability(
        "Adaptability",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return 2.0;
        },
        new AbilityActivation[] {
            AbilityActivation.STABCalc
        },
        false, false, false
    );

    public static final Ability aerilate = new Ability(
        "Aerilate",
        (_, _, _, move, type, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.CallMoveType) {
                if (move.getType(false, true).compare(TypeList.normal)) {
                    return TypeList.flying;
                }
                return type;
            }
            if (condition == AbilityActivation.UserPowerCalc) {
                if (move.getType(false, true).compare(TypeList.normal)) {
                    return 1.2;
                }
                return 1.0;
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.CallMoveType,
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability air_lock = new Ability(
        "Air Lock",
        (_, self, opponent, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println("The effects of the weather disappeared due to " + self.getName(true, false) + "'s Air Lock!");

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
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.CallWeather) {
                if (!Battle.faintCheck(self, false) &&
                    (self == Battle.yourActivePokemon || self == Battle.opponentActivePokemon)) { // garante que não vai afetar as abilities em SwitchOut
                    return FieldConditionList.clear.cause(-1, null, null);
                }
                return Battle.getTrueWeather();
            }

            if ((condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) &&
                (
                    !Battle.opponentActivePokemon.getAbility().compare(AbilityList.air_lock) ||
                    !Battle.opponentActivePokemon.getAbility().shouldActivate(null)
                )) {
                if (condition == AbilityActivation.SwitchOut) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else if (condition == AbilityActivation.FaintUser) {
                    System.out.println();
                }
                if (!self.getAbility().compare(AbilityList.delta_stream)) { // garante que não vai aparecer quando o Rayquaza Mega Evoluir
                    System.out.println("The effects of the weather returned!");
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
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.CallWeather,
            AbilityActivation.SwitchOut,
            AbilityActivation.Removed,
            AbilityActivation.FaintUser
        },
        false, false, false
    );

    public static final Ability antithesis = new Ability( // fanmade
        "Antithesis",
        (_, self, _, _, type, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Antithesis has distorted its types!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.CallUserSuperEffective || condition == AbilityActivation.CallOpponentSuperEffective) {
                List<Type> newWeaknesses = new ArrayList<>();

                for (Type type2 : AllTypes.allTypes) {
                    for (Type type2Weakness : type2.getSuperEffective(null, true)) {
                        if (type2Weakness.compare(type)) {
                            newWeaknesses.add(type2);
                            break;
                        }
                    }
                }

                return newWeaknesses.toArray(new Type[0]);
            }

            if (condition == AbilityActivation.CallUserNotVeryEffective || condition == AbilityActivation.CallOpponentNotVeryEffective) {
                List<Type> newResistances = new ArrayList<>();

                for (Type type2 : AllTypes.allTypes) {
                    for (Type type2Resistance : type2.getNotVeryEffective(null, true)) {
                        if (type2Resistance.compare(type)) {
                            newResistances.add(type2);
                            break;
                        }
                    }
                }

                return newResistances.toArray(new Type[0]);
            }

            if (condition == AbilityActivation.CallUserIneffective || condition == AbilityActivation.CallOpponentIneffective) {
                List<Type> newImmunities = new ArrayList<>();

                for (Type type2 : AllTypes.allTypes) {
                    for (Type type2Immunity : type2.getIneffective(null, true)) {
                        if (type2Immunity.compare(type)) {
                            newImmunities.add(type2);
                            break;
                        }
                    }
                }

                return newImmunities.toArray(new Type[0]);
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.CallUserSuperEffective,
            AbilityActivation.CallUserNotVeryEffective,
            AbilityActivation.CallUserIneffective,
            AbilityActivation.CallOpponentSuperEffective,
            AbilityActivation.CallOpponentNotVeryEffective,
            AbilityActivation.CallOpponentIneffective
        },
        false, false, false,
        true
    );

    public static final Ability as_one_ice = new Ability(
        "As One",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " has two abilities!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Unnerve made " + (self.getTeam() == 0 ? "the opposing" : "your") + " team too nervous to eat Berries!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.OpponentTryUseBerry) {
                return false;
            }

            if (condition == AbilityActivation.FaintTarget) {
                if (self.getStat(StatName.Atk).getTrueStages() < 6) {
                    self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.OpponentTryUseBerry,
            AbilityActivation.FaintTarget
        },
        true, true, false
    );

    public static final Ability as_one_shadow = new Ability(
        "As One",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " has two abilities!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Unnerve made " + (self.getTeam() == 0 ? "the opposing" : "your") + " team too nervous to eat Berries!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.OpponentTryUseBerry) {
                return false;
            }

            if (condition == AbilityActivation.FaintTarget) {
                if (self.getStat(StatName.SpA).getTrueStages() < 6) {
                    self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.OpponentTryUseBerry,
            AbilityActivation.FaintTarget
        },
        true, true, false
    );

    public static final Ability aura_break = new Ability(
        "Aura Break",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                System.out.println();
            }
            System.out.println(self.getName(true, true) + "'s Aura Break reversed all other Pokémon's auras!");
            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        true, true, true
    );

    public static final Ability bad_dreams = new Ability(
        "Bad Dreams",
        (thisAbility, self, opponent, _, _, _, _, _, _, _) -> {
            if (opponent.getNonVolatileStatus().compare(StatusConditionList.sleep)) {
                int badDreamsDamage = Integer.max(opponent.getHP()/8, 1);
                String message = opponent.getName(true, true) + " was tormented by " + self.getName(true, false) + "'s Bad Dreams!";
                Damage.indirectDamage(opponent, self, badDreamsDamage, DamageSource.Ability, thisAbility, message, true);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability battery = new Ability(
        "Battery",
        (_, _, _, _, _, _, _, _, _, _) -> {
            // sem uso em singles
            return null;
        },
        new AbilityActivation[0], // AbilityActivation.AllyDamageCalc
        false, false, false
    );

    public static final Ability battle_armor = new Ability(
        "Battle Armor",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryCritUser
        },
        false, false, false,
        true
    );

    public static final Ability beads_of_ruin = new Ability(
        "Beads of Ruin",
        (thisAbility, self, _, _, _, _, _, stat, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Beads of Ruin weakened the Special Defense of all surrounding Pokémon!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AnyStatCalc) {
                if (stat.getPokemon().getAbility().compare(thisAbility)) {
                    return 1.0;
                }

                boolean magicRoom = false;
                for (FieldCondition fieldCondition : Battle.generalField) {
                    if (fieldCondition.compare(FieldConditionList.magic_room)) {
                        magicRoom = true;
                    }
                }

                if (!magicRoom && stat.compare(Stat.spd) ||
                    magicRoom && stat.compare(Stat.def)) {
                    return 0.75;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.AnyStatCalc
        },
        false, false, false,
        true
    );

    public static final Ability berserk = new Ability(
        "Berserk",
        (thisAbility, self, _, _, _, damage, _, _, _, _) -> {
            if (self.getCurrentHP() < self.getHP()/2.0 &&
                self.getCurrentHP() + damage.amount >= self.getHP()/2.0) {
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.HitUser
        },
        false, false, false
    );

    public static final Ability blaze = new Ability(
        "Blaze",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() <= self.getHP()/3 && move.getType(false, false).compare(TypeList.fire)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability bulletproof = new Ability(
        "Bulletproof",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (Arrays.asList(move.getMoveTypes()).contains(MoveType.BallBomb)) {
                System.out.println(self.getName(true, true) + " was protected by Bulletproof!");
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability chilling_neigh = new Ability(
        "Chilling Neigh",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            if (self.getStat(StatName.Atk).getTrueStages() < 6) {
                self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.FaintTarget
        },
        false, false, false
    );

    public static final Ability chlorophyll = new Ability(
        "Chlorophyll",
        (_, _, _, _, _, _, _, _, _, _) -> {
            if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                return 2.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.SpeedCalc
        },
        false, false, false
    );

    public static final Ability clear_body = new Ability(
        "Clear Body",
        (_, self, _, _, _, _, _, _, statChangeStages, _) -> {
            if (statChangeStages < 0) {
                System.out.println(self.getName(true, true) + " is protected by Clear Body!");
                return true;
            }
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatChangeOnUser
        },
        false, false, false,
        true
    );

    public static final Ability competitive = new Ability(
        "Competitive",
        (thisAbility, self, _, _, _, _, _, _, statChangeStages, _) -> {
            if (statChangeStages < 0) {
                self.getStat(StatName.SpA).change(2, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.StatChangeOnUser
        },
        false, false, false
    );

    public static final Ability compound_eyes = new Ability(
        "Compound Eyes",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getAccuracy() != -1) {
                return 5325.0/4096.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AccuracyCalc
        },
        false, false, false
    );

    public static final Ability contrary = new Ability(
        "Contrary",
        (_, _, _, _, _, _, _, _, statChangeStages, _) -> {
            return statChangeStages * -1;
        },
        new AbilityActivation[] {
            AbilityActivation.ModifyStatChangeStages
        },
        false, false, false,
        true
    );

    public static final Ability cute_charm = new Ability(
        "Cute Charm",
        (thisAbility, self, opponent, move, _, _, _, _, _, _) -> {
            if (move.makesContact(false)) {
                if (!self.getGender().equals("Unknown") &&
                    !opponent.getGender().equals("Unknown") &&
                    !opponent.getGender().equals(self.getGender()) &&
                    opponent.getVolatileStatus(StatusConditionList.infatuation) == null &&
                    Math.random() < 1) {
                    StatusConditionList.infatuation.apply(opponent, thisAbility, self, true);
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.HitUser
        },
        false, false, false
    );

    public static final Ability damp = new Ability(
        "Damp",
        (_, self, opponent, move, _, _, _, _, _, _) -> {
            // impede Self-Destruct, Explosion, Mind Blown, Misty Explosion e Aftermath
            if (move.compare(MoveList.self_destruct) || move.compare(MoveList.explosion)) {
                System.out.println(opponent.getName(true, true) + " cannot use " + move.getName() + " due to " + (self != opponent ? (self.getName(true, false) + "'s") : "its") + " Damp!");
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser,
            AbilityActivation.TryUseMove
        },
        false, false, false,
        true
    );

    public static final Ability dark_aura = new Ability(
        "Dark Aura",
        (_, self, opponent, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " is radiating a Dark Aura!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AnyPowerCalc) {
                if (move.getType(false, false).compare(TypeList.dark)) {
                    if (opponent.getAbility().compare(AbilityList.aura_break) &&
                        opponent.getAbility().shouldActivate(null)) {
                        return 4096.0/5448.0;
                    } else {
                        return 5448.0/4096.0;
                    }
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.AnyPowerCalc
        },
        false, false, false
    );

    public static final Ability darkest_day = new Ability( // fanmade
        "Darkest Day",
        (thisAbility, self, _, move, _, _, _, _, _, condition) -> {
            if (self.compare(Data.get().getPokemon("eternatus"), true)) {
                if (condition == AbilityActivation.AbilityUpdate) {
                    System.out.println("\nThe Darkest Day has arrived!");
                }

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

                // mudanças de Dynamax Cannon e Eternabeam são feitos nos respectivos movimentos

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

                    if (thisAbility.getCounter() >= 3) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        System.out.println(self.getName(true, true) + " has returned to its usual form!");
                        self.changeForm("");
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }

                // ativa ao trocar de forma
                if (condition == AbilityActivation.Removed) {
                    if (self.getVolatileStatus(StatusConditionList.readying_switch) != null) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }
                    System.out.println("The Darkest Day has come to an end!");
                    if (self.getVolatileStatus(StatusConditionList.readying_switch) != null) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else {
                if (condition == AbilityActivation.UserPowerCalc) {
                    return 1.0;
                }
                if (condition == AbilityActivation.OpponentDamageCalc) {
                    return 1.0;
                }
                if (condition == AbilityActivation.CallMove) {
                    return move;
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.AbilityUpdate,
            AbilityActivation.UserPowerCalc,
            AbilityActivation.OpponentDamageCalc,
            AbilityActivation.CallMove,
            AbilityActivation.TurnEnd,
            AbilityActivation.Removed
        },
        true, true, true
    );

    public static final Ability dauntless_shield = new Ability(
        "Dauntless Shield",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                System.out.println();
            }

            self.getStat(StatName.Def).change(1, thisAbility, true, true, false);

            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            thisAbility.setActive(false);

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability defiant = new Ability(
        "Defiant",
        (thisAbility, self, _, _, _, _, _, _, statChangeStages, _) -> {
            if (statChangeStages < 0) {
                self.getStat(StatName.Atk).change(2, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.StatChangeOnUser
        },
        false, false, false
    );

    public static final Ability delta_stream = new Ability(
        "Delta Stream",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                boolean canActivate = FieldConditionList.delta_stream.apply(thisAbility, true, false);

                if (canActivate) {
                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    FieldConditionList.delta_stream.apply(thisAbility, false, true);

                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            if (condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) {
                if (Battle.getTrueWeather().compare(FieldConditionList.delta_stream) &&
                    (
                        !Battle.opponentActivePokemon.getAbility().compare(AbilityList.delta_stream) ||
                        !Battle.opponentActivePokemon.getAbility().shouldActivate(null)
                    )) {
                    if (condition == AbilityActivation.SwitchOut) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else if (condition == AbilityActivation.FaintUser) {
                        System.out.println();
                    }

                    Battle.getTrueWeather().end();

                    if (condition == AbilityActivation.SwitchOut) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.SwitchOut,
            AbilityActivation.Removed,
            AbilityActivation.FaintUser
        },
        false, false, false
    );

    public static final Ability desolate_land = new Ability(
        "Desolate Land",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                boolean canActivate = FieldConditionList.desolate_land.apply(thisAbility, true, false);

                if (canActivate) {
                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    FieldConditionList.desolate_land.apply(thisAbility, false, true);

                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            if (condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) {
                if (Battle.getTrueWeather().compare(FieldConditionList.desolate_land) &&
                    (
                        !Battle.opponentActivePokemon.getAbility().compare(AbilityList.desolate_land) ||
                        !Battle.opponentActivePokemon.getAbility().shouldActivate(null)
                    )) {
                    if (condition == AbilityActivation.SwitchOut) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else if (condition == AbilityActivation.FaintUser) {
                        System.out.println();
                    }

                    Battle.getTrueWeather().end();

                    if (condition == AbilityActivation.SwitchOut) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.SwitchOut,
            AbilityActivation.Removed,
            AbilityActivation.FaintUser
        },
        false, false, false
    );

    public static final Ability download = new Ability(
        "Download",
        (thisAbility, self, opponent, _, _, _, _, _, _, condition) -> {
            int opponentDef = opponent.getStat(StatName.Def).getValue();
            int opponentDefStages = opponent.getStat(StatName.Def).getStages(null, null);
            double valDef = 1 + Math.abs(opponentDefStages)*0.5;
            opponentDef = (int) (opponentDefStages >= 0 ? opponentDef*valDef : opponentDef/valDef);

            int opponentSpD = opponent.getStat(StatName.SpD).getValue();
            int opponentSpDStages = opponent.getStat(StatName.SpD).getStages(null, null);
            double valSpD = 1 + Math.abs(opponentSpDStages)*0.5;
            opponentSpD = (int) (opponentSpDStages >= 0 ? opponentSpD*valSpD : opponentSpD/valSpD);

            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                System.out.println();
            }

            if (opponentDef < opponentSpD) {
                self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
            } else {
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
            }

            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability dragons_maw = new Ability(
        "Dragon's Maw",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.dragon)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability drizzle = new Ability(
        "Drizzle",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (self.compare(Data.get().getPokemon("kyogre"), true) && self.getItem().compare(ItemList.blue_orb)) {
                return null;
            }

            boolean canActivate = FieldConditionList.rain.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.rain.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability drought = new Ability(
        "Drought",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (self.compare(Data.get().getPokemon("groudon"), true) && self.getItem().compare(ItemList.red_orb)) {
                return null;
            }

            boolean canActivate = FieldConditionList.sun.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.sun.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability electric_surge = new Ability(
        "Electric Surge",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            boolean canActivate = FieldConditionList.electric_terrain.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.electric_terrain.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability fairy_aura = new Ability(
        "Fairy Aura",
        (_, self, opponent, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " is radiating a Fairy Aura!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AnyPowerCalc) {
                if (move.getType(false, false).compare(TypeList.fairy)) {
                    if (opponent.getAbility().compare(AbilityList.aura_break) &&
                        opponent.getAbility().shouldActivate(null)) {
                        return 4096.0/5448.0;
                    } else {
                        return 5448.0/4096.0;
                    }
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.AnyPowerCalc
        },
        false, false, false
    );

    public static final Ability flame_body = new Ability(
        "Flame Body",
        (thisAbility, _, opponent, move, _, _, _, _, _, _) -> {
            if (move.makesContact(false)) {
                if (opponent.getNonVolatileStatus().compare(StatusConditionList.none) &&
                    Math.random() < 0.3) {
                    StatusConditionList.burn.apply(opponent, thisAbility, true);
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.HitUser
        },
        false, false, false
    );

    public static final Ability flash_fire = new Ability(
        "Flash Fire",
        (thisAbility, self, _, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.TryHitUser) {
                if (move.getType(false, false).compare(TypeList.fire) && move.targetsOpponent()) {
                    if (!thisAbility.persistentIsActive()) {
                        System.out.println(self.getName(true, true) + "'s Flash Fire raised the power of its Fire-type moves!");
                        thisAbility.setPersistentActive(true);
                    } else {
                        System.out.println(self.getName(true, true) + "'s Flash Fire absorbed the move!");
                    }
                    return false;
                }
                return true;
            }

            if (condition == AbilityActivation.AttackCalc || condition == AbilityActivation.SpecialAttackCalc) {
                if (thisAbility.persistentIsActive() &&
                    move.getType(false, false).compare(TypeList.fire)) {
                    return 1.5;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser,
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false,
        true
    );

    public static final Ability forecast = new Ability(
        "Forecast",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (self.compare(Data.get().getPokemon("castform"), true)) {
                if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
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
                } else if (Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) {
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
                } else if (Battle.getWeather().compare(FieldConditionList.snow)) {
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
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.WeatherChange,
            AbilityActivation.Entry
        },
        true, true, true
    );

    public static final Ability frisk = new Ability(
        "Frisk",
        (_, self, opponent, _, _, _, _, _, _, condition) -> {
            if (!opponent.getItem().compare(ItemList.none)) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " frisked " + opponent.getName(true, false) + " and found its " + opponent.getItem().getName() + "!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability full_metal_body = new Ability(
        "Full Metal Body",
        (_, self, _, _, _, _, _, _, statChangeStages, _) -> {
            if (statChangeStages < 0) {
                System.out.println(self.getName(true, true) + " is protected by Full Metal Body!");
                return true;
            }
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatChangeOnUser
        },
        false, false, false
    );

    public static final Ability grassy_surge = new Ability(
        "Grassy Surge",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            boolean canActivate = FieldConditionList.grassy_terrain.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.grassy_terrain.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability grim_neigh = new Ability(
        "Grim Neigh",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            if (self.getStat(StatName.SpA).getTrueStages() < 6) {
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.FaintTarget
        },
        false, false, false
    );

    public static final Ability guard_dog = new Ability(
        "Guard Dog",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.TryForceSwitch) {
                System.out.println(self.getName(true, true) + "'s Guard Dog let it bravely stand its ground!");
                return false;
            }

            if (condition == AbilityActivation.TryIntimidate) {
                return false;
            }

            if (condition == AbilityActivation.Intimidated) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TryForceSwitch,
            AbilityActivation.TryIntimidate,
            AbilityActivation.Intimidated
        },
        false, false, false,
        true
    );

    public static final Ability guts = new Ability(
        "Guts",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (!self.getNonVolatileStatus().compare(StatusConditionList.none)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc
        },
        false, false, false
    );

    public static final Ability hadron_engine = new Ability(
        "Hadron Engine",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            boolean electricTerrainActive = Battle.getTerrain().compare(FieldConditionList.electric_terrain);

            if ((condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) &&
                !electricTerrainActive) {
                boolean canActivate = FieldConditionList.electric_terrain.apply(thisAbility, true, false);

                if (canActivate) {
                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    System.out.println(self.getName(true, true) + "'s Hadron Engine turned the ground into Electric Terrain, becoming energized!");
                    FieldConditionList.electric_terrain.apply(thisAbility, false, false);

                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else if ((condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate ||
                        condition == AbilityActivation.TerrainChange && Battle.getTerrain().getCause() != thisAbility) &&
                       electricTerrainActive) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                System.out.println(self.getName(true, true) + " used the Electric Terrain to energize its Hadron Engine!");

                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.SpecialAttackCalc) {
                if (electricTerrainActive) {
                    return 5461.0/4096.0;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.TerrainChange,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability hustle = new Ability(
        "Hustle",
        (_, _, _, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.AttackCalc) {
                return 1.5;
            }

            if (condition == AbilityActivation.AccuracyCalc) {
                if (move.getCategory() == Category.Physical) {
                    return 3277.0/4096.0;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.AccuracyCalc
        },
        false, false, false
    );

    public static final Ability hydration = new Ability(
        "Hydration",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if ((Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) &&
                !self.getNonVolatileStatus().compare(StatusConditionList.none)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + "'s Hydration cured its " + self.getNonVolatileStatus().getName() + "!");
                self.endNonVolatileStatus(false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability ice_body = new Ability(
        "Ice Body",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (Battle.getWeather().compare(FieldConditionList.snow) &&
                self.getCurrentHP() < self.getHP()) {
                int healedDamage = Integer.max(self.getHP()/16, 1);

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + " restored health thanks to Ice Body!");
                Damage.heal(self, null, healedDamage, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability illusion = new Ability(
        "Illusion",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
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
        },
        new AbilityActivation[] {
            AbilityActivation.CallUserData,
            AbilityActivation.HitUser,
            AbilityActivation.Removed
        },
        true, false, false
    );

    public static final Ability immovability = new Ability( // fanmade
        "Immovability",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Immovability has stopped time!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.TryFieldCountDown) {
                return false;
            }

            if (condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) {
                if (condition == AbilityActivation.SwitchOut) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else if (condition == AbilityActivation.FaintUser) {
                    System.out.println();
                }
                System.out.println("Time has begun to flow again!");
                if (condition == AbilityActivation.SwitchOut) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.TryFieldCountDown,
            AbilityActivation.SwitchOut,
            AbilityActivation.Removed,
            AbilityActivation.FaintUser
        },
        false, false, false
    );

    public static final Ability inner_focus = new Ability(
        "Inner Focus",
        (_, self, _, _, _, _, statusCondition, _, _, condition) -> {
            if (condition == AbilityActivation.TryStatusConditionOnUser &&
                statusCondition.compare(StatusConditionList.flinch)) {
                return true;
            }

            if (condition == AbilityActivation.AbilityUpdate && self.getVolatileStatus(StatusConditionList.flinch) != null ||
                condition == AbilityActivation.StatusConditionOnUser && statusCondition.compare(StatusConditionList.flinch)) {
                self.endVolatileStatus(StatusConditionList.flinch, false);
            }

            if (condition == AbilityActivation.TryIntimidate) {
                return false;
            }

            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatusConditionOnUser,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.StatusConditionOnUser,
            AbilityActivation.TryIntimidate
        },
        false, false, false,
        true
    );

    public static final Ability insomnia = new Ability(
        "Insomnia",
        (_, self, _, _, _, _, statusCondition, _, _, condition) -> {
            if (condition == AbilityActivation.TryStatusConditionOnUser &&
                statusCondition.compare(StatusConditionList.sleep)) {
                return true;
            }

            if (condition == AbilityActivation.AbilityUpdate && self.getNonVolatileStatus().compare(StatusConditionList.sleep) ||
                condition == AbilityActivation.StatusConditionOnUser && statusCondition.compare(StatusConditionList.sleep)) {
                System.out.println(self.getName(true, true) + "'s Insomnia woke it up!");
                self.endNonVolatileStatus(false);
            }

            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatusConditionOnUser,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.StatusConditionOnUser
        },
        false, false, false,
        true
    );

    public static final Ability intimidate = new Ability(
        "Intimidate",
        (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            // Oblivious, Own Tempo, Inner Focus, Scrappy e Guard Dog bloqueiam Intimidate
            if (opponent.getAbility().shouldActivate(AbilityActivation.TryIntimidate) &&
                !(boolean) opponent.getAbility().activate(opponent, self, move, type, damage, statusCondition, stat, statChangeStages, AbilityActivation.TryIntimidate)) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(opponent.getName(true, true) + " was not intimidated thanks to its " + opponent.getAbility().getName() + "!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            } else {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                opponent.getStat(StatName.Atk).change(-1, thisAbility, false, true, false);
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (Arrays.asList(opponent.getAbility().getConditions()).contains(AbilityActivation.Intimidated)) {
                opponent.getAbility().activate(opponent, self, move, type, damage, statusCondition, stat, statChangeStages, AbilityActivation.Intimidated);
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability intrepid_sword = new Ability(
        "Intrepid Sword",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                System.out.println();
            }

            self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);

            if (condition != AbilityActivation.AbilityUpdate) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            thisAbility.setActive(false);

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability iron_fist = new Ability(
        "Iron Fist",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (Arrays.asList(move.getMoveTypes()).contains(MoveType.Punch)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability justified = new Ability(
        "Justified",
        (thisAbility, self, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.dark) && move.targetsOpponent()) {
                System.out.println(self.getName(true, true) + "'s Justified suppressed the move!");
                self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability leaf_guard = new Ability(
        "Leaf Guard",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if ((Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) &&
                !self.getNonVolatileStatus().compare(StatusConditionList.none)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + "'s Leaf Guard cured its " + self.getNonVolatileStatus().getName() + "!");
                self.endNonVolatileStatus(false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability levitate = new Ability(
        "Levitate",
        null,
        new AbilityActivation[0],
        false, false, false,
        true
    );

    public static final Ability libero = new Ability(
        "Libero",
        (thisAbility, self, _, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.UseMove &&
                !thisAbility.persistentIsActive()) {
                self.setTypes(new Type[] {move.getType(false, false)});
                System.out.println(self.getName(true, true) + "'s Libero transformed it into the " + move.getType(false, false).getName() + " type!");
                thisAbility.setPersistentActive(true);
            }
            if (condition == AbilityActivation.SwitchOut) {
                thisAbility.setPersistentActive(false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.UseMove,
            AbilityActivation.SwitchOut
        },
        false, false, false
    );

    public static final Ability light_metal = new Ability(
        "Light Metal",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return 0.5;
        },
        new AbilityActivation[] {
            AbilityActivation.WeightCalc
        },
        false, false, false,
        true
    );

    public static final Ability lightning_rod = new Ability(
        "Lightning Rod",
        (thisAbility, self, _, move, _, _, _, _, _, _) -> {
            // TODO redireção
            if (move.getType(false, false).compare(TypeList.electric) && move.targetsOpponent()) {
                System.out.println(self.getName(true, true) + "'s Lightning Rod absorbed the move!");
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability limber = new Ability(
        "Limber",
        (_, self, _, _, _, _, statusCondition, _, _, condition) -> {
            if (condition == AbilityActivation.TryStatusConditionOnUser &&
                statusCondition.compare(StatusConditionList.paralysis)) {
                return true;
            }

            if (condition == AbilityActivation.AbilityUpdate && self.getNonVolatileStatus().compare(StatusConditionList.paralysis) ||
                condition == AbilityActivation.StatusConditionOnUser && statusCondition.compare(StatusConditionList.paralysis)) {
                System.out.println(self.getName(true, true) + "'s Limber cured its paralysis!");
                self.endNonVolatileStatus(false);
            }

            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatusConditionOnUser,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.StatusConditionOnUser
        },
        false, false, false,
        true
    );

    public static final Ability liquid_voice = new Ability(
        "Liquid Voice",
        (_, _, _, move, type, _, _, _, _, _) -> {
            if (Arrays.asList(move.getMoveTypes()).contains(MoveType.Sound)) {
                return TypeList.water;
            }
            return type;
        },
        new AbilityActivation[] {
            AbilityActivation.CallMoveType
        },
        false, false, false
    );

    public static final Ability long_reach = new Ability(
        "Long Reach",
        (_, _, _, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.CallContact) {
                return false;
            }

            if (condition == AbilityActivation.SpeedCalc) {
                if (move != null &&
                    move.makesContact(true)) {
                    return Double.MAX_VALUE;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.CallContact,
            AbilityActivation.SpeedCalc
        },
        false, false, false
    );

    public static final Ability magic_bounce = new Ability(
        "Magic Bounce",
        (_, self, opponent, move, _, _, _, _, _, _) -> {
            if (move.getCategory() == Category.Status && move.targetsOpponent() &&
                !move.hasInherentProperty(InherentProperty.NotReflectable) &&
                !move.getTemporaryProperties().contains(TemporaryProperty.Reflected)) {
                System.out.println(self.getName(true, true) + "'s Magic Bounce bounced the " + move.getName() + " back!");

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
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability magic_guard = new Ability(
        "Magic Guard",
        (_, _, _, _, _, damage, _, _, _, _) -> {
            if (damage.source != DamageSource.Move) {
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryDamage
        },
        false, false, false
    );

    public static final Ability magician = new Ability(
        "Magician",
        (_, self, opponent, move, _, _, _, _, _, _) -> {
            boolean opponentItemRemovable = !opponent.getItem().heldByValidUser(true) || !opponent.getItem().isTetheredToValidUser();

            if (!opponent.getItem().compare(ItemList.none) &&
                opponent.getItem().getType() != ItemType.ZCrystal &&
                opponentItemRemovable &&
                self.getItem().compare(ItemList.none) &&
                move.targetsOpponent() &&
                // Fling, Natural Gift, Future Sight e Doom Desire não ativam
                !move.compare(MoveList.fling) &&
                !move.compare(MoveList.future_sight) &&
                !move.compare(MoveList.doom_desire)) {
                System.out.println(self.getName(true, true) + "'s Magician stole " + opponent.getName(true, false) + "'s " + opponent.getItem().getName() + "!");
                self.giveItem(opponent.takeItem());
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.HitTarget
        },
        false, false, false
    );

    public static final Ability magnet_pull = new Ability(
        "Magnet Pull",
        (_, self, opponent, _, _, _, _, _, _, _) -> {
            if (opponent.hasType(TypeList.steel)) {
                System.out.println("\n!- " + self.getName(true, true) + "'s Magnet Pull prevents Steel-type Pokémon from switching out -!\n");
                return true;
            }
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentTrySwitch
        },
        false, false, false
    );

    public static final Ability marvel_scale = new Ability(
        "Marvel Scale",
        (_, self, _, _, _, _, _, stat, _, _) -> {
            if (stat.compare(Stat.def) &&
                !self.getNonVolatileStatus().compare(StatusConditionList.none)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.DefenseCalc
        },
        false, false, false,
        true
    );

    public static final Ability mega_launcher = new Ability(
        "Mega Launcher",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (Arrays.asList(move.getMoveTypes()).contains(MoveType.Pulse)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability metatype = new Ability( // fanmade
        "Metatype",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.CallSTAB
        },
        false, false, false
    );

    public static final Ability minus = new Ability(
        "Minus",
        (_, _, _, _, _, _, _, _, _, _) -> {
            // sem uso em singles
            return null;
        },
        new AbilityActivation[0], // AbilityActivation.AllyDamageCalc
        false, false, false
    );

    public static final Ability misty_surge = new Ability(
        "Misty Surge",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            boolean canActivate = FieldConditionList.misty_terrain.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.misty_terrain.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability mold_breaker = new Ability(
        "Mold Breaker",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " breaks the mold!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.IgnoreAbility) {
                return true;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.IgnoreAbility
        },
        false, false, false
    );

    public static final Ability moxie = new Ability(
        "Moxie",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            if (self.getStat(StatName.Atk).getTrueStages() < 6) {
                self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.FaintTarget
        },
        false, false, false
    );

    public static final Ability multiscale = new Ability(
        "Multiscale",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() == self.getHP()) {
                return 0.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentDamageCalc
        },
        false, false, false,
        true
    );

    public static final Ability multitype = new Ability(
        "Multitype",
        null,
        new AbilityActivation[0],
        true, true, true
    );

    public static final Ability natural_cure = new Ability(
        "Natural Cure",
        (_, self, _, _, _, _, _, _, _, _) -> {
            self.endNonVolatileStatus(false);
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.SwitchOut
        },
        false, false, false
    );

    public static final Ability neuroforce = new Ability(
        "Neuroforce",
        (_, _, opponent, move, _, _, _, _, _, _) -> {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(move, opponent);
            effectivenessMultiplier /= Damage.notVeryEffective(move, opponent);

            if (effectivenessMultiplier > 1) {
                return 1.25;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserDamageCalc
        },
        false, false, false
    );

    public static final Ability orichalcum_pulse = new Ability(
        "Orichalcum Pulse",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            boolean sunActive = Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land);

            if ((condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) &&
                !sunActive) {
                boolean canActivate = FieldConditionList.sun.apply(thisAbility, true, false);

                if (canActivate) {
                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    System.out.println(self.getName(true, true) + "'s Orichalcum Pulse turned the sunlight harsh, going into a frenzy!");
                    FieldConditionList.sun.apply(thisAbility, false, false);

                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else if ((condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate ||
                        condition == AbilityActivation.WeatherChange && Battle.getWeather().getCause() != thisAbility) &&
                       sunActive) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                System.out.println(self.getName(true, true) + " basked in the sunlight, sending its Orichalcum Pulse into a frenzy!");

                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AttackCalc) {
                if (sunActive) {
                    return 5461.0/4096.0;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.WeatherChange,
            AbilityActivation.AttackCalc
        },
        true, true, false
    );

    public static final Ability overcoat = new Ability(
        "Overcoat",
        (_, self, _, move, _, _, _, _, _, _) -> {
            // proteção contra Sandstorm é feita em FieldConditionList.sand
            if (Arrays.asList(move.getMoveTypes()).contains(MoveType.Powder)) {
                System.out.println(self.getName(true, true) + " was protected by Overcoat!");
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability overgrow = new Ability(
        "Overgrow",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() <= self.getHP()/3 && move.getType(false, false).compare(TypeList.grass)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability overload = new Ability( // fanmade
        "Overload",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() <= self.getHP()/3 && move.getType(false, false).compare(TypeList.electric)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability own_tempo = new Ability(
        "Own Tempo",
        (_, self, _, _, _, _, statusCondition, _, _, condition) -> {
            if (condition == AbilityActivation.TryStatusConditionOnUser &&
                statusCondition.compare(StatusConditionList.confusion)) {
                return true;
            }

            if (condition == AbilityActivation.AbilityUpdate && self.getVolatileStatus(StatusConditionList.confusion) != null ||
                condition == AbilityActivation.StatusConditionOnUser && statusCondition.compare(StatusConditionList.confusion)) {
                System.out.println(self.getName(true, true) + "'s Own Tempo snapped it out of its confusion!");
                self.endVolatileStatus(StatusConditionList.confusion, false);
            }

            if (condition == AbilityActivation.TryIntimidate) {
                return false;
            }

            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatusConditionOnUser,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.StatusConditionOnUser,
            AbilityActivation.TryIntimidate
        },
        false, false, false,
        true
    );

    public static final Ability plus = new Ability(
        "Plus",
        (_, _, _, _, _, _, _, _, _, _) -> {
            // sem uso em singles
            return null;
        },
        new AbilityActivation[0], // AbilityActivation.AllyDamageCalc
        false, false, false
    );

    public static final Ability poison_puppeteer = new Ability(
        "Poison Puppeteer",
        (thisAbility, _, opponent, _, _, _, statusCondition, _, _, _) -> {
            if (statusCondition.compare(StatusConditionList.poison) ||
                statusCondition.compare(StatusConditionList.bad_poison)) {
                StatusConditionList.confusion.apply(opponent, thisAbility, (int) Math.ceil(Math.random()*4)+1, true); // 2-5 turnos
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.StatusConditionOnTarget
        },
        false, false, false
    );

    public static final Ability power_construct = new Ability(
        "Power Construct",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (self.compare(Data.get().getPokemon("zygarde"), true) &&
                (
                    self.compareWithForm(Data.get().getPokemon("zygarde")) ||
                    self.compareWithForm(Data.get().getPokemon("zygarde_10"))
                )) {
                if (self.getCurrentHP() < self.getHP()/2.0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    System.out.println("You sense the presence of many!");
                    self.changeForm("Complete");
                    System.out.println(self.getName(true, true) + " transformed into its Complete Forme!");
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        true, true, true
    );

    public static final Ability prankster = new Ability(
        "Prankster",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getCategory() == Category.Status) {
                move.addProperty(TemporaryProperty.PranksterBoosted);
                return 1;
            }
            return 0;
        },
        new AbilityActivation[] {
            AbilityActivation.PriorityCalc
        },
        false, false, false
    );

    public static final Ability pressure = new Ability(
        "Pressure",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " is exerting its Pressure!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.PPConsumption) {
                return 2;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.PPConsumption
        },
        false, false, false
    );

    public static final Ability primordial_sea = new Ability(
        "Primordial Sea",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                boolean canActivate = FieldConditionList.primordial_sea.apply(thisAbility, true, false);

                if (canActivate) {
                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    FieldConditionList.primordial_sea.apply(thisAbility, false, true);

                    if (condition != AbilityActivation.AbilityUpdate) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            if (condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) {
                if (Battle.getTrueWeather().compare(FieldConditionList.primordial_sea) &&
                    (
                        !Battle.opponentActivePokemon.getAbility().compare(AbilityList.primordial_sea) ||
                        !Battle.opponentActivePokemon.getAbility().shouldActivate(null)
                    )) {
                    if (condition == AbilityActivation.SwitchOut) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else if (condition == AbilityActivation.FaintUser) {
                        System.out.println();
                    }

                    Battle.getTrueWeather().end();

                    if (condition == AbilityActivation.SwitchOut) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.SwitchOut,
            AbilityActivation.Removed,
            AbilityActivation.FaintUser
        },
        false, false, false
    );

    public static final Ability prism_armor = new Ability(
        "Prism Armor",
        (_, self, _, move, _, _, _, _, _, _) -> {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(move, self);
            effectivenessMultiplier /= Damage.notVeryEffective(move, self);

            if (effectivenessMultiplier > 1) {
                return 0.75;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentDamageCalc
        },
        false, false, false
    );

    public static final Ability protean = new Ability(
        "Protean",
        (thisAbility, self, _, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.UseMove &&
                !thisAbility.persistentIsActive()) {
                self.setTypes(new Type[] {move.getType(false, false)});
                System.out.println(self.getName(true, true) + "'s Protean transformed it into the " + move.getType(false, false).getName() + " type!");
                thisAbility.setPersistentActive(true);
            }
            if (condition == AbilityActivation.SwitchOut) {
                thisAbility.setPersistentActive(false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.UseMove,
            AbilityActivation.SwitchOut
        },
        false, false, false
    );

    public static final Ability psychic_surge = new Ability(
        "Psychic Surge",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            boolean canActivate = FieldConditionList.psychic_terrain.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.psychic_terrain.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability rain_dish = new Ability(
        "Rain Dish",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if ((Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) &&
                self.getCurrentHP() < self.getHP()) {
                int healedDamage = Integer.max(self.getHP()/16, 1);

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + " restored health thanks to Rain Dish!");
                Damage.heal(self, null, healedDamage, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability reckless = new Ability(
        "Reckless",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (!move.compare(MoveList.struggle) && (
                    move.hasInherentProperty(InherentProperty.Recoil) ||
                    move.hasInherentProperty(InherentProperty.CrashDamage)
                )) {
                return 1.2;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability regenerator = new Ability(
        "Regenerator",
        (_, self, _, _, _, _, _, _, _, _) -> {
            Damage.heal(self, null, self.getHP()/3, false, false);
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.SwitchOut
        },
        false, false, false
    );

    public static final Ability rks_system = new Ability(
        "RKS System",
        null,
        new AbilityActivation[0],
        true, true, true
    );

    public static final Ability rock_head = new Ability(
        "Rock Head",
        (_, _, _, _, _, damage, _, _, _, _) -> {
            if (damage.source == DamageSource.Recoil) {
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryDamage
        },
        false, false, false
    );

    public static final Ability rocky_payload = new Ability(
        "Rocky Payload",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.rock)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability run_away = new Ability(
        "Run Away",
        (_, self, _, _, _, _, statusCondition, _, _, condition) -> {
            if (condition == AbilityActivation.TryStatusConditionOnUser &&
                statusCondition.compare(StatusConditionList.trapped)) {
                return true;
            }

            if (condition == AbilityActivation.AbilityUpdate && self.getVolatileStatus(StatusConditionList.trapped) != null ||
                condition == AbilityActivation.StatusConditionOnUser && statusCondition.compare(StatusConditionList.trapped)) {
                System.out.println(self.getName(true, true) + "'s Run Away freed it to flee!");
                self.endVolatileStatus(StatusConditionList.trapped, false);
            }

            // também vai retornar false em BlockSwitch
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatusConditionOnUser,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.StatusConditionOnUser,
            AbilityActivation.BlockSwitch
        },
        false, false, false,
        true
    );

    public static final Ability sand_force = new Ability(
        "Sand Force",
        (_, _, _, move, _, _, _, _, _, _) -> {
            // proteção contra Sandstorm é feita em FieldConditionList.sand
            if (Battle.getWeather().compare(FieldConditionList.sand) &&
                (move.getType(false, false).compare(TypeList.ground) || move.getType(false, false).compare(TypeList.rock) || move.getType(false, false).compare(TypeList.steel))) {
                return 1.3;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability sand_stream = new Ability(
        "Sand Stream",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            boolean canActivate = FieldConditionList.sand.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.sand.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability sand_veil = new Ability(
        "Sand Veil",
        (_, _, _, _, _, _, _, _, _, _) -> {
            // proteção contra Sandstorm é feita em FieldConditionList.sand
            if (Battle.getWeather().compare(FieldConditionList.sand)) {
                return 3277.0/4096.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentAccuracyCalc
        },
        false, false, false,
        true
    );

    public static final Ability scrappy = new Ability(
        "Scrappy",
        (_, _, _, _, type, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.ChangeOpponentIneffective) {
                if (type.compare(TypeList.ghost)) {
                    return new Type[0];
                }
                return type.getIneffective(null, true);
            }

            if (condition == AbilityActivation.TryIntimidate) {
                return false;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.ChangeOpponentIneffective,
            AbilityActivation.TryIntimidate
        },
        false, false, false
    );

    public static final Ability serene_grace = new Ability(
        "Serene Grace",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return 2.0;
        },
        new AbilityActivation[] {
            AbilityActivation.EffectChanceCalc
        },
        false, false, false
    );

    public static final Ability shadow_shield = new Ability(
        "Shadow Shield",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() == self.getHP()) {
                return 0.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentDamageCalc
        },
        false, false, false
    );

    public static final Ability sharpness = new Ability(
        "Sharpness",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (Arrays.asList(move.getMoveTypes()).contains(MoveType.Slicing)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability shed_skin = new Ability(
        "Shed Skin",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (!self.getNonVolatileStatus().compare(StatusConditionList.none) &&
                Math.random() < 1.0/3.0) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + "'s Shed Skin cured its " + self.getNonVolatileStatus().getName() + "!");
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                self.endNonVolatileStatus(false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability sheer_force = new Ability(
        "Sheer Force",
        (_, _, _, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.SecondaryEffectActivation) {
                return true;
            }

            if (condition == AbilityActivation.UserPowerCalc) {
                if (move.getSecondaryEffect() != null) {
                    return 5325.0/4096.0;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.SecondaryEffectActivation,
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability shell_armor = new Ability(
        "Shell Armor",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryCritUser
        },
        false, false, false,
        true
    );

    public static final Ability shield_dust = new Ability(
        "Shield Dust",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getSecondaryEffectTarget() == EffectTarget.Target) {
                return true;
            }
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentSecondaryEffectActivation
        },
        false, false, false,
        true
    );

    public static final Ability simple = new Ability(
        "Simple",
        (_, _, _, _, _, _, _, _, statChangeStages, _) -> {
            return statChangeStages * 2;
        },
        new AbilityActivation[] {
            AbilityActivation.ModifyStatChangeStages
        },
        false, false, false,
        true
    );

    public static final Ability slow_start = new Ability(
        "Slow Start",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                System.out.println(self.getName(true, true) + " can't get it going because of its Slow Start!");
                // cause é null para não aparecer Slow Start na mensagem
                self.getStat(StatName.Atk).change(-2, null, true, true, false);
                self.getStat(StatName.Spe).change(-2, null, true, true, false);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.TurnEnd) {
                thisAbility.setCounter(thisAbility.getCounter() + 1);

                if (thisAbility.getCounter() >= 5) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    System.out.println(self.getName(true, true) + " finally got its act together!");
                    // cause é null para não aparecer Slow Start na mensagem
                    self.getStat(StatName.Atk).change(2, null, true, true, false);
                    self.getStat(StatName.Spe).change(2, null, true, true, false);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    thisAbility.setActive(false);
                }
            }

            if (condition == AbilityActivation.Removed) {
                System.out.println(self.getName(true, true) + " got its act together!");
                // cause é null para não aparecer Slow Start na mensagem
                self.getStat(StatName.Atk).change(2, null, true, true, false);
                self.getStat(StatName.Spe).change(2, null, true, true, false);
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.TurnEnd,
            AbilityActivation.Removed
        },
        false, false, false
    );

    public static final Ability sniper = new Ability(
        "Sniper",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return 1.5;
        },
        new AbilityActivation[] {
            AbilityActivation.Crit
        },
        false, false, false
    );

    public static final Ability snow_cloak = new Ability(
        "Snow Cloak",
        (_, _, _, _, _, _, _, _, _, _) -> {
            if (Battle.getWeather().compare(FieldConditionList.snow)) {
                return 3277.0/4096.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentAccuracyCalc
        },
        false, false, false,
        true
    );

    public static final Ability snow_warning = new Ability(
        "Snow Warning",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
            boolean canActivate = FieldConditionList.snow.apply(thisAbility, true, false);

            if (canActivate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                FieldConditionList.snow.apply(thisAbility, false, true);

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability solar_power = new Ability(
        "Solar Power",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                if (condition == AbilityActivation.SpecialAttackCalc) {
                    return 1.5;
                }

                if (condition == AbilityActivation.TurnEnd) {
                    int sunDamage = Integer.max(self.getHP()/8, 1);

                    String message = self.getName(true, true) + " was hurt by Solar Power!";
                    Damage.indirectDamage(self, self, sunDamage, DamageSource.Ability, thisAbility, message, true);
                }
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.SpecialAttackCalc,
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability soul_heart = new Ability(
        "Soul-Heart",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            if (self.getStat(StatName.SpA).getTrueStages() < 6) {
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.AnyFaint
        },
        false, false, false
    );

    public static final Ability speed_boost = new Ability(
        "Speed Boost",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            if (self.getTurnsOnField() > 0 &&
                self.getStat(StatName.Spe).getTrueStages() < 6) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                self.getStat(StatName.Spe).change(1, thisAbility, true, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.TurnEnd
        },
        false, false, false
    );

    public static final Ability static_ab = new Ability(
        "Static",
        (thisAbility, _, opponent, move, _, _, _, _, _, _) -> {
            if (move.makesContact(false)) {
                if (opponent.getNonVolatileStatus().compare(StatusConditionList.none) &&
                    Math.random() < 0.3) {
                    StatusConditionList.paralysis.apply(opponent, thisAbility, true);
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.HitUser
        },
        false, false, false
    );

    public static final Ability steadfast = new Ability(
        "Steadfast",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            self.getStat(StatName.Atk).change(1, thisAbility, true, true, false);
            self.getStat(StatName.Spe).change(1, thisAbility, true, true, false);
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Flinch
        },
        false, false, false
    );

    public static final Ability steelworker = new Ability(
        "Steelworker",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.steel)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability storm_drain = new Ability(
        "Storm Drain",
        (thisAbility, self, _, move, _, _, _, _, _, _) -> {
            // TODO redireção
            if (move.getType(false, false).compare(TypeList.water) && move.targetsOpponent()) {
                System.out.println(self.getName(true, true) + "'s Storm Drain absorbed the move!");
                self.getStat(StatName.SpA).change(1, thisAbility, true, true, false);
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability sturdy = new Ability(
        "Sturdy",
        (thisAbility, self, _, move, _, damage, _, _, _, condition) -> {
            if (condition == AbilityActivation.TryHitUser) {
                if (move.hasInherentProperty(InherentProperty.OneHitKO)) {
                    System.out.println(self.getName(true, true) + " was protected by Sturdy!");
                    return false;
                }
                return true;
            }

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
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser,
            AbilityActivation.DeductHP,
            AbilityActivation.PostHitMessage
        },
        false, false, false,
        true
    );

    public static final Ability super_luck = new Ability(
        "Super Luck",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return 1;
        },
        new AbilityActivation[] {
            AbilityActivation.CritRatioCalc
        },
        false, false, false
    );

    public static final Ability swarm = new Ability(
        "Swarm",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() <= self.getHP()/3 && move.getType(false, false).compare(TypeList.bug)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability swift_swim = new Ability(
        "Swift Swim",
        (_, _, _, _, _, _, _, _, _, _) -> {
            if (Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) {
                return 2.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.SpeedCalc
        },
        false, false, false
    );

    public static final Ability sword_of_ruin = new Ability(
        "Sword of Ruin",
        (thisAbility, self, _, _, _, _, _, stat, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Sword of Ruin weakened the Defense of all surrounding Pokémon!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AnyStatCalc) {
                if (stat.getPokemon().getAbility().compare(thisAbility)) {
                    return 1.0;
                }

                boolean magicRoom = false;
                for (FieldCondition fieldCondition : Battle.generalField) {
                    if (fieldCondition.compare(FieldConditionList.magic_room)) {
                        magicRoom = true;
                    }
                }

                if (!magicRoom && stat.compare(Stat.def) ||
                    magicRoom && stat.compare(Stat.spd)) {
                    return 0.75;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.AnyStatCalc
        },
        false, false, false,
        true
    );

    public static final Ability synchronize = new Ability(
        "Synchronize",
        (thisAbility, _, opponent, _, _, _, statusCondition, _, _, _) -> {
            if (opponent.getNonVolatileStatus().compare(StatusConditionList.none) && (
                    statusCondition.compare(StatusConditionList.burn) ||
                    statusCondition.compare(StatusConditionList.paralysis) ||
                    statusCondition.compare(StatusConditionList.poison) ||
                    statusCondition.compare(StatusConditionList.bad_poison) ||
                    statusCondition.compare(StatusConditionList.frostbite)
                )) {
                StatusCondition synchronizeStatus = new StatusCondition(statusCondition);
                synchronizeStatus.apply(opponent, thisAbility, true);
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.StatusConditionOnUser
        },
        false, false, false
    );

    public static final Ability tablets_of_ruin = new Ability(
        "Tablets of Ruin",
        (thisAbility, self, _, _, _, _, _, stat, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Tablets of Ruin weakened the Attack of all surrounding Pokémon!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AnyStatCalc) {
                if (stat.getPokemon().getAbility().compare(thisAbility)) {
                    return 1.0;
                }

                if (stat.compare(Stat.atk)) {
                    return 0.75;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.AnyStatCalc
        },
        false, false, false,
        true
    );

    public static final Ability technician = new Ability(
        "Technician",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getPower(false, true, 0) <= 60) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability telepathy = new Ability(
        "Telepathy",
        (_, _, _, _, _, _, _, _, _, _) -> {
            // sem uso em singles
            return null;
        },
        new AbilityActivation[0], // AbilityActivation.AllyTryHitUser
        false, false, false,
        true
    );

    public static final Ability tera_shell = new Ability(
        "Tera Shell",
        (_, self, _, move, type, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.CallUserSuperEffective) {
                if (self.getCurrentHP() == self.getHP()) {
                    return new Type[0];
                }
                return type.getSuperEffective(null, true);
            }

            if (condition == AbilityActivation.CallUserNotVeryEffective) {
                if (self.getCurrentHP() == self.getHP()) {
                    List<Type> newResistances = new ArrayList<>();

                    for (Type type2 : AllTypes.allTypes) {
                        if (!type2.compare(TypeList.typeless)) {
                            newResistances.add(type2);
                        }
                    }
                    return newResistances.toArray(new Type[0]);
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
        },
        new AbilityActivation[] {
            AbilityActivation.CallUserSuperEffective,
            AbilityActivation.CallUserNotVeryEffective,
            AbilityActivation.BeforeHit
        },
        false, false, false,
        true
    );

    public static final Ability tera_shift = new Ability(
        "Tera Shift",
        (_, self, _, _, _, _, _, _, _, _) -> {
            if (self.compare(Data.get().getPokemon("terapagos"), true) &&
                self.compareWithForm(Data.get().getPokemon("terapagos"))) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(self.getName(true, true) + " transformed into its Terastal Form!");
                self.changeForm("Terastal");
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry
        },
        true, true, true
    );

    public static final Ability teraform_zero = new Ability(
        "Teraform Zero",
        (thisAbility, self, _, _, _, _, _, _, _, _) -> {
            System.out.println("\n" + self.getName(true, true) + "'s Teraform Zero has reduced weather and terrain to zero!");
            Battle.getTrueWeather().end();
            Battle.getTerrain().end();
            thisAbility.setActive(false);
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.AbilityUpdate
        },
        true, true, true
    );

    public static final Ability teravolt = new Ability(
        "Teravolt",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " is radiating a bursting aura!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.IgnoreAbility) {
                return true;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.IgnoreAbility
        },
        false, false, false
    );

    public static final Ability thick_fat = new Ability(
        "Thick Fat",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.fire) || move.getType(false, false).compare(TypeList.ice)) {
                return 0.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentAttackCalc,
            AbilityActivation.OpponentSpecialAttackCalc
        },
        false, false, false,
        true
    );

    public static final Ability tinted_lens = new Ability(
        "Tinted Lens",
        (_, _, opponent, move, _, _, _, _, _, _) -> {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(move, opponent);
            effectivenessMultiplier /= Damage.notVeryEffective(move, opponent);

            if (effectivenessMultiplier < 1) {
                return 2.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserDamageCalc
        },
        false, false, false
    );

    public static final Ability torrent = new Ability(
        "Torrent",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (self.getCurrentHP() <= self.getHP()/3 && move.getType(false, false).compare(TypeList.water)) {
                return 1.5;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability tough_claws = new Ability(
        "Tough Claws",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.makesContact(false)) {
                return 5325.0/4096.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.UserPowerCalc
        },
        false, false, false
    );

    public static final Ability toxic_chain = new Ability(
        "Toxic Chain",
        (thisAbility, _, opponent, move, _, _, _, _, _, _) -> {
            if (move.targetsOpponent() && move.getCategory() != Category.Status) {
                if (opponent.getNonVolatileStatus().compare(StatusConditionList.none) &&
                    Math.random() < 0.3) {
                    StatusConditionList.bad_poison.apply(opponent, thisAbility, 1, true);
                }
            }
            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.HitTarget
        },
        false, false, false
    );

    public static final Ability transistor = new Ability(
        "Transistor",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.electric)) {
                return 5325.0/4096.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AttackCalc,
            AbilityActivation.SpecialAttackCalc
        },
        false, false, false
    );

    public static final Ability turboblaze = new Ability(
        "Turboblaze",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + " is radiating a blazing aura!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.IgnoreAbility) {
                return true;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.IgnoreAbility
        },
        false, false, false
    );

    public static final Ability ultimate_weapon = new Ability( // fanmade
        "Ultimate Weapon",
        (_, self, _, move, type, _, _, _, _, condition) -> {
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
                    for (Type resistance : type.getNotVeryEffective(null, true)) {
                        if (ultimateType.compare(resistance)) {
                            List<Type> newResistances = new ArrayList<>(Arrays.asList(type.getNotVeryEffective(null, true)));
                            newResistances.remove(resistance);
                            return newResistances.toArray(new Type[0]);
                        }
                    }
                }

                return type.getNotVeryEffective(null, true);
            }

            if (condition == AbilityActivation.ChangeOpponentIneffective) {
                if (sameTypeAsUser) {
                    for (Type immunity : type.getIneffective(null, true)) {
                        if (ultimateType.compare(immunity)) {
                            List<Type> newImmunities = new ArrayList<>(Arrays.asList(type.getIneffective(null, true)));
                            newImmunities.remove(immunity);
                            return newImmunities.toArray(new Type[0]);
                        }
                    }
                }

                return type.getIneffective(null, true);
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.ChangeOpponentNotVeryEffective,
            AbilityActivation.ChangeOpponentIneffective
        },
        false, false, false
    );

    public static final Ability unaware = new Ability(
        "Unaware",
        (_, _, _, _, _, _, _, _, _, _) -> {
            return 0;
        },
        new AbilityActivation[] {
            AbilityActivation.CallOpponentStatStages
        },
        false, false, false,
        true
    );

    public static final Ability unburden = new Ability(
        "Unburden",
        (thisAbility, _, _, _, _, _, _, _, _, condition) -> {
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
        },
        new AbilityActivation[] {
            AbilityActivation.ItemConsumed,
            AbilityActivation.ItemGained,
            AbilityActivation.SpeedCalc
        },
        false, false, false
    );

    public static final Ability unnerve = new Ability(
        "Unnerve",
        (_, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Unnerve made " + (self.getTeam() == 0 ? "the opposing" : "your") + " team too nervous to eat Berries!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.OpponentTryUseBerry) {
                return false;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.OpponentTryUseBerry
        },
        false, false, false
    );

    public static final Ability unseen_fist = new Ability(
        "Unseen Fist",
        (_, _, opponent, move, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.OpponentTryProtect) {
                if (move.makesContact(false)) {
                    return false;
                }
                return true;
            }

            if (condition == AbilityActivation.UserDamageCalc) {
                boolean opponentProtected = false;
                boolean affected = false;

                if (opponent.getVolatileStatus(StatusConditionList.protection) != null) {
                    opponentProtected = true;
                    affected = move.targetsOpponent();
                }

                if (!opponentProtected || !affected) {
                    for (FieldCondition fieldCondition : Battle.teamFields.get(opponent.getTeam())) {
                        if (fieldCondition.compare(FieldConditionList.quick_guard)) {
                            opponentProtected = true;
                            affected = move.targetsOpponent() && move.getPriority() > 0;
                        } else if (fieldCondition.compare(FieldConditionList.wide_guard)) {
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
        },
        new AbilityActivation[] {
            AbilityActivation.OpponentTryProtect,
            AbilityActivation.UserDamageCalc
        },
        false, false, false
    );

    public static final Ability unstoppability = new Ability( // fanmade
        "Unstoppability",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                System.out.println(self.getName(true, true) + "'s Unstoppability has warped space!");

                Battle.getTrueWeather().end();
                Battle.getTerrain().end();
                for (FieldCondition fieldCondition : Battle.generalField) {
                    if (!fieldCondition.compare(FieldConditionList.uproar)) {
                        fieldCondition.end();
                    }
                }
                for (int i = 0; i < 2; i++) {
                    ArrayList<FieldCondition> field = Battle.teamFields.get(i);
                    for (FieldCondition fieldCondition : field) {
                        fieldCondition.end(field);
                    }
                }

                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                thisAbility.setActive(false);
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate
        },
        false, false, false
    );

    public static final Ability vessel_of_ruin = new Ability(
        "Vessel of Ruin",
        (thisAbility, self, _, _, _, _, _, stat, _, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }
                System.out.println(self.getName(true, true) + "'s Vessel of Ruin weakened the Special Attack of all surrounding Pokémon!");
                if (condition != AbilityActivation.AbilityUpdate) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == AbilityActivation.AnyStatCalc) {
                if (stat.getPokemon().getAbility().compare(thisAbility)) {
                    return 1.0;
                }

                if (stat.compare(Stat.spa)) {
                    return 0.75;
                }
                return 1.0;
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.Entry,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.AnyStatCalc
        },
        false, false, false,
        true
    );

    public static final Ability victory_star = new Ability(
        "Victory Star",
        (_, _, _, move, _, _, _, _, _, _) -> {
            if (move.getAccuracy() != -1) {
                return 4506.0/4096.0;
            }
            return 1.0;
        },
        new AbilityActivation[] {
            AbilityActivation.AccuracyCalc
        },
        false, false, false
    );

    public static final Ability volt_absorb = new Ability(
        "Volt Absorb",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.electric) && move.targetsOpponent()) {
                System.out.println(self.getName(true, true) + " restored HP using its Volt Absorb!");
                Damage.heal(self, null, self.getHP()/4, true, false);
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability water_absorb = new Ability(
        "Water Absorb",
        (_, self, _, move, _, _, _, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.water) && move.targetsOpponent()) {
                System.out.println(self.getName(true, true) + " restored HP using its Water Absorb!");
                Damage.heal(self, null, self.getHP()/4, true, false);
                return false;
            }
            return true;
        },
        new AbilityActivation[] {
            AbilityActivation.TryHitUser
        },
        false, false, false,
        true
    );

    public static final Ability water_veil = new Ability(
        "Water Veil",
        (_, self, _, _, _, _, statusCondition, _, _, condition) -> {
            if (condition == AbilityActivation.TryStatusConditionOnUser &&
                statusCondition.compare(StatusConditionList.burn)) {
                return true;
            }

            if (condition == AbilityActivation.AbilityUpdate && self.getNonVolatileStatus().compare(StatusConditionList.burn) ||
                condition == AbilityActivation.StatusConditionOnUser && statusCondition.compare(StatusConditionList.burn)) {
                System.out.println(self.getName(true, true) + "'s Water Veil healed its burn!");
                self.endNonVolatileStatus(false);
            }

            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatusConditionOnUser,
            AbilityActivation.AbilityUpdate,
            AbilityActivation.StatusConditionOnUser
        },
        false, false, false,
        true
    );

    public static final Ability white_smoke = new Ability(
        "White Smoke",
        (_, self, _, _, _, _, _, _, statChangeStages, _) -> {
            if (statChangeStages < 0) {
                System.out.println(self.getName(true, true) + " is protected by White Smoke!");
                return true;
            }
            return false;
        },
        new AbilityActivation[] {
            AbilityActivation.TryStatChangeOnUser
        },
        false, false, false,
        true
    );

    public static final Ability zero_to_hero = new Ability(
        "Zero to Hero",
        (thisAbility, self, _, _, _, _, _, _, _, condition) -> {
            if (self.compare(Data.get().getPokemon("palafin"), true)) {
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
            }

            return null;
        },
        new AbilityActivation[] {
            AbilityActivation.SwitchOut,
            AbilityActivation.Entry
        },
        true, true, true
    );
}
