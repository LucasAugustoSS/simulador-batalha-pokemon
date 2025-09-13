package data.classes.effects;

import data.activationConditions.StatusActivation;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.StatusCondition;

public interface StatusConditionEffect {
    public Object activate(StatusCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, int damage, boolean showMessages, StatusActivation activation);
}
