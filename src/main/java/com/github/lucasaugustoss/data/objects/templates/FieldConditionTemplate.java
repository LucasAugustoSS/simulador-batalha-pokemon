package com.github.lucasaugustoss.data.objects.templates;

import java.util.HashMap;
import java.util.Map;

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
    private Message messages;
    private Map<String, Integer> defaultParams;

    public FieldConditionTemplate(
        int index, String id,
        String name, FieldConditionType type,
        FieldConditionEffectDTO[] effectDTOs,
        Message messages,
        Map<String, Integer> defaultParams
    ) {
        super(index, id);
        this.name = name;
        this.type = type;
        this.effectDTOs = effectDTOs;
        this.messages = messages;
        this.defaultParams = defaultParams;
    }

    public String getName() {
        return name;
    }

    public FieldConditionType getType() {
        return type;
    }

    public FieldConditionEffectDTO[] getEffectDTOs() {
        return effectDTOs;
    }

    public FieldConditionEffect[] getEffects() {
        return effects;
    }

    public Message getMessages() {
        return messages;
    }

    public Map<String, Integer> getDefaultParams() {
        return defaultParams;
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

    public Map<String, Integer> joinedParams(Map<String, Integer> params) {
        Map<String, Integer> newParams = new HashMap<>();

        String[] keys = new String[] {"Timer", "Counter"};

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

        params = joinedParams(params);

        if (params.containsKey("Timer")) {
            timer = params.get("Timer");
        }

        if (params.containsKey("Counter")) {
            counter = params.get("Counter");
        }

        return new FieldCondition(this, timer, counter, cause, causer);
    }



    public boolean[] apply(
        Object cause, boolean test,
        Map<String, Integer> params,
        boolean showMessages
    ) {
        return apply(cause, test, -1, params, showMessages);
    }

    public boolean[] apply( // 0: sucesso; 1: imprimir mensagem de falha
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
                        return new boolean[] {
                            (boolean) condition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Repeat),
                            true
                        };
                    }
                }
            }
            if (team != -1) {
                for (FieldCondition condition : Battle.teamFields.get(team)) {
                    if (condition.compare(this)) {
                        alreadyActive = true;
    
                        if (condition.shouldActivate(FieldActivation.Repeat)) {
                            return new boolean[] {
                                (boolean) condition.activate(Battle.getActivePokemon(team), Battle.getOpposingPokemon(team), null, null, null, null, 0, false, true, FieldActivation.Repeat),
                                true
                            };
                        }
                    }
                }
            }
        }

        boolean isPrimalWeather = compare(Data.get().getFieldCondition("desolate_land")) || compare(Data.get().getFieldCondition("primordial_sea")) || compare(Data.get().getFieldCondition("delta_stream"));
        boolean primalWeatherActive = Battle.getTrueWeather().compare(Data.get().getFieldCondition("desolate_land")) || Battle.getTrueWeather().compare(Data.get().getFieldCondition("primordial_sea")) || Battle.getTrueWeather().compare(Data.get().getFieldCondition("delta_stream"));

        if (!isPrimalWeather && primalWeatherActive) {
            if (showMessages) {
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

                FieldCondition newCondition = cause(cause, causer, params);
                if (type == FieldConditionType.Weather) {
                    Battle.setWeather(newCondition);
                } else if (type == FieldConditionType.Terrain) {
                    Battle.setTerrain(newCondition);
                } else if (team == -1) {
                    Battle.generalField.add(newCondition);
                } else {
                    Battle.teamFields.get(team).add(newCondition);
                }

                if (newCondition != null) {
                    for (FieldConditionEffect effect : newCondition.getEffects()) {
                        if (effect.shouldActivate(FieldActivation.Start)) {
                            newCondition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.Start);
                        }
                    }
                }
            }

            return new boolean[] {true, true};
        } else {
            return new boolean[] {false, !cantOverride};
        }
    }
}
