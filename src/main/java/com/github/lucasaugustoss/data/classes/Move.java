package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.classes.effects.MoveEffect;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.oldObjects.AbilityList;
import com.github.lucasaugustoss.data.objects.oldObjects.MoveList;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.data.properties.moves.*;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class Move {
    private String name;
    private boolean zMove;
    private boolean signatureZMove;
    private Move moveOrigin;
    private Type type;
    private Category category;
    private int PP;
    private int currentPP;
    private double power;
    private double zMovePower;
    private boolean zPowered;
    private int accuracy;
    private int critRatio;
    private boolean contact;
    private int priority;
    private int[] hits;
    private MoveTarget moveTarget;

    private MoveEffect primaryEffect;
    private EffectTarget primaryEffectTarget;
    private int primaryEffectCounter;
    private MoveEffect secondaryEffect;
    private EffectTarget secondaryEffectTarget;
    private MoveEffectActivation[] effectConditions;

    private MoveEffect zEffect;
    private EffectTarget zEffectTarget;
    private MoveEffectActivation[] zEffectConditions;

    private Pokemon user;

    private MoveType[] moveTypes;

    private boolean used;
    private int consecutiveUses;

    private InherentProperty[] inherentProperties;
    private ArrayList<TemporaryProperty> temporaryProperties;

    // damaging moves

    public Move( // default
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with Z-Move power
        String name,
        TypeTemplate type, Category category, int PP, int power, int zMovePower, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.zMovePower = zMovePower;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // multi-hit
        String name,
        TypeTemplate type, Category category, int PP, int power, int zMovePower, int accuracy,
        int critRatio, boolean contact, int priority, int[] hits, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.zMovePower = zMovePower;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = hits;
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with primary effect counter
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget, int primaryEffectCounter,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.primaryEffectCounter = primaryEffectCounter;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with move type
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveType[] moveTypes
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with move type and Z-Move power
        String name,
        TypeTemplate type, Category category, int PP, int power, int zMovePower, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveType[] moveTypes
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.zMovePower = zMovePower;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with move type (multi-hit)
        String name,
        TypeTemplate type, Category category, int PP, int power, int zMovePower, int accuracy,
        int critRatio, boolean contact, int priority, int[] hits, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveType[] moveTypes
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.zMovePower = zMovePower;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = hits;
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with inherent properties
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = inherentProperties;
    }
    public Move( // with inherent properties and Z-Move power
        String name,
        TypeTemplate type, Category category, int PP, int power, int zMovePower, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.zMovePower = zMovePower;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = inherentProperties;
    }
    public Move( // with move type and inherent properties
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveType[] moveTypes, InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = inherentProperties;
    }
    public Move( // with primary effect counter and move type
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget, int primaryEffectCounter,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveType[] moveTypes
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.primaryEffectCounter = primaryEffectCounter;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with primary effect counter, move type, and inherent properties
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, boolean contact, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget, int primaryEffectCounter,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveType[] moveTypes, InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.primaryEffectCounter = primaryEffectCounter;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = inherentProperties;
    }


    // status moves

    public Move( // default
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.effectConditions = effectConditions;
        this.zEffect = zEffect;
        this.zEffectTarget = zEffectTarget;
        this.zEffectConditions = zEffectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with primary effect counter
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget, int primaryEffectCounter,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.primaryEffectCounter = primaryEffectCounter;
        this.effectConditions = effectConditions;
        this.zEffect = zEffect;
        this.zEffectTarget = zEffectTarget;
        this.zEffectConditions = zEffectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with move type
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions,
        MoveType[] moveTypes
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.effectConditions = effectConditions;
        this.zEffect = zEffect;
        this.zEffectTarget = zEffectTarget;
        this.zEffectConditions = zEffectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with inherent properties
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions,
        InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.effectConditions = effectConditions;
        this.zEffect = zEffect;
        this.zEffectTarget = zEffectTarget;
        this.zEffectConditions = zEffectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = inherentProperties;
    }
    public Move( // with move type and inherent properties
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions,
        MoveType[] moveTypes, InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.effectConditions = effectConditions;
        this.zEffect = zEffect;
        this.zEffectTarget = zEffectTarget;
        this.zEffectConditions = zEffectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = inherentProperties;
    }
    public Move( // with primary effect counter and move type
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget, int primaryEffectCounter,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions,
        MoveType[] moveTypes
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.primaryEffectCounter = primaryEffectCounter;
        this.effectConditions = effectConditions;
        this.zEffect = zEffect;
        this.zEffectTarget = zEffectTarget;
        this.zEffectConditions = zEffectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // with primary effect counter, move type, and inherent properties
        String name,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget, int primaryEffectCounter,
        MoveEffectActivation[] effectConditions,
        MoveEffect zEffect, EffectTarget zEffectTarget,
        MoveEffectActivation[] zEffectConditions,
        MoveType[] moveTypes, InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.primaryEffectCounter = primaryEffectCounter;
        this.effectConditions = effectConditions;
        this.moveTypes = moveTypes;
        this.inherentProperties = inherentProperties;
    }


    // Z-Moves

    public Move( // type Z-Move
        String name, boolean zMove, boolean signatureZMove,
        TypeTemplate type, int PP, int power, int accuracy,
        int critRatio, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions
    ) {
        this.name = name;
        this.zMove = zMove;
        this.signatureZMove = signatureZMove;
        this.type = new Type(type, this);
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // signature Z-Move
        String name, boolean zMove, boolean signatureZMove,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions) {
        this.name = name;
        this.zMove = zMove;
        this.signatureZMove = signatureZMove;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = new InherentProperty[0];
    }
    public Move( // signature Z-Move; with inherent properties
        String name, boolean zMove, boolean signatureZMove,
        TypeTemplate type, Category category, int PP, int power, int accuracy,
        int critRatio, int priority, MoveTarget moveTarget,
        MoveEffect primaryEffect, EffectTarget primaryEffectTarget,
        MoveEffect secondaryEffect, EffectTarget secondaryEffectTarget,
        MoveEffectActivation[] effectConditions,
        InherentProperty[] inherentProperties
    ) {
        this.name = name;
        this.zMove = zMove;
        this.signatureZMove = signatureZMove;
        this.type = new Type(type, this);
        this.category = category;
        this.PP = PP;
        this.currentPP = PP;
        this.power = power;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.priority = priority;
        this.hits = new int[] {1};
        this.moveTarget = moveTarget;
        this.primaryEffect = primaryEffect;
        this.primaryEffectTarget = primaryEffectTarget;
        this.secondaryEffect = secondaryEffect;
        this.secondaryEffectTarget = secondaryEffectTarget;
        this.effectConditions = effectConditions;
        this.moveTypes = new MoveType[] {MoveType.Regular};
        this.inherentProperties = inherentProperties;
    }


    public Move( // copy object
        Move original, Pokemon user
    ) {
        this.name = original.name;
        this.zMove = original.zMove;
        this.signatureZMove = original.signatureZMove;
        this.type = new Type(original.type, this);
        this.category = original.category;
        this.PP = original.PP;
        this.currentPP = original.currentPP;
        this.power = original.power;
        this.zMovePower = original.zMovePower;
        this.zPowered = false;
        this.accuracy = original.accuracy;
        this.critRatio = original.critRatio;
        this.contact = original.contact;
        this.priority = original.priority;
        this.hits = original.hits;
        this.moveTarget = original.moveTarget;
        this.primaryEffect = original.primaryEffect;
        this.primaryEffectTarget = original.primaryEffectTarget;
        this.primaryEffectCounter = original.primaryEffectCounter;
        this.secondaryEffect = original.secondaryEffect;
        this.secondaryEffectTarget = original.secondaryEffectTarget;
        this.effectConditions = original.effectConditions;
        this.zEffect = original.zEffect;
        this.zEffectTarget = original.zEffectTarget;
        this.zEffectConditions = original.zEffectConditions;
        this.moveTypes = original.moveTypes;
        this.inherentProperties = original.inherentProperties;
        this.temporaryProperties = new ArrayList<>();

        this.used = false;
        this.consecutiveUses = 0;

        this.user = user;
    }
    public Move( // copy object (transformed)
        Move original, Move moveOrigin, Pokemon user
    ) {
        this.name = original.name;
        this.zMove = original.zMove;
        this.signatureZMove = original.signatureZMove;
        this.type = new Type(original.type, this);
        this.power = original.power;
        this.accuracy = original.accuracy;
        this.critRatio = original.critRatio;
        this.priority = original.priority;
        this.hits = original.hits;
        this.moveTarget = original.moveTarget;
        this.primaryEffect = original.primaryEffect;
        this.primaryEffectTarget = original.primaryEffectTarget;
        this.primaryEffectCounter = original.primaryEffectCounter;
        this.secondaryEffect = original.secondaryEffect;
        this.secondaryEffectTarget = original.secondaryEffectTarget;
        this.effectConditions = original.effectConditions;
        this.moveTypes = original.moveTypes;
        this.inherentProperties = original.inherentProperties;
        this.temporaryProperties = new ArrayList<>();

        this.used = false;
        this.consecutiveUses = 0;

        this.moveOrigin = moveOrigin;
        this.user = user;

        if (original.category != null) {
            this.category = original.category;
        } else {
            this.category = moveOrigin.category;
        }
        this.contact = moveOrigin.contact;
        this.PP = moveOrigin.PP;
        this.currentPP = moveOrigin.currentPP;

        if (zMove) {
            this.power = getZMovePower();
        } else {
            this.power = original.power;
        }
    }



    public String getName() {
        Move self = this;
        if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallMoveData)) {
            self = (Move) activatePrimaryEffect(user, user, null, null, 0, true, MoveEffectActivation.CallMoveData);
        }
        if (isZMove() &&
            Arrays.asList(moveOrigin.effectConditions).contains(MoveEffectActivation.CallMoveData)) {
            self = ((Move) moveOrigin.activatePrimaryEffect(user, user, null, null, 0, true, MoveEffectActivation.CallMoveData)).turnZMove();
        }

        return self.getTrueName();
    }

    public String getTrueName() {
        return name;
    }

    public boolean isZMove() {
        return zMove;
    }

    public boolean isSignatureZMove() {
        return signatureZMove;
    }

    public Move getMoveOrigin() {
        return moveOrigin;
    }

    public Move turnZMove() {
        if (category == null) {
            return this;
        }

        if (category == Category.Status) {
            Move newMove = new Move(this, user);
            newMove.setZPowered(true);
            return newMove;
        }

        if (getType(true, false).compare(Data.get().getType("normal"))) {
            return MoveList.breakneck_blitz;
        } else if (getType(true, false).compare(Data.get().getType("fighting"))) {
            return MoveList.all_out_pummeling;
        } else if (getType(true, false).compare(Data.get().getType("flying"))) {
            return MoveList.supersonic_skystrike;
        } else if (getType(true, false).compare(Data.get().getType("poison"))) {
            return MoveList.acid_downpour;
        } else if (getType(true, false).compare(Data.get().getType("ground"))) {
            return MoveList.tectonic_rage;
        } else if (getType(true, false).compare(Data.get().getType("rock"))) {
            return MoveList.continental_crush;
        } else if (getType(true, false).compare(Data.get().getType("bug"))) {
            return MoveList.savage_spin_out;
        } else if (getType(true, false).compare(Data.get().getType("ghost"))) {
            return MoveList.never_ending_nightmare;
        } else if (getType(true, false).compare(Data.get().getType("steel"))) {
            return MoveList.corkscrew_crash;
        } else if (getType(true, false).compare(Data.get().getType("fire"))) {
            return MoveList.inferno_overdrive;
        } else if (getType(true, false).compare(Data.get().getType("water"))) {
            return MoveList.hydro_vortex;
        } else if (getType(true, false).compare(Data.get().getType("grass"))) {
            return MoveList.bloom_doom;
        } else if (getType(true, false).compare(Data.get().getType("electric"))) {
            return MoveList.gigavolt_havoc;
        } else if (getType(true, false).compare(Data.get().getType("psychic"))) {
            return MoveList.shattered_psyche;
        } else if (getType(true, false).compare(Data.get().getType("ice"))) {
            return MoveList.subzero_slammer;
        } else if (getType(true, false).compare(Data.get().getType("dragon"))) {
            return MoveList.devastating_drake;
        } else if (getType(true, false).compare(Data.get().getType("dark"))) {
            return MoveList.black_hole_eclipse;
        } else if (getType(true, false).compare(Data.get().getType("fairy"))) {
            return MoveList.twinkle_tackle;
        } else {
            // failsafe
            return MoveList.breakneck_blitz;
        }
    }

    public Type getTrueType() {
        return type;
    }

    public Type getType(boolean zMoveConversion, boolean ignoreAbility) {
        Type currentType = type;

        if (!zMoveConversion) {
            if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallType)) {
                currentType = new Type((Type) activatePrimaryEffect(user, user, currentType, null, 0, true, MoveEffectActivation.CallType), this);
            }

            if (!ignoreAbility) {
                if (Arrays.asList(user.getAbility().getConditions()).contains(AbilityActivation.CallMoveType)) {
                    currentType = new Type((Type) user.getAbility().activate(user, user, this, currentType, null, null, null, 0, AbilityActivation.CallMoveType), this);
                }
            }
        } else {
            if (Arrays.asList(effectConditions).contains(MoveEffectActivation.ZCallType)) {
                currentType = new Type((Type) activatePrimaryEffect(user, user, currentType, null, 0, true, MoveEffectActivation.ZCallType), this);
            }
        }

        for (FieldCondition condition : Battle.generalField) {
            if (condition.shouldActivate(FieldActivation.CallType)) {
                currentType = new Type((Type) condition.activate(user, user, this, currentType, null, null, 0, false, true, FieldActivation.CallType), this);
            }
        }

        return currentType;
    }

    public Type[] getTypeList() {
        if (primaryEffect != null &&
            Arrays.asList(effectConditions).contains(MoveEffectActivation.EffectivenessCalc)) {
            return (Type[]) activatePrimaryEffect(user, user, null, null, 0, true, MoveEffectActivation.EffectivenessCalc);
        }

        return new Type[] {getType(false, false)};
    }

    public Category getCategory() {
        if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallCategory)) {
            return (Category) activatePrimaryEffect(user, user, null, null, 0, true, MoveEffectActivation.CallCategory);
        }

        return category;
    }

    public Category getTrueCategory() {
        return category;
    }

    public int getPP() {
        return PP;
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public void setPP(int PP) {
        this.PP = PP;
        if (currentPP > PP) {
            this.currentPP = PP;
        }
    }

    public void setCurrentPP(int currentPP) {
        if (moveOrigin != null) {
            moveOrigin.setCurrentPP(currentPP);
        }

        if (currentPP > PP) {
            this.currentPP = PP;
        } else if (currentPP < 0) {
            this.currentPP = 0;
        } else {
            this.currentPP = currentPP;
        }
    }

    public double getPower(boolean truePower, boolean unmodified, int hit) {
        double power = this.power;

        Pokemon opponent;
        if (user == Battle.yourActivePokemon) {
            opponent = Battle.opponentActivePokemon;
        } else {
            opponent = Battle.yourActivePokemon;
        }

        if (!truePower) {
            if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallPower)) {
                power = (double) activatePrimaryEffect(user, opponent, null, null, hit, true, MoveEffectActivation.CallPower);
            }
        }

        if (!unmodified) {
            if (user.getAbility().shouldActivate(AbilityActivation.UserPowerCalc)) {
                power *= (double) user.getAbility().activate(user, opponent, this, null, null, null, null, 0, AbilityActivation.UserPowerCalc);
            }
            if (opponent.getAbility().shouldActivate(this, AbilityActivation.OpponentPowerCalc)) {
                power *= (double) opponent.getAbility().activate(opponent, user, this, null, null, null, null, 0, AbilityActivation.OpponentPowerCalc);
            }
            if (user.getAbility().shouldActivate(AbilityActivation.AnyPowerCalc)) {
                power *= (double) user.getAbility().activate(user, opponent, this, null, null, null, null, 0, AbilityActivation.AnyPowerCalc);
            }
            if (!opponent.getAbility().compare(user.getAbility()) &&
                opponent.getAbility().shouldActivate(this, AbilityActivation.AnyPowerCalc)) {
                power *= (double) opponent.getAbility().activate(opponent, user, this, null, null, null, null, 0, AbilityActivation.AnyPowerCalc);
            }

            if (user.getItem().shouldActivate(ItemActivation.PowerCalc)) {
                power *= (double) user.getItem().activate(user, user, opponent, this, null, ItemActivation.PowerCalc);
            }
        }
        return power;
    }

    public double getZMovePower() {
        if (power > -1) {
            return power;
        }

        if (moveOrigin.zMovePower != 0) {
            return moveOrigin.zMovePower;
        }

        double originPower = moveOrigin.getPower(true, true, 0);

        if (originPower < 60) {
            return 100;
        } else if (originPower < 70) {
            return 120;
        } else if (originPower < 80) {
            return 140;
        } else if (originPower < 90) {
            return 160;
        } else if (originPower < 100) {
            return 175;
        } else if (originPower < 110) {
            return 180;
        } else if (originPower < 120) {
            return 185;
        } else if (originPower < 130) {
            return 190;
        } else if (originPower < 140) {
            return 195;
        } else {
            return 200;
        }
    }

    public boolean isZPowered() {
        return zPowered;
    }

    public void setZPowered(boolean zPowered) {
        this.zPowered = zPowered;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getCritRatio() {
        return critRatio;
    }

    public boolean makesContact(boolean trueContact) {
        if (!trueContact) {
            if (Arrays.asList(user.getAbility().getConditions()).contains(AbilityActivation.CallContact)) {
                return (boolean) user.getAbility().activate(user, user, this, null, null, null, null, 0, AbilityActivation.CallContact);
            }
        }

        return contact;
    }

    public int getPriority() {
        if (user.getAbility().shouldActivate(AbilityActivation.PriorityCalc)) {
            return priority + (int) user.getAbility().activate(user, user, this, null, null, null, null, 0, AbilityActivation.PriorityCalc);
        }

        if (!user.getAbility().compare(AbilityList.prankster) || temporaryProperties.contains(TemporaryProperty.Reflected)) {
            removeProperty(TemporaryProperty.PranksterBoosted);
        }

        return priority;
    }

    public int[] getHits() {
        return hits;
    }

    public MoveTarget getMoveTarget() {
        if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallMoveTarget)) {
            return (MoveTarget) activatePrimaryEffect(user, user, null, null, 0, true, MoveEffectActivation.CallMoveTarget);
        }
        return moveTarget;
    }

    public boolean targetsUser() {
        if (getMoveTarget() == MoveTarget.User ||
            getMoveTarget() == MoveTarget.UserAndAlly ||
            getMoveTarget() == MoveTarget.UserField) {
            return true;
        }
        return false;
    }

    public boolean targetsOpponent() {
        if (getMoveTarget() == MoveTarget.Normal ||
            getMoveTarget() == MoveTarget.AllOpponents ||
            getMoveTarget() == MoveTarget.AllAdjacent ||
            getMoveTarget() == MoveTarget.RandomOpponent ||
            getMoveTarget() == MoveTarget.OpponentField) {
            return true;
        }
        return false;
    }

    public void setMoveTarget(MoveTarget moveTarget) {
        this.moveTarget = moveTarget;
    }

    public MoveEffect getPrimaryEffect() {
        return primaryEffect;
    }

    public Object activatePrimaryEffect(Pokemon user, Pokemon target, Type type, Damage damage, int hit, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted) {
            if (user != target) {
                for (StatusCondition vol : target.getVolatileStatusList()) {
                    if (Arrays.asList(vol.getActivation()).contains(StatusActivation.PrimaryEffectActivation) &&
                        (boolean) vol.activate(target, user, this, null, true, StatusActivation.PrimaryEffectActivation)) {
                        return true;
                    }
                }
            }

            if (primaryEffect != null) {
                return primaryEffect.activate(this, user, target, type, damage, hit, showMessages, condition);
            }
        }
        return null;
    }

    public EffectTarget getPrimaryEffectTarget() {
        if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallEffectTarget)) {
            return (EffectTarget) activatePrimaryEffect(user, user, null, null, 0, true, MoveEffectActivation.CallEffectTarget);
        }
        return primaryEffectTarget;
    }

    public int getPrimaryEffectCounter() {
        return primaryEffectCounter;
    }

    public void setPrimaryEffectCounter(int primaryEffectCounter) {
        this.primaryEffectCounter = primaryEffectCounter;
    }

    public void primaryEffectCountDown() {
        this.primaryEffectCounter -= 1;
    }

    public MoveEffect getSecondaryEffect() {
        return secondaryEffect;
    }

    public Object activateSecondaryEffect(Pokemon user, Pokemon target, Type type, Damage damage, int hit, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted) {
            for (StatusCondition vol : target.getVolatileStatusList()) {
                if (Arrays.asList(vol.getActivation()).contains(StatusActivation.SecondaryEffectActivation) &&
                    (boolean) vol.activate(target, user, this, null, true, StatusActivation.SecondaryEffectActivation)) {
                    return null;
                }
            }

            boolean suppressed = false;

            if (user.getAbility().shouldActivate(AbilityActivation.SecondaryEffectActivation)) {
                suppressed = (boolean) user.getAbility().activate(user, target, this, null, null, null, null, 0, AbilityActivation.SecondaryEffectActivation);
            }
            if (!suppressed) {
                if (target.getAbility().shouldActivate(this, AbilityActivation.OpponentSecondaryEffectActivation)) {
                    suppressed = (boolean) target.getAbility().activate(target, user, this, null, null, null, null, 0, AbilityActivation.OpponentSecondaryEffectActivation);
                }
            }

            if (secondaryEffect != null && !suppressed) {
                return secondaryEffect.activate(this, user, target, type, damage, hit, showMessages, condition);
            }
        }
        return null;
    }

    public EffectTarget getSecondaryEffectTarget() {
        return secondaryEffectTarget;
    }

    public MoveEffectActivation[] getConditions() {
        return effectConditions;
    }

    public MoveEffect getZEffect() {
        return zEffect;
    }

    public Object activateZEffect(Pokemon user, Pokemon target, Type type, Damage damage, int hit, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted) {
            if (zEffect != null) {
                return zEffect.activate(this, user, target, type, damage, hit, showMessages, condition);
            }
        }
        return null;
    }

    public EffectTarget getZEffectTarget() {
        if (Arrays.asList(effectConditions).contains(MoveEffectActivation.CallEffectTarget)) {
            return (EffectTarget) activateZEffect(user, user, null, null, 0, true, MoveEffectActivation.CallEffectTarget);
        }
        return zEffectTarget;
    }

    public MoveEffectActivation[] getZConditions() {
        return zEffectConditions;
    }

    public Pokemon getUser() {
        return user;
    }

    public MoveType[] getMoveTypes() {
        return moveTypes;
    }

    public boolean isUsed() {
        if (moveOrigin != null) {
            return moveOrigin.isUsed();
        }

        return used;
    }

    public void setUsed(boolean used) {
        if (moveOrigin != null) {
            moveOrigin.setUsed(used);
        } else {
            this.used = used;
        }
    }

    public int getConsecutiveUses() {
        if (moveOrigin != null) {
            return moveOrigin.getConsecutiveUses();
        }

        return consecutiveUses;
    }

    public void addUse() {
        if (moveOrigin != null) {
            moveOrigin.addUse();
        } else {
            consecutiveUses++;
            used = true;
        }
    }

    public void setConsecutiveUses(int consecutiveUses) {
        if (moveOrigin != null) {
            moveOrigin.setConsecutiveUses(consecutiveUses);
        } else {
            this.consecutiveUses = consecutiveUses;
        }
    }

    public InherentProperty[] getInherentProperties() {
        return inherentProperties;
    }

    public boolean hasInherentProperty(InherentProperty property) {
        return Arrays.asList(inherentProperties).contains(property);
    }

    public ArrayList<TemporaryProperty> getTemporaryProperties() {
        return temporaryProperties;
    }

    public void addProperty(TemporaryProperty property) {
        if (!temporaryProperties.contains(property)) {
            temporaryProperties.add(property);
        }
    }

    public void removeProperty(TemporaryProperty property) {
        temporaryProperties.remove(property);
    }

    public boolean compare(Move move) {
        return this.name.equals(move.name);
    }

    public boolean compareTrue(Move move) {
        Move comparingMove = move.moveOrigin == null ? move : move.moveOrigin;
        Move originalMove = moveOrigin == null ? this : moveOrigin;

        return originalMove.name.equals(comparingMove.name);
    }
}