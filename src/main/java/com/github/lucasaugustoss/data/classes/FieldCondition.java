package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.FieldConditionEffect;
import com.github.lucasaugustoss.data.objects.templates.FieldConditionTemplate;
import com.github.lucasaugustoss.data.properties.fieldConditions.*;
import com.github.lucasaugustoss.simulator.Battle;

public class FieldCondition {
    private String name;
    private FieldConditionType type;
    private int timer;
    private int counter;
    private Object cause;
    private Pokemon causer;
    private FieldConditionEffect[] effects;

    private boolean activatedThisTurn;

    private Message messages;

    private Map<String, Integer> defaultParams;

    public FieldCondition( // create
        FieldConditionTemplate template,
        int timer, int counter, Object cause, Pokemon causer
    ) {
        this.name = template.getName();
        this.type = template.getType();
        this.effects = template.getEffects();
        this.messages = template.getMessages();
        this.defaultParams = template.getDefaultParams();
        this.timer = timer;
        this.counter = counter;
        this.cause = cause;
        this.causer = causer;
        this.activatedThisTurn = true;
    }

    public FieldCondition( // copy
        FieldCondition original,
        int timer, int counter, Object cause, Pokemon causer
    ) {
        this.name = original.name;
        this.type = original.type;
        this.effects = original.effects;
        this.messages = original.messages;
        this.defaultParams = original.defaultParams;
        this.timer = timer;
        this.counter = counter;
        this.cause = cause;
        this.causer = causer;
        this.activatedThisTurn = true;
    }



    public String getName() {
        return name;
    }

    public FieldConditionType getType() {
        return type;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        if (!this.name.equals("Clear")) {
            this.timer = timer;
        }
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void countDown() { // weather/terrain/general field
        if (timer == -1) {
            return;
        }

        if (activatedThisTurn && timer > 0) {
            return;
        }

        if (timer > 0) {
            for (Pokemon pokemon : Battle.orderPokemon(Battle.yourActivePokemon, Battle.opponentActivePokemon)) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.TryFieldCountDown) &&
                    !(boolean) pokemon.getAbility().activate(pokemon, null, null, null, null, null, null, 0, AbilityActivation.TryFieldCountDown)) {
                    return;
                }
            }
        }

        if (!compare(Data.get().getFieldCondition("placeholder"))) {
            timer--;
            if (timer <= 0) {
                if (messages != null && messages.hasMessage("end")) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                end();

                if (messages != null && messages.hasMessage("end")) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
        }
    }

    public void countDown(ArrayList<FieldCondition> field) { // team field
        if (timer == -1) {
            return;
        }

        if (activatedThisTurn && timer > 0) {
            return;
        }

        if (timer > 0) {
            for (Pokemon pokemon : Battle.orderPokemon(Battle.yourActivePokemon, Battle.opponentActivePokemon)) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.TryFieldCountDown) &&
                    !(boolean) pokemon.getAbility().activate(pokemon, null, null, null, null, null, null, 0, AbilityActivation.TryFieldCountDown)) {
                    return;
                }
            }
        }

        if (!compare(Data.get().getFieldCondition("placeholder"))) {
            timer--;
            if (timer <= 0) {
                if (messages != null && messages.hasMessage("end")) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }

                end(field);

                if (messages != null && messages.hasMessage("end")) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
        }
    }

    public void end() {
        if (messages != null) {
            if (causer != null) {
                messages.print("end", Map.of(
                    "Pokemon", causer.getName(true, false)
                ));
            } else {
                messages.print("end");
            }
        }

        if (type == FieldConditionType.Weather) {
            Battle.setWeather(Data.get().getFieldCondition("clear").cause(null, null, null));
        } else if (type == FieldConditionType.Terrain) {
            Battle.setTerrain(Data.get().getFieldCondition("no_terrain").cause(null, null, null));
        } else {
            Battle.removeGeneralFieldCondition(this);
        }
    }

    public void end(ArrayList<FieldCondition> field) {
        int team = Battle.teamFields.indexOf(field);

        if (messages != null) {
            messages.print("end", Map.of(
                "Team", String.valueOf(team)
            ));
        }

        if (shouldActivate(FieldActivation.BeforeEnd)) {
            if (team == 0) {
                activate(Battle.yourActivePokemon, Battle.opponentActivePokemon, null, null, null, null, 0, false, true, FieldActivation.BeforeEnd);
            } else {
                activate(Battle.opponentActivePokemon, Battle.yourActivePokemon, null, null, null, null, 0, false, true, FieldActivation.BeforeEnd);
            }
        }

        Battle.removeTeamFieldCondition(this, team);
    }

    public Object getCause() {
        return cause;
    }

    public Pokemon getCauser() {
        return causer;
    }

    public FieldConditionEffect[] getEffects() {
        return effects;
    }

    public Object activate(Pokemon pokemon, Pokemon opponent, Move move, Type type, StatusCondition statusCondition, Stat stat, int statChangeStages, boolean criticalHit, boolean showMessages, FieldActivation activation) {
        if (App.battleStarted) {
            for (FieldConditionEffect effect : effects) {
                if (effect.shouldActivate(activation)) {
                    return effect.activate(this, pokemon, opponent, move, type, statusCondition, stat, statChangeStages, criticalHit, showMessages, activation);
                }
            }
        }
        return null;
    }

    public FieldActivation[] getFieldActivation() {
        if (effects == null) {
            return new FieldActivation[0];
        }

        ArrayList<FieldActivation> conditions = new ArrayList<>();

        for (FieldConditionEffect effect : effects) {
            for (FieldActivation condition : effect.getActivation()) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        return conditions.toArray(new FieldActivation[0]);
    }

    public boolean activatedThisTurn() {
        return activatedThisTurn;
    }

    public void setActivatedThisTurn(boolean activatedThisTurn) {
        this.activatedThisTurn = activatedThisTurn;
    }

    public Message getMessages() {
        return messages;
    }

    public Map<String, Integer> getDefaultParams() {
        return defaultParams;
    }



    public boolean shouldActivate(FieldActivation activation) {
        if (getFieldActivation() != null &&
            Arrays.asList(getFieldActivation()).contains(activation)) {
            return true;
        }
        return false;
    }

    public boolean shouldActivate(Pokemon pokemon, FieldActivation activation) {
        if (pokemon == null) {
            return shouldActivate(activation);
        }

        if (getFieldActivation() != null &&
            Arrays.asList(getFieldActivation()).contains(activation) &&
            (type != FieldConditionType.Terrain || pokemon.isGrounded(null))) {
            return true;
        }
        return false;
    }

    public boolean compare(FieldCondition other) {
        return this.name.equals(other.name);
    }

    public boolean compare(FieldConditionTemplate template) {
        return this.name.equals(template.getName());
    }

    public Map<String, Integer> joinedParams(Map<String, Integer> params) {
        Map<String, Integer> newParams = new HashMap<>();

        String[] keys = new String[] {"Counter", "Causer", "Affected Move"};

        for (String key : keys) {
            if (params != null && params.containsKey(key)) {
                newParams.put(key, params.get(key));
            } else if (defaultParams.containsKey(key)) {
                newParams.put(key, defaultParams.get(key));
            }
        }

        return newParams;
    }



    public FieldCondition cause(
        Object cause, Pokemon causer,
        Map<String, Integer> params
    ) {
        int timer = -1;
        int counter = 0;

        if (joinedParams(params).containsKey("Timer")) {
            timer = params.get("Timer");
        }

        if (joinedParams(params).containsKey("Counter")) {
            counter = params.get("Counter");
        }

        return new FieldCondition(this, timer, counter, cause, causer);
    }



    public boolean apply(
        Object cause, boolean test,
        Map<String, Integer> params,
        boolean showMessages
    ) {
        return apply(cause, test, -1, params, showMessages);
    }

    public boolean apply(
        Object cause, boolean test,
        int team,
        Map<String, Integer> params,
        boolean showMessages
    ) {
        Pokemon causer = null;
        if (cause instanceof Ability) {
            causer = ((Ability) cause).getPokemon();
        } else if (cause instanceof Move) {
            causer = ((Move) cause).getUser();
        }

        boolean alreadyActive = false;
        boolean cantOverride = false;

        if (type == FieldConditionType.Weather && Battle.getTrueWeather().compare(this) ||
            type == FieldConditionType.Terrain && Battle.getTerrain().compare(this)) {
            alreadyActive = true;
        } else if (type != FieldConditionType.Weather && type != FieldConditionType.Terrain) {
            for (FieldCondition condition : Battle.generalField) {
                if (condition.compare(this)) {
                    alreadyActive = true;

                    if (condition.shouldActivate(FieldActivation.Repeat)) {
                        return (boolean) condition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Repeat);
                    }
                }
            }
            for (FieldCondition condition : Battle.teamFields.get(team)) {
                if (condition.compare(this)) {
                    alreadyActive = true;

                    if (condition.shouldActivate(FieldActivation.Repeat)) {
                        if (team == 0) {
                            return (boolean) condition.activate(Battle.yourActivePokemon, Battle.opponentActivePokemon, null, null, null, null, 0, false, true, FieldActivation.Repeat);
                        } else {
                            return (boolean) condition.activate(Battle.opponentActivePokemon, Battle.yourActivePokemon, null, null, null, null, 0, false, true, FieldActivation.Repeat);
                        }
                    }
                }
            }
        }

        boolean isPrimalWeather = compare(Data.get().getFieldCondition("desolate_land")) || compare(Data.get().getFieldCondition("primordial_sea")) || compare(Data.get().getFieldCondition("delta_stream"));
        boolean primalWeatherActive = Battle.getTrueWeather().compare(Data.get().getFieldCondition("desolate_land")) || Battle.getTrueWeather().compare(Data.get().getFieldCondition("primordial_sea")) || Battle.getTrueWeather().compare(Data.get().getFieldCondition("delta_stream"));

        if (!isPrimalWeather && primalWeatherActive) {
            if (!test) {
                Battle.getTrueWeather().getMessages().print("fail replace");
            }
            cantOverride = true;
        }

        if (!alreadyActive && !cantOverride) {
            if (!test) {
                if (showMessages && messages != null) {
                    Map<String, String> names = new HashMap<>();
                    names.put("Pokemon", causer.getName(true, false));
                    names.put("Team", String.valueOf(team));

                    String key = "start";

                    if (cause instanceof Ability) {
                        names.put("Ability", ((Ability) cause).getName());
                        key = "start by ability";
                    }

                    messages.print(key, names);
                }

                if (type == FieldConditionType.Weather) {
                    Battle.setWeather(cause(cause, causer, params));
                } else if (type == FieldConditionType.Terrain) {
                    Battle.setTerrain(cause(cause, causer, params));
                } else if (team == -1) {
                    Battle.generalField.add(cause(cause, causer, params));
                } else {
                    Battle.teamFields.get(team).add(cause(cause, causer, params));
                }

                if (shouldActivate(FieldActivation.Start)) {
                    activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Start);
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
