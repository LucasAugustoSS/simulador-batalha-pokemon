package com.github.lucasaugustoss.data.classes;

import java.util.Arrays;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.effects.ItemEffect;
import com.github.lucasaugustoss.data.objects.oldObjects.ItemList;
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
    private PokemonTemplate transformsInto;
    private TypeTemplate changesTypeTo;
    private Move zMove;
    private Move zMoveOrigin;
    private ItemEffect effect;
    private ItemActivation[] activation;
    private boolean cantFling;
    private double flingPower;
    private ItemEffect flingEffect;
    private boolean tetheredToValidUser;

    private boolean activated;

    private Pokemon holder;

    public Item( // default
        String name, boolean consumable,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = ItemType.Other;
        this.users = new PokemonTemplate[0];
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
    }
    public Item( // with type
        String name, boolean consumable,
        ItemType type,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = new PokemonTemplate[0];
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
    }
    public Item( // with type and users
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
    }
    public Item( // with type, users, and form it transforms into
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users, PokemonTemplate transformsInto,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.transformsInto = transformsInto;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
    }
    public Item( // with type, users, and form it transforms into (not flingable)
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users, PokemonTemplate transformsInto,
        ItemEffect effect, ItemActivation[] activation
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.transformsInto = transformsInto;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = true;
        this.flingPower = 0;
        this.flingEffect = null;
    }
    public Item( // with type, users, form it transforms into, and type it changes to
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users, PokemonTemplate transformsInto, TypeTemplate changesTypeTo,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.transformsInto = transformsInto;
        this.changesTypeTo = changesTypeTo;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
    }
    public Item( // with type, users, and form it transforms into (tethered to valid user)
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users, PokemonTemplate transformsInto,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect,
        boolean tetheredToValidUser
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.transformsInto = transformsInto;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
        this.tetheredToValidUser = tetheredToValidUser;
    }
    public Item( // with type, users, and form it transforms into (not flingable) (tethered to valid user)
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users, PokemonTemplate transformsInto,
        ItemEffect effect, ItemActivation[] activation,
        boolean tetheredToValidUser
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.transformsInto = transformsInto;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = true;
        this.flingPower = 0;
        this.flingEffect = null;
        this.tetheredToValidUser = tetheredToValidUser;
    }
    public Item( // with users and form it transforms into (not flingable) (tethered to valid user)
        String name, boolean consumable,
        PokemonTemplate[] users, PokemonTemplate transformsInto,
        ItemEffect effect, ItemActivation[] activation,
        boolean tetheredToValidUser
    ) {
        this.name = name;
        this.consumable = consumable;
        this.users = users;
        this.transformsInto = transformsInto;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = true;
        this.flingPower = 0;
        this.flingEffect = null;
        this.tetheredToValidUser = tetheredToValidUser;
    }
    public Item( // with type, users, form it transforms into, and type it changes to (tethered to valid user)
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users, PokemonTemplate transformsInto, TypeTemplate changesTypeTo,
        ItemEffect effect, ItemActivation[] activation,
        double flingPower, ItemEffect flingEffect,
        boolean tetheredToValidUser
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.transformsInto = transformsInto;
        this.changesTypeTo = changesTypeTo;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = false;
        this.flingPower = flingPower;
        this.flingEffect = flingEffect;
        this.tetheredToValidUser = tetheredToValidUser;
    }
    public Item( // with type and Z-Move (not flingable)
        String name, boolean consumable,
        ItemType type,
        Move zMove,
        ItemEffect effect, ItemActivation[] activation
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = new PokemonTemplate[0];
        this.zMove = zMove;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = true;
        this.flingPower = 0;
        this.flingEffect = null;
    }
    public Item( // with type, users, Z-Move, and Z-Move origin (not flingable)
        String name, boolean consumable,
        ItemType type, PokemonTemplate[] users,
        Move zMove, Move zMoveOrigin,
        ItemEffect effect, ItemActivation[] activation
    ) {
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.users = users;
        this.zMove = zMove;
        this.zMoveOrigin = zMoveOrigin;
        this.effect = effect;
        this.activation = activation;
        this.cantFling = true;
        this.flingPower = 0;
        this.flingEffect = null;
    }

    public Item( // copy object
        Item original, Pokemon holder
    ) {
        this.name = original.name;
        this.consumable = original.consumable;
        this.consumed = false;
        this.type = original.type;
        this.users = original.users;
        this.transformsInto = original.transformsInto;
        this.changesTypeTo = original.changesTypeTo;
        this.zMove = original.zMove;
        this.zMoveOrigin = original.zMoveOrigin;
        this.effect = original.effect;
        this.activation = original.activation;
        this.flingPower = original.flingPower;
        this.flingEffect = original.flingEffect;
        this.tetheredToValidUser = original.tetheredToValidUser;
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
        return activation;
    }

    public boolean shouldActivate(ItemActivation condition) {
        if (condition != null &&
            !Arrays.asList(activation).contains(condition)) {
            return false;
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
        holder.setItem(ItemList.none);

        if (holder.getAbility().shouldActivate(AbilityActivation.ItemConsumed)) {
            holder.getAbility().activate(holder, holder, null, null, null, null, null, 0, AbilityActivation.ItemConsumed);
        }
    }

    public boolean compare(Item item) {
        return this.name.equals(item.name);
    }
}
