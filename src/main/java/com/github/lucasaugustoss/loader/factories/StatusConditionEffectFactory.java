package com.github.lucasaugustoss.loader.factories;

import java.util.Arrays;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.effectFunctions.StatusConditionEffectFunction;
import com.github.lucasaugustoss.data.objects.effects.StatusConditionEffect;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.loader.dtos.StatusConditionEffectDTO;
import com.github.lucasaugustoss.loader.factories.otherEffects.OtherStatusConditionEffects;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;

public class StatusConditionEffectFactory {
    public static StatusConditionEffect buildEffect(
        StatusConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        if (dto == null) {
            return null;
        }
        
        String type = dto.type;
        StatusActivation[] activation = FactoryTools.convertEnumArray(dto.activation, StatusActivation.class).toArray(new StatusActivation[0]);
        StatusConditionEffectFunction effect = null;

        switch (type) {
            case "chip_damage":
                effect = buildChipDamage(dto);
                break;

            case "chip_heal":
                effect = buildChipHeal(dto);
                break;

            case "stop_move":
                effect = buildStopMove(dto, typeMap);
                break;

            case "count_down":
                effect = buildCountDown(dto, statusConditionMap);
                break;

            case "block_move_selection":
                effect = buildBlockMoveSelection(dto, statusConditionMap);
                break;

            case "lock":
                effect = buildLock(dto, statusConditionMap);
                break;

            case "steal_move":
                effect = buildStealMove(dto, typeMap);
                break;

            case "modify_crit_ratio":
                effect = buildModifyCritRatio(dto);
                break;

            case "end_status":
                effect = buildEndStatus(dto, statusConditionMap);
                break;

            case "cause_faint":
                effect = buildCauseFaint(dto);
                break;

            case "other":
                effect = getOther(dto.otherID);
                break;

            default:
                return null;
        }

        return new StatusConditionEffect(type, activation, effect);
    }

    private static StatusConditionEffectFunction buildChipDamage(StatusConditionEffectDTO dto) {
        final double damageValue = FactoryTools.convertFraction(dto.damageFraction);
        final boolean damageIncreases = dto.damageIncreases;
        final boolean drain = dto.drain;

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            double totalDamage = damageValue;
            if (damageIncreases) {
                totalDamage *= thisCondition.getCounter();
            }
            int chipDamage = (int) Math.floor(pokemon.getHP()*totalDamage);
            chipDamage = Integer.max(chipDamage, 1);

            if (activation == StatusActivation.EndOfTurn) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            String message = thisCondition.getMessages().getMessage("chip damage", Map.of(
                "Pokemon", pokemon.getName(true, false),
                "Move", thisCondition.getCausingMove() != null ? thisCondition.getCausingMove().getName() : ""
            ));

            int finalDamage = Damage.indirectDamage(pokemon, thisCondition.getCauser(), chipDamage, DamageSource.StatusCondition, thisCondition, message, false).amount;

            if (drain) {
                if (opponent.getCurrentHP() < opponent.getHP()) {
                    if (Battle.faintCheck(pokemon, false)) {
                        System.out.println();
                    }

                    Damage.heal(opponent, null, finalDamage, true, false);
                }
            }

            if (damageIncreases &&
                !Battle.faintCheck(pokemon, false) &&
                thisCondition.getCounter() < 15) {
                thisCondition.setCounter(thisCondition.getCounter() + 1);
            }

            if (activation == StatusActivation.EndOfTurn) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        };
    }

    private static StatusConditionEffectFunction buildChipHeal(StatusConditionEffectDTO dto) {
        final double healValue = FactoryTools.convertFraction(dto.damageFraction);

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (pokemon.getCurrentHP() < pokemon.getHP()) {
                int healedDamage = Integer.max((int) Math.floor(pokemon.getHP()*healValue), 1);
    
                if (activation == StatusActivation.EndOfTurn) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
    
                thisCondition.getMessages().print("chip heal", Map.of(
                    "Pokemon", pokemon.getName(true, false)
                ));
    
                Damage.heal(pokemon, null, healedDamage, true, false);
    
                if (activation == StatusActivation.EndOfTurn) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            return null;
        };
    }

    private static StatusConditionEffectFunction buildStopMove(
        StatusConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final double chance = dto.chance != null ? FactoryTools.convertFraction(dto.chance) : 1;
        final InherentProperty ignoreStopProperty = FactoryTools.convertEnum(dto.ignoreStopProperty, InherentProperty.class);
        final boolean confusionHit = dto.confusionHit;
        final String specialStop = dto.specialStop != null ? dto.specialStop : "";

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            thisCondition.getMessages().print("try move", Map.of(
                "Pokemon", pokemon.getName(true, false),
                "Causer", thisCondition.getCauser() != null ? thisCondition.getCauser().getName(true, false) : ""
            ));

            if (Math.random() < chance) {
                if (move.hasInherentProperty(ignoreStopProperty)) {
                    return true;
                }

                boolean willStop;
                switch (specialStop) {
                    case "taunt":
                        willStop = !move.isZPowered() && move.getCategory() == Category.Status;
                        break;

                    case "imprison":
                        boolean moveSealed = false;
                        for (Move userMove : pokemon.getTrueMoves()) {
                            if (userMove != null && move.compareTrue(userMove)) {
                                moveSealed = true;
                                break;
                            }
                        }
                        willStop = moveSealed;
                        break;

                    case "focus":
                        willStop = thisCondition.getCounter() == 1;
                        break;

                    case "throat_chop":
                        willStop = !move.isZMove() && !move.isZPowered() &&
                                   Arrays.asList(move.getMoveTypes()).contains(MoveType.Sound);
                        break;

                    case "move_disabled":
                        willStop = !move.isZPowered() && move == thisCondition.getAffectedMove() &&
                                   !move.getTemporaryProperties().contains(TemporaryProperty.Called);
                        break;

                    default:
                        willStop = true;
                        break;
                }

                if (!willStop) {
                    return true;
                }

                thisCondition.getMessages().print("stop move", Map.of(
                    "Pokemon", move.getUser().getName(true, false),
                    "Move", move.getName()
                ));

                if (confusionHit) {
                    Move confusionMove = new Move(
                        new Move(
                            "",
                            typeMap.get("typeless"),
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

                    Damage.directDamage(pokemon, pokemon, confusionMove, true);
                }

                return false;
            }
            return true;
        };
    }

    private static StatusConditionEffectFunction buildCountDown(
        StatusConditionEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final String lastTurnEffect = dto.lastTurnEffect != null ? dto.lastTurnEffect : "";

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (thisCondition.getCounter() <= 0) {
                boolean printDividers = activation == StatusActivation.EndOfTurn && (
                                            thisCondition.getMessages().hasMessage("end") ||
                                            !lastTurnEffect.isEmpty()
                                        );

                if (printDividers) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                switch (lastTurnEffect) {
                    case "drowsiness":
                        statusConditionMap.get("sleep").apply(
                            pokemon, thisCondition.getCausingMove(), Map.of(
                                "Counter", (int) Math.floor(Math.random()*3)+1
                            ),
                            true, false
                        );
                        break;
                
                    default:
                        break;
                }

                if (!thisCondition.isVolatileCondition()) {
                    pokemon.endNonVolatileStatus(true);
                } else {
                    pokemon.endVolatileStatus(thisCondition, true);
                }

                if (printDividers) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            } else {
                boolean printDividers = activation == StatusActivation.EndOfTurn && (
                                            thisCondition.getMessages().hasMessage("count down") ||
                                            Arrays.asList(thisCondition.getActivation()).contains(StatusActivation.AfterCountDown)
                                        );

                thisCondition.setCounter(thisCondition.getCounter() - 1);

                if (printDividers) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                thisCondition.getMessages().print("count down", Map.of(
                    "Pokemon", pokemon.getName(true, false),
                    "Number", String.valueOf(thisCondition.getCounter())
                ));

                if (Arrays.asList(thisCondition.getActivation()).contains(StatusActivation.AfterCountDown)) {
                    Object result = thisCondition.activate(pokemon, opponent, move, damage, showMessages, StatusActivation.AfterCountDown);
                    if ((activation == StatusActivation.TryAct || activation == StatusActivation.TryMove) &&
                        result instanceof Boolean) {
                        return (boolean) result;
                    }
                }

                if (printDividers) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            return true;
        };
    }

    private static StatusConditionEffectFunction buildBlockMoveSelection(
        StatusConditionEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final String specialStop = dto.specialStop != null ? dto.specialStop : "";

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            Move printedMove = move;

            boolean willBlock;
            switch (specialStop) {
                case "taunt":
                    willBlock = move.getCategory() == Category.Status;
                    break;

                case "torment":
                    willBlock = pokemon.getLastUsedMove() == move &&
                                pokemon.getVolatileStatus(statusConditionMap.get("rampage")) == null &&
                                pokemon.getVolatileStatus(statusConditionMap.get("charging_turn")) == null &&
                                pokemon.getVolatileStatus(statusConditionMap.get("semi_invulnerable_charging_turn")) == null &&
                                pokemon.getVolatileStatus(statusConditionMap.get("recharging_turn")) == null;
                    break;

                case "imprison":
                    boolean moveSealed = false;
                    for (Move userMove : pokemon.getTrueMoves()) {
                        if (userMove != null && move.compareTrue(userMove)) {
                            moveSealed = true;
                            break;
                        }
                    }
                    willBlock = moveSealed;
                    break;

                case "encore":
                    willBlock = move != pokemon.getLastUsedMove();
                    printedMove = pokemon.getLastUsedMove();
                    break;

                case "throat_chop":
                    willBlock = Arrays.asList(move.getMoveTypes()).contains(MoveType.Sound);
                    break;

                case "affected_move":
                    willBlock = move == thisCondition.getAffectedMove();
                    break;

                default:
                    willBlock = true;
                    break;
            }

            if (!willBlock) {
                return true;
            }

            if (showMessages) {
                thisCondition.getMessages().print("block move selection", Map.of(
                    "Pokemon", move.getUser().getTrueName(false, false),
                    "Move", printedMove.getName()
                ));
            }

            return false;
        };
    }

    private static StatusConditionEffectFunction buildLock(
        StatusConditionEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final boolean fatigue = dto.fatigue;

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            thisCondition.setCounter(thisCondition.getCounter() - 1);
            if (thisCondition.getCounter() <= 0) {
                pokemon.setReadiedMove(null);
                if (fatigue && pokemon.getVolatileStatus(statusConditionMap.get("confusion")) == null) {
                    statusConditionMap.get("confusion").apply(
                        pokemon, move, Map.of(
                            "Counter", (int) Math.floor(Math.random()*4)+2,
                            "Causer", pokemon
                        ),
                        false, false
                    );
                    if (Battle.faintCheck(opponent, false)) {
                        System.out.println();
                    }
                    System.out.println(pokemon.getName(true, true) + " became confused due to fatigue!");
                }
            }
            return null;
        };
    }

    private static StatusConditionEffectFunction buildStealMove(
        StatusConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final String stealType = dto.stealType != null ? dto.stealType : "";

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            boolean willSteal = !move.isZPowered() &&
                                !move.hasInherentProperty(InherentProperty.NotSnatchable) &&
                                !move.getTemporaryProperties().contains(TemporaryProperty.Snatched);

            if (willSteal) {
                switch (stealType) {
                    case "hydrokinesis":
                        willSteal = move.getType(false, false).compare(typeMap.get("water")) && !move.isZMove();
                        break;
    
                    case "snatch":
                        willSteal = move.targetsUser();
                        break;
                
                    default:
                        break;
                }
            }

            if (willSteal) {
                thisCondition.getMessages().print("steal move", Map.of(
                    "Pokemon", pokemon.getName(true, false),
                    "Target", move.getUser().getName(true, false)
                ));

                Action moveAction = Battle.findAction(move, move.getUser());
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
        };
    }

    private static StatusConditionEffectFunction buildModifyCritRatio(StatusConditionEffectDTO dto) {
        final int modifier = dto.modifier;

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            return modifier;
        };
    }

    private static StatusConditionEffectFunction buildEndStatus(
        StatusConditionEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate status = FactoryTools.convertObject(dto.status, statusConditionMap);

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (pokemon.getVolatileStatus(status) != null) {
                pokemon.endVolatileStatus(status, true);
            }
            return null;
        };
    }

    private static StatusConditionEffectFunction buildCauseFaint(StatusConditionEffectDTO dto) {
        final String target = dto.target;

        return (thisCondition, pokemon, opponent, move, damage, showMessages, activation) -> {
            if (thisCondition.getCounter() == 0) {
                Pokemon faintTarget = target.equals("self") ? pokemon : opponent;
    
                thisCondition.getMessages().print("cause faint", Map.of(
                    "Pokemon", pokemon.getName(true, false)
                ));
    
                faintTarget.setCurrentHP(0);
                Battle.faintCheck(faintTarget, true);
            }
            return null;
        };
    }

    private static StatusConditionEffectFunction getOther(String otherID) {
        switch (otherID) {
            case "freeze":
                return OtherStatusConditionEffects.freeze;

            case "end":
                return OtherStatusConditionEffects.end;

            case "block_switch":
                return OtherStatusConditionEffects.block_switch;

            case "block_forced_switch":
                return OtherStatusConditionEffects.block_forced_switch;

            case "semi_invulnerable_charging_turn":
                return OtherStatusConditionEffects.semi_invulnerable_charging_turn;

            case "protection":
                return OtherStatusConditionEffects.protection;

            case "substitute":
                return OtherStatusConditionEffects.substitute;

            case "countering":
                return OtherStatusConditionEffects.countering;

            case "endure":
                return OtherStatusConditionEffects.endure;

            case "roost":
                return OtherStatusConditionEffects.roost;

            case "encore_move_change":
                return OtherStatusConditionEffects.encore_move_change;

            case "charge":
                return OtherStatusConditionEffects.charge;

            case "focus_break":
                return OtherStatusConditionEffects.focus_break;

            case "ability_removal":
                return OtherStatusConditionEffects.ability_removal;

            case "taking_aim":
                return OtherStatusConditionEffects.taking_aim;

            case "magic_coat":
                return OtherStatusConditionEffects.magic_coat;

            default:
                return null;
        }
    }
}
