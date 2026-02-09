package com.github.lucasaugustoss.data.objects.templates;

import java.util.Map;

import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.properties.stats.StatName;

public class PokemonTemplate {
    private int pokedexNumber;
    private int formNumber;
    private String id;
    private String name;
    private String form;
    private boolean formChangeInBattle;
    private boolean resetFormOnSwitch;
    private int generation;
    private String[] typeIDs;
    private TypeTemplate[] types;
    private double[] genderRatio;
    private double weight;
    private Ability[] abilityList;
    private Move[] learnset;
    private int baseHP, baseAtk, baseDef, baseSpA, baseSpD, baseSpe;
    private PokemonTemplate[] evolutions;
    private PokemonTemplate baseForm;
    private Item[] itemsNeededForForm;
    private Move moveNeededForForm;
    private PokemonTemplate[] forms;

    public PokemonTemplate(
        int pokedexNumber, int formNumber,
        String id, String name, String form, boolean formChangeInBattle, boolean resetFormOnSwitch,
        int generation, String[] typeIDs, double[] genderRatio, double weight,
        Ability[] abilityList, Move[] learnset, int[] stats,
        Item[] itemsNeededForForm, Move moveNeededForForm
    ) {
        this.pokedexNumber = pokedexNumber;
        this.formNumber = formNumber;
        this.id = id;
        this.name = name;
        this.form = form;
        this.formChangeInBattle = formChangeInBattle;
        this.resetFormOnSwitch = resetFormOnSwitch;
        this.generation = generation;
        this.typeIDs = typeIDs;
        this.types = new TypeTemplate[2];
        this.genderRatio = genderRatio;
        this.weight = weight;
        this.abilityList = abilityList;
        this.learnset = learnset;
        this.baseHP = stats[0];
        this.baseAtk = stats[1];
        this.baseDef = stats[2];
        this.baseSpA = stats[3];
        this.baseSpD = stats[4];
        this.baseSpe = stats[5];
        this.itemsNeededForForm = itemsNeededForForm;
        this.moveNeededForForm = moveNeededForForm;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public int getFormNumber() {
        return formNumber;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public boolean formChangeIsInBattle() {
        return formChangeInBattle;
    }

    public boolean formResetsOnSwitch() {
        return resetFormOnSwitch;
    }

    public int getGeneration() {
        return generation;
    }

    public TypeTemplate[] getTypes() {
        return types;
    }

    public double[] getGenderRatio() {
        return genderRatio;
    }

    public double getWeight() {
        return weight;
    }

    public Ability[] getAbilityList() {
        return abilityList;
    }

    public Move[] getLearnset() {
        return learnset;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public int getBaseAtk() {
        return baseAtk;
    }

    public int getBaseDef() {
        return baseDef;
    }

    public int getBaseSpA() {
        return baseSpA;
    }

    public int getBaseSpD() {
        return baseSpD;
    }

    public int getBaseSpe() {
        return baseSpe;
    }

    public int getBaseStat(StatName stat) {
        switch (stat) {
            case Atk:
                return baseAtk;

            case Def:
                return baseDef;

            case SpA:
                return baseSpA;

            case SpD:
                return baseSpD;

            case Spe:
                return baseSpe;

            default:
                return 0;
        }
    }

    public PokemonTemplate[] getEvolutions() {
        return evolutions;
    }

    public PokemonTemplate getBaseForm() {
        return baseForm;
    }

    public Item[] getItemsNeededForForm() {
        return itemsNeededForForm;
    }

    public boolean needsItemForForm(Item item) {
        for (Item itemNeeded : itemsNeededForForm) {
            if (itemNeeded.compare(item)) {
                return true;
            }
        }
        return false;
    }

    public Move getMoveNeededForForm() {
        return moveNeededForForm;
    }

    public PokemonTemplate[] getForms() {
        return forms;
    }



    public void convertTypes(Map<String, TypeTemplate> typeMap) {
        for (int i = 0; i < 2; i++) {
            types[i] = typeMap.get("typeless");

            if (typeIDs.length <= i || typeMap.get(typeIDs[i]) == null) {
                continue;
            }

            types[i] = typeMap.get(typeIDs[i]);
        }
    }

    public void setEvolutions(PokemonTemplate[] evolutions) {
        this.evolutions = evolutions;
    }

    public void setBaseForm(PokemonTemplate baseForm) {
        this.baseForm = baseForm;
    }

    public void setForms(PokemonTemplate[] forms) {
        this.forms = forms;
    }

    public boolean compare(PokemonTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(Pokemon pokemon, boolean trueSpecies) {
        return trueSpecies ? this.name.equals(pokemon.getOriginalSpecies()) : this.name.equals(pokemon.getSpecies());
    }

    public boolean compareWithForm(PokemonTemplate other) {
        return this.name.equals(other.name) && this.form.equals(other.form);
    }

    public boolean compareWithForm(Pokemon pokemon) {
        return this.name.equals(pokemon.getSpecies()) && this.form.equals(pokemon.getForm());
    }
}
