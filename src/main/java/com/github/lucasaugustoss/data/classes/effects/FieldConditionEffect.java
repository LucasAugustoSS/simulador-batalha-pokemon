package com.github.lucasaugustoss.data.classes.effects;

import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;

public interface FieldConditionEffect {
    public Object activate(FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation);
}
