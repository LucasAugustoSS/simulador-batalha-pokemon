package com.github.lucasaugustoss.loader.factories.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.lists.AllAbilities;
import com.github.lucasaugustoss.data.lists.AllItems;
import com.github.lucasaugustoss.data.lists.AllMoves;
import com.github.lucasaugustoss.data.lists.AllStatusConditions;

public class FactoryTools {
    public static Ability[] convertAbilityArray(String[] names) {
        if (names == null) {
            return new Ability[0];
        }

        ArrayList<Ability> abilities = new ArrayList<>();

        for (String name : names) {
            for (Ability ability : AllAbilities.allAbilities) {
                if (formatName(ability.getName()).equals(name)) {
                    abilities.add(ability);
                    break;
                }
            }
        }

        return abilities.toArray(new Ability[0]);
    }

    public static Move[] convertMoveArray(String[] names) {
        if (names == null) {
            return new Move[0];
        }

        ArrayList<Move> moves = new ArrayList<>();

        for (String name : names) {
            for (Move move : AllMoves.allMoves) {
                if (formatName(move.getTrueName()).equals(name)) {
                    moves.add(move);
                    break;
                }
            }
        }

        return moves.toArray(new Move[0]);
    }

    public static Move convertMove(String name) {
        for (Move move : AllMoves.allMoves) {
            if (formatName(move.getTrueName()).equals(name)) {
                return move;
            }
        }

        return null;
    }

    public static Item[] convertItemArray(String[] names) {
        if (names == null) {
            return new Item[0];
        }

        ArrayList<Item> items = new ArrayList<>();

        for (String name : names) {
            for (Item item : AllItems.allItems) {
                if (formatName(item.getName()).equals(name)) {
                    items.add(item);
                    break;
                }
            }
        }

        return items.toArray(new Item[0]);
    }

    public static StatusCondition[] convertStatusConditionArray(String[] names) {
        if (names == null) {
            return new StatusCondition[0];
        }

        ArrayList<StatusCondition> statusConditions = new ArrayList<>();

        for (String name : names) {
            for (StatusCondition statusCondition : AllStatusConditions.allStatusConditions) {
                if (formatName(statusCondition.getName()).equals(name)) {
                    statusConditions.add(statusCondition);
                    break;
                }
            }
        }

        return statusConditions.toArray(new StatusCondition[0]);
    }

    public static <T> List<T> convertObjectArray(String[] ids, Map<String, T> map) {
        ArrayList<T> convertedArray = new ArrayList<>();

        if (ids == null) {
            return convertedArray;
        }

        for (String id : ids) {
            if (map.containsKey(id)) {
                convertedArray.add(map.get(id));
            }
        }

        return convertedArray;
    }

    public static <T> T convertObject(String id, Map<String, T> map) {
        return map.get(id);
    }

    public static <E extends Enum<E>> List<E> convertEnumArray(String[] ids, Class<E> enumClass) {
        ArrayList<E> result = new ArrayList<>();

        if (ids == null) {
            return result;
        }

        for (String id : ids) {
            result.add(Enum.valueOf(enumClass, id));
        }

        return result;
    }

    public static <E extends Enum<E>> E convertEnum(String id, Class<E> enumClass) {
        return Enum.valueOf(enumClass, id);
    }

    public static String formatName(String name) {
        name = name.toLowerCase();
        name = name.replaceAll("[-: ]+", "_");
        name = name.replaceAll("[']+", "");

        return name;
    }
}
