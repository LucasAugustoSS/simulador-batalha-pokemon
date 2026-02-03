package data.classes.effects;

import data.activationConditions.ItemActivation;
import data.classes.Item;
import data.classes.Move;
import data.classes.Pokemon;
import main.Damage;

public interface ItemEffect {
    public Object activate(Item thisItem, Pokemon holder, Pokemon user, Pokemon opponent, Move move, Damage damage, ItemActivation activation);
}
