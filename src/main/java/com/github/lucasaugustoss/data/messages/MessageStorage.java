package com.github.lucasaugustoss.data.messages;

import java.util.Map;

import com.github.lucasaugustoss.data.properties.other.MessageType;

public class MessageStorage {
    public MessageType type;
    public String name;
    public String key;
    public Map<String, String> params;

    public MessageStorage(String name, String key, Map<String, String> params) {
        this.type = MessageHandler.currentType;
        this.name = name;
        this.key = key;
        this.params = params;
    }

    public MessageStorage(MessageType type, String name, String key, Map<String, String> params) {
        this.type = type;
        this.name = name;
        this.key = key;
        this.params = params;
    }
}
