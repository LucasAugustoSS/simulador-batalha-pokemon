package data.classes;

import main.Battle;

import data.activationConditions.AbilityActivation;
import data.activationConditions.FieldActivation;
import data.messages.list.GeneralMessages;
import data.objects.StatusConditionList;
import data.properties.moves.InherentProperty;
import data.properties.stats.StatName;
import data.properties.stats.StatType;

public class Stat {
    private String name;
    private StatName nameShort;
    private StatType type;
    private Pokemon pokemon;
    private int value;
    private int stages;

    public Stat( // default
        String name, StatName nameShort, StatType type
    ) {
        this.name = name;
        this.nameShort = nameShort;
        this.type = type;
    }

    public Stat( // copy object
        Stat original, Pokemon pokemon, int value
    ) {
        this.name = original.name;
        this.nameShort = original.nameShort;
        this.type = original.type;
        this.pokemon = pokemon;
        this.value = value;
        this.stages = 0;
    }

    public static final Stat atk = new Stat("Attack", StatName.Atk, StatType.Offensive);
    public static final Stat def = new Stat("Defense", StatName.Def, StatType.Defensive);
    public static final Stat spa = new Stat("Special Attack", StatName.SpA, StatType.Offensive);
    public static final Stat spd = new Stat("Special Defense", StatName.SpD, StatType.Defensive);
    public static final Stat spe = new Stat("Speed", StatName.Spe, StatType.Speed);
    public static final Stat acc = new Stat("Accuracy", StatName.Acc, StatType.Evasive);
    public static final Stat eva = new Stat("Evasion", StatName.Eva, StatType.Evasive);

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

        for (FieldCondition condition : Battle.generalField) {
            if (condition.shouldActivate(FieldActivation.CallStatValue)) {
                effectiveValue = (int) condition.activate(pokemon, opponent, move, null, null, this, 0, false, true, FieldActivation.CallStatValue);
            }
        }

        if (opponent.getAbility().shouldActivate(move, AbilityActivation.AnyStatCalc)) {
            effectiveValue *= (double) opponent.getAbility().activate(opponent, pokemon, move, null, 0, null, this, 0, AbilityActivation.AnyStatCalc);
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

            if (compare(atk)) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.AttackCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, 0, null, this, 0, AbilityActivation.AttackCalc));
                }
                if (opponent.getAbility().shouldActivate(move, AbilityActivation.OpponentAttackCalc)) {
                    effectiveValue *= ((double) opponent.getAbility().activate(opponent, pokemon, move, null, 0, null, this, 0, AbilityActivation.OpponentAttackCalc));
                }
            } else if (compare(spa)) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.SpecialAttackCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, 0, null, this, 0, AbilityActivation.SpecialAttackCalc));
                }
                if (opponent.getAbility().shouldActivate(move, AbilityActivation.OpponentSpecialAttackCalc)) {
                    effectiveValue *= ((double) opponent.getAbility().activate(opponent, pokemon, move, null, 0, null, this, 0, AbilityActivation.OpponentSpecialAttackCalc));
                }
            }
        } else if (treatedAs == StatType.Defensive) {
            if (!move.hasInherentProperty(InherentProperty.IgnoresDefensiveAndEvasionStages) &&
                (stages <= 0 || !criticalHit)) {
                double val = 1 + Math.abs(stages)*0.5;
                effectiveValue = (int) (stages >= 0 ? effectiveValue*val : effectiveValue/val);
            }

            if (compare(def)) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.DefenseCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, 0, null, this, 0, AbilityActivation.DefenseCalc));
                }
                if (Battle.getWeather().shouldActivate(FieldActivation.DefenseCalc)) {
                    effectiveValue *= (double) Battle.getWeather().activate(pokemon, opponent, move, null, null, null, 0, false, true, FieldActivation.DefenseCalc);
                }
            } else if (compare(spd)) {
                if (pokemon.getAbility().shouldActivate(AbilityActivation.SpecialDefenseCalc)) {
                    effectiveValue *= ((double) pokemon.getAbility().activate(pokemon, opponent, move, null, 0, null, this, 0, AbilityActivation.SpecialDefenseCalc));
                }
            }
        } else if (treatedAs == StatType.Speed) {
            double val = 1 + Math.abs(stages)*0.5;
            effectiveValue = (int) (stages >= 0 ? effectiveValue*val : effectiveValue/val);

            if (pokemon.getNonVolatileStatus().compare(StatusConditionList.paralysis)) {
                effectiveValue *= 0.5;
            }
            if (pokemon.getAbility().shouldActivate(AbilityActivation.SpeedCalc)) {
                effectiveValue = (int) (effectiveValue*((double) pokemon.getAbility().activate(pokemon, null, move, null, 0, null, this, 0, AbilityActivation.SpeedCalc)));
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
                return (int) opponent.getAbility().activate(opponent, pokemon, move, null, 0, null, this, 0, AbilityActivation.CallOpponentStatStages);
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

    public Stat initialize(Pokemon pokemon, int value) {
        return new Stat(this, pokemon, value);
    }

    public boolean change(int newStages, Object cause, boolean selfInflicted, boolean showMessages, boolean zPowered) {
        if (Battle.faintCheck(pokemon, false)) {
            return false;
        }

        Move causingMove = null;
        if (cause instanceof Move) {
            causingMove = (Move) cause;
        }

        if (!selfInflicted) {
            if (pokemon.getAbility().shouldActivate(causingMove, AbilityActivation.TryStatChangeOnUser) &&
                (boolean) pokemon.getAbility().activate(pokemon, null, null, null, 0, null, this, newStages, AbilityActivation.TryStatChangeOnUser)) {
                return false;
            }

            for (FieldCondition condition : Battle.teamFields.get(pokemon.getTeam())) {
                if (condition.shouldActivate(FieldActivation.TryStatChange) &&
                    (boolean) condition.activate(pokemon, null, null, null, null, this, newStages, false, false, FieldActivation.TryStatChange)) {
                    return false;
                }
            }
        }


        if (pokemon.getAbility().shouldActivate(causingMove, AbilityActivation.ModifyStatChangeStages)) {
            newStages = (int) pokemon.getAbility().activate(pokemon, null, null, null, 0, null, this, newStages, AbilityActivation.ModifyStatChangeStages);
        }

        if (newStages > 0) {
            if (stages >= 6) {
                if (showMessages) {
                    if (!zPowered) {
                        GeneralMessages.stat_change.print("inc limit", pokemon, this);
                    }
                }
                return false;
            } else {
                changeStages(newStages);
                if (showMessages) {
                    String key = "+" + (newStages > 3 ? 3 : newStages);

                    if (cause instanceof Ability) {
                        key += " ability";

                        if (((Ability) cause).getPokemon() == pokemon) {
                            key += " own";
                        } else {
                            key += " other";
                        }

                        GeneralMessages.stat_change.print(key, pokemon, (Ability) cause, this);
                    } else {
                        if (zPowered) {
                            key += " Z";
                        }

                        GeneralMessages.stat_change.print(key, pokemon, this);
                    }
                }
            }
        }
        if (newStages < 0) {
            if (stages <= -6) {
                if (showMessages) {
                    GeneralMessages.stat_change.print("dec limit", pokemon, this);
                }
                return false;
            } else {
                changeStages(newStages);
                if (showMessages) {
                    String key = Integer.toString(newStages < -3 ? -3 : newStages);

                    if (cause instanceof Ability) {
                        key += " ability";

                        if (((Ability) cause).getPokemon() == pokemon) {
                            key += " own";
                        } else {
                            key += " other";
                        }

                        GeneralMessages.stat_change.print(key, pokemon, (Ability) cause, this);
                    } else {
                        GeneralMessages.stat_change.print(key, pokemon, this);
                    }
                }
            }
        }


        if (!selfInflicted) {
            if (pokemon.getAbility().shouldActivate(causingMove, AbilityActivation.StatChangeOnUser)) {
                pokemon.getAbility().activate(pokemon, null, null, null, 0, null, this, newStages, AbilityActivation.StatChangeOnUser);
            }
        }

        return true;
    }


    public boolean compare(Stat other) {
        return this.nameShort == other.nameShort;
    }
}
