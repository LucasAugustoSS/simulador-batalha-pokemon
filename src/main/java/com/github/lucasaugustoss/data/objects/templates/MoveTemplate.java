package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.effects.MoveEffect;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveTarget;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.loader.dtos.MoveEffectDTO;

public class MoveTemplate extends Template {
    private String name;
    private boolean notTrueMove;
    private boolean debug;
    private boolean zMove;
    private boolean signatureZMove;
    private String typeID;
    private TypeTemplate type;
    private Category category;
    private int PP;
    private double power;
    private double zMovePower;
    private int accuracy;
    private int critRatio;
    private boolean contact;
    private int priority;
    private int[] hits;
    private MoveTarget moveTarget;
    private MoveEffectDTO[] primaryEffectDTOs;
    private MoveEffect[] primaryEffects;
    private MoveEffectDTO[] secondaryEffectDTOs;
    private MoveEffect[] secondaryEffects;
    private MoveEffectDTO zEffectDTO;
    private MoveEffect zEffect;
    private MoveType[] moveTypes;
    private InherentProperty[] inherentProperties;
    private String exclusiveUserID;
    private PokemonTemplate exclusiveUser;
    private boolean exclusiveForm;
    private String messagesID;
    private Message messages;

    public MoveTemplate(
        int index, String id,
        String name,
        boolean notTrueMove, boolean debug,
        boolean zMove, boolean signatureZMove,
        String typeID, Category category,
        int PP, double power, double zMovePower, int accuracy,
        int critRatio, boolean contact, int priority, int[] hits,
        MoveTarget moveTarget,
        MoveEffectDTO[] primaryEffectDTOs, MoveEffectDTO[] secondaryEffectDTOs, MoveEffectDTO zEffectDTO,
        MoveType[] moveTypes, InherentProperty[] inherentProperties,
        String exclusiveUserID, boolean exclusiveForm,
        String messagesID
    ) {
        super(index, id);
        this.name = name;
        this.notTrueMove = notTrueMove;
        this.debug = debug;
        this.zMove = zMove;
        this.signatureZMove = signatureZMove;
        this.typeID = typeID;
        this.category = category;
        this.PP = PP;
        this.power = power;
        this.zMovePower = zMovePower;
        this.accuracy = accuracy;
        this.critRatio = critRatio;
        this.contact = contact;
        this.priority = priority;
        this.hits = hits;
        this.moveTarget = moveTarget;
        this.primaryEffectDTOs = primaryEffectDTOs;
        this.secondaryEffectDTOs = secondaryEffectDTOs;
        this.zEffectDTO = zEffectDTO;
        this.moveTypes = moveTypes;
        this.inherentProperties = inherentProperties;
        this.exclusiveUserID = exclusiveUserID;
        this.exclusiveForm = exclusiveForm;
        this.messagesID = messagesID;
    }

    public String getName() {
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

    public String getTypeID() {
        return typeID;
    }

    public TypeTemplate getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public int getPP() {
        return PP;
    }

    public double getPower() {
        return power;
    }

    public double getZMovePower() {
        return zMovePower;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getCritRatio() {
        return critRatio;
    }

    public boolean isContact() {
        return contact;
    }

    public int getPriority() {
        return priority;
    }

    public int[] getHits() {
        return hits;
    }

    public MoveTarget getMoveTarget() {
        return moveTarget;
    }

    public MoveEffectDTO[] getPrimaryEffectDTOs() {
        return primaryEffectDTOs;
    }

    public MoveEffect[] getPrimaryEffects() {
        return primaryEffects;
    }

    public MoveEffectDTO[] getSecondaryEffectDTOs() {
        return secondaryEffectDTOs;
    }

    public MoveEffect[] getSecondaryEffects() {
        return secondaryEffects;
    }

    public MoveEffectDTO getZEffectDTO() {
        return zEffectDTO;
    }

    public MoveEffect getZEffect() {
        return zEffect;
    }

    public MoveType[] getMoveTypes() {
        return moveTypes;
    }

    public InherentProperty[] getInherentProperties() {
        return inherentProperties;
    }

    public String getExclusiveUserID() {
        return exclusiveUserID;
    }

    public PokemonTemplate getExclusiveUser() {
        return exclusiveUser;
    }

    public boolean isExclusiveForm() {
        return exclusiveForm;
    }

    public String getMessagesID() {
        return messagesID;
    }

    public Message getMessages() {
        return messages;
    }



    public void setType(TypeTemplate type) {
        this.type = type;
    }

    public void setPrimaryEffects(MoveEffect[] primaryEffects) {
        this.primaryEffects = primaryEffects;
    }

    public void setSecondaryEffects(MoveEffect[] secondaryEffects) {
        this.secondaryEffects = secondaryEffects;
    }

    public void setZEffect(MoveEffect zEffect) {
        this.zEffect = zEffect;
    }

    public void setExclusiveUser(PokemonTemplate exclusiveUser) {
        this.exclusiveUser = exclusiveUser;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }

    public boolean compare(MoveTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(Move move) {
        return this.name.equals(move.getName());
    }

    public boolean compareTrue(Move move) {
        Move comparingMove = move.getMoveOrigin() == null ? move : move.getMoveOrigin();

        return this.name.equals(comparingMove.getName());
    }
}
