package com.github.lucasaugustoss.loader.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.lists.AllAbilities;
import com.github.lucasaugustoss.data.lists.AllItems;
import com.github.lucasaugustoss.data.lists.AllMoves;
import com.github.lucasaugustoss.data.lists.AllTypes;
import com.github.lucasaugustoss.data.objects.oldObjects.TypeList;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.PokemonDTO;

public class PokemonFactory {
    private final Map<String, PokemonTemplate> pokemonList = new HashMap<>();

    public Map<String, PokemonTemplate> build(JSONLoader data) {
        createPokemon(data);
        assignRelationships(data);
        return Map.copyOf(pokemonList);
    }

    public void createPokemon(JSONLoader data) {
        for (PokemonDTO dto : data.getPokemonData().values()) {
            PokemonTemplate pokemon = new PokemonTemplate(
                dto.pokedexNumber,
                dto.formNumber,
                dto.id,
                dto.name,
                dto.form == null ? "Normal" : dto.form,
                dto.formChangeInBattle,
                dto.resetFormOnSwitch,
                dto.generation,
                convertTypeArray(dto.type),
                dto.genderRatio,
                dto.weight,
                convertAbilityArray(dto.ability),
                convertMoveArray(data.getLearnsetData().get(dto.learnset)),
                dto.stats,
                dto.itemsNeeded == null ? new Item[0] : convertItemArray(dto.itemsNeeded),
                convertMove(dto.moveNeeded)
            );

            pokemonList.put(dto.id, pokemon);
        }
    }

    private void assignRelationships(JSONLoader data) {
        for (PokemonDTO dto : data.getPokemonData().values()) {
            PokemonTemplate pokemon = pokemonList.get(dto.id);

            pokemon.setEvolutions(dto.evolutions != null ? convertPokemonArray(dto.evolutions) : new PokemonTemplate[0]);
            pokemon.setBaseForm(convertPokemon(dto.baseForm != null ? dto.baseForm : dto.id));
            pokemon.setForms(dto.forms != null ? convertPokemonArray(dto.forms) : new PokemonTemplate[0]);
        }
    }

    private Type[] convertTypeArray(String[] names) {
        Type[] types = new Type[3];

        for (int i = 0; i < 3; i++) {
            types[i] = TypeList.typeless;

            if (names.length <= i) {
                continue;
            }

            String name = names[i];
            for (Type type : AllTypes.allTypes) {
                if (formatName(type.getName()).equals(name)) {
                    types[i] = type;
                    break;
                }
            }
        }

        return types;
    }

    private Ability[] convertAbilityArray(String[] names) {
        ArrayList<Ability> abilities = new ArrayList<>();

        for (String name : names) {
            for (Ability ability : AllAbilities.allAbilities) {
                if (formatName(ability.getName()).equals(name)) {
                    abilities.add(ability);
                    break;
                }
            }
        }

        return abilities.toArray(new Ability[0]);
    }

    private Move[] convertMoveArray(String[] names) {
        ArrayList<Move> moves = new ArrayList<>();

        for (String name : names) {
            for (Move move : AllMoves.allMoves) {
                if (formatName(move.getTrueName()).equals(name)) {
                    moves.add(move);
                    break;
                }
            }
        }

        return moves.toArray(new Move[0]);
    }

    private Move convertMove(String name) {
        for (Move move : AllMoves.allMoves) {
            if (formatName(move.getTrueName()).equals(name)) {
                return move;
            }
        }

        return null;
    }

    private Item[] convertItemArray(String[] names) {
        ArrayList<Item> items = new ArrayList<>();

        for (String name : names) {
            for (Item item : AllItems.allItems) {
                if (formatName(item.getName()).equals(name)) {
                    items.add(item);
                    break;
                }
            }
        }

        return items.toArray(new Item[0]);
    }

    private PokemonTemplate[] convertPokemonArray(String[] ids) {
        ArrayList<PokemonTemplate> pokemon = new ArrayList<>();

        for (String id : ids) {
            if (pokemonList.containsKey(id)) {
                pokemon.add(pokemonList.get(id));
            }
        }

        return pokemon.toArray(new PokemonTemplate[0]);
    }

    private PokemonTemplate convertPokemon(String id) {
        if (pokemonList.containsKey(id)) {
            return pokemonList.get(id);
        }

        return null;
    }

    private String formatName(String name) {
        name = name.toLowerCase();
        name = name.replaceAll("[-: ]+", "_");
        name = name.replaceAll("[']+", "");

        return name;
    }
}
