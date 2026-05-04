package com.github.lucasaugustoss.data.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.AbilityActivation;
import com.github.lucasaugustoss.data.activationConditions.FieldActivation;
import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.activationConditions.MoveEffectActivation;
import com.github.lucasaugustoss.data.activationConditions.StatusActivation;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.MoveEffect;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.properties.moves.*;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class Move {
    private MoveTemplate template;

    private String name;
    private boolean notTrueMove;
    private boolean debug;
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

    private MoveEffect[] primaryEffects;
    private MoveEffect[] secondaryEffects;
    private MoveEffect zEffect;

    private MoveType[] moveTypes;
    private InherentProperty[] inherentProperties;
    private List<TemporaryProperty> temporaryProperties;

    private Pokemon user;
    private PokemonTemplate exclusiveUser;
    private boolean exclusiveForm;

    private Message messages;

    private boolean used;
    private int consecutiveUses;

    public Move( // create
        MoveTemplate template, Pokemon user
    ) {
        this.template = template;
        this.name = template.getName();
        this.notTrueMove = template.isNotTrueMove();
        this.debug = template.isDebug();
        this.zMove = template.isZMove();
        this.signatureZMove = template.isSignatureZMove();
        this.type = new Type(template.getType(), this);
        this.category = template.getCategory();
        this.PP = template.getPP();
        this.currentPP = template.getPP();
        this.power = template.getPower();
        this.zMovePower = template.getZMovePower();
        this.accuracy = template.getAccuracy();
        this.critRatio = template.getCritRatio();
        this.contact = template.isContact();
        this.priority = template.getPriority();
        this.hits = template.getHits();
        this.moveTarget = template.getMoveTarget();
        this.primaryEffects = template.getPrimaryEffects();
        this.secondaryEffects = template.getSecondaryEffects();
        this.zEffect = template.getZEffect();
        this.moveTypes = template.getMoveTypes();
        this.inherentProperties = template.getInherentProperties();
        this.temporaryProperties = new ArrayList<>();
        this.user = user;
        this.exclusiveUser = template.getExclusiveUser();
        this.exclusiveForm = template.isExclusiveForm();
        this.messages = template.getMessages();

        if (this.primaryEffects != null) {
            for (MoveEffect primaryEffect : this.primaryEffects) {
                primaryEffect.setMove(this);
            }
        }
        if (this.secondaryEffects != null) {
            for (MoveEffect secondaryEffect : this.secondaryEffects) {
                secondaryEffect.setMove(this);
            }
        }
        if (this.zEffect != null) {
            this.zEffect.setMove(this);
        }
    }
    public Move( // create (transformed)
        MoveTemplate template, Move moveOrigin, Pokemon user
    ) {
        this.template = template;
        this.name = template.getName();
        this.notTrueMove = template.isNotTrueMove();
        this.debug = template.isDebug();
        this.zMove = template.isZMove();
        this.signatureZMove = template.isSignatureZMove();
        this.moveOrigin = moveOrigin;
        this.type = new Type(template.getType(), this);
        this.power = template.getPower();
        this.zMovePower = template.getZMovePower();
        this.accuracy = template.getAccuracy();
        this.critRatio = template.getCritRatio();
        this.priority = template.getPriority();
        this.hits = template.getHits();
        this.moveTarget = template.getMoveTarget();
        this.primaryEffects = template.getPrimaryEffects();
        this.secondaryEffects = template.getSecondaryEffects();
        this.zEffect = template.getZEffect();
        this.moveTypes = template.getMoveTypes();
        this.inherentProperties = template.getInherentProperties();
        this.temporaryProperties = new ArrayList<>();
        this.user = user;
        this.exclusiveUser = template.getExclusiveUser();
        this.exclusiveForm = template.isExclusiveForm();
        this.messages = template.getMessages();

        if (template.getCategory() != null) {
            this.category = template.getCategory();
        } else {
            this.category = moveOrigin.category;
        }

        this.PP = template.getPP();
        this.currentPP = template.getPP();
        this.contact = template.isContact();

        if (zMove) {
            this.power = getZMovePower();
        } else {
            this.power = template.getPower();
        }

        if (this.primaryEffects != null) {
            for (MoveEffect primaryEffect : this.primaryEffects) {
                primaryEffect.setMove(this);
            }
        }
        if (this.secondaryEffects != null) {
            for (MoveEffect secondaryEffect : this.secondaryEffects) {
                secondaryEffect.setMove(this);
            }
        }
        if (this.zEffect != null) {
            this.zEffect.setMove(this);
        }
    }

    public Move( // copy
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
        this.accuracy = original.accuracy;
        this.critRatio = original.critRatio;
        this.contact = original.contact;
        this.priority = original.priority;
        this.hits = original.hits;
        this.moveTarget = original.moveTarget;
        this.primaryEffects = original.primaryEffects;
        this.secondaryEffects = original.secondaryEffects;
        this.zEffect = original.zEffect;
        this.moveTypes = original.moveTypes;
        this.inherentProperties = original.inherentProperties;
        this.temporaryProperties = new ArrayList<>();
        this.user = user;
        this.exclusiveUser = original.exclusiveUser;
        this.exclusiveForm = original.exclusiveForm;
        this.messages = original.messages;

        if (this.primaryEffects != null) {
            for (MoveEffect primaryEffect : this.primaryEffects) {
                primaryEffect.setMove(this);
            }
        }
        if (this.secondaryEffects != null) {
            for (MoveEffect secondaryEffect : this.secondaryEffects) {
                secondaryEffect.setMove(this);
            }
        }
        if (this.zEffect != null) {
            this.zEffect.setMove(this);
        }
    }
    public Move( // copy (transformed)
        Move original, Move moveOrigin, Pokemon user
    ) {
        this.name = original.name;
        this.zMove = original.zMove;
        this.signatureZMove = original.signatureZMove;
        this.moveOrigin = moveOrigin;
        this.type = new Type(original.type, this);
        this.power = original.power;
        this.zMovePower = original.zMovePower;
        this.accuracy = original.accuracy;
        this.critRatio = original.critRatio;
        this.priority = original.priority;
        this.hits = original.hits;
        this.moveTarget = original.moveTarget;
        this.primaryEffects = original.primaryEffects;
        this.secondaryEffects = original.secondaryEffects;
        this.zEffect = original.zEffect;
        this.moveTypes = original.moveTypes;
        this.inherentProperties = original.inherentProperties;
        this.temporaryProperties = new ArrayList<>();
        this.user = user;
        this.exclusiveUser = original.exclusiveUser;
        this.exclusiveForm = original.exclusiveForm;
        this.messages = original.messages;

        if (original.category != null) {
            this.category = original.category;
        } else {
            this.category = moveOrigin.category;
        }

        this.PP = moveOrigin.PP;
        this.currentPP = moveOrigin.currentPP;
        this.contact = moveOrigin.contact;

        if (zMove) {
            this.power = getZMovePower();
        } else {
            this.power = original.power;
        }

        if (this.primaryEffects != null) {
            for (MoveEffect primaryEffect : this.primaryEffects) {
                primaryEffect.setMove(this);
            }
        }
        if (this.secondaryEffects != null) {
            for (MoveEffect secondaryEffect : this.secondaryEffects) {
                secondaryEffect.setMove(this);
            }
        }
        if (this.zEffect != null) {
            this.zEffect.setMove(this);
        }
    }



    public MoveTemplate getTemplate() {
        return template;
    }
    
    public String getName() {
        Move self = this;
        if (Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.CallMoveData)) {
            self = (Move) activatePrimary(user, user, null, null, 0, null, true, MoveEffectActivation.CallMoveData);
        }
        if (isZMove() &&
            Arrays.asList(moveOrigin.getPrimaryConditions()).contains(MoveEffectActivation.CallMoveData)) {
            self = ((Move) moveOrigin.activatePrimary(user, user, null, null, 0, null, true, MoveEffectActivation.CallMoveData)).turnZMove();
        }

        return self.getTrueName();
    }

    public String getTrueName() {
        return name;
    }

    public boolean isNotTrueMove() {
        return notTrueMove;
    }

    public boolean isDebug() {
        return debug;
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
            return new Move(Data.get().getMove("breakneck_blitz"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("fighting"))) {
            return new Move(Data.get().getMove("all_out_pummeling"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("flying"))) {
            return new Move(Data.get().getMove("supersonic_skystrike"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("poison"))) {
            return new Move(Data.get().getMove("acid_downpour"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("ground"))) {
            return new Move(Data.get().getMove("tectonic_rage"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("rock"))) {
            return new Move(Data.get().getMove("continental_crush"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("bug"))) {
            return new Move(Data.get().getMove("savage_spin_out"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("ghost"))) {
            return new Move(Data.get().getMove("never_ending_nightmare"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("steel"))) {
            return new Move(Data.get().getMove("corkscrew_crash"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("fire"))) {
            return new Move(Data.get().getMove("inferno_overdrive"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("water"))) {
            return new Move(Data.get().getMove("hydro_vortex"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("grass"))) {
            return new Move(Data.get().getMove("bloom_doom"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("electric"))) {
            return new Move(Data.get().getMove("gigavolt_havoc"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("psychic"))) {
            return new Move(Data.get().getMove("shattered_psyche"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("ice"))) {
            return new Move(Data.get().getMove("subzero_slammer"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("dragon"))) {
            return new Move(Data.get().getMove("devastating_drake"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("dark"))) {
            return new Move(Data.get().getMove("black_hole_eclipse"), this, user);
        } else if (getType(true, false).compare(Data.get().getType("fairy"))) {
            return new Move(Data.get().getMove("twinkle_tackle"), this, user);
        } else {
            // failsafe
            return new Move(Data.get().getMove("breakneck_blitz"), this, user);
        }
    }

    public Type getTrueType() {
        return type;
    }

    public Type getType(boolean zMoveConversion, boolean ignoreAbility) {
        Type currentType = type;

        if (!zMoveConversion) {
            if (Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.CallType)) {
                currentType = new Type((Type) activatePrimary(user, user, currentType, null, 0, null, true, MoveEffectActivation.CallType), this);
            }

            if (!ignoreAbility) {
                if (Arrays.asList(user.getAbility().getConditions()).contains(AbilityActivation.CallMoveType)) {
                    currentType = new Type((Type) user.getAbility().activate(user, user, this, currentType, null, null, null, 0, AbilityActivation.CallMoveType), this);
                }
            }
        } else {
            if (Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.ZCallType)) {
                currentType = new Type((Type) activatePrimary(user, user, currentType, null, 0, null, true, MoveEffectActivation.ZCallType), this);
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
        if (Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.EffectivenessCalc)) {
            return (Type[]) activatePrimary(user, user, null, null, 0, null, true, MoveEffectActivation.EffectivenessCalc);
        }

        return new Type[] {getType(false, false)};
    }

    public Category getCategory() {
        if (Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.CallCategory)) {
            return (Category) activatePrimary(user, user, null, null, 0, null, true, MoveEffectActivation.CallCategory);
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

        for (StatusCondition vol : user.getVolatileStatusList()) {
            if (Arrays.asList(vol.getActivation()).contains(StatusActivation.PPChange)) {
                vol.activate(user, user, this, null, true, StatusActivation.PPChange);
            }
        }
    }

    public double getPower(boolean truePower, boolean unmodified, int hit) {
        double power = this.power;

        Pokemon opponent = Battle.getOpposingPokemon(user.getTeam());

        if (!truePower) {
            if (Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.CallPower)) {
                power = (double) activatePrimary(user, opponent, null, null, hit, null, true, MoveEffectActivation.CallPower);
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

        if (!user.getAbility().compare(Data.get().getAbility("prankster")) || temporaryProperties.contains(TemporaryProperty.Reflected)) {
            removeProperty(TemporaryProperty.PranksterBoosted);
        }

        return priority;
    }

    public int[] getHits() {
        return hits;
    }

    public MoveTarget getMoveTarget(boolean trueTarget) {
        if (!trueTarget &&
            Arrays.asList(getPrimaryConditions()).contains(MoveEffectActivation.CallMoveTarget)) {
            return (MoveTarget) activatePrimary(user, user, null, null, 0, null, true, MoveEffectActivation.CallMoveTarget);
        }
        return moveTarget;
    }

    public boolean targetsUser() {
        if (getMoveTarget(false) == MoveTarget.User ||
            getMoveTarget(false) == MoveTarget.UserAndAlly ||
            getMoveTarget(false) == MoveTarget.UserField) {
            return true;
        }
        return false;
    }

    public boolean targetsOpponent() {
        if (getMoveTarget(false) == MoveTarget.Normal ||
            getMoveTarget(false) == MoveTarget.AllOpponents ||
            getMoveTarget(false) == MoveTarget.AllAdjacent ||
            getMoveTarget(false) == MoveTarget.RandomOpponent ||
            getMoveTarget(false) == MoveTarget.OpponentField) {
            return true;
        }
        return false;
    }

    public void setMoveTarget(MoveTarget moveTarget) {
        this.moveTarget = moveTarget;
    }

    public MoveEffect[] getPrimaryEffect() {
        return primaryEffects;
    }

    public MoveEffectActivation[] getPrimaryConditions() {
        if (primaryEffects == null) {
            return new MoveEffectActivation[0];
        }

        List<MoveEffectActivation> conditions = new ArrayList<>();

        for (MoveEffect effect : primaryEffects) {
            for (MoveEffectActivation condition : effect.getActivation()) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        return conditions.toArray(new MoveEffectActivation[0]);
    }

    public Object activatePrimary(Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted) {
            for (MoveEffect effect : primaryEffects) {
                if (!effect.shouldActivate(condition)) {
                    continue;
                }

                Object result = activatePrimarySingle(effect, user, target, type, damage, hit, stat, showMessages, condition);

                if (condition == MoveEffectActivation.AfterMove) {
                    continue;
                }

                return result;
            }
        }
        return null;
    }

    public Object activatePrimarySingle(MoveEffect effect, Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted && effect != null) {
            if (condition != MoveEffectActivation.TryActivate &&
                primaryShouldActivate(MoveEffectActivation.TryActivate) &&
                !((boolean) activatePrimary(user, target, type, damage, hit, stat, showMessages, MoveEffectActivation.TryActivate))) {
                return effect.activateDefault(this, user, target, type, damage, hit, stat, showMessages, condition);
            }

            if (user != target) {
                for (StatusCondition vol : target.getVolatileStatusList()) {
                    if (Arrays.asList(vol.getActivation()).contains(StatusActivation.PrimaryEffectActivation)) {
                        MoveEffect[] blockedEffects = (MoveEffect[]) vol.activate(target, user, this, null, true, StatusActivation.PrimaryEffectActivation);
                        if (Arrays.asList(blockedEffects).contains(effect)) {
                            return effect.activateDefault(this, user, target, type, damage, hit, stat, showMessages, condition);
                        }
                    }
                }
            }

            Object result = effect.activate(this, user, target, type, damage, hit, stat, showMessages, condition);

            if (result instanceof Boolean success && success == true &&
                condition != MoveEffectActivation.PrimarySuccess &&
                primaryShouldActivate(MoveEffectActivation.PrimarySuccess)) {
                activatePrimary(user, target, type, damage, hit, stat, showMessages, MoveEffectActivation.PrimarySuccess);
            }

            return result;
        }
        return null;
    }

    public boolean primaryShouldActivate(MoveEffectActivation condition) {
        for (MoveEffect effect : primaryEffects) {
            if (effect.shouldActivate(condition)) {
                return true;
            }
        }
        return false;
    }

    public MoveEffect[] getSecondaryEffect() {
        return secondaryEffects;
    }

    public MoveEffectActivation[] getSecondaryConditions() {
        if (secondaryEffects == null) {
            return new MoveEffectActivation[0];
        }

        List<MoveEffectActivation> conditions = new ArrayList<>();

        for (MoveEffect effect : secondaryEffects) {
            for (MoveEffectActivation condition : effect.getActivation()) {
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }

        return conditions.toArray(new MoveEffectActivation[0]);
    }

    public Object activateSecondary(Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted) {
            for (MoveEffect effect : secondaryEffects) {
                if (!effect.shouldActivate(condition)) {
                    continue;
                }

                Object result = activateSecondarySingle(effect, user, target, type, damage, hit, stat, showMessages, condition);

                if (condition == MoveEffectActivation.AfterMove) {
                    continue;
                }

                return result;
            }
        }
        return null;
    }

    public Object activateSecondarySingle(MoveEffect effect, Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted && effect != null) {
            if (condition != MoveEffectActivation.TryActivate &&
                secondaryShouldActivate(MoveEffectActivation.TryActivate) &&
                !((boolean) activateSecondary(user, target, type, damage, hit, stat, showMessages, MoveEffectActivation.TryActivate))) {
                return effect.activateDefault(this, user, target, type, damage, hit, stat, showMessages, condition);
            }

            boolean suppressed = false;
            if (user != target) {
                for (StatusCondition vol : target.getVolatileStatusList()) {
                    if (Arrays.asList(vol.getActivation()).contains(StatusActivation.SecondaryEffectActivation)) {
                        MoveEffect[] blockedEffects = (MoveEffect[]) vol.activate(target, user, this, null, true, StatusActivation.PrimaryEffectActivation);
                        if (Arrays.asList(blockedEffects).contains(effect)) {
                            return effect.activateDefault(this, user, target, type, damage, hit, stat, showMessages, condition);
                        }
                    }
                }

                if (user.getAbility().shouldActivate(AbilityActivation.SecondaryEffectActivation)) {
                    suppressed = (boolean) user.getAbility().activate(user, target, this, null, null, null, null, 0, AbilityActivation.SecondaryEffectActivation);
                }
                if (!suppressed) {
                    if (target.getAbility().shouldActivate(this, AbilityActivation.OpponentSecondaryEffectActivation)) {
                        MoveEffect[] blockedEffects = (MoveEffect[]) target.getAbility().activate(target, user, this, null, null, null, null, 0, AbilityActivation.OpponentSecondaryEffectActivation);
                        if (Arrays.asList(blockedEffects).contains(effect)) {
                            suppressed = true;
                        }
                    }
                }
            }

            if (!suppressed) {
                return effect.activate(this, user, target, type, damage, hit, stat, showMessages, condition);
            } else {
                return effect.activateDefault(this, user, target, type, damage, hit, stat, showMessages, condition);
            }
        }
        return null;
    }

    public boolean secondaryShouldActivate(MoveEffectActivation condition) {
        for (MoveEffect effect : secondaryEffects) {
            if (effect.shouldActivate(condition)) {
                return true;
            }
        }
        return false;
    }

    public MoveEffect getZEffect() {
        return zEffect;
    }

    public MoveEffectActivation[] getZConditions() {
        if (zEffect == null) {
            return new MoveEffectActivation[0];
        }

        List<MoveEffectActivation> conditions = new ArrayList<>();

        for (MoveEffectActivation condition : zEffect.getActivation()) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);
            }
        }

        return conditions.toArray(new MoveEffectActivation[0]);
    }

    public Object activateZ(Pokemon user, Pokemon target, Type type, Damage damage, int hit, Stat stat, boolean showMessages, MoveEffectActivation condition) {
        if (App.battleStarted && zEffect != null) {
            return zEffect.activate(this, user, target, type, damage, hit, stat, showMessages, condition);
        }
        return zEffect.activateDefault(this, user, target, type, damage, hit, stat, showMessages, condition);
    }

    public boolean zShouldActivate(MoveEffectActivation condition) {
        return zEffect != null ? zEffect.shouldActivate(condition) : false;
    }

    public MoveType[] getMoveTypes() {
        return moveTypes;
    }

    public InherentProperty[] getInherentProperties() {
        return inherentProperties;
    }

    public boolean hasInherentProperty(InherentProperty property) {
        return Arrays.asList(inherentProperties).contains(property);
    }

    public List<TemporaryProperty> getTemporaryProperties() {
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

    public Pokemon getUser() {
        return user;
    }

    public PokemonTemplate getExclusiveUser() {
        return exclusiveUser;
    }

    public boolean isExclusiveForm() {
        return exclusiveForm;
    }

    public Message getMessages() {
        return messages;
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

    public boolean compare(Move other) {
        return this.name.equals(other.name);
    }

    public boolean compare(MoveTemplate template) {
        return this.name.equals(template.getName());
    }

    public boolean compareTrue(Move other) {
        Move comparingMove = other.moveOrigin == null ? other : other.moveOrigin;
        Move originalMove = moveOrigin == null ? this : moveOrigin;

        return originalMove.name.equals(comparingMove.name);
    }
}