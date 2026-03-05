package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.FieldConditionEffect;
import com.github.lucasaugustoss.data.properties.fieldConditions.FieldConditionType;
import com.github.lucasaugustoss.loader.dtos.FieldConditionEffectDTO;
import com.github.lucasaugustoss.simulator.Battle;

public class FieldConditionTemplate extends Template {
    private String name;
    private FieldConditionType type;
    private FieldConditionEffectDTO[] effectDTOs;
    private FieldConditionEffect[] effects;
    private boolean hasCounter;
    private Message messages;

    public FieldConditionTemplate(
        int index, String id,
        String name, FieldConditionType type,
        FieldConditionEffectDTO[] effectDTOs,
        boolean hasCounter,
        Message messages
    ) {
        super(index, id);
        this.name = name;
        this.type = type;
        this.effectDTOs = effectDTOs;
        this.hasCounter = hasCounter;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public FieldConditionType getType() {
        return type;
    }

    public FieldConditionEffectDTO[] getEffectDTO() {
        return effectDTOs;
    }

    public FieldConditionEffect[] getEffects() {
        return effects;
    }

    public boolean hasCounter() {
        return hasCounter;
    }

    public Message getMessages() {
        return messages;
    }



    public void setEffects(FieldConditionEffect[] effects) {
        this.effects = effects;
    }

    public boolean compare(FieldConditionTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(FieldCondition condition) {
        return this.name.equals(condition.getName());
    }



    public FieldCondition cause(int counter, Object cause, Pokemon causer) {
        if (hasCounter) {
            return new FieldCondition(this, -1, counter, cause, causer);
        } else {
            return new FieldCondition(this, counter, 0, cause, causer);
        }
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
                        messages.print("start by ability", (Ability) cause);
                    } else if (cause instanceof Move) {
                        messages.print("start", ((Move) cause).getUser());
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

                FieldCondition newCondition = null;
                if (type == FieldConditionType.Weather) {
                    int timer = isPrimalWeather ? -1 : 5;
                    newCondition = cause(timer, cause, causer);
                    Battle.setWeather(newCondition);
                } else if (type == FieldConditionType.Terrain) {
                    newCondition = cause(5, cause, causer);
                    Battle.setTerrain(newCondition);
                } else {
                    newCondition = cause(5, cause, causer);
                    Battle.generalField.add(newCondition);
                }

                if (newCondition != null) {
                    for (FieldConditionEffect effect : newCondition.getEffects()) {
                        if (effect.shouldActivate(FieldActivation.Start)) {
                            newCondition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Start);
                        }
                    }
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
                    messages.print("start by ability", (Ability) cause);
                } else if (cause instanceof Move) {
                    messages.print("start", ((Move) cause).getUser());
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

            FieldCondition newCondition = cause(counter, cause, causer);
            Battle.generalField.add(newCondition);

            if (newCondition != null) {
                for (FieldConditionEffect effect : newCondition.getEffects()) {
                    if (effect.shouldActivate(FieldActivation.Start)) {
                        newCondition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Start);
                    }
                }
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
                messages.print("start", team);
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
                messages.print("start", team);
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
