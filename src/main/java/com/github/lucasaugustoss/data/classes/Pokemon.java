package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.oldObjects.AbilityList;
import com.github.lucasaugustoss.data.objects.oldObjects.FieldConditionList;
import com.github.lucasaugustoss.data.objects.oldObjects.ItemList;
import com.github.lucasaugustoss.data.objects.oldObjects.MoveList;
import com.github.lucasaugustoss.data.objects.oldObjects.StatusConditionList;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.Battle;

public class Pokemon {
    private String name;
    private String species;
    private String originalSpecies;
    private String form;
    private boolean formChangeInBattle;
    private boolean resetFormOnSwitch;
    private int generation;
    private Type[] types;
    private double[] genderRatio;
    private String gender;
    private double weight;
    private Ability ability;
    private Ability[] abilityList;
    private Move[] moves;
    private Move[] moveList;
    private PokemonTemplate baseForm;
    private Item[] itemsNeededForForm;
    private Move moveNeededForForm;
    private PokemonTemplate[] forms;
    private boolean transformed;
    private Pokemon pokemonTransformedInto;
    private PokemonTemplate[] evolutions;
    private Item item;
    private int level;
    private int baseHP, baseAtk, baseDef, baseSpA, baseSpD, baseSpe;
    private int HP;
    private Stat Atk, Def, SpA, SpD, Spe, Acc, Eva;
    private int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    private int evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    private Nature nature;
    private int currentHP;
    private StatusCondition nonVolatileStatus;
    private ArrayList<StatusCondition> volatileStatus;
    private int team;
    private int battleAction;
    private Move lastUsedMove;
    private Move readiedMove;
    private int turnsOnField;
    private int damageDealt;
    private int consecutiveProtections;
    private boolean damagedThisTurn;
    private Pokemon damager;
    private boolean currentMoveFailed;
    private boolean lastMoveFailed;

    private Object[] defaultValues;

    public Pokemon(PokemonTemplate template, int team) { // create
        this.name = template.getName();
        this.species = template.getName();
        this.originalSpecies = template.getName();
        this.form = template.getForm();
        this.formChangeInBattle = template.formChangeIsInBattle();
        this.resetFormOnSwitch = template.formResetsOnSwitch();
        this.generation = template.getGeneration();
        this.types = new Type[] {new Type(template.getTypes()[0], this), new Type(template.getTypes()[1], this), new Type(Data.get().getType("typeless"), this)};
        this.ability = new Ability(template.getAbilityList()[0], false, this);
        this.abilityList = template.getAbilityList();
        this.moves = new Move[4];
        this.moveList = template.getLearnset();
        this.baseForm = template.getBaseForm();
        this.itemsNeededForForm = template.getItemsNeededForForm();
        this.moveNeededForForm = template.getMoveNeededForForm();
        this.forms = template.getForms();
        this.evolutions = template.getEvolutions();
        this.item = new Item(ItemList.none, this);

        this.level = 100;
        this.genderRatio = template.getGenderRatio();
        if (genderRatio[0] == 0 && genderRatio[1] == 0) {
            gender = "Unknown";
        } else if (genderRatio[0] < genderRatio[1]) {
            gender = "Female";
        } else {
            gender = "Male";
        }
        this.weight = template.getWeight();

        this.baseHP = template.getBaseHP();
        this.baseAtk = template.getBaseAtk();
        this.baseDef = template.getBaseDef();
        this.baseSpA = template.getBaseSpA();
        this.baseSpD = template.getBaseSpD();
        this.baseSpe = template.getBaseSpe();

        this.ivHP = 31;
        this.ivAtk = 31;
        this.ivDef = 31;
        this.ivSpA = 31;
        this.ivSpD = 31;
        this.ivSpe = 31;

        this.evHP = 0;
        this.evAtk = 0;
        this.evDef = 0;
        this.evSpA = 0;
        this.evSpD = 0;
        this.evSpe = 0;

        this.nature = Data.get().getNature("hardy");

        this.HP = (int) Math.floor(0.01*(2 * baseHP + ivHP + Math.floor(0.25 * evHP) )*level) + level + 10;

        int valueAtk = (int) ((Math.floor(0.01*(2 * baseAtk + ivAtk + Math.floor(0.25 * evAtk))*level) + 5) * nature.multiplier(Atk));
        int valueDef = (int) ((Math.floor(0.01*(2 * baseDef + ivDef + Math.floor(0.25 * evDef))*level) + 5) * nature.multiplier(Def));
        int valueSpA = (int) ((Math.floor(0.01*(2 * baseSpA + ivSpA + Math.floor(0.25 * evSpA))*level) + 5) * nature.multiplier(SpA));
        int valueSpD = (int) ((Math.floor(0.01*(2 * baseSpD + ivSpD + Math.floor(0.25 * evSpD))*level) + 5) * nature.multiplier(SpD));
        int valueSpe = (int) ((Math.floor(0.01*(2 * baseSpe + ivSpe + Math.floor(0.25 * evSpe))*level) + 5) * nature.multiplier(Spe));

        this.Atk = Stat.atk.initialize(this, valueAtk);
        this.Def = Stat.def.initialize(this, valueDef);
        this.SpA = Stat.spa.initialize(this, valueSpA);
        this.SpD = Stat.spd.initialize(this, valueSpD);
        this.Spe = Stat.spe.initialize(this, valueSpe);
        this.Acc = Stat.acc.initialize(this, 0);
        this.Eva = Stat.eva.initialize(this, 0);

        this.currentHP = HP;

        this.nonVolatileStatus = StatusConditionList.none.cause(null, this);
        this.volatileStatus = new ArrayList<>();

        this.team = team;
        this.battleAction = 0;

        this.defaultValues = new Object[21];
    }

    public Pokemon(Pokemon original, int team) { // copy
        this.name = original.name;
        this.species = original.species;
        this.originalSpecies = original.originalSpecies;
        this.form = original.form;
        this.formChangeInBattle = original.formChangeInBattle;
        this.generation = original.generation;
        this.types = new Type[] {new Type(original.types[0], this), new Type(original.types[1], this), new Type(original.types[2], this)};
        this.ability = new Ability(original.abilityList[0], false, this);
        this.abilityList = original.abilityList;
        this.moves = new Move[4];
        this.moveList = original.moveList;
        this.baseForm = original.baseForm;
        this.itemsNeededForForm = original.itemsNeededForForm;
        this.moveNeededForForm = original.moveNeededForForm;
        this.forms = original.forms;
        this.resetFormOnSwitch = original.resetFormOnSwitch;
        this.evolutions = original.evolutions;
        this.item = new Item(ItemList.none, this);

        this.level = 100;
        this.genderRatio = original.genderRatio;
        if (genderRatio[0] == 0 && genderRatio[1] == 0) {
            gender = "Unknown";
        } else if (genderRatio[0] < genderRatio[1]) {
            gender = "Female";
        } else {
            gender = "Male";
        }
        this.weight = original.weight;

        this.baseHP = original.baseHP;
        this.baseAtk = original.baseAtk;
        this.baseDef = original.baseDef;
        this.baseSpA = original.baseSpA;
        this.baseSpD = original.baseSpD;
        this.baseSpe = original.baseSpe;

        this.ivHP = 31;
        this.ivAtk = 31;
        this.ivDef = 31;
        this.ivSpA = 31;
        this.ivSpD = 31;
        this.ivSpe = 31;

        this.evHP = 0;
        this.evAtk = 0;
        this.evDef = 0;
        this.evSpA = 0;
        this.evSpD = 0;
        this.evSpe = 0;

        this.nature = Data.get().getNature("hardy");

        this.HP = (int) Math.floor(0.01*(2 * baseHP + ivHP + Math.floor(0.25 * evHP) )*level) + level + 10;

        int valueAtk = (int) ((Math.floor(0.01*(2 * baseAtk + ivAtk + Math.floor(0.25 * evAtk))*level) + 5) * nature.multiplier(Atk));
        int valueDef = (int) ((Math.floor(0.01*(2 * baseDef + ivDef + Math.floor(0.25 * evDef))*level) + 5) * nature.multiplier(Def));
        int valueSpA = (int) ((Math.floor(0.01*(2 * baseSpA + ivSpA + Math.floor(0.25 * evSpA))*level) + 5) * nature.multiplier(SpA));
        int valueSpD = (int) ((Math.floor(0.01*(2 * baseSpD + ivSpD + Math.floor(0.25 * evSpD))*level) + 5) * nature.multiplier(SpD));
        int valueSpe = (int) ((Math.floor(0.01*(2 * baseSpe + ivSpe + Math.floor(0.25 * evSpe))*level) + 5) * nature.multiplier(Spe));

        this.Atk = Stat.atk.initialize(this, valueAtk);
        this.Def = Stat.def.initialize(this, valueDef);
        this.SpA = Stat.spa.initialize(this, valueSpA);
        this.SpD = Stat.spd.initialize(this, valueSpD);
        this.Spe = Stat.spe.initialize(this, valueSpe);
        this.Acc = Stat.acc.initialize(this, 0);
        this.Eva = Stat.eva.initialize(this, 0);

        this.currentHP = HP;

        this.nonVolatileStatus = StatusConditionList.none.cause(null, this);
        this.volatileStatus = new ArrayList<>();

        this.team = team;
        this.battleAction = 0;

        this.defaultValues = new Object[21];
    }



    public String getName(boolean hasPrefix, boolean capitalized) {
        Pokemon self = this;
        if (ability.shouldActivate(AbilityActivation.CallUserData)) {
            self = (Pokemon) ability.activate(this, this, null, null, null, null, null, 0, AbilityActivation.CallUserData);
        }

        return self.getTrueName(hasPrefix, capitalized);
    }

    public String getTrueName(boolean hasPrefix, boolean capitalized) {
        String prefix = "";
        if (hasPrefix && team == 1) {
            if (capitalized) {
                prefix = "The opposing ";
            } else {
                prefix = "the opposing ";
            }
        }

        return prefix + name;
    }

    public String getSpecies() {
        return species;
    }

    public String getOriginalSpecies() {
        return originalSpecies;
    }

    public String getForm() {
        Pokemon self = this;
        if (ability != null &&
            ability.shouldActivate(AbilityActivation.CallUserData)) {
            self = (Pokemon) ability.activate(this, this, null, null, null, null, null, 0, AbilityActivation.CallUserData);
        }

        return self.getTrueForm();
    }

    public String getTrueForm() {
        return form;
    }

    public String getNameAndForm(boolean hasPrefix, boolean capitalized) {
        Pokemon self = this;
        if (ability.shouldActivate(AbilityActivation.CallUserData)) {
            self = (Pokemon) ability.activate(this, this, null, null, null, null, null, 0, AbilityActivation.CallUserData);
        }

        if (self.getForm() != null && !self.getForm().isEmpty()) {
            return self.getName(hasPrefix, capitalized) + " (" + self.getForm() + ")";
        }
        return self.getName(hasPrefix, capitalized);
    }

    public String getTrueNameAndForm(boolean hasPrefix, boolean capitalized) {
        if (form != null && !form.isEmpty()) {
            return getTrueName(hasPrefix, capitalized) + " (" + form + ")";
        }
        return getTrueName(hasPrefix, capitalized);
    }

    public boolean formChangeIsInBattle() {
        return formChangeInBattle;
    }

    public int getGeneration() {
        return generation;
    }

    public PokemonTemplate[] getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(PokemonTemplate[] evolutions) {
        this.evolutions = evolutions;
    }

    public Type[] getTypes() {
        return types;
    }

    public Type getType(int index) {
        return types[index];
    }

    public Type getType(Type type) {
        for (Type userType : types) {
            if (!userType.compare(Data.get().getType("typeless")) &&
                userType.compare(type)) {
                return userType;
            }
        }

        return new Type(Data.get().getType("typeless"), this);
    }

    public Type getType(TypeTemplate type) {
        for (Type userType : types) {
            if (!userType.compare(Data.get().getType("typeless")) &&
                userType.compare(type)) {
                return userType;
            }
        }

        return new Type(Data.get().getType("typeless"), this);
    }

    public boolean hasType(Type type) {
        for (Type userType : types) {
            if (!userType.compare(Data.get().getType("typeless")) &&
                userType.compare(type)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasType(TypeTemplate type) {
        for (Type userType : types) {
            if (!userType.compare(Data.get().getType("typeless")) &&
                userType.compare(type)) {
                return true;
            }
        }

        return false;
    }

    public void setTypes(Type[] types) {
        Type[] newTypes = new Type[3];
        for (int i = 0; i < 3; i++) {
            if (types.length >= (i+1)) {
                newTypes[i] = new Type(types[i], this);
            } else {
                newTypes[i] = new Type(Data.get().getType("typeless"), this);
            }
        }

        this.types = newTypes;
    }

    public void setTypes(TypeTemplate[] types) {
        Type[] newTypes = new Type[3];
        for (int i = 0; i < 3; i++) {
            if (types.length >= (i+1)) {
                newTypes[i] = new Type(types[i], this);
            } else {
                newTypes[i] = new Type(Data.get().getType("typeless"), this);
            }
        }

        this.types = newTypes;
    }

    public double[] getGenderRatio() {
        return genderRatio;
    }

    public String getGender() {
        Pokemon self = this;
        if (ability.shouldActivate(AbilityActivation.CallUserData)) {
            self = (Pokemon) ability.activate(this, this, null, null, null, null, null, 0, AbilityActivation.CallUserData);
        }

        return self.getTrueGender();
    }

    public String getTrueGender() {
        return gender;
    }

    public void setGender(int value) {
        switch (value) {
            case 1:
                this.gender = "Male";
                break;

            case 2:
                this.gender = "Female";
                break;

            default:
                break;
        }
    }

    public double getWeight(Move move) {
        double weight = this.weight;

        if (ability.shouldActivate(move, AbilityActivation.WeightCalc)) {
            weight *= (double) ability.activate(this, null, null, null, null, null, null, 0, AbilityActivation.WeightCalc);
            weight = Math.floor(weight*10)/10;
        }

        return weight;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability, boolean active, Pokemon opponent) {
        Ability oldAbility = this.ability;
        this.ability = new Ability(ability, active, this);

        if (App.battleStarted &&
            !Battle.battleOverCheck()) {
            if (oldAbility.shouldActivate(AbilityActivation.Removed)) {
                oldAbility.activate(this, opponent, null, null, null, null, null, 0, AbilityActivation.Removed);
            }

            if (getVolatileStatus(StatusConditionList.readying_switch) == null &&
                this.ability.shouldActivate(AbilityActivation.AbilityUpdate)) {
                this.ability.activate(this, opponent, null, null, null, null, null, 0, AbilityActivation.AbilityUpdate);
            }
        }
    }

    public void swapAbilities(Pokemon target) {
        Ability selfOldAbility = ability;
        Ability targetOldAbility = target.ability;

        this.ability = new Ability(target.getAbility(), true, this);
        target.ability = new Ability(selfOldAbility, true, target);

        for (Pokemon pokemon : Battle.orderPokemon(this, target)) {
            if (pokemon == this) {
                if (selfOldAbility.shouldActivate(AbilityActivation.Removed)) {
                    selfOldAbility.activate(this, target, null, null, null, null, null, 0, AbilityActivation.Removed);
                }
            } else {
                if (targetOldAbility.shouldActivate(AbilityActivation.Removed)) {
                    targetOldAbility.activate(target, this, null, null, null, null, null, 0, AbilityActivation.Removed);
                }
            }
        }

        for (Pokemon pokemon : Battle.orderPokemon(this, target)) {
            if (pokemon == this) {
                if (ability.shouldActivate(AbilityActivation.AbilityUpdate)) {
                    ability.activate(this, target, null, null, null, null, null, 0, AbilityActivation.AbilityUpdate);
                }
            } else {
                if (target.ability.shouldActivate(AbilityActivation.AbilityUpdate)) {
                    target.ability.activate(target, this, null, null, null, null, null, 0, AbilityActivation.AbilityUpdate);
                }
            }
        }
    }

    public Ability[] getAbilityList() {
        return abilityList;
    }

    public Move[] getMoves() {
        if (ability.shouldActivate(AbilityActivation.CallMove)) {
            Move[] newMoves = new Move[4];
            for (int i = 0; i < moves.length; i++) {
                newMoves[i] = (Move) ability.activate(this, null, moves[i], null, null, null, null, 0, AbilityActivation.CallMove);
            }
            return newMoves;
        }

        return moves;
    }

    public Move[] getTrueMoves() {
        return moves;
    }

    public void setMoves(Move[] moves) {
        Move[] newMoves = new Move[4];
        for (int i = 0; i < 4; i++) {
            if (moves.length >= (i+1) &&
                moves[i] != null) {
                newMoves[i] = new Move(moves[i], this);
            }
        }

        this.moves = newMoves;
    }

    public Move getMove(Move chosenMove) {
        for (Move move : moves) {
            if (move != null &&
                move.compare(chosenMove)) {
                return move;
            }
        }
        return null;
    }

    public void setMove(Move move, int slot) {
        this.moves[slot] = move;
    }

    public Move[] getMoveList() {
        return moveList;
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

    public void setItemsNeededForForm(Item[] itemsNeededForForm) {
        this.itemsNeededForForm = itemsNeededForForm;
    }

    public Move getMoveNeededForForm() {
        return moveNeededForForm;
    }

    public void setMoveNeededForForm(Move moveNeededForForm) {
        this.moveNeededForForm = moveNeededForForm;
    }

    public PokemonTemplate[] getForms() {
        return forms;
    }

    public void setForms(PokemonTemplate[] forms) {
        this.forms = forms;
    }

    public void changeForm(String formName) {
        if (transformed) {
            return;
        }

        if (form.equals(formName)) {
            return;
        }

        PokemonTemplate newForm = null;
        for (PokemonTemplate altForm : forms) {
            if (altForm.getForm().equals(formName)) {
                newForm = altForm;
                break;
            }
        }

        if (newForm == null) {
            return;
        }

        Pokemon opponent;
        if (this == Battle.yourActivePokemon) {
            opponent = Battle.opponentActivePokemon;
        } else {
            opponent = Battle.yourActivePokemon;
        }

        name = newForm.getName();
        form = newForm.getForm();
        types = new Type[] {new Type(newForm.getTypes()[0], this), new Type(newForm.getTypes()[1], this), types[2]};
        genderRatio = newForm.getGenderRatio();
        weight = newForm.getWeight();

        int abilityIndex = 0;
        for (int i = 0; i < abilityList.length; i++) {
            if (abilityList[i].compare(ability)) {
                abilityIndex = i;
                break;
            }
        }
        boolean active = ability.isActive();
        abilityList = newForm.getAbilityList();
        Ability newAbility = newForm.getAbilityList().length > abilityIndex ? newForm.getAbilityList()[abilityIndex] : newForm.getAbilityList()[0];
        setAbility(newAbility, active, opponent);

        moveList = newForm.getLearnset();

        itemsNeededForForm = newForm.getItemsNeededForForm();
        moveNeededForForm = newForm.getMoveNeededForForm();
        forms = newForm.getForms();
        resetFormOnSwitch = newForm.formResetsOnSwitch();
        setBaseHP(newForm.getBaseHP());
        for (Stat stat : getStats()) {
            setBaseStat(stat.getNameShort(), newForm.getBaseStat(stat.getNameShort()));
        }

        if (App.battleStarted) {
            setDefaultValues();
        }
    }

    public boolean isTransformed() {
        return transformed;
    }

    public Pokemon getPokemonTransformedInto() {
        return pokemonTransformedInto;
    }

    public boolean transform(Pokemon pokemon, boolean transformTest) {
        if (transformed) {
            return false;
        }

        if (pokemon.transformed) {
            return false;
        }

        if (pokemon.getAbility().compare(AbilityList.illusion) &&
            pokemon.getAbility().isActive()) {
            return false;
        }

        if (transformTest) {
            return true;
        }

        setDefaultValues();

        transformed = true;
        pokemonTransformedInto = pokemon;

        species = pokemon.species;
        setTypes(pokemon.types);
        gender = pokemon.gender;
        weight = pokemon.weight;
        setMoves(pokemon.moves);
        for (Move move : moves) {
            if (move != null) {
                move.setPP(5);
                move.setCurrentPP(5);
            }
        }

        for (Stat stat : getStats()) {
            setBaseStat(stat.getNameShort(), pokemon.getBaseStat(stat.getNameShort()));
            stat.setStages(pokemon.getStat(stat.getNameShort()).getStages(null, null));
        }
        setAbility(pokemon.ability, true, pokemon);

        return true;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void giveItem(Item item) {
        this.item = item;
        if (ability.shouldActivate(AbilityActivation.ItemGained)) {
            ability.activate(this, this, null, null, null, null, null, 0, AbilityActivation.ItemGained);
        }
        if (item.shouldActivate(ItemActivation.Given)) {
            item.activate(this, this, this, null, null, ItemActivation.Given);
        }
    }

    public Item takeItem() {
        if (ability.shouldActivate(AbilityActivation.ItemConsumed)) {
            ability.activate(this, this, null, null, null, null, null, 0, AbilityActivation.ItemConsumed);
        }
        Item takenItem = item;
        item = ItemList.none;
        return takenItem;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        updateHP();
        for (Stat stat : getStats()) {
            updateStat(stat.getNameShort());
        }
    }

    public int getBaseHP() {
        return baseHP;
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

    public void setBaseHP(int value) {
        baseHP = value;
        updateHP();
    }

    public void setBaseStat(StatName stat, int value) {
        switch (stat) {
            case Atk:
                baseAtk = value;
                updateAtk();
                break;

            case Def:
                baseDef = value;
                updateDef();
                break;

            case SpA:
                baseSpA = value;
                updateSpA();
                break;

            case SpD:
                baseSpD = value;
                updateSpD();
                break;

            case Spe:
                baseSpe = value;
                updateSpe();
                break;

            default:
                break;
        }
    }

    public int getHP() {
        return HP;
    }

    public Stat getStat(StatName stat) {
        switch (stat) {
            case Atk:
                return Atk;

            case Def:
                return Def;

            case SpA:
                return SpA;

            case SpD:
                return SpD;

            case Spe:
                return Spe;

            case Acc:
                return Acc;

            case Eva:
                return Eva;

            default:
                return null;
        }
    }

    public Stat[] getStats() {
        return new Stat[] {Atk, Def, SpA, SpD, Spe, Acc, Eva};
    }

    public int getIV(StatName stat) {
        switch (stat) {
            case HP:
                return ivHP;

            case Atk:
                return ivAtk;

            case Def:
                return ivDef;

            case SpA:
                return ivSpA;

            case SpD:
                return ivSpD;

            case Spe:
                return ivSpe;

            default:
                return 0;
        }
    }

    public int getEV(StatName stat) {
        switch (stat) {
            case HP:
                return evHP;

            case Atk:
                return evAtk;

            case Def:
                return evDef;

            case SpA:
                return evSpA;

            case SpD:
                return evSpD;

            case Spe:
                return evSpe;

            default:
                return 0;
        }
    }

    public void setIV(StatName stat, int value) {
        if (value < 0) {
            System.out.println("!- The minimum value for IVs is 0 -!");
            value = 0;
        } else if (value > 31) {
            System.out.println("!- The maximum value for IVs is 31 -!");
            value = 31;
        }

        switch (stat) {
            case HP:
                ivHP = value;
                updateHP();
                break;

            case Atk:
                ivAtk = value;
                updateAtk();
                break;

            case Def:
                ivDef = value;
                updateDef();
                break;

            case SpA:
                ivSpA = value;
                updateSpA();
                break;

            case SpD:
                ivSpD = value;
                updateSpD();
                break;

            case Spe:
                ivSpe = value;
                updateSpe();
                break;

            default:
                break;
        }
    }

    public void setEV(StatName stat, int value) {
        if (value < 0) {
            System.out.println("!- The minimum value for a singular EV is 0 -!");
            value = 0;
        } else if (value > 252) {
            System.out.println("!- The maximum value for a singular EV is 252 -!");
            value = 252;
        }

        if (evHP + evAtk + evDef + evSpA + evSpD + evSpe + value > 510) {
            System.out.println("!- The maximum total value for EVs is 510 -!");
            value = 510 - (evHP + evAtk + evDef + evSpA + evSpD + evSpe);
        }

        switch (stat) {
            case HP:
                evHP = value;
                updateHP();
                break;

            case Atk:
                evAtk = value;
                updateAtk();
                break;

            case Def:
                evDef = value;
                updateDef();
                break;

            case SpA:
                evSpA = value;
                updateSpA();
                break;

            case SpD:
                evSpD = value;
                updateSpD();
                break;

            case Spe:
                evSpe = value;
                updateSpe();
                break;

            default:
                break;
        }
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
        updateHP();
        for (Stat stat : getStats()) {
            updateStat(stat.getNameShort());
        }
    }

    public void updateStat(StatName stat) {
        switch (stat) {
            case Atk:
                updateAtk();
                break;

            case Def:
                updateDef();
                break;

            case SpA:
                updateSpA();
                break;

            case SpD:
                updateSpD();
                break;

            case Spe:
                updateSpe();
                break;

            default:
                break;
        }
    }

    public void updateHP() {
        int newHP = (int) Math.floor(0.01*(2 * baseHP + ivHP + Math.floor(0.25 * evHP) )*level) + level + 10;
        int newCurrentHP = currentHP;

        if (ability.compare(AbilityList.darkest_day)) {
            newHP *= 1.5;
        }

        if (compare(Data.get().getPokemon("eternatus"), true)) {
            newCurrentHP = (newHP*currentHP)/HP;
        } else {
            newCurrentHP = currentHP + newHP - HP;
        }

        if (newCurrentHP <= 0) {
            if (!Battle.faintCheck(this, false)) {
                newCurrentHP = 1;
            } else {
                newCurrentHP = 0;
            }
        }

        HP = newHP;

        setCurrentHP(newCurrentHP);
    }

    public void updateAtk() {
        int ivValue = ivAtk;
        int evValue = evAtk;
        int levelValue = level;
        double natureMultiplier = nature.multiplier(Atk);
        if (transformed) {
            ivValue = pokemonTransformedInto.ivAtk;
            evValue = pokemonTransformedInto.evAtk;
            levelValue = pokemonTransformedInto.level;
            natureMultiplier = pokemonTransformedInto.nature.multiplier(Atk);
        }

        int valueAtk = (int) ((Math.floor(0.01*(2 * baseAtk + ivValue + Math.floor(0.25 * evValue))*levelValue) + 5) * natureMultiplier);
        Atk.setValue(valueAtk);
    }

    public void updateDef() {
        int ivValue = ivDef;
        int evValue = evDef;
        int levelValue = level;
        double natureMultiplier = nature.multiplier(Def);
        if (transformed) {
            ivValue = pokemonTransformedInto.ivDef;
            evValue = pokemonTransformedInto.evDef;
            levelValue = pokemonTransformedInto.level;
            natureMultiplier = pokemonTransformedInto.nature.multiplier(Def);
        }

        int valueDef = (int) ((Math.floor(0.01*(2 * baseDef + ivValue + Math.floor(0.25 * evValue))*levelValue) + 5) * natureMultiplier);
        Def.setValue(valueDef);
    }

    public void updateSpA() {
        int ivValue = ivSpA;
        int evValue = evSpA;
        int levelValue = level;
        double natureMultiplier = nature.multiplier(SpA);
        if (transformed) {
            ivValue = pokemonTransformedInto.ivSpA;
            evValue = pokemonTransformedInto.evSpA;
            levelValue = pokemonTransformedInto.level;
            natureMultiplier = pokemonTransformedInto.nature.multiplier(SpA);
        }

        int valueSpA = (int) ((Math.floor(0.01*(2 * baseSpA + ivValue + Math.floor(0.25 * evValue))*levelValue) + 5) * natureMultiplier);
        SpA.setValue(valueSpA);
    }

    public void updateSpD() {
        int ivValue = ivSpD;
        int evValue = evSpD;
        int levelValue = level;
        double natureMultiplier = nature.multiplier(SpD);
        if (transformed) {
            ivValue = pokemonTransformedInto.ivSpD;
            evValue = pokemonTransformedInto.evSpD;
            levelValue = pokemonTransformedInto.level;
            natureMultiplier = pokemonTransformedInto.nature.multiplier(SpD);
        }

        int valueSpD = (int) ((Math.floor(0.01*(2 * baseSpD + ivValue + Math.floor(0.25 * evValue))*levelValue) + 5) * natureMultiplier);
        SpD.setValue(valueSpD);
    }

    public void updateSpe() {
        int ivValue = ivSpe;
        int evValue = evSpe;
        int levelValue = level;
        double natureMultiplier = nature.multiplier(Spe);
        if (transformed) {
            ivValue = pokemonTransformedInto.ivSpe;
            evValue = pokemonTransformedInto.evSpe;
            levelValue = pokemonTransformedInto.level;
            natureMultiplier = pokemonTransformedInto.nature.multiplier(Spe);
        }

        int valueSpe = (int) ((Math.floor(0.01*(2 * baseSpe + ivValue + Math.floor(0.25 * evValue))*levelValue) + 5) * natureMultiplier);
        Spe.setValue(valueSpe);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        if (currentHP <= HP) {
            this.currentHP = currentHP;
        } else {
            this.currentHP = HP;
        }
    }

    public StatusCondition getNonVolatileStatus() {
        return nonVolatileStatus;
    }

    public void setNonVolatileStatus(StatusCondition nonVolatileStatus) {
        this.nonVolatileStatus = nonVolatileStatus;
    }

    public void endNonVolatileStatus(boolean showMessages) {
        nonVolatileStatus.end(this, showMessages);
    }

    public ArrayList<StatusCondition> getVolatileStatusList() {
        return volatileStatus;
    }

    public ArrayList<StatusCondition> orderVolatileStatusList() {
        ArrayList<StatusCondition> newVolatileStatus = new ArrayList<>();

        for (StatusCondition statusCondition : volatileStatus) {
            if (!statusCondition.compare(StatusConditionList.placeholder)) {
                newVolatileStatus.add(statusCondition);
            }
        }

        volatileStatus = newVolatileStatus;
        return volatileStatus;
    }

    public StatusCondition getVolatileStatus(StatusCondition original) {
        for (StatusCondition status : volatileStatus) {
            if (status.compare(original)) {
                return status;
            }
        }

        return null;
    }

    public StatusCondition getVolatileStatus(StatusCondition original, Pokemon causer) {
        for (StatusCondition status : volatileStatus) {
            if (status.compare(original) && status.getCauser() == causer) {
                return status;
            }
        }

        return null;
    }

    public void addVolatileStatus(StatusCondition volatileStatus) {
        this.volatileStatus.add(volatileStatus);
    }

    public void removeVolatileStatus(StatusCondition volatileStatus, Pokemon causer) {
        for (StatusCondition status : this.volatileStatus) {
            if (status.compare(volatileStatus) &&
                (causer == null || status.getCauser() == causer)) {
                int index = this.volatileStatus.indexOf(status);
                this.volatileStatus.set(index, StatusConditionList.placeholder.cause(null, this));
                break;
            }
        }
    }

    public void endVolatileStatus(StatusCondition volatileStatus, boolean showMessages) {
        for (StatusCondition status : this.volatileStatus) {
            if (volatileStatus.compare(status)) {
                volatileStatus = status;
                break;
            };
        }
        volatileStatus.end(this, showMessages);
    }

    public void endVolatileStatus(StatusCondition volatileStatus, Pokemon causer, boolean showMessages) {
        for (StatusCondition status : this.volatileStatus) {
            if (volatileStatus.compare(status) && status.getCauser() == causer) {
                volatileStatus = status;
                break;
            };
        }
        volatileStatus.end(this, showMessages);
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getBattleAction() {
        return battleAction;
    }

    public void setBattleAction(int battleAction) {
        this.battleAction = battleAction;
    }

    public Object[] getDefaultValues() {
        return defaultValues;
    }

    public Move getReadiedMove() {
        return readiedMove;
    }

    public void setReadiedMove(Move readiedMove) {
        this.readiedMove = readiedMove;
    }

    public Move getLastUsedMove() {
        return lastUsedMove;
    }

    public void setLastUsedMove(Move lastUsedMove) {
        if (lastUsedMove == null) {
            consecutiveProtections = 0;
            this.lastUsedMove = null;
            return;
        }

        if (!lastUsedMove.compare(MoveList.detect) &&
            !lastUsedMove.compare(MoveList.endure) &&
            !lastUsedMove.compare(MoveList.protect) &&
            !lastUsedMove.compare(MoveList.spiky_shield) &&
            !lastUsedMove.compare(MoveList.max_guard)) {
            consecutiveProtections = 0;
        }

        if (lastUsedMove.getMoveOrigin() != null) {
            lastUsedMove = lastUsedMove.getMoveOrigin();
        }

        this.lastUsedMove = lastUsedMove;
    }

    public int getTurnsOnField() {
        return turnsOnField;
    }

    public void addTurnOnField() {
        turnsOnField++;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void addDamageDealt(int damage) {
        damageDealt += damage;
    }

    public void setDefaultValues() {
        defaultValues[0] = species;
        defaultValues[1] = types[0];
        defaultValues[2] = types[1];
        defaultValues[3] = types[2];
        defaultValues[4] = gender;
        defaultValues[5] = weight;
        defaultValues[6] = ability;
        defaultValues[7] = moves[0];
        defaultValues[8] = moves[1];
        defaultValues[9] = moves[2];
        defaultValues[10] = moves[3];
        defaultValues[11] = baseAtk;
        defaultValues[12] = baseDef;
        defaultValues[13] = baseSpA;
        defaultValues[14] = baseSpD;
        defaultValues[15] = baseSpe;
        defaultValues[16] = (int) ((Math.floor(0.01*(2 * baseAtk + ivAtk + Math.floor(0.25 * evAtk))*level) + 5) * nature.multiplier(Atk));
        defaultValues[17] = (int) ((Math.floor(0.01*(2 * baseDef + ivDef + Math.floor(0.25 * evDef))*level) + 5) * nature.multiplier(Def));
        defaultValues[18] = (int) ((Math.floor(0.01*(2 * baseSpA + ivSpA + Math.floor(0.25 * evSpA))*level) + 5) * nature.multiplier(SpA));
        defaultValues[19] = (int) ((Math.floor(0.01*(2 * baseSpD + ivSpD + Math.floor(0.25 * evSpD))*level) + 5) * nature.multiplier(SpD));
        defaultValues[20] = (int) ((Math.floor(0.01*(2 * baseSpe + ivSpe + Math.floor(0.25 * evSpe))*level) + 5) * nature.multiplier(Spe));
    }

    public void restoreDefaultValues() {
        species = (String) defaultValues[0];
        types[0] = (Type) defaultValues[1];
        types[1] = (Type) defaultValues[2];
        types[2] = (Type) defaultValues[3];
        gender = (String) defaultValues[4];
        weight = (double) defaultValues[5];
        ability = (Ability) defaultValues[6];
        moves[0] = (Move) defaultValues[7];
        moves[1] = (Move) defaultValues[8];
        moves[2] = (Move) defaultValues[9];
        moves[3] = (Move) defaultValues[10];
        baseAtk = (int) defaultValues[11];
        baseDef = (int) defaultValues[12];
        baseSpA = (int) defaultValues[13];
        baseSpD = (int) defaultValues[14];
        baseSpe = (int) defaultValues[15];
        Atk.setValue((int) defaultValues[16]);
        Def.setValue((int) defaultValues[17]);
        SpA.setValue((int) defaultValues[18]);
        SpD.setValue((int) defaultValues[19]);
        Spe.setValue((int) defaultValues[20]);

        for (Stat stat : getStats()) {
            stat.setStages(0);
        }

        transformed = false;
        pokemonTransformedInto = null;

        volatileStatus.clear();
        ability.setPersistentActive(false);

        battleAction = 0;
        readiedMove = null;
        lastUsedMove = null;
        turnsOnField = 0;

        currentMoveFailed = false;
        lastMoveFailed = false;

        for (Move move : moves) {
            if (move != null) {
                move.setUsed(false);
                move.setConsecutiveUses(0);
            }
        }

        if (resetFormOnSwitch) {
            changeForm(baseForm.getForm());
        }
    }

    public void transferValues(Pokemon receiver) {
        for (Stat stat : getStats()) {
            receiver.getStat(stat.getNameShort()).setStages(stat.getTrueStages());
        }

        for (StatusCondition condition : volatileStatus) {
            if (condition.compare(StatusConditionList.confusion) ||
                condition.compare(StatusConditionList.pumped) ||
                condition.compare(StatusConditionList.trapped) ||
                condition.compare(StatusConditionList.suppressed_ability) && !receiver.getAbility().isNotSuppressable() ||
                condition.compare(StatusConditionList.seed) ||
                condition.compare(StatusConditionList.curse) ||
                condition.compare(StatusConditionList.substitute) ||
                condition.compare(StatusConditionList.ingrain) ||
                // Power Trick
                // Heal Block
                // Embargo
                condition.compare(StatusConditionList.perish_song) ||
                condition.compare(StatusConditionList.magnet_rise) ||
                condition.compare(StatusConditionList.aqua_ring)
                ) {
                receiver.volatileStatus.add(condition);
            }
        }
    }

    public int getConsecutiveProtections() {
        return consecutiveProtections;
    }

    public void setConsecutiveProtections(int protections) {
        consecutiveProtections = protections;
    }

    public void addConsecutiveProtection() {
        consecutiveProtections++;
    }

    // TODO mudar para doubles
    public boolean wasDamagedThisTurn() {
        return damagedThisTurn;
    }

    // TODO mudar para doubles
    public void setDamagedThisTurn(boolean damagedThisTurn, Pokemon damager) {
        this.damagedThisTurn = damagedThisTurn;
        this.damager = damager;
    }

    // TODO mudar para doubles
    public Pokemon getDamager() {
        return damager;
    }

    public boolean currentMoveFailed() {
        return currentMoveFailed;
    }

    public void setCurrentMoveFailed(boolean currentMoveFailed) {
        this.currentMoveFailed = currentMoveFailed;
    }

    public boolean lastMoveFailed() {
        return lastMoveFailed;
    }

    public void setLastMoveFailed(boolean lastMoveFailed) {
        this.lastMoveFailed = lastMoveFailed;
    }

    public boolean isGrounded(Move move) {
        boolean flyingType = (hasType(Data.get().getType("flying")) &&
                              !getType(Data.get().getType("flying")).isSuppressed());

        boolean levitate = (ability.compare(AbilityList.levitate) &&
                            ability.isActive() &&
                            getVolatileStatus(StatusConditionList.suppressed_ability) == null);
        if (levitate && move != null && !ability.shouldActivate(move, null)) {
            levitate = false;
        }

        boolean magnetRise = getVolatileStatus(StatusConditionList.magnet_rise) != null;

        boolean gravity = false;
        for (FieldCondition fieldCondition : Battle.generalField) {
            if (fieldCondition.compare(FieldConditionList.gravity)) {
                gravity = true;
                break;
            }
        }

        boolean ingrain = getVolatileStatus(StatusConditionList.ingrain) != null;

        boolean groundedCondition = getVolatileStatus(StatusConditionList.grounded) != null;

        boolean moveHitsAirborne = move != null && move.hasInherentProperty(InherentProperty.HitsAirborne);


        return ((!flyingType && !levitate && !magnetRise) || gravity || ingrain || groundedCondition || moveHitsAirborne);
    }


    public boolean compare(Pokemon other, boolean trueSpecies) {
        return trueSpecies ? this.originalSpecies.equals(other.originalSpecies) : this.species.equals(other.species);
    }

    public boolean compare(PokemonTemplate template, boolean trueSpecies) {
        return trueSpecies ? this.originalSpecies.equals(template.getName()) : this.species.equals(template.getName());
    }

    public boolean compareWithForm(Pokemon other) {
        return this.species.equals(other.species) && this.form.equals(other.form);
    }

    public boolean compareWithForm(PokemonTemplate template) {
        return this.species.equals(template.getName()) && this.form.equals(template.getForm());
    }
}