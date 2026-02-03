package data.classes;

import java.util.Arrays;

import data.activationConditions.AbilityActivation;
import data.activationConditions.FieldActivation;
import data.activationConditions.MoveEffectActivation;
import main.Battle;

public class Type {
    private String name;
    private Type[] superEffective;
    private Type[] notVeryEffective;
    private Type[] ineffective;
    private Object[] additionalImmunities;

    private Object source;

    private boolean suppressed;

    public Type( // default
        String name
    ) {
        this.name = name;
    }

    public Type( // copy object
        Type original, Object source
    ) {
        this.name = original.name;
        this.superEffective = original.superEffective;
        this.notVeryEffective = original.notVeryEffective;
        this.ineffective = original.ineffective;
        this.additionalImmunities = original.additionalImmunities;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public Type[] getSuperEffective(Move move, boolean trueEffectiveness) {
        Pokemon pokemon = null;
        if (source instanceof Pokemon) {
            pokemon = (Pokemon) source;
        }

        Type typeCopy = new Type(this, source);

        if (!trueEffectiveness) {
            // mudança geral

            if (move != null &&
                move.getUser().getAbility().shouldActivate(AbilityActivation.CallOpponentSuperEffective)) {
                typeCopy.setSuperEffective((Type[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.CallOpponentSuperEffective));
            }

            if (pokemon.getAbility().shouldActivate(move, AbilityActivation.CallUserSuperEffective)) {
                typeCopy.setSuperEffective((Type[]) pokemon.getAbility().activate(pokemon, null, move, typeCopy, null, null, null, 0, AbilityActivation.CallUserSuperEffective));
            }


            // mudança específica

            if (move != null) {
                if (move.getPrimaryEffect() != null &&
                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallSuperEffective)) {
                    typeCopy.setSuperEffective((Type[]) move.activatePrimaryEffect(move.getUser(), pokemon, typeCopy, null, 0, true, MoveEffectActivation.CallSuperEffective));
                }

                if (move.getUser().getAbility().shouldActivate(AbilityActivation.ChangeOpponentSuperEffective)) {
                    typeCopy.setSuperEffective((Type[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.ChangeOpponentSuperEffective));
                }

                if (Battle.getWeather().shouldActivate(FieldActivation.CallSuperEffective)) {
                    typeCopy.setSuperEffective((Type[]) Battle.getWeather().activate(move.getUser(), pokemon, move, typeCopy, null, null, 0, false, true, FieldActivation.CallSuperEffective));
                }
            }
        }

        return typeCopy.superEffective;
    }

    public void setSuperEffective(Type[] superEffective) {
        this.superEffective = superEffective;
    }

    public Type[] getNotVeryEffective(Move move, boolean trueEffectiveness) {
        Pokemon pokemon = null;
        if (source instanceof Pokemon) {
            pokemon = (Pokemon) source;
        }

        Type typeCopy = new Type(this, source);

        if (!trueEffectiveness) {
            // mudança geral

            if (move != null &&
                move.getUser().getAbility().shouldActivate(AbilityActivation.CallOpponentNotVeryEffective)) {
                typeCopy.setNotVeryEffective((Type[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.CallOpponentNotVeryEffective));
            }

            if (pokemon.getAbility().shouldActivate(move, AbilityActivation.CallUserNotVeryEffective)) {
                typeCopy.setNotVeryEffective((Type[]) pokemon.getAbility().activate(pokemon, null, move, typeCopy, null, null, null, 0, AbilityActivation.CallUserNotVeryEffective));
            }


            // mudança específica

            if (move != null) {
                if (move.getPrimaryEffect() != null &&
                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallNotVeryEffective)) {
                    typeCopy.setNotVeryEffective((Type[]) move.activatePrimaryEffect(move.getUser(), pokemon, typeCopy, null, 0, true, MoveEffectActivation.CallNotVeryEffective));
                }

                if (move.getUser().getAbility().shouldActivate(AbilityActivation.ChangeOpponentNotVeryEffective)) {
                    typeCopy.setNotVeryEffective((Type[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.ChangeOpponentNotVeryEffective));
                }
            }
        }

        return typeCopy.notVeryEffective;
    }

    public void setNotVeryEffective(Type[] notVeryEffective) {
        this.notVeryEffective = notVeryEffective;
    }

    public Type[] getIneffective(Move move, boolean trueEffectiveness) {
        Pokemon pokemon = null;
        if (source instanceof Pokemon) {
            pokemon = (Pokemon) source;
        }

        Type typeCopy = new Type(this, source);

        if (!trueEffectiveness) {
            // mudança geral

            if (move != null &&
                move.getUser().getAbility().shouldActivate(AbilityActivation.CallOpponentIneffective)) {
                typeCopy.setIneffective((Type[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.CallOpponentIneffective));
            }

            if (pokemon.getAbility().shouldActivate(move, AbilityActivation.CallUserIneffective)) {
                typeCopy.setIneffective((Type[]) pokemon.getAbility().activate(pokemon, null, move, typeCopy, null, null, null, 0, AbilityActivation.CallUserIneffective));
            }


            // mudança específica

            if (move != null) {
                if (move.getPrimaryEffect() != null &&
                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallIneffective)) {
                    typeCopy.setIneffective((Type[]) move.activatePrimaryEffect(move.getUser(), pokemon, typeCopy, null, 0, true, MoveEffectActivation.CallIneffective));
                }

                if (move.getUser().getAbility().shouldActivate(AbilityActivation.ChangeOpponentIneffective)) {
                    typeCopy.setIneffective((Type[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.ChangeOpponentIneffective));
                }
            }
        }

        return typeCopy.ineffective;
    }

    public void setIneffective(Type[] ineffective) {
        this.ineffective = ineffective;
    }

    public Object[] getAdditionalImmunities() {
        return additionalImmunities;
    }

    public void setAdditionalImmunities(Object[] additionalImmunities) {
        this.additionalImmunities = additionalImmunities;
    }

    public boolean isSuppressed() {
        return suppressed;
    }

    public void setSuppressed(boolean suppressed) {
        this.suppressed = suppressed;
    }

    public boolean compare(Type other) {
        return this.name.equals(other.getName());
    }
}