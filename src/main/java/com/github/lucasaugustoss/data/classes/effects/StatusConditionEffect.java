package com.github.lucasaugustoss.data.classes.effects;

import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.simulator.Damage;


public interface StatusConditionEffect {
    public Object activate(StatusCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Damage damage, boolean showMessages, StatusActivation activation);
}
