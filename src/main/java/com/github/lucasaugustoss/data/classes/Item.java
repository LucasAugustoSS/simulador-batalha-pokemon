package com.github.lucasaugustoss.data.classes;

import java.util.Arrays;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.ItemEffect;
import com.github.lucasaugustoss.data.objects.templates.ItemTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.items.*;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class Item {
    private String name;
    private boolean consumable;
    private boolean consumed;
    private ItemType type;
    private PokemonTemplate[] users;
    private boolean tetheredToValidUser;
    private PokemonTemplate transformsInto;
    private TypeTemplate changesTypeTo;
    private Move zMove;
    private Move zMoveOrigin;
    private ItemEffect effect;
    private boolean cantFling;
    private double flingPower;
    private ItemEffect flingEffect;

    private boolean activated;

    private Pokemon holder;

    public Item(ItemTemplate template, Pokemon holder) { // create
        this.name = template.getName();
        this.consumable = template.isConsumable();
        this.type = template.getType();
        this.users = template.getUsers();
        this.tetheredToValidUser = template.isTetheredToValidUser();
        this.transformsInto = template.getTransformsInto();
        this.changesTypeTo = template.getChangesTypeTo();
        this.zMove = template.getZMove();
        this.zMoveOrigin = template.getZMoveOrigin();
        this.effect = template.getEffect();
        this.flingPower = template.getFlingPower();
        this.flingEffect = template.getFlingEffect();
        this.holder = holder;
    }

    public Item(Item original, Pokemon holder) { // copy
        this.name = original.name;
        this.consumable = original.consumable;
        this.type = original.type;
        this.users = original.users;
        this.tetheredToValidUser = original.tetheredToValidUser;
        this.transformsInto = original.transformsInto;
        this.changesTypeTo = original.changesTypeTo;
        this.zMove = original.zMove;
        this.zMoveOrigin = original.zMoveOrigin;
        this.effect = original.effect;
        this.flingPower = original.flingPower;
        this.flingEffect = original.flingEffect;
        this.holder = holder;
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

    public Move getZMove() {
        return zMove;
    }

    public Move getZMoveOrigin() {
        return zMoveOrigin;
    }

    public ItemEffect getEffect() {
        return effect;
    }

    public Object activate(Pokemon holder, Pokemon user, Pokemon opponent, Move move, Damage damage, ItemActivation activation) {
        if (App.battleStarted) {
            if (effect != null) {
                return effect.activate(this, holder, user, opponent, move, damage, activation);
            }
        }
        return null;
    }

    public ItemActivation[] getActivation() {
        return effect.getActivation();
    }

    public boolean shouldActivate(ItemActivation condition) {
        if (condition != null) {
            if (effect == null) {
                return false;
            }
    
            if (!effect.shouldActivate(condition)) {
                return false;
            }
        }

        for (FieldCondition fieldCondition : Battle.generalField) {
            if (Arrays.asList(fieldCondition.getFieldActivation()).contains(FieldActivation.TryUseItem) &&
                !((boolean) fieldCondition.activate(holder, null, null, null, null, null, 0, false, true, FieldActivation.TryUseItem))) {
                return false;
            }
        }

        Pokemon opponent;
        if (holder.getTeam() == 0) {
            opponent = Battle.opponentActivePokemon;
        } else {
            opponent = Battle.yourActivePokemon;
        }

        if (type == ItemType.Berry &&
            opponent.getAbility().shouldActivate(AbilityActivation.OpponentTryUseBerry) &&
            !((boolean) opponent.getAbility().activate(opponent, holder, null, null, null, null, null, 0, AbilityActivation.OpponentTryUseBerry))) {
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
                return flingEffect.activate(this, holder, user, opponent, move, null, null);
            }
        }
        return null;
    }

    public boolean isTetheredToValidUser() {
        return tetheredToValidUser;
    }

    public boolean wasActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Pokemon getHolder() {
        return holder;
    }

    public void consume(boolean selfConsumed, boolean destroyed) {
        holder.setItem(Data.get().getItem("none"));

        if (holder.getAbility().shouldActivate(AbilityActivation.ItemConsumed)) {
            holder.getAbility().activate(holder, holder, null, null, null, null, null, 0, AbilityActivation.ItemConsumed);
        }
    }

    public boolean compare(Item other) {
        return this.name.equals(other.name);
    }

    public boolean compare(ItemTemplate template) {
        return this.name.equals(template.getName());
    }
}
