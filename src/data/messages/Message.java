package data.messages;

import java.util.HashMap;
import java.util.Map;

import data.classes.Ability;
import data.classes.Item;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.Stat;

public class Message {
    private HashMap<String, String> messages;

    public Message(Map<String, String> messages) {
        this.messages = new HashMap<>(messages);
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

    public boolean hasMessage(String key) {
        return messages.containsKey(key);
    }

    public void print(String key) {
        if (messages.containsKey(key)) {
            System.out.println(messages.get(key));
        }
    }

    public void print(String key, Pokemon pokemon) {
        if (messages.containsKey(key)) {
            boolean capitalized = messages.get(key).startsWith("(Pokemon)");

            String message = messages.get(key).replace("(Pokemon)", pokemon.getName(true, capitalized));

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, Pokemon causer) {
        if (messages.containsKey(key)) {
            boolean pokemonCapitalized = messages.get(key).startsWith("(Pokemon)");
            boolean causerCapitalized = messages.get(key).startsWith("(Causer)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, pokemonCapitalized));
            message = message.replace("(Causer)", pokemon.getName(true, causerCapitalized));

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, Stat stat) {
        if (messages.containsKey(key)) {
            boolean pokemonCapitalized = messages.get(key).startsWith("(Pokemon)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, pokemonCapitalized));
            message = message.replace("(Stat)", stat.getName());

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, Ability ability, Stat stat) {
        if (messages.containsKey(key)) {
            boolean pokemonCapitalized = messages.get(key).startsWith("(Pokemon)");
            boolean causerCapitalized = messages.get(key).startsWith("(Causer)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, pokemonCapitalized));
            message = message.replace("(Causer)", ability.getPokemon().getName(true, causerCapitalized));
            message = message.replace("(Ability)", ability.getName());
            message = message.replace("(Stat)", stat.getName());

            System.out.println(message);
        }
    }

    public void print(String key, int team) {
        if (messages.containsKey(key)) {
            boolean capitalized = messages.get(key).startsWith("(Team)");

            String teamPrefix;
            if (team == 0) {
                teamPrefix = "Your";
            } else {
                teamPrefix = "The opposing";
            }
            if (!capitalized) {
                teamPrefix = teamPrefix.toLowerCase();
            }

            String message = messages.get(key);
            message = message.replace("(Team)", teamPrefix);

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, int number) {
        if (messages.containsKey(key)) {
            boolean pokemonCapitalized = messages.get(key).startsWith("(Pokemon)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, pokemonCapitalized));
            message = message.replace("(Number)", Integer.toString(number));

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, Pokemon causer, int number) {
        if (messages.containsKey(key)) {
            boolean pokemonCapitalized = messages.get(key).startsWith("(Pokemon)");
            boolean causerCapitalized = messages.get(key).startsWith("(Causer)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, pokemonCapitalized));
            message = message.replace("(Causer)", causer.getName(true, causerCapitalized));
            message = message.replace("(Number)", Integer.toString(number));

            System.out.println(message);
        }
    }

    public void print(String key, Ability ability) {
        if (messages.containsKey(key)) {
            boolean capitalized = messages.get(key).startsWith("(Pokemon)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", ability.getPokemon().getName(true, capitalized));
            message = message.replace("(Ability)", ability.getName());

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, Ability ability) {
        if (messages.containsKey(key)) {
            boolean pokemonCapitalized = messages.get(key).startsWith("(Pokemon)");
            boolean causerCapitalized = messages.get(key).startsWith("(Causer)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, pokemonCapitalized));
            message = message.replace("(Causer)", ability.getPokemon().getName(true, causerCapitalized));
            message = message.replace("(Ability)", ability.getName());

            System.out.println(message);
        }
    }

    public void print(String key, Pokemon pokemon, Item item) {
        if (messages.containsKey(key)) {
            boolean capitalized = messages.get(key).startsWith("(Pokemon)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", pokemon.getName(true, capitalized));
            message = message.replace("(Item)", item.getName());

            System.out.println(message);
        }
    }

    public void print(String key, Move move) {
        if (messages.containsKey(key)) {
            boolean capitalized = messages.get(key).startsWith("(Pokemon)");

            String message = messages.get(key);
            message = message.replace("(Pokemon)", move.getUser().getName(true, capitalized));
            message = message.replace("(Move)", move.getName());

            System.out.println(message);
        }
    }
}