package com.github.lucasaugustoss.data.objects.oldObjects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.messages.list.StatusMessages;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;

public class StatusConditionList {
    // não condições

    public static final StatusCondition none = new StatusCondition(
        "None",
        false,
        null,
        new StatusActivation[0],
        null
    );

    public static final StatusCondition placeholder = new StatusCondition(
        "Volatile Placeholder",
        true,
        null,
        new StatusActivation[0],
        null
    );

    public static final StatusCondition readying_switch = new StatusCondition(
        "Readying Switch",
        true,
        null,
        new StatusActivation[0],
        null
    );


    // não-voláteis

    public static final StatusCondition burn = new StatusCondition(
        "Burn",
        false,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            int burnDamage = Integer.max(pokemon.getHP()/16, 1);
            String message = pokemon.getName(true, true) + " was hurt by its burn!";
            Damage.indirectDamage(pokemon, thisCondition.getCauser(), burnDamage, DamageSource.StatusCondition, thisCondition, message, true);
            // redução de ataque físico é feito em Damage.calcDamage()
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.burn
    );

    public static final StatusCondition paralysis = new StatusCondition(
        "Paralysis",
        false,
        (_, pokemon, _, _, _, _, _) -> {
            if (Math.random() < 0.25) {
                System.out.println(pokemon.getName(true, true) + " couldn't move because it's paralyzed!");
                return false;
            }
            return true;
        },
        new StatusActivation[] {
            StatusActivation.TryMove
        },
        StatusMessages.paralysis
    );

    public static final StatusCondition poison = new StatusCondition(
        "Poison",
        false,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            int poisonDamage = Integer.max(pokemon.getHP()/8, 1);
            String message = pokemon.getName(true, true) + " was hurt by poison!";
            Damage.indirectDamage(pokemon, thisCondition.getCauser(), poisonDamage, DamageSource.StatusCondition, thisCondition, message, true);
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.poison
    );

    public static final StatusCondition bad_poison = new StatusCondition(
        "Bad Poison",
        false,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            int poisonDamage = Integer.max((int) (pokemon.getHP()*(pokemon.getNonVolatileStatus().getCounter()/16.0)), 1);
            String message = pokemon.getName(true, true) + " was hurt by poison!";
            Damage.indirectDamage(pokemon, thisCondition.getCauser(), poisonDamage, DamageSource.StatusCondition, thisCondition, message, true);

            if (!Battle.faintCheck(pokemon, false) &&
                pokemon.getNonVolatileStatus().getCounter() < 15) {
                int value = pokemon.getNonVolatileStatus().getCounter() + 1;
                pokemon.getNonVolatileStatus().setCounter(value);
            }

            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.bad_poison
    );

    public static final StatusCondition sleep = new StatusCondition(
        "Sleep",
        false,
        (_, pokemon, _, move, _, _, _) -> {
            if (pokemon.getNonVolatileStatus().getCounter() <= 0) {
                pokemon.endNonVolatileStatus(true);
                return true;
            } else {
                int value = pokemon.getNonVolatileStatus().getCounter() - 1;
                pokemon.getNonVolatileStatus().setCounter(value);
                System.out.println(pokemon.getName(true, true) + " is fast asleep.");

                if (move.hasInherentProperty(InherentProperty.UsableOnSleep)) {
                    return true;
                } else {
                    return false;
                }
            }
        },
        new StatusActivation[] {
            StatusActivation.TryAct
        },
        StatusMessages.sleep
    );

    public static final StatusCondition freeze = new StatusCondition( // NÃO USADO
        "Freeze",
        false,
        (_, pokemon, _, move, _, _, activation) -> {
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
                System.out.println(pokemon.getName(true, true) + " is frozen solid!");
                return false;
            }

            if (activation == StatusActivation.Hit) {
                if (move.getType(false, false).compare(TypeList.fire) ||
                    move.hasInherentProperty(InherentProperty.ThawsTarget)) {
                    pokemon.endNonVolatileStatus(false);
                    System.out.println(move.getUser().getName(true, true) + "'s " + move.getName() + " melted the ice!");
                }
            }

            return null;
        },
        new StatusActivation[] {
            StatusActivation.Start,
            StatusActivation.TryAct,
            StatusActivation.Hit
        },
        StatusMessages.freeze
    );

    public static final StatusCondition frostbite = new StatusCondition(
        "Frostbite",
        false,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            int frostbiteDamage = Integer.max(pokemon.getHP()/16, 1);
            String message = pokemon.getName(true, true) + " was hurt by its frostbite!";
            Damage.indirectDamage(pokemon, thisCondition.getCauser(), frostbiteDamage, DamageSource.StatusCondition, thisCondition, message, true);
            // redução de defesa física é feita em Damage.calcDamage()
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.frostbite
    );


    // voláteis

    public static final StatusCondition confusion = new StatusCondition(
        "Confusion",
        true,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            if (thisCondition.getCounter() <= 0) {
                pokemon.endVolatileStatus(thisCondition, true);
                return true;
            } else {
                thisCondition.setCounter(thisCondition.getCounter() - 1);

                System.out.println(pokemon.getName(true, true) + " is confused!");

                if (Math.random() < 33.0/100.0) {
                    System.out.println("It hurt itself in its confusion!");

                    Move confusionHit = new Move(
                        new Move(
                            "",
                            TypeList.typeless,
                            Category.Physical,
                            1,
                            40,
                            -1,
                            1,
                            false,
                            0,
                            MoveTarget.User,
                            null,
                            null,
                            null,
                            null,
                            new MoveEffectActivation[0]
                        ),
                        pokemon
                    );

                    Damage.directDamage(pokemon, pokemon, confusionHit, true);

                    return false;
                }
                return true;
            }
        },
        new StatusActivation[] {
            StatusActivation.TryMove
        },
        StatusMessages.confusion
    );

    public static final StatusCondition flinch = new StatusCondition(
        "Flinch",
        true,
        (thisCondition, pokemon, _, _, _, _, activation) -> {
            if (activation == StatusActivation.TryAct) {
                System.out.println(pokemon.getName(true, true) + " flinched and couldn't move!");
                if (pokemon.getAbility().shouldActivate(AbilityActivation.Flinch)) {
                    pokemon.getAbility().activate(pokemon, null, null, null, null, null, null, 0, AbilityActivation.Flinch);
                }
                return false;
            }
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.TryAct,
            StatusActivation.EndOfTurn
        },
        null
    );

    public static final StatusCondition bind = new StatusCondition(
        "Bind",
        true,
        (thisCondition, pokemon, _, move, _, _, activation) -> {
            if (activation == StatusActivation.CauserLeaveField) {
                pokemon.endVolatileStatus(thisCondition, false);
            }
            if (activation == StatusActivation.EndOfTurn) {
                int bindDamage = Integer.max(pokemon.getHP()/8, 1);
                String message = pokemon.getName(true, true) + " is hurt by " + thisCondition.getCausingMove().getName() + "!";
                Damage.indirectDamage(pokemon, thisCondition.getCauser(), bindDamage, DamageSource.StatusCondition, thisCondition, message, true);

                thisCondition.setCounter(thisCondition.getCounter() - 1);
                if (thisCondition.getCounter() <= 0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println(pokemon.getName(true, true) + " was freed from " + thisCondition.getCausingMove().getName() + "!");
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            if (activation == StatusActivation.TrySwitch) {
                System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can't escape because of " + move.getName() + " -!\n");
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.CauserLeaveField,
            StatusActivation.EndOfTurn,
            StatusActivation.TrySwitch
        },
        null
    );

    public static final StatusCondition taunt = new StatusCondition(
        "Taunt",
        true,
        (thisCondition, pokemon, _, move, _, showMessages, activation) -> {
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            if (activation == StatusActivation.TryAct) {
                if (!move.isZPowered() &&
                    move.getCategory() == Category.Status) {
                    System.out.println(pokemon.getName(true, true) + " can't use " + move.getName() + " after the taunt!");
                    return false;
                }
                return true;
            }
            if (activation == StatusActivation.TrySelectMove) {
                if (move.getCategory() == Category.Status) {
                    if (showMessages) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can't use " + move.getName() + " after the taunt -!\n");
                    }
                    return false;
                }
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn,
            StatusActivation.TryAct,
            StatusActivation.TrySelectMove
        },
        StatusMessages.taunt
    );

    public static final StatusCondition seed = new StatusCondition(
        "Seed",
        true,
        (thisCondition, pokemon, opponent, _, _, _, _) -> {
            if (!Battle.faintCheck(opponent, false)) {
                int seedDamage = Integer.max(pokemon.getHP()/8, 1);
                String message = pokemon.getName(true, true) + "'s health is sapped by Leech Seed!";
                Damage.indirectDamage(pokemon, opponent, seedDamage, DamageSource.StatusCondition, thisCondition, message, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.seed
    );

    public static final StatusCondition charging_turn = new StatusCondition(
        "Charging Turn",
        true,
        null,
        new StatusActivation[0],
        null
    );

    public static final StatusCondition semi_invulnerable_charging_turn = new StatusCondition(
        "Semi-Invulnerable Charging Turn",
        true,
        (thisCondition, _, _, move, _, _, activation) -> {
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
        },
        new StatusActivation[] {
            StatusActivation.Invulnerability,
            StatusActivation.OpponentDamageCalc
        },
        null
    );

    public static final StatusCondition recharging_turn = new StatusCondition(
        "Recharging Turn",
        true,
        null,
        new StatusActivation[0],
        null
    );

    public static final StatusCondition unusable_move_turn = new StatusCondition(
        "Unusable Move Turn",
        true,
        (thisCondition, pokemon, _, move, _, showMessages, activation) -> {
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    pokemon.endVolatileStatus(thisCondition, true);
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            if (activation == StatusActivation.TrySelectMove) {
                if (move == thisCondition.getAffectedMove()) {
                    if (showMessages) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can't use " + move.getName() + " -!\n");
                    }
                    return false;
                }
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn,
            StatusActivation.TrySelectMove
        },
        null
    );

    public static final StatusCondition rampage = new StatusCondition(
        "Rampage",
        true,
        (thisCondition, pokemon, opponent, move, _, _, _) -> {
            thisCondition.setCounter(thisCondition.getCounter() - 1);
            if (thisCondition.getCounter() <= 0) {
                pokemon.setReadiedMove(null);
                if (pokemon.getVolatileStatus(StatusConditionList.confusion) == null) {
                    StatusConditionList.confusion.apply(pokemon, move, (int) Math.ceil(Math.random()*4)+1, pokemon, false);
                    if (Battle.faintCheck(opponent, false)) {
                        System.out.println();
                    }
                    System.out.println(pokemon.getName(true, true) + " became confused due to fatigue!");
                }
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.UseMove
        },
        null
    );

    public static final StatusCondition protection = new StatusCondition(
        "Protection",
        true,
        (thisCondition, pokemon, opponent, move, _, _, activation) -> {
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
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.OpponentTryUseMoveTargeted,
            StatusActivation.OpponentDamageCalc,
            StatusActivation.EndOfTurn
        },
        StatusMessages.protection
    );

    public static final StatusCondition torment = new StatusCondition(
        "Torment",
        true,
        (_, pokemon, _, move, _, showMessages, _) -> {
            if (pokemon.getLastUsedMove() == move &&
                pokemon.getVolatileStatus(StatusConditionList.rampage) == null &&
                pokemon.getVolatileStatus(StatusConditionList.charging_turn) == null &&
                pokemon.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn) == null &&
                pokemon.getVolatileStatus(StatusConditionList.recharging_turn) == null) {
                if (showMessages) {
                    System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can't use " + move.getName() + " after the torment -!\n");
                }
                return false;
            }
            return true;
        },
        new StatusActivation[] {
            StatusActivation.TrySelectMove
        },
        StatusMessages.torment
    );

    public static final StatusCondition hydrokinesis = new StatusCondition(
        "Hydrokinesis",
        true,
        (thisCondition, pokemon, opponent, move, _, _, activation) -> {
            if (activation == StatusActivation.OpponentTryUseMoveAny) {
                if (move.getType(false, false).compare(TypeList.water) &&
                    !move.isZMove() &&
                    !move.isZPowered() &&
                    !move.hasInherentProperty(InherentProperty.NotSnatchable) &&
                    !move.getTemporaryProperties().contains(TemporaryProperty.Snatched)) {
                    System.out.println(pokemon.getName(true, true) + " took control of " + opponent.getName(true, false) + "'s move!");

                    Action moveAction = Battle.findAction(move, opponent);
                    Move copiedMove = new Move(move, pokemon);
                    copiedMove.addProperty(TemporaryProperty.Snatched);

                    Pokemon target;
                    if (move.targetsOpponent()) {
                        target = opponent;
                    } else {
                        target = pokemon;
                    }

                    Battle.addAction(new Action(copiedMove, pokemon, target), moveAction);

                    return false;
                }
                return true;
            }
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.OpponentTryUseMoveAny,
            StatusActivation.EndOfTurn
        },
        StatusMessages.hydrokinesis
    );

    public static final StatusCondition substitute = new StatusCondition(
        "Substitute",
        true,
        (thisCondition, pokemon, _, move, damage, _, activation) -> {
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
        },
        new StatusActivation[] {
            StatusActivation.BeforeHit,
            StatusActivation.PrimaryEffectActivation,
            StatusActivation.SecondaryEffectActivation
        },
        StatusMessages.substitute
    );

    public static final StatusCondition drowsiness = new StatusCondition(
        "Drowsiness",
        true,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            if (thisCondition.getCounter() <= 0) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                StatusConditionList.sleep.apply(pokemon, thisCondition.getCausingMove(), (int) Math.ceil(Math.random()*3), true);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                thisCondition.setCounter(thisCondition.getCounter() - 1);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.drowsiness
    );

    public static final StatusCondition countering = new StatusCondition(
        "Countering",
        true,
        (thisCondition, pokemon, _, move, damage, _, activation) -> {
            if (activation == StatusActivation.Hit) {
                if (thisCondition.getCausingMove().compare(MoveList.counter) && move.getCategory() == Category.Physical ||
                    thisCondition.getCausingMove().compare(MoveList.mirror_coat) && move.getCategory() == Category.Special ||
                    thisCondition.getCausingMove().compare(MoveList.metal_burst)) {
                    thisCondition.setCounter(thisCondition.getCounter() + damage.amount);
                    thisCondition.setAffectedMove(move);
                }
            }
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.Hit,
            StatusActivation.EndOfTurn
        },
        null
    );

    public static final StatusCondition endure = new StatusCondition(
        "Endure",
        true,
        (thisCondition, pokemon, _, _, _, _, activation) -> {
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
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.DeductHP,
            StatusActivation.PostHitMessage,
            StatusActivation.EndOfTurn
        },
        StatusMessages.endure
    );

    public static final StatusCondition imprison = new StatusCondition(
        "Imprison",
        true,
        (_, pokemon, _, move, _, showMessages, activation) -> {
            boolean moveSealed = false;
            for (Move userMove : pokemon.getTrueMoves()) {
                if (userMove != null && move.compareTrue(userMove)) {
                    moveSealed = true;
                    break;
                }
            }

            if (activation == StatusActivation.TryAct) {
                if (!move.isZPowered() &&
                    moveSealed && !move.getTemporaryProperties().contains(TemporaryProperty.Called)) {
                    System.out.println(move.getUser().getName(true, true) + " can't use its sealed " + move.getName() + "!");
                    return false;
                }
            }
            if (activation == StatusActivation.OpponentTrySelectMove) {
                if (moveSealed) {
                    if (showMessages) {
                        System.out.println("\n!- " + move.getUser().getTrueName(false, false) + " can't use its sealed " + move.getName() + " -!\n");
                    }
                    return false;
                }
            }
            return true;
        },
        new StatusActivation[] {
            StatusActivation.TryAct,
            StatusActivation.OpponentTrySelectMove
        },
        StatusMessages.imprison
    );

    public static final StatusCondition curse = new StatusCondition(
        "Curse",
        true,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            int curseDamage = Integer.max(pokemon.getHP()/4, 1);
            String message = pokemon.getName(true, true) + " is afflicted by the curse!";
            Damage.indirectDamage(pokemon, thisCondition.getCauser(), curseDamage, DamageSource.StatusCondition, thisCondition, message, true);
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.curse
    );

    public static final StatusCondition roost = new StatusCondition(
        "Roost",
        true,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            if (pokemon.hasType(TypeList.flying)) {
                pokemon.getType(TypeList.flying).setSuppressed(false);
            }
            pokemon.endVolatileStatus(thisCondition, true);
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        null
    );

    public static final StatusCondition encore = new StatusCondition(
        "Encore",
        true,
        (thisCondition, pokemon, _, move, _, showMessages, activation) -> {
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            if (activation == StatusActivation.ChangeMove) {
                if (!move.isZMove() && !move.isZPowered()) {
                    if (!move.compare(MoveList.struggle) &&
                        pokemon.getLastUsedMove().getCurrentPP() > 0) {
                        return pokemon.getLastUsedMove();
                    } else {
                        if (move.compare(MoveList.struggle)) {
                            return move;
                        }
                        return new Move(MoveList.struggle, pokemon);
                    }
                }
                return move;
            }
            if (activation == StatusActivation.TrySelectMove) {
                if (move != pokemon.getLastUsedMove()) {
                    if (showMessages) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " must use " + pokemon.getLastUsedMove().getName() + " after the encore -!\n");
                    }
                    return false;
                }
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn,
            StatusActivation.ChangeMove,
            StatusActivation.TrySelectMove
        },
        StatusMessages.encore
    );

    public static final StatusCondition charge = new StatusCondition(
        "Charge",
        true,
        (thisCondition, pokemon, _, move, _, _, _) -> {
            if (move.getType(false, false).compare(TypeList.electric)) {
                pokemon.endVolatileStatus(thisCondition, true);
                return 2.0;
            }
            return 1.0;
        },
        new StatusActivation[] {
            StatusActivation.DamageCalc
        },
        null
    );

    public static final StatusCondition focus = new StatusCondition(
        "Focus",
        true,
        (thisCondition, pokemon, _, move, _, _, activation) -> {
            if (activation == StatusActivation.Hit) {
                if (move.getCategory() != Category.Status) {
                    thisCondition.setCounter(1);
                }
            }
            if (activation == StatusActivation.TryAct) {
                if (thisCondition.getCounter() == 1) {
                    System.out.println(pokemon.getName(true, true) + " lost its focus and couldn't move!");
                    return false;
                }
                return true;
            }
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.Hit,
            StatusActivation.TryAct,
            StatusActivation.EndOfTurn
        },
        StatusMessages.focus
    );

    public static final StatusCondition pumped = new StatusCondition(
        "Pumped",
        true,
        (_, _, _, _, _, _, _) -> {
            return 2;
        },
        new StatusActivation[] {
            StatusActivation.CritRatioCalc
        },
        StatusMessages.pumped
    );

    public static final StatusCondition locked = new StatusCondition(
        "Locked",
        true,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            thisCondition.setCounter(thisCondition.getCounter() - 1);
            if (thisCondition.getCounter() <= 0) {
                pokemon.setReadiedMove(null);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.UseMove
        },
        null
    );

    public static final StatusCondition defense_curl = new StatusCondition(
        "Defense Curl",
        true,
        null,
        new StatusActivation[0],
        null
    );

    public static final StatusCondition suppressed_ability = new StatusCondition(
        "Suppressed Ability",
        true,
        (_, pokemon, opponent, move, _, _, _) -> {
            if (pokemon.getAbility().shouldActivate(AbilityActivation.Removed)) {
                pokemon.getAbility().activate(pokemon, opponent, move, null, null, null, null, 0, AbilityActivation.Removed);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.Start
        },
        StatusMessages.suppressed_ability
    );

    public static final StatusCondition grounded = new StatusCondition(
        "Grounded",
        true,
        (_, pokemon, _, _, _, _, _) -> {
            if (pokemon.getVolatileStatus(StatusConditionList.magnet_rise) != null) {
                pokemon.endVolatileStatus(StatusConditionList.magnet_rise, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.Start
        },
        StatusMessages.grounded
    );

    public static final StatusCondition throat_chop = new StatusCondition(
        "Throat Chop",
        true,
        (thisCondition, pokemon, _, move, _, showMessages, activation) -> {
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            if (activation == StatusActivation.TryAct) {
                if (!move.isZMove() && !move.isZPowered() &&
                    Arrays.asList(move.getMoveTypes()).contains(MoveType.Sound)) {
                    System.out.println("The effects of Throat Chop prevent " + pokemon.getName(true, false) + " from using certain moves!");
                    return false;
                }
                return true;
            }
            if (activation == StatusActivation.TrySelectMove) {
                if (Arrays.asList(move.getMoveTypes()).contains(MoveType.Sound)) {
                    if (showMessages) {
                        System.out.println("\n!- The effects of Throat Chop prevent " + pokemon.getName(true, false) + " from using certain moves -!\n");
                    }
                    return false;
                }
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn,
            StatusActivation.TryAct,
            StatusActivation.TrySelectMove
        },
        StatusMessages.throat_chop
    );

    public static final StatusCondition trapped = new StatusCondition(
        "Trapped",
        true,
        (thisCondition, pokemon, _, _, _, _, activation) -> {
            if (activation == StatusActivation.CauserLeaveField) {
                pokemon.endVolatileStatus(thisCondition, false);
            }
            if (activation == StatusActivation.TrySwitch) {
                System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can't escape -!\n");
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.CauserLeaveField,
            StatusActivation.TrySwitch
        },
        StatusMessages.trapped
    );

    public static final StatusCondition move_disabled = new StatusCondition(
        "Move Disabled",
        true,
        (thisCondition, pokemon, _, move, _, showMessages, activation) -> {
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            if (activation == StatusActivation.TryAct) {
                if (!move.isZPowered() &&
                    move == thisCondition.getAffectedMove() &&
                    !move.getTemporaryProperties().contains(TemporaryProperty.Called)) {
                    System.out.println(pokemon.getName(true, true) + "'s " + move.getName() + " is disabled!");
                    return false;
                }
                return true;
            }
            if (activation == StatusActivation.TrySelectMove) {
                if (move == thisCondition.getAffectedMove()) {
                    if (showMessages) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + "'s " + move.getName() + " is disabled -!\n");
                    }
                    return false;
                }
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn,
            StatusActivation.TryAct,
            StatusActivation.TrySelectMove
        },
        StatusMessages.move_disabled
    );

    public static final StatusCondition perish_song = new StatusCondition(
        "Perish Song",
        true,
        (thisCondition, pokemon, _, _, _, _, _) -> {
            thisCondition.setCounter(thisCondition.getCounter() - 1);
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            System.out.println(pokemon.getName(true, true) + "'s perish count fell to " + thisCondition.getCounter() + "!");
            if (thisCondition.getCounter() <= 0) {
                pokemon.setCurrentHP(0);
                Battle.faintCheck(pokemon, true);
            }
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        null
    );

    public static final StatusCondition taking_aim = new StatusCondition(
        "Taking Aim",
        true,
        (thisCondition, pokemon, opponent, move, _, _, activation) -> {
            if (activation == StatusActivation.OpponentHitGuarantee) {
                if (thisCondition.getCauser() == opponent &&
                    move.getUser() == opponent &&
                    opponent.getVolatileStatus(thisCondition) != null) {
                    move.addProperty(TemporaryProperty.CantMiss);
                }
            }
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    pokemon.endVolatileStatus(thisCondition, thisCondition.getCauser(), true);
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.OpponentHitGuarantee,
            StatusActivation.EndOfTurn
        },
        true,
        StatusMessages.taking_aim
    );

    public static final StatusCondition aqua_ring = new StatusCondition(
        "Aqua Ring",
        true,
        (_, pokemon, _, _, _, _, _) -> {
            int healedDamage = Integer.max(pokemon.getHP()/16, 1);

            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            System.out.println("A veil of water restored " + pokemon.getName(true, false) + "'s HP!");
            Damage.heal(pokemon, null, healedDamage, true, false);
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn
        },
        StatusMessages.aqua_ring
    );

    public static final StatusCondition destiny_bond = new StatusCondition(
        "Destiny Bond",
        true,
        (thisCondition, pokemon, opponent, _, _, _, activation) -> {
            if (activation == StatusActivation.Faint) {
                System.out.println("\n" + pokemon.getName(true, true) + " took its attacker down with it!");
                opponent.setCurrentHP(0);
                Battle.faintCheck(opponent, true);
            }
            if (activation == StatusActivation.UseMove) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.Faint,
            StatusActivation.UseMove
        },
        StatusMessages.destiny_bond
    );

    public static final StatusCondition magic_coat = new StatusCondition(
        "Magic Coat",
        true,
        (thisCondition, pokemon, opponent, move, _, _, activation) -> {
            if (activation == StatusActivation.OpponentTryUseMoveTargeted) {
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
            }
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.OpponentTryUseMoveTargeted,
            StatusActivation.EndOfTurn
        },
        StatusMessages.magic_coat
    );

    public static final StatusCondition snatch = new StatusCondition(
        "Snatch",
        true,
        (thisCondition, pokemon, opponent, move, _, _, activation) -> {
            if (activation == StatusActivation.OpponentTryUseMoveAny) {
                if (move.targetsUser() &&
                    !move.isZPowered() &&
                    !move.hasInherentProperty(InherentProperty.NotSnatchable) &&
                    !move.getTemporaryProperties().contains(TemporaryProperty.Snatched)) {
                    System.out.println(pokemon.getName(true, true) + " snatched " + opponent.getName(true, false) + "'s move!");

                    Action moveAction = Battle.findAction(move, opponent);
                    Move copiedMove = new Move(move, pokemon);
                    copiedMove.addProperty(TemporaryProperty.Snatched);

                    Battle.addAction(new Action(copiedMove, pokemon, pokemon), moveAction);

                    return false;
                }
                return true;
            }
            if (activation == StatusActivation.EndOfTurn) {
                pokemon.endVolatileStatus(thisCondition, true);
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.OpponentTryUseMoveAny,
            StatusActivation.EndOfTurn
        },
        StatusMessages.snatch
    );

    public static final StatusCondition magnet_rise = new StatusCondition(
        "Magnet Rise",
        true,
        (thisCondition, pokemon, _, _, _, _, activation) -> {
            if (activation == StatusActivation.Start) {
                if (pokemon.getVolatileStatus(StatusConditionList.grounded) != null) {
                    pokemon.endVolatileStatus(StatusConditionList.grounded, true);
                }
            }
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    pokemon.endVolatileStatus(thisCondition, true);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.Start,
            StatusActivation.EndOfTurn
        },
        StatusMessages.magnet_rise
    );

    public static final StatusCondition ingrain = new StatusCondition(
        "Ingrain",
        true,
        (_, pokemon, _, _, _, _, activation) -> {
            if (activation == StatusActivation.EndOfTurn) {
                int healedDamage = Integer.max(pokemon.getHP()/16, 1);

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(pokemon.getName(true, false) + " absorbed nutrients with its roots!");
                Damage.heal(pokemon, null, healedDamage, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            if (activation == StatusActivation.TrySwitch) {
                System.out.println("\n!- " + pokemon.getTrueName(false, false) + " is anchored in place with its roots -!\n");
                return true;
            }

            if (activation == StatusActivation.TryForceSwitch) {
                System.out.println(pokemon.getName(true, true) + " is anchored in place with its roots!");
                return false;
            }

            return null;
        },
        new StatusActivation[] {
            StatusActivation.EndOfTurn,
            StatusActivation.TrySwitch,
            StatusActivation.TryForceSwitch
        },
        StatusMessages.ingrain
    );

    public static final StatusCondition laser_focus = new StatusCondition(
        "Laser Focus",
        true,
        (thisCondition, pokemon, _, _, _, _, activation) -> {
            if (activation == StatusActivation.CritRatioCalc) {
                return 10; // guaranteed
            }
            if (activation == StatusActivation.EndOfTurn) {
                if (thisCondition.getCounter() <= 0) {
                    pokemon.endVolatileStatus(thisCondition, true);
                } else {
                    thisCondition.setCounter(thisCondition.getCounter() - 1);
                }
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.CritRatioCalc,
            StatusActivation.EndOfTurn
        },
        StatusMessages.laser_focus
    );

    public static final StatusCondition infatuation = new StatusCondition(
        "Infatuation",
        true,
        (thisCondition, pokemon, _, _, _, _, activation) -> {
            if (activation == StatusActivation.CauserLeaveField) {
                pokemon.endVolatileStatus(thisCondition, false);
            }
            if (activation == StatusActivation.TryMove) {
                System.out.println(pokemon.getName(true, true) + " is in love with " + thisCondition.getCauser().getName(true, false) + "!");

                if (Math.random() < 0.5) {
                    System.out.println(pokemon.getName(true, true) + " is immobilized by love!");
                    return false;
                }
                return true;
            }
            return null;
        },
        new StatusActivation[] {
            StatusActivation.CauserLeaveField,
            StatusActivation.TryMove
        },
        StatusMessages.infatuation
    );
}