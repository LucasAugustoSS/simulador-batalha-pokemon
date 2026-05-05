package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.effects.MoveEffect;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.AbilityTemplate;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.MoveDTO;
import com.github.lucasaugustoss.loader.dtos.MoveEffectDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class MoveFactory {
    private final Map<String, MoveTemplate> moveList = new HashMap<>();

    public Map<String, MoveTemplate> build(JSONLoader data) {
        createMove(data);
        return Map.copyOf(moveList);
    }

    private void createMove(JSONLoader data) {
        for (MoveDTO dto : data.getMoveData().values()) {
            MoveTemplate move = new MoveTemplate(
                dto.index,
                dto.id,
                dto.name,
                dto.notTrueMove,
                dto.debug,
                dto.zMove,
                dto.signatureZMove,
                dto.type != null ? dto.type : "typeless",
                FactoryTools.convertEnum(dto.category, Category.class),
                dto.PP,
                dto.power,
                dto.zMovePower,
                dto.accuracy > 0 ? dto.accuracy : -1,
                dto.critRatio,
                dto.contact,
                dto.priority,
                dto.hits != null ? dto.hits : new int[] {1},
                FactoryTools.convertEnum(dto.moveTarget, MoveTarget.class),
                dto.primaryEffect,
                dto.secondaryEffect,
                dto.zEffect,
                dto.moveTypes != null ? FactoryTools.convertEnumArray(dto.moveTypes, MoveType.class).toArray(new MoveType[0]) : new MoveType[] {MoveType.Regular},
                FactoryTools.convertEnumArray(dto.inherentProperties, InherentProperty.class).toArray(new InherentProperty[0]),
                dto.exclusiveUser,
                dto.exclusiveForm,
                dto.messages
            );

            moveList.put(dto.id, move);
        }
    }

    public void convertObjects(
        Map<String, MoveTemplate> moveMap,
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, AbilityTemplate> abilityMap,
        Map<String, ItemTemplate> itemMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap,
        Map<String, Message> messageMap
    ) {
        for (MoveTemplate move : moveMap.values()) {
            move.setType(FactoryTools.convertObject(move.getTypeID(), typeMap));
            move.setExclusiveUser(FactoryTools.convertObject(move.getExclusiveUserID(), pokemonMap));

            ArrayList<MoveEffect> primaryEffects = new ArrayList<>();
            if (move.getPrimaryEffectDTOs() != null) {
                for (MoveEffectDTO effect : move.getPrimaryEffectDTOs()) {
                    primaryEffects.add(MoveEffectFactory.buildEffect(
                        effect,
                        pokemonMap,
                        typeMap,
                        abilityMap,
                        itemMap,
                        statusConditionMap,
                        fieldConditionMap,
                        moveMap,
                        messageMap
                    ));
                }
            }
            move.setPrimaryEffects(primaryEffects.toArray(new MoveEffect[0]));

            ArrayList<MoveEffect> secondaryEffects = new ArrayList<>();
            if (move.getSecondaryEffectDTOs() != null) {
                for (MoveEffectDTO effect : move.getSecondaryEffectDTOs()) {
                    secondaryEffects.add(MoveEffectFactory.buildEffect(
                        effect,
                        pokemonMap,
                        typeMap,
                        abilityMap,
                        itemMap,
                        statusConditionMap,
                        fieldConditionMap,
                        moveMap,
                        messageMap
                    ));
                }
            }
            move.setSecondaryEffects(secondaryEffects.toArray(new MoveEffect[0]));

            if (move.getZEffectDTO() != null) {
                move.setZEffect(
                    MoveEffectFactory.buildEffect(
                        move.getZEffectDTO(),
                        pokemonMap,
                        typeMap,
                        abilityMap,
                        itemMap,
                        statusConditionMap,
                        fieldConditionMap,
                        moveMap,
                        messageMap
                    )
                );
            }

            move.setMessages(FactoryTools.convertObject(move.getMessagesID(), messageMap));
        }
    }
}
