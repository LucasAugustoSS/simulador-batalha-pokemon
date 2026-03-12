package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.TypeDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class TypeFactory {
    private final Map<String, TypeTemplate> typeList = new HashMap<>();

    public Map<String, TypeTemplate> build(JSONLoader data) {
        createType(data);
        assignRelationships(data);
        return Map.copyOf(typeList);
    }

    private void createType(JSONLoader data) {
        for (TypeDTO dto : data.getTypeData().values()) {
            TypeTemplate type = new TypeTemplate(
                dto.index,
                dto.id,
                dto.name,
                dto.additionalImmunities
            );

            typeList.put(dto.id, type);
        }
    }

    private void assignRelationships(JSONLoader data) {
        for (TypeDTO dto : data.getTypeData().values()) {
            TypeTemplate type = typeList.get(dto.id);

            type.setSuperEffective(FactoryTools.convertObjectArray(dto.superEffective, typeList).toArray(new TypeTemplate[0]));
            type.setNotVeryEffective(FactoryTools.convertObjectArray(dto.notVeryEffective, typeList).toArray(new TypeTemplate[0]));
            type.setIneffective(FactoryTools.convertObjectArray(dto.ineffective, typeList).toArray(new TypeTemplate[0]));
        }
    }

    public void convertAdditionalImmunities(
        Map<String, TypeTemplate> typeMap,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        for (TypeTemplate type : typeMap.values()) {;
            ArrayList<Object> immunities = new ArrayList<>();

            if (type.getAdditionalImmunityDTO() == null) {
                type.setAdditionalImmunities(new Object[0]);
                continue;
            }

            immunities.addAll(FactoryTools.convertObjectArray(type.getAdditionalImmunityDTO().statusConditions, statusConditionMap));
            immunities.addAll(FactoryTools.convertEnumArray(type.getAdditionalImmunityDTO().moveTypes, MoveType.class));
            immunities.addAll(FactoryTools.convertEnumArray(type.getAdditionalImmunityDTO().temporaryProperties, TemporaryProperty.class));

            type.setAdditionalImmunities(immunities.toArray(new Object[0]));
        }
    }
}
