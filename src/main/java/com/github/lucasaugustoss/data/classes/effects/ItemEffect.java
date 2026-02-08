package com.github.lucasaugustoss.data.classes.effects;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.simulator.Damage;


public interface ItemEffect {
    public Object activate(Item thisItem, Pokemon holder, Pokemon user, Pokemon opponent, Move move, Damage damage, ItemActivation activation);
}
