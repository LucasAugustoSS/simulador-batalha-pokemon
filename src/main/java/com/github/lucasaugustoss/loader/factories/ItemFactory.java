package com.github.lucasaugustoss.loader.factories;

import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.ItemDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class ItemFactory {
    private final Map<String, ItemTemplate> itemList = new HashMap<>();

    public Map<String, ItemTemplate> build(JSONLoader data) {
        createItem(data);
        return Map.copyOf(itemList);
    }

    private void createItem(JSONLoader data) {
        for (ItemDTO dto : data.getItemData().values()) {
            ItemTemplate item = new ItemTemplate(
                dto.index,
                dto.id,
                dto.name,
                dto.consumable,
                dto.type != null ? FactoryTools.convertEnum(dto.type, ItemType.class) : ItemType.Other,
                dto.users,
                dto.tetheredToValidUser,
                dto.transformsInto,
                dto.changesTypeTo,
                FactoryTools.convertMove(dto.zMove),
                FactoryTools.convertMove(dto.zMoveOrigin),
                dto.effect,
                dto.flingPower,
                dto.flingEffect
            );

            itemList.put(dto.id, item);
        }
    }

    public void convertObjects(
        Map<String, ItemTemplate> itemMap,
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        for (ItemTemplate item : itemMap.values()) {
            item.setUsers(FactoryTools.convertObjectArray(item.getUserIDs(), pokemonMap).toArray(new PokemonTemplate[0]));
            item.setTransformsInto(FactoryTools.convertObject(item.getTransformsIntoID(), pokemonMap));
            item.setChangesTypeTo(FactoryTools.convertObject(item.getChangesTypeToID(), typeMap));
            item.setEffect(ItemEffectFactory.buildEffect(item.getEffectDTO(), typeMap, statusConditionMap));
            item.setFlingEffect(ItemEffectFactory.buildEffect(item.getFlingEffectDTO(), typeMap, statusConditionMap));
        }
    }
}
