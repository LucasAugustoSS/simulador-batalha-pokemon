package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;
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

    private boolean hasCounter;
    private boolean activatedThisTurn;

    private Message messages;

    public FieldCondition( // create
        FieldConditionTemplate template,
        int timer, int counter, Object cause, Pokemon causer
    ) {
        this.name = template.getName();
        this.type = template.getType();
        this.effects = template.getEffects();
        this.messages = template.getMessages();
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
            Battle.setWeather(Data.get().getFieldCondition("clear").cause(-1, null, null));
        } else if (type == FieldConditionType.Terrain) {
            Battle.setTerrain(Data.get().getFieldCondition("no_terrain").cause(-1, null, null));
        } else {
            Battle.removeGeneralFieldCondition(this);
        }
    }

    public void end(ArrayList<FieldCondition> field) {
        int team = Battle.teamFields.indexOf(field);

        if (messages != null) {
            messages.print("end", Map.of(
                "Team", team == 0 ? "Your" : "The opposing"
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

    public boolean hasCounter() {
        return hasCounter;
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



    public FieldCondition cause(int counter, Object cause, Pokemon causer) {
        if (hasCounter) {
            return new FieldCondition(this, -1, counter, cause, causer);
        } else {
            return new FieldCondition(this, counter, 0, cause, causer);
        }
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



    public boolean apply(Object cause, boolean test, boolean showMessages) { // general field
        boolean alreadyActive = false;
        boolean cantOverride = false;

        if (type == FieldConditionType.Weather && Battle.getTrueWeather().compare(this) ||
            type == FieldConditionType.Terrain && Battle.getTerrain().compare(this)) {
            alreadyActive = true;
        }
        if (type != FieldConditionType.Weather && type != FieldConditionType.Terrain) {
            for (FieldCondition condition : Battle.generalField) {
                if (condition.compare(this)) {
                    alreadyActive = true;

                    if (condition.shouldActivate(FieldActivation.Repeat)) {
                        return (boolean) condition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Repeat);
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
                if (messages != null && showMessages) {
                    if (cause instanceof Ability) {
                        messages.print("start by ability", Map.of(
                            "Pokemon", ((Ability) cause).getPokemon().getName(true, false),
                            "Ability", ((Ability) cause).getName()
                        ));
                    } else if (cause instanceof Move) {
                        messages.print("start", Map.of(
                            "Pokemon", ((Move) cause).getUser().getName(true, false)
                        ));
                    } else {
                        messages.print("start");
                    }
                }

                Pokemon causer = null;
                if (cause instanceof Ability) {
                    causer = ((Ability) cause).getPokemon();
                } else if (cause instanceof Move) {
                    causer = ((Move) cause).getUser();
                }

                if (type == FieldConditionType.Weather) {
                    int timer = isPrimalWeather ? -1 : 5;
                    Battle.setWeather(cause(timer, cause, causer));
                } else if (type == FieldConditionType.Terrain) {
                    Battle.setTerrain(cause(5, cause, causer));
                } else {
                    Battle.generalField.add(cause(5, cause, causer));
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
    public boolean apply(Object cause, int counter, boolean showMessages) { // general field with counter
        boolean alreadyActive = false;

        for (FieldCondition condition : Battle.generalField) {
            if (condition.compare(this)) {
                alreadyActive = true;

                if (condition.shouldActivate(FieldActivation.Repeat)) {
                    return (boolean) condition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Repeat);
                }
            }
        }

        if (!alreadyActive) {
            if (messages != null && showMessages) {
                if (cause instanceof Ability) {
                    messages.print("start by ability", Map.of(
                        "Pokemon", ((Ability) cause).getPokemon().getName(true, false),
                        "Ability", ((Ability) cause).getName()
                    ));
                } else if (cause instanceof Move) {
                    messages.print("start", Map.of(
                        "Pokemon", ((Move) cause).getUser().getName(true, false)
                    ));
                } else {
                    messages.print("start");
                }
            }

            Pokemon causer = null;
            if (cause instanceof Ability) {
                causer = ((Ability) cause).getPokemon();
            } else if (cause instanceof Move) {
                causer = ((Move) cause).getUser();
            }

            Battle.generalField.add(cause(counter, cause, causer));

            if (shouldActivate(FieldActivation.Start)) {
                activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Start);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean apply(int team, Object cause, boolean showMessages) { // team field
        boolean alreadyActive = false;
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

        if (!alreadyActive) {
            if (messages != null && showMessages) {
                messages.print("start", Map.of(
                    "Team", team == 0 ? "Your" : "The opposing"
                ));
            }

            Pokemon causer = null;
            if (cause instanceof Ability) {
                causer = ((Ability) cause).getPokemon();
            } else if (cause instanceof Move) {
                causer = ((Move) cause).getUser();
            }

            Battle.teamFields.get(team).add(cause(5, cause, causer));

            return true;
        } else {
            return false;
        }
    }
    public boolean apply(int team, int counter, Object cause, boolean showMessages) { // team field with counter
        boolean alreadyActive = false;
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

        if (!alreadyActive) {
            if (messages != null && showMessages) {
                messages.print("start", Map.of(
                    "Team", team == 0 ? "Your" : "The opposing"
                ));
            }

            Pokemon causer = null;
            if (cause instanceof Ability) {
                causer = ((Ability) cause).getPokemon();
            } else if (cause instanceof Move) {
                causer = ((Move) cause).getUser();
            }

            Battle.teamFields.get(team).add(cause(counter, cause, causer));

            return true;
        } else {
            return false;
        }
    }
}
