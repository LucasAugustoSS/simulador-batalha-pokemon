package com.github.lucasaugustoss.data.messages;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private String name;
    private HashMap<String, String> messages;

    public Message(String name, Map<String, String> messages) {
        this.name = name;
        this.messages = new HashMap<>(messages);
    }

    public String getName() {
        return name;
    }

    public boolean hasMessage(String key) {
        return messages.containsKey(key);
    }



    public String getMessage(String key) {
        return messages.get(key);
    }

    public String getMessage(String key, Map<String, String> names) {
        if (!hasMessage(key)) {
            return "";
        }

        names = new HashMap<>(names);

        String[] pokemonPlaceholders = new String[] {"Pokemon", "Causer", "Target"};

        for (String placeholder : pokemonPlaceholders) {
            if (names.containsKey(placeholder) &&
                names.get(placeholder).startsWith("the opposing ")) {
                if (messages.get(key).startsWith("(" + placeholder + ")")) {
                    String name = names.get(placeholder);
                    name = name.substring(0, 1).toUpperCase() +
                           name.substring(1);
                    names.put(placeholder, name);
                }
            }
        }

        if (names.containsKey("Team")) {
            if (messages.get(key).startsWith("(Team)")) {
                String name = names.get("Team");
                name = name.substring(0, 1).toUpperCase() +
                       name.substring(1);
                names.put("Team", name);
            }
        }
        
        String message = messages.get(key);

        if (names != null) {
            for (Map.Entry<String, String> name : names.entrySet()) {
                message = message.replace("(" + name.getKey() + ")", name.getValue());
            }
        }
        
        return message;
    }



    public void print(String key) {
        if (messages.containsKey(key)) {
            System.out.println(messages.get(key));
        }
    }

    public void print(String key, Map<String, String> names) {
        if (messages.containsKey(key)) {
            if (names == null) {
                System.out.println(messages.get(key));
                return;
            }

            System.out.println(getMessage(key, names));
        }
    }
}