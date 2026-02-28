package com.github.lucasaugustoss.loader.factories;

import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.PokemonDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class PokemonFactory {
    private final Map<String, PokemonTemplate> pokemonList = new HashMap<>();

    public Map<String, PokemonTemplate> build(JSONLoader data) {
        createPokemon(data);
        assignRelationships(data);
        return Map.copyOf(pokemonList);
    }

    private void createPokemon(JSONLoader data) {
        for (PokemonDTO dto : data.getPokemonData().values()) {
            PokemonTemplate pokemon = new PokemonTemplate(
                dto.index,
                dto.id,
                dto.pokedexNumber,
                dto.formNumber,
                dto.name,
                dto.form == null ? "Normal" : dto.form,
                dto.formChangeInBattle,
                dto.resetFormOnSwitch,
                dto.generation,
                dto.type,
                dto.genderRatio,
                dto.weight,
                FactoryTools.convertAbilityArray(dto.ability),
                FactoryTools.convertMoveArray(data.getLearnsetData().get(dto.learnset)),
                dto.stats,
                dto.itemsNeeded,
                FactoryTools.convertMove(dto.moveNeeded)
            );

            pokemonList.put(dto.id, pokemon);
        }
    }

    private void assignRelationships(JSONLoader data) {
        for (PokemonDTO dto : data.getPokemonData().values()) {
            PokemonTemplate pokemon = pokemonList.get(dto.id);

            pokemon.setEvolutions(FactoryTools.convertObjectArray(dto.evolutions, pokemonList).toArray(new PokemonTemplate[0]));
            pokemon.setBaseForm(FactoryTools.convertObject(dto.baseForm != null ? dto.baseForm : dto.id, pokemonList));
            pokemon.setForms(FactoryTools.convertObjectArray(dto.forms, pokemonList).toArray(new PokemonTemplate[0]));
        }
    }

    public void convertObjects(
        Map<String, PokemonTemplate> pokemonMap,
        Map<String, TypeTemplate> typeMap,
        Map<String, ItemTemplate> itemMap
    ) {
        for (PokemonTemplate pokemon : pokemonMap.values()) {
            pokemon.convertTypes(typeMap);
            pokemon.setItemsNeededForForm(FactoryTools.convertObjectArray(pokemon.getItemsNeededForFormIDs(), itemMap).toArray(new ItemTemplate[0]));
        }
    }
}
