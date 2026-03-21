package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.effects.AbilityEffect;
import com.github.lucasaugustoss.loader.dtos.AbilityEffectDTO;

public class AbilityTemplate extends Template {
    private String name;
    private AbilityEffectDTO[] effectDTOs;
    private AbilityEffect[] effects;
    private boolean notTransferable;
    private boolean notReplaceable;
    private boolean notSuppressable;
    private boolean ignorable;
    private String exclusiveUserDTO;
    private PokemonTemplate exclusiveUser;
    private Message messages;

    public AbilityTemplate(
        int index, String id,
        String name,
        AbilityEffectDTO[] effectDTOs,
        boolean notTransferable, boolean notReplaceable,
        boolean notSuppressable, boolean ignorable,
        String exclusiveUserDTO,
        Message messages
    ) {
        super(index, id);
        this.name = name;
        this.effectDTOs = effectDTOs;
        this.notTransferable = notTransferable;
        this.notReplaceable = notReplaceable;
        this.notSuppressable = notSuppressable;
        this.ignorable = ignorable;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public AbilityEffectDTO[] getEffectDTOs() {
        return effectDTOs;
    }

    public AbilityEffect[] getEffects() {
        return effects;
    }

    public boolean isNotTransferable() {
        return notTransferable;
    }

    public boolean isNotReplaceable() {
        return notReplaceable;
    }

    public boolean isNotSuppressable() {
        return notSuppressable;
    }

    public boolean isIgnorable() {
        return ignorable;
    }

    public String getExclusiveUserDTO() {
        return exclusiveUserDTO;
    }

    public PokemonTemplate getExclusiveUser() {
        return exclusiveUser;
    }

    public Message getMessages() {
        return messages;
    }



    public void setEffects(AbilityEffect[] effects) {
        this.effects = effects;
    }

    public void setExclusiveUser(PokemonTemplate exclusiveUser) {
        this.exclusiveUser = exclusiveUser;
    }

    public boolean compare(AbilityTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(Ability ability) {
        return this.name.equals(ability.getName());
    }
}
