package data.classes.effects;

import data.activationConditions.MoveEffectActivation;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.Type;
import main.Damage;

public interface MoveEffect {
    public Object activate(Move thisMove, Pokemon user, Pokemon target, Type type, Damage damage, int hit, boolean showMessages, MoveEffectActivation condition);
}