package com.github.lucasaugustoss.loader.factories.otherEffects;

import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.FieldConditionEffectFunction;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.messages.MessageStorage;
import com.github.lucasaugustoss.data.objects.Data;
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
                    MessageHandler.add(thisCondition.getMessages().getName(), "activate", null);
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
                    MessageHandler.add(thisCondition.getMessages().getName(), "chip heal", Map.of(
                        "Pokemon", pokemon.getName(true, false)
                    ));
                }

                Damage.heal(pokemon, null, healedDamage, showMessage, false);
            }
            return null;
        };

    public static final FieldConditionEffectFunction spikes =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (activation == FieldActivation.Entry) {
                if (pokemon.isGrounded(null)) {
                    int damageAmount = 8-2*(thisCondition.getCounter()-1);
                    int damage = Integer.max(pokemon.getHP()/damageAmount, 1);

                    MessageStorage message = new MessageStorage(
                        thisCondition.getMessages().getName(), "chip damage", Map.of(
                            "Pokemon", pokemon.getName(true, false)
                        )
                    );

                    Damage.indirectDamage(pokemon, null, damage, 0, DamageSource.FieldCondition, thisCondition, message);
                }
            }
            if (activation == FieldActivation.Repeat) {
                if (thisCondition.getCounter() < 3) {
                    thisCondition.setCounter(thisCondition.getCounter() + 1);

                    MessageHandler.add(thisCondition.getMessages().getName(), "start", Map.of(
                        "Team", String.valueOf(pokemon.getTeam())
                    ));

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

            MessageStorage message = new MessageStorage(
                thisCondition.getMessages().getName(), "chip damage", Map.of(
                    "Pokemon", pokemon.getName(true, false)
                )
            );

            Damage.indirectDamage(pokemon, null, damage, 0, DamageSource.FieldCondition, thisCondition, message);

            return null;
        };

    public static final FieldConditionEffectFunction sticky_web =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (pokemon.isGrounded(null)) {
                MessageHandler.add(thisCondition.getMessages().getName(), "activate", Map.of(
                    "Pokemon", pokemon.getName(true, false)
                ));
                pokemon.getStat(StatName.Spe).change(-1, thisCondition, thisCondition.getCauser(), true, false);
            }
            return null;
        };

    public static final FieldConditionEffectFunction block_stat_drops =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (move.getUser().getAbility().compare(Data.get().getAbility("infiltrator"))) {
                return false;
            }

            if (statChangeStages < 0) {
                if (showMessages &&
                    thisCondition.getMessages() != null &&
                    thisCondition.getMessages().hasMessage("block stat drop")) {
                    MessageHandler.add(thisCondition.getMessages().getName(), "block stat drop", Map.of(
                        "Pokemon", pokemon.getName(true, false)
                    ));
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
            Pokemon statOwner = activation == FieldActivation.CallAttackingStat ? pokemon : opponent;

            if (stat.compare(Data.get().getStat("Def"))) {
                return statOwner.getStat(StatName.SpD);
            } else if (stat.compare(Data.get().getStat("SpD"))) {
                return statOwner.getStat(StatName.Def);
            }
            return stat;
        };

    public static final FieldConditionEffectFunction uproar_countdown =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (pokemon == thisCondition.getCauser()) {
                if (pokemon.getVolatileStatus(Data.get().getStatusCondition("locked")) != null) {
                    MessageHandler.add(thisCondition.getMessages().getName(), "continue", Map.of(
                        "Pokemon", pokemon.getName(true, false)
                    ));
                } else {
                    thisCondition.end();
                }
            }
            return null;
        };

    public static final FieldConditionEffectFunction gravity =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (activation == FieldActivation.Start) {
                for (Pokemon activePokemon : Battle.orderActivePokemonList()) {
                    StatusCondition chargeCondition = activePokemon.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn"));
                    // Bounce/Fly/Sky Drop
                    if (chargeCondition != null &&
                        (
                            chargeCondition.getCausingMove().compare(Data.get().getMove("bounce")) ||
                            chargeCondition.getCausingMove().compare(Data.get().getMove("fly"))
                        )) {
                        Action chargeAction = Battle.findAction(chargeCondition.getCausingMove(), activePokemon);
                        Battle.removeAction(chargeAction);
                        activePokemon.setReadiedMove(null);
                        activePokemon.endVolatileStatus(chargeCondition, false);

                        MessageHandler.add(thisCondition.getMessages().getName(), "end move", Map.of(
                            "Pokemon", activePokemon.getName(true, false)
                        ));
                    }
                }
            }

            if (activation == FieldActivation.TryAct) {
                if (move.hasInherentProperty(InherentProperty.GravityUnusable)) {
                    MessageHandler.add(thisCondition.getMessages().getName(), "block move", Map.of(
                        "Pokemon", pokemon.getName(true, false),
                        "Move", move.getName()
                    ));
                    return false;
                }
                return true;
            }

            return null;
        };

    public static final FieldConditionEffectFunction echoed_voice =
        (thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation) -> {
            if (Battle.findAction(Data.get().getMove("echoed_voice")) != null) {
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
