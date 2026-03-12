package com.github.lucasaugustoss.data.objects.effects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.effectFunctions.StatusConditionEffectFunction;
import com.github.lucasaugustoss.simulator.Damage;

public class StatusConditionEffect {
    private String type;
    private StatusActivation[] activation;
    private StatusConditionEffectFunction effect;

    public StatusConditionEffect( // create
        String type,
        StatusActivation[] activation,
        StatusConditionEffectFunction effect
    ) {
        this.type = type;
        this.activation = activation;
        this.effect = effect;
    }

    public StatusConditionEffect(StatusConditionEffect original) { // copy
        this.type = original.type;
        this.activation = original.activation;
        this.effect = original.effect;
    }

    public StatusActivation[] getActivation() {
        return activation;
    }

    public boolean shouldActivate(StatusActivation condition) {
        return condition != null && Arrays.asList(activation).contains(condition);
    }

    public Object activate(StatusCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Damage damage, boolean showMessages, StatusActivation activation) {
        if (effect != null) {
            return effect.activate(thisCondition, pokemon, opponent, move, damage, showMessages, activation);
        }
        return null;
    }
}
