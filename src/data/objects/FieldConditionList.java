package data.objects;

import java.util.ArrayList;

import data.activationConditions.AbilityActivation;
import data.activationConditions.FieldActivation;
import data.classes.FieldCondition;
import data.classes.Pokemon;
import data.classes.Stat;
import data.classes.StatusCondition;
import data.classes.Type;
import data.messages.list.FieldMessages;
import data.properties.fieldConditions.FieldConditionType;
import data.properties.moves.Category;
import data.properties.moves.InherentProperty;
import data.properties.moves.MoveTarget;
import data.properties.stats.StatName;
import main.Battle;
import main.Damage;
import main.actions.Action;

public class FieldConditionList {
    // not actual conditions

    public static final FieldCondition placeholder = new FieldCondition(
        "Placeholder",
        FieldConditionType.OTHER,
        null,
        new FieldActivation[0],
        null
    );


    // weather

    public static final FieldCondition clear = new FieldCondition(
        "Clear",
        FieldConditionType.WEATHER,
        null,
        new FieldActivation[0],
        null
    );

    public static final FieldCondition sun = new FieldCondition(
        "Harsh Sunlight",
        FieldConditionType.WEATHER,
        (_, _, _, move, _, statusCondition, _, _, _, _, activation) -> {
            if (activation == FieldActivation.DamageCalcAtk) {
                if (move.getType(false).compare(TypeList.fire)) {
                    return 1.5;
                } else if (move.getType(false).compare(TypeList.water)) {
                    return 0.5;
                }
                return 1.0;
            }
            if (activation == FieldActivation.TryStatus) {
                if (statusCondition.compare(StatusConditionList.freeze)) {
                    return true;
                }
                return false;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcAtk,
            FieldActivation.TryStatus
        },
        FieldMessages.sun
    );

    public static final FieldCondition desolate_land = new FieldCondition(
        "Extremely Harsh Sunlight",
        FieldConditionType.WEATHER,
        (_, _, _, move, _, statusCondition, _, _, _, _, activation) -> {
            if (activation == FieldActivation.DamageCalcAtk) {
                if (move.getType(false).compare(TypeList.fire)) {
                    return 1.5;
                }
                return 1.0;
            }
            if (activation == FieldActivation.TryUseMove) {
                if (move.getType(false).compare(TypeList.water) &&
                    move.getCategory() != Category.Status) {
                    System.out.println("The Water-type attack evaporated in the harsh sunlight!");
                    return false;
                }
                return true;
            }
            if (activation == FieldActivation.TryStatus) {
                if (statusCondition.compare(StatusConditionList.freeze)) {
                    return true;
                }
                return false;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcAtk,
            FieldActivation.TryUseMove,
            FieldActivation.TryStatus
        },
        FieldMessages.desolate_land
    );

    public static final FieldCondition rain = new FieldCondition(
        "Rain",
        FieldConditionType.WEATHER,
        (_, _, _, move, _, _, _, _, _, _, _) -> {
            if (move.getType(false).compare(TypeList.water)) {
                return 1.5;
            } else if (move.getType(false).compare(TypeList.fire)) {
                return 0.5;
            }
            return 1.0;
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcAtk
        },
        FieldMessages.rain
    );

    public static final FieldCondition primordial_sea = new FieldCondition(
        "Heavy Rain",
        FieldConditionType.WEATHER,
        (_, _, _, move, _, _, _, _, _, _, activation) -> {
            if (activation == FieldActivation.DamageCalcAtk) {
                if (move.getType(false).compare(TypeList.water)) {
                    return 1.5;
                }
                return 1.0;
            }
            if (activation == FieldActivation.TryUseMove) {
                if (move.getType(false).compare(TypeList.fire) &&
                    move.getCategory() != Category.Status) {
                    System.out.println("The Fire-type attack fizzled out in the heavy rain!");
                    return false;
                }
                return true;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcAtk,
            FieldActivation.TryUseMove
        },
        FieldMessages.primordial_sea
    );

    public static final FieldCondition sand = new FieldCondition(
        "Sandstorm",
        FieldConditionType.WEATHER,
        (thisCondition, pokemon, _, move, _, _, _, _, _, _, activation) -> {
            if (activation == FieldActivation.EndOfTurn) {
                boolean immune = false;
                for (Type pokemonType : pokemon.getTypes()) {
                    if (!pokemonType.isSuppressed() &&
                        (pokemonType.compare(TypeList.ground) || pokemonType.compare(TypeList.rock) || pokemonType.compare(TypeList.steel))) {
                        immune = true;
                    }
                }
                if (pokemon.getAbility().shouldActivate(null) &&
                    (
                        pokemon.getAbility().compare(AbilityList.overcoat) ||
                        pokemon.getAbility().compare(AbilityList.sand_force)
                    )) {
                    immune = true;
                }
                if (!immune) {
                    int damage = Integer.max(pokemon.getHP()/16, 1);
                    String message = pokemon.getName(true, true) + " is buffeted by the sandstorm!";
                    Damage.indirectDamage(pokemon, null, damage, thisCondition, message, true);
                }
            }
            if (activation == FieldActivation.DefenseCalc) {
                if (pokemon.hasType(TypeList.rock) &&
                    move.getCategory() == Category.Special) {
                    return 1.5;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.EndOfTurn,
            FieldActivation.DefenseCalc
        },
        FieldMessages.sand
    );

    public static final FieldCondition snow = new FieldCondition(
        "Snow",
        FieldConditionType.WEATHER,
        (_, pokemon, _, move, _, _, _, _, _, _, _) -> {
            if (pokemon.hasType(TypeList.ice) &&
                move.getCategory() == Category.Physical) {
                return 1.5;
            }
            return 1.0;
        },
        new FieldActivation[] {
            FieldActivation.DefenseCalc
        },
        FieldMessages.snow
    );

    public static final FieldCondition delta_stream = new FieldCondition(
        "Strong Winds",
        FieldConditionType.WEATHER,
        (_, _, _, move, type, _, _, _, _, _, _) -> {
            if (type.compare(TypeList.flying)) {
                boolean weakToMove = false;
                for (Type moveType : move.getTypeList()) {
                    for (Type weakness : type.getSuperEffective(null, true)) {
                        if (moveType.compare(weakness)) {
                            weakToMove = true;
                            break;
                        }
                    }
                }

                if (weakToMove) {
                    System.out.println("The mysterious strong winds weakened the attack!");
                }
                return new Type[0];
            }

            return type.getSuperEffective(null, true);
        },
        new FieldActivation[] {
            FieldActivation.CallSuperEffective
        },
        FieldMessages.delta_stream
    );


    // terrain

    public static final FieldCondition no_terrain = new FieldCondition(
        "No Terrain",
        FieldConditionType.TERRAIN,
        null,
        new FieldActivation[0],
        null
    );

    public static final FieldCondition electric_terrain = new FieldCondition(
        "Electric Terrain",
        FieldConditionType.TERRAIN,
        (_, pokemon, _, move, _, statusCondition, _, _, _, showMessages, activation) -> {
            if (activation == FieldActivation.TryStatus) {
                if (statusCondition.compare(StatusConditionList.sleep) || statusCondition.compare(StatusConditionList.drowsiness)) {
                    if (showMessages) {
                        System.out.println(pokemon.getName(true, true) + " is protected by the Electric Terrain!");
                    }
                    return true;
                }
                return false;
            }
            if (activation == FieldActivation.DamageCalcAtk) {
                if (move.getType(false).compare(TypeList.electric)) {
                    return 1.3;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.TryStatus,
            FieldActivation.DamageCalcAtk
        },
        FieldMessages.electric_terrain
    );

    public static final FieldCondition grassy_terrain = new FieldCondition(
        "Grassy Terrain",
        FieldConditionType.TERRAIN,
        (_, pokemon, _, move, _, _, _, _, _, _, activation) -> {
            if (activation == FieldActivation.EndOfTurn) {
                if (pokemon.getCurrentHP() < pokemon.getHP()) {
                    int healedDamage = Integer.max(pokemon.getHP()/16, 1);

                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    System.out.println("The Grassy Terrain restored " + pokemon.getName(true, false) + "'s health!");
                    Damage.heal(pokemon, null, healedDamage, true, false);
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            if (activation == FieldActivation.DamageCalcAtk) {
                if (move.getType(false).compare(TypeList.grass)) {
                    return 1.3;
                }
                return 1.0;
            }
            if (activation == FieldActivation.DamageCalcDef) {
                // Bulldoze, Earthquake, Magnitude
                if (move.compare(MoveList.bulldoze) ||
                    move.compare(MoveList.earthquake)) {
                    return 0.5;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.EndOfTurn,
            FieldActivation.DamageCalcAtk,
            FieldActivation.DamageCalcDef
        },
        FieldMessages.grassy_terrain
    );

    public static final FieldCondition misty_terrain = new FieldCondition(
        "Misty Terrain",
        FieldConditionType.TERRAIN,
        (_, pokemon, _, move, _, statusCondition, _, _, _, showMessages, activation) -> {
            if (activation == FieldActivation.TryStatus) {
                if (!statusCondition.isVolatileCondition() || statusCondition.compare(StatusConditionList.confusion)) {
                    if (showMessages) {
                        System.out.println(pokemon.getName(true, true) + " surrounds itself with a protective mist!");
                    }
                    return true;
                }
                return false;
            }
            if (activation == FieldActivation.DamageCalcDef) {
                if (move.getType(false).compare(TypeList.dragon)) {
                    return 0.5;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.TryStatus,
            FieldActivation.DamageCalcDef
        },
        FieldMessages.misty_terrain
    );

    public static final FieldCondition psychic_terrain = new FieldCondition(
        "Psychic Terrain",
        FieldConditionType.TERRAIN,
        (_, pokemon, opponent, move, _, _, _, _, _, _, activation) -> {
            if (activation == FieldActivation.TryUseMove) {
                if (move.getUser() == opponent &&
                    move.targetsOpponent() &&
                    opponent.getTeam() != pokemon.getTeam() &&
                    move.getPriority() > 0) {
                    System.out.println(pokemon.getName(true, true) + " is protected by the Psychic Terrain!");
                    return false;
                }
                return true;
            }
            if (activation == FieldActivation.DamageCalcAtk) {
                if (move.getType(false).compare(TypeList.psychic)) {
                    return 1.3;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.TryUseMove,
            FieldActivation.DamageCalcAtk
        },
        FieldMessages.psychic_terrain
    );


    // entry hazards

    public static final FieldCondition spikes = new FieldCondition(
        "Spikes",
        FieldConditionType.ENTRY_HAZARD,
        (thisCondition, pokemon, _, _, _, _, _, _, _, _, activation) -> {
            if (activation == FieldActivation.Entry) {
                if (pokemon.isGrounded(null)) {
                    int damageAmount = 8-2*(thisCondition.getCounter()-1);
                    int damage = Integer.max(pokemon.getHP()/damageAmount, 1);
                    String message = pokemon.getName(true, true) + " was hurt by the spikes!";
                    Damage.indirectDamage(pokemon, null, damage, thisCondition, message, true);
                }
            }
            if (activation == FieldActivation.Repeat) {
                if (thisCondition.getCounter() < 3) {
                    thisCondition.setCounter(thisCondition.getCounter() + 1);
                    thisCondition.getMessages().print("start", pokemon.getTeam());
                    return true;
                }
                return false;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.Entry,
            FieldActivation.Repeat
        },
        true,
        FieldMessages.spikes
    );

    public static final FieldCondition stealth_rock = new FieldCondition(
        "Stealth Rock",
        FieldConditionType.ENTRY_HAZARD,
        (thisCondition, pokemon, _, _, _, _, _, _, _, _, _) -> {
            int damageAmount = 8;

            damageAmount /= Damage.superEffective(TypeList.rock, pokemon);
            damageAmount *= Damage.notVeryEffective(TypeList.rock, pokemon);

            int damage = Integer.max(pokemon.getHP()/damageAmount, 1);
            String message = "Pointed stones dug into " + pokemon.getName(true, false) + "!";
            Damage.indirectDamage(pokemon, null, damage, thisCondition, message, true);

            return null;
        },
        new FieldActivation[] {
            FieldActivation.Entry
        },
        FieldMessages.stealth_rock
    );

    public static final FieldCondition sticky_web = new FieldCondition(
        "Sticky Web",
        FieldConditionType.ENTRY_HAZARD,
        (thisCondition, pokemon, _, _, _, _, _, _, _, _, _) -> {
            if (pokemon.isGrounded(null)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(pokemon.getName(true, true) + " was caught in a sticky web!");
                pokemon.getStat(StatName.Spe).change(-1, thisCondition, false, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.Entry
        },
        FieldMessages.sticky_web
    );


    // other

    public static final FieldCondition mist = new FieldCondition(
        "Mist",
        FieldConditionType.OTHER,
        (_, pokemon, _, _, _, _, _, statChangeStages, _, showMessages, _) -> {
            if (statChangeStages < 0) {
                if (showMessages) {
                    System.out.println(pokemon.getName(true, true) + " is protected by the mist!");
                }
                return true;
            }
            return false;
        },
        new FieldActivation[] {
            FieldActivation.TryStatChange
        },
        FieldMessages.mist
    );

    public static final FieldCondition safeguard = new FieldCondition(
        "Safeguard",
        FieldConditionType.OTHER,
        (_, pokemon, _, _, _, statusCondition, _, _, _, showMessages, _) -> {
            if (!statusCondition.isVolatileCondition() || statusCondition.compare(StatusConditionList.confusion)) {
                if (showMessages) {
                    System.out.println(pokemon.getName(true, true) + " is protected by Safeguard!");
                }
                return true;
            }
            return false;
        },
        new FieldActivation[] {
            FieldActivation.TryStatus
        },
        FieldMessages.safeguard
    );

    public static final FieldCondition tailwind = new FieldCondition(
        "Tailwind",
        FieldConditionType.OTHER,
        (_, _, _, _, _, _, _, _, _, _, _) -> {
            return 2.0;
        },
        new FieldActivation[] {
            FieldActivation.SpeedCalc
        },
        FieldMessages.tailwind
    );

    public static final FieldCondition reflect = new FieldCondition(
        "Reflect",
        FieldConditionType.OTHER,
        (_, _, _, move, _, _, _, _, criticalHit, _, _) -> {
            if (!criticalHit &&
                move.getCategory() == Category.Physical) {
                return 0.5; // 2732/4096 em doubles
            }
            return 1.0;
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcDef
        },
        FieldMessages.reflect
    );

    public static final FieldCondition light_screen = new FieldCondition(
        "Light Screen",
        FieldConditionType.OTHER,
        (_, _, _, move, _, _, _, _, criticalHit, _, _) -> {
            if (!criticalHit &&
                move.getCategory() == Category.Special) {
                return 0.5; // 2732/4096 em doubles
            }
            return 1.0;
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcDef
        },
        FieldMessages.light_screen
    );

    public static final FieldCondition aurora_veil = new FieldCondition(
        "Aurora Veil",
        FieldConditionType.OTHER,
        (_, _, opponent, move, _, _, _, _, criticalHit, _, _) -> {
            boolean hasReflect = false;
            boolean hasLightScreen = false;
            for (FieldCondition fieldCondition : Battle.teamFields.get(opponent.getTeam())) {
                if (fieldCondition.compare(FieldConditionList.reflect)) {
                    hasReflect = true;
                }
                if (fieldCondition.compare(FieldConditionList.light_screen)) {
                    hasLightScreen = true;
                }
            }

            if (criticalHit ||
                hasReflect && move.getCategory() == Category.Physical ||
                hasLightScreen && move.getCategory() == Category.Special) {
                return 1.0;
            }
            return 0.5; // 2732/4096 em doubles
        },
        new FieldActivation[] {
            FieldActivation.DamageCalcDef
        },
        FieldMessages.aurora_veil
    );

    public static final FieldCondition quick_guard = new FieldCondition(
        "Quick Guard",
        FieldConditionType.OTHER,
        (thisCondition, pokemon, opponent, move, _, _, _, _, _, _, activation) -> {
            boolean affected = move != null && move.targetsOpponent() && move.getPriority() > 0;

            if (activation == FieldActivation.OpponentTryUseMove) {
                if (opponent.getAbility().shouldActivate(AbilityActivation.OpponentTryProtect) &&
                    !((boolean) opponent.getAbility().activate(opponent, pokemon, move, null, 0, null, null, 0, AbilityActivation.OpponentTryProtect))) {
                    return true;
                }

                if (affected &&
                    !move.isZMove() &&
                    !move.hasInherentProperty(InherentProperty.IgnoresProtection)) {
                    System.out.println(pokemon.getName(true, true) + " protected itself!");
                    return false;
                }
                if (move.hasInherentProperty(InherentProperty.BreaksProtection)) {
                    ArrayList<FieldCondition> field = Battle.teamFields.get(pokemon.getTeam());
                    for (FieldCondition fieldCondition : field) {
                        if (fieldCondition == thisCondition) {
                            fieldCondition.end(field);
                            break;
                        }
                    }

                    String team = pokemon.getTeam() == 0 ? "your" : "the opposing";
                    System.out.println("It broke through " + team + " team's protection!");
                }
                return true;
            }
            if (activation == FieldActivation.DamageCalcDef) {
                if (move.isZMove() && affected) {
                    System.out.println(pokemon.getName(true, true) + " couldn't fully protect itself and got hurt!");
                    return 0.25;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.OpponentTryUseMove,
            FieldActivation.DamageCalcDef
        },
        FieldMessages.quick_guard
    );

    public static final FieldCondition wide_guard = new FieldCondition(
        "Wide Guard",
        FieldConditionType.OTHER,
        (thisCondition, pokemon, opponent, move, _, _, _, _, _, _, activation) -> {
            boolean affected = move != null && (move.getMoveTarget() == MoveTarget.AllOpponents || move.getMoveTarget() == MoveTarget.AllAdjacent);

            if (activation == FieldActivation.OpponentTryUseMove) {
                if (opponent.getAbility().shouldActivate(AbilityActivation.OpponentTryProtect) &&
                    !((boolean) opponent.getAbility().activate(opponent, pokemon, move, null, 0, null, null, 0, AbilityActivation.OpponentTryProtect))) {
                    return true;
                }

                if (affected &&
                    !move.isZMove() &&
                    !move.hasInherentProperty(InherentProperty.IgnoresProtection)) {
                    System.out.println(pokemon.getName(true, true) + " protected itself!");
                    return false;
                }
                if (move.hasInherentProperty(InherentProperty.BreaksProtection)) {
                    ArrayList<FieldCondition> field = Battle.teamFields.get(pokemon.getTeam());
                    for (FieldCondition fieldCondition : field) {
                        if (fieldCondition == thisCondition) {
                            fieldCondition.end(field);
                            break;
                        }
                    }

                    String team = pokemon.getTeam() == 0 ? "your" : "the opposing";
                    System.out.println("It broke through " + team + " team's protection!");
                }
                return true;
            }
            if (activation == FieldActivation.DamageCalcDef) {
                if (move.isZMove() && affected) {
                    System.out.println(pokemon.getName(true, true) + " couldn't fully protect itself and got hurt!");
                    return 0.25;
                }
                return 1.0;
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.OpponentTryUseMove,
            FieldActivation.DamageCalcDef
        },
        FieldMessages.wide_guard
    );

    public static final FieldCondition magic_room = new FieldCondition(
        "Magic Room",
        FieldConditionType.OTHER,
        (thisCondition, _, _, _, _, _, _, _, _, _, activation) -> {
            if (activation == FieldActivation.TryUseItem) {
                return false;
            }

            if (activation == FieldActivation.Repeat) {
                thisCondition.end();
                return true;
            }

            return null;
        },
        new FieldActivation[] {
            FieldActivation.TryUseItem,
            FieldActivation.Repeat
        },
        FieldMessages.magic_room
    );

    public static final FieldCondition uproar = new FieldCondition(
        "Uproar",
        FieldConditionType.OTHER,
        (thisCondition, pokemon, _, _, _, statusCondition, _, _, _, showMessages, activation) -> {
            if (activation == FieldActivation.TryStatus) {
                if (statusCondition.compare(StatusConditionList.sleep)) {
                    if (showMessages) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        System.out.println("But the uproar kept " + pokemon.getName(true, false) + " awake!");
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                    return true;
                }
                return false;
            }
            if (activation == FieldActivation.EndOfTurn) {
                if (pokemon == thisCondition.getCauser()) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    if (pokemon.getVolatileStatus(StatusConditionList.locked) != null) {
                        System.out.println(pokemon.getName(true, true) + " is making an uproar!");
                    } else {
                        thisCondition.end();
                    }
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            return null;
        },
        new FieldActivation[] {
            FieldActivation.TryStatus,
            FieldActivation.EndOfTurn
        },
        FieldMessages.uproar
    );

    public static final FieldCondition trick_room = new FieldCondition(
        "Trick Room",
        FieldConditionType.OTHER,
        (thisCondition, _, _, _, _, _, _, _, _, _, _) -> {
            // cálculo de velocidade é feito em Battle
            thisCondition.end();
            return true;
        },
        new FieldActivation[] {
            FieldActivation.Repeat
        },
        FieldMessages.trick_room
    );

    public static final FieldCondition gravity = new FieldCondition(
        "Gravity",
        FieldConditionType.OTHER,
        (_, pokemon, _, move, _, _, _, _, _, showMessages, activation) -> {
            if (activation == FieldActivation.Start) {
                for (Pokemon activePokemon : Battle.orderPokemon(Battle.yourActivePokemon, Battle.opponentActivePokemon)) {
                    StatusCondition chargeCondition = activePokemon.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);
                    // Fly/Bounce/Sky Drop
                    if (chargeCondition != null &&
                        (
                            chargeCondition.getCausingMove().compare(MoveList.fly) ||
                            chargeCondition.getCausingMove().compare(MoveList.bounce)
                        )) {
                        Action chargeAction = Battle.findAction(chargeCondition.getCausingMove(), activePokemon);
                        Battle.removeAction(chargeAction);
                        activePokemon.setReadiedMove(null);
                        activePokemon.endVolatileStatus(chargeCondition, false);

                        System.out.println(activePokemon.getName(true, true) + " fell from the sky due to the gravity!");
                    }
                }
            }

            if (activation == FieldActivation.TryAct) {
                if (move.hasInherentProperty(InherentProperty.GravityUnusable)) {
                    System.out.println(pokemon.getTrueName(false, false) + " can't use " + move.getName() + " because of gravity!");
                    return false;
                }
                return true;
            }

            if (activation == FieldActivation.TrySelectMove) {
                if (move.hasInherentProperty(InherentProperty.GravityUnusable)) {
                    if (showMessages) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can't use " + move.getName() + " because of gravity -!\n");
                    }
                    return false;
                }
                return true;
            }

            if (activation == FieldActivation.AccuracyCalc) {
                return 6840.0/4096.0;
            }

            return null;
        },
        new FieldActivation[] {
            FieldActivation.Start,
            FieldActivation.TryAct,
            FieldActivation.TrySelectMove,
            FieldActivation.AccuracyCalc
        },
        FieldMessages.gravity
    );

    public static final FieldCondition wonder_room = new FieldCondition(
        "Wonder Room",
        FieldConditionType.OTHER,
        (thisCondition, pokemon, _, _, _, _, stat, _, _, _, activation) -> {
            if (activation == FieldActivation.CallStatValue) {
                if (stat.compare(Stat.def)) {
                    return pokemon.getStat(StatName.SpD).getValue();
                } else if (stat.compare(Stat.spd)) {
                    return pokemon.getStat(StatName.Def).getValue();
                }
                return stat.getValue();
            }

            if (activation == FieldActivation.Repeat) {
                thisCondition.end();
                return true;
            }

            return null;
        },
        new FieldActivation[] {
            FieldActivation.CallStatValue,
            FieldActivation.Repeat
        },
        FieldMessages.wonder_room
    );

    public static final FieldCondition echoed_voice = new FieldCondition(
        "Echoed Voice",
        FieldConditionType.OTHER,
        (thisCondition, _, _, _, _, _, _, _, _, _, _) -> {
            if (Battle.findAction(MoveList.echoed_voice) != null) {
                if (thisCondition.getCounter() < 5) {
                    thisCondition.setCounter(thisCondition.getCounter() + 1);
                }
            } else {
                thisCondition.end();
            }
            return null;
        },
        new FieldActivation[] {
            FieldActivation.EndOfTurnOnce
        },
        true,
        null
    );

    public static final FieldCondition ion_deluge = new FieldCondition(
        "Ion Deluge",
        FieldConditionType.OTHER,
        (_, _, _, _, type, _, _, _, _, _, _) -> {
            if (type.compare(TypeList.normal)) {
                return TypeList.electric;
            }
            return type;
        },
        new FieldActivation[] {
            FieldActivation.CallType
        },
        FieldMessages.ion_deluge
    );
}