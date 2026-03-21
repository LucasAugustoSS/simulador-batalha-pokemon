package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.AbilityEffect;
import com.github.lucasaugustoss.data.objects.templates.AbilityTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.simulator.Damage;

public class Ability {
    private String name;
    private AbilityEffect[] effects;
    private boolean notTransferable;
    private boolean notReplaceable;
    private boolean notSuppressable;
    private boolean ignorable;
    private PokemonTemplate exclusiveUser;
    private Message messages;

    private boolean active;
    private boolean persistentEffectActive;
    private int counter;

    private Pokemon pokemon;

    public Ability( // create
        AbilityTemplate template, boolean active, Pokemon pokemon
    ) {
        this.name = template.getName();
        this.effects = template.getEffects();
        this.notTransferable = template.isNotTransferable();
        this.notReplaceable = template.isNotReplaceable();
        this.notSuppressable = template.isNotSuppressable();
        this.ignorable = template.isIgnorable();
        this.exclusiveUser = template.getExclusiveUser();
        this.messages = template.getMessages();
        this.active = active;
        this.pokemon = pokemon;
    }

    public Ability( // copy
        Ability original, boolean active, Pokemon pokemon
    ) {
        this.name = original.name;
        this.effects = original.effects;
        this.notTransferable = original.notTransferable;
        this.notReplaceable = original.notReplaceable;
        this.notSuppressable = original.notSuppressable;
        this.ignorable = original.ignorable;
        this.exclusiveUser = original.exclusiveUser;
        this.messages = original.messages;
        this.active = active;
        this.pokemon = pokemon;
    }



    public String getName() {
        return name;
    }

    public AbilityEffect[] getEffects() {
        return effects;
    }

    public AbilityActivation[] getConditions() {
        if (effects == null) {
            return new AbilityActivation[0];
        }

        ArrayList<AbilityActivation> conditions = new ArrayList<>();

        for (AbilityEffect effect : effects) {
            for (AbilityActivation condition : effect.getActivation()) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        return conditions.toArray(new AbilityActivation[0]);
    }

    public Object activate(Pokemon self, Pokemon opponent, Move move, Type type, Damage damage, StatusCondition statusCondition, Stat stat, int statChangeStages, AbilityActivation condition) {
        if (App.battleStarted && (
            exclusiveUser == null || pokemon.compare(exclusiveUser, true)
        )) {
            for (AbilityEffect effect : effects) {
                if (effect.shouldActivate(condition)) {
                    Object result = effect.activate(this, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition);

                    if (condition != AbilityActivation.AfterActivation &&
                        shouldActivate(AbilityActivation.AfterActivation)) {
                        activate(self, opponent, move, type, damage, statusCondition, stat, statChangeStages, AbilityActivation.AfterActivation);
                    }

                    return result;
                }
            }
        }
        return null;
    };

    public boolean isNotTransferable() {
        return notTransferable;
    }

    public boolean isNotReplaceable() {
        return notReplaceable;
    }

    public boolean isNotSuppressable() {
        return notSuppressable;
    }

    public boolean isIgnorable() {
        return ignorable;
    }

    public PokemonTemplate getExclusiveUser() {
        return exclusiveUser;
    }

    public Message getMessages() {
        return messages;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean persistentIsActive() {
        return persistentEffectActive;
    }

    public void setPersistentActive(boolean persistentEffectActive) {
        this.persistentEffectActive = persistentEffectActive;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean shouldActivate(AbilityActivation condition) {
        if (!active) {
            return false;
        }

        if (exclusiveUser != null && !pokemon.compare(exclusiveUser, true)) {
            return false;
        }

        if (condition != null &&
            !Arrays.asList(getConditions()).contains(condition)) {
            return false;
        }

        if (pokemon.getVolatileStatus(Data.get().getStatusCondition("suppressed_ability")) != null &&
            condition != AbilityActivation.Removed) {
            return false;
        }

        return true;
    }

    public boolean shouldActivate(Move move, AbilityActivation condition) {
        if (move == null) {
            return shouldActivate(condition);
        }

        if (!active) {
            return false;
        }

        if (exclusiveUser != null && !pokemon.compare(exclusiveUser, true)) {
            return false;
        }

        if (condition != null &&
            !Arrays.asList(getConditions()).contains(condition)) {
            return false;
        }

        if (move.targetsOpponent() && move.getUser() != pokemon && ignorable) {
            if (move.hasInherentProperty(InherentProperty.IgnoresAbility)) {
                return false;
            }
            if (move.getUser().getAbility().shouldActivate(AbilityActivation.IgnoreAbility) &&
                (boolean) move.getUser().getAbility().activate(move.getUser(), pokemon, move, null, null, null, null, 0, AbilityActivation.IgnoreAbility)) {
                return false;
            }
        }

        if (pokemon.getVolatileStatus(Data.get().getStatusCondition("suppressed_ability")) != null &&
            condition != AbilityActivation.Removed) {
            return false;
        }

        return true;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public boolean compare(Ability other) {
        return this.name.equals(other.name);
    }

    public boolean compare(AbilityTemplate template) {
        return this.name.equals(template.getName());
    }
}