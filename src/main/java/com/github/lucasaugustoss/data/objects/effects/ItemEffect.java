package com.github.lucasaugustoss.data.objects.effects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.effectFunctions.ItemEffectFunction;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.simulator.Damage;

public class ItemEffect {
    private String type;
    private boolean sheerForceNegated;
    private ItemActivation[] activation;
    private ItemEffectFunction effect;

    public ItemEffect( // create
        String type,
        boolean sheerForceNegated,
        ItemActivation[] activation,
        ItemEffectFunction effect
    ) {
        this.type = type;
        this.sheerForceNegated = sheerForceNegated;
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

    public boolean shouldActivate(Move move, ItemActivation condition) {
        if (move == null) {
            return shouldActivate(condition);
        }

        if (condition == null) {
            return false;
        }

        if (!Arrays.asList(activation).contains(condition)) {
            return false;
        }

        if (sheerForceNegated && move.getTemporaryProperties().contains(TemporaryProperty.SheerForceBoosted)) {
            return false;
        }

        return true;
    }

    public Object activate(Item item, Pokemon holder, Pokemon user, Pokemon opponent, Move move, Damage damage, boolean showMessages, ItemActivation activation) {
        if (effect != null) {
            return effect.activate(item, holder, user, opponent, move, damage, showMessages, activation);
        }
        return null;
    }
}
