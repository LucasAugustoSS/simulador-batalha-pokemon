package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.StatusConditionEffect;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class StatusCondition {
    private String name;
    private boolean volatileCondition;
    private StatusConditionTemplate similarCondition;
    private Move causingMove;
    private int counter;
    private Pokemon causer;
    private Move affectedMove;
    private StatusConditionEffect[] effects;
    private boolean stackable;

    private Message messages;

    public StatusCondition ( // create
        StatusConditionTemplate template,
        Move causingMove, int counter, Pokemon causer, Move affectedMove
    ) {
        this.name = template.getName();
        this.volatileCondition = template.isVolatileCondition();
        this.similarCondition = template.getSimilarCondition();
        this.causingMove = causingMove;
        this.counter = counter;
        this.causer = causer;
        this.affectedMove = affectedMove;
        this.effects = template.getEffects();
        this.stackable = template.isStackable();
        this.messages = template.getMessages();
    }

    public StatusCondition ( // copy
        StatusCondition original,
        Move causingMove, int counter, Pokemon causer, Move affectedMove
    ) {
        this.name = original.name;
        this.volatileCondition = original.volatileCondition;
        this.causingMove = causingMove;
        this.counter = counter;
        this.causer = causer;
        this.affectedMove = affectedMove;
        this.effects = original.effects;
        this.stackable = original.stackable;
        this.messages = original.messages;
    }



    public String getName() {
        return name;
    }

    public boolean isVolatileCondition() {
        return volatileCondition;
    }

    public StatusConditionTemplate getSimilarCondition() {
        return similarCondition;
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

    public StatusConditionEffect[] getEffects() {
        return effects;
    }

    public Object activate(Pokemon pokemon, Pokemon opponent, Move move, Damage damage, boolean showMessages, StatusActivation activation) {
        if (App.battleStarted) {
            for (StatusConditionEffect effect : effects) {
                if (effect.shouldActivate(activation)) {
                    return effect.activate(this, pokemon, opponent, move, damage, showMessages, activation);
                }
            }
        }
        return null;
    }

    public StatusActivation[] getActivation() {
        if (effects == null) {
            return new StatusActivation[0];
        }

        ArrayList<StatusActivation> conditions = new ArrayList<>();

        for (StatusConditionEffect effect : effects) {
            for (StatusActivation condition : effect.getActivation()) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        return conditions.toArray(new StatusActivation[0]);
    }

    public boolean isStackable() {
        return stackable;
    }

    public Message getMessages() {
        return messages;
    }



    public boolean compare(StatusCondition other) {
        return this.name.equals(other.name);
    }

    public boolean compare(StatusConditionTemplate template) {
        return this.name.equals(template.getName());
    }



    public boolean immune(Pokemon target) {
        for (Type type : target.getTypes()) {
            if (!type.isSuppressed()) {
                for (Object immunity : type.getAdditionalImmunities()) {
                    if (immunity instanceof StatusConditionTemplate &&
                        ((StatusConditionTemplate) immunity).compare(this)) {
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
        if (Battle.getWeather().shouldActivate(FieldActivation.TryStatus) &&
            (boolean) Battle.getWeather().activate(target, null, null, null, this, null, 0, false, showMessages, FieldActivation.TryStatus)) {
            return true;
        }

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



    public StatusCondition cause(Move causingMove, Map<String, Object> params) {
        int counter = 0;
        Pokemon causer = null;
        Move affectedMove = null;


        if (params != null) {
            if (params.containsKey("Counter")) {
                counter = (Integer) params.get("Counter");
            }

            if (params.containsKey("Causer")) {
                causer = (Pokemon) params.get("Causer");
            }

            if (params.containsKey("Affected Move")) {
                affectedMove = (Move) params.get("Affected Move");
            }
        }

        return new StatusCondition(this, causingMove, counter, causer, affectedMove);
    }

    public boolean apply(
        Pokemon target, Object cause,
        Map<String, Object> params,
        boolean showMessages, boolean zPowered
    ) {
        if (Battle.faintCheck(target, false)) {
            return true;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        int counter = 0;
        Pokemon causer = null;
        Move affectedMove = null;

        if (cause instanceof Move) {
            causer = ((Move) cause).getUser();
        } else if (cause instanceof Ability) {
            causer = ((Ability) cause).getPokemon();
        }

        if (params != null) {
            params = new HashMap<>(params);

            if (params.containsKey("Counter")) {
                counter = (Integer) params.get("Counter");
            }
    
            if (params.containsKey("Causer")) {
                causer = (Pokemon) params.get("Causer");
            } else {
                params.put("Causer", causer);
            }
    
            if (params.containsKey("Affected Move")) {
                affectedMove = (Move) params.get("Affected Move");
            }
        }

        if (immune(target)) {
            return false;
        } else if (targetProtected(target, showMessages)) {
            return true;
        } else {
            boolean hasCondition = false;
            StatusCondition copiedCondition = null;

            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(Data.get().getStatusCondition("none"))) {
                    copiedCondition = cause(causingMove, params);
                    target.setNonVolatileStatus(copiedCondition);
                } else {
                    hasCondition = true;
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    copiedCondition = cause(causingMove, params);
                    target.addVolatileStatus(copiedCondition);
                } else {
                    hasCondition = true;
                }
            }

            if (!hasCondition) {
                if (showMessages) {
                    if (messages != null) {
                        Map<String, String> names = new HashMap<>();
                        names.put("Pokemon", target.getName(true, false));
                        names.put("Number", String.valueOf(counter));
                        names.put("Causer", causer != null ? causer.getName(true, false) : "");
                        names.put("Move", affectedMove != null ? affectedMove.getName() : "");

                        String key = "start";

                        if (cause instanceof Ability) {
                            names.put("Ability", ((Ability) cause).getName());
                            key = "start by ability";
                        } else if (cause instanceof Item) {
                            names.put("Item", ((Item) cause).getName());
                            key = "start by item";
                        } else if (cause instanceof Move && zPowered) {
                            key = "start Z";
                        }

                        messages.print(key, names);
                    }

                    if (Arrays.asList(copiedCondition.getActivation()).contains(StatusActivation.Start)) {
                        copiedCondition.activate(target, causer, causingMove, null, true, StatusActivation.Start);
                    }

                    if (causer != null && causer != target) {
                        if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                            causer.getAbility().activate(causer, target, causingMove, null, null, copiedCondition, null, 0, AbilityActivation.StatusConditionOnTarget);
                        }

                        if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                            target.getAbility().activate(target, causer, causingMove, null, null, copiedCondition, null, 0, AbilityActivation.StatusConditionOnUser);
                        }
                    }
                }
            } else {
                boolean hasThisCondition;
                if (!volatileCondition) {
                    hasThisCondition = target.getNonVolatileStatus().compare(this) || target.getNonVolatileStatus().compare(similarCondition);
                } else {
                    hasThisCondition = target.getVolatileStatus(this) != null || target.getVolatileStatus(similarCondition) != null;
                }

                if (showMessages && messages != null && hasThisCondition) {
                    messages.print("repeat", Map.of(
                        "Pokemon", target.getName(true, false)
                    ));
                }
            }

            return true;
        }
    }

    public void end(Pokemon target, boolean showMessages) {
        if (!volatileCondition) {
            target.setNonVolatileStatus(Data.get().getStatusCondition("none").cause(null, null));
        } else {
            target.removeVolatileStatus(this, causer);
        }

        if (messages != null && showMessages) {
            messages.print("end", Map.of(
                "Pokemon", target.getName(true, false),
                "Move", causingMove != null ? causingMove.getName() : ""
            ));
        }
    }
}