package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.objects.effects.ItemEffect;
import com.github.lucasaugustoss.data.properties.items.ItemCategory;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.loader.dtos.ItemEffectDTO;

public class ItemTemplate extends Template {
    private String name;
    private boolean consumable;
    private ItemCategory[] categories;
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
    private ItemEffectDTO[] effectDTOs;
    private ItemEffect[] effects;
    private int flingPower;
    private ItemEffectDTO flingEffectDTO;
    private ItemEffect flingEffect;
    private String messagesID;
    private Message messages;

    public ItemTemplate(
        int index, String id,
        String name, boolean consumable, ItemCategory[] categories, ItemType type,
        String[] userIDs, boolean tetheredToValidUser, String transformsIntoID, String changesTypeToID,
        String zMoveID, String zMoveOriginID,
        ItemEffectDTO[] effectDTOs,
        int flingPower, ItemEffectDTO flingEffectDTO,
        String messagesID
    ) {
        super(index, id);
        this.name = name;
        this.consumable = consumable;
        this.categories = categories;
        this.type = type;
        this.userIDs = userIDs;
        this.tetheredToValidUser = tetheredToValidUser;
        this.transformsIntoID = transformsIntoID;
        this.changesTypeToID = changesTypeToID;
        this.zMoveID = zMoveID;
        this.zMoveOriginID = zMoveOriginID;
        this.effectDTOs = effectDTOs;
        this.flingPower = flingPower;
        this.flingEffectDTO = flingEffectDTO;
        this.messagesID = messagesID;
    }

    public String getName() {
        return name;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public ItemCategory[] getCategories() {
        return categories;
    }

    public boolean isCategory(ItemCategory category) {
        for (ItemCategory itemCategory : categories) {
            if (itemCategory == category) {
                return true;
            }
        }
        return false;
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

    public ItemEffectDTO[] getEffectDTOs() {
        return effectDTOs;
    }

    public ItemEffect[] getEffects() {
        return effects;
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

    public String getMessagesID() {
        return messagesID;
    }

    public Message getMessages() {
        return messages;
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

    public void setEffects(ItemEffect[] effects) {
        this.effects = effects;
    }

    public void setFlingEffect(ItemEffect flingEffect) {
        this.flingEffect = flingEffect;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }

    public boolean compare(ItemTemplate other) {
        return this.name.equals(other.name);
    }

    public boolean compare(Item item) {
        return this.name.equals(item.getName());
    }
}
