package data.objects;

import data.classes.Nature;
import data.classes.Stat;

public class NatureList {
    public static final Nature hardy = new Nature(
        "Hardy",
        Stat.atk,
        Stat.atk
    );
    public static final Nature lonely = new Nature(
        "Lonely",
        Stat.atk,
        Stat.def
    );
    public static final Nature brave = new Nature(
        "Brave",
        Stat.atk,
        Stat.spe
    );
    public static final Nature adamant = new Nature(
        "Adamant",
        Stat.atk,
        Stat.spa
    );
    public static final Nature naughty = new Nature(
        "Naughty",
        Stat.atk,
        Stat.spd
    );

    public static final Nature bold = new Nature(
        "Bold",
        Stat.def,
        Stat.atk
    );
    public static final Nature docile = new Nature(
        "Docile",
        Stat.def,
        Stat.def
    );
    public static final Nature relaxed = new Nature(
        "Relaxed",
        Stat.def,
        Stat.spe
    );
    public static final Nature impish = new Nature(
        "Impish",
        Stat.def,
        Stat.spa
    );
    public static final Nature lax = new Nature(
        "Lax",
        Stat.def,
        Stat.spd
    );

    public static final Nature timid = new Nature(
        "Timid",
        Stat.spe,
        Stat.atk
    );
    public static final Nature hasty = new Nature(
        "Hasty",
        Stat.spe,
        Stat.def
    );
    public static final Nature serious = new Nature(
        "Serious",
        Stat.spe,
        Stat.spe
    );
    public static final Nature jolly = new Nature(
        "Jolly",
        Stat.spe,
        Stat.spa
    );
    public static final Nature naive = new Nature(
        "Naive",
        Stat.spe,
        Stat.spd
    );

    public static final Nature modest = new Nature(
        "Modest",
        Stat.spa,
        Stat.atk
    );
    public static final Nature mild = new Nature(
        "Mild",
        Stat.spa,
        Stat.def
    );
    public static final Nature quiet = new Nature(
        "Quiet",
        Stat.spa,
        Stat.spe
    );
    public static final Nature bashful = new Nature(
        "Bashful",
        Stat.spa,
        Stat.spa
    );
    public static final Nature rash = new Nature(
        "Rash",
        Stat.spa,
        Stat.spd
    );

    public static final Nature calm = new Nature(
        "Calm",
        Stat.spd,
        Stat.atk
    );
    public static final Nature gentle = new Nature(
        "Gentle",
        Stat.spd,
        Stat.def
    );
    public static final Nature sassy = new Nature(
        "Sassy",
        Stat.spd,
        Stat.spe
    );
    public static final Nature careful = new Nature(
        "Careful",
        Stat.spd,
        Stat.spa
    );
    public static final Nature quirky = new Nature(
        "Quirky",
        Stat.spd,
        Stat.spd
    );
}
