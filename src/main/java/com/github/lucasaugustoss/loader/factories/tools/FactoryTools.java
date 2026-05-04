package com.github.lucasaugustoss.loader.factories.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.data.lists.AllMessages;
import com.github.lucasaugustoss.data.messages.Message;

public class FactoryTools {
    public static Message convertMessage(String name) {
        for (Message message : AllMessages.allMessages) {
            if (formatName(message.getName()).equals(name)) {
                return message;
            }
        }

        return null;
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
        if (id == null) {
            return null;
        }

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
        if (id == null) {
            return null;
        }

        return Enum.valueOf(enumClass, id);
    }

    public static String formatName(String name) {
        name = name.toLowerCase();
        name = name.replaceAll("[-: ]+", "_");
        name = name.replaceAll("[']+", "");

        return name;
    }

    public static double convertFraction(String fraction) {
        if (fraction == null) {
            return 0;
        }

        String[] fractionValues = fraction.split("/");
        double fractionNum = Integer.parseInt(fractionValues[0]);
        double fractionDen = fractionValues.length > 1 ? Integer.parseInt(fractionValues[1]) : 1;
        return fractionNum/fractionDen;
    }

    public static double[] convertFractionArray(String[] fractions) {
        ArrayList<Double> result = new ArrayList<>();

        for (String fraction : fractions) {
            String[] fractionValues = fraction.split("/");
            double fractionNum = Integer.parseInt(fractionValues[0]);
            double fractionDen = fractionValues.length > 1 ? Integer.parseInt(fractionValues[1]) : 1;
            result.add(fractionNum/fractionDen);
        }

        return result.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
