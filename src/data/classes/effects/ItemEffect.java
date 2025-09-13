package data.classes.effects;

import data.activationConditions.ItemActivation;
import data.classes.Item;
import data.classes.Move;
import data.classes.Pokemon;

public interface ItemEffect {
    public Object activate(Item thisItem, Pokemon holder, Pokemon user, Pokemon opponent, Move move, int damage, ItemActivation activation);
}
