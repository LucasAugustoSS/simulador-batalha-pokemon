package data.classes.effects;

import data.activationConditions.FieldActivation;
import data.classes.FieldCondition;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.Stat;
import data.classes.StatusCondition;
import data.classes.Type;

public interface FieldConditionEffect {
    public Object activate(FieldCondition thisCondition, Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation);
}
