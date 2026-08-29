package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.ItemEffect;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.items.*;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class Item {
    private ItemTemplate template;

    private String name;
    private boolean consumable;
    private boolean consumed;
    private ItemCategory[] categories;
    private ItemType type;
    private PokemonTemplate[] users;
    private boolean tetheredToValidUser;
    private PokemonTemplate transformsInto;
    private TypeTemplate changesTypeTo;
    private MoveTemplate zMove;
    private MoveTemplate zMoveOrigin;
    private ItemEffect[] effects;
    private boolean cantFling;
    private double flingPower;
    private ItemEffect flingEffect;

    private Message messages;

    private boolean activated;
    private Move affectedMove;

    private Pokemon holder;
    private Pokemon originalHolder;

    public Item(ItemTemplate template, Pokemon holder) { // create
        this.template = template;
        this.name = template.getName();
        this.consumable = template.isConsumable();
        this.categories = template.getCategories();
        this.type = template.getType();
        this.users = template.getUsers();
        this.tetheredToValidUser = template.isTetheredToValidUser();
        this.transformsInto = template.getTransformsInto();
        this.changesTypeTo = template.getChangesTypeTo();
        this.zMove = template.getZMove();
        this.zMoveOrigin = template.getZMoveOrigin();
        this.effects = template.getEffects();
        this.flingPower = template.getFlingPower();
        this.flingEffect = template.getFlingEffect();
        this.messages = template.getMessages();
        this.holder = holder;
        this.originalHolder = holder;
    }

    public Item(Item original, Pokemon holder) { // copy
        this.template = original.template;
        this.name = original.name;
        this.consumable = original.consumable;
        this.categories = original.categories;
        this.type = original.type;
        this.users = original.users;
        this.tetheredToValidUser = original.tetheredToValidUser;
        this.transformsInto = original.transformsInto;
        this.changesTypeTo = original.changesTypeTo;
        this.zMove = original.zMove;
        this.zMoveOrigin = original.zMoveOrigin;
        this.effects = original.effects;
        this.flingPower = original.flingPower;
        this.flingEffect = original.flingEffect;
        this.messages = original.messages;
        this.holder = holder;
        this.originalHolder = holder;
    }



    public ItemTemplate getTemplate() {
        return template;
    }

    public String getName() {
        return name;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public ItemCategory[] getCategories() {
        return categories;
    }

    public boolean isCategory(ItemCategory category) {
        for (ItemCategory itemCategory : categories) {
            if (itemCategory == category) {
                return true;
            }
        }
        return false;
    }

    public ItemType getType() {
        return type;
    }

    public PokemonTemplate[] getUsers() {
        return users;
    }

    public boolean isValidUser(Pokemon pokemon) {
        if (users.length == 0) {
            return true;
        }

        for (PokemonTemplate user : Arrays.asList(users)) {
            if (user.compare(pokemon, true)) {
                return true;
            }
        }
        return false;
    }

    public boolean heldByValidUser(boolean trueSpecies) {
        if (users.length == 0) {
            return true;
        }

        for (PokemonTemplate user : Arrays.asList(users)) {
            if (user.compare(holder, trueSpecies)) {
                return true;
            }
        }
        return false;
    }

    public boolean heldByValidForm(boolean trueSpecies) {
        if (users.length == 0) {
            return true;
        }

        for (PokemonTemplate user : Arrays.asList(users)) {
            if (user.compare(holder, trueSpecies) &&
                user.compareWithForm(holder)) {
                return true;
            }
        }
        return false;
    }

    public PokemonTemplate getTransformsInto() {
        return transformsInto;
    }

    public TypeTemplate getChangesTypeTo() {
        return changesTypeTo;
    }

    public MoveTemplate getZMove() {
        return zMove;
    }

    public MoveTemplate getZMoveOrigin() {
        return zMoveOrigin;
    }

    public ItemEffect[] getEffects() {
        return effects;
    }

    public Object activate(Pokemon holder, Pokemon user, Pokemon opponent, Move move, Damage damage, boolean showMessages, ItemActivation activation) {
        if (App.battleStarted) {
            for (ItemEffect effect : effects) {
                if (effect.shouldActivate(activation)) {
                    return effect.activate(this, holder, user, opponent, move, damage, showMessages, activation);
                }
            }
        }
        return null;
    }

    public ItemActivation[] getActivation() {
        List<ItemActivation> conditions = new ArrayList<>();

        for (ItemEffect effect : effects) {
            for (ItemActivation condition : effect.getActivation()) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        return conditions.toArray(new ItemActivation[0]);
    }

    public boolean shouldActivate(ItemActivation condition) {
        if (condition != null) {
            if (effects.length == 0) {
                return false;
            }

            if (!Arrays.asList(getActivation()).contains(condition)) {
                return false;
            }
        }

        for (FieldCondition fieldCondition : Battle.generalField) {
            if (Arrays.asList(fieldCondition.getFieldActivation()).contains(FieldActivation.TryUseItem) &&
                !((boolean) fieldCondition.activate(holder, null, null, null, null, null, 0, false, true, FieldActivation.TryUseItem))) {
                return false;
            }
        }

        Pokemon opponent = Battle.getOpposingPokemon(holder.getTeam());

        if (type == ItemType.Berry &&
            opponent.getAbility().shouldActivate(AbilityActivation.OpponentTryUseBerry) &&
            !((boolean) opponent.getAbility().activate(opponent, holder, null, null, null, 0, null, null, 0, true, AbilityActivation.OpponentTryUseBerry))) {
            return false;
        }

        return true;
    }

    public boolean cantFling() {
        return cantFling;
    }

    public double getFlingPower() {
        return flingPower;
    }

    public ItemEffect getFlingEffect() {
        return flingEffect;
    }

    public Object activateFlingEffect(Pokemon holder, Pokemon user, Pokemon opponent, Move move) {
        if (App.battleStarted) {
            if (flingEffect != null) {
                return flingEffect.activate(this, holder, user, opponent, move, null, true, null);
            }
        }
        return null;
    }

    public boolean isTetheredToValidUser() {
        return tetheredToValidUser;
    }

    public Message getMessages() {
        return messages;
    }

    public boolean wasActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Move getAffectedMove() {
        return affectedMove;
    }

    public void setAffectedMove(Move affectedMove) {
        this.affectedMove = affectedMove;
    }

    public Pokemon getHolder() {
        return holder;
    }

    public void setHolder(Pokemon holder) {
        this.holder = holder;
    }

    public Pokemon getOriginalHolder() {
        return originalHolder;
    }

    public void setOriginalHolder(Pokemon originalHolder) {
        this.originalHolder = originalHolder;
    }

    public void consume(boolean selfConsumed, boolean destroyed) {
        if (selfConsumed) {
            holder.setConsumedItem(this);
        }
        holder.setItem(Data.get().getItem("none"));

        if (holder.getAbility().shouldActivate(AbilityActivation.ItemConsumed)) {
            holder.getAbility().activate(holder, holder, null, null, null, 0, null, null, 0, true, AbilityActivation.ItemConsumed);
        }
    }

    public boolean compare(Item other) {
        return this.name.equals(other.name);
    }

    public boolean compare(ItemTemplate template) {
        return this.name.equals(template.getName());
    }
}
