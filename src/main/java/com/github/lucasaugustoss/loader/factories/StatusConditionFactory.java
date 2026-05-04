package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.effects.StatusConditionEffect;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.StatusConditionDTO;
import com.github.lucasaugustoss.loader.dtos.StatusConditionEffectDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class StatusConditionFactory {
    private final Map<String, StatusConditionTemplate> statusConditionList = new HashMap<>();

    public Map<String, StatusConditionTemplate> build(JSONLoader data) {
        createStatusCondition(data);
        assignRelationships(data);
        return Map.copyOf(statusConditionList);
    }

    private void createStatusCondition(JSONLoader data) {
        for (StatusConditionDTO dto : data.getStatusConditionData().values()) {
            StatusConditionTemplate statusCondition = new StatusConditionTemplate(
                dto.index,
                dto.id,
                dto.name,
                dto.volatileCondition,
                dto.similarCondition,
                dto.effect,
                dto.stackable,
                FactoryTools.convertMessage(dto.messages),
                dto.defaultParams != null ? dto.defaultParams : new HashMap<>()
            );

            statusConditionList.put(dto.id, statusCondition);
        }
    }

    private void assignRelationships(JSONLoader data) {
        for (StatusConditionDTO dto : data.getStatusConditionData().values()) {
            StatusConditionTemplate condition = statusConditionList.get(dto.id);
            condition.setSimilarCondition(FactoryTools.convertObject(dto.similarCondition, statusConditionList));
        }
    }

    public void convertEffects(
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, MoveTemplate> moveMap
    ) {
        for (StatusConditionTemplate statusCondition : statusConditionMap.values()) {
            ArrayList<StatusConditionEffect> effects = new ArrayList<>();
            if (statusCondition.getEffectDTOs() != null) {
                for (StatusConditionEffectDTO effect : statusCondition.getEffectDTOs()) {
                    effects.add(StatusConditionEffectFactory.buildEffect(effect, typeMap, moveMap, statusConditionMap));
                }
            }
            statusCondition.setEffects(effects.toArray(new StatusConditionEffect[0]));
        }
    }
}
