package com.github.lucasaugustoss.data.objects.templates;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.StatusConditionEffect;
import com.github.lucasaugustoss.loader.dtos.StatusConditionEffectDTO;
import com.github.lucasaugustoss.simulator.Battle;

public class StatusConditionTemplate extends Template {
    private String name;
    private boolean volatileCondition;
    private String similarConditionID;
    private StatusConditionTemplate similarCondition;
    private StatusConditionEffectDTO[] effectDTOs;
    private StatusConditionEffect[] effects;
    private boolean stackable;
    private Message messages;

    public StatusConditionTemplate(
        int index, String id,
        String name, boolean volatileCondition, String similarConditionID,
        StatusConditionEffectDTO[] effectDTOs,
        boolean stackable,
        Message messages
    ) {
        super(index, id);
        this.name = name;
        this.volatileCondition = volatileCondition;
        this.similarConditionID = similarConditionID;
        this.effectDTOs = effectDTOs;
        this.stackable = stackable;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public boolean isVolatileCondition() {
        return volatileCondition;
    }

    public String getSimilarConditionID() {
        return similarConditionID;
    }

    public StatusConditionTemplate getSimilarCondition() {
        return similarCondition;
    }

    public StatusConditionEffectDTO[] getEffectDTOs() {
        return effectDTOs;
    }

    public StatusConditionEffect[] getEffects() {
        return effects;
    }

    public boolean isStackable() {
        return stackable;
    }

    public Message getMessages() {
        return messages;
    }



    public void setSimilarCondition(StatusConditionTemplate similarCondition) {
        this.similarCondition = similarCondition;
    }

    public void setEffects(StatusConditionEffect[] effects) {
        this.effects = effects;
    }

    public boolean compare(StatusConditionTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(StatusCondition condition) {
        return this.name.equals(condition.getName());
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
        if (target.getAbility().shouldActivate(AbilityActivation.TryStatusConditionOnUser) &&
            (boolean) target.getAbility().activate(target, null, null, null, null, new StatusCondition(this, null, 0, null, null), null, 0, AbilityActivation.TryStatusConditionOnUser)) {
            return true;
        }

        return false;
    }

    public boolean targetProtected(Pokemon target, boolean showMessages) {
        if (Battle.getWeather().shouldActivate(FieldActivation.TryStatus) &&
            (boolean) Battle.getWeather().activate(target, null, null, null, new StatusCondition(this, null, 0, null, null), null, 0, false, showMessages, FieldActivation.TryStatus)) {
            return true;
        }

        if (Battle.getTerrain().shouldActivate(FieldActivation.TryStatus) &&
            (boolean) Battle.getTerrain().activate(target, null, null, null, new StatusCondition(this, null, 0, null, null), null, 0, false, showMessages, FieldActivation.TryStatus)) {
            return true;
        }

        for (FieldCondition condition : Battle.generalField) {
            if (condition.shouldActivate(FieldActivation.TryStatus) &&
                (boolean) condition.activate(target, null, null, null, new StatusCondition(this, null, 0, null, null), null, 0, false, showMessages, FieldActivation.TryStatus)) {
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
            StatusCondition createdCondition = null;

            if (!volatileCondition) {
                if (target.getNonVolatileStatus().compare(Data.get().getStatusCondition("none"))) {
                    createdCondition = cause(causingMove, params);
                    target.setNonVolatileStatus(createdCondition);
                } else {
                    hasCondition = true;
                }
            } else {
                if (target.getVolatileStatus(this) == null || stackable) {
                    createdCondition = cause(causingMove, params);
                    target.addVolatileStatus(createdCondition);
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

                    if (Arrays.asList(createdCondition.getActivation()).contains(StatusActivation.Start)) {
                        createdCondition.activate(target, causer, causingMove, null, true, StatusActivation.Start);
                    }

                    if (causer != null && causer != target) {
                        if (causer.getAbility().shouldActivate(AbilityActivation.StatusConditionOnTarget)) {
                            causer.getAbility().activate(causer, target, causingMove, null, null, createdCondition, null, 0, AbilityActivation.StatusConditionOnTarget);
                        }

                        if (target.getAbility().shouldActivate(causingMove, AbilityActivation.StatusConditionOnUser)) {
                            target.getAbility().activate(target, causer, causingMove, null, null, createdCondition, null, 0, AbilityActivation.StatusConditionOnUser);
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
}
