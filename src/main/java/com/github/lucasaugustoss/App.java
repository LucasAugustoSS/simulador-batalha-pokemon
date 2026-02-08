package com.github.lucasaugustoss;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.lucasaugustoss.data.classes.Ability;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Nature;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.lists.AllItems;
import com.github.lucasaugustoss.data.lists.AllNatures;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.oldObjects.ItemList;
import com.github.lucasaugustoss.data.objects.oldObjects.TypeList;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.Battle;

public class App {
    private static final Scanner sc = new Scanner(System.in);

    public static Pokemon[] playerTeam = new Pokemon[6];
    public static Pokemon[] opposingTeam = new Pokemon[6];

    public static Pokemon playerPokemon = null;
    public static Pokemon opposingPokemon = null;

    public static boolean battleStarted = false;

    public static boolean debug = false;

    public static void main(String[] args) throws Exception {
        Data.get();

        System.out.println("- Your Team -\n");

        playerTeam = teamBuilder(0);

        System.out.println("\n-------------------------\n");

        System.out.println("Opponent's Team:\n");

        opposingTeam = teamBuilder(1);

        System.out.println("\n-------------------------\n");
        System.out.println("- Your Pokémon -");
        System.out.println("\n-------------------------\n");
        for (Pokemon pokemon : playerTeam) {
            if (pokemon != null) {
                pokemonBuilder(pokemon);
            }
        }
        System.out.println("\n-------------------------\n");
        System.out.println("\n-------------------------\n");
        System.out.println("- Opponent's Pokémon -");
        System.out.println("\n-------------------------\n");
        for (Pokemon pokemon : opposingTeam) {
            if (pokemon != null) {
                pokemonBuilder(pokemon);
            }
        }

        for (Pokemon pokemon : playerTeam) {
            if (pokemon != null) {
                pokemon.setDefaultValues();
            }
        }
        for (Pokemon pokemon : opposingTeam) {
            if (pokemon != null) {
                pokemon.setDefaultValues();
            }
        }

        System.out.println("\n");

        battleStarted = true;
        Battle.start(new Pokemon[][] {playerTeam, opposingTeam});

        sc.close();
    }

    private static Pokemon[] teamBuilder(int teamNumber) {
        int option;
        int slot = 0;
        ArrayList<PokemonTemplate> list = null;

        Pokemon[] team = new Pokemon[6];

        do {
            System.out.println("0. All Pokémon");
            System.out.println("1. Generation 1");
            System.out.println("2. Generation 2");
            System.out.println("3. Generation 3");
            System.out.println("4. Generation 4");
            System.out.println("5. Generation 5");
            System.out.println("6. Generation 6");
            System.out.println("7. Generation 7");
            System.out.println("8. Generation 8");
            System.out.println("9. Generation 9");
            System.out.println("10. Fakémon");

            do {
                System.out.println("\nGeneration:");
                option = readOption("> ");

                switch (option) {
                    case 0:
                        list = new ArrayList<>(Data.get().getSelectablePokemonList());
                        break;

                    case 1:
                        list = pokemonByGeneration(1);
                        break;

                    case 2:
                        list = pokemonByGeneration(2);
                        break;

                    case 3:
                        list = pokemonByGeneration(3);
                        break;

                    case 4:
                        list = pokemonByGeneration(4);
                        break;

                    case 5:
                        list = pokemonByGeneration(5);
                        break;

                    case 6:
                        list = pokemonByGeneration(6);
                        break;

                    case 7:
                        list = pokemonByGeneration(7);
                        break;

                    case 8:
                        list = pokemonByGeneration(8);
                        break;

                    case 9:
                        list = pokemonByGeneration(9);
                        break;

                    case 10:
                        list = pokemonByGeneration(-1);
                        break;

                    default:
                        System.out.println("!- There is no option with this number -!");
                        break;
                }
            } while (option < 0 || option > 10);

            System.out.println("\n");

            for (int i = 1; i <= list.size(); i++) {
                System.out.println(i + "- " + list.get(i-1).getName());
            }

            do {
                System.out.println("\nTeam:");
                for (int i = 1; i <= team.length; i++) {
                    System.out.println(i + ". " + (team[i-1] != null ? team[i-1].getTrueName(false, false) : ""));
                }
                System.out.println(
                    "\n- Insert a maximum of 6 Pokémon -\n" +
                    "(0 to finish, -1 to remove a Pokémon, -2 to return to generation selection)\n"
                );

                do {
                    option = readOption("> ");

                    if (option >= 1 && option <= list.size()) {
                        if (team[5] == null) {
                            team[slot] = new Pokemon(list.get(option-1), teamNumber);
                        } else {
                            do {
                                slot = readOption("\nSlot (1-6) (0 to cancel): ");

                                if (slot >= 1 && slot <= 6) {
                                    team[slot-1] = new Pokemon(list.get(option-1), teamNumber);
                                } else if (slot != 0) {
                                    System.out.println("!- There is no slot with this index -!\n");
                                }
                            } while (slot < 0 || slot > 6);
                        }
                    } else if (option == -1) {
                        int count = 0;
                        int removalSlot;
                        for (Pokemon pokemon : team) {
                            if (pokemon != null) {
                                if (count == 0) {
                                    System.out.println("\n");
                                }
                                count++;
                                System.out.println(count + ". " + pokemon.getName(false, false));
                            }
                        }

                        if (count > 0) {
                            System.out.println();
                            do {
                                removalSlot = readOption("\nPokémon to remove (0 to cancel): ");

                                if (removalSlot < 0 || removalSlot > count) {
                                    System.out.println("!- There is no Pokémon with this index -!\n");
                                }
                            } while (removalSlot < 0 || removalSlot > count);
                        } else {
                            System.out.println("!- There are no Pokémon to remove -!");
                            removalSlot = -1;
                        }

                        if (removalSlot > 0) {
                            team[removalSlot-1] = null;

                            Pokemon[] reorderedTeam = new Pokemon[6];
                            int reorderedCount = 0;
                            for (Pokemon pokemon : team) {
                                if (pokemon != null) {
                                    reorderedTeam[reorderedCount] = pokemon;
                                    reorderedCount++;
                                }
                            }
                            team = reorderedTeam;
                        }
                    } else if (option != -2 && option != 0) {
                        System.out.println("!- There is no Pokémon with this index -!\n");
                    }
                } while (option < -2 || option > list.size());

                if (option == 0) {
                    if (team[0] == null) {
                        System.out.println("!- Your team is empty -!");
                    } else {
                        return team;
                    }
                } else if (option == -1) {
                    slot = 0;
                    for (Pokemon pokemon : team) {
                        if (pokemon != null) {
                            System.out.println(pokemon.getName(false, false));
                            slot++;
                        }
                    }
                } else if (option != -2) {
                    if (team[5] == null) {
                        slot++;
                    }
                } else {
                    System.out.println();
                }
                System.out.println();
            } while (option != -2);
        } while (true);
    }

    private static void pokemonBuilder(Pokemon pokemon) {
        int option;
        boolean canConfirm;

        do {
            canConfirm = true;

            System.out.println("\n-------------------------\n");
            System.out.println("- Build the Pokémon -\n");
            System.out.println(pokemon.getTrueNameAndForm(false, false));
            System.out.print("Type: " + pokemon.getType(0).getName());
            if (!pokemon.getType(1).compare(TypeList.typeless)) {
                System.out.print("/" + pokemon.getType(1).getName());
            }
            System.out.println("\n");

            System.out.println("1. Level");
            System.out.println("- " + pokemon.getLevel() + "\n");

            System.out.println("2. Gender");
            System.out.println("- " + pokemon.getGender() + "\n");

            System.out.println("3. Form");
            System.out.println("- " + ((pokemon.getForm() != null && !pokemon.getForm().isBlank()) ? pokemon.getForm() : "Normal") + "\n");

            System.out.println("4. Ability");
            System.out.println("- " + pokemon.getAbility().getName() + "\n");

            System.out.println("5. Item");
            System.out.println("- " + pokemon.getItem().getName() + "\n");

            System.out.println("6. Moves");
            System.out.println("- " + (pokemon.getTrueMoves()[0] != null ? pokemon.getTrueMoves()[0].getTrueName() : "[None]"));
            System.out.println("- " + (pokemon.getTrueMoves()[1] != null ? pokemon.getTrueMoves()[1].getTrueName() : "[None]"));
            System.out.println("- " + (pokemon.getTrueMoves()[2] != null ? pokemon.getTrueMoves()[2].getTrueName() : "[None]"));
            System.out.println("- " + (pokemon.getTrueMoves()[3] != null ? pokemon.getTrueMoves()[3].getTrueName() : "[None]") + "\n");

            System.out.println("7. IVs");
            System.out.println("- " +
                pokemon.getIV(StatName.HP) + " HP | " +
                pokemon.getIV(StatName.Atk) + " Attack | " +
                pokemon.getIV(StatName.Def) + " Defense | " +
                pokemon.getIV(StatName.SpA) + " Sp. Atk | " +
                pokemon.getIV(StatName.SpD) + " Sp. Def | " +
                pokemon.getIV(StatName.Spe) + " Speed\n"
            );

            System.out.println("8. EVs");
            System.out.println("- " +
                pokemon.getEV(StatName.HP) + " HP | " +
                pokemon.getEV(StatName.Atk) + " Attack | " +
                pokemon.getEV(StatName.Def) + " Defense | " +
                pokemon.getEV(StatName.SpA) + " Sp. Atk | " +
                pokemon.getEV(StatName.SpD) + " Sp. Def | " +
                pokemon.getEV(StatName.Spe) + " Speed\n"
            );

            System.out.println("9. Nature");
            System.out.println("- " + pokemon.getNature().getName() + "\n");

            System.out.println("0. Confirm");

            System.out.println("\n.  .  .  .  .  .  .  .  .\n");

            option = readOption("> ");

            int count;

            switch (option) {
                case 1:
                    int level;

                    do {
                        level = readOption("\n" + pokemon.getTrueName(false, false) + "'s level (0 to cancel): ");
                        if (level < 0 || level > 100) {
                            System.out.println("!- A Pokémon's level must be between 1 and 100 -!\n");
                        }
                    } while (level < 0 || level > 100);

                    if (level != 0) {
                        pokemon.setLevel(level);
                    }
                    break;

                case 2:
                    if (pokemon.getGenderRatio()[0] == 100 ||
                        pokemon.getGenderRatio()[1] == 100 ||
                        pokemon.getGender().equals("Unknown")) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + "'s gender can only be " + pokemon.getGender() + " -!");
                    } else {
                        int gender;

                        System.out.println("\n" + pokemon.getTrueName(false, false) + "'s possible genders:\n");
                        System.out.println("1- Male");
                        System.out.println("2- Female");

                        do {
                            gender = readOption("\nGender (0 to cancel): ");
                            if (gender < 0 || gender > 2) {
                                System.out.println("!- There is no gender with this index -!\n");
                            }
                        } while (gender < 0 || gender > 2);

                        if (gender != 0) {
                            pokemon.setGender(gender);
                        }
                    }
                    break;

                case 3:
                    boolean onlyChangeableForm = true;
                    for (PokemonTemplate form : pokemon.getForms()) {
                        if (!form.formChangeIsInBattle() &&
                            !form.compareWithForm(pokemon)) {
                            onlyChangeableForm = false;
                            break;
                        }
                    }

                    if (pokemon.getForms().length == 0) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " has no alternate forms -!");
                    } else if (onlyChangeableForm) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " can only change form in battle -!");
                    } else {
                        count = 0;
                        System.out.println("\n" + pokemon.getTrueName(false, false) + "'s forms:\n");

                        ArrayList<PokemonTemplate> forms = new ArrayList<>();
                        for (PokemonTemplate form : pokemon.getForms()) {
                            if (!form.formChangeIsInBattle()) {
                                count++;
                                System.out.println(count + ". " + form.getForm());
                                forms.add(form);
                            }
                        }

                        int chosenForm;

                        do {
                            chosenForm = readOption("\nForm (0 to cancel): ");
                            if (chosenForm < 0 || chosenForm > count) {
                                System.out.println("!- There is no form with this index -!\n");
                            }
                        } while (chosenForm < 0 || chosenForm > count);

                        if (chosenForm != 0) {
                            boolean keepItem = false;
                            if (pokemon.getItemsNeededForForm().length == 0 &&
                                forms.get(chosenForm-1).getItemsNeededForForm().length == 0) {
                                keepItem = true;
                            }
                            for (Item item : forms.get(chosenForm-1).getItemsNeededForForm()) {
                                if (pokemon.needsItemForForm(item)) {
                                    keepItem = true;
                                    break;
                                }
                            }
                            if (pokemon.getItem() != null && !keepItem) {
                                pokemon.setItem(new Item(ItemList.none, pokemon));
                            }

                            if (pokemon.getMoveNeededForForm() != forms.get(chosenForm-1).getMoveNeededForForm()) {
                                for (int i = 0; i < pokemon.getTrueMoves().length; i++) {
                                    Move move = pokemon.getTrueMoves()[i];
                                    if (move != null &&
                                        pokemon.getMoveNeededForForm() != null &&
                                        pokemon.getMoveNeededForForm().compareTrue(move)) {
                                        pokemon.setMove(null, i);
                                        break;
                                    }
                                }
                            }

                            boolean cantMoveSlotsFull = true;
                            for (Move move : pokemon.getTrueMoves()) {
                                if (move == null) {
                                    cantMoveSlotsFull = false;
                                    break;
                                }
                            }
                            if (cantMoveSlotsFull) {
                                System.out.println("!- " + pokemon.getName(false, false) + " needs " + forms.get(chosenForm-1).getMoveNeededForForm().getName() + " to change form, but it already has 4 moves -!\n");
                                break;
                            }

                            pokemon.changeForm(forms.get(chosenForm-1).getForm());

                            if (pokemon.getItemsNeededForForm().length != 0) {
                                pokemon.setItem(new Item(pokemon.getItemsNeededForForm()[0], pokemon));
                            }

                            if (pokemon.getGenderRatio()[0] == 100) {
                                pokemon.setGender(1);
                            } else if (pokemon.getGenderRatio()[1] == 100) {
                                pokemon.setGender(2);
                            }

                            Move[] newMoveList = new Move[pokemon.getTrueMoves().length];
                            int j = 0;
                            for (Move move : pokemon.getTrueMoves()) {
                                if (move != null) {
                                    for (Move learnableMove : pokemon.getMoveList()) {
                                        if (move.compareTrue(learnableMove)) {
                                            newMoveList[j] = move;
                                            j++;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (pokemon.getMoveNeededForForm() != null) {
                                for (int i = 0; i < newMoveList.length; i++) {
                                    Move move = newMoveList[i];

                                    if (move == null) {
                                        newMoveList[i] = new Move(pokemon.getMoveNeededForForm(), pokemon);
                                        break;
                                    }
                                }
                            }
                            pokemon.setMoves(newMoveList);
                        }
                    }
                    break;

                case 4:
                    count = 0;
                    System.out.println("\n" + pokemon.getTrueName(false, false) + "'s abilities:\n");
                    for (Ability ability : pokemon.getAbilityList()) {
                        count++;
                        System.out.println(count + ". " + ability.getName());
                    }

                    int chosenAbility;

                    do {
                        chosenAbility = readOption("\nAbility (0 to cancel): ");
                        if (chosenAbility < 0 || chosenAbility > count) {
                            System.out.println("!- There is no ability with this index -!\n");
                        }
                    } while (chosenAbility < 0 || chosenAbility > count);

                    if (chosenAbility != 0) {
                        pokemon.setAbility(pokemon.getAbilityList()[chosenAbility-1], false, null);
                    }
                    break;

                case 5:
                    count = 0;
                    System.out.println("\nHeld items:\n");
                    for (Item item : AllItems.allItems) {
                        count++;
                        System.out.println(count + ". " + item.getName());
                    }

                    int chosenItem;

                    do {
                        chosenItem = readOption("\nItem (0 to cancel) (-1 to remove): ");
                        if (chosenItem < -1 || chosenItem > count) {
                            System.out.println("!- There is no item with this index -!\n");
                        }
                    } while (chosenItem < -1 || chosenItem > count);

                    if (chosenItem > 0) {
                        pokemon.setItem(new Item(AllItems.allItems.get(chosenItem-1), pokemon));

                        for (PokemonTemplate form : pokemon.getForms()) {
                            if (!form.formChangeIsInBattle() &&
                                form.needsItemForForm(AllItems.allItems.get(chosenItem-1)) &&
                                !form.compareWithForm(pokemon)) {
                                pokemon.changeForm(form.getForm());
                                System.out.println("\n- " + pokemon.getName(false, false) + "'s form changed to " + pokemon.getForm() + " -");
                                break;
                            }
                        }

                        if (pokemon.getItemsNeededForForm().length != 0 &&
                            !pokemon.needsItemForForm(AllItems.allItems.get(chosenItem-1))) {
                            pokemon.changeForm(pokemon.getBaseForm().getForm());
                            System.out.println("\n- " + pokemon.getName(false, false) + "'s form changed to " + pokemon.getForm() + " -");
                        }
                    } else if (chosenItem == -1) {
                        if (!pokemon.getItem().compare(ItemList.none)) {
                            pokemon.setItem(new Item(ItemList.none, pokemon));

                            if (!pokemon.formChangeIsInBattle()) {
                                if (pokemon.getItemsNeededForForm().length != 0) {
                                    pokemon.changeForm(pokemon.getBaseForm().getForm());
                                    System.out.println("\n- " + pokemon.getName(false, false) + "'s form changed to " + pokemon.getForm() + " -");
                                }
                            }
                        } else {
                            System.out.println("!- " + pokemon.getTrueName(false, false) + " has no item to remove -!");
                        }
                    }
                    break;

                case 6:
                    count = 0;
                    System.out.println("\n" + pokemon.getTrueName(false, false) + "'s moves:\n");
                    for (Move move : pokemon.getMoveList()) {
                        count++;
                        System.out.println(count + ". " + move.getTrueName());
                        System.out.println("Type: " + move.getTrueType().getName());
                        System.out.println("Category: " + move.getTrueCategory());
                        System.out.print("Power: " );
                        if (move.getPower(true, true, 0) != 0) {
                            System.out.println((int) Math.floor(move.getPower(true, true, 0)));
                        } else {
                            System.out.println("-");
                        }
                        System.out.print("Accuracy: ");
                        if (move.getAccuracy() > 0) {
                            System.out.println(move.getAccuracy());
                        } else {
                            System.out.println("-");
                        }
                        System.out.println("PP: " + move.getPP() + "\n");
                    }

                    int chosenMove;
                    boolean moveAlreadyChosen;

                    do {
                        chosenMove = readOption("\nMove (0 to cancel) (-1 to remove): ");
                        moveAlreadyChosen = false;

                        if (chosenMove < -1 || chosenMove > count) {
                            System.out.println("!- There is no move with this index -!\n");
                        } else if (chosenMove > 0) {
                            for (Move move : pokemon.getTrueMoves()) {
                                if (move != null &&
                                    move.compareTrue(pokemon.getMoveList()[chosenMove-1])) {
                                    System.out.println("!- " + pokemon.getTrueName(false, false) + " already knows this move -!\n");
                                    moveAlreadyChosen = true;
                                }
                            }
                        }
                    } while (chosenMove < -1 || chosenMove > count || moveAlreadyChosen);

                    if (chosenMove != 0) {
                        int slot = 1;

                        if (chosenMove != -1) {
                            if (pokemon.getTrueMoves()[3] == null) {
                                while (pokemon.getTrueMoves()[slot-1] != null) {
                                    slot++;
                                }
                            } else {
                                do {
                                    slot = readOption("\nSlot (1-4) (0 to cancel): ");
                                    if (slot < 0 || slot > 4) {
                                        System.out.println("!- The chosen move slot must be between 1 and 4 -!\n");
                                    }
                                } while (slot < 0 || slot > 4);
                            }
                        } else {
                            count = 0;
                            for (Move move : pokemon.getTrueMoves()) {
                                if (move != null) {
                                    if (count == 0) {
                                        System.out.println("\n");
                                    }
                                    count++;
                                    System.out.println(count + ". " + move.getTrueName());
                                }
                            }

                            if (count > 0) {
                                System.out.println();
                                do {
                                    slot = readOption("\nMove to remove (0 to cancel): ");

                                    if (slot < 0 || slot > count) {
                                        System.out.println("!- There is no move with this index -!\n");
                                    }
                                } while (slot < 0 || slot > count);
                            } else {
                                System.out.println("!- " + pokemon.getTrueName(false, false) + " has no moves to remove -!");
                                slot = 0;
                            }
                        }

                        if (slot != 0) {
                            if (chosenMove != -1) {
                                pokemon.setMove(new Move(pokemon.getMoveList()[chosenMove-1], pokemon), slot-1);

                                for (PokemonTemplate form : pokemon.getForms()) {
                                    if (!form.formChangeIsInBattle() &&
                                        form.getMoveNeededForForm() != null &&
                                        form.getMoveNeededForForm().compareTrue(pokemon.getMoveList()[chosenMove-1])) {
                                        pokemon.changeForm(form.getForm());
                                        System.out.println("\n- " + pokemon.getName(false, false) + "'s form changed to " + pokemon.getForm() + " -");
                                        break;
                                    }
                                }

                                if (pokemon.getMoveNeededForForm() != null) {
                                    boolean neededMoveRemoved = true;
                                    for (Move move : pokemon.getTrueMoves()) {
                                        if (move != null && move.compareTrue(pokemon.getMoveNeededForForm())) {
                                            neededMoveRemoved = false;
                                        }
                                    }
                                    if (neededMoveRemoved) {
                                        pokemon.changeForm(pokemon.getBaseForm().getForm());
                                        System.out.println("\n- " + pokemon.getName(false, false) + "'s form changed to " + pokemon.getForm() + " -");
                                    }
                                }
                            } else {
                                if (pokemon.getMoveNeededForForm() != null &&
                                    pokemon.getMoveNeededForForm().compareTrue(pokemon.getTrueMoves()[slot-1])) {
                                    pokemon.changeForm(pokemon.getBaseForm().getForm());
                                    System.out.println("\n- " + pokemon.getName(false, false) + "'s form changed to " + pokemon.getForm() + " -");
                                }

                                pokemon.setMove(null, slot-1);
                                Move[] reorderedMoves = new Move[4];
                                int reorderedCount = 0;
                                for (Move move : pokemon.getTrueMoves()) {
                                    if (move != null) {
                                        reorderedMoves[reorderedCount] = move;
                                        reorderedCount++;
                                    }
                                }
                                pokemon.setMoves(reorderedMoves);
                            }
                        }
                    }
                    break;

                case 7:
                    System.out.println("\n1. HP");
                    System.out.println("2. Attack");
                    System.out.println("3. Defense");
                    System.out.println("4. Special Attack");
                    System.out.println("5. Special Defense");
                    System.out.println("6. Speed");

                    int ivStat = 0;
                    do {
                        ivStat = readOption("\nStat (0 to cancel): ");

                        if (ivStat < 0 || ivStat > 6) {
                            System.out.println("!- There is no stat with this index -!\n");
                        }
                    } while (ivStat < 0 || ivStat > 6);

                    if (ivStat != 0) {
                        int ivValue = readOption("\nValue: ");

                        switch (ivStat) {
                            case 1:
                                pokemon.setIV(StatName.HP, ivValue);
                                break;

                            case 2:
                                pokemon.setIV(StatName.Atk, ivValue);
                                break;

                            case 3:
                                pokemon.setIV(StatName.Def, ivValue);
                                break;

                            case 4:
                                pokemon.setIV(StatName.SpA, ivValue);
                                break;

                            case 5:
                                pokemon.setIV(StatName.SpD, ivValue);
                                break;

                            case 6:
                                pokemon.setIV(StatName.Spe, ivValue);
                                break;

                            default:
                                break;
                        }
                    }

                    break;

                case 8:
                    System.out.println("\n1. HP");
                    System.out.println("2. Attack");
                    System.out.println("3. Defense");
                    System.out.println("4. Special Attack");
                    System.out.println("5. Special Defense");
                    System.out.println("6. Speed");

                    int evStat = 0;
                    do {
                        evStat = readOption("\nStat (0 to cancel): ");

                        if (evStat < 0 || evStat > 6) {
                            System.out.println("!- There is no stat with this index -!\n");
                        }
                    } while (evStat < 0 || evStat > 6);

                    if (evStat != 0) {
                        int evValue = readOption("\nValue: ");

                        switch (evStat) {
                            case 1:
                                pokemon.setEV(StatName.HP, evValue);
                                break;

                            case 2:
                                pokemon.setEV(StatName.Atk, evValue);
                                break;

                            case 3:
                                pokemon.setEV(StatName.Def, evValue);
                                break;

                            case 4:
                                pokemon.setEV(StatName.SpA, evValue);
                                break;

                            case 5:
                                pokemon.setEV(StatName.SpD, evValue);
                                break;

                            case 6:
                                pokemon.setEV(StatName.Spe, evValue);
                                break;

                            default:
                                break;
                        }
                    }

                    break;

                case 9:
                    count = 0;
                    System.out.println("\nNatures:\n");
                    for (Nature nature : AllNatures.allNatures) {
                        count++;
                        System.out.print(count + ". " + nature.getName() + "  ");
                        for (int i = 11; i > (count + ". " + nature.getName()).length(); i--) {
                            System.out.print(".");
                        }

                        System.out.println("...  + " + nature.getBoostedStat().getName() + "  - " + nature.getReducedStat().getName());
                    }

                    int chosenNature;

                    do {
                        chosenNature = readOption("\nNature (0 to cancel): ");
                        if (chosenNature < 0 || chosenNature > count) {
                            System.out.println("!- There is no nature with this index -!\n");
                        }
                    } while (chosenNature < 0 || chosenNature > count);

                    if (chosenNature > 0) {
                        pokemon.setNature(AllNatures.allNatures.get(chosenNature-1));
                    }
                    break;

                case 0:
                    if (pokemon.getTrueMoves()[0] == null) {
                        System.out.println("\n!- " + pokemon.getTrueName(false, false) + " doesn't know any moves -!");
                        canConfirm = false;
                    }
                    break;

                default:
                    System.out.println("\n!- There is no option with this index -!");
                    break;
            }
        } while (option != 0 || !canConfirm);
    }

    private static ArrayList<PokemonTemplate> pokemonByGeneration(int generation) {
        ArrayList<PokemonTemplate> genPokemon = new ArrayList<>();

        for (PokemonTemplate pokemon : Data.get().getSelectablePokemonList()) {
            if (pokemon.getGeneration() == generation) {
                genPokemon.add(pokemon);
            }
        }

        return genPokemon;
    }

    public static int readOption(String prompt) {
        while (true) {
            System.out.print(prompt);
            String option = sc.nextLine();

            try {
                if (!option.equals("debug")) {
                    return Integer.parseInt(option);
                } else {
                    if (!debug) {
                        System.out.println("\n--- Debug Mode active ---\n");
                        debug = true;
                    } else {
                        System.out.println("\n--- Debug Mode inactive ---\n");
                        debug = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("\n!- Option must be a number -!\n");
            }
        }
    }
}