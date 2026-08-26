package com.github.lucasaugustoss.data.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.properties.other.MessageType;

public class MessageHandler {
    private static List<List<MessageStorage>> messageQueue = new ArrayList<>();
    private static List<MessageStorage> currentGroup;
    public static MessageType currentType;

    public static void newGroup() {
        currentGroup = new ArrayList<>();
    }

    public static void groupCheckpoint() {
        if (currentGroup != null && !currentGroup.isEmpty()) {
            if (currentGroup.stream().anyMatch(storage ->
                storage.type == MessageType.M_SUCCESS
            )) {
                currentGroup.removeIf(storage ->
                    storage.type == MessageType.M_FAIL
                );
            }

            messageQueue.add(currentGroup);
        }
    }

    public static void endGroup() {
        if (currentGroup != null && !currentGroup.isEmpty()) {
            boolean empty = true;
            for (MessageStorage storage : currentGroup) {
                String message = Data.get().getMessage(storage.name).getMessage(storage.key, storage.params);
                if (message != null && !message.isEmpty()) {
                    empty = false;
                }
            }

            if (!empty) {
                if (currentGroup.stream().anyMatch(storage ->
                    storage.type == MessageType.M_SUCCESS
                )) {
                    currentGroup.removeIf(storage ->
                        storage.type == MessageType.M_FAIL
                    );
                }

                messageQueue.add(currentGroup);
            }
        }
        currentGroup = null;
    }

    public static void add(MessageType type, String name, String key, Map<String, String> params) {
        if (currentGroup != null) {
            currentGroup.add(new MessageStorage(type, name, key, params));
        }
    }

    public static void add(String name, String key, Map<String, String> params) {
        if (currentGroup != null) {
            currentGroup.add(new MessageStorage(currentType, name, key, params));
        }
    }

    public static void add(MessageStorage message) {
        if (currentGroup != null) {
            currentGroup.add(message);
        }
    }

    public static void printStack() {
        for (List<MessageStorage> group : messageQueue) {
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            for (MessageStorage stored : group) {
                try {
                    String message = Data.get().getMessage(stored.name).getMessage(stored.key, stored.params);
                    if (message != null && !message.isEmpty()) {
                        if (App.debug) {
                            System.out.print("[" + stored.type + "]: ");
                        }
                        System.out.println(message);

                        if (!App.debug) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error in message: " + e);
                    System.out.println(
                        "name: " + stored.name + " | " +
                        "key: " + stored.key + " | " +
                        "params: " + stored.params
                    );
                }
            }
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
        }

        messageQueue.clear();
        currentType = null;
    }
}
