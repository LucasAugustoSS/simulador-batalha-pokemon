package data.lists;
import java.util.ArrayList;

import data.classes.Pokemon;
import data.objects.PokemonList;

/**
 * Armazena todas as espécies de Pokémon (excluindo formas alternativas) em ArrayLists
 * correspondentes às gerações nas quais foram introduzidas.
 */

public class AllPokemon {
    public static final ArrayList<Pokemon> allPokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen1Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen2Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen3Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen4Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen5Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen6Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen7Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen8Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> gen9Pokemon = new ArrayList<>();
    public static final ArrayList<Pokemon> fakemon = new ArrayList<>();

    static {
        allPokemon.add(PokemonList.bulbasaur);
        allPokemon.add(PokemonList.ivysaur);
        allPokemon.add(PokemonList.venusaur);
        allPokemon.add(PokemonList.charmander);
        allPokemon.add(PokemonList.charmeleon);
        allPokemon.add(PokemonList.charizard);
        allPokemon.add(PokemonList.squirtle);
        allPokemon.add(PokemonList.wartortle);
        allPokemon.add(PokemonList.blastoise);
        allPokemon.add(PokemonList.caterpie);
        allPokemon.add(PokemonList.metapod);
        allPokemon.add(PokemonList.butterfree);
        allPokemon.add(PokemonList.articuno);
        allPokemon.add(PokemonList.zapdos);
        allPokemon.add(PokemonList.moltres);
        allPokemon.add(PokemonList.dratini);
        allPokemon.add(PokemonList.dragonair);
        allPokemon.add(PokemonList.dragonite);
        allPokemon.add(PokemonList.mewtwo);
        allPokemon.add(PokemonList.mew);

        allPokemon.add(PokemonList.chikorita);
        allPokemon.add(PokemonList.bayleef);
        allPokemon.add(PokemonList.meganium);
        allPokemon.add(PokemonList.cyndaquil);
        allPokemon.add(PokemonList.quilava);
        allPokemon.add(PokemonList.typhlosion);
        allPokemon.add(PokemonList.totodile);
        allPokemon.add(PokemonList.croconaw);
        allPokemon.add(PokemonList.feraligatr);
        allPokemon.add(PokemonList.togepi);
        allPokemon.add(PokemonList.togetic);
        allPokemon.add(PokemonList.raikou);
        allPokemon.add(PokemonList.entei);
        allPokemon.add(PokemonList.suicune);
        allPokemon.add(PokemonList.lugia);
        allPokemon.add(PokemonList.ho_oh);
        allPokemon.add(PokemonList.celebi);

        allPokemon.add(PokemonList.treecko);
        allPokemon.add(PokemonList.grovyle);
        allPokemon.add(PokemonList.sceptile);
        allPokemon.add(PokemonList.torchic);
        allPokemon.add(PokemonList.combusken);
        allPokemon.add(PokemonList.blaziken);
        allPokemon.add(PokemonList.mudkip);
        allPokemon.add(PokemonList.marshtomp);
        allPokemon.add(PokemonList.swampert);
        allPokemon.add(PokemonList.castform);
        allPokemon.add(PokemonList.regirock);
        allPokemon.add(PokemonList.regice);
        allPokemon.add(PokemonList.registeel);
        allPokemon.add(PokemonList.latias);
        allPokemon.add(PokemonList.latios);
        allPokemon.add(PokemonList.kyogre);
        allPokemon.add(PokemonList.groudon);
        allPokemon.add(PokemonList.rayquaza);
        allPokemon.add(PokemonList.jirachi);
        allPokemon.add(PokemonList.deoxys);

        allPokemon.add(PokemonList.turtwig);
        allPokemon.add(PokemonList.grotle);
        allPokemon.add(PokemonList.torterra);
        allPokemon.add(PokemonList.chimchar);
        allPokemon.add(PokemonList.monferno);
        allPokemon.add(PokemonList.infernape);
        allPokemon.add(PokemonList.piplup);
        allPokemon.add(PokemonList.prinplup);
        allPokemon.add(PokemonList.empoleon);
        allPokemon.add(PokemonList.riolu);
        allPokemon.add(PokemonList.lucario);
        allPokemon.add(PokemonList.togekiss);
        allPokemon.add(PokemonList.uxie);
        allPokemon.add(PokemonList.mesprit);
        allPokemon.add(PokemonList.azelf);
        allPokemon.add(PokemonList.dialga);
        allPokemon.add(PokemonList.palkia);
        allPokemon.add(PokemonList.heatran);
        allPokemon.add(PokemonList.regigigas);
        allPokemon.add(PokemonList.giratina);
        allPokemon.add(PokemonList.cresselia);
        allPokemon.add(PokemonList.phione);
        allPokemon.add(PokemonList.manaphy);
        allPokemon.add(PokemonList.darkrai);
        allPokemon.add(PokemonList.shaymin);
        allPokemon.add(PokemonList.arceus);

        allPokemon.add(PokemonList.victini);
        allPokemon.add(PokemonList.snivy);
        allPokemon.add(PokemonList.servine);
        allPokemon.add(PokemonList.serperior);
        allPokemon.add(PokemonList.tepig);
        allPokemon.add(PokemonList.pignite);
        allPokemon.add(PokemonList.emboar);
        allPokemon.add(PokemonList.oshawott);
        allPokemon.add(PokemonList.dewott);
        allPokemon.add(PokemonList.samurott);
        allPokemon.add(PokemonList.zorua);
        allPokemon.add(PokemonList.zoroark);
        allPokemon.add(PokemonList.cobalion);
        allPokemon.add(PokemonList.terrakion);
        allPokemon.add(PokemonList.virizion);
        allPokemon.add(PokemonList.tornadus);
        allPokemon.add(PokemonList.thundurus);
        allPokemon.add(PokemonList.reshiram);
        allPokemon.add(PokemonList.zekrom);
        allPokemon.add(PokemonList.landorus);
        allPokemon.add(PokemonList.kyurem);
        allPokemon.add(PokemonList.keldeo);
        allPokemon.add(PokemonList.meloetta);
        allPokemon.add(PokemonList.genesect);

        allPokemon.add(PokemonList.chespin);
        allPokemon.add(PokemonList.quilladin);
        allPokemon.add(PokemonList.chesnaught);
        allPokemon.add(PokemonList.fennekin);
        allPokemon.add(PokemonList.braixen);
        allPokemon.add(PokemonList.delphox);
        allPokemon.add(PokemonList.froakie);
        allPokemon.add(PokemonList.frogadier);
        allPokemon.add(PokemonList.greninja);
        allPokemon.add(PokemonList.hawlucha);
        allPokemon.add(PokemonList.xerneas);
        allPokemon.add(PokemonList.yveltal);
        allPokemon.add(PokemonList.zygarde);
        allPokemon.add(PokemonList.diancie);
        allPokemon.add(PokemonList.hoopa);
        allPokemon.add(PokemonList.volcanion);

        allPokemon.add(PokemonList.rowlet);
        allPokemon.add(PokemonList.dartrix);
        allPokemon.add(PokemonList.decidueye);
        allPokemon.add(PokemonList.litten);
        allPokemon.add(PokemonList.torracat);
        allPokemon.add(PokemonList.incineroar);
        allPokemon.add(PokemonList.popplio);
        allPokemon.add(PokemonList.brionne);
        allPokemon.add(PokemonList.primarina);
        allPokemon.add(PokemonList.grubbin);
        allPokemon.add(PokemonList.charjabug);
        allPokemon.add(PokemonList.vikavolt);
        allPokemon.add(PokemonList.type_null);
        allPokemon.add(PokemonList.silvally);
        allPokemon.add(PokemonList.tapu_koko);
        allPokemon.add(PokemonList.tapu_lele);
        allPokemon.add(PokemonList.tapu_bulu);
        allPokemon.add(PokemonList.tapu_fini);
        allPokemon.add(PokemonList.cosmog);
        allPokemon.add(PokemonList.cosmoem);
        allPokemon.add(PokemonList.solgaleo);
        allPokemon.add(PokemonList.lunala);
        allPokemon.add(PokemonList.necrozma);
        allPokemon.add(PokemonList.magearna);
        allPokemon.add(PokemonList.marshadow);
        allPokemon.add(PokemonList.zeraora);
        allPokemon.add(PokemonList.meltan);
        allPokemon.add(PokemonList.melmetal);

        allPokemon.add(PokemonList.grookey);
        allPokemon.add(PokemonList.thwackey);
        allPokemon.add(PokemonList.rillaboom);
        allPokemon.add(PokemonList.scorbunny);
        allPokemon.add(PokemonList.raboot);
        allPokemon.add(PokemonList.cinderace);
        allPokemon.add(PokemonList.sobble);
        allPokemon.add(PokemonList.drizzile);
        allPokemon.add(PokemonList.inteleon);
        allPokemon.add(PokemonList.indeedee);
        allPokemon.add(PokemonList.zacian);
        allPokemon.add(PokemonList.zamazenta);
        allPokemon.add(PokemonList.eternatus);
        allPokemon.add(PokemonList.kubfu);
        allPokemon.add(PokemonList.urshifu);
        allPokemon.add(PokemonList.zarude);
        allPokemon.add(PokemonList.regieleki);
        allPokemon.add(PokemonList.regidrago);
        allPokemon.add(PokemonList.glastrier);
        allPokemon.add(PokemonList.spectrier);
        allPokemon.add(PokemonList.calyrex);
        allPokemon.add(PokemonList.enamorus);

        allPokemon.add(PokemonList.sprigatito);
        allPokemon.add(PokemonList.floragato);
        allPokemon.add(PokemonList.meowscarada);
        allPokemon.add(PokemonList.fuecoco);
        allPokemon.add(PokemonList.crocalor);
        allPokemon.add(PokemonList.skeledirge);
        allPokemon.add(PokemonList.quaxly);
        allPokemon.add(PokemonList.quaxwell);
        allPokemon.add(PokemonList.quaquaval);
        allPokemon.add(PokemonList.finizen);
        allPokemon.add(PokemonList.palafin);
        allPokemon.add(PokemonList.wo_chien);
        allPokemon.add(PokemonList.chien_pao);
        allPokemon.add(PokemonList.ting_lu);
        allPokemon.add(PokemonList.chi_yu);
        allPokemon.add(PokemonList.koraidon);
        allPokemon.add(PokemonList.miraidon);
        allPokemon.add(PokemonList.okidogi);
        allPokemon.add(PokemonList.munkidori);
        allPokemon.add(PokemonList.fezandipiti);
        allPokemon.add(PokemonList.ogerpon);
        allPokemon.add(PokemonList.terapagos);
        allPokemon.add(PokemonList.pecharunt);

        allPokemon.add(PokemonList.chrysprout);
        allPokemon.add(PokemonList.chrysora);
        allPokemon.add(PokemonList.chrypuscule);
        allPokemon.add(PokemonList.blasnake);
        allPokemon.add(PokemonList.slinflame);
        allPokemon.add(PokemonList.boitabright);
        allPokemon.add(PokemonList.flowphin);
        allPokemon.add(PokemonList.cetaqua);
        allPokemon.add(PokemonList.botempest);



        for (Pokemon pokemon : allPokemon) {
            switch (pokemon.getGeneration()) {
                case 1:
                    gen1Pokemon.add(pokemon);
                    break;

                case 2:
                    gen2Pokemon.add(pokemon);
                    break;

                case 3:
                    gen3Pokemon.add(pokemon);
                    break;

                case 4:
                    gen4Pokemon.add(pokemon);
                    break;

                case 5:
                    gen5Pokemon.add(pokemon);
                    break;

                case 6:
                    gen6Pokemon.add(pokemon);
                    break;

                case 7:
                    gen7Pokemon.add(pokemon);
                    break;

                case 8:
                    gen8Pokemon.add(pokemon);
                    break;

                case 9:
                    gen9Pokemon.add(pokemon);
                    break;

                case -1:
                    fakemon.add(pokemon);
                    break;

                default:
                    break;
            }
        }
    }
}
