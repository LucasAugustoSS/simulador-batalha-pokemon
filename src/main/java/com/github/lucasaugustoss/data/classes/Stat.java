package com.github.lucasaugustoss.data.classes;

import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.templates.StatTemplate;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.data.properties.stats.StatType;
import com.github.lucasaugustoss.simulator.Battle;

public class Stat {
    private StatTemplate template;

    private String name;
    private StatName nameShort;
    private StatType type;
    private Pokemon pokemon;
    private int value;
    private int stages;

    public Stat(StatTemplate template, Pokemon pokemon, int value) { // create
        this.template = template;
        this.name = template.getName();
        this.nameShort = template.getNameShort();
        this.type = template.getType();
        this.pokemon = pokemon;
        this.value = value;
        this.stages = 0;
    }

    public Stat(Stat original, Pokemon pokemon, int value, int stages) { // copy
        this.template = original.template;
        this.name = original.name;
        this.nameShort = original.nameShort;
        this.type = original.type;
        this.pokemon = pokemon;
        this.value = value;
        this.stages = stages;
    }



    public StatTemplate getTemplate() {
        return template;
    }

    public String getName() {
        return name;
    }

    public StatName getNameShort() {
        return nameShort;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public int getValue() {
        return value;
    }

    public int getEffectiveValue(Pokemon opponent, Move move, boolean criticalHit, StatType treatedAs) {
        int effectiveValue = value;

        if (opponent.getAbility().shouldActivate(move, AbilityActivation.AnyStatCalc)) {
            effectiveValue *= (double) opponent.getAbility().activate(opponent, pokemon, move, null, null, 0, null, this, 0, true, AbilityActivation.AnyStatCalc);
        }

        int stages = getStages(opponent, move);

        if (treatedAs == null) {
            treatedAs = type;
        }

        if (treatedAs == StatType.Offensive) {
            if (stages >= 0 || !criticalHit) {
                double val = 1 + Math.abs(stages)*0.5;
                effectiveValue = (int) (stages >= 0 ? effectiveValue*val : effectiveValue/val);
            }

            if (move.getCategory() == Category.Physical) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.AttackCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, null, 0, null, this, 0, true, AbilityActivation.AttackCalc));
                }
                if (opponent.getAbility().shouldActivate(move, AbilityActivation.OpponentAttackCalc)) {
                    effectiveValue *= ((double) opponent.getAbility().activate(opponent, pokemon, move, null, null, 0, null, this, 0, true, AbilityActivation.OpponentAttackCalc));
                }

                if (pokemon.getItem().shouldActivate(ItemActivation.AttackCalc)) {
                    effectiveValue *= ((double) pokemon.getItem().activate(pokemon, pokemon, opponent, move, null, true, ItemActivation.AttackCalc));
                }
            } else if (move.getCategory() == Category.Special) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.SpecialAttackCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, null, 0, null, this, 0, true, AbilityActivation.SpecialAttackCalc));
                }
                if (opponent.getAbility().shouldActivate(move, AbilityActivation.OpponentSpecialAttackCalc)) {
                    effectiveValue *= ((double) opponent.getAbility().activate(opponent, pokemon, move, null, null, 0, null, this, 0, true, AbilityActivation.OpponentSpecialAttackCalc));
                }

                if (pokemon.getItem().shouldActivate(ItemActivation.SpecialAttackCalc)) {
                    effectiveValue *= ((double) pokemon.getItem().activate(pokemon, pokemon, opponent, move, null, true, ItemActivation.SpecialAttackCalc));
                }
            }
        } else if (treatedAs == StatType.Defensive) {
            if (!move.hasInherentProperty(InherentProperty.IgnoresDefensiveAndEvasionStages) &&
                (stages <= 0 || !criticalHit)) {
                double val = 1 + Math.abs(stages)*0.5;
                effectiveValue = (int) (stages >= 0 ? effectiveValue*val : effectiveValue/val);
            }

            if (move.getCategory() == Category.Physical) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.DefenseCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, null, 0, null, this, 0, true, AbilityActivation.DefenseCalc));
                }

                if (Battle.getWeather(move).shouldActivate(FieldActivation.DefenseCalc)) {
                    effectiveValue *= (double) Battle.getWeather(move).activate(pokemon, opponent, move, null, null, null, 0, false, true, FieldActivation.DefenseCalc);
                }
            } else if (move.getCategory() == Category.Special) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.SpecialDefenseCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, null, 0, null, this, 0, true, AbilityActivation.SpecialDefenseCalc));
                }

                if (Battle.getWeather(move).shouldActivate(FieldActivation.SpecialDefenseCalc)) {
                    effectiveValue *= (double) Battle.getWeather(move).activate(pokemon, opponent, move, null, null, null, 0, false, true, FieldActivation.SpecialDefenseCalc);
                }
            }
        } else if (treatedAs == StatType.Speed) {
            double val = 1 + Math.abs(stages)*0.5;
            effectiveValue = (int) (stages >= 0 ? effectiveValue*val : effectiveValue/val);

            if (pokemon.getNonVolatileStatus().compare(Data.get().getStatusCondition("paralysis"))) {
                effectiveValue *= 0.5;
            }
            if (pokemon.getAbility().shouldActivate(AbilityActivation.SpeedCalc)) {
                effectiveValue = (int) (effectiveValue*((double) pokemon.getAbility().activate(pokemon, null, move, null, null, 0, null, this, 0, true, AbilityActivation.SpeedCalc)));
            }

            if (pokemon.getItem().shouldActivate(ItemActivation.SpeedCalc)) {
                effectiveValue *= ((double) pokemon.getItem().activate(pokemon, pokemon, opponent, move, null, true, ItemActivation.SpeedCalc));
            }

            for (FieldCondition condition : Battle.teamFields.get(pokemon.getTeam())) {
                if (condition.shouldActivate(FieldActivation.SpeedCalc)) {
                    effectiveValue = (int) (effectiveValue*((double) condition.activate(pokemon, null, null, null, null, null, 0, false, true, FieldActivation.SpeedCalc)));
                }
            }
        }

        return effectiveValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTrueStages() {
        return stages;
    }

    public int getStages(Pokemon opponent, Move move) {
        if (opponent != null &&
            pokemon != opponent) {
            if (opponent.getAbility().shouldActivate(move, AbilityActivation.CallOpponentStatStages)) {
                return (int) opponent.getAbility().activate(opponent, pokemon, move, null, null, 0, null, this, 0, true, AbilityActivation.CallOpponentStatStages);
            }
        }
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public void changeStages(int stages) {
        if (this.stages + stages > 6) {
            this.stages = 6;
        } else if (this.stages + stages < -6) {
            this.stages = -6;
        } else {
            this.stages += stages;
        }
    }

    public boolean change(int newStages, Object cause, Pokemon causer, boolean showMessages, boolean zPowered) {
        if (Battle.faintCheck(pokemon, null, false)) {
            return false;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        Ability causingAbility = null;
        if (cause instanceof Ability) {
            causingAbility = (Ability) cause;
        }

        Item causingItem = null;
        if (cause instanceof Item) {
            causingItem = (Item) cause;
        }

        if (causer != pokemon) {
            if (pokemon.getAbility().shouldActivate(causingMove, AbilityActivation.TryStatChangeOnUser) &&
                (boolean) pokemon.getAbility().activate(pokemon, null, null, null, null, 0, null, this, newStages, true, AbilityActivation.TryStatChangeOnUser)) {
                return false;
            }

            for (FieldCondition condition : Battle.teamFields.get(pokemon.getTeam())) {
                if (condition.shouldActivate(FieldActivation.TryStatChange) &&
                    (boolean) condition.activate(pokemon, null, null, null, null, this, newStages, false, true, FieldActivation.TryStatChange)) {
                    return false;
                }
            }
        }


        if (!zPowered) {
            if (pokemon.getAbility().shouldActivate(causingMove, AbilityActivation.ModifyStatChangeStages)) {
                newStages = (int) pokemon.getAbility().activate(pokemon, null, null, null, null, 0, null, this, newStages, true, AbilityActivation.ModifyStatChangeStages);
            }
        }

        if (newStages != 0) {
            if (stages >= 6 || stages <= -6) {
                if (showMessages && !zPowered) {
                    String key = newStages > 0 ? "inc limit" : "dec limit";
                    MessageHandler.add("stat_change", key, Map.of(
                        "Pokemon", pokemon.getName(true, false),
                        "Stat", name
                    ));
                }
                return false;
            } else {
                changeStages(newStages);
                if (showMessages && !pokemon.isDummy()) {
                    String key = (newStages > 0 ? "+" : "-") + (Math.abs(newStages) > 3 ? 3 : Math.abs(newStages));

                    if (cause instanceof Ability) {
                        key += " ability";

                        if (causer == pokemon) {
                            key += " own";
                        } else {
                            key += " other";
                        }
                    } else if (cause instanceof Item) {
                        key += " item";
                    } else if (zPowered) {
                        key += " Z";
                    }

                    MessageHandler.add("stat_change", key, Map.of(
                        "Pokemon", pokemon.getName(true, false),
                        "Causer", causer != null ? causer.getName(true, false) : "",
                        "Ability", causingAbility != null ? causingAbility.getName() : "",
                        "Item", causingItem != null ? causingItem.getName() : "",
                        "Stat", name
                    ));
                }
            }
        }

        if (causer != pokemon && !pokemon.isDummy()) {
            if (pokemon.getAbility().shouldActivate(causingMove, AbilityActivation.StatChangeOnUser)) {
                pokemon.getAbility().activate(pokemon, null, null, null, null, 0, null, this, newStages, true, AbilityActivation.StatChangeOnUser);
            }
        }

        return true;
    }


    public boolean compare(Stat other) {
        return this.nameShort == other.nameShort;
    }

    public boolean compare(StatTemplate template) {
        return this.nameShort == template.getNameShort();
    }
}
