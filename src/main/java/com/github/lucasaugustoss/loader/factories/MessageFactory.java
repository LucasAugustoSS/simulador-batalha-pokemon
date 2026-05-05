package com.github.lucasaugustoss.loader.factories;

import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.MessageDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class MessageFactory {
    private final Map<String, Message> messageList = new HashMap<>();

    public Map<String, Message> build(JSONLoader data) {
        createMessage(data);
        return Map.copyOf(messageList);
    }

    private void createMessage(JSONLoader data) {
        for (MessageDTO dto : data.getMessageData().values()) {
            Message message = new Message(
                dto.name,
                dto.type,
                dto.messages
            );

            messageList.put(FactoryTools.formatName(dto.name), message);
        }
    }
}
