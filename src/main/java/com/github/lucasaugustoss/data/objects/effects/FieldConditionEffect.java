package com.github.lucasaugustoss.data.objects.effects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.FieldConditionEffectFunction;

public class FieldConditionEffect {
    private String type;
    private FieldActivation[] activation;
    private FieldConditionEffectFunction effect;

    public FieldConditionEffect( // create
        String type,
        FieldActivation[] activation,
        FieldConditionEffectFunction effect
    ) {
        this.type = type;
        this.activation = activation;
        this.effect = effect;
    }

    public FieldConditionEffect(FieldConditionEffect original) { // copy
        this.type = original.type;
        this.activation = original.activation;
        this.effect = original.effect;
    }

    public FieldActivation[] getActivation() {
        return activation;
    }

    public boolean shouldActivate(FieldActivation condition) {
        return condition != null && Arrays.asList(activation).contains(condition);
    }

    public Object activate(FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) {
        if (effect != null) {
            return effect.activate(thisCondition, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation);
        }
        return null;
    }
}
