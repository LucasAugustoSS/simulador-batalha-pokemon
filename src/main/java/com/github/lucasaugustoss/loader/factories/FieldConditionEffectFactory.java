package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.FieldConditionEffectFunction;
import com.github.lucasaugustoss.data.objects.effects.FieldConditionEffect;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.other.DamageSource;
import com.github.lucasaugustoss.loader.dtos.FieldConditionEffectDTO;
import com.github.lucasaugustoss.loader.factories.otherEffects.OtherFieldConditionEffects;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class FieldConditionEffectFactory {
    public static FieldConditionEffect buildEffect(
        FieldConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        if (dto == null) {
            return null;
        }
        
        String type = dto.type;
        FieldActivation[] activation = FactoryTools.convertEnumArray(dto.activation, FieldActivation.class).toArray(new FieldActivation[0]);
        FieldConditionEffectFunction effect = null;

        switch (type) {
            case "modify_damage":
                effect = buildModifyDamage(dto, typeMap, fieldConditionMap);
                break;

            case "modify_stat":
                effect = buildModifyStat(dto, typeMap);
                break;

            case "block_status_condition":
                effect = buildBlockStatusCondition(dto, statusConditionMap);
                break;

            case "block_move":
                effect = buildBlockMove(dto, typeMap);
                break;

            case "block_move_selection":
                effect = buildBlockMoveSelection(dto);
                break;

            case "chip_damage":
                effect = buildChipDamage(dto, typeMap);
                break;

            case "protection":
                effect = buildProtection(dto);
                break;

            case "other":
                effect = getOther(dto.otherID);
                break;

            default:
                return null;
        }

        return new FieldConditionEffect(type, activation, effect);
    }

    private static FieldConditionEffectFunction buildModifyDamage(
        FieldConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);
        final Move[] moves = FactoryTools.convertMoveArray(dto.moves);
        final List<Category> categories = FactoryTools.convertEnumArray(dto.categories, Category.class);
        final double[] modifiers = FactoryTools.convertFractionArray(dto.modifiers);
        final boolean critBypasses = dto.critBypasses;
        final boolean screensSuppress = dto.screensSuppress;

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            if (critBypasses && criticalHit) {
                return 1.0;
            }

            boolean hasReflect = false;
            boolean hasLightScreen = false;
            for (FieldCondition fieldCondition : Battle.teamFields.get(opponent.getTeam())) {
                if (fieldCondition.compare(fieldConditionMap.get("reflect"))) {
                    hasReflect = true;
                }
                if (fieldCondition.compare(fieldConditionMap.get("light_screen"))) {
                    hasLightScreen = true;
                }
            }

            if (screensSuppress && (
                hasReflect && move.getCategory() == Category.Physical ||
                hasLightScreen && move.getCategory() == Category.Special
            )) {
                return 1.0;
            }

            boolean rightType = false;
            int modifierIndex = 0;
            if (types.length > 0) {
                for (int i = 0; i < types.length; i++) {
                    TypeTemplate modifiedType = types[i];
                    if (move.getType(false, false).compare(modifiedType)) {
                        rightType = true;
                        modifierIndex = i;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            boolean rightMove = false;
            if (moves.length > 0) {
                for (Move modifiedMove : moves) {
                    if (move.compare(modifiedMove)) {
                        rightMove = true;
                        break;
                    }
                }
            } else {
                rightMove = true;
            }

            boolean rightCategory = false;
            if (categories.size() > 0) {
                for (Category modifiedCategory : categories) {
                    if (move.getCategory() == (modifiedCategory)) {
                        rightCategory = true;
                        break;
                    }
                }
            } else {
                rightCategory = true;
            }

            if (!rightType || !rightMove || !rightCategory) {
                return 1.0;
            }

            return modifiers[modifierIndex];
        };
    }

    private static FieldConditionEffectFunction buildModifyStat(
        FieldConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final double modifier = FactoryTools.convertFraction(dto.modifier);
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            boolean rightType = false;
            if (types.length > 0) {
                for (TypeTemplate modifiedType : types) {
                    if (pokemon.hasType(modifiedType)) {
                        rightType = true;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            if (!rightType) {
                return 1.0;
            }

            return modifier;
        };
    }

    private static FieldConditionEffectFunction buildBlockStatusCondition(
        FieldConditionEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate[] statusConditions = FactoryTools.convertObjectArray(dto.statusConditions, statusConditionMap).toArray(new StatusConditionTemplate[0]);

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            for (StatusConditionTemplate blockedCondition : statusConditions) {
                if (statusCondition.compare(blockedCondition)) {
                    if (showMessages &&
                        thisCondition.getMessages() != null &&
                        thisCondition.getMessages().hasMessage("block status")) {
                        thisCondition.getMessages().print("block status", Map.of(
                            "Pokemon", pokemon.getName(true, false)
                        ));
                    }
                    return true;
                }
            }
            return false;
        };
    }

    private static FieldConditionEffectFunction buildBlockMove(
        FieldConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final TypeTemplate[] types = FactoryTools.convertObjectArray(dto.types, typeMap).toArray(new TypeTemplate[0]);
        final List<Category> categories = FactoryTools.convertEnumArray(dto.categories, Category.class);
        final boolean blockPriority = dto.blockPriority;

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            boolean rightType = false;
            if (types.length > 0) {
                for (TypeTemplate modifiedType : types) {
                    if (move.getType(false, false).compare(modifiedType)) {
                        rightType = true;
                        break;
                    }
                }
            } else {
                rightType = true;
            }

            boolean rightCategory = false;
            if (categories.size() > 0) {
                for (Category modifiedCategory : categories) {
                    if (move.getCategory() == (modifiedCategory)) {
                        rightCategory = true;
                        break;
                    }
                }
            } else {
                rightCategory = true;
            }

            boolean rightPriority = false;
            if (blockPriority) {
                if (move.getUser() == opponent &&
                    move.targetsOpponent() &&
                    opponent.getTeam() != pokemon.getTeam() &&
                    move.getPriority() > 0) {
                    rightPriority = true;
                }
            } else {
                rightPriority = true;
            }

            if (rightType && rightCategory && rightPriority) {
                if (showMessages &&
                    thisCondition.getMessages() != null &&
                    thisCondition.getMessages().hasMessage("block move")) {
                    thisCondition.getMessages().print("block move", Map.of(
                        "Pokemon", pokemon.getName(true, false)
                    ));
                }
                return false;
            }

            return true;
        };
    }

    private static FieldConditionEffectFunction buildBlockMoveSelection(FieldConditionEffectDTO dto) {
        final List<InherentProperty> blockedProperties = FactoryTools.convertEnumArray(dto.moveProperties, InherentProperty.class);

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            for (InherentProperty property : blockedProperties) {
                if (move.hasInherentProperty(property)) {
                    if (showMessages &&
                        thisCondition.getMessages() != null &&
                        thisCondition.getMessages().hasMessage("block move selection")) {
                        thisCondition.getMessages().print("block move selection", Map.of(
                            "Pokemon", move.getUser().getName(true, false),
                            "Move", move.getName()
                        ));
                    }
                    return false;
                }
            }
            return true;
        };
    }

    private static FieldConditionEffectFunction buildChipDamage(
        FieldConditionEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final double damage = FactoryTools.convertFraction(dto.damageFraction);
        final TypeTemplate[] immuneTypes = FactoryTools.convertObjectArray(dto.immuneTypes, typeMap).toArray(new TypeTemplate[0]);

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            for (TypeTemplate immuneType : immuneTypes) {
                for (Type pokemonType : pokemon.getTypes()) {
                    if (!pokemonType.isSuppressed() &&
                        pokemonType.compare(immuneType)) {
                        return null;
                    }
                }
            }

            int chipDamage = Integer.max((int) Math.floor(pokemon.getHP()*damage), 1);

            String message = null;
            if (showMessages &&
                thisCondition.getMessages() != null &&
                thisCondition.getMessages().hasMessage("chip damage")) {
                message = thisCondition.getMessages().getMessage("chip damage");

                boolean capitalized = message.startsWith("(Pokemon)");
                message = message.replace("(Pokemon)", pokemon.getName(true, capitalized));
            }
            Damage.indirectDamage(pokemon, null, chipDamage, DamageSource.FieldCondition, thisCondition, message, true);
            return null;
        };
    }

    private static FieldConditionEffectFunction buildProtection(FieldConditionEffectDTO dto) {
        final String protectionType = dto.protectionType;

        return (FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) -> {
            boolean affected = move != null;

            if (affected) {
                switch (protectionType) {
                    case "quick_guard":
                        affected = move.targetsOpponent() && move.getPriority() > 0;
                        break;
                
                    case "wide_guard":
                        affected = (move.getMoveTarget() == MoveTarget.AllOpponents || move.getMoveTarget() == MoveTarget.AllAdjacent);
                        break;
                
                    default:
                        break;
                }
            }

            if (activation == FieldActivation.OpponentTryUseMove) {
                if (opponent.getAbility().shouldActivate(AbilityActivation.OpponentTryProtect) &&
                    !((boolean) opponent.getAbility().activate(opponent, pokemon, move, null, null, null, null, 0, AbilityActivation.OpponentTryProtect))) {
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
        };
    }

    private static FieldConditionEffectFunction getOther(String otherID) {
        switch (otherID) {
            case "delta_stream":
                return OtherFieldConditionEffects.delta_stream;

            case "chip_heal":
                return OtherFieldConditionEffects.chip_heal;

            case "spikes":
                return OtherFieldConditionEffects.spikes;

            case "stealth_rock":
                return OtherFieldConditionEffects.stealth_rock;

            case "sticky_web":
                return OtherFieldConditionEffects.sticky_web;

            case "block_stat_drops":
                return OtherFieldConditionEffects.block_stat_drops;

            case "cancel":
                return OtherFieldConditionEffects.cancel;

            case "magic_room":
                return OtherFieldConditionEffects.magic_room;

            case "wonder_room":
                return OtherFieldConditionEffects.wonder_room;

            case "uproar_countdown":
                return OtherFieldConditionEffects.uproar_countdown;

            case "gravity":
                return OtherFieldConditionEffects.gravity;

            case "echoed_voice":
                return OtherFieldConditionEffects.echoed_voice;

            case "ion_deluge":
                return OtherFieldConditionEffects.ion_deluge;

            default:
                return null;
        }
    }
}
