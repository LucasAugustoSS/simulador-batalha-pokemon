package com.github.lucasaugustoss.data.classes.effectFunctions;

import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.simulator.Damage;


public interface MoveEffectFunction {
    public Object activate(Move thisMove, Pokemon user, Pokemon target, Type type, Damage damage, int hit, boolean showMessages, MoveEffectActivation condition);
}