package com.github.lucasaugustoss.data.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.classes.Nature;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.templates.AbilityTemplate;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatTemplate;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.Template;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.factories.AbilityFactory;
import com.github.lucasaugustoss.loader.factories.FieldConditionFactory;
import com.github.lucasaugustoss.loader.factories.ItemFactory;
import com.github.lucasaugustoss.loader.factories.MessageFactory;
import com.github.lucasaugustoss.loader.factories.MoveFactory;
import com.github.lucasaugustoss.loader.factories.NatureFactory;
import com.github.lucasaugustoss.loader.factories.PokemonFactory;
import com.github.lucasaugustoss.loader.factories.StatFactory;
import com.github.lucasaugustoss.loader.factories.StatusConditionFactory;
import com.github.lucasaugustoss.loader.factories.TypeFactory;

public class Data {
    private static Data instance;

    private final Map<String, PokemonTemplate> PokemonList;
    private final List<PokemonTemplate> SelectablePokemonList;
    private final Map<String, TypeTemplate> TypeList;
    private final Map<String, MoveTemplate> MoveList;
    private final List<MoveTemplate> RegularMoveList;
    private final Map<String, AbilityTemplate> AbilityList;
    private final Map<String, StatTemplate> StatList;
    private final Map<String, Nature> NatureList;
    private final List<Nature> OrderedNatureList;
    private final Map<String, ItemTemplate> ItemList;
    private final List<ItemTemplate> OrderedItemList;
    private final Map<String, FieldConditionTemplate> FieldConditionList;
    private final Map<String, StatusConditionTemplate> StatusConditionList;
    private final Map<String, Message> MessageList;

    private Data() {
        JSONLoader loader = new JSONLoader();
        PokemonFactory pokemonFactory = new PokemonFactory();
        TypeFactory typeFactory = new TypeFactory();
        MoveFactory moveFactory = new MoveFactory();
        AbilityFactory abilityFactory = new AbilityFactory();
        StatFactory statFactory = new StatFactory();
        NatureFactory natureFactory = new NatureFactory();
        ItemFactory itemFactory = new ItemFactory();
        FieldConditionFactory fieldConditionFactory = new FieldConditionFactory();
        StatusConditionFactory statusConditionFactory = new StatusConditionFactory();
        MessageFactory messageFactory = new MessageFactory();

        this.PokemonList = pokemonFactory.build(loader);
        this.TypeList = typeFactory.build(loader);
        this.MoveList = moveFactory.build(loader);
        this.AbilityList = abilityFactory.build(loader);
        this.StatList = statFactory.build(loader);
        this.NatureList = natureFactory.build(loader);
        this.ItemList = itemFactory.build(loader);
        this.FieldConditionList = fieldConditionFactory.build(loader);
        this.StatusConditionList = statusConditionFactory.build(loader);
        this.MessageList = messageFactory.build(loader);


        pokemonFactory.convertObjects(PokemonList, TypeList, MoveList, AbilityList, ItemList);

        List<PokemonTemplate> pokemon = new ArrayList<>(this.PokemonList.values());
        pokemon = selectablePokemonList(pokemon);
        this.SelectablePokemonList = sortListByIndex(pokemon);

        typeFactory.convertAdditionalImmunities(TypeList, MoveList, StatusConditionList);

        moveFactory.convertObjects(
            MoveList, PokemonList, TypeList,
            AbilityList, ItemList, StatusConditionList,
            FieldConditionList, MessageList
        );
        this.RegularMoveList = regularMoveList(new ArrayList<MoveTemplate>(this.MoveList.values()));

        abilityFactory.convertObjects(
            AbilityList, PokemonList, TypeList,
            MoveList, StatList, ItemList,
            StatusConditionList, FieldConditionList, MessageList
        );

        natureFactory.convertStats(NatureList, StatList);

        this.OrderedNatureList = sortNatureList(new ArrayList<>(this.NatureList.values()));

        itemFactory.convertObjects(ItemList, PokemonList, TypeList, MoveList, StatusConditionList);

        this.OrderedItemList = sortListByIndex(new ArrayList<>(this.ItemList.values()));
        this.OrderedItemList.remove(0);

        fieldConditionFactory.convertEffects(
            FieldConditionList, TypeList, MoveList,
            StatusConditionList, MessageList
        );

        statusConditionFactory.convertEffects(StatusConditionList, TypeList, MoveList, MessageList);
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

    public List<PokemonTemplate> getSelectablePokemonList() {
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

    public Map<String, MoveTemplate> getMoveList() {
        return MoveList;
    }

    public List<MoveTemplate> getRegularMoveList() {
        return RegularMoveList;
    }

    public MoveTemplate getMove(String id) {
        return MoveList.get(id);
    }

    public Map<String, AbilityTemplate> getAbilityList() {
        return AbilityList;
    }

    public AbilityTemplate getAbility(String id) {
        return AbilityList.get(id);
    }

    public Map<String, Nature> getNatureList() {
        return NatureList;
    }

    public List<Nature> getOrderedNatureList() {
        return OrderedNatureList;
    }

    public Nature getNature(String id) {
        return NatureList.get(id);
    }

    public Map<String, StatTemplate> getStatList() {
        return StatList;
    }

    public StatTemplate getStat(String id) {
        return StatList.get(id);
    }

    public Map<String, ItemTemplate> getItemList() {
        return ItemList;
    }

    public ItemTemplate getItem(String id) {
        return ItemList.get(id);
    }

    public List<ItemTemplate> getOrderedItemList() {
        return OrderedItemList;
    }

    public Map<String, FieldConditionTemplate> getFieldConditionList() {
        return FieldConditionList;
    }

    public FieldConditionTemplate getFieldCondition(String id) {
        return FieldConditionList.get(id);
    }

    public Map<String, StatusConditionTemplate> getStatusConditionList() {
        return StatusConditionList;
    }

    public StatusConditionTemplate getStatusCondition(String id) {
        return StatusConditionList.get(id);
    }

    public Map<String, Message> getMessageList() {
        return MessageList;
    }

    public Message getMessage(String id) {
        return MessageList.get(id);
    }



    private <T extends Template> List<T> sortListByIndex(List<T> list) {
        int len = list.size();
        
        if (len <= 1) {
            return list;
        }

        ArrayList<T> orderedList = new ArrayList<>();
        ArrayList<T> before = new ArrayList<>();
        ArrayList<T> after = new ArrayList<>();

        T pivot = list.get(list.size()/2);
        list.remove(pivot);
        
        for (T i : list) {
            if (i.getIndex() < pivot.getIndex()) {
                before.add(i);
            } else {
                after.add(i);
            }
        }

        orderedList.addAll(sortListByIndex(before));
        orderedList.add(pivot);
        orderedList.addAll(sortListByIndex(after));

        return orderedList;
    }

    private List<PokemonTemplate> selectablePokemonList(List<PokemonTemplate> list) {
        list.removeIf(pokemon ->
            !pokemon.getID().equals(pokemon.getBaseForm().getID())
        );
        return list;
    }

    private List<MoveTemplate> regularMoveList(List<MoveTemplate> list) {
        list.removeIf(move ->
            move.isNotTrueMove() ||
            move.isDebug() ||
            move.isZMove() ||
            move.getID().equals("max_guard") ||
            move.getID().equals("struggle")
        );
        return list;
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