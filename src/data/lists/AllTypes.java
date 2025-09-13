package data.lists;

import java.util.ArrayList;

import data.classes.Type;
import data.objects.TypeList;

public class AllTypes {
    public static final ArrayList<Type> allTypes = new ArrayList<>();

    static {
        allTypes.add(TypeList.normal);
        allTypes.add(TypeList.fighting);
        allTypes.add(TypeList.flying);
        allTypes.add(TypeList.poison);
        allTypes.add(TypeList.ground);
        allTypes.add(TypeList.rock);
        allTypes.add(TypeList.bug);
        allTypes.add(TypeList.ghost);
        allTypes.add(TypeList.steel);
        allTypes.add(TypeList.fire);
        allTypes.add(TypeList.water);
        allTypes.add(TypeList.grass);
        allTypes.add(TypeList.electric);
        allTypes.add(TypeList.psychic);
        allTypes.add(TypeList.ice);
        allTypes.add(TypeList.dragon);
        allTypes.add(TypeList.dark);
        allTypes.add(TypeList.fairy);
        allTypes.add(TypeList.stellar);
        allTypes.add(TypeList.typeless);
    }
}
