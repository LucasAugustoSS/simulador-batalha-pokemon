package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.AbilityEffectFunction;
import com.github.lucasaugustoss.data.objects.effects.AbilityEffect;
import com.github.lucasaugustoss.data.objects.templates.AbilityTemplate;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.loader.dtos.AbilityEffectDTO;
import com.github.lucasaugustoss.loader.factories.otherEffects.OtherAbilityEffects;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class AbilityEffectFactory {
    public static AbilityEffect buildEffect(
        AbilityEffectDTO dto,
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, StatTemplate> statMap,
        Map<String, ItemTemplate> itemMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap,
        Map<String, AbilityTemplate> abilityMap
    ) {
        if (dto == null) {
            return null;
        }

        String type = dto.type;
        AbilityActivation[] activation = FactoryTools.convertEnumArray(dto.activation, AbilityActivation.class).toArray(new AbilityActivation[0]);
        AbilityEffectFunction effect = null;

        switch (type) {
            case "print_message":
                effect = buildPrintMessage(dto, abilityMap);
                break;

            case "modify_stat":
                effect = buildModifyStat(dto, typeMap, statusConditionMap, fieldConditionMap);
                break;

            case "modify_stat_any":
                effect = buildModifyStatAny(dto, statMap, fieldConditionMap);
                break;

            case "modify_stat_stages":
                effect = buildModifyStatStages(dto);
                break;

            case "modify_power":
                effect = buildModifyPower(dto, typeMap, fieldConditionMap, abilityMap);
                break;

            case "modify_damage":
                effect = buildModifyDamage(dto);
                break;

            case "block_move":
                effect = buildBlockMove(dto, typeMap);
                break;

            case "block_move_all":
                effect = buildBlockMoveAll(dto);
                break;

            case "stat_change":
                effect = buildStatChange(dto, statMap, abilityMap);
                break;

            case "status_condition":
                effect = buildStatusCondition(dto, statusConditionMap);
                break;

            case "cure_status":
                effect = buildCureStatus(dto, statusConditionMap, fieldConditionMap);
                break;

            case "status_immunity":
                effect = buildStatusImmunity(dto, statusConditionMap);
                break;

            case "set_weather":
                effect = buildSetWeather(dto, pokemonMap, itemMap, fieldConditionMap);
                break;

            case "set_primal_weather":
                effect = buildSetPrimalWeather(dto, fieldConditionMap);
                break;

            case "set_terrain":
                effect = buildSetTerrain(dto, fieldConditionMap);
                break;

            case "paradox_field_and_boost":
                effect = buildParadoxFieldAndBoost(dto, fieldConditionMap);
                break;

            case "clear_field":
                effect = buildClearField(dto, fieldConditionMap);
                break;

            case "chip_damage":
                effect = buildChipDamage(dto, statusConditionMap, fieldConditionMap);
                break;

            case "stop_damage":
                effect = buildStopDamage(dto, fieldConditionMap);
                break;

            case "heal":
                effect = buildHeal(dto, fieldConditionMap);
                break;

            case "trap":
                effect = buildTrap(dto, typeMap);
                break;

            case "change_type":
                effect = buildChangeType(dto, typeMap);
                break;

            case "remove_immunities":
                effect = buildRemoveImmunities(dto, typeMap);
                break;

            case "fixed_int":
                effect = buildFixedInt(dto);
                break;

            case "fixed_double":
                effect = buildFixedDouble(dto);
                break;

            case "fixed_boolean":
                effect = buildFixedBoolean(dto);
                break;

            // temporário
            case "doubles_placeholder":
                effect = buildDoublesPlaceholder();
                break;

            case "other":
                effect = getOther(dto.otherID);
                break;

            default:
                return null;
        }

        return new AbilityEffect(type, activation, effect);
    }

    public static AbilityEffectFunction buildPrintMessage(
        AbilityEffectDTO dto,
        Map<String, AbilityTemplate> abilityMap
    ) {
        final String messageType = dto.messageType;
        final AbilityTemplate otherAbility = FactoryTools.convertObject(dto.otherAbility, abilityMap);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            String key = messageType;
            AbilityActivation dividerCondition = messageType.equals("start") ? AbilityActivation.Entry : AbilityActivation.SwitchOut;
            AbilityActivation lineBreakCondition = messageType.equals("start") ? AbilityActivation.AbilityUpdate : AbilityActivation.FaintUser;

            if (condition == dividerCondition) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else if (condition == lineBreakCondition) {
                System.out.println();
            }

            thisAbility.getMessages().print(key, Map.of(
                "Pokemon", self.getName(true, false),
                "Team", String.valueOf(opponent.getTeam())
            ));

            if (otherAbility != null) {
                otherAbility.getMessages().print(key, Map.of(
                    "Pokemon", self.getName(true, false),
                    "Team", String.valueOf(opponent.getTeam())
                ));
            }

            if (condition == dividerCondition) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildModifyStat(
        AbilityEffectDTO dto,
        Map<String, TypeTemplate> typeMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final double modifier = !dto.modifier.equals("max") ? FactoryTools.convertFraction(dto.modifier) : Double.MAX_VALUE;
        final double pinchHP = dto.pinchHP != null ? FactoryTools.convertFraction(dto.pinchHP) : 0;
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);
        final List<Category> categories = FactoryTools.convertEnumArray(dto.categories, Category.class);
        final boolean contact = dto.contact;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightHP = pinchHP != 0 ? self.getCurrentHP() <= self.getHP()*pinchHP : true;

            boolean rightType = false;
            if (types.length > 0) {
                for (TypeTemplate affectedType : types) {
                    if (move != null &&
                        move.getType(false, false).compare(affectedType)) {
                        rightType = true;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            boolean rightStatus = false;
            if (statusConditions.length > 0) {
                for (StatusConditionTemplate affectedStatus : statusConditions) {
                    if (self.getNonVolatileStatus().compare(affectedStatus)) {
                        rightStatus = true;
                        break;
                    }

                    for (StatusCondition volatileCondition : self.getVolatileStatusList()) {
                        if (volatileCondition.compare(affectedStatus)) {
                            rightStatus = true;
                            break;
                        }
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

                    if (Battle.getTerrain().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    for (FieldCondition fieldCondition : Battle.generalField) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }

                    for (FieldCondition fieldCondition : Battle.teamFields.get(self.getTeam())) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }
                }
            } else {
                rightField = true;
            }

            boolean rightCategory = false;
            if (categories.size() > 0) {
                for (Category modifiedCategory : categories) {
                    if (move != null &&
                        move.getCategory() == (modifiedCategory)) {
                        rightCategory = true;
                        break;
                    }
                }
            } else {
                rightCategory = true;
            }

            boolean rightContact = !contact || move != null && move.makesContact(true);

            if (!rightHP || !rightType || !rightStatus || !rightField || !rightCategory || !rightContact) {
                return 1.0;
            }

            return modifier;
        };
    }

    public static AbilityEffectFunction buildModifyStatAny(
        AbilityEffectDTO dto,
        Map<String, StatTemplate> statMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final StatTemplate[] stats = FactoryTools.convertObjectArray(dto.stats, statMap).toArray(new StatTemplate[0]);
        final double modifier = FactoryTools.convertFraction(dto.modifier);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (stat.getPokemon().getAbility().compare(thisAbility)) {
                return 1.0;
            }

            boolean wonderRoom = false;
            for (FieldCondition fieldCondition : Battle.generalField) {
                if (fieldCondition.compare(fieldConditionMap.get("wonder_room"))) {
                    wonderRoom = true;
                    break;
                }
            }

            for (StatTemplate modifiedStat : stats) {
                if (wonderRoom) {
                    if (modifiedStat.getNameShort() == StatName.Def) {
                        modifiedStat = statMap.get("SpD");
                    } else if (modifiedStat.getNameShort() == StatName.SpD) {
                        modifiedStat = statMap.get("Def");
                    }
                }

                if (stat.compare(modifiedStat)) {
                    return modifier;
                }
            }

            return 1.0;
        };
    }

    public static AbilityEffectFunction buildModifyStatStages(AbilityEffectDTO dto) {
        final double modifier = FactoryTools.convertFraction(dto.modifier);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            return (int) (statChangeStages * modifier);
        };
    }

    public static AbilityEffectFunction buildModifyPower(
        AbilityEffectDTO dto,
        Map<String, TypeTemplate> typeMap,
        Map<String, FieldConditionTemplate> fieldConditionMap,
        Map<String, AbilityTemplate> abilityMap
    ) {
        final double modifier = FactoryTools.convertFraction(dto.modifier);
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);
        final boolean trueTypes = dto.trueTypes;
        final MoveType moveType = FactoryTools.convertEnum(dto.moveType, MoveType.class);
        final InherentProperty[] moveProperties = FactoryTools.convertEnumArray(dto.moveProperties, InherentProperty.class).toArray(new InherentProperty[0]);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);
        final String specialCondition = dto.specialCondition != null ? dto.specialCondition : "";
        final boolean aura = dto.aura;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightType = false;
            if (types.length > 0) {
                for (TypeTemplate affectedType : types) {
                    if (move.getType(false, trueTypes).compare(affectedType)) {
                        rightType = true;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            boolean rightMoveType = moveType == null || Arrays.asList(move.getMoveTypes()).contains(moveType);

            boolean rightProperties = false;
            if (moveProperties.length > 0) {
                for (InherentProperty property : moveProperties) {
                    if (move.hasInherentProperty(property)) {
                        rightProperties = true;
                        break;
                    }
                }
            } else {
                rightProperties = true;
            }

            boolean rightField = false;
            if (fieldConditions.length > 0) {
                for (FieldConditionTemplate affectedField : fieldConditions) {
                    if (Battle.getWeather().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    if (Battle.getTerrain().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    for (FieldCondition fieldCondition : Battle.generalField) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }

                    for (FieldCondition fieldCondition : Battle.teamFields.get(self.getTeam())) {
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
                case "sheer_force":
                    rightSpecial = move.getSecondaryEffect() != null;
                    break;

                case "technician":
                    rightSpecial = move.getPower(false, true, 0) <= 60;
                    break;

                case "contact":
                    rightSpecial = move.makesContact(false);
                    break;

                default:
                    rightSpecial = true;
                    break;
            }

            if (!rightType || !rightMoveType || !rightProperties || !rightField || !rightSpecial) {
                return 1.0;
            }

            if (aura) {
                // TODO mudar para verificar todos os Pokémon em doubles
                boolean auraBreak = opponent.getAbility().compare(abilityMap.get("aura_break")) &&
                                    opponent.getAbility().shouldActivate(null);

                if (auraBreak) {
                    return 1/modifier;
                }
            }

            return modifier;
        };
    }

    public static AbilityEffectFunction buildModifyDamage(AbilityEffectDTO dto) {
        final double modifier = FactoryTools.convertFraction(dto.modifier);
        final String target = dto.target;
        final String modifyDamageType = dto.modifyDamageType != null ? dto.modifyDamageType : "";

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            Pokemon opposingPokemon = target.equals("self") ? opponent : self;

            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(move, opposingPokemon);
            effectivenessMultiplier /= Damage.notVeryEffective(move, opposingPokemon);

            boolean willModify;
            switch (modifyDamageType) {
                case "full_hp":
                    willModify = self.getCurrentHP() == self.getHP();
                    break;

                case "super_effective":
                    willModify = effectivenessMultiplier > 1;
                    break;

                case "not_very_effective":
                    willModify = effectivenessMultiplier < 1;
                    break;

                default:
                    willModify = true;
                    break;
            }

            if (!willModify) {
                return 1.0;
            }

            return modifier;
        };
    }

    public static AbilityEffectFunction buildBlockMove(
        AbilityEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);
        final MoveType moveType = FactoryTools.convertEnum(dto.moveType, MoveType.class);
        final InherentProperty[] moveProperties = FactoryTools.convertEnumArray(dto.moveProperties, InherentProperty.class).toArray(new InherentProperty[0]);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightType = false;
            if (types.length > 0) {
                for (TypeTemplate affectedType : types) {
                    if (move.getType(false, false).compare(affectedType)) {
                        rightType = true;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            boolean rightMoveType = moveType == null || Arrays.asList(move.getMoveTypes()).contains(moveType);

            boolean rightProperties = false;
            if (moveProperties.length > 0) {
                for (InherentProperty property : moveProperties) {
                    if (move.hasInherentProperty(property)) {
                        rightProperties = true;
                        break;
                    }
                }
            } else {
                rightProperties = true;
            }

            boolean rightTarget = move.targetsOpponent();

            if (!rightType || !rightMoveType || !rightProperties || !rightTarget) {
                return true;
            }

            thisAbility.getMessages().print("block move", Map.of(
                "Pokemon", self.getName(true, false)
            ));

            if (thisAbility.shouldActivate(AbilityActivation.AfterBlockMove)) {
                thisAbility.activate(self, opponent, move, type, damage, statusCondition, stat, statChangeStages, AbilityActivation.AfterBlockMove);
            }

            return false;
        };
    }

    public static AbilityEffectFunction buildBlockMoveAll(AbilityEffectDTO dto) {
        final Move[] moves = FactoryTools.convertMoveArray(dto.moves);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightMove = false;
            if (moves.length > 0) {
                for (Move blockedMove : moves) {
                    if (move.compare(blockedMove)) {
                        rightMove = true;
                        break;
                    }
                }
            } else {
                rightMove = true;
            }

            if (!rightMove) {
                return true;
            }

            String key = move.getUser() == self ? "block move self" : "block move";

            thisAbility.getMessages().print(key, Map.of(
                "Pokemon", move.getUser().getName(true, false),
                "Move", move.getName(),
                "Causer", self.getName(true, false)
            ));

            return false;
        };
    }

    public static AbilityEffectFunction buildStatChange(
        AbilityEffectDTO dto,
        Map<String, StatTemplate> statMap,
        Map<String, AbilityTemplate> abilityMap
    ) {
        final StatName[] stats = FactoryTools.convertEnumArray(dto.stats, StatName.class).toArray(new StatName[0]);
        final int[] stages = dto.stages;
        final String target = dto.target;
        final AbilityTemplate otherAbility = FactoryTools.convertObject(dto.otherAbility, abilityMap);
        final String statChangeCondition = dto.statChangeCondition != null ? dto.statChangeCondition : "";
        final boolean intimidate = dto.intimidate;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.TurnEnd && self.justSwitchedIn()) {
                return null;
            }

            Pokemon targetPokemon = target.equals("self") ? self : opponent;

            boolean rightSpecial = false;
            switch (statChangeCondition) {
                case "pinch":
                    rightSpecial = targetPokemon.getCurrentHP() <= targetPokemon.getHP()/2.0 &&
                                   targetPokemon.getCurrentHP() + damage.amount >= targetPokemon.getHP()/2.0;
                    break;

                case "stat_drop":
                    rightSpecial = statChangeStages < 0;
                    break;

                default:
                    rightSpecial = true;
                    break;
            }

            boolean intimidateNotBlocked = false;
            if (intimidate) {
                if (targetPokemon.getAbility().shouldActivate(AbilityActivation.TryIntimidate) &&
                    !(boolean) targetPokemon.getAbility().activate(targetPokemon, self, move, type, damage, statusCondition, stat, statChangeStages, AbilityActivation.TryIntimidate)) {
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    System.out.println(targetPokemon.getName(true, true) + " was not intimidated thanks to its " + targetPokemon.getAbility().getName() + "!");

                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                } else {
                    intimidateNotBlocked = true;
                }
            } else {
                intimidateNotBlocked = true;
            }

            if (rightSpecial && intimidateNotBlocked) {
                if (condition == AbilityActivation.Entry ||
                    condition == AbilityActivation.TurnEnd ||
                    condition == AbilityActivation.Intimidated) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                for (int i = 0; i < stats.length; i++) {
                    StatName changedStat = stats[i];
                    int changeStages = stages[i];

                    Ability printedAbility = otherAbility != null ?
                        new Ability(otherAbility, true, thisAbility.getPokemon()) : thisAbility;

                    targetPokemon.getStat(changedStat).change(
                        changeStages,
                        printedAbility,
                        targetPokemon == self,
                        true,
                        false
                    );
                }

                if (condition == AbilityActivation.Entry ||
                    condition == AbilityActivation.TurnEnd ||
                    condition == AbilityActivation.Intimidated) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (intimidate) {
                if (targetPokemon.getAbility().shouldActivate(AbilityActivation.Intimidated)) {
                    targetPokemon.getAbility().activate(targetPokemon, self, move, type, damage, statusCondition, stat, statChangeStages, AbilityActivation.Intimidated);
                }
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildStatusCondition(
        AbilityEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate inflictedStatus = FactoryTools.convertObject(dto.statusCondition, statusConditionMap);
        final double chance = dto.chance != null ? FactoryTools.convertFraction(dto.chance) : 1;
        final String target = dto.target;
        final String specialCondition = dto.specialCondition != null ? dto.specialCondition : "";

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (Math.random() < chance) {
                Pokemon targetPokemon = target.equals("self") ? self : opponent;

                boolean willInflict;
                switch (specialCondition) {
                    case "contact":
                        willInflict = move.makesContact(false);
                        break;

                    case "poisoned":
                        willInflict = statusCondition.compare(statusConditionMap.get("poison")) ||
                                      statusCondition.compare(statusConditionMap.get("bad_poison"));
                        break;

                    default:
                        willInflict = true;
                        break;
                }

                boolean canInflict = !inflictedStatus.isVolatileCondition() && targetPokemon.getNonVolatileStatus().compare(statusConditionMap.get("none")) ||
                                     inflictedStatus.isVolatileCondition() && (targetPokemon.getVolatileStatus(inflictedStatus) == null ||
                                     inflictedStatus.isStackable());

                boolean infatuationCheck = !inflictedStatus.compare(statusConditionMap.get("infatuation")) ||
                                           !self.getGender().equals("Unknown") &&
                                           !targetPokemon.getGender().equals("Unknown") &&
                                           !targetPokemon.getGender().equals(self.getGender());

                if (!willInflict || !canInflict || !infatuationCheck) {
                    return null;
                }

                inflictedStatus.apply(targetPokemon, thisAbility, null, true, false);
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildCureStatus(
        AbilityEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);
        final double chance = dto.chance != null ? FactoryTools.convertFraction(dto.chance) : 1;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (Math.random() < chance) {
                boolean rightField = false;
                if (fieldConditions.length > 0) {
                    for (FieldConditionTemplate affectedField : fieldConditions) {
                        if (Battle.getWeather().compare(affectedField)) {
                            rightField = true;
                            break;
                        }

                        if (Battle.getTerrain().compare(affectedField)) {
                            rightField = true;
                            break;
                        }

                        for (FieldCondition fieldCondition : Battle.generalField) {
                            if (fieldCondition.compare(affectedField)) {
                                rightField = true;
                                break;
                            }
                        }

                        for (FieldCondition fieldCondition : Battle.teamFields.get(self.getTeam())) {
                            if (fieldCondition.compare(affectedField)) {
                                rightField = true;
                                break;
                            }
                        }
                    }
                } else {
                    rightField = true;
                }

                if (!rightField) {
                    return null;
                }

                for (StatusConditionTemplate curedStatus : statusConditions) {
                    boolean rightCondition;
                    if (condition == AbilityActivation.StatusConditionOnUser) {
                        rightCondition = statusCondition.compare(curedStatus);
                    } else {
                        if (!curedStatus.isVolatileCondition()) {
                            rightCondition = self.getNonVolatileStatus().compare(curedStatus);
                        } else {
                            rightCondition = self.getVolatileStatus(curedStatus) != null;
                        }
                    }

                    if (rightCondition) {
                        if (condition == AbilityActivation.TurnEnd) {
                            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        }

                        if (thisAbility.getMessages() != null) {
                            thisAbility.getMessages().print("cure status", Map.of(
                                "Pokemon", self.getName(true, false),
                                "Status", curedStatus.getName()
                            ));
                        }

                        if (!curedStatus.isVolatileCondition()) {
                            self.endNonVolatileStatus(false);
                        } else {
                            self.endVolatileStatus(curedStatus, false);
                        }

                        if (condition == AbilityActivation.TurnEnd) {
                            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        }
                    }
                }
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildStatusImmunity(
        AbilityEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            for (StatusConditionTemplate curedStatus : statusConditions) {
                if (statusCondition.compare(curedStatus)) {
                    return true;
                }
            }

            return false;
        };
    }

    public static AbilityEffectFunction buildSetWeather(
        AbilityEffectDTO dto,
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, ItemTemplate> itemMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final FieldConditionTemplate weather = FactoryTools.convertObject(dto.fieldCondition, fieldConditionMap);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean primal = weather.compare(fieldConditionMap.get("sun")) && self.compare(pokemonMap.get("groudon"), true) && self.getItem().compare(itemMap.get("red_orb")) ||
                             weather.compare(fieldConditionMap.get("rain")) && self.compare(pokemonMap.get("kyogre"), true) && self.getItem().compare(itemMap.get("blue_orb"));

            if (primal) {
                return null;
            }

            boolean canActivate = weather.apply(thisAbility, true, null, false);

            if (canActivate) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                weather.apply(thisAbility, false, null, true);

                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        };
    }

    public static AbilityEffectFunction buildSetPrimalWeather(
        AbilityEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final FieldConditionTemplate weather = FactoryTools.convertObject(dto.fieldCondition, fieldConditionMap);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) {
                boolean canActivate = weather.apply(thisAbility, true, null, false);

                if (canActivate) {
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    weather.apply(thisAbility, false, null, true);

                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            }

            if (condition == AbilityActivation.SwitchOut || condition == AbilityActivation.Removed || condition == AbilityActivation.FaintUser) {
                if (Battle.getTrueWeather().compare(weather) &&
                    (
                        !Battle.opponentActivePokemon.getAbility().compare(thisAbility) ||
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
        };
    }

    public static AbilityEffectFunction buildSetTerrain(
        AbilityEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final FieldConditionTemplate terrain = FactoryTools.convertObject(dto.fieldCondition, fieldConditionMap);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean canActivate = terrain.apply(thisAbility, true, null, false);

            if (canActivate) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                terrain.apply(thisAbility, false, null, true);

                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        };
    }

    public static AbilityEffectFunction buildParadoxFieldAndBoost(
        AbilityEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final String paradoxType = dto.paradoxType;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            FieldConditionTemplate fieldCondition = paradoxType.equals("past") ? fieldConditionMap.get("sun") : fieldConditionMap.get("electric_terrain");
            FieldCondition activeField = paradoxType.equals("past") ? Battle.getWeather() : Battle.getTerrain();
            AbilityActivation fieldChangeActivation = paradoxType.equals("past") ? AbilityActivation.WeatherChange : AbilityActivation.TerrainChange;
            AbilityActivation boostActivation = paradoxType.equals("past") ? AbilityActivation.AttackCalc : AbilityActivation.SpecialAttackCalc;

            boolean fieldActive = paradoxType.equals("past") ? Battle.getWeather().compare(fieldCondition) : Battle.getTerrain().compare(fieldCondition);

            if ((condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate) &&
                !fieldActive) {
                boolean canActivate = fieldCondition.apply(thisAbility, true, null, false);

                if (canActivate) {
                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    } else {
                        System.out.println();
                    }

                    thisAbility.getMessages().print("start", Map.of(
                        "Pokemon", self.getName(true, false)
                    ));

                    fieldCondition.apply(thisAbility, false, null, false);

                    if (condition == AbilityActivation.Entry) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }
                }
            } else if ((condition == AbilityActivation.Entry || condition == AbilityActivation.AbilityUpdate ||
                        condition == fieldChangeActivation && activeField.getCause() != thisAbility) &&
                       fieldActive) {
                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                } else {
                    System.out.println();
                }

                thisAbility.getMessages().print("activate", Map.of(
                    "Pokemon", self.getName(true, false)
                ));

                if (condition == AbilityActivation.Entry) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }

            if (condition == boostActivation) {
                if (fieldActive) {
                    return 5461.0/4096.0;
                }
                return 1.0;
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildClearField(
        AbilityEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final String[] fieldTypes = dto.fieldTypes;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            if (condition == AbilityActivation.Entry) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            } else {
                System.out.println();
            }

            thisAbility.getMessages().print("start", Map.of(
                "Pokemon", self.getName(true, false)
            ));

            if (Arrays.asList(fieldTypes).contains("weather")) {
                Battle.getTrueWeather().end();
            }

            if (Arrays.asList(fieldTypes).contains("terrain")) {
                Battle.getTerrain().end();
            }

            if (Arrays.asList(fieldTypes).contains("general_field")) {
                for (FieldCondition fieldCondition : Battle.generalField) {
                    if (!fieldCondition.compare(fieldConditionMap.get("uproar"))) {
                        fieldCondition.end();
                    }
                }
            }

            if (Arrays.asList(fieldTypes).contains("team_fields")) {
                for (ArrayList<FieldCondition> field : Battle.teamFields) {
                    for (FieldCondition fieldCondition : field) {
                        fieldCondition.end(field);
                    }
                }
            }

            if (condition == AbilityActivation.Entry) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildChipDamage(
        AbilityEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final String target = dto.target;
        final double damageValue = FactoryTools.convertFraction(dto.damageFraction);
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            Pokemon targetPokemon = target.equals("self") ? self : opponent;

            boolean rightStatus = false;
            if (statusConditions.length > 0) {
                for (StatusConditionTemplate affectedStatus : statusConditions) {
                    if (targetPokemon.getNonVolatileStatus().compare(affectedStatus)) {
                        rightStatus = true;
                        break;
                    }

                    for (StatusCondition volatileCondition : targetPokemon.getVolatileStatusList()) {
                        if (volatileCondition.compare(affectedStatus)) {
                            rightStatus = true;
                            break;
                        }
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

                    if (Battle.getTerrain().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    for (FieldCondition fieldCondition : Battle.generalField) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }

                    for (FieldCondition fieldCondition : Battle.teamFields.get(targetPokemon.getTeam())) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }
                }
            } else {
                rightField = true;
            }

            if (!rightStatus || !rightField) {
                return null;
            }

            int chipDamage = (int) Math.floor(targetPokemon.getHP()*damageValue);
            chipDamage = Integer.max(chipDamage, 1);

            String message = thisAbility.getMessages().getMessage("chip damage", Map.of(
                "Pokemon", targetPokemon.getName(true, false),
                "Causer", self.getName(true, false)
            ));

            Damage.indirectDamage(targetPokemon, self, chipDamage, 0, DamageSource.StatusCondition, thisAbility, message, condition == AbilityActivation.TurnEnd);

            return null;
        };
    }

    public static AbilityEffectFunction buildStopDamage(
        AbilityEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final DamageSource sourceNotBlocked = FactoryTools.convertEnum(dto.sourceNotBlocked, DamageSource.class);
        final DamageSource sourceBlocked = FactoryTools.convertEnum(dto.sourceBlocked, DamageSource.class);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightField = false;
            if (fieldConditions.length > 0) {
                for (FieldConditionTemplate affectedField : fieldConditions) {
                    if (Battle.getWeather().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    if (Battle.getTerrain().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    for (FieldCondition fieldCondition : Battle.generalField) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }

                    for (FieldCondition fieldCondition : Battle.teamFields.get(self.getTeam())) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }
                }
            } else {
                rightField = true;
            }

            boolean rightSource;
            if (sourceBlocked != null) {
                rightSource = damage.sourceType == sourceBlocked;
            } else if (sourceNotBlocked != null) {
                rightSource = damage.sourceType != sourceNotBlocked;
            } else {
                rightSource = true;
            }

            if (!rightField || !rightSource) {
                return true;
            }

            return false;
        };
    }

    public static AbilityEffectFunction buildHeal(
        AbilityEffectDTO dto,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final double healValue = FactoryTools.convertFraction(dto.healFraction);
        final FieldConditionTemplate[] fieldConditions = FactoryTools.convertObjectArray(dto.fieldConditions, fieldConditionMap).toArray(new FieldConditionTemplate[0]);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightField = false;
            if (fieldConditions.length > 0) {
                for (FieldConditionTemplate affectedField : fieldConditions) {
                    if (Battle.getWeather().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    if (Battle.getTerrain().compare(affectedField)) {
                        rightField = true;
                        break;
                    }

                    for (FieldCondition fieldCondition : Battle.generalField) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }

                    for (FieldCondition fieldCondition : Battle.teamFields.get(self.getTeam())) {
                        if (fieldCondition.compare(affectedField)) {
                            rightField = true;
                            break;
                        }
                    }
                }
            } else {
                rightField = true;
            }

            if (!rightField) {
                return null;
            }

            if (condition == AbilityActivation.Entry || condition == AbilityActivation.TurnEnd) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            int healedDamage = (int) Math.floor(self.getHP()*healValue);
            healedDamage = Integer.max(healedDamage, 1);

            if (thisAbility.getMessages() != null) {
                thisAbility.getMessages().print("heal", Map.of(
                    "Pokemon", self.getName(true, false)
                ));
            }

            Damage.heal(self, null, healedDamage, true, false);

            if (condition == AbilityActivation.Entry || condition == AbilityActivation.TurnEnd) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        };
    }

    public static AbilityEffectFunction buildTrap(
        AbilityEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final String trapType = dto.trapType != null ? dto.trapType : "";

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean willTrap;
            switch (trapType) {
                case "magnet_pull":
                    willTrap = opponent.hasType(typeMap.get("steel"));
                    break;

                default:
                    willTrap = true;
                    break;
            }

            if (!willTrap) {
                return false;
            }

            thisAbility.getMessages().print("block switch", Map.of(
                "Pokemon", self.getName(true, false)
            ));

            return true;
        };
    }

    public static AbilityEffectFunction buildChangeType(
        AbilityEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final TypeTemplate oldType = FactoryTools.convertObject(dto.oldType, typeMap);
        final TypeTemplate newType = FactoryTools.convertObject(dto.newType, typeMap);
        final MoveType moveType = FactoryTools.convertEnum(dto.moveType, MoveType.class);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightType = oldType == null || move.getType(false, true).compare(oldType);
            boolean rightMoveType = moveType == null || Arrays.asList(move.getMoveTypes()).contains(moveType);

            if (!rightType || !rightMoveType) {
                return type;
            }

            return new Type(newType, move);
        };
    }

    public static AbilityEffectFunction buildRemoveImmunities(
        AbilityEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            boolean rightType = false;
            if (types.length > 0) {
                for (TypeTemplate affectedType : types) {
                    if (type.compare(affectedType)) {
                        rightType = true;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            if (!rightType) {
                return type.getIneffective(null, true);
            }

            return new TypeTemplate[0];
        };
    }

    public static AbilityEffectFunction buildFixedInt(AbilityEffectDTO dto) {
        final int intValue = dto.intValue;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            return intValue;
        };
    }

    public static AbilityEffectFunction buildFixedDouble(AbilityEffectDTO dto) {
        final double doubleValue = dto.doubleValue;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            return doubleValue;
        };
    }

    public static AbilityEffectFunction buildFixedBoolean(AbilityEffectDTO dto) {
        final boolean booleanValue = dto.booleanValue;

        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            return booleanValue;
        };
    }

    public static AbilityEffectFunction buildDoublesPlaceholder() {
        return (thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition) -> {
            return null;
        };
    }

    private static AbilityEffectFunction getOther(String otherID) {
        switch (otherID) {
            case "air_lock":
                return OtherAbilityEffects.air_lock;

            case "antithesis":
                return OtherAbilityEffects.antithesis;

            case "block_stat_drops":
                return OtherAbilityEffects.block_stat_drops;

            case "darkest_day":
                return OtherAbilityEffects.darkest_day;

            case "disable_permanent":
                return OtherAbilityEffects.disable_permanent;

            case "download":
                return OtherAbilityEffects.download;

            case "flash_fire":
                return OtherAbilityEffects.flash_fire;

            case "forecast":
                return OtherAbilityEffects.forecast;

            case "frisk":
                return OtherAbilityEffects.frisk;

            case "block_forced_switch":
                return OtherAbilityEffects.block_forced_switch;

            case "illusion":
                return OtherAbilityEffects.illusion;

            case "protean":
                return OtherAbilityEffects.protean;

            case "magic_bounce":
                return OtherAbilityEffects.magic_bounce;

            case "magician":
                return OtherAbilityEffects.magician;

            case "power_construct":
                return OtherAbilityEffects.power_construct;

            case "prankster":
                return OtherAbilityEffects.prankster;

            case "block_secondary_effect":
                return OtherAbilityEffects.block_secondary_effect;

            case "slow_start":
                return OtherAbilityEffects.slow_start;

            case "sturdy":
                return OtherAbilityEffects.sturdy;

            case "synchronize":
                return OtherAbilityEffects.synchronize;

            case "tera_shell":
                return OtherAbilityEffects.tera_shell;

            case "tera_shift":
                return OtherAbilityEffects.tera_shift;

            case "ultimate_weapon":
                return OtherAbilityEffects.ultimate_weapon;

            case "unburden":
                return OtherAbilityEffects.unburden;

            case "unseen_fist":
                return OtherAbilityEffects.unseen_fist;

            case "zero_to_hero":
                return OtherAbilityEffects.zero_to_hero;

            default:
                return null;
        }
    }
}
