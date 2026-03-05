package com.github.lucasaugustoss.data.objects.effects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.effectFunctions.ItemEffectFunction;
import com.github.lucasaugustoss.simulator.Damage;

public class ItemEffect {
    private String type;
    private ItemActivation[] activation;
    private ItemEffectFunction effect;

    public ItemEffect( // create
        String type,
        ItemActivation[] activation,
        ItemEffectFunction effect
    ) {
        this.type = type;
        this.activation = activation;
        this.effect = effect;
    }

    public ItemEffect(ItemEffect original) { // copy
        this.type = original.type;
        this.activation = original.activation;
        this.effect = original.effect;
    }

    public ItemActivation[] getActivation() {
        return activation;
    }

    public boolean shouldActivate(ItemActivation condition) {
        return condition != null && Arrays.asList(activation).contains(condition);
    }

    public Object activate(Item item, Pokemon holder, Pokemon user, Pokemon opponent, Move move, Damage damage, ItemActivation activation) {
        if (effect != null) {
            return effect.activate(item, holder, user, opponent, move, damage, activation);
        }
        return null;
    }
}
