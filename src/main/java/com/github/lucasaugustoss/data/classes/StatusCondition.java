package com.github.lucasaugustoss.data.classes;

import java.util.Arrays;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.effects.StatusConditionEffect;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.oldObjects.StatusConditionList;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class StatusCondition {
    private String name;
    private boolean volatileCondition;
    private Move causingMove;
    private int counter;
    private Pokemon causer;
    private Move affectedMove;
    private StatusConditionEffect effect;
    private StatusActivation[] activation;
    private boolean stackable;

    private Message messages;

    // initial declaration
    public StatusCondition( // default
        String name, boolean volatileCondition,
        StatusConditionEffect effect, StatusActivation[] activation,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.effect = effect;
        this.activation = activation;
        this.messages = messages;
    }
    public StatusCondition( // with stackable
        String name, boolean volatileCondition,
        StatusConditionEffect effect, StatusActivation[] activation,
        boolean stackable,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.effect = effect;
        this.activation = activation;
        this.stackable = stackable;
        this.messages = messages;
    }

    // added causing move
    public StatusCondition( // default
        String name, boolean volatileCondition, Move causingMove,
        StatusConditionEffect effect, StatusActivation[] activation,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.causingMove = causingMove;
        this.effect = effect;
        this.activation = activation;
        this.messages = messages;
    }
    public StatusCondition( // with counter
        String name, boolean volatileCondition, Move causingMove,
        int counter,
        StatusConditionEffect effect, StatusActivation[] activation,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.causingMove = causingMove;
        this.counter = counter;
        this.effect = effect;
        this.activation = activation;
        this.messages = messages;
    }
    public StatusCondition( // with causer
        String name, boolean volatileCondition, Move causingMove,
        Pokemon causer,
        StatusConditionEffect effect, StatusActivation[] activation,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.causingMove = causingMove;
        this.causer = causer;
        this.effect = effect;
        this.activation = activation;
        this.messages = messages;
    }
    public StatusCondition( // with counter and causer
        String name, boolean volatileCondition, Move causingMove,
        int counter, Pokemon causer,
        StatusConditionEffect effect, StatusActivation[] activation,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.causingMove = causingMove;
        this.counter = counter;
        this.causer = causer;
        this.effect = effect;
        this.activation = activation;
        this.messages = messages;
    }
    public StatusCondition( // with counter, causer, and stackable
        String name, boolean volatileCondition, Move causingMove,
        int counter, Pokemon causer,
        StatusConditionEffect effect, StatusActivation[] activation,
        boolean stackable,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.causingMove = causingMove;
        this.counter = counter;
        this.causer = causer;
        this.effect = effect;
        this.activation = activation;
        this.stackable = stackable;
        this.messages = messages;
    }
    public StatusCondition( // with counter and affected move
        String name, boolean volatileCondition, Move causingMove,
        int counter, Move affectedMove,
        StatusConditionEffect effect, StatusActivation[] activation,
        Message messages
    ) {
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.causingMove = causingMove;
        this.counter = counter;
        this.affectedMove = affectedMove;
        this.effect = effect;
        this.activation = activation;
        this.messages = messages;
    }

    // copy
    public StatusCondition( // copy object
        StatusCondition original
    ) {
        this.name = original.name;
        this.volatileCondition = original.volatileCondition;
        this.causingMove = original.causingMove;
        this.counter = original.counter;
        this.causer = original.causer;
        this.affectedMove = original.affectedMove;
        this.effect = original.effect;
        this.activation = original.activation;
        this.stackable = original.stackable;
        this.messages = original.messages;
    }



    public String getName() {
        return name;
    }

    public boolean isVolatileCondition() {
        return volatileCondition;
    }

    public Move getCausingMove() {
        return causingMove;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Pokemon getCauser() {
        return causer;
    }

    public Move getAffectedMove() {
        return affectedMove;
    }

    public void setAffectedMove(Move affectedMove) {
        this.affectedMove = affectedMove;
    }

    public StatusConditionEffect getEffect() {
        return effect;
    }

    public Object activate(Pokemon pokemon, Pokemon opponent, Move move, Damage damage, boolean showMessages, StatusActivation activation) {
        if (App.battleStarted) {
            if (effect != null) {
                return effect.activate(this, pokemon, opponent, move, damage, showMessages, activation);
            }
        }
        return null;
    }

    public StatusActivation[] getActivation() {
        return activation;
    }

    public boolean isStackable() {
        return stackable;
    }



    public StatusCondition cause(Move causingMove) { // default
        return new StatusCondition(this.name, this.volatileCondition, causingMove, this.effect, this.activation, this.messages);
    }

    public StatusCondition cause(Move causingMove, int counter) { // with counter
        return new StatusCondition(this.name, this.volatileCondition, causingMove, counter, this.effect, this.activation, this.messages);
    }

    public StatusCondition cause(Move causingMove, Pokemon causer) { // with causer
        return new StatusCondition(this.name, this.volatileCondition, causingMove, causer, this.effect, this.activation, this.messages);
    }

    public StatusCondition cause(Move causingMove, int counter, Pokemon causer) { // with counter and causer
        return new StatusCondition(this.name, this.volatileCondition, causingMove, counter, causer, this.effect, this.activation, this.messages);
    }

    public StatusCondition cause(Move causingMove, int counter, Move affectedMove) { // with counter and affected move
        return new StatusCondition(this.name, this.volatileCondition, causingMove, counter, affectedMove, this.effect, this.activation, this.messages);
    }

    public boolean compare(StatusCondition other) {
        return this.name.equals(other.name);
    }



    public boolean immune(Pokemon target) {
        for (Type type : target.getTypes()) {
            if (!type.isSuppressed()) {
                for (Object immunity : type.getAdditionalImmunities()) {
                    if (immunity instanceof StatusCondition &&
                        ((StatusCondition) immunity).compare(this)) {
                        return true;
                    }
                }
            }
        }
        if (target.getAbility().shouldActivate(causingMove, AbilityActivation.TryStatusConditionOnUser) &&
            (boolean) target.getAbility().activate(target, null, causingMove, null, null, this, null, 0, AbilityActivation.TryStatusConditionOnUser)) {
            return true;
        }

        return false;
    }

    public boolean targetProtected(Pokemon target, boolean showMessages) {
        if (Battle.getTerrain().shouldActivate(FieldActivation.TryStatus) &&
            (boolean) Battle.getTerrain().activate(target, null, null, null, this, null, 0, false, showMessages, FieldActivation.TryStatus)) {
            return true;
        }

        for (FieldCondition condition : Battle.generalField) {
            if (condition.shouldActivate(FieldActivation.TryStatus) &&
                (boolean) condition.activate(target, null, null, null, this, null, 0, false, showMessages, FieldActivation.TryStatus)) {
                return true;
            }
        }

        for (FieldCondition condition : Battle.teamFields.get(target.getTeam())) {
            if (target != causer &&
                condition.shouldActivate(FieldActivation.TryStatus) &&
                (boolean) condition.activate(target, null, null, null, this, null, 0, false, showMessages, FieldActivation.TryStatus)) {
                return true;
            }
        }

        return false;
    }

    public boolean apply(Pokemon target, Object cause, boolean showMessages) { // default
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    target.setNonVolatileStatus(cause(causingMove));
                    if (showMessages) {
                        if (messages != null) {
                            if (cause instanceof Ability) {
                                messages.print("start by ability", target, (Ability) cause);
                            } else if (cause instanceof Item) {
                                messages.print("start by item", target, (Item) cause);
                            } else {
                                messages.print("start", target);
                            }
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (Arrays.asList(activation).contains(StatusActivation.Start)) {
                            activate(target, causer, causingMove, null, true, StatusActivation.Start);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null &&
                        (
                            target.getNonVolatileStatus().compare(this) ||
                            compare(StatusConditionList.poison) && target.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                            compare(StatusConditionList.bad_poison) && target.getNonVolatileStatus().compare(StatusConditionList.poison)
                        )) {
                        messages.print("repeat", target);
                    }
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    target.addVolatileStatus(cause(causingMove));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", target);
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (Arrays.asList(activation).contains(StatusActivation.Start)) {
                            activate(target, causer, causingMove, null, true, StatusActivation.Start);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null) {
                        messages.print("repeat", target);
                    }
                }
            }
            return true;
        }
    }

    public boolean apply(Pokemon target, Object cause, boolean showMessages, boolean zPowered) { // Z-Powered
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    target.setNonVolatileStatus(cause(causingMove));
                    if (showMessages) {
                        if (messages != null) {
                            if (cause instanceof Ability) {
                                messages.print("start by ability", target, (Ability) cause);
                            } else if (cause instanceof Item) {
                                messages.print("start by item", target, (Item) cause);
                            } else if (cause instanceof Move && zPowered) {
                                messages.print("start Z", target);
                            } else {
                                messages.print("start", target);
                            }
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (Arrays.asList(activation).contains(StatusActivation.Start)) {
                            activate(target, causer, causingMove, null, true, StatusActivation.Start);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null &&
                        (
                            target.getNonVolatileStatus().compare(this) ||
                            compare(StatusConditionList.poison) && target.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                            compare(StatusConditionList.bad_poison) && target.getNonVolatileStatus().compare(StatusConditionList.poison)
                        )) {
                        messages.print("repeat", target);
                    }
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    target.addVolatileStatus(cause(causingMove));
                    if (showMessages) {
                        if (messages != null) {
                            if (cause instanceof Move && zPowered) {
                                messages.print("start Z", target);
                            } else {
                                messages.print("start", target);
                            }
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (Arrays.asList(activation).contains(StatusActivation.Start)) {
                            activate(target, causer, causingMove, null, true, StatusActivation.Start);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null) {
                        messages.print("repeat", target);
                    }
                }
            }
            return true;
        }
    }

    public boolean apply(Pokemon target, Object cause, int counter, boolean showMessages) { // with counter
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    target.setNonVolatileStatus(cause(causingMove, counter));
                    if (showMessages) {
                        if (messages != null) {
                            if (cause instanceof Ability) {
                                messages.print("start by ability", target, (Ability) cause);
                            } else if (cause instanceof Item) {
                                messages.print("start by item", target, (Item) cause);
                            } else {
                                messages.print("start", target);
                            }
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null &&
                        (
                            target.getNonVolatileStatus().compare(this) ||
                            compare(StatusConditionList.poison) && target.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                            compare(StatusConditionList.bad_poison) && target.getNonVolatileStatus().compare(StatusConditionList.poison)
                        )) {
                        messages.print("repeat", target);
                    }
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    target.addVolatileStatus(cause(causingMove, counter));
                    if (showMessages) {
                        if (messages != null) {
                            if (cause instanceof Ability) {
                                messages.print("start by ability", target, (Ability) cause);
                            } else {
                                messages.print("start", target, counter);
                            }
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (Arrays.asList(activation).contains(StatusActivation.Start)) {
                            activate(target, causer, causingMove, null, true, StatusActivation.Start);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null) {
                        messages.print("repeat", target);
                    }
                }
            }
            return true;
        }
    }

    public boolean apply(Pokemon target, Object cause, Pokemon causer, boolean showMessages) { // with causer
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    target.setNonVolatileStatus(cause(causingMove, causer));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", target, causer);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null &&
                        (
                            target.getNonVolatileStatus().compare(this) ||
                            compare(StatusConditionList.poison) && target.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                            compare(StatusConditionList.bad_poison) && target.getNonVolatileStatus().compare(StatusConditionList.poison)
                        )) {
                        messages.print("repeat", target);
                    }
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    target.addVolatileStatus(cause(causingMove, causer));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", target, causer);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null) {
                        messages.print("repeat", target);
                    }
                }
            }
            return true;
        }
    }

    public boolean apply(Pokemon target, Object cause, int counter, Pokemon causer, boolean showMessages) { // with counter and causer
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    target.setNonVolatileStatus(cause(causingMove, counter, causer));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", target, causer, counter);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null &&
                        (
                            target.getNonVolatileStatus().compare(this) ||
                            compare(StatusConditionList.poison) && target.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                            compare(StatusConditionList.bad_poison) && target.getNonVolatileStatus().compare(StatusConditionList.poison)
                        )) {
                        messages.print("repeat", target);
                    }
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    target.addVolatileStatus(cause(causingMove, counter, causer));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", target, causer, counter);
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null) {
                        messages.print("repeat", target);
                    }
                }
            }
            return true;
        }
    }

    public boolean apply(Pokemon target, Object cause, int counter, Move affectedMove, boolean showMessages) { // with counter and affected move
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    target.setNonVolatileStatus(cause(causingMove, counter, affectedMove));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", affectedMove);
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null &&
                        (
                            target.getNonVolatileStatus().compare(this) ||
                            compare(StatusConditionList.poison) && target.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                            compare(StatusConditionList.bad_poison) && target.getNonVolatileStatus().compare(StatusConditionList.poison)
                        )) {
                        messages.print("repeat", target);
                    }
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    target.addVolatileStatus(cause(causingMove, counter, affectedMove));
                    if (showMessages) {
                        if (messages != null) {
                            messages.print("start", affectedMove);
                        }

                        Pokemon causer = null;
                        if (cause instanceof Move) {
                            causer = ((Move) cause).getUser();
                        } else if (cause instanceof Ability) {
                            causer = ((Ability) cause).getPokemon();
                        }

                        if (causer != null && causer != target) {
                            if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                                causer.getAbility().activate(causer, target, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnTarget);
                            }

                            if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                                target.getAbility().activate(target, causer, causingMove, null, null, this, null, 0, AbilityActivation.StatusConditionOnUser);
                            }
                        }
                    }
                } else {
                    if (showMessages && messages != null) {
                        messages.print("repeat", target);
                    }
                }
            }
            return true;
        }
    }

    public void end(Pokemon target, boolean showMessages) {
        if (!volatileCondition) {
            target.setNonVolatileStatus(StatusConditionList.none.cause(null, target));
            if (messages != null && showMessages) {
                messages.print("end", target);
            }
        } else {
            target.removeVolatileStatus(this, causer);
            if (messages != null && showMessages) {
                messages.print("end", target);
            }
        }
    }
}