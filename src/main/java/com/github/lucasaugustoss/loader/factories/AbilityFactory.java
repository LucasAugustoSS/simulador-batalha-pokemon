package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.effects.AbilityEffect;
import com.github.lucasaugustoss.data.objects.templates.AbilityTemplate;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.AbilityDTO;
import com.github.lucasaugustoss.loader.dtos.AbilityEffectDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class AbilityFactory {
    private final Map<String, AbilityTemplate> abilityList = new HashMap<>();

    public Map<String, AbilityTemplate> build(JSONLoader data) {
        createAbility(data);
        return Map.copyOf(abilityList);
    }

    private void createAbility(JSONLoader data) {
        for (AbilityDTO dto : data.getAbilityData().values()) {
            AbilityTemplate ability = new AbilityTemplate(
                dto.index,
                dto.id,
                dto.name,
                dto.effect,
                dto.notTransferable,
                dto.notReplaceable,
                dto.notSuppressable,
                dto.ignorable,
                dto.exclusiveUser,
                FactoryTools.convertMessage(dto.messages)
            );

            abilityList.put(dto.id, ability);
        }
    }

    public void convertObjects(
        Map<String, AbilityTemplate> abilityMap,
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, MoveTemplate> moveMap,
        Map<String, StatTemplate> statMap,
        Map<String, ItemTemplate> itemMap,
        Map<String, StatusConditionTemplate> statusConditionMap,
        Map<String, FieldConditionTemplate> fieldConditionMap
    ) {
        for (AbilityTemplate ability : abilityMap.values()) {
            ability.setExclusiveUser(FactoryTools.convertObject(ability.getExclusiveUserID(), pokemonMap));

            ArrayList<AbilityEffect> effects = new ArrayList<>();
            if (ability.getEffectDTOs() != null) {
                for (AbilityEffectDTO effect : ability.getEffectDTOs()) {
                    effects.add(AbilityEffectFactory.buildEffect(
                        effect,
                        pokemonMap,
                        typeMap,
                        moveMap,
                        statMap,
                        itemMap,
                        statusConditionMap,
                        fieldConditionMap,
                        abilityMap
                    ));
                }
            }
            ability.setEffects(effects.toArray(new AbilityEffect[0]));
        }
    }
}
