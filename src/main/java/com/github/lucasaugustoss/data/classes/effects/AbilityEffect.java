package com.github.lucasaugustoss.data.classes.effects;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.simulator.Damage;

public interface AbilityEffect {
    public Object activate(Ability thisAbility, Pokemon self, Pokemon opponent, Move move, Type type, Damage damage, StatusCondition statusCondition, Stat stat, int statChangeStages, AbilityActivation condition);
}