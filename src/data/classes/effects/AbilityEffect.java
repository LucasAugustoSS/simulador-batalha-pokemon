package data.classes.effects;

import data.activationConditions.AbilityActivation;
import data.classes.Ability;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.Stat;
import data.classes.StatusCondition;
import data.classes.Type;

public interface AbilityEffect {
    public Object activate(Ability thisAbility, Pokemon self, Pokemon opponent, Move move, Type type, int damage, StatusCondition statusCondition, Stat stat, int statChangeStages, AbilityActivation condition);
}