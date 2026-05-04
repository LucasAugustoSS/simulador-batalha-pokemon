package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.objects.effects.ItemEffect;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.loader.dtos.ItemEffectDTO;

public class ItemTemplate extends Template {
    private String name;
    private boolean consumable;
    private ItemType type;
    private String[] userIDs;
    private PokemonTemplate[] users;
    private boolean tetheredToValidUser;
    private String transformsIntoID;
    private PokemonTemplate transformsInto;
    private String changesTypeToID;
    private TypeTemplate changesTypeTo;
    private String zMoveID;
    private MoveTemplate zMove;
    private String zMoveOriginID;
    private MoveTemplate zMoveOrigin;
    private ItemEffectDTO effectDTO;
    private ItemEffect effect;
    private int flingPower;
    private ItemEffectDTO flingEffectDTO;
    private ItemEffect flingEffect;

    public ItemTemplate(
        int index, String id,
        String name, boolean consumable, ItemType type,
        String[] userIDs, boolean tetheredToValidUser, String transformsIntoID, String changesTypeToID,
        String zMoveID, String zMoveOriginID,
        ItemEffectDTO effectDTO,
        int flingPower, ItemEffectDTO flingEffectDTO
    ) {
        super(index, id);
        this.name = name;
        this.consumable = consumable;
        this.type = type;
        this.userIDs = userIDs;
        this.tetheredToValidUser = tetheredToValidUser;
        this.transformsIntoID = transformsIntoID;
        this.changesTypeToID = changesTypeToID;
        this.zMoveID = zMoveID;
        this.zMoveOriginID = zMoveOriginID;
        this.effectDTO = effectDTO;
        this.flingPower = flingPower;
        this.flingEffectDTO = flingEffectDTO;
    }

    public String getName() {
        return name;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public ItemType getType() {
        return type;
    }

    public String[] getUserIDs() {
        return userIDs;
    }

    public PokemonTemplate[] getUsers() {
        return users;
    }

    public boolean isTetheredToValidUser() {
        return tetheredToValidUser;
    }

    public String getTransformsIntoID() {
        return transformsIntoID;
    }

    public PokemonTemplate getTransformsInto() {
        return transformsInto;
    }

    public String getChangesTypeToID() {
        return changesTypeToID;
    }

    public TypeTemplate getChangesTypeTo() {
        return changesTypeTo;
    }

    public String getZMoveID() {
        return zMoveID;
    }

    public MoveTemplate getZMove() {
        return zMove;
    }

    public String getZMoveOriginID() {
        return zMoveOriginID;
    }

    public MoveTemplate getZMoveOrigin() {
        return zMoveOrigin;
    }

    public ItemEffectDTO getEffectDTO() {
        return effectDTO;
    }

    public ItemEffect getEffect() {
        return effect;
    }

    public int getFlingPower() {
        return flingPower;
    }

    public ItemEffectDTO getFlingEffectDTO() {
        return flingEffectDTO;
    }

    public ItemEffect getFlingEffect() {
        return flingEffect;
    }



    public void setUsers(PokemonTemplate[] users) {
        this.users = users;
    }

    public void setTransformsInto(PokemonTemplate transformsInto) {
        this.transformsInto = transformsInto;
    }

    public void setChangesTypeTo(TypeTemplate changesTypeTo) {
        this.changesTypeTo = changesTypeTo;
    }

    public void setZMove(MoveTemplate zMove) {
        this.zMove = zMove;
    }

    public void setZMoveOrigin(MoveTemplate zMoveOrigin) {
        this.zMoveOrigin = zMoveOrigin;
    }

    public void setEffect(ItemEffect effect) {
        this.effect = effect;
    }

    public void setFlingEffect(ItemEffect flingEffect) {
        this.flingEffect = flingEffect;
    }

    public boolean compare(ItemTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(Item item) {
        return this.name.equals(item.getName());
    }
}
