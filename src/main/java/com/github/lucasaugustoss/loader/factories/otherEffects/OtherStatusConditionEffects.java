package com.github.lucasaugustoss.loader.factories.otherEffects;

import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.effectFunctions.StatusConditionEffectFunction;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.oldObjects.MoveList;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;

public class OtherStatusConditionEffects {
    public static final StatusConditionEffectFunction freeze =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (activation == StatusActivation.Start) {
                if (pokemon.compare(Data.get().getPokemon("shaymin"), true) &&
                    pokemon.compareWithForm(Data.get().getPokemon("shaymin_sky"))) {
                    pokemon.changeForm("Land");
                    System.out.println(pokemon.getName(true, true) + " returned to its Land form!");
                }
            }

            if (activation == StatusActivation.TryAct) {
                if (move.hasInherentProperty(InherentProperty.ThawsUser)) {
                    pokemon.endNonVolatileStatus(false);
                    System.out.println(pokemon.getName(true, true) + "'s " + move.getName() + " melted the ice!");
                    return true;
                }
                if (Math.random() < 0.2) {
                    pokemon.endNonVolatileStatus(true);
                    return true;
                }

                thisCondition.getMessages().print("stop move", Map.of(
                    "Pokemon", pokemon.getName(true, false)
                ));
                return false;
            }

            if (activation == StatusActivation.Hit) {
                if (move.getType(false, false).compare(Data.get().getType("fire")) ||
                    move.hasInherentProperty(InherentProperty.ThawsTarget)) {
                    pokemon.endNonVolatileStatus(false);
                    System.out.println(move.getUser().getName(true, true) + "'s " + move.getName() + " melted the ice!");
                }
            }

            return null;
        };

    public static final StatusConditionEffectFunction end =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            pokemon.endVolatileStatus(thisCondition, showMessages);
            return null;
        };

    public static final StatusConditionEffectFunction block_switch =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            thisCondition.getMessages().print("block switch", Map.of(
                "Pokemon", pokemon.getTrueName(false, false),
                "Move", move.getName()
            ));
            return true;
        };

    public static final StatusConditionEffectFunction block_forced_switch =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            thisCondition.getMessages().print("block forced switch", Map.of(
                "Pokemon", pokemon.getTrueName(false, false)
            ));
            return false;
        };

    public static final StatusConditionEffectFunction semi_invulnerable_charging_turn =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            // Fly/Bounce/Sky Drop: Gust, Sky Uppercut, Thunder, Twister
            // Smack Down e Thousand Arrows são casos especiais (mas ainda devem entrar aqui)
            if ((
                    thisCondition.getCausingMove().compare(MoveList.bounce) ||
                    thisCondition.getCausingMove().compare(MoveList.fly)
                ) &&
                (
                    move.compare(MoveList.gust) ||
                    move.compare(MoveList.smack_down) ||
                    move.compare(MoveList.thousand_arrows) ||
                    move.compare(MoveList.thunder) ||
                    move.compare(MoveList.twister)
                )) {
                if (activation == StatusActivation.Invulnerability) {
                    return true;
                }
                if (activation == StatusActivation.OpponentDamageCalc) {
                    // Gust e Twister têm poder dobrado
                    if (move.compare(MoveList.gust) ||
                        move.compare(MoveList.twister)) {
                        return 2.0;
                    }
                }
            }

            // Dig: Earthquake, Magnitude, Fissure
            if (thisCondition.getCausingMove().compare(MoveList.dig) &&
                (
                    move.compare(MoveList.earthquake) ||
                    move.compare(MoveList.fissure)
                )) {
                if (activation == StatusActivation.Invulnerability) {
                    return true;
                }
                if (activation == StatusActivation.OpponentDamageCalc) {
                    return 2.0;
                }
            }

            // Dive: Surf, Whirlpool
            if (thisCondition.getCausingMove().compare(MoveList.dive) &&
                (
                    move.compare(MoveList.surf) ||
                    move.compare(MoveList.whirlpool)
                )) {
                if (activation == StatusActivation.Invulnerability) {
                    return true;
                }
                if (activation == StatusActivation.OpponentDamageCalc) {
                    return 2.0;
                }
            }

            if (activation == StatusActivation.Invulnerability) {
                return false;
            }
            if (activation == StatusActivation.OpponentDamageCalc) {
                return 1.0;
            }
            return null;
        };

    public static final StatusConditionEffectFunction protection =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            boolean affected = move != null && move.targetsOpponent();

            if (activation == StatusActivation.OpponentTryUseMoveTargeted) {
                if (!thisCondition.getCausingMove().compare(MoveList.max_guard) &&
                    opponent.getAbility().shouldActivate(AbilityActivation.OpponentTryProtect) &&
                    !((boolean) opponent.getAbility().activate(opponent, pokemon, move, null, null, null, null, 0, AbilityActivation.OpponentTryProtect))) {
                    return true;
                }

                if (affected &&
                    !move.isZMove() &&
                    (!move.hasInherentProperty(InherentProperty.IgnoresProtection) || thisCondition.getCausingMove().compare(MoveList.max_guard))) {
                    System.out.println(pokemon.getName(true, true) + " protected itself!");

                    if (thisCondition.getCausingMove().compare(MoveList.spiky_shield) &&
                        move.makesContact(false)) {
                        Damage.indirectDamage(opponent, pokemon, opponent.getHP()/8, DamageSource.StatusCondition, thisCondition, "", false);
                    }

                    return false;
                }
                if (move.hasInherentProperty(InherentProperty.BreaksProtection) && !thisCondition.getCausingMove().compare(MoveList.max_guard)) {
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println("It broke through " + pokemon.getName(true, false) + "'s protection!");
                }
                return true;
            }
            if (activation == StatusActivation.OpponentDamageCalc) {
                if (move.isZMove() && affected) {
                    System.out.println(pokemon.getName(true, true) + " couldn't fully protect itself and got hurt!");
                    return 0.25;
                }
                return 1.0;
            }
            return null;
        };

    public static final StatusConditionEffectFunction substitute =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (activation == StatusActivation.BeforeHit) {
                if (move.hasInherentProperty(InherentProperty.IgnoresSubstitute)) {
                    return false;
                }

                Pokemon user = move.getUser();

                int remainingSubstituteHP = thisCondition.getCounter() - damage.amount;

                int dealtDamage = damage.amount;
                if (remainingSubstituteHP < 0) {
                    dealtDamage = thisCondition.getCounter();
                    thisCondition.setCounter(0);
                } else {
                    thisCondition.setCounter(remainingSubstituteHP);
                }

                if (user.getTeam() != pokemon.getTeam()) {
                    user.addDamageDealt(dealtDamage);
                }

                System.out.println(pokemon.getName(true, true) + "'s substitute took " + damage + " damage!");

                if (thisCondition.getCounter() == 0) {
                    pokemon.endVolatileStatus(thisCondition, true);
                }

                return true;
            }
            if (activation == StatusActivation.PrimaryEffectActivation) {
                if (move.getPrimaryEffectTarget() == EffectTarget.Target &&
                    !move.hasInherentProperty(InherentProperty.IgnoresSubstitute)) {
                    return true;
                }
                return false;
            }
            if (activation == StatusActivation.SecondaryEffectActivation) {
                if (move.getSecondaryEffectTarget() == EffectTarget.Target &&
                    !move.hasInherentProperty(InherentProperty.IgnoresSubstitute)) {
                    return true;
                }
                return false;
            }
            return null;
        };

    public static final StatusConditionEffectFunction countering =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (thisCondition.getCausingMove().compare(MoveList.counter) && move.getCategory() == Category.Physical ||
                thisCondition.getCausingMove().compare(MoveList.mirror_coat) && move.getCategory() == Category.Special ||
                thisCondition.getCausingMove().compare(MoveList.metal_burst)) {
                thisCondition.setCounter(thisCondition.getCounter() + damage.amount);
                thisCondition.setAffectedMove(move);
            }
            return null;
        };

    public static final StatusConditionEffectFunction endure =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (activation == StatusActivation.DeductHP) {
                thisCondition.setCounter(1);
                return true;
            }
            if (activation == StatusActivation.PostHitMessage) {
                if (thisCondition.getCounter() == 1 &&
                    !Battle.faintCheck(pokemon, false)) {
                    System.out.println(pokemon.getName(true, true) + " endured the hit!");
                    thisCondition.setCounter(0);
                }
            }
            return null;
        };

    public static final StatusConditionEffectFunction roost =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (pokemon.hasType(Data.get().getType("flying"))) {
                pokemon.getType(Data.get().getType("flying")).setSuppressed(false);
            }
            pokemon.endVolatileStatus(thisCondition, true);
            return null;
        };

    public static final StatusConditionEffectFunction encore_move_change =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (!move.isZMove() && !move.isZPowered() && !move.compare(MoveList.struggle)) {
                if (pokemon.getLastUsedMove().getCurrentPP() > 0) {
                    return pokemon.getLastUsedMove();
                } else {
                    return new Move(MoveList.struggle, pokemon);
                }
            }
            return move;
        };

    public static final StatusConditionEffectFunction charge =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (move.getType(false, false).compare(Data.get().getType("electric"))) {
                pokemon.endVolatileStatus(thisCondition, true);
                return 2.0;
            }
            return 1.0;
        };

    public static final StatusConditionEffectFunction focus_break =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (move.getCategory() != Category.Status) {
                thisCondition.setCounter(1);
            }
            return null;
        };

    public static final StatusConditionEffectFunction ability_removal =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (pokemon.getAbility().shouldActivate(AbilityActivation.Removed)) {
                pokemon.getAbility().activate(pokemon, opponent, move, null, null, null, null, 0, AbilityActivation.Removed);
            }
            return null;
        };

    public static final StatusConditionEffectFunction taking_aim =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (thisCondition.getCauser() == opponent &&
                move.getUser() == opponent &&
                opponent.getVolatileStatus(thisCondition) != null) {
                move.addProperty(TemporaryProperty.CantMiss);
            }
            return null;
        };

    public static final StatusConditionEffectFunction magic_coat =
        (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (move.getCategory() == Category.Status && move.targetsOpponent() &&
                !move.hasInherentProperty(InherentProperty.NotReflectable) &&
                !move.getTemporaryProperties().contains(TemporaryProperty.Reflected)) {
                System.out.println(pokemon.getName(true, true) + " bounced the " + move.getName() + " back!");

                Action moveAction = Battle.findAction(move, opponent);
                Move copiedMove = new Move(move, pokemon);
                for (TemporaryProperty property : move.getTemporaryProperties()) {
                    copiedMove.addProperty(property);
                }
                copiedMove.addProperty(TemporaryProperty.Reflected);

                Battle.addAction(new Action(copiedMove, pokemon, opponent), moveAction);

                return false;
            }
            return true;
        };
}
