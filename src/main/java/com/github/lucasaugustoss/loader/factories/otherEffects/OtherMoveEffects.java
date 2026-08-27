package com.github.lucasaugustoss.loader.factories.otherEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.MoveEffectFunction;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.messages.MessageStorage;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;
import com.github.lucasaugustoss.simulator.actions.PriorityBracket;

public class OtherMoveEffects {
    public static final MoveEffectFunction[] after_you = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Action targetAction = Battle.findAction(target, false);

            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {!Battle.actionIsAfterOther(user.getCurrentAction(), targetAction), true};
            }

            if (condition == MoveEffectActivation.AfterMove) {
                Battle.moveAction(targetAction, user.getCurrentAction());

                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Target", target.getName(true, false)
                ));
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] remove_status_team = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            MessageHandler.add(thisMove.getMessages().getName(), "use", null);
            for (Pokemon pokemon : Battle.teams.get(user.getTeam())) {
                if (pokemon != null) {
                    pokemon.endNonVolatileStatus(true);
                }
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] autotomize = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            user.setWeight(user.getTrueWeight() - 100);
            MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                "Pokemon", user.getName(true, false)
            ));
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] baton_pass = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Move switchMove = new Move(Data.get().getMove("_switch_"), user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            switchMove.addProperty(TemporaryProperty._TransferValues_);
            Battle.addAction(new Action(switchMove, user, user), user.getCurrentAction());
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] beat_up = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            ArrayList<Pokemon> attackers = new ArrayList<>();
            for (Pokemon pokemon : Battle.teams.get(user.getTeam())) {
                if (pokemon != null &&
                    !Battle.faintCheck(pokemon, null, false) &&
                    (pokemon == user || pokemon.getNonVolatileStatus().compare(Data.get().getStatusCondition("none")))) {
                    attackers.add(pokemon);
                }
            }

            if (condition == MoveEffectActivation.CallHits) {
                return attackers.size();
            }

            if (condition == MoveEffectActivation.CallPower) {
                return (attackers.get(hit).getBaseStat(StatName.Atk)/10.0) + 5;
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallHits) {
                return 0;
            } else if (condition == MoveEffectActivation.CallPower) {
                return 0.0;
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] move_illusion = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.getAbility().compare(Data.get().getAbility("illusion")) &&
                user.getAbility().isActive()) {
                Pokemon pokemonDisguise = (Pokemon) user.getAbility().activate(user, target, thisMove, null, null, 0, null, null, 0, showMessages, AbilityActivation.CallUserData);

                Move moveDisguise = null;
                for (Move move : pokemonDisguise.getMoves()) {
                    if (move != null &&
                        move.getCategory() != Category.Status) {
                        moveDisguise = move;
                        break;
                    }
                }

                if (moveDisguise != null) {
                    return moveDisguise;
                }
            }

            return thisMove;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return thisMove;
        }
    };

    public static final MoveEffectFunction[] eat_berry = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (target.getAbility().shouldActivate(thisMove, AbilityActivation.TryRemoveItem) &&
                !((boolean) target.getAbility().activate(target, user, thisMove, null, null, 0, null, null, 0, true, AbilityActivation.TryRemoveItem))) {
                return null;
            }

            if (target.getItem().getType() == ItemType.Berry) {
                MessageHandler.add(thisMove.getMessages().getName(), "remove item", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Item", target.getItem().getName()
                ));

                target.getItem().activate(target, user, target, thisMove, null, ItemActivation.ForceUse);

                if (target.getItem().shouldActivate(ItemActivation.Consumed)) {
                    target.getItem().activate(target, user, target, thisMove, null, ItemActivation.Consumed);
                }
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] captivate = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (!user.getGender().equals("Unknown") &&
                !target.getGender().equals("Unknown") &&
                !target.getGender().equals(user.getGender())) {
                target.getStat(StatName.SpA).change(-2, thisMove, user, true, false);
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] boost_super_effective_damage = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(thisMove, target);
            effectivenessMultiplier /= Damage.notVeryEffective(thisMove, target);

            if (effectivenessMultiplier > 1) {
                return 5461.0/4096.0;
            }
            return 1.0;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return 1.0;
        }
    };

    public static final MoveEffectFunction[] copycat = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Move copiedMove = null;
            if (Battle.lastUsedMove != null &&
                !Battle.lastUsedMove.hasInherentProperty(InherentProperty.CopycatFails)) {
                if (Battle.lastUsedMove.isZMove()) {
                    copiedMove = new Move(Battle.lastUsedMove, Battle.lastUsedMove.getMoveOrigin(), user);
                } else {
                    copiedMove = new Move(Battle.lastUsedMove, user);
                }
            }
            if (thisMove.isZPowered() &&
                copiedMove != null &&
                copiedMove.getCategory() != Category.Status) {
                copiedMove = new Move(copiedMove.turnZMove(), copiedMove, user);
            }

            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {copiedMove != null, true};
            }

            if (condition == MoveEffectActivation.AfterMove) {
                MessageHandler.add(thisMove.getMessages().getName(), "call move", Map.of(
                    "Source", (thisMove.isZPowered() ? "Z-" : "") + thisMove.getName(),
                    "Move", copiedMove.getName()
                ));

                for (TemporaryProperty temporaryProperties : thisMove.getTemporaryProperties()) {
                    copiedMove.addProperty(temporaryProperties);
                }
                copiedMove.addProperty(TemporaryProperty.Called);

                Pokemon moveTarget;
                if (copiedMove.targetsOpponent()) {
                    moveTarget = Battle.getOpposingPokemon(user.getTeam());
                } else {
                    moveTarget = Battle.getActivePokemon(user.getTeam());
                }

                Battle.addAction(new Action(copiedMove, user, moveTarget), user.getCurrentAction());
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] core_enforcer = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Action targetAction = Battle.findAction(target, true);

            if (Battle.actionIsAfterOther(user.getCurrentAction(), targetAction) &&
                !target.getAbility().isNotSuppressable()) {
                Data.get().getStatusCondition("suppressed_ability").apply(target, thisMove, null, true, false);
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] court_change = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            ArrayList<FieldCondition> temp = new ArrayList<>(Battle.teamFields.get(0));
            Battle.teamFields.get(0).clear();
            Battle.teamFields.get(0).addAll(Battle.teamFields.get(1));
            Battle.teamFields.get(1).clear();
            Battle.teamFields.get(1).addAll(temp);

            MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                "Pokemon", user.getName(true, false)
            ));

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] curse = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean ghostUser = false;
            for (Type userType : user.getTypes()) {
                if (userType.compare(Data.get().getType("ghost"))) {
                    ghostUser = true;
                    break;
                }
            }

            if (condition == MoveEffectActivation.CallMoveTarget) {
                if (!ghostUser) {
                    return MoveTarget.User;
                }
                return MoveTarget.Normal;
            } else if (condition == MoveEffectActivation.CallEffectTarget) {
                if (!ghostUser) {
                    return EffectTarget.User;
                }
                return EffectTarget.Target;
            } else {
                Pokemon targetPokemon = thisEffect.getTarget() == EffectTarget.User ? user : target;

                Pokemon pokemon;
                if (condition != MoveEffectActivation.TryUse) {
                    pokemon = targetPokemon;
                } else {
                    pokemon = new Pokemon(targetPokemon, true);
                }

                boolean[] success = new boolean[3];
                if (ghostUser) {
                    success = Data.get().getStatusCondition("curse").apply(pokemon, thisMove, null, true, false);

                    if (success[0] && condition != MoveEffectActivation.TryUse) {
                        int remainingHP = Integer.max(user.getCurrentHP() - user.getHP()/2, 0);
                        user.setCurrentHP(remainingHP);

                        Battle.faintCheck(user, null, true);
                    }
                } else {
                    boolean speDrop = pokemon.getStat(StatName.Spe).change(-1, thisMove, user, true, false);
                    boolean atkBoost = user.getStat(StatName.Atk).change(1, thisMove, user, true, false);
                    boolean defBoost = pokemon.getStat(StatName.Def).change(1, thisMove, pokemon, true, false);

                    success[0] = speDrop || atkBoost || defBoost;
                }

                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {success[0], success[2]};
                }
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallMoveTarget) {
                return MoveTarget.User;
            } else if (condition == MoveEffectActivation.CallEffectTarget) {
                return EffectTarget.User;
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] try_protect = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (Battle.nextMove(user.getCurrentAction()) == null) {
                user.setConsecutiveProtections(0);
                return new boolean[] {false, true};
            }

            double chance = 1*(Math.pow(1.0/3.0, user.getConsecutiveProtections()));
            if (Math.random() < chance) {
                return new boolean[] {true, true};
            }

            user.setConsecutiveProtections(0);
            return new boolean[] {false, true};
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            user.setConsecutiveProtections(0);
            return new boolean[] {false, true};
        }
    };

    public static final MoveEffectFunction[] add_protection = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            user.addConsecutiveProtection();
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] disable = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean willInflict = target.getLastUsedMove() != null && !target.getLastUsedMove().compare(Data.get().getMove("struggle"));

            if (!willInflict) {
                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {false, true};
                }
                return false;
            }

            Move disabledMove = target.getLastUsedMove().getMoveOrigin() == null ? target.getLastUsedMove() : target.getLastUsedMove().getMoveOrigin();

            Pokemon pokemon;
            if (condition != MoveEffectActivation.TryUse) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, true);
            }

            boolean[] success = Data.get().getStatusCondition("move_disabled").apply(
                pokemon, thisMove, Map.of(
                    "Affected Move", disabledMove
                ),
                showMessages, false
            );

            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {success[0], success[2]};
            } else {
                return success[0];
            }
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] future_attack = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (!thisMove.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                    for (Move delayedMove : Battle.delayedMoves.get(user.getTeam())) {
                        if (delayedMove.getUser() == user && (
                                delayedMove.compare(Data.get().getMove("doom_desire")) ||
                                delayedMove.compare(Data.get().getMove("future_sight"))
                            )) {
                            return new boolean[] {false, true};
                        }
                    }
                }
                return new boolean[] {true, true};
            }

            if (condition == MoveEffectActivation.AfterMove &&
                !thisMove.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));
            }

            if (condition == MoveEffectActivation.DelayedTurnEnd) {
                if (thisEffect.getCounter() <= 0) {
                    if (!Battle.faintCheck(target, null, false)) {
                        MessageHandler.add(thisMove.getMessages().getName(), "activate", Map.of(
                            "Target", target.getName(true, false),
                            "Move", thisMove.getName()
                        ));
                        thisMove.addProperty(TemporaryProperty.FutureHit);
                        Battle.useMove(thisMove, user, target, false, false);
                    }

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(Data.get().getMove("_placeholder_"), user));
                }
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] force_switch = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (Arrays.asList(target.getAbility().getConditions()).contains(AbilityActivation.TryForceSwitch) &&
                !(boolean) target.getAbility().activate(target, user, thisMove, null, damage, 0, null, null, 0, showMessages, AbilityActivation.TryForceSwitch)) {
                return null;
            }

            for (StatusCondition statusCondition : target.getVolatileStatusList()) {
                if (Arrays.asList(statusCondition.getActivation()).contains(StatusActivation.TryForceSwitch) &&
                    !(boolean) statusCondition.activate(target, user, thisMove, damage, true, StatusActivation.TryForceSwitch)) {
                    return null;
                }
            }

            Move switchMove = new Move(Data.get().getMove("_switch_"), target);
            switchMove.addProperty(TemporaryProperty._Forced_);
            Battle.addAction(new Action(switchMove, target, target), user.getCurrentAction());

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] echoed_voice = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.AfterMove || condition == MoveEffectActivation.Miss) {
                Data.get().getFieldCondition("echoed_voice").apply(thisMove, false, null, true);
            }

            if (condition == MoveEffectActivation.CallPower) {
                double power = thisMove.getPower(true, true, hit);

                for (FieldCondition fieldCondition : Battle.generalField) {
                    if (fieldCondition.compare(Data.get().getFieldCondition("echoed_voice"))) {
                        power *= fieldCondition.getCounter();
                        break;
                    }
                }

                return power;
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallPower) {
                return thisMove.getPower(true, true, hit);
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] encore = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean willInflict = target.getLastUsedMove() != null &&
                                  target.getLastUsedMove().getCurrentPP() > 0 &&
                                  !target.getLastUsedMove().hasInherentProperty(InherentProperty.EncoreFails);

            if (!willInflict) {
                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {false, true};
                }
                return false;
            }

            Move lockedMove = target.getLastUsedMove().getMoveOrigin() == null ? target.getLastUsedMove() : target.getLastUsedMove().getMoveOrigin();

            Pokemon pokemon;
            if (condition != MoveEffectActivation.TryUse) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, true);
            }

            boolean[] success = Data.get().getStatusCondition("encore").apply(
                pokemon, thisMove, Map.of(
                    "Affected Move", lockedMove
                ),
                showMessages, false
            );

            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {success[0], success[2]};
            } else {
                return success[0];
            }
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] endeavor = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int moveDamage = target.getCurrentHP() - user.getCurrentHP();
            return Integer.max(moveDamage, 0);
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return 0;
        }
    };

    public static final MoveEffectFunction[] eternabeam = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                boolean willChangeForm = user.compare(Data.get().getPokemon("eternatus"), true) && user.compareWithForm(Data.get().getPokemon("eternatus")) &&
                                         user.getItem().compare(Data.get().getItem("eternal_wishing_star")) && !user.getItem().wasActivated();

                if (!willChangeForm &&
                    !user.getAbility().compare(Data.get().getAbility("darkest_day"))) {
                    StatusCondition rechargeCondition = user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn"));

                    if (rechargeCondition == null) {
                        user.setReadiedMove(thisMove);
                        Data.get().getStatusCondition("recharging_turn").apply(user, thisMove, null, true, false);
                    } else {
                        user.setReadiedMove(null);
                        user.endVolatileStatus(rechargeCondition, true);
                    }
                } else if (willChangeForm ||
                           user.getAbility().compare(Data.get().getAbility("darkest_day"))) {
                    StatusCondition unusableMoveCondition = user.getVolatileStatus(Data.get().getStatusCondition("unusable_move_turn"));

                    if (unusableMoveCondition == null) {
                        Data.get().getStatusCondition("unusable_move_turn").apply(
                            user, thisMove, Map.of(
                                "Affected Move", thisMove
                            ),
                            true, false
                        );
                    } else {
                        user.endVolatileStatus(unusableMoveCondition, true);
                    }
                }

                if (willChangeForm) {
                    MessageHandler.add(user.getMessages().getName(), "form eternamax", Map.of(
                        "Pokemon", user.getName(true, false),
                        "Item", user.getItem().getName()
                    ));
                    user.changeForm("Eternamax");
                    user.getItem().setActivated(true);
                }
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] self_ko = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            user.setCurrentHP(0);
            Battle.faintCheck(user, null, true);

            if (thisMove.isZPowered()) {
                thisMove.activateZ(user, target, null, null, 0, null, true, MoveEffectActivation.ZPrimarySuccess);
            }

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] damp_block = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            for (Pokemon activePokemon : Battle.orderActivePokemonList()) {
                if (activePokemon.getAbility().compare(Data.get().getAbility("damp")) &&
                    activePokemon.getAbility().isActive()) {
                    return false;
                }
            }
            return true;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return true;
        }
    };

    public static final MoveEffectFunction[] first_turn_only = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.getTurnsOnField() < 2) {
                    return new boolean[] {true, true};
                }
            }
            return new boolean[] {false, true};
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new boolean[] {false, true};
        }
    };

    public static final MoveEffectFunction[] false_swipe = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (target.getVolatileStatus(Data.get().getStatusCondition("substitute")) != null) {
                return damage.amount;
            }
            return Integer.min(damage.amount, target.getCurrentHP() - 1);
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return damage.amount;
        }
    };

    public static final MoveEffectFunction[] fell_stinger = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (Battle.faintCheck(target, null, false)) {
                user.getStat(StatName.Atk).change(3, thisMove, user, true, false);
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] final_gambit = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return user.getCurrentHP();
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return 0;
        }
    };

    public static final MoveEffectFunction[] fling = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.getItem().compare(Data.get().getItem("none")) || user.getItem().cantFling() ||
                    user.getItem().heldByValidUser(true) && user.getItem().isTetheredToValidUser()) {
                    return new boolean[] {false, true};
                }
                return new boolean[] {true, true};
            }

            if (condition == MoveEffectActivation.BeforeMove) {
                MessageHandler.add(thisMove.getMessages().getName(), "remove item", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Item", user.getItem().getName()
                ));
            }

            if (condition == MoveEffectActivation.CallPower) {
                return user.getItem().getFlingPower();
            }

            if (condition == MoveEffectActivation.AfterMove) {
                if (!Battle.faintCheck(target, null, false)) {
                    user.getItem().activateFlingEffect(user, user, target, thisMove);

                    if (!user.getItem().compare(Data.get().getItem("none"))) {
                        user.getItem().setConsumed(true);
                        user.getItem().consume(true, false);
                    }
                } else {
                    user.getItem().setConsumed(true);
                    user.getItem().consume(true, false);
                }
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            } else if (condition == MoveEffectActivation.CallPower) {
                return 0.0;
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] pivot = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Move switchMove = new Move(Data.get().getMove("_switch_"), user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            Battle.addAction(new Action(switchMove, user, user), user.getCurrentAction());

            if (thisMove.isZPowered()) {
                thisMove.activateZ(user, target, null, null, 0, null, true, MoveEffectActivation.ZPrimarySuccess);
            }

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] flying_press = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new Type[] {
                thisMove.getType(false, false),
                new Type(Data.get().getType("flying"), thisMove)
            };
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new Type[] {thisMove.getType(false, false)};
        }
    };

    public static final MoveEffectFunction[] freeze_dry = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallSuperEffective) {
                if (type.compare(Data.get().getType("water"))) {
                    List<TypeTemplate> newWeaknesses = new ArrayList<>(Arrays.asList(type.getSuperEffective(null, true)));

                    boolean alreadyWeak = false;
                    for (TypeTemplate weakness : newWeaknesses) {
                        if (weakness.compare(thisMove.getType(false, false))) {
                            alreadyWeak = true;
                            break;
                        }
                    }

                    if (!alreadyWeak) {
                        Type newWeakness = thisMove.getType(false, false);
                        newWeaknesses.add(Data.get().getType(newWeakness.getName().toLowerCase()));
                    }

                    return newWeaknesses.toArray(new TypeTemplate[0]);
                }

                return type.getSuperEffective(null, true);
            }

            if (condition == MoveEffectActivation.CallNotVeryEffective) {
                if (type.compare(Data.get().getType("water"))) {
                    List<TypeTemplate> newResistances = new ArrayList<>(Arrays.asList(type.getNotVeryEffective(null, true)));

                    for (TypeTemplate resistance : newResistances) {
                        if (resistance.compare(thisMove.getType(false, false))) {
                            newResistances.remove(resistance);
                            break;
                        }
                    }

                    return newResistances.toArray(new TypeTemplate[0]);
                }

                return type.getNotVeryEffective(null, true);
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallSuperEffective) {
                return type.getSuperEffective(null, true);
            } else if (condition == MoveEffectActivation.CallNotVeryEffective) {
                return type.getNotVeryEffective(null, true);
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] fury_cutter = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            double power = thisMove.getPower(true, true, hit);
            for (int i = 0; i < thisMove.getConsecutiveUses(); i++) {
                power *= 2;

                if (power >= 160.0) {
                    break;
                }
            }
            return power;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return thisMove.getPower(true, true, hit);
        }
    };

    public static final MoveEffectFunction[] recharge = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            StatusCondition rechargeCondition = user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn"));

            if (rechargeCondition == null) {
                user.setReadiedMove(thisMove);
                Data.get().getStatusCondition("recharging_turn").apply(user, thisMove, null, true, false);
            } else {
                user.setReadiedMove(null);
                user.endVolatileStatus(rechargeCondition, true);
            }

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] growth = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int stages = Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("sun")) ||
                         Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("desolate_land")) ?
                2 : 1;

            user.getStat(StatName.Atk).change(stages, thisMove, user, true, false);
            user.getStat(StatName.SpA).change(stages, thisMove, user, true, false);
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] guardian_flame = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int stages = target.getDamageDealt()/100;

            target.getStat(StatName.Def).change(-stages, thisMove, user, true, false);
            target.getStat(StatName.SpD).change(-stages, thisMove, user, true, false);
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] speed_power_scale_calc = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int targetSpeed = target.getStat(StatName.Spe).getEffectiveValue(user, null, false, null);
            int userSpeed = user.getStat(StatName.Spe).getEffectiveValue(target, null, false, null);

            return Math.min(25.0*targetSpeed/userSpeed + 1, 150.0);
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return 1.0;
        }
    };

    public static final MoveEffectFunction[] hidden_power = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Pokemon moveTarget = thisMove.getTarget();

            if (moveTarget == null) {
                return null;
            }

            List<TypeTemplate> allTypes = new ArrayList<>(Data.get().getRegularTypeList());

            Map<TypeTemplate, Integer> weak = new HashMap<>();
            Map<TypeTemplate, Integer> resist = new HashMap<>();
            List<TypeTemplate> immune = new ArrayList<>();

            for (TypeTemplate t : allTypes) {
                weak.put(t, 0);

                for (Type targetType : moveTarget.getTypes()) {
                    if (targetType.isSuppressed()) {
                        continue;
                    }

                    for (TypeTemplate weakness : Arrays.asList(targetType.getSuperEffective(null, false))) {
                        if (weakness.compare(t)) {
                            weak.put(t, weak.get(t) + 1);
                            break;
                        }
                    }
                }
            }

            for (TypeTemplate t : allTypes) {
                resist.put(t, 0);

                for (Type targetType : moveTarget.getTypes()) {
                    if (targetType.isSuppressed()) {
                        continue;
                    }

                    for (TypeTemplate resistance : Arrays.asList(targetType.getNotVeryEffective(null, false))) {
                        if (resistance.compare(t)) {
                            resist.put(t, resist.get(t) + 1);
                            break;
                        }
                    }
                }
            }

            for (TypeTemplate t : allTypes) {
                Move testMove = new Move(thisMove, user);
                testMove.setType(new Type(t, testMove));

                boolean ineffective = Damage.ineffective(t, moveTarget);
                if (moveTarget.getAbility().shouldActivate(testMove, AbilityActivation.TryHitUserTest) &&
                    !((boolean) moveTarget.getAbility().activate(moveTarget, user, testMove, null, null, 0, null, null, 0, showMessages, AbilityActivation.TryHitUserTest))) {
                    ineffective = true;
                }
                if (Battle.getWeather(testMove).shouldActivate(moveTarget, FieldActivation.TryUseMove) &&
                    !((boolean) Battle.getWeather(testMove).activate(moveTarget, user, testMove, null, null, null, 0, false, false, FieldActivation.TryUseMove))) {
                    ineffective = true;
                }

                if (ineffective) {
                    immune.add(t);
                }
            }

            Map<TypeTemplate, Integer> typePossibilities = new HashMap<>();
            for (TypeTemplate t : allTypes) {
                if (immune.contains(t)) {
                    continue;
                }

                int sum = weak.get(t) - resist.get(t);
                typePossibilities.put(t, sum);
            }

            int maxValue = Collections.max(typePossibilities.values());

            typePossibilities.entrySet().removeIf(e -> e.getValue() < maxValue);

            List<TypeTemplate> possibleTypes = new ArrayList<>(typePossibilities.keySet());

            if (possibleTypes.isEmpty()) {
                return type;
            }

            Type newType = new Type(
                possibleTypes.get((int) (Math.random()*possibleTypes.size())),
                thisMove
            );
            thisMove.setType(newType);

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] crash = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int crashDamage = user.getHP()/2;

            MessageStorage message = new MessageStorage(
                "modify_health", "crash", Map.of(
                    "Pokemon", user.getName(true, false)
                )
            );

            Damage.indirectDamage(user, user, crashDamage, 0, DamageSource.Crash, thisMove, message, false);
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] last_resort = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.getMove(Data.get().getMove("last_resort")) == null) {
                return new boolean[] {false, true};
            }

            boolean onlyMove = true;
            boolean hasUnusedMove = false;
            for (Move move : user.getMoves()) {
                if (move != null &&
                    !move.compare(Data.get().getMove("last_resort"))) {
                    onlyMove = false;
                    if (!move.isUsed()) {
                        hasUnusedMove = true;
                    }
                }
            }

            if (!onlyMove && !hasUnusedMove) {
                return new boolean[] {true, true};
            }
            return new boolean[] {false, true};
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new boolean[] {false, true};
        }
    };

    public static final MoveEffectFunction[] lock_on = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Pokemon userPokemon;
            Pokemon targetPokemon;
            if (condition != MoveEffectActivation.TryUse) {
                userPokemon = user;
                targetPokemon = target;
            } else {
                userPokemon = new Pokemon(user, true);
                targetPokemon = new Pokemon(target, true);
            }

            boolean success = false;
            if (target.getVolatileStatus(Data.get().getStatusCondition("taking_aim"), user) == null) {
                if (Data.get().getStatusCondition("taking_aim").apply(userPokemon, thisMove, null, false, false)[0] &&
                    Data.get().getStatusCondition("taking_aim").apply(targetPokemon, thisMove, null, true, false)[0]) {
                    success = true;
                }
            }

            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {success, true};
            }
            return success;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return false;
        }
    };

    public static final MoveEffectFunction[] fail_last = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new boolean[] {Battle.nextMove(user.getCurrentAction()) != null, true};
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new boolean[] {false, true};
        }
    };

    public static final MoveEffectFunction[] magnetic_flux = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.getAbility().compare(Data.get().getAbility("plus")) || user.getAbility().compare(Data.get().getAbility("minus"))) {
                user.getStat(StatName.Def).change(1, thisMove, user, true, false);
                user.getStat(StatName.SpD).change(1, thisMove, user, true, false);
            }
            // TODO afeta aliados também
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] metronome = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Move calledMove = null;
            do {
                int calledMoveIndex = (int) (Math.random()*Data.get().getRegularMoveList().size());
                calledMove = new Move(Data.get().getRegularMoveList().get(calledMoveIndex), user);
            } while (calledMove.hasInherentProperty(InherentProperty.MetronomeUncallable));

            if (thisMove.isZPowered() &&
                calledMove.getCategory() != Category.Status) {
                calledMove = new Move(calledMove.turnZMove(), calledMove, user);
            }

            MessageHandler.add(thisMove.getMessages().getName(), "call move", Map.of(
                "Source", (thisMove.isZPowered() ? "Z-" : "") + thisMove.getName(),
                "Move", calledMove.getName()
            ));

            for (TemporaryProperty temporaryProperties : thisMove.getTemporaryProperties()) {
                calledMove.addProperty(temporaryProperties);
            }
            calledMove.addProperty(TemporaryProperty.Called);

            Pokemon moveTarget;
            if (calledMove.targetsOpponent()) {
                moveTarget = Battle.getOpposingPokemon(user.getTeam());
            } else {
                moveTarget = Battle.getActivePokemon(user.getTeam());
            }

            Battle.addAction(new Action(calledMove, user, moveTarget), user.getCurrentAction());

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] nature_power = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Move transformedMove = null;
            if (Battle.getTerrain().compare(Data.get().getFieldCondition("electric_terrain"))) {
                transformedMove = new Move(Data.get().getMove("thunderbolt"), user);
            } else if (Battle.getTerrain().compare(Data.get().getFieldCondition("grassy_terrain"))) {
                transformedMove = new Move(Data.get().getMove("energy_ball"), user);
            } else if (Battle.getTerrain().compare(Data.get().getFieldCondition("misty_terrain"))) {
                transformedMove = new Move(Data.get().getMove("moonblast"), user);
            } else if (Battle.getTerrain().compare(Data.get().getFieldCondition("psychic_terrain"))) {
                transformedMove = new Move(Data.get().getMove("psychic"), user);
            } else {
                transformedMove = new Move(Data.get().getMove("tri_attack"), user);
            }

            if (thisMove.isZPowered() &&
                transformedMove.getCategory() != Category.Status) {
                transformedMove = new Move(transformedMove.turnZMove(), transformedMove, user);
            }

            if (transformedMove != null) {
                MessageHandler.add(thisMove.getMessages().getName(), "call move", Map.of(
                    "Source", (thisMove.isZPowered() ? "Z-" : "") + thisMove.getName(),
                    "Move", transformedMove.getName()
                ));

                for (TemporaryProperty temporaryProperties : thisMove.getTemporaryProperties()) {
                    transformedMove.addProperty(temporaryProperties);
                }
                transformedMove.addProperty(TemporaryProperty.Called);

                Battle.addAction(new Action(transformedMove, user, target), user.getCurrentAction());
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] pain_split = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int newHP = (user.getCurrentHP() + target.getCurrentHP())/2;
            user.setCurrentHP(newHP);
            target.setCurrentHP(newHP);

            MessageHandler.add(thisMove.getMessages().getName(), "use", null);

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] perish_song = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Move perishTest = new Move(thisMove, user);
            perishTest.setMoveTarget(MoveTarget.Normal);

            for (Pokemon activePokemon : Battle.orderActivePokemonList()) {
                if (!activePokemon.getAbility().shouldActivate(perishTest, AbilityActivation.TryHitUser) ||
                    (boolean) activePokemon.getAbility().activate(activePokemon, user, perishTest, null, null, 0, null, null, 0, showMessages, AbilityActivation.TryHitUser)) {
                    Data.get().getStatusCondition("perish_song").apply(activePokemon, thisMove, null, true, false);
                }
            }

            MessageHandler.add(thisMove.getMessages().getName(), "use", null);

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] modify_category = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int userAtk = user.getStat(StatName.Atk).getValue();
            int userAtkStages = user.getStat(StatName.Atk).getStages(null, null);
            double valAtk = 1 + Math.abs(userAtkStages)*0.5;
            userAtk = (int) (userAtkStages >= 0 ? userAtk*valAtk : userAtk/valAtk);

            int userSpA = user.getStat(StatName.SpA).getValue();
            int userSpAStages = user.getStat(StatName.SpA).getStages(null, null);
            double valSpA = 1 + Math.abs(userSpAStages)*0.5;
            userSpA = (int) (userSpAStages >= 0 ? userSpA*valSpA : userSpA/valSpA);

            return userAtk > userSpA ? Category.Physical : Category.Special;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return thisMove.getCategory();
        }
    };

    public static final MoveEffectFunction[] psych_up = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            for (Stat userStat : user.getStats()) {
                int targetStages = target.getStat(userStat.getNameShort()).getTrueStages();
                userStat.setStages(targetStages);
            }

            if (target.getVolatileStatus(Data.get().getStatusCondition("pumped")) != null &&
                user.getVolatileStatus(Data.get().getStatusCondition("pumped")) == null) {
                Data.get().getStatusCondition("pumped").apply(user, thisMove, null, false, false);
            } else if (target.getVolatileStatus(Data.get().getStatusCondition("pumped")) == null &&
                       user.getVolatileStatus(Data.get().getStatusCondition("pumped")) != null) {
                user.endVolatileStatus(Data.get().getStatusCondition("pumped"), false);
            }

            if (target.getVolatileStatus(Data.get().getStatusCondition("laser_focus")) != null &&
                user.getVolatileStatus(Data.get().getStatusCondition("laser_focus")) == null) {
                Data.get().getStatusCondition("laser_focus").apply(user, thisMove, null, false, false);
            } else if (target.getVolatileStatus(Data.get().getStatusCondition("laser_focus")) == null &&
                       user.getVolatileStatus(Data.get().getStatusCondition("laser_focus")) != null) {
                user.endVolatileStatus(Data.get().getStatusCondition("laser_focus"), false);
            }

            MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                "Pokemon", user.getName(true, false),
                "Target", target.getName(true, false)
            ));

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] pursuit = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.OpponentSwitch) {
                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(Data.get().getMove("_switch_"), target);

                Battle.moveAction(userAction, targetAction);
            }

            if (condition == MoveEffectActivation.CallPower) {
                if (target.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) != null) {
                    return thisMove.getPower(true, true, hit) * 2;
                }
                return thisMove.getPower(true, true, hit);
            }

            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (target.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) != null) {
                    return -1.0;
                }
                return (double) thisMove.getAccuracy();
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallPower) {
                return thisMove.getPower(true, true, hit);
            } else if (condition == MoveEffectActivation.AccuracyCalc) {
                return (double) thisMove.getAccuracy();
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] recycle = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (!user.getItem().compare(Data.get().getItem("none"))) {
                    return new boolean[] {false, true};
                }
                if (user.getConsumedItem().compare(Data.get().getItem("none"))) {
                    return new boolean[] {false, true};
                }
                return new boolean[] {true, true};
            }

            if (condition == MoveEffectActivation.AfterMove) {
                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Item", user.getConsumedItem().getName()
                ));
                user.restoreConsumedItem();
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] reflect_type = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean typeless = true;
            for (Type targetType : target.getTypes()) {
                if (!targetType.compare(Data.get().getType("typeless"))) {
                    typeless = false;
                    break;
                }
            }

            if (!typeless) {
                user.setTypes(target.getTypes());

                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Target", target.getName(true, false)
                ));
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] relic_song = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.compareWithForm(Data.get().getPokemon("meloetta"))) {
                MessageHandler.add("pokemon", "change form", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Form", "Pirouette"
                ));
                user.changeForm("Pirouette");
            } else {
                MessageHandler.add("pokemon", "change form", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Form", "Aria"
                ));
                user.changeForm("Aria");
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] rest = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                StatusCondition sleepTest = Data.get().getStatusCondition("sleep").cause(
                    thisMove, Map.of(
                        "Counter", 2,
                        "Causer", user // com causer pra impedir que seja bloqueado por Safeguard
                    )
                );

                boolean failSleep = sleepTest.immune(user) || sleepTest.targetProtected(user, user, thisMove, true);

                if (user.getCurrentHP() == user.getHP() ||
                    user.getNonVolatileStatus().compare(Data.get().getStatusCondition("sleep")) ||
                    failSleep) {
                    return new boolean[] {false, !failSleep};
                }
                return new boolean[] {true, true};
            }

            if (condition == MoveEffectActivation.AfterMove) {
                user.endNonVolatileStatus(false);
                Data.get().getStatusCondition("sleep").apply(
                    user, thisMove, Map.of(
                        "Counter", 2
                    ),
                    false, false
                );

                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));

                Damage.heal(user, thisMove, user.getHP(), true, false);
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] revelation_dance = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            for (Type userType : user.getTypes()) {
                if (!userType.compare(Data.get().getType("typeless"))) {
                    return new Type(userType, thisMove);
                }
            }
            return new Type(Data.get().getType("typeless"), thisMove);
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new Type(Data.get().getType("typeless"), thisMove);
        }
    };

    public static final MoveEffectFunction[] rollout = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            StatusCondition locked = user.getVolatileStatus(Data.get().getStatusCondition("locked"));

            if (condition == MoveEffectActivation.AfterMove) {
                if (locked == null) {
                    user.setReadiedMove(thisMove);
                    Data.get().getStatusCondition("locked").apply(
                        user, thisMove, Map.of(
                            "Counter", 4 // 5 turnos (primeiro não é contado)
                        ),
                        true, false
                    );
                } else if (locked.getCounter() <= 0) {
                    user.endVolatileStatus(locked, true);
                }
            }

            if (condition == MoveEffectActivation.CallPower) {
                double power = thisMove.getPower(true, true, hit);
                if (locked != null) {
                    for (int i = 5; i > locked.getCounter(); i--) {
                        power *= 2;
                    }
                }

                if (user.getVolatileStatus(Data.get().getStatusCondition("defense_curl")) != null) {
                    power *= 2;
                }

                return power;
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallPower) {
                return thisMove.getPower(true, true, hit);
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] roost = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.hasType(Data.get().getType("flying"))) {
                user.getType(Data.get().getType("flying")).setSuppressed(true);
            }
            Data.get().getStatusCondition("roost").apply(user, thisMove, null, true, false);
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] round = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.OpponentMove) {
                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(Data.get().getMove("round"), target);

                Battle.moveAction(userAction, targetAction);
            }

            if (condition == MoveEffectActivation.CallPower) {
                Action targetAction = Battle.findAction(Data.get().getMove("round"), target);

                if (Battle.actionIsAfterOther(user.getCurrentAction(), targetAction)) {
                    return thisMove.getPower(true, true, hit) * 2;
                }
                return thisMove.getPower(true, true, hit);
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallPower) {
                return thisMove.getPower(true, true, hit);
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] shed_tail = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams.get(user.getTeam())) {
                    if (pokemon != null &&
                        pokemon != Battle.getActivePokemon(user.getTeam()) &&
                        !Battle.faintCheck(pokemon, null, false)) {
                        teamFainted = false;
                    }
                }

                if (teamFainted) {
                    return new boolean[] {false, true};
                }

                if (user.getVolatileStatus(Data.get().getStatusCondition("substitute")) != null) {
                    return new boolean[] {false, true};
                }

                int cutHP = Integer.max((int) Math.ceil(user.getHP()/2.0), 1);
                if (user.getCurrentHP() <= cutHP) {
                    return new boolean[] {false, true};
                }

                return new boolean[] {true, true};
            }

            if (condition == MoveEffectActivation.AfterMove) {
                int cutHP = Integer.max((int) Math.ceil(user.getHP()/2.0), 1);
                if (user.getCurrentHP() > cutHP) {
                    int remainingHP = user.getCurrentHP() - cutHP;
                    user.setCurrentHP(remainingHP);

                    Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));

                    MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                        "Pokemon", user.getName(true, false),
                        "Number", String.valueOf(cutHP)
                    ));

                    Move switchAction = new Move(Data.get().getMove("_switch_"), user);
                    switchAction.addProperty(TemporaryProperty._Pivot_);
                    Battle.addAction(new Action(switchAction, user, user), user.getCurrentAction());
                }
            }

            if (condition == MoveEffectActivation.DelayedSwitch) {
                int cutHP = Integer.max(user.getHP()/4, 1);
                // x/4 e Math.ceil(x/2.0)/2 têm resultados diferentes se o resto for 3
                // então não se pode usar o mesmo cutHP de antes e dividir por 2

                Data.get().getStatusCondition("substitute").apply(
                    target, thisMove, Map.of(
                        "Counter", cutHP
                    ),
                    false, false
                );

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(Data.get().getMove("_placeholder_"), user));
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] skill_swap = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (!user.getAbility().isNotTransferable() &&
                !target.getAbility().isNotTransferable() &&
                !user.getAbility().isNotReplaceable() &&
                !target.getAbility().isNotReplaceable()) {
                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));
                user.swapAbilities(target);
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] ground = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean showMessage = false;

            if (!target.isGrounded(null)) {
                Data.get().getStatusCondition("grounded").apply(target, thisMove, null, false, false);
                showMessage = true;
            }

            StatusCondition chargeCondition = target.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn"));
            // Bounce/Fly/Sky Drop
            if (chargeCondition != null &&
                (
                    chargeCondition.getCausingMove().compare(Data.get().getMove("bounce")) ||
                    chargeCondition.getCausingMove().compare(Data.get().getMove("fly"))
                )) {
                Action chargeAction = Battle.findAction(chargeCondition.getCausingMove(), target);
                Battle.removeAction(chargeAction);
                target.setReadiedMove(null);
                target.endVolatileStatus(chargeCondition, false);
                showMessage = true;
            }

            if (showMessage) {
                MessageHandler.add(thisMove.getMessages().getName(), "activate", Map.of(
                    "Pokemon", target.getName(true, false)
                ));
            }

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] soak = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean pureWaterType = true;
            boolean typeless = true;
            for (Type targetType : target.getTypes()) {
                if (!targetType.compare(Data.get().getType("water")) && !targetType.compare(Data.get().getType("typeless"))) {
                    pureWaterType = false;
                }
                if (!targetType.compare(Data.get().getType("typeless"))) {
                    typeless = false;
                }
            }
            if (typeless) {
                pureWaterType = false;
            }

            if (!pureWaterType) {
                target.setTypes(new TypeTemplate[] {Data.get().getType("water")});
                MessageHandler.add("pokemon", "change type", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Type", "Water"
                ));
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] spectral_thief = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            boolean stoleChange = false;
            for (Stat targetStat : target.getStats()) {
                int stages = targetStat.getTrueStages();

                if (stages > 0) {
                    targetStat.setStages(0);
                    user.getStat(targetStat.getNameShort()).change(stages, thisMove, user, false, false);
                    stoleChange = true;
                }
            }

            if (stoleChange) {
                MessageHandler.add(thisMove.getMessages().getName(), "clear stat boost", Map.of(
                    "Pokemon", user.getName(true, false)
                ));
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] stored_power = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int damageMultiplier = 1;
            for (Stat userStat : user.getStats()) {
                if (userStat.getTrueStages() > 0) {
                    damageMultiplier += userStat.getTrueStages();
                }
            }
            return thisMove.getPower(true, true, hit) * damageMultiplier;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return thisMove.getPower(true, true, hit);
        }
    };

    public static final MoveEffectFunction[] substitute = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.getVolatileStatus(Data.get().getStatusCondition("substitute")) == null) {
                int cutHP = Integer.max(user.getHP()/4, 1);

                if (user.getCurrentHP() > cutHP) {
                    int remainingHP = user.getCurrentHP() - cutHP;
                    user.setCurrentHP(remainingHP);

                    Data.get().getStatusCondition("substitute").apply(
                        user, thisMove, Map.of(
                            "Counter", cutHP
                        ),
                        true, false
                    );

                    return new boolean[] {true, true};
                }
            }
            return new boolean[] {false, true};
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] fail_no_attack = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            Action targetAction = Battle.findAction(target, false);

            if (Battle.actionIsAfterOther(user.getCurrentAction(), targetAction)) {
                return new boolean[] {false, true};
            }

            if (targetAction.move.getCategory() != null &&
                targetAction.move.getCategory() != Category.Status) {
                return new boolean[] {true, true};
            }
            return new boolean[] {false, true};
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return new boolean[] {false, true};
        }
    };

    public static final MoveEffectFunction[] swap_items = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (target.getAbility().shouldActivate(thisMove, AbilityActivation.TryRemoveItem) &&
                    !((boolean) target.getAbility().activate(target, user, thisMove, null, null, 0, null, null, 0, true, AbilityActivation.TryRemoveItem))) {
                    return new boolean[] {false, false};
                }

                boolean userRemovable = (!user.getItem().heldByValidUser(true) || !user.getItem().isTetheredToValidUser()) && user.getItem().getType() != ItemType.ZCrystal;
                boolean userGivable = !target.getItem().isValidUser(user) || !target.getItem().isTetheredToValidUser();
                boolean targetRemovable = (!target.getItem().heldByValidUser(true) || !target.getItem().isTetheredToValidUser()) && target.getItem().getType() != ItemType.ZCrystal;
                boolean targetGivable = !user.getItem().isValidUser(target) || !user.getItem().isTetheredToValidUser();

                return new boolean[] {
                    userRemovable && targetRemovable && userGivable && targetGivable &&
                    (
                        !user.getItem().compare(Data.get().getItem("none")) ||
                        !target.getItem().compare(Data.get().getItem("none"))
                    ),
                    true
                };
            }

            if (condition == MoveEffectActivation.AfterMove) {
                Item userItem = user.takeItem();
                Item targetItem = target.takeItem();
                user.giveItem(targetItem);
                target.giveItem(userItem);

                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] taunt = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int counter = 4;
            boolean userMoved = false;
            boolean targetMoved = false;

            for (PriorityBracket priorityBracket : Battle.actionOrder) {
                for (Action action : priorityBracket.actions) {
                    if (action.user == user) {
                        userMoved = true;
                    }
                    if (action.user == target) {
                        targetMoved = true;
                    }

                    if (userMoved && !targetMoved) {
                        counter = 3;
                        break;
                    }
                }
                if (counter < 4) {
                    break;
                }
            }

            Pokemon pokemon;
            if (condition != MoveEffectActivation.TryUse) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, true);
            }

            boolean[] success = Data.get().getStatusCondition("taunt").apply(
                pokemon, thisMove, Map.of(
                    "Counter", counter
                ),
                showMessages, false
            );

            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {success[0], success[2]};
            } else {
                return success[0];
            }
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return new boolean[] {false, true};
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] toxic_guarantee_hit = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            for (Type userType : user.getTypes()) {
                if (userType.compare(Data.get().getType("poison"))) {
                    thisMove.addProperty(TemporaryProperty.CantMiss);
                    break;
                }
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] transform = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (user.transform(target, true)) {
                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Target", target.getSpecies()
                ));
                user.transform(target, false);
            }
            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] uproar = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            StatusCondition locked = user.getVolatileStatus(Data.get().getStatusCondition("locked"));

            if (locked == null) {
                user.setReadiedMove(thisMove);
                Data.get().getStatusCondition("locked").apply(
                    user, thisMove, Map.of(
                        "Counter", 2 // 3 turnos (primeiro não é contado)
                    ),
                    true, false
                );
                Data.get().getFieldCondition("uproar").apply(thisMove, false, null, true);
            } else if (locked.getCounter() <= 0) {
                user.endVolatileStatus(locked, true);
            }

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] weather_ball = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallType || condition == MoveEffectActivation.ZCallType) {
                if (Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("sun")) || Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("desolate_land"))) {
                    return new Type(Data.get().getType("fire"), thisMove);
                } else if (Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("rain")) || Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("primordial_sea"))) {
                    return new Type(Data.get().getType("water"), thisMove);
                } else if (Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("sand"))) {
                    return new Type(Data.get().getType("rock"), thisMove);
                } else if (Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("snow"))) {
                    return new Type(Data.get().getType("ice"), thisMove);
                }
                return type;
            }

            if (condition == MoveEffectActivation.CallPower) {
                if (!Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("clear")) && !Battle.getWeather(thisMove).compare(Data.get().getFieldCondition("delta_stream"))) {
                    return thisMove.getPower(true, true, hit) * 2;
                }
                return thisMove.getPower(true, true, hit);
            }

            return null;
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.CallType || condition == MoveEffectActivation.ZCallType) {
                return type;
            } else if (condition == MoveEffectActivation.CallPower) {
                return thisMove.getPower(true, true, hit);
            }
            return null;
        }
    };

    public static final MoveEffectFunction[] wish = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));

                MessageHandler.add(thisMove.getMessages().getName(), "use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));
            }

            if (condition == MoveEffectActivation.DelayedTurnEnd) {
                if (thisEffect.getCounter() <= 0) {
                    if (!Battle.faintCheck(target, null, false)) {
                        MessageHandler.add(thisMove.getMessages().getName(), "activate", Map.of(
                            "Pokemon", user.getName(true, false)
                        ));
                        Damage.heal(target, thisMove, user.getHP()/2, true, false);
                    }

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(Data.get().getMove("_placeholder_"), user));
                }
            }

            return null;
        },

        // default
        null
    };

    public static final MoveEffectFunction[] guardian_of_alola = new MoveEffectFunction[] {
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            int moveDamage = (int) (target.getCurrentHP()*0.75);

            // dano fixo não passa por cálculo, então OpponentDamageCalc tem que ser ativado aqui
            if (target.getVolatileStatus(Data.get().getStatusCondition("protection")) != null) {
                moveDamage *= (double) target.getVolatileStatus(Data.get().getStatusCondition("protection")).activate(target, user, thisMove, new Damage(moveDamage, thisMove, DamageSource.Move), true, StatusActivation.OpponentDamageCalc);
            }

            return Integer.max(moveDamage, 1);
        },

        // default
        (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
            return 0.0;
        }
    };
}
