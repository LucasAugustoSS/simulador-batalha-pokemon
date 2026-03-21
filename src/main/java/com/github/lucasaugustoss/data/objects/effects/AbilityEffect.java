package com.github.lucasaugustoss.data.objects.effects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.AbilityEffectFunction;
import com.github.lucasaugustoss.simulator.Damage;

public class AbilityEffect {
    private String type;
    private AbilityActivation[] activation;
    private AbilityEffectFunction effect;

    public AbilityEffect( // create
        String type,
        AbilityActivation[] activation,
        AbilityEffectFunction effect
    ) {
        this.type = type;
        this.activation = activation;
        this.effect = effect;
    }

    public AbilityEffect(AbilityEffect original) { // copy
        this.type = original.type;
        this.activation = original.activation;
        this.effect = original.effect;
    }

    public AbilityActivation[] getActivation() {
        return activation;
    }

    public boolean shouldActivate(AbilityActivation condition) {
        return condition != null && Arrays.asList(activation).contains(condition);
    }

    public Object activate(Ability thisAbility, Pokemon self, Pokemon opponent, Move move, Type type, Damage damage, StatusCondition statusCondition, Stat stat, int statChangeStages, AbilityActivation condition) {
        if (effect != null) {
            return effect.activate(thisAbility, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition);
        }
        return null;
    }
}
