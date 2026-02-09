package com.github.lucasaugustoss.data.classes;

import java.util.Arrays;

import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.simulator.Battle;

public class Type {
    private String name;
    private TypeTemplate[] superEffective;
    private TypeTemplate[] notVeryEffective;
    private TypeTemplate[] ineffective;
    private Object[] additionalImmunities;

    private Object source;

    private boolean suppressed;

    public Type(TypeTemplate template, Object source) { // create
        this.name = template.getName();
        this.superEffective = template.getSuperEffective();
        this.notVeryEffective = template.getNotVeryEffective();
        this.ineffective = template.getIneffective();
        this.additionalImmunities = template.getAdditionalImmunities();
        this.source = source;
    }

    public Type(Type original, Object source) { // copy
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

    public TypeTemplate[] getSuperEffective(Move move, boolean trueEffectiveness) {
        Pokemon pokemon = null;
        if (source instanceof Pokemon) {
            pokemon = (Pokemon) source;
        }

        Type typeCopy = new Type(this, source);

        if (!trueEffectiveness) {
            // mudança geral

            if (move != null &&
                move.getUser().getAbility().shouldActivate(AbilityActivation.CallOpponentSuperEffective)) {
                typeCopy.setSuperEffective((TypeTemplate[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.CallOpponentSuperEffective));
            }

            if (pokemon.getAbility().shouldActivate(move, AbilityActivation.CallUserSuperEffective)) {
                typeCopy.setSuperEffective((TypeTemplate[]) pokemon.getAbility().activate(pokemon, null, move, typeCopy, null, null, null, 0, AbilityActivation.CallUserSuperEffective));
            }


            // mudança específica

            if (move != null) {
                if (move.getPrimaryEffect() != null &&
                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallSuperEffective)) {
                    typeCopy.setSuperEffective((TypeTemplate[]) move.activatePrimaryEffect(move.getUser(), pokemon, typeCopy, null, 0, true, MoveEffectActivation.CallSuperEffective));
                }

                if (move.getUser().getAbility().shouldActivate(AbilityActivation.ChangeOpponentSuperEffective)) {
                    typeCopy.setSuperEffective((TypeTemplate[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.ChangeOpponentSuperEffective));
                }

                if (Battle.getWeather().shouldActivate(FieldActivation.CallSuperEffective)) {
                    typeCopy.setSuperEffective((TypeTemplate[]) Battle.getWeather().activate(move.getUser(), pokemon, move, typeCopy, null, null, 0, false, true, FieldActivation.CallSuperEffective));
                }
            }
        }

        return typeCopy.superEffective;
    }

    public void setSuperEffective(TypeTemplate[] superEffective) {
        this.superEffective = superEffective;
    }

    public TypeTemplate[] getNotVeryEffective(Move move, boolean trueEffectiveness) {
        Pokemon pokemon = null;
        if (source instanceof Pokemon) {
            pokemon = (Pokemon) source;
        }

        Type typeCopy = new Type(this, source);

        if (!trueEffectiveness) {
            // mudança geral

            if (move != null &&
                move.getUser().getAbility().shouldActivate(AbilityActivation.CallOpponentNotVeryEffective)) {
                typeCopy.setNotVeryEffective((TypeTemplate[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.CallOpponentNotVeryEffective));
            }

            if (pokemon.getAbility().shouldActivate(move, AbilityActivation.CallUserNotVeryEffective)) {
                typeCopy.setNotVeryEffective((TypeTemplate[]) pokemon.getAbility().activate(pokemon, null, move, typeCopy, null, null, null, 0, AbilityActivation.CallUserNotVeryEffective));
            }


            // mudança específica

            if (move != null) {
                if (move.getPrimaryEffect() != null &&
                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallNotVeryEffective)) {
                    typeCopy.setNotVeryEffective((TypeTemplate[]) move.activatePrimaryEffect(move.getUser(), pokemon, typeCopy, null, 0, true, MoveEffectActivation.CallNotVeryEffective));
                }

                if (move.getUser().getAbility().shouldActivate(AbilityActivation.ChangeOpponentNotVeryEffective)) {
                    typeCopy.setNotVeryEffective((TypeTemplate[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.ChangeOpponentNotVeryEffective));
                }
            }
        }

        return typeCopy.notVeryEffective;
    }

    public void setNotVeryEffective(TypeTemplate[] notVeryEffective) {
        this.notVeryEffective = notVeryEffective;
    }

    public TypeTemplate[] getIneffective(Move move, boolean trueEffectiveness) {
        Pokemon pokemon = null;
        if (source instanceof Pokemon) {
            pokemon = (Pokemon) source;
        }

        Type typeCopy = new Type(this, source);

        if (!trueEffectiveness) {
            // mudança geral

            if (move != null &&
                move.getUser().getAbility().shouldActivate(AbilityActivation.CallOpponentIneffective)) {
                typeCopy.setIneffective((TypeTemplate[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.CallOpponentIneffective));
            }

            if (pokemon.getAbility().shouldActivate(move, AbilityActivation.CallUserIneffective)) {
                typeCopy.setIneffective((TypeTemplate[]) pokemon.getAbility().activate(pokemon, null, move, typeCopy, null, null, null, 0, AbilityActivation.CallUserIneffective));
            }


            // mudança específica

            if (move != null) {
                if (move.getPrimaryEffect() != null &&
                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.CallIneffective)) {
                    typeCopy.setIneffective((TypeTemplate[]) move.activatePrimaryEffect(move.getUser(), pokemon, typeCopy, null, 0, true, MoveEffectActivation.CallIneffective));
                }

                if (move.getUser().getAbility().shouldActivate(AbilityActivation.ChangeOpponentIneffective)) {
                    typeCopy.setIneffective((TypeTemplate[]) move.getUser().getAbility().activate(move.getUser(), pokemon, move, typeCopy, null, null, null, 0, AbilityActivation.ChangeOpponentIneffective));
                }
            }
        }

        return typeCopy.ineffective;
    }

    public void setIneffective(TypeTemplate[] ineffective) {
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

    public boolean compare(TypeTemplate template) {
        return this.name.equals(template.getName());
    }
}