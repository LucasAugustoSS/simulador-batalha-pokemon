package com.github.lucasaugustoss.data.objects.effects;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.classes.effectFunctions.MoveEffectFunction;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.simulator.Damage;

public class MoveEffect {
    private String type;
    private EffectTarget target;
    private int counter;
    private PokemonTemplate exclusiveUser;
    private boolean exclusiveForm;
    private double chance;
    private MoveEffectActivation[] activation;
    private MoveEffectFunction effect;
    private MoveEffectFunction defaultReturn;
    private Move move;

    public MoveEffect( // create
        String type,
        EffectTarget target,
        int counter,
        PokemonTemplate exclusiveUser, boolean exclusiveForm,
        double chance,
        MoveEffectActivation[] activation,
        MoveEffectFunction effect,
        MoveEffectFunction defaultReturn
    ) {
        this.type = type;
        this.target = target;
        this.counter = counter;
        this.exclusiveUser = exclusiveUser;
        this.exclusiveForm = exclusiveForm;
        this.chance = chance;
        this.activation = activation;
        this.effect = effect;
        this.defaultReturn = defaultReturn;
    }

    public MoveEffect(MoveEffect original) { // copy
        this.type = original.type;
        this.target = original.target;
        this.counter = original.counter;
        this.exclusiveUser = original.exclusiveUser;
        this.exclusiveForm = original.exclusiveForm;
        this.chance = original.chance;
        this.activation = original.activation;
        this.effect = original.effect;
        this.defaultReturn = original.defaultReturn;
    }

    public String getType() {
        return type;
    }

    public EffectTarget getTarget() {
        if (Arrays.asList(activation).contains(MoveEffectActivation.CallEffectTarget)) {
            return (EffectTarget) activate(move, move.getUser(), move.getUser(), null, null, 0, null, true, MoveEffectActivation.CallEffectTarget);
        }
        return target;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void countDown() {
        counter--;
    }

    public PokemonTemplate getExclusiveUser() {
        return exclusiveUser;
    }

    public boolean getExclusiveForm() {
        return exclusiveForm;
    }

    public double getChance() {
        return chance;
    }

    public MoveEffectActivation[] getActivation() {
        return activation;
    }

    public boolean shouldActivate(MoveEffectActivation condition) {
        return condition != null && Arrays.asList(activation).contains(condition);
    }

    public Object activate(Move thisMove, Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (effect != null) {
            if (exclusiveUser != null && (
                !user.compare(exclusiveUser, false) ||
                (exclusiveForm && !user.compareWithForm(exclusiveUser))
            )) {
                return activateDefault(thisMove, user, target, type, damage, hit, stat, showMessages, condition);
            }

            if (Math.random() >= chance) {
                return activateDefault(thisMove, user, target, type, damage, hit, stat, showMessages, condition);
            }

            return effect.activate(thisMove, this, user, target, type, damage, hit, stat, showMessages, condition);
        }
        return null;
    }

    public Object activateDefault(Move thisMove, Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (defaultReturn != null) {
            return defaultReturn.activate(thisMove, this, user, target, type, damage, hit, stat, showMessages, condition);
        }
        return null;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
