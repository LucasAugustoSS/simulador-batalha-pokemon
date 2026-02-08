package com.github.lucasaugustoss.data.objects.oldObjects;

import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;

public class TypeList {
    public static final Type normal = new Type("Normal");
    public static final Type fighting = new Type("Fighting");
    public static final Type flying = new Type("Flying");
    public static final Type poison = new Type("Poison");
    public static final Type ground = new Type("Ground");
    public static final Type rock = new Type("Rock");
    public static final Type bug = new Type("Bug");
    public static final Type ghost = new Type("Ghost");
    public static final Type steel = new Type("Steel");
    public static final Type fire = new Type("Fire");
    public static final Type water = new Type("Water");
    public static final Type grass = new Type("Grass");
    public static final Type electric = new Type("Electric");
    public static final Type psychic = new Type("Psychic");
    public static final Type ice = new Type("Ice");
    public static final Type dragon = new Type("Dragon");
    public static final Type dark = new Type("Dark");
    public static final Type fairy = new Type("Fairy");
    public static final Type stellar = new Type("Stellar");
    public static final Type typeless = new Type("???");

    static {
        normal.setSuperEffective(new Type[] {fighting});
        normal.setNotVeryEffective(new Type[0]);
        normal.setIneffective(new Type[] {ghost});
        normal.setAdditionalImmunities(new Object[0]);

        fighting.setSuperEffective(new Type[] {flying, psychic, fairy});
        fighting.setNotVeryEffective(new Type[] {rock, bug, dark});
        fighting.setIneffective(new Type[0]);
        fighting.setAdditionalImmunities(new Object[0]);

        flying.setSuperEffective(new Type[] {rock, electric, ice});
        flying.setNotVeryEffective(new Type[] {fighting, bug, grass});
        flying.setIneffective(new Type[] {ground});
        flying.setAdditionalImmunities(new Object[0]);

        poison.setSuperEffective(new Type[] {ground, psychic});
        poison.setNotVeryEffective(new Type[] {fighting, poison, bug, grass, fairy});
        poison.setIneffective(new Type[0]);
        poison.setAdditionalImmunities(new Object[] {StatusConditionList.poison, StatusConditionList.bad_poison});

        ground.setSuperEffective(new Type[] {water, grass, ice});
        ground.setNotVeryEffective(new Type[] {poison, rock});
        ground.setIneffective(new Type[] {electric});
        ground.setAdditionalImmunities(new Object[0]);

        rock.setSuperEffective(new Type[] {fighting, ground, steel, water, grass});
        rock.setNotVeryEffective(new Type[] {normal, flying, poison, fire});
        rock.setIneffective(new Type[0]);
        rock.setAdditionalImmunities(new Object[0]);

        bug.setSuperEffective(new Type[] {flying, rock, fire});
        bug.setNotVeryEffective(new Type[] {fighting, ground, grass});
        bug.setIneffective(new Type[0]);
        bug.setAdditionalImmunities(new Object[0]);

        ghost.setSuperEffective(new Type[] {ghost, dark});
        ghost.setNotVeryEffective(new Type[] {poison, bug});
        ghost.setIneffective(new Type[] {normal, fighting});
        ghost.setAdditionalImmunities(new Object[] {StatusConditionList.trapped});

        steel.setSuperEffective(new Type[] {fighting, ground, fire});
        steel.setNotVeryEffective(new Type[] {normal, flying, rock, bug, steel, grass, psychic, ice, dragon, fairy});
        steel.setIneffective(new Type[] {poison});
        steel.setAdditionalImmunities(new Object[] {StatusConditionList.poison, StatusConditionList.bad_poison});

        fire.setSuperEffective(new Type[] {ground, rock, water});
        fire.setNotVeryEffective(new Type[] {bug, steel, fire, grass, ice, fairy});
        fire.setIneffective(new Type[0]);
        fire.setAdditionalImmunities(new Object[] {StatusConditionList.burn});

        water.setSuperEffective(new Type[] {grass, electric});
        water.setNotVeryEffective(new Type[] {steel, fire, water, ice});
        water.setIneffective(new Type[0]);
        water.setAdditionalImmunities(new Object[0]);

        grass.setSuperEffective(new Type[] {flying, poison, bug, fire, ice});
        grass.setNotVeryEffective(new Type[] {ground, water, grass, electric});
        grass.setIneffective(new Type[0]);
        grass.setAdditionalImmunities(new Object[] {StatusConditionList.seed, MoveType.Powder});

        electric.setSuperEffective(new Type[] {ground});
        electric.setNotVeryEffective(new Type[] {flying, steel, electric});
        electric.setIneffective(new Type[0]);
        electric.setAdditionalImmunities(new Object[] {StatusConditionList.paralysis});

        psychic.setSuperEffective(new Type[] {bug, ghost, dark});
        psychic.setNotVeryEffective(new Type[] {fighting, psychic});
        psychic.setIneffective(new Type[0]);
        psychic.setAdditionalImmunities(new Object[0]);

        ice.setSuperEffective(new Type[] {fighting, rock, steel, fire});
        ice.setNotVeryEffective(new Type[] {ice});
        ice.setIneffective(new Type[0]);
        ice.setAdditionalImmunities(new Object[] {StatusConditionList.freeze, StatusConditionList.frostbite});

        dragon.setSuperEffective(new Type[] {ice, dragon, fairy});
        dragon.setNotVeryEffective(new Type[] {fire, water, grass, electric});
        dragon.setIneffective(new Type[0]);
        dragon.setAdditionalImmunities(new Object[0]);

        dark.setSuperEffective(new Type[] {fighting, bug, fairy});
        dark.setNotVeryEffective(new Type[] {ghost, dark});
        dark.setIneffective(new Type[] {psychic});
        dark.setAdditionalImmunities(new Object[] {TemporaryProperty.PranksterBoosted});

        fairy.setSuperEffective(new Type[] {poison, steel});
        fairy.setNotVeryEffective(new Type[] {fighting, bug, dark});
        fairy.setIneffective(new Type[] {dragon});
        fairy.setAdditionalImmunities(new Object[0]);

        stellar.setSuperEffective(new Type[0]);
        stellar.setNotVeryEffective(new Type[0]);
        stellar.setIneffective(new Type[0]);
        stellar.setAdditionalImmunities(new Object[0]);

        typeless.setSuperEffective(new Type[0]);
        typeless.setNotVeryEffective(new Type[0]);
        typeless.setIneffective(new Type[0]);
        typeless.setAdditionalImmunities(new Object[0]);
    }
}
