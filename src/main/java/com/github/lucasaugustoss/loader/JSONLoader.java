package com.github.lucasaugustoss.loader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.loader.dtos.AbilityDTO;
import com.github.lucasaugustoss.loader.dtos.FieldConditionDTO;
import com.github.lucasaugustoss.loader.dtos.ItemDTO;
import com.github.lucasaugustoss.loader.dtos.MessageDTO;
import com.github.lucasaugustoss.loader.dtos.MoveDTO;
import com.github.lucasaugustoss.loader.dtos.NatureDTO;
import com.github.lucasaugustoss.loader.dtos.PokemonDTO;
import com.github.lucasaugustoss.loader.dtos.StatDTO;
import com.github.lucasaugustoss.loader.dtos.StatusConditionDTO;
import com.github.lucasaugustoss.loader.dtos.TypeDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONLoader {
    private Map<String, PokemonDTO> pokemonData;
    private Map<String, String[]> learnsetData;
    private Map<String, TypeDTO> typeData;
    private Map<String, StatDTO> statData;
    private Map<String, NatureDTO> natureData;
    private Map<String, MoveDTO> moveData;
    private Map<String, AbilityDTO> abilityData;
    private Map<String, ItemDTO> itemData;
    private Map<String, FieldConditionDTO> fieldConditionData;
    private Map<String, StatusConditionDTO> statusConditionData;
    private Map<String, MessageDTO> messageData;

    public JSONLoader() {
        Gson gson = new Gson();

        this.pokemonData = load(
            gson,
            "PokemonData.json",
            new TypeToken<Map<String, PokemonDTO>>() {}
        );

        this.learnsetData = load(
            gson,
            "PokemonLearnsetData.json",
            new TypeToken<Map<String, String[]>>() {}
        );

        this.typeData = load(
            gson,
            "TypeData.json",
            new TypeToken<Map<String, TypeDTO>>() {}
        );

        this.statData = load(
            gson,
            "StatData.json",
            new TypeToken<Map<String, StatDTO>>() {}
        );

        this.natureData = load(
            gson,
            "NatureData.json",
            new TypeToken<Map<String, NatureDTO>>() {}
        );

        this.moveData = load(
            gson,
            "MoveData.json",
            new TypeToken<Map<String, MoveDTO>>() {}
        );

        this.abilityData = load(
            gson,
            "AbilityData.json",
            new TypeToken<Map<String, AbilityDTO>>() {}
        );

        this.itemData = load(
            gson,
            "ItemData.json",
            new TypeToken<Map<String, ItemDTO>>() {}
        );

        this.fieldConditionData = load(
            gson,
            "FieldConditionData.json",
            new TypeToken<Map<String, FieldConditionDTO>>() {}
        );

        this.statusConditionData = load(
            gson,
            "StatusConditionData.json",
            new TypeToken<Map<String, StatusConditionDTO>>() {}
        );

        this.messageData = load(
            gson,
            "MessageData.json",
            new TypeToken<Map<String, MessageDTO>>() {}
        );
    }

    public Map<String, PokemonDTO> getPokemonData() {
        return pokemonData;
    }

    public Map<String, String[]> getLearnsetData() {
        return learnsetData;
    }

    public Map<String, TypeDTO> getTypeData() {
        return typeData;
    }

    public Map<String, StatDTO> getStatData() {
        return statData;
    }

    public Map<String, NatureDTO> getNatureData() {
        return natureData;
    }

    public Map<String, MoveDTO> getMoveData() {
        return moveData;
    }

    public Map<String, AbilityDTO> getAbilityData() {
        return abilityData;
    }

    public Map<String, ItemDTO> getItemData() {
        return itemData;
    }

    public Map<String, FieldConditionDTO> getFieldConditionData() {
        return fieldConditionData;
    }

    public Map<String, StatusConditionDTO> getStatusConditionData() {
        return statusConditionData;
    }

    public Map<String, MessageDTO> getMessageData() {
        return messageData;
    }



    private <T> T load(Gson gson, String file, TypeToken<T> token) {
        InputStream is = Data.class.getClassLoader().getResourceAsStream(file);

        if (is == null) {
            throw new RuntimeException("File not found: " + file);
        }

        Type type = token.getType();
        return gson.fromJson(new InputStreamReader(is), type);
    }
}