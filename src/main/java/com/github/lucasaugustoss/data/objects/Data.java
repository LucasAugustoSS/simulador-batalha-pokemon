package com.github.lucasaugustoss.data.objects;

import java.util.ArrayList;
import java.util.Map;

import com.github.lucasaugustoss.data.classes.Nature;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.factories.NatureFactory;
import com.github.lucasaugustoss.loader.factories.PokemonFactory;
import com.github.lucasaugustoss.loader.factories.TypeFactory;

public class Data {
    private static Data instance;

    private final Map<String, PokemonTemplate> PokemonList;
    private final ArrayList<PokemonTemplate> SelectablePokemonList;
    private final Map<String, TypeTemplate> TypeList;
    private final Map<String, Nature> NatureList;
    private final ArrayList<Nature> OrderedNatureList;

    private Data() {
        JSONLoader loader = new JSONLoader();
        PokemonFactory pokemonFactory = new PokemonFactory();
        TypeFactory typeFactory = new TypeFactory();
        NatureFactory natureFactory = new NatureFactory();

        this.PokemonList = pokemonFactory.build(loader);
        this.TypeList = typeFactory.build(loader);
        this.NatureList = natureFactory.build(loader);


        pokemonFactory.convertTypes(PokemonList, TypeList);

        ArrayList<PokemonTemplate> pokemon = new ArrayList<>(this.PokemonList.values());
        pokemon = selectablePokemonList(pokemon);
        this.SelectablePokemonList = sortPokemonList(pokemon);

        this.OrderedNatureList = sortNatureList(new ArrayList<>(this.NatureList.values()));
    }

    public static Data get() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }



    public Map<String, PokemonTemplate> getPokemonList() {
        return PokemonList;
    }

    public ArrayList<PokemonTemplate> getSelectablePokemonList() {
        return SelectablePokemonList;
    }

    public PokemonTemplate getPokemon(String id) {
        return PokemonList.get(id);
    }

    public Map<String, TypeTemplate> getTypeList() {
        return TypeList;
    }

    public TypeTemplate getType(String id) {
        return TypeList.get(id);
    }

    public Map<String, Nature> getNatureList() {
        return NatureList;
    }

    public ArrayList<Nature> getOrderedNatureList() {
        return OrderedNatureList;
    }

    public Nature getNature(String id) {
        return NatureList.get(id);
    }



    private ArrayList<PokemonTemplate> selectablePokemonList(ArrayList<PokemonTemplate> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getID().equals(list.get(i).getBaseForm().getID())) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    private ArrayList<PokemonTemplate> sortPokemonList(ArrayList<PokemonTemplate> list) {
        int len = list.size();
        
        if (len <= 1) {
            return list;
        }

        ArrayList<PokemonTemplate> orderedList = new ArrayList<>();
        ArrayList<PokemonTemplate> before = new ArrayList<>();
        ArrayList<PokemonTemplate> after = new ArrayList<>();

        PokemonTemplate pivot = list.get(list.size()/2);
        list.remove(pivot);
        
        for (PokemonTemplate i : list) {
            if (firstBeforeSecond(i, pivot)) {
                before.add(i);
            } else {
                after.add(i);
            }
        }

        orderedList.addAll(sortPokemonList(before));
        orderedList.add(pivot);
        orderedList.addAll(sortPokemonList(after));

        return orderedList;
    }

    private boolean firstBeforeSecond(PokemonTemplate first, PokemonTemplate second) {
        if (first.getGeneration() != -1 && second.getGeneration() == -1) {
            return true;
        }

        if (first.getGeneration() == -1 && second.getGeneration() != -1) {
            return false;
        }

        if (Math.abs(first.getPokedexNumber()) < Math.abs(second.getPokedexNumber())) {
            return true;
        }
        return false;
    }

    private ArrayList<Nature> sortNatureList(ArrayList<Nature> list) {
        int len = list.size();
        
        if (len <= 1) {
            return list;
        }

        ArrayList<Nature> orderedList = new ArrayList<>();
        String[] statOrder = new String[] {"atk", "def", "spa", "spd", "spe"};

        for (String boostedStat : statOrder) {
            for (String reducedStat : statOrder) {
                for (Nature nature : list) {
                    String natureBoostedStat = nature.getBoostedStat().getNameShort().toString().toLowerCase();
                    String natureReducedStat = nature.getReducedStat().getNameShort().toString().toLowerCase();
                    if (boostedStat.equals(natureBoostedStat) && reducedStat.equals(natureReducedStat)) {
                        orderedList.add(nature);
                        list.remove(nature);
                        break;
                    }
                }
            }
        }

        return orderedList;
    }
}