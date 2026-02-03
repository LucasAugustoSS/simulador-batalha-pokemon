package data.classes;

import main.App;
import main.Damage;
import data.activationConditions.AbilityActivation;
import data.classes.effects.AbilityEffect;
import data.objects.StatusConditionList;
import data.properties.moves.InherentProperty;

import java.util.Arrays;

public class Ability {
    private String name;
    private AbilityEffect effect;
    private AbilityActivation[] conditions;
    private boolean notTransferable;
    private boolean notReplaceable;
    private boolean notSuppressable;
    private boolean ignorable;

    private boolean active;
    private boolean persistentEffectActive;
    private int counter;

    private Pokemon pokemon;

    public Ability( // normal
        String name, AbilityEffect effect, AbilityActivation[] conditions,
        boolean notTransferable, boolean notReplaceable, boolean notSuppressable
    ) {
        this.name = name;
        this.effect = effect;
        this.conditions = conditions;
        this.notTransferable = notTransferable;
        this.notReplaceable = notReplaceable;
        this.notSuppressable = notSuppressable;
    }
    public Ability( // ignorable
        String name, AbilityEffect effect, AbilityActivation[] conditions,
        boolean notTransferable, boolean notReplaceable, boolean notSuppressable, boolean ignorable
    ) {
        this.name = name;
        this.effect = effect;
        this.conditions = conditions;
        this.notTransferable = notTransferable;
        this.notReplaceable = notReplaceable;
        this.notSuppressable = notSuppressable;
        this.ignorable = ignorable;
    }

    public Ability( // copy object
        Ability original, boolean active, Pokemon pokemon
    ) {
        this.name = original.name;
        this.effect = original.effect;
        this.conditions = original.conditions;
        this.notTransferable = original.notTransferable;
        this.notReplaceable = original.notReplaceable;
        this.notSuppressable = original.notSuppressable;
        this.ignorable = original.ignorable;
        this.active = active;
        this.pokemon = pokemon;
    }



    public String getName() {
        return name;
    }

    public AbilityEffect getAbilityEffect() {
        return effect;
    }

    public AbilityActivation[] getConditions() {
        return conditions;
    }

    public Object activate(Pokemon self, Pokemon opponent, Move move, Type type, Damage damage, StatusCondition statusCondition, Stat stat, int statChangeStages, AbilityActivation condition) {
        if (App.battleStarted) {
            return effect.activate(this, self, opponent, move, type, damage, statusCondition, stat, statChangeStages, condition);
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

        if (condition != null &&
            !Arrays.asList(conditions).contains(condition)) {
            return false;
        }

        if (pokemon.getVolatileStatus(StatusConditionList.suppressed_ability) != null &&
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

        if (condition != null &&
            !Arrays.asList(conditions).contains(condition)) {
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

        if (pokemon.getVolatileStatus(StatusConditionList.suppressed_ability) != null &&
            condition != AbilityActivation.Removed) {
            return false;
        }

        return true;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public boolean compare(Ability ability) {
        return this.name.equals(ability.name);
    }
}