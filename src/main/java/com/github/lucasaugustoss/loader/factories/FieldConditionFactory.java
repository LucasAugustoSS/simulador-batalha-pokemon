package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.effects.FieldConditionEffect;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.fieldConditions.FieldConditionType;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.FieldConditionDTO;
import com.github.lucasaugustoss.loader.dtos.FieldConditionEffectDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class FieldConditionFactory {
    private final Map<String, FieldConditionTemplate> fieldConditionList = new HashMap<>();

    public Map<String, FieldConditionTemplate> build(JSONLoader data) {
        createFieldCondition(data);
        return Map.copyOf(fieldConditionList);
    }

    private void createFieldCondition(JSONLoader data) {
        for (FieldConditionDTO dto : data.getFieldConditionData().values()) {
            FieldConditionTemplate fieldCondition = new FieldConditionTemplate(
                dto.index,
                dto.id,
                dto.name,
                FactoryTools.convertEnum(dto.type, FieldConditionType.class),
                dto.effect,
                dto.hasCounter,
                FactoryTools.convertMessage(dto.messages)
            );

            fieldConditionList.put(dto.id, fieldCondition);
        }
    }

    public void convertEffects(
        Map<String, FieldConditionTemplate> fieldConditionMap,
        Map<String, TypeTemplate> typeMap
    ) {
        for (FieldConditionTemplate fieldCondition : fieldConditionMap.values()) {
            if (fieldCondition.getEffectDTO() == null) {
                continue;
            }

            ArrayList<FieldConditionEffect> effects = new ArrayList<>();
            for (FieldConditionEffectDTO effect : fieldCondition.getEffectDTO()) {
                effects.add(FieldConditionEffectFactory.buildEffect(effect, typeMap, fieldConditionMap));
            }
            fieldCondition.setEffects(effects.toArray(new FieldConditionEffect[0]));
        }
    }
}
