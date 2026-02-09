package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.AdditionalImmunitiesDTO;
import com.github.lucasaugustoss.loader.dtos.TypeDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class TypeFactory {
    private final Map<String, TypeTemplate> typeList = new HashMap<>();

    public Map<String, TypeTemplate> build(JSONLoader data) {
        createType(data);
        assignRelationships(data);
        buildAdditionalImmunities(data);
        return Map.copyOf(typeList);
    }

    private void createType(JSONLoader data) {
        for (TypeDTO dto : data.getTypeData().values()) {
            TypeTemplate type = new TypeTemplate(
                dto.id,
                dto.name
            );

            typeList.put(dto.id, type);
        }
    }

    private void assignRelationships(JSONLoader data) {
        for (TypeDTO dto : data.getTypeData().values()) {
            TypeTemplate type = typeList.get(dto.id);

            type.setSuperEffective(dto.superEffective != null ? FactoryTools.convertObjectArray(dto.superEffective, typeList).toArray(new TypeTemplate[0]) : new TypeTemplate[0]);
            type.setNotVeryEffective(dto.notVeryEffective != null ? FactoryTools.convertObjectArray(dto.notVeryEffective, typeList).toArray(new TypeTemplate[0]) : new TypeTemplate[0]);
            type.setIneffective(dto.ineffective != null ? FactoryTools.convertObjectArray(dto.ineffective, typeList).toArray(new TypeTemplate[0]) : new TypeTemplate[0]);
        }
    }

    private void buildAdditionalImmunities(JSONLoader data) {
        for (TypeDTO dto : data.getTypeData().values()) {
            TypeTemplate type = typeList.get(dto.id);
            AdditionalImmunitiesDTO immunitiesDTO = dto.additionalImmunities;

            ArrayList<Object> immunities = new ArrayList<>();

            if (immunitiesDTO == null) {
                type.setAdditionalImmunities(new Object[0]);
                continue;
            }

            immunities.addAll(Arrays.asList(FactoryTools.convertStatusConditionArray(immunitiesDTO.statusConditions)));
            immunities.addAll(Arrays.asList(FactoryTools.convertEnumArray(immunitiesDTO.moveTypes, MoveType.class)));
            immunities.addAll(Arrays.asList(FactoryTools.convertEnumArray(immunitiesDTO.temporaryProperties, TemporaryProperty.class)));

            type.setAdditionalImmunities(immunities.toArray(new Object[0]));
        }
    }
}
