package com.github.lucasaugustoss.loader.factories.otherEffects;

import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.FieldConditionEffectFunction;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.oldObjects.MoveList;
import com.github.lucasaugustoss.data.objects.oldObjects.StatusConditionList;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;

public class OtherFieldConditionEffects {
    public static final FieldConditionEffectFunction delta_stream =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (type.compare(Data.get().getType("flying"))) {
                boolean weakToMove = false;
                for (Type moveType : move.getTypeList()) {
                    for (TypeTemplate weakness : type.getSuperEffective(null, true)) {
                        if (moveType.compare(weakness)) {
                            weakToMove = true;
                            break;
                        }
                    }
                }

                if (weakToMove) {
                    System.out.println("The mysterious strong winds weakened the attack!");
                }
                return new TypeTemplate[0];
            }

            return type.getSuperEffective(null, true);
        };

    public static final FieldConditionEffectFunction chip_heal =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (pokemon.getCurrentHP() < pokemon.getHP()) {
                int healedDamage = Integer.max(pokemon.getHP()/16, 1);

                boolean showMessage = showMessages &&
                                      thisCondition.getMessages() != null &&
                                      thisCondition.getMessages().hasMessage("chip heal");

                if (showMessage) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    thisCondition.getMessages().print("chip heal", pokemon);
                }

                Damage.heal(pokemon, null, healedDamage, showMessage, false);

                if (showMessage) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        };

    public static final FieldConditionEffectFunction spikes =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (activation == FieldActivation.Entry) {
                if (pokemon.isGrounded(null)) {
                    int damageAmount = 8-2*(thisCondition.getCounter()-1);
                    int damage = Integer.max(pokemon.getHP()/damageAmount, 1);
                    String message = pokemon.getName(true, true) + " was hurt by the spikes!";
                    Damage.indirectDamage(pokemon, null, damage, DamageSource.FieldCondition, thisCondition, message, true);
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
        };

    public static final FieldConditionEffectFunction stealth_rock =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            int damageAmount = 8;

            damageAmount /= Damage.superEffective(Data.get().getType("rock"), pokemon);
            damageAmount *= Damage.notVeryEffective(Data.get().getType("rock"), pokemon);

            int damage = Integer.max(pokemon.getHP()/damageAmount, 1);
            String message = "Pointed stones dug into " + pokemon.getName(true, false) + "!";
            Damage.indirectDamage(pokemon, null, damage, DamageSource.FieldCondition, thisCondition, message, true);

            return null;
        };

    public static final FieldConditionEffectFunction sticky_web =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (pokemon.isGrounded(null)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(pokemon.getName(true, true) + " was caught in a sticky web!");
                pokemon.getStat(StatName.Spe).change(-1, thisCondition, false, true, false);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        };

    public static final FieldConditionEffectFunction block_stat_drops =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (statChangeStages < 0) {
                if (showMessages &&
                    thisCondition.getMessages() != null &&
                    thisCondition.getMessages().hasMessage("block stat drop")) {
                    thisCondition.getMessages().print("block stat drop", pokemon);
                }
                return true;
            }
            return false;
        };

    public static final FieldConditionEffectFunction cancel =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            thisCondition.end();
            return true;
        };

    public static final FieldConditionEffectFunction magic_room =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            return false;
        };

    public static final FieldConditionEffectFunction wonder_room =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (stat.compare(Data.get().getStat("Def"))) {
                return pokemon.getStat(StatName.SpD).getValue();
            } else if (stat.compare(Data.get().getStat("SpD"))) {
                return pokemon.getStat(StatName.Def).getValue();
            }
            return stat.getValue();
        };

    public static final FieldConditionEffectFunction uproar_countdown =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (pokemon == thisCondition.getCauser()) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                if (pokemon.getVolatileStatus(StatusConditionList.locked) != null) {
                    System.out.println(pokemon.getName(true, true) + " is making an uproar!");
                } else {
                    thisCondition.end();
                }
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        };

    public static final FieldConditionEffectFunction gravity =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
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
            return null;
        };

    public static final FieldConditionEffectFunction echoed_voice =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (Battle.findAction(MoveList.echoed_voice) != null) {
                if (thisCondition.getCounter() < 5) {
                    thisCondition.setCounter(thisCondition.getCounter() + 1);
                }
            } else {
                thisCondition.end();
            }
            return null;
        };

    public static final FieldConditionEffectFunction ion_deluge =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (type.compare(Data.get().getType("normal"))) {
                return new Type(Data.get().getType("electric"), move);
            }
            return type;
        };
}
