package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.MoveEffectFunction;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.effects.MoveEffect;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.AbilityTemplate;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.loader.dtos.MoveEffectDTO;
import com.github.lucasaugustoss.loader.factories.otherEffects.OtherMoveEffects;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;
import com.github.lucasaugustoss.simulator.actions.Action;

public class MoveEffectFactory {
    public static MoveEffect buildEffect(
        MoveEffectDTO dto,
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, AbilityTemplate> abilityMap,
        Map<String, ItemTemplate> itemMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap,
        Map<String, MoveTemplate> moveMap,
        Map<String, Message> messageMap
    ) {
        if (dto == null) {
            return null;
        }

        String type = dto.type;
        EffectTarget effectTarget = FactoryTools.convertEnum(dto.effectTarget, EffectTarget.class);
        int counter = dto.counter;
        PokemonTemplate exclusiveUser = FactoryTools.convertObject(dto.exclusiveUser, pokemonMap);
        boolean exclusiveForm = dto.exclusiveForm;
        double chance = dto.chance != null ? FactoryTools.convertFraction(dto.chance) : 1;
        MoveEffectActivation[] activation = FactoryTools.convertEnumArray(dto.activation, MoveEffectActivation.class).toArray(new MoveEffectActivation[0]);
        MoveEffectFunction[] effect;

        switch (type) {
            case "print_message":
                effect = buildPrintMessage(dto);
                break;

            case "heal":
                effect = buildHeal(dto, effectTarget, abilityMap, fieldConditionMap);
                break;

            case "cure_status":
                effect = buildCureStatus(dto, effectTarget, statusConditionMap);
                break;

            case "status_condition":
                effect = buildStatusCondition(dto, effectTarget, statusConditionMap);
                break;

            case "drain":
                effect = buildDrain(dto);
                break;

            case "stat_change":
                effect = buildStatChange(dto, effectTarget);
                break;

            case "modify_power":
                effect = buildModifyPower(dto, itemMap, statusConditionMap, fieldConditionMap);
                break;

            case "field_condition":
                effect = buildFieldCondition(dto, effectTarget, fieldConditionMap);
                break;

            case "modify_accuracy":
                effect = buildModifyAccuracy(dto, fieldConditionMap);
                break;

            case "call_stat":
                effect = buildCallStat(dto);
                break;

            case "charge":
                effect = buildCharge(dto, statusConditionMap, fieldConditionMap);
                break;

            case "recoil":
                effect = buildRecoil(dto);
                break;

            case "remove_field":
                effect = buildRemoveField(dto, effectTarget, fieldConditionMap);
                break;

            case "reset_stats":
                effect = buildResetStats(effectTarget);
                break;

            case "counter":
                effect = buildCounter(dto, statusConditionMap);
                break;

            case "hp_power_scale_calc":
                effect = buildHPPowerScaleCalc(dto);
                break;

            case "requires_status":
                effect = buildRequiresStatus(dto, statusConditionMap);
                break;

            case "modify_target":
                effect = buildModifyTarget(dto, abilityMap, fieldConditionMap);
                break;

            case "ohko":
                effect = buildOHKO(dto, typeMap);
                break;

            case "power_scale_fixed":
                effect = buildPowerScaleFixed(dto);
                break;

            case "fusion":
                effect = buildFusion(dto, moveMap);
                break;

            case "split_stats":
                effect = buildSplitStats(dto);
                break;

            case "swap_stat_stages":
                effect = buildSwapStatStages(dto);
                break;

            case "sacrifice_heal":
                effect = buildSacrificeHeal(dto, moveMap, statusConditionMap);
                break;

            case "remove_item":
                effect = buildRemoveItem(dto, effectTarget, itemMap);
                break;

            case "call_type":
                effect = buildCallType(dto, typeMap);
                break;

            case "fixed_damage":
                effect = buildFixedDamage(dto);
                break;

            case "lock":
                effect = buildLock(dto, statusConditionMap);
                break;

            case "replace_ability":
                effect = buildReplaceAbility(dto, effectTarget, abilityMap);
                break;

            case "reduce_pp":
                effect = buildReducePP(dto, moveMap);
                break;

            case "z_reset":
                effect = buildZReset(messageMap);
                break;

            case "z_stat_change":
                effect = buildZStatChange(dto, messageMap);
                break;

            case "z_heal_user":
                effect = buildZHealUser();
                break;

            case "z_curse":
                effect = buildZCurse(typeMap);
                break;

            case "z_status_condition":
                effect = buildZStatusCondition(dto, statusConditionMap);
                break;

            case "z_heal_replacement":
                effect = buildZHealReplacement(moveMap);
                break;

            // temporário
            case "doubles_placeholder":
                effect = buildDoublesPlaceholder();
                break;

            // temporário
            case "z_doubles_placeholder":
                effect = buildZDoublesPlaceholder();
                break;

            case "other":
                effect = getOther(dto.otherID);
                break;

            default:
                return null;
        }

        return new MoveEffect(
            !type.equals("other") ? type : dto.otherID,
            effectTarget, counter, exclusiveUser, exclusiveForm, chance, activation, effect[0], effect[1]
        );
    }

    public static MoveEffectFunction[] buildPrintMessage(MoveEffectDTO dto) {
        final String messageType = dto.messageType;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                thisMove.getMessages().print(messageType, null);
                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildHeal(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, AbilityTemplate> abilityMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final double healValue = FactoryTools.convertFraction(dto.healFraction);
        final String specialHealChange = dto.specialHealChange != null ? dto.specialHealChange : "";
        final FieldConditionTemplate[] goodFieldConditions = FactoryTools.convertObjectArray(dto.goodFieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);
        final FieldConditionTemplate[] badFieldConditions = FactoryTools.convertObjectArray(dto.badFieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                double finalHealValue = healValue;

                switch (specialHealChange) {
                    case "heal pulse":
                        if (user.getAbility().compare(abilityMap.get("mega_launcher")) &&
                            user.getAbility().shouldActivate(null)) {
                            finalHealValue *= 1.5;
                        }
                        break;

                    case "field condition":
                        for (FieldConditionTemplate fieldCondition : goodFieldConditions) {
                            if (Battle.getWeather().compare(fieldCondition)) {
                                finalHealValue *= 4.0/3.0;
                                break;
                            }
                        }
                        for (FieldConditionTemplate fieldCondition : badFieldConditions) {
                            if (Battle.getWeather().compare(fieldCondition)) {
                                finalHealValue *= 0.5;
                                break;
                            }
                        }
                        break;

                    default:
                        break;
                }

                int healedDamage = Integer.max((int) Math.floor(targetPokemon.getHP() * finalHealValue), 1);

                Damage.heal(targetPokemon, thisMove, healedDamage, true, false);
                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildCureStatus(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                if (statusConditions.length > 0) {
                    for (StatusConditionTemplate statusCondition : statusConditions) {
                        if (targetPokemon.getNonVolatileStatus().compare(statusCondition)) {
                            targetPokemon.endNonVolatileStatus(true);
                            continue;
                        }
                        for (StatusCondition vol : targetPokemon.getVolatileStatusList()) {
                            if (vol.compare(statusCondition)) {
                                if (Battle.faintCheck(targetPokemon, false)) {
                                    System.out.println();
                                }

                                targetPokemon.endVolatileStatus(vol, true);
                            }
                        }
                        targetPokemon.orderVolatileStatusList();
                    }
                } else {
                    targetPokemon.endNonVolatileStatus(true);
                }

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildStatusCondition(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                int statusConditionIndex = (int) (Math.random()*statusConditions.length);
                StatusConditionTemplate statusCondition = statusConditions[statusConditionIndex];

                boolean specialCondition = true;
                boolean willInflict = true;
                switch (statusCondition.getID()) {
                    case "infatuation":
                        willInflict = !user.getGender().equals("Unknown") &&
                                      !targetPokemon.getGender().equals("Unknown") &&
                                      !targetPokemon.getGender().equals(user.getGender());
                        break;

                    case "destiny_bond":
                        // garante que não vai poder ativar Destiny Bond em dois turnos seguidos
                        willInflict = targetPokemon.getVolatileStatus(statusConditionMap.get("destiny_bond")) == null;
                        break;

                    case "encore":
                        willInflict = targetPokemon.getLastUsedMove() != null &&
                                      targetPokemon.getLastUsedMove().getCurrentPP() > 0 &&
                                      !targetPokemon.getLastUsedMove().hasInherentProperty(InherentProperty.EncoreFails);
                        break;

                    case "suppressed_ability":
                        willInflict = !targetPokemon.getAbility().isNotSuppressable();
                        break;

                    case "magnet_rise":
                        willInflict = targetPokemon.getVolatileStatus(statusConditionMap.get("ingrain")) == null;
                        break;

                    case "grounded":
                        willInflict = !target.isGrounded(null);
                        break;

                    case "drowsiness":
                        willInflict = target.getNonVolatileStatus().compare(statusConditionMap.get("none"));
                        break;

                    default:
                        specialCondition = false;
                        break;
                }

                if (specialCondition) {
                    if (condition == MoveEffectActivation.TryUse) {
                        return new boolean[] {willInflict, true};
                    } else if (!willInflict) {
                        return false;
                    }
                }

                Pokemon pokemon;
                if (condition != MoveEffectActivation.TryUse && condition != MoveEffectActivation.TestImmunities) {
                    pokemon = targetPokemon;
                } else {
                    pokemon = new Pokemon(targetPokemon, true);
                }

                boolean[] success = statusCondition.apply(pokemon, thisMove, null, showMessages, false);

                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {success[0], success[2]};
                } else if (condition == MoveEffectActivation.TestImmunities) {
                    return success[1];
                } else {
                    return success[0];
                }
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {false, true};
                }
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildDrain(MoveEffectDTO dto) {
        final double healValue = FactoryTools.convertFraction(dto.healFraction);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                int healedDamage = Integer.max((int) (damage.amount * healValue), 1);
                Damage.heal(user, thisMove, healedDamage, true, false);
                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildStatChange(
        MoveEffectDTO dto,
        EffectTarget effectTarget
    ) {
        final StatName[] stats = FactoryTools.convertEnumArray(dto.stats, StatName.class).toArray(new StatName[0]);
        final int[] stages = dto.stages;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                Pokemon pokemon;
                if (condition != MoveEffectActivation.TryUse) {
                    pokemon = targetPokemon;
                } else {
                    pokemon = new Pokemon(targetPokemon, true);
                }

                // TODO tratamento de mensagem (se um stat chegar no limite e outro não a mensagem aparece duas vezes)

                boolean changed = false;
                for (int i = 0; i < stats.length; i++) {
                    StatName changedStat = stats[i];
                    int changeStages = stages[i];

                    changed = pokemon.getStat(changedStat).change(
                        changeStages,
                        thisMove,
                        pokemon == user,
                        true,
                        false
                    );
                }

                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {changed, false};
                }
                return changed;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {false, true};
                }
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildModifyPower(
        MoveEffectDTO dto,
        Map<String, ItemTemplate> itemMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final double modifier = dto.modifier;
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);
        final String specialCondition = dto.specialCondition != null ? dto.specialCondition : "";

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetStatus = null;
                if (specialCondition != null) {
                    if (specialCondition.equals("user status")) {
                        targetStatus = user;
                    } else if (specialCondition.equals("opponent status")) {
                        targetStatus = target;
                    }
                }

                boolean rightStatus = false;
                if (targetStatus != null) {
                    if (statusConditions.length > 0) {
                        for (StatusConditionTemplate affectedStatus : statusConditions) {
                            if (targetStatus.getNonVolatileStatus().compare(affectedStatus)) {
                                rightStatus = true;
                                break;
                            }
                        }
                    } else {
                        if (!targetStatus.getNonVolatileStatus().compare(statusConditionMap.get("none"))) {
                            rightStatus = true;
                        }
                    }
                } else {
                    rightStatus = true;
                }

                boolean rightField = false;
                if (fieldConditions.length > 0) {
                    for (FieldConditionTemplate affectedField : fieldConditions) {
                        if (Battle.getWeather().compare(affectedField)) {
                            rightField = true;
                            break;
                        }

                        if (Battle.getTerrain().compare(affectedField) && user.isGrounded(null)) {
                            rightField = true;
                            break;
                        }

                        for (FieldCondition fieldCondition : Battle.generalField) {
                            if (fieldCondition.compare(affectedField)) {
                                rightField = true;
                                break;
                            }
                        }

                        for (FieldCondition fieldCondition : Battle.teamFields.get(user.getTeam())) {
                            if (fieldCondition.compare(affectedField)) {
                                rightField = true;
                                break;
                            }
                        }
                    }
                } else {
                    rightField = true;
                }

                boolean rightSpecial;
                switch (specialCondition) {
                    case "assurance":
                        rightSpecial = target.wasDamagedThisTurn();
                        break;

                    case "damaged by target":
                        rightSpecial = user.getDamager() == target;
                        break;

                    case "brine":
                        rightSpecial = target.getCurrentHP() <= target.getHP()/2;
                        break;

                    case "knock off":
                        boolean removable = !target.getItem().heldByValidUser(true) || !target.getItem().isTetheredToValidUser() || target.getItem().getType() != ItemType.ZCrystal;
                        rightSpecial = removable && !target.getItem().compare(itemMap.get("none"));
                        break;

                    case "moved after target":
                        Action userAction = Battle.findAction(thisMove, user);
                        Action targetAction = Battle.findAction(target, true);
                        rightSpecial = Battle.actionIsAfterOther(userAction, targetAction);
                        break;

                    case "retaliate":
                        rightSpecial = Battle.pokemonFaintedLastTurn[user.getTeam()] > 0;
                        break;

                    case "last move failed":
                        rightSpecial = user.lastMoveFailed();
                        break;

                    default:
                        rightSpecial = true;
                        break;
                }

                if (!rightStatus || !rightField || !rightSpecial) {
                    return thisMove.getPower(true, true, hit);
                }

                return thisMove.getPower(true, true, hit) * modifier;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return thisMove.getPower(true, true, hit);
            }
        };
    }

    public static MoveEffectFunction[] buildFieldCondition(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final FieldConditionTemplate fieldCondition = FactoryTools.convertObject(dto.fieldCondition, fieldConditionMap);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                int team;
                switch (effectTarget) {
                    case EffectTarget.User:
                        team = user.getTeam();
                        break;

                    case EffectTarget.Target:
                        team = target.getTeam();
                        break;

                    default:
                        team = -1;
                        break;
                }

                boolean rightField = false;
                if (fieldConditions.length > 0) {
                    for (FieldConditionTemplate affectedField : fieldConditions) {
                        if (Battle.getWeather().compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }
                } else {
                    rightField = true;
                }

                if (!rightField) {
                    if (condition == MoveEffectActivation.TryUse) {
                        return new boolean[] {false, true};
                    }
                    return null;
                }

                boolean[] success = fieldCondition.apply(thisMove, condition == MoveEffectActivation.TryUse, team, null, true);

                if (condition == MoveEffectActivation.TryUse) {
                    return success;
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
    }

    public static MoveEffectFunction[] buildModifyAccuracy(
        MoveEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final double accuracy = dto.accuracy;
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                boolean rightField = false;
                if (fieldConditions.length > 0) {
                    for (FieldConditionTemplate affectedField : fieldConditions) {
                        if (Battle.getWeather().compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }
                } else {
                    rightField = true;
                }

                if (!rightField) {
                    return (double) thisMove.getAccuracy();
                }

                return accuracy;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return (double) thisMove.getAccuracy();
            }
        };
    }

    public static MoveEffectFunction[] buildCallStat(MoveEffectDTO dto) {
        final StatName calledStat = FactoryTools.convertEnum(dto.stat, StatName.class);
        final String owner = dto.target;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon statOwner = owner.equals("user") ? user : target;
                return statOwner.getStat(calledStat);
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return stat;
            }
        };
    }

    public static MoveEffectFunction[] buildCharge(
        MoveEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final boolean semiInvulnerable = dto.semiInvulnerable;
        final FieldConditionTemplate[] quickChargeFieldConditions = FactoryTools.convertObjectArray(dto.quickChargeFieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                StatusConditionTemplate charge = semiInvulnerable ?
                    statusConditionMap.get("semi_invulnerable_charging_turn") :
                    statusConditionMap.get("charging_turn");
                StatusCondition chargeCondition = user.getVolatileStatus(charge);

                if (condition == MoveEffectActivation.AfterMove) {
                    if (chargeCondition == null) {
                        user.setReadiedMove(thisMove);
                        charge.apply(user, thisMove, null, true, false);

                        thisMove.getMessages().print("charge", Map.of(
                            "Pokemon", user.getName(true, false)
                        ));

                        if (thisMove.primaryShouldActivate(MoveEffectActivation.AfterCharge)) {
                            thisMove.activatePrimary(user, target, type, damage, hit, stat, showMessages, MoveEffectActivation.AfterCharge);
                        }

                        boolean rightField = false;
                        if (quickChargeFieldConditions.length > 0) {
                            for (FieldConditionTemplate affectedField : quickChargeFieldConditions) {
                                if (Battle.getWeather().compare(affectedField)) {
                                    rightField = true;
                                    break;
                                }
                            }
                        }

                        if (rightField) {
                            thisMove.getMessages().print("quick charge", Map.of(
                                "Pokemon", user.getName(true, false)
                            ));

                            Action moveLocation = Battle.findAction(thisMove, user);
                            Battle.addAction(new Action(thisMove, user, target), moveLocation);
                        }
                    } else {
                        user.setReadiedMove(null);
                        user.endVolatileStatus(chargeCondition, true);
                    }
                }
                if (condition == MoveEffectActivation.Miss) {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildRecoil(MoveEffectDTO dto) {
        final String recoilType = dto.recoilType;
        final double damageValue = FactoryTools.convertFraction(dto.damageFraction);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                int recoilDamage = recoilType.equals("damage") ? damage.amount : user.getHP();

                switch (recoilType) {
                    case "damage":
                        recoilDamage = (int) Math.floor(recoilDamage*damageValue);
                        break;

                    case "HP":
                        recoilDamage = (int) Math.ceil(recoilDamage*damageValue);
                        break;

                    default:
                        recoilDamage = (int) Math.round(recoilDamage*damageValue);
                        break;
                }
                recoilDamage = Integer.max(recoilDamage, 1);

                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                String message = user.getName(true, true) + " was damaged by the recoil!";
                Damage.indirectDamage(user, user, recoilDamage, 0, DamageSource.Recoil, thisMove, message, false);

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildRemoveField(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);
        final String[] fieldConditionsGeneral = dto.fieldConditionsGeneral;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                int team = effectTarget == EffectTarget.All ? -1 : targetPokemon.getTeam();

                if (team != -1) {
                    List<FieldCondition> field = Battle.teamFields.get(targetPokemon.getTeam());
                    for (FieldConditionTemplate removedCondition : fieldConditions) {
                        for (FieldCondition fieldCondition : field) {
                            if (fieldCondition.compare(removedCondition)) {
                                fieldCondition.end(field);
                            }
                        }
                    }
                } else {
                    if (fieldConditionsGeneral.length > 0) {
                        for (String generalConditions : fieldConditionsGeneral) {
                            switch (generalConditions) {
                                case "weather":
                                    Battle.getWeather().end();
                                    break;

                                case "terrain":
                                    Battle.getTerrain().end();
                                    break;

                                default:
                                    break;
                            }
                        }
                    }

                    for (FieldConditionTemplate removedCondition : fieldConditions) {
                        if (Battle.getWeather().compare(removedCondition)) {
                            Battle.getWeather().end();
                        }

                        if (Battle.getTerrain().compare(removedCondition)) {
                            Battle.getTerrain().end();
                        }

                        for (FieldCondition fieldCondition : Battle.generalField) {
                            if (fieldCondition.compare(removedCondition)) {
                                fieldCondition.end();
                            }
                        }

                        for (List<FieldCondition> teamField : Battle.teamFields) {
                            for (FieldCondition fieldCondition : teamField) {
                                if (fieldCondition.compare(removedCondition)) {
                                    fieldCondition.end(teamField);
                                }
                            }
                        }
                    }
                }

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildResetStats(EffectTarget effectTarget) {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                List<Pokemon> targets = new ArrayList<>();
                if (effectTarget == EffectTarget.User) {
                    targets.add(user);
                } else if (effectTarget == EffectTarget.Target) {
                    targets.add(target);
                } else if (effectTarget == EffectTarget.All) {
                    targets = Battle.orderActivePokemonList();
                }

                for (Pokemon pokemon : targets) {
                    for (Stat pokemonStat : pokemon.getStats()) {
                        pokemonStat.setStages(0);
                    }
                }
                System.out.println("All stat changes were eliminated!");

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildCounter(
        MoveEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final double modifier = dto.modifier;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                StatusCondition counterCondition = user.getVolatileStatus(statusConditionMap.get("countering"));

                if (condition == MoveEffectActivation.ChangeTarget) {
                    if (counterCondition != null &&
                        counterCondition.getAffectedMove() != null) {
                        return counterCondition.getAffectedMove().getUser();
                    }
                    return user;
                }

                if (condition == MoveEffectActivation.TryUse) {
                    if (counterCondition == null) {
                        return new boolean[] {false, true};
                    }

                    Action userAction = Battle.findAction(thisMove, user);
                    Action targetAction = Battle.findAction(target, false);
                    if (!Battle.actionIsAfterOther(userAction, targetAction)) {
                        return new boolean[] {false, true};
                    }

                    return new boolean[] {counterCondition.getCounter() > 0, true};
                }

                if (condition == MoveEffectActivation.FixedDamage) {
                    return (int) Math.floor(counterCondition.getCounter()*modifier);
                }

                return null;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.ChangeTarget) {
                    return user;
                } else if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {false, true};
                } else if (condition == MoveEffectActivation.FixedDamage) {
                    return 0;
                }
                return null;
            }
        };
    }

    public static MoveEffectFunction[] buildHPPowerScaleCalc(MoveEffectDTO dto) {
        final double maxPower = dto.maxPower;
        final String owner = dto.target;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                double trueMaxPower = maxPower > 0 ? maxPower : thisMove.getPower(true, true, hit);
                Pokemon hpOwner = owner.equals("user") ? user : target;

                return Math.max(trueMaxPower*hpOwner.getCurrentHP()/hpOwner.getHP(), 1.0);
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return 1.0;
            }
        };
    }

    public static MoveEffectFunction[] buildRequiresStatus(
        MoveEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate statusCondition = FactoryTools.convertObject(dto.statusCondition, statusConditionMap);
        final String victim = dto.target;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon statusVictim = victim.equals("user") ? user : target;

                if (statusVictim.getNonVolatileStatus().compare(statusCondition)) {
                    return new boolean[] {true, true};
                }
                return new boolean[] {false, true};
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return new boolean[] {false, true};
            }
        };
    }

    public static MoveEffectFunction[] buildModifyTarget(
        MoveEffectDTO dto,
        Map<String, AbilityTemplate> abilityMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final MoveTarget moveTarget = FactoryTools.convertEnum(dto.moveTarget, MoveTarget.class);
        final AbilityTemplate ability = FactoryTools.convertObject(dto.ability, abilityMap);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                boolean rightAbility = ability != null ? user.getAbility().compare(ability) : true;

                boolean rightField = false;
                if (fieldConditions.length > 0) {
                    for (FieldConditionTemplate affectedField : fieldConditions) {
                        if (Battle.getWeather().compare(affectedField)) {
                            rightField = true;
                            break;
                        }

                        if (Battle.getTerrain().compare(affectedField) && user.isGrounded(null)) {
                            rightField = true;
                            break;
                        }

                        for (FieldCondition fieldCondition : Battle.generalField) {
                            if (fieldCondition.compare(affectedField)) {
                                rightField = true;
                                break;
                            }
                        }

                        for (FieldCondition fieldCondition : Battle.teamFields.get(user.getTeam())) {
                            if (fieldCondition.compare(affectedField)) {
                                rightField = true;
                                break;
                            }
                        }
                    }
                } else {
                    rightField = true;
                }

                if (!rightAbility || !rightField) {
                    return thisMove.getMoveTarget(true);
                }

                return moveTarget;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return thisMove.getMoveTarget(true);
            }
        };
    }

    public static MoveEffectFunction[] buildOHKO(
        MoveEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final String specialOHKO = dto.specialOHKO != null ? dto.specialOHKO : "";

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {user.getLevel() >= target.getLevel(), true};
                }

                if (condition == MoveEffectActivation.OneHitKOAccuracy) {
                    int baseHitChance = 30;
                    if (specialOHKO.equals("sheer_cold") &&
                        !user.hasType(typeMap.get("ice"))) {
                        baseHitChance = 20;
                    }
                    double hitChance = (user.getLevel() - target.getLevel() + baseHitChance);
                    return Math.random() < hitChance/100.0;
                }

                if (condition == MoveEffectActivation.FixedDamage) {
                    System.out.println("It's a one-hit KO!");
                    return target.getHP();
                }

                return null;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {false, true};
                } else if (condition == MoveEffectActivation.OneHitKOAccuracy) {
                    return false;
                } else if (condition == MoveEffectActivation.FixedDamage) {
                    return 0;
                }
                return null;
            }
        };
    }

    public static MoveEffectFunction[] buildPowerScaleFixed(MoveEffectDTO dto) {
        final String comparedVariable = dto.comparedVariable;
        final double[][] amounts = dto.amounts;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                double variable;
                switch (comparedVariable) {
                    case "user HP":
                        variable = (double) user.getCurrentHP()/user.getHP();
                        break;

                    case "target weight":
                        variable = target.getWeight(thisMove);
                        break;

                    case "user vs target weight":
                        variable = user.getWeight(thisMove)/target.getWeight(thisMove);
                        break;

                    default:
                        variable = 0;
                        break;
                }

                for (double[] amount : amounts) {
                    if (variable >= amount[0]) {
                        return amount[1];
                    }
                }
                return 1.0;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return 1.0;
            }
        };
    }

    public static MoveEffectFunction[] buildFusion(
        MoveEffectDTO dto,
        Map<String, MoveTemplate> moveMap
    ) {
        final MoveTemplate move = FactoryTools.convertObject(dto.move, moveMap);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (Battle.lastUsedMove != null &&
                    Battle.lastUsedMove.compare(move)) {
                    return thisMove.getPower(true, true, hit) * 2.0;
                }
                return thisMove.getPower(true, true, hit);
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return thisMove.getPower(true, true, hit);
            }
        };
    }

    public static MoveEffectFunction[] buildSplitStats(MoveEffectDTO dto) {
        final StatName[] stats = FactoryTools.convertEnumArray(dto.stats, StatName.class).toArray(new StatName[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                for (StatName statName : stats) {
                    int newStat = (user.getStat(statName).getValue() + target.getStat(statName).getValue())/2;

                    // TODO itens modificadores

                    user.getStat(statName).setValue(newStat);
                    target.getStat(statName).setValue(newStat);
                }

                thisMove.getMessages().print("use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildSwapStatStages(MoveEffectDTO dto) {
        final StatName[] stats = FactoryTools.convertEnumArray(dto.stats, StatName.class).toArray(new StatName[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                for (StatName statName : stats) {
                    int userStages = user.getStat(statName).getTrueStages();
                    int targetStages = target.getStat(statName).getTrueStages();

                    user.getStat(statName).setStages(targetStages);
                    target.getStat(statName).setStages(userStages);
                }

                thisMove.getMessages().print("use", Map.of(
                    "Pokemon", user.getName(true, false)
                ));

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildSacrificeHeal(
        MoveEffectDTO dto,
        Map<String, MoveTemplate> moveMap,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final boolean restorePP = dto.restorePP;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.AfterMove) {
                    boolean teamFainted = true;
                    for (Pokemon pokemon : Battle.teams.get(user.getTeam())) {
                        if (pokemon != null &&
                            pokemon != user &&
                            !Battle.faintCheck(pokemon, false)) {
                            teamFainted = false;
                        }
                    }

                    if (!teamFainted) {
                        Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));

                        thisMove.getMessages().print("use", Map.of(
                            "Pokemon", user.getName(true, false)
                        ));

                        user.setCurrentHP(0);

                        Battle.faintCheck(user, true);
                    }
                }
                if (condition == MoveEffectActivation.DelayedSwitch) {
                    boolean fullPP = true;
                    if (restorePP) {
                        for (Move move : target.getMoves()) {
                            if (move != null &&
                                move.getCurrentPP() < move.getPP()) {
                                fullPP = false;
                                break;
                            }
                        }
                    }

                    if (target.getCurrentHP() >= target.getHP() &&
                        fullPP &&
                        target.getNonVolatileStatus().compare(statusConditionMap.get("none"))) {
                        return null;
                    }

                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                    thisMove.getMessages().print("heal", Map.of(
                        "Target", target.getName(true, false)
                    ));

                    if (target.getCurrentHP() < target.getHP()) {
                        Damage.heal(target, thisMove, target.getHP(), true, false);
                    }

                    if (restorePP) {
                        for (Move move : target.getMoves()) {
                            if (move != null &&
                                move.getCurrentPP() < move.getPP()) {
                                int restoredPP = move.getPP() - move.getCurrentPP();
                                move.setCurrentPP(move.getPP());
                                System.out.println(target.getName(true, true) + " restored " + restoredPP + " PP to its move " + move.getName());
                            }
                        }
                    }

                    target.endNonVolatileStatus(true);

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(moveMap.get("_placeholder_"), user));

                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildRemoveItem(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, ItemTemplate> itemMap
    ) {
        final ItemType itemType = FactoryTools.convertEnum(dto.itemType, ItemType.class);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                if (targetPokemon.getItem().compare(itemMap.get("none"))) {
                    return null;
                }

                if (targetPokemon.getItem().getType() == ItemType.ZCrystal ||
                    targetPokemon.getItem().heldByValidUser(true) && targetPokemon.getItem().isTetheredToValidUser()) {
                    return null;
                }

                if (itemType != null && targetPokemon.getItem().getType() != itemType) {
                    return null;
                }

                if (Battle.faintCheck(targetPokemon, false)) {
                    System.out.println();
                }
                thisMove.getMessages().print("remove item", Map.of(
                    "Pokemon", user.getName(true, false),
                    "Target", targetPokemon.getName(true, false),
                    "Item", targetPokemon.getItem().getName()
                ));

                targetPokemon.getItem().setConsumed(true);
                targetPokemon.getItem().consume(false, true);

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildCallType(
        MoveEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final TypeTemplate changedType = FactoryTools.convertObject(dto.changedType, typeMap);
        final ItemType itemType = FactoryTools.convertEnum(dto.itemType, ItemType.class);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (changedType != null) {
                    return new Type(changedType, thisMove);
                }

                if (itemType != null) {
                    if (user.getItem().getType() == itemType &&
                        user.getItem().shouldActivate(null)) {
                        return new Type(user.getItem().getChangesTypeTo(), thisMove);
                    }
                }

                return type;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return type;
            }
        };
    }

    public static MoveEffectFunction[] buildFixedDamage(MoveEffectDTO dto) {
        final double damageValue = FactoryTools.convertFraction(dto.damageFraction);
        final String fixedDamageType = dto.fixedDamageType;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                switch (fixedDamageType) {
                    case "user level":
                        return user.getLevel();

                    case "target HP":
                        return Integer.max((int) (target.getCurrentHP()*damageValue), 1);

                    default:
                        return 0;
                }
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return 0;
            }
        };
    }

    public static MoveEffectFunction[] buildLock(
        MoveEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final boolean rampage = dto.rampage;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                StatusConditionTemplate lockCondition = rampage ? statusConditionMap.get("rampage") : statusConditionMap.get("locked");

                StatusCondition lock = user.getVolatileStatus(lockCondition);
                if (lock == null) {
                    user.setReadiedMove(thisMove);
                    lockCondition.apply(user, thisMove, null, true, false);
                } else if (lock.getCounter() <= 0) {
                    user.endVolatileStatus(lock, true);
                }

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildReplaceAbility(
        MoveEffectDTO dto,
        EffectTarget effectTarget,
        Map<String, AbilityTemplate> abilityMap
    ) {
        final AbilityTemplate ability = FactoryTools.convertObject(dto.ability, abilityMap);
        final String abilityOwner = dto.abilityOwner != null ? dto.abilityOwner : "";

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                Pokemon targetPokemon = effectTarget == EffectTarget.User ? user : target;

                if (targetPokemon.getAbility().isNotReplaceable()) {
                    if (condition == MoveEffectActivation.TryUse) {
                        return new boolean[] {false, true};
                    }
                    return null;
                }

                boolean willChange = true;
                AbilityTemplate newAbility = null;
                Pokemon owner = abilityOwner.equals("user") ? user : target;
                if (ability != null) {
                    newAbility = ability;
                } else if (abilityOwner != null) {
                    if (!owner.getAbility().isNotTransferable()) {
                        newAbility = owner.getAbility().getTemplate();
                    } else {
                        willChange = false;
                    }
                }

                if (!willChange || targetPokemon.getAbility().compare(newAbility)) {
                    if (condition == MoveEffectActivation.TryUse) {
                        return new boolean[] {false, true};
                    }
                    return null;
                }

                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {true, true};
                }

                thisMove.getMessages().print("use", Map.of(
                    "Pokemon", targetPokemon.getName(true, false),
                    "Target", owner.getName(true, false),
                    "Ability", newAbility.getName()
                ));

                targetPokemon.setAbility(newAbility, true, user);

                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildReducePP(
        MoveEffectDTO dto,
        Map<String, MoveTemplate> moveMap
    ) {
        final int ppAmount = dto.ppAmount;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                boolean canReduce = target.getLastUsedMove() != null &&
                                    target.getLastUsedMove().getCurrentPP() != 0 &&
                                    !target.getLastUsedMove().compare(moveMap.get("struggle"));

                if (condition == MoveEffectActivation.TryUse) {
                    return new boolean[] {canReduce, true};
                }

                if (condition == MoveEffectActivation.AfterMove) {
                    int remainingPP = target.getLastUsedMove().getCurrentPP() - ppAmount;

                    if (remainingPP < 0) {
                        target.getLastUsedMove().setCurrentPP(0);
                    } else {
                        target.getLastUsedMove().setCurrentPP(remainingPP);
                    }

                    int reducingAmount = remainingPP >= 0 ? ppAmount : remainingPP + ppAmount;

                    System.out.println("It reduced the PP of " + target.getName(true, false) + "'s " + target.getLastUsedMove().getName() + " by " + reducingAmount);
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
    }

    public static MoveEffectFunction[] buildZReset(Map<String, Message> messageMap) {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                boolean reset = false;
                for (Stat userStat : user.getStats()) {
                    if (userStat.getStages(null, null) < 0) {
                        userStat.setStages(0);
                        reset = true;
                    }
                }
                if (reset) {
                    messageMap.get("stat_change").print("reset Z", Map.of(
                        "Pokemon", user.getName(true, false)
                    ));
                }
                return reset;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildZStatChange(
        MoveEffectDTO dto,
        Map<String, Message> messageMap
    ) {
        final StatName[] stats = FactoryTools.convertEnumArray(dto.stats, StatName.class).toArray(new StatName[0]);
        final int[] stages = dto.stages;

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                boolean changed = false;
                for (int i = 0; i < stats.length; i++) {
                    StatName changedStat = stats[i];
                    int changeStages = stages[i];

                    if (user.getStat(changedStat).change(
                            changeStages,
                            thisMove,
                            true,
                            stats.length == 1,
                            true
                        )) {
                        changed = true;
                    }
                }

                if (stats.length > 1 && changed) {
                    messageMap.get("stat_change").print("+1 many Z", Map.of(
                        "Pokemon", user.getName(true, false)
                    ));
                }
                return changed;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildZHealUser() {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return Damage.heal(user, thisMove, user.getHP(), true, true);
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildZCurse(Map<String, TypeTemplate> typeMap) {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                boolean ghostUser = false;
                for (Type userType : user.getTypes()) {
                    if (userType.compare(typeMap.get("ghost"))) {
                        ghostUser = true;
                        break;
                    }
                }

                if (ghostUser) {
                    return Damage.heal(user, thisMove, user.getHP(), true, true);
                } else {
                    return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
                }
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildZStatusCondition(
        MoveEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);

        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return statusConditions[0].apply(user, thisMove, null, true, true)[0];
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return false;
            }
        };
    }

    public static MoveEffectFunction[] buildZHealReplacement(Map<String, MoveTemplate> moveMap) {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                if (condition == MoveEffectActivation.ZPrimarySuccess) {
                    boolean teamFainted = true;
                    for (Pokemon pokemon : Battle.teams.get(user.getTeam())) {
                        if (pokemon != null &&
                            pokemon != user &&
                            !Battle.faintCheck(pokemon, false)) {
                            teamFainted = false;
                        }
                    }

                    if (!teamFainted) {
                        Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                    }
                }
                if (condition == MoveEffectActivation.ZDelayedSwitch) {
                    if (target.getCurrentHP() >= target.getHP()) {
                        return null;
                    }

                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                    System.out.println("The Z-Power restored " + target.getName(true, false) + "'s health!");

                    Damage.heal(target, thisMove, target.getHP(), true, false);

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(moveMap.get("_placeholder_"), user));

                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
                return null;
            },

            // default
            null
        };
    }

    public static MoveEffectFunction[] buildDoublesPlaceholder() {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return new boolean[] {false, true};
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return new boolean[] {false, true};
            }
        };
    }

    public static MoveEffectFunction[] buildZDoublesPlaceholder() {
        return new MoveEffectFunction[] {
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return true;
            },

            // default
            (thisMove, thisEffect, user, target, type, damage, hit, stat, showMessages, condition) -> {
                return true;
            }
        };
    }

    private static MoveEffectFunction[] getOther(String otherID) {
        switch (otherID) {
            case "after_you":
                return OtherMoveEffects.after_you;

            case "cure_status_team":
                return OtherMoveEffects.cure_status_team;

            case "baton_pass":
                return OtherMoveEffects.baton_pass;

            case "beat_up":
                return OtherMoveEffects.beat_up;

            case "move_illusion":
                return OtherMoveEffects.move_illusion;

            case "eat_berry":
                return OtherMoveEffects.eat_berry;

            case "captivate":
                return OtherMoveEffects.captivate;

            case "boost_super_effective_damage":
                return OtherMoveEffects.boost_super_effective_damage;

            case "copycat":
                return OtherMoveEffects.copycat;

            case "core_enforcer":
                return OtherMoveEffects.core_enforcer;

            case "court_change":
                return OtherMoveEffects.court_change;

            case "curse":
                return OtherMoveEffects.curse;

            case "try_protect":
                return OtherMoveEffects.try_protect;

            case "add_protection":
                return OtherMoveEffects.add_protection;

            case "disable":
                return OtherMoveEffects.disable;

            case "future_attack":
                return OtherMoveEffects.future_attack;

            case "force_switch":
                return OtherMoveEffects.force_switch;

            case "echoed_voice":
                return OtherMoveEffects.echoed_voice;

            case "endeavor":
                return OtherMoveEffects.endeavor;

            case "eternabeam":
                return OtherMoveEffects.eternabeam;

            case "self_ko":
                return OtherMoveEffects.self_ko;

            case "damp_block":
                return OtherMoveEffects.damp_block;

            case "first_turn_only":
                return OtherMoveEffects.first_turn_only;

            case "false_swipe":
                return OtherMoveEffects.false_swipe;

            case "fell_stinger":
                return OtherMoveEffects.fell_stinger;

            case "final_gambit":
                return OtherMoveEffects.final_gambit;

            case "fling":
                return OtherMoveEffects.fling;

            case "pivot":
                return OtherMoveEffects.pivot;

            case "flying_press":
                return OtherMoveEffects.flying_press;

            case "freeze_dry":
                return OtherMoveEffects.freeze_dry;

            case "fury_cutter":
                return OtherMoveEffects.fury_cutter;

            case "recharge":
                return OtherMoveEffects.recharge;

            case "growth":
                return OtherMoveEffects.growth;

            case "guardian_flame":
                return OtherMoveEffects.guardian_flame;

            case "speed_power_scale_calc":
                return OtherMoveEffects.speed_power_scale_calc;

            case "crash":
                return OtherMoveEffects.crash;

            case "last_resort":
                return OtherMoveEffects.last_resort;

            case "lock_on":
                return OtherMoveEffects.lock_on;

            case "fail_last":
                return OtherMoveEffects.fail_last;

            case "magnetic_flux":
                return OtherMoveEffects.magnetic_flux;

            case "metronome":
                return OtherMoveEffects.metronome;

            case "nature_power":
                return OtherMoveEffects.nature_power;

            case "pain_split":
                return OtherMoveEffects.pain_split;

            case "perish_song":
                return OtherMoveEffects.perish_song;

            case "modify_category":
                return OtherMoveEffects.modify_category;

            case "psych_up":
                return OtherMoveEffects.psych_up;

            case "pursuit":
                return OtherMoveEffects.pursuit;

            case "reflect_type":
                return OtherMoveEffects.reflect_type;

            case "relic_song":
                return OtherMoveEffects.relic_song;

            case "rest":
                return OtherMoveEffects.rest;

            case "rollout":
                return OtherMoveEffects.rollout;

            case "roost":
                return OtherMoveEffects.roost;

            case "round":
                return OtherMoveEffects.round;

            case "shed_tail":
                return OtherMoveEffects.shed_tail;

            case "skill_swap":
                return OtherMoveEffects.skill_swap;

            case "soak":
                return OtherMoveEffects.soak;

            case "spectral_thief":
                return OtherMoveEffects.spectral_thief;

            case "stored_power":
                return OtherMoveEffects.stored_power;

            case "substitute":
                return OtherMoveEffects.substitute;

            case "fail_no_attack":
                return OtherMoveEffects.fail_no_attack;

            case "swap_items":
                return OtherMoveEffects.swap_items;

            case "taunt":
                return OtherMoveEffects.taunt;

            case "toxic_guarantee_hit":
                return OtherMoveEffects.toxic_guarantee_hit;

            case "transform":
                return OtherMoveEffects.transform;

            case "uproar":
                return OtherMoveEffects.uproar;

            case "weather_ball":
                return OtherMoveEffects.weather_ball;

            case "wish":
                return OtherMoveEffects.wish;

            case "guardian_of_alola":
                return OtherMoveEffects.guardian_of_alola;

            default:
                return null;
        }
    }
}
