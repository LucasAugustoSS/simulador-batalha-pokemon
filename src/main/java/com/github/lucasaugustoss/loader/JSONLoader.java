package com.github.lucasaugustoss.loader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.loader.dtos.NatureDTO;
import com.github.lucasaugustoss.loader.dtos.PokemonDTO;
import com.github.lucasaugustoss.loader.dtos.TypeDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONLoader {
    private Map<String, PokemonDTO> pokemonData;
    private Map<String, String[]> learnsetData;
    private Map<String, TypeDTO> typeData;
    private Map<String, NatureDTO> natureData;

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

        this.natureData = load(
            gson,
            "NatureData.json",
            new TypeToken<Map<String, NatureDTO>>() {}
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

    public Map<String, NatureDTO> getNatureData() {
        return natureData;
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