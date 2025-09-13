package main;

import java.util.ArrayList;
import java.util.Arrays;

import data.activationConditions.*;
import data.classes.FieldCondition;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.StatusCondition;
import data.objects.FieldConditionList;
import data.objects.ItemList;
import data.objects.MoveList;
import data.objects.PokemonList;
import data.objects.StatusConditionList;
import data.objects.TypeList;
import data.properties.items.ItemType;
import data.properties.moves.Category;
import data.properties.moves.EffectTarget;
import data.properties.moves.InherentProperty;
import data.properties.moves.MoveType;
import data.properties.moves.TemporaryProperty;
import data.properties.stats.StatName;
import main.actions.Action;
import main.actions.PriorityBracket;

public class Battle {
    public static boolean battleStartedTrue = false;
    private static boolean battleOver = false;
    public static int losingTeam = -1;

    public static Pokemon[][] teams;
    public static ArrayList<ArrayList<FieldCondition>> teamFields;
    public static ArrayList<FieldCondition> generalField;
    public static ArrayList<ArrayList<Move>> delayedMoves;
    public static Pokemon yourActivePokemon;
    public static Pokemon opponentActivePokemon;
    public static int yourActivePokemonIndex;
    public static int opponentActivePokemonIndex;

    public static ArrayList<PriorityBracket> actionOrder;
    public static PriorityBracket currentPriorityBracket;
    public static Action currentAction;
    public static Move lastUsedMove;
    public static int[] pokemonFaintedLastTurn;
    public static boolean[] megaEvolutionUsed;
    public static boolean[] zMoveUsed;
    public static boolean[] ultraBurstUsed;
    public static boolean[] terastallizationUsed;

    private static FieldCondition weather = FieldConditionList.clear.cause(-1, null, null);
    private static FieldCondition terrain = FieldConditionList.no_terrain.cause(-1, null, null);

    public static FieldCondition getWeather() {
        if (yourActivePokemon.getAbility().shouldActivate(AbilityActivation.CallWeather)) {
            return (FieldCondition) yourActivePokemon.getAbility().activate(yourActivePokemon, opponentActivePokemon, null, null, 0, null, null, 0, AbilityActivation.CallWeather);
        }

        if (opponentActivePokemon.getAbility().shouldActivate(AbilityActivation.CallWeather)) {
            return (FieldCondition) opponentActivePokemon.getAbility().activate(opponentActivePokemon, yourActivePokemon, null, null, 0, null, null, 0, AbilityActivation.CallWeather);
        }

        return weather;
    }

    public static FieldCondition getTrueWeather() {
        return weather;
    }

    public static FieldCondition getTerrain() {
        return terrain;
    }

    public static void setWeather(FieldCondition newWeather) {
        weather = newWeather;

        if (battleStartedTrue) {
            for (Pokemon activePokemon : orderPokemon(yourActivePokemon, opponentActivePokemon)) {
                Pokemon opponent = null;
                if (activePokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                if (activePokemon.getAbility().shouldActivate(AbilityActivation.WeatherChange)) {
                    activePokemon.getAbility().activate(activePokemon, opponent, null, null, 0, null, null, 0, AbilityActivation.WeatherChange);
                }
            }
        }
    }

    public static void setTerrain(FieldCondition newTerrain) {
        terrain = newTerrain;

        if (battleStartedTrue) {
            for (Pokemon activePokemon : orderPokemon(yourActivePokemon, opponentActivePokemon)) {
                Pokemon opponent = null;
                if (activePokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                if (activePokemon.getAbility().shouldActivate(AbilityActivation.TerrainChange)) {
                    activePokemon.getAbility().activate(activePokemon, opponent, null, null, 0, null, null, 0, AbilityActivation.TerrainChange);
                }
            }
        }
    }

    public static void removeGeneralFieldCondition(FieldCondition fieldCondition) {
        if (generalField.contains(fieldCondition)) {
            generalField.set(generalField.indexOf(fieldCondition), FieldConditionList.placeholder);
        }
    }

    public static void removeTeamFieldCondition(FieldCondition fieldCondition, int fieldIndex) {
        ArrayList<FieldCondition> field = teamFields.get(fieldIndex);
        if (field.contains(fieldCondition)) {
            field.set(field.indexOf(fieldCondition), FieldConditionList.placeholder);
        }
    }

    public static void start(Pokemon[][] pokemonTeams) {
        teams = pokemonTeams;
        teamFields = new ArrayList<>();
        teamFields.add(new ArrayList<>());
        teamFields.add(new ArrayList<>());
        generalField = new ArrayList<>();
        delayedMoves = new ArrayList<>();
        delayedMoves.add(new ArrayList<>());
        delayedMoves.add(new ArrayList<>());
        pokemonFaintedLastTurn = new int[2];
        megaEvolutionUsed = new boolean[2];
        zMoveUsed = new boolean[2];
        ultraBurstUsed = new boolean[2];
        terastallizationUsed = new boolean[2];

        for (Pokemon[] team : teams) {
            for (Pokemon pokemon : team) {
                if (pokemon != null) {
                    pokemon.getAbility().setActive(true);
                }
            }
        }

        makeCoolPokeball();

        System.out.println("\n\nYou are challenged by Pokémon Trainer Player 2!");

        yourActivePokemon = teams[0][0];
        opponentActivePokemon = teams[1][0];

        yourActivePokemon.addTurnOnField();
        opponentActivePokemon.addTurnOnField();

        yourActivePokemonIndex = 0;
        opponentActivePokemonIndex = 0;

        for (Pokemon pokemon : orderPokemon(yourActivePokemon, opponentActivePokemon)) {
            Pokemon opponent;
            if (pokemon == yourActivePokemon) {
                opponent = opponentActivePokemon;
            } else {
                opponent = yourActivePokemon;
            }

            if (pokemon.getAbility().shouldActivate(AbilityActivation.Entry)) {
                pokemon.getAbility().activate(pokemon, opponent, null, null, 0, null, null, 0, AbilityActivation.Entry);
            }

            if (pokemon.getItem().shouldActivate(ItemActivation.Entry)) {
                pokemon.getItem().activate(pokemon, pokemon, opponent, null, 0, ItemActivation.Entry);
            }
        }

        weather.setActivatedThisTurn(false);
        terrain.setActivatedThisTurn(false);
        for (FieldCondition condition : generalField) {
            condition.setActivatedThisTurn(false);
        }
        for (ArrayList<FieldCondition> field : teamFields) {
            for (FieldCondition condition : field) {
                condition.setActivatedThisTurn(false);
            }
        }

        battleStartedTrue = true;

        do {
            System.out.println("\n---------------- Player 1 ----------------");
            Move yourMove = turn(yourActivePokemon, opponentActivePokemon);
            if (yourActivePokemon.getBattleAction() == 3) {
                battleOver = true;
                losingTeam = 0;
                break;
            }
            System.out.println("\n---------------- Player 2 ----------------");
            Move opponentMove = turn(opponentActivePokemon, yourActivePokemon);
            if (opponentActivePokemon.getBattleAction() == 3) {
                battleOver = true;
                losingTeam = 1;
                break;
            }


            orderActions(yourMove, yourActivePokemon, opponentMove, opponentActivePokemon);

            for (int i = 0; i < actionOrder.size(); i++) {
                PriorityBracket priorityBracket = actionOrder.get(i);
                currentPriorityBracket = priorityBracket;
                for (int j = 0; j < priorityBracket.actions.size(); j++) {
                    Action action = priorityBracket.actions.get(j);
                    currentAction = action;

                    // teste de ordem
                    if (App.debug) {
                        for (PriorityBracket priorityBracket2 : actionOrder) {
                            for (Action action2 : priorityBracket2.actions) {
                                if (action2 == action) {
                                    System.out.print("-> ");
                                }
                                System.out.print(action2.move.getTrueName() + " | ");
                                System.out.print(action2.user.getTrueNameAndForm(false, false) + " " + action2.user.getTeam() + " | ");
                                System.out.print(action2.target.getTrueNameAndForm(false, false) + " " + action2.target.getTeam() + " | ");
                                System.out.println("Priority " + action2.priorityBracket + ", Speed " + action2.actionSpeed);
                            }
                        }
                    }

                    if (!faintCheck(action.user, false)) {
                        if (action.user.getBattleAction() == 1 ||
                            action.user.getBattleAction() == 4 ||
                            action.user.getBattleAction() == 5 ||
                            action.user.getBattleAction() == 6) {
                            useMove(
                                action.move, action.user, action.target,
                                action.move.getTemporaryProperties().contains(TemporaryProperty.Readying),
                                action.move.getTemporaryProperties().contains(TemporaryProperty.Called),
                                !action.move.compare(MoveList._switch_) && !action.move.getTemporaryProperties().contains(TemporaryProperty.Readying)
                            );
                        } else {
                            int teamIndex = (action.user).getTeam();
                            int pokemonIndex;
                            if (teamIndex == 0) {
                                pokemonIndex = yourActivePokemonIndex;
                            } else {
                                pokemonIndex = opponentActivePokemonIndex;
                            }
                            switchOut(action.user, teams[teamIndex][pokemonIndex], action.move);
                        }
                    }
                    action.executed = true;
                    reorderActions();

                    if (battleOver) {
                        break;
                    }
                }
                if (battleOver) {
                    break;
                }
            }

            if (!battleOver) {
                endOfTurnEffects(orderPokemon(yourActivePokemon, opponentActivePokemon));

                if (!battleOver) {
                    faintReplacement();
                }

                if (!faintCheck(yourActivePokemon, false)) {
                    yourActivePokemon.addTurnOnField();
                }

                if (!faintCheck(opponentActivePokemon, false)) {
                    opponentActivePokemon.addTurnOnField();
                }
            }

            yourActivePokemon.setDamagedThisTurn(false, null);
            opponentActivePokemon.setDamagedThisTurn(false, null);

            yourActivePokemon.setCurrentMoveFailed(false);
            opponentActivePokemon.setCurrentMoveFailed(false);

            for (int i = 0; i < pokemonFaintedLastTurn.length; i++) {
                if (pokemonFaintedLastTurn[i] > 0) {
                    pokemonFaintedLastTurn[i]--;
                }
            }

            weather.setActivatedThisTurn(false);
            terrain.setActivatedThisTurn(false);
            for (FieldCondition condition : generalField) {
                condition.setActivatedThisTurn(false);
            }
            for (ArrayList<FieldCondition> field : teamFields) {
                for (FieldCondition condition : field) {
                    condition.setActivatedThisTurn(false);
                }
            }
        } while (!battleOver);

        if (yourActivePokemon.getBattleAction() == 3) {
            losingTeam = 0;
        } else if (opponentActivePokemon.getBattleAction() == 3) {
            losingTeam = 1;
        }

        if (losingTeam == 1) {
            System.out.println("\n- You won -");
        } else {
            System.out.println("\n- You lost -");
        }
    }

    private static void makeCoolPokeball() {
        String[] coolPokeball = {
            "            ░░░░░░░░░░░░░░░░░░░░░            ",
            "      ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░      ",
            "   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░   ",
            "   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░   ",
            "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░",
            "░░░░░░░░░░░░░░░░░░         ░░░░░░░░░░░░░░░░░░",
            "░░░░░░░░░░░░░░░   █████████   ░░░░░░░░░░░░░░░",
            "                  █████████                  ",
            "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓   █████████   ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓",
            "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓         ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓",
            "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓",
            "   ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓   ",
            "   ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓   ",
            "      ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓      ",
            "            ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓            ",
        };

        for (String line : coolPokeball) {
            System.out.println(line);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Move turn(Pokemon userPokemon, Pokemon opposingPokemon) {
        if (userPokemon.getReadiedMove() == null) {
            System.out.println("\n------------------------------------------");
            System.out.println(opposingPokemon.getNameAndForm(false, false) + "\tLv " + opposingPokemon.getLevel());
            System.out.println(healthBar(opposingPokemon) + " " + opposingPokemon.getCurrentHP() + "/" + opposingPokemon.getHP());
            System.out.println("Status: " + opposingPokemon.getNonVolatileStatus().getName());
            System.out.println("\nWeather: " + weather.getName());
            System.out.println("Terrain: " + terrain.getName() + "\n");
            System.out.println("Status: " + userPokemon.getNonVolatileStatus().getName());
            System.out.println(healthBar(userPokemon) + " " + userPokemon.getCurrentHP() + "/" + userPokemon.getHP());
            System.out.println(userPokemon.getNameAndForm(false, false) + "\tLv " + userPokemon.getLevel());
            System.out.println("------------------------------------------");

            String extraOption = "";
            if (userPokemon.getItem().getType() == ItemType.MegaStone && userPokemon.getItem().heldByValidForm(true) ||
                userPokemon.compare(PokemonList.rayquaza, true) && userPokemon.getMove(MoveList.dragon_ascent) != null && userPokemon.getItem().getType() != ItemType.ZCrystal) {
                extraOption = "Mega Evolve";
            }
            if (userPokemon.getItem().getType() == ItemType.ZCrystal && userPokemon.getItem().heldByValidForm(false)) {
                if (userPokemon.getItem().compare(ItemList.ultranecrozium_z) &&
                    (userPokemon.compareWithForm(PokemonList.necrozma_dusk_mane) || userPokemon.compareWithForm(PokemonList.necrozma_dawn_wings))) {
                    if (userPokemon.getItem().heldByValidForm(true)) {
                        extraOption = "Ultra Burst";
                    }
                } else {
                    extraOption = "Z-Move";
                }
            }
            if (userPokemon.getItem().compare(ItemList.stellar_orb) && userPokemon.getItem().heldByValidUser(true)) {
                extraOption = "Terastallize";
            }

            int option;

            do {
                System.out.println("\nWhat will " + userPokemon.getTrueName(false, false) + " do?");
                System.out.println("1. Fight");
                System.out.println("2. Pokémon");
                System.out.println("3. Run");

                option = App.readOption("\n> ");

                switch (option) {
                    case 1:
                        boolean movesUnusable = true;

                        boolean usableMoves[] = {true, true, true, true};

                        for (int i = 0; i < 4; i++) {
                            Move move = userPokemon.getMoves()[i];
                            if (move != null) {
                                boolean noPP = false;
                                boolean fieldBlocked = false;
                                boolean statusBlocked = false;

                                // sem PP
                                if (move.getCurrentPP() <= 0) {
                                    noPP = true;
                                }

                                // bloqueado por efeito de campo
                                for (FieldCondition condition : generalField) {
                                    if (condition.shouldActivate(FieldActivation.TrySelectMove)) {
                                        fieldBlocked = !((boolean) condition.activate(userPokemon, opposingPokemon, move, null, null, null, 0, false, false, FieldActivation.TrySelectMove));
                                        if (fieldBlocked) {
                                            break;
                                        }
                                    }
                                }

                                // bloqueado por condição de status
                                for (StatusCondition condition : userPokemon.getVolatileStatusList()) {
                                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TrySelectMove)) {
                                        statusBlocked = !((boolean) condition.activate(userPokemon, opposingPokemon, move, 0, false, StatusActivation.TrySelectMove));
                                        if (statusBlocked) {
                                            break;
                                        }
                                    }
                                }
                                if (!statusBlocked) {
                                    for (StatusCondition condition : opposingPokemon.getVolatileStatusList()) {
                                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTrySelectMove)) {
                                            statusBlocked = !((boolean) condition.activate(opposingPokemon, userPokemon, move, 0, false, StatusActivation.OpponentTrySelectMove));
                                            if (statusBlocked) {
                                                break;
                                            }
                                        }
                                    }
                                }


                                if (noPP || fieldBlocked || statusBlocked) {
                                    usableMoves[i] = false;
                                }
                            } else {
                                usableMoves[i] = false;
                            }
                        }

                        for (boolean moveIsUsable : usableMoves) {
                            if (moveIsUsable) {
                                movesUnusable = false;
                                break;
                            }
                        }



                        if (!movesUnusable) {
                            boolean willMegaEvolve = false;
                            boolean willUseZMove = false;
                            boolean willUltraBurst = false;
                            boolean willTerastallize = false;

                            System.out.println("\n");
                            int count = 0;

                            for (Move move : userPokemon.getMoves()) {
                                if (move != null) {
                                    count++;
                                    System.out.println(count + ". " + move.getTrueName());
                                    System.out.println("Type: " + move.getTrueType().getName() + "\t" +
                                                       "PP: " + move.getCurrentPP() + "/" + move.getPP());
                                }
                            }

                            boolean canMegaEvolve = extraOption.equals("Mega Evolve") && !(megaEvolutionUsed[userPokemon.getTeam()]);
                            boolean canUseZMove = extraOption.equals("Z-Move") && !(zMoveUsed[userPokemon.getTeam()]);
                            boolean canUltraBurst = extraOption.equals("Ultra Burst") && !(ultraBurstUsed[userPokemon.getTeam()]);
                            boolean canTerastallize = extraOption.equals("Terastallize") && !(terastallizationUsed[userPokemon.getTeam()]);

                            if (canMegaEvolve || canUseZMove || canUltraBurst || canTerastallize) {
                                count++;
                                System.out.println("\n" + count + ". " + extraOption);
                            }

                            do {
                                System.out.println("\n(0 to cancel)");
                                option = App.readOption("> ");

                                if (option < 0 || option > count) {
                                    System.out.println("\n!- There is no move with this index -!\n");
                                } else if (option != 0 &&
                                            (option < count ||
                                                (!canMegaEvolve || willMegaEvolve) &&
                                                (!canUseZMove || willUseZMove) &&
                                                (!canUltraBurst || willUltraBurst) &&
                                                (!canTerastallize || willTerastallize)
                                            )) {
                                    if (userPokemon.getMoves()[option-1].getCurrentPP() == 0) {
                                        System.out.println("\n!- There is no PP left for this move -!\n");
                                    } else {
                                        boolean canUse = true;

                                        if (!willUseZMove) {
                                            if (canUse) {
                                                for (FieldCondition condition : generalField) {
                                                    if (condition.shouldActivate(FieldActivation.TrySelectMove)) {
                                                        canUse = (boolean) condition.activate(userPokemon, opposingPokemon, userPokemon.getMoves()[option-1], null, null, null, 0, false, true, FieldActivation.TrySelectMove);
                                                        if (!canUse) {
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if (canUse) {
                                                for (StatusCondition condition : userPokemon.getVolatileStatusList()) {
                                                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TrySelectMove)) {
                                                        canUse = (boolean) condition.activate(userPokemon, opposingPokemon, userPokemon.getMoves()[option-1], 0, true, StatusActivation.TrySelectMove);
                                                        if (!canUse) {
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if (canUse) {
                                                for (StatusCondition condition : opposingPokemon.getVolatileStatusList()) {
                                                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTrySelectMove)) {
                                                        canUse = (boolean) condition.activate(opposingPokemon, userPokemon, userPokemon.getMoves()[option-1], 0, true, StatusActivation.OpponentTrySelectMove);
                                                        if (!canUse) {
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (canUse) {
                                            Move moveToUse = userPokemon.getMoves()[option-1];
                                            if (willUseZMove) {
                                                Move zMove = userPokemon.getItem().getZMove();
                                                Move zMoveOrigin = userPokemon.getItem().getZMoveOrigin();

                                                if (zMoveOrigin == null && moveToUse.getTrueType().compare(zMove.getTrueType())) {
                                                    if (moveToUse.getCategory() != Category.Status) {
                                                        moveToUse = new Move(zMove, moveToUse, userPokemon);
                                                    } else {
                                                        moveToUse.setZPowered(true);
                                                    }
                                                } else if (zMoveOrigin != null && moveToUse.compare(zMoveOrigin)) {
                                                    moveToUse = new Move(zMove, moveToUse, userPokemon);
                                                } else {
                                                    System.out.println("!- " + moveToUse.getTrueName() + " can't be turned into a Z-Move with this Z-Crystal -!");
                                                    continue;
                                                }
                                            }

                                            if (willMegaEvolve) {
                                                userPokemon.setBattleAction(4);
                                            } else if (willUltraBurst) {
                                                userPokemon.setBattleAction(5);
                                            } else if (willTerastallize) {
                                                userPokemon.setBattleAction(6);
                                            } else {
                                                userPokemon.setBattleAction(1);
                                            }
                                            System.out.println("\n------------------------------------------");
                                            return moveToUse;
                                        }
                                    }
                                } else if (option != 0) {
                                    if (extraOption.equals("Mega Evolve")) {
                                        willMegaEvolve = true;
                                        System.out.println("\n- " + userPokemon.getName(false, false) + " will Mega Evolve this turn -");
                                    } else if (extraOption.equals("Z-Move")) {
                                        willUseZMove = true;
                                        count = 0;

                                        System.out.println();
                                        for (Move move : userPokemon.getMoves()) {
                                            Move zMove = userPokemon.getItem().getZMove();
                                            Move zMoveOrigin = userPokemon.getItem().getZMoveOrigin();

                                            if (move != null) {
                                                boolean isTurned = false;
                                                count++;

                                                System.out.print(count + ". ");
                                                if (zMoveOrigin == null) {
                                                    if (move.getTrueType().compare(zMove.getTrueType())) {
                                                        if (move.getCategory() != Category.Status) {
                                                            System.out.println(zMove.getTrueName());
                                                        } else {
                                                            System.out.println("Z-" + move.getTrueName());
                                                        }
                                                        isTurned = true;
                                                    } else {
                                                        System.out.println("-");
                                                    }
                                                } else {
                                                    if (move.compare(zMoveOrigin)) {
                                                        System.out.println(zMove.getTrueName());
                                                        isTurned = true;
                                                    } else {
                                                        System.out.println("-");
                                                    }
                                                }
                                                if (isTurned) {
                                                    System.out.println("Type: " + zMove.getTrueType().getName() + "\t" +
                                                                       "PP: " + move.getCurrentPP() + "/" + move.getPP());
                                                } else {
                                                    System.out.println("Type: -\tPP: -");
                                                }
                                            }
                                        }
                                    } else if (extraOption.equals("Ultra Burst")) {
                                        willUltraBurst = true;
                                        System.out.println("\n- " + userPokemon.getName(false, false) + " will undergo Ultra Burst this turn -");
                                    } else if (extraOption.equals("Terastallize")) {
                                        willTerastallize = true;
                                        System.out.println("\n- " + userPokemon.getName(false, false) + " will Terastallize this turn -");
                                    }
                                }

                                if (option == 0) {
                                    willMegaEvolve = false;
                                    willUseZMove = false;
                                    willUltraBurst = false;
                                    willTerastallize = false;
                                }
                            } while (option != 0);
                        } else {
                            userPokemon.setBattleAction(1);
                            System.out.println("\n------------------------------------------");
                            return new Move(MoveList.struggle, userPokemon);
                        }

                        break;

                    case 2:
                        boolean trapped = false;
                        boolean cantTrap = userPokemon.hasType(TypeList.ghost);
                        if (!cantTrap &&
                            userPokemon.getAbility().shouldActivate(AbilityActivation.BlockSwitch) &&
                            !((boolean) userPokemon.getAbility().activate(userPokemon, null, null, null, 0, null, null, 0, AbilityActivation.BlockSwitch))) {
                            cantTrap = true;
                        }

                        if (!cantTrap) {
                            if (opposingPokemon.getAbility().shouldActivate(AbilityActivation.OpponentTrySwitch) &&
                                (boolean) opposingPokemon.getAbility().activate(opposingPokemon, userPokemon, null, null, 0, null, null, 0, AbilityActivation.OpponentTrySwitch)) {
                                trapped = true;
                                break;
                            }

                            for (StatusCondition condition : userPokemon.getVolatileStatusList()) {
                                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TrySwitch) &&
                                    (boolean) condition.activate(userPokemon, opposingPokemon, condition.getCausingMove(), 0, true, StatusActivation.TrySwitch)) {
                                    trapped = true;
                                    break;
                                }
                            }
                        }

                        if (trapped) {
                            break;
                        } else {
                            System.out.println("\n");
                            int index = pokemonToSwitchIn(userPokemon.getTeam(), false);
                            if (index == -1) {
                                break;
                            }

                            userPokemon.setBattleAction(2);
                            System.out.println("\n------------------------------------------");
                            return new Move(MoveList._switch_, userPokemon);
                        }

                    case 3:
                        userPokemon.setBattleAction(3);
                        System.out.println("\n------------------------------------------");
                        return null;

                    default:
                        System.out.println("\n!- Invalid option -!\n");
                        break;
                }
            } while (true);
        } else {
            System.out.println("\n------------------------------------------\n");
            System.out.println(userPokemon.getTrueName(false, false) + " already has a move readied");
            System.out.println("\n------------------------------------------");
            userPokemon.setBattleAction(1);
            return userPokemon.getReadiedMove();
        }
    }

    private static String healthBar(Pokemon pokemon) {
        String healthBar = "[";

        double hpPercentage = (double) pokemon.getCurrentHP()/pokemon.getHP();

        int slices = (int) Math.ceil(hpPercentage*20);
        int emptySlices = 20-slices;

        for (int i = 0; i < slices; i++) {
            healthBar += "#";
        }

        for (int i = 0; i < emptySlices; i++) {
            healthBar += " ";
        }

        healthBar += "]";

        return healthBar;
    }

    public static void useMove(Move move, Pokemon user, Pokemon target, boolean readyingAtStart, boolean called, boolean dividers) {
        if (move == null) {
            return;
        }

        if (dividers) {
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
        }

        if (move.compare(MoveList._switch_)) {
            if (user.getVolatileStatus(StatusConditionList.readying_switch) == null) {
                switchOut(user, null, move);
            } else {
                Pokemon incomingPokemon = null;
                if (move.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                    int index;
                    if (user.getTeam() == 0) {
                        index = yourActivePokemonIndex;
                    } else {
                        index = opponentActivePokemonIndex;
                    }

                    do {
                        if (index >= 5) {
                            index = 0;
                        } else {
                            index++;
                        }
                        incomingPokemon = Battle.teams[user.getTeam()][index];

                        if (user.getTeam() == 0) {
                            yourActivePokemonIndex = index;
                        } else {
                            opponentActivePokemonIndex = index;
                        }
                    } while (incomingPokemon == null ||
                             incomingPokemon == Battle.opponentActivePokemon ||
                             Battle.faintCheck(incomingPokemon, false));
                } else {
                    System.out.println("\n------------------------------------------\n");
                    incomingPokemon = teams[user.getTeam()][pokemonToSwitchIn(user.getTeam(), true)];
                    System.out.println("\n------------------------------------------\n");
                }
                switchOut(user, incomingPokemon, move);
            }
        } else if (move.compare(MoveList._mega_evolve_)) {
            for (Pokemon form : user.getForms()) {
                if (!user.compare(PokemonList.rayquaza, true) && form.compareWithForm(user.getItem().getTransformsInto()) ||
                    user.compare(PokemonList.rayquaza, true) && form.compareWithForm(PokemonList.rayquaza_mega)) {
                    if (!user.compare(PokemonList.rayquaza, true)) {
                        System.out.println(user.getName(true, true) + "'s " + user.getItem().getName() + " is reacting to Player " + (user.getTeam() + 1) + "'s Mega Ring!");
                    } else {
                        System.out.println("Player "  + (user.getTeam() + 1) + "'s fervent wish has reached " + user.getName(true, false) + "!");
                    }

                    System.out.print(user.getName(true, true) + " has Mega Evolved into Mega " + user.getName(false, false));
                    if (form.getForm().equals("Mega X")) {
                        System.out.print(" X");
                    }
                    if (form.getForm().equals("Mega Y")) {
                        System.out.print(" Y");
                    }
                    System.out.println("!");

                    user.changeForm(form.getForm());
                    megaEvolutionUsed[user.getTeam()] = true;
                    break;
                }
            }
        } else if (move.compare(MoveList._ultra_burst_)) {
            for (Pokemon form : user.getForms()) {
                if (form.compareWithForm(PokemonList.necrozma_ultra)) {
                    System.out.println("Bright light is about to burst out of " + user.getName(true, false) + "!");
                    System.out.println(user.getName(true, true) + " regained its true power through Ultra Burst!");
                    user.changeForm(form.getForm());
                    ultraBurstUsed[user.getTeam()] = true;
                    break;
                }
            }
        } else if (move.compare(MoveList._terastallize_)) {
            for (Pokemon form : user.getForms()) {
                if (form.compareWithForm(PokemonList.terapagos_stellar)) {
                    System.out.println(user.getName(true, true) + "'s " + user.getItem().getName() + " is emanating an overwhelming energy!");
                    System.out.println(user.getName(true, true) + " has Terastallized into the Stellar type!");
                    user.changeForm(form.getForm());
                    terastallizationUsed[user.getTeam()] = true;
                    break;
                }
            }
        } else if (readyingAtStart) {
            if (Arrays.asList(move.getConditions()).contains(MoveEffectActivation.TurnStart)) {
                move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.TurnStart);
            }
        } else {
            if (!move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                for (StatusCondition condition : user.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.ChangeMove)) {
                        move = (Move) condition.activate(user, target, move, 0, true, StatusActivation.ChangeMove);
                    }
                }
            }

            boolean canMove = true;

            if (!called &&
                !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                // condições de status não-voláteis
                if (Arrays.asList(user.getNonVolatileStatus().getActivation()).contains(StatusActivation.TryAct)) {
                    canMove = (boolean) user.getNonVolatileStatus().activate(user, target, move, 0, true, StatusActivation.TryAct);
                }

                // condições de status voláteis
                for (StatusCondition condition : user.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TryAct)) {
                        canMove = (boolean) condition.activate(user, target, move, 0, true, StatusActivation.TryAct);
                    }

                    if (battleOver) {
                        return;
                    }
                }

                // condições de campo (geral)
                for (FieldCondition condition : generalField) {
                    if (condition.shouldActivate(FieldActivation.TryAct)) {
                        canMove = (boolean) condition.activate(user, target, move, null, null, null, 0, false, true, FieldActivation.TryAct);
                    }
                }
            }

            if (canMove) {
                boolean canMove2 = true;

                if (!called &&
                    !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                    // condições de status voláteis
                    for (StatusCondition condition : user.getVolatileStatusList()) {
                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TryMove)) {
                            canMove2 = (boolean) condition.activate(user, target, move, 0, true, StatusActivation.TryMove);
                        }

                        if (battleOver) {
                            return;
                        }
                    }

                    // condições de status não-voláteis
                    if (Arrays.asList(user.getNonVolatileStatus().getActivation()).contains(StatusActivation.TryMove)) {
                        canMove2 = (boolean) user.getNonVolatileStatus().activate(user, target, move, 0, true, StatusActivation.TryMove);
                    }
                }

                if (canMove2) {
                    boolean moveSuccessful = true;

                    if (move.isZMove() || move.isZPowered()) {
                        if (!called) {
                            boolean zEffectActivated = false;
                            if (move.isZPowered() &&
                                move.getZEffect() != null &&
                                Arrays.asList(move.getZConditions()).contains(MoveEffectActivation.ZNormal)) {
                                zEffectActivated = (boolean) move.activateZEffect(user, target, null, 0, 0, true, MoveEffectActivation.ZNormal);
                            }

                            if (move.isZMove() || !zEffectActivated) {
                                System.out.println(user.getName(true, true) + " surrounded itself with its Z-Power!");
                            }

                            System.out.println(user.getName(true, true) + " unleashed its full-force Z-Move!");
                        }

                        if (move.isZMove() && !move.isSignatureZMove() && !move.compare(move.getMoveOrigin().turnZMove())) {
                            move = new Move(move.getMoveOrigin().turnZMove(), move.getMoveOrigin(), move.getUser());

                            if (move.getMoveOrigin().compare(MoveList.weather_ball)) {
                                System.out.println("Breakneck Blitz turned into " + move.getName() + " due to the weather!");
                            }
                        }

                        if (!called) {
                            System.out.println();
                        }
                    }

                    if (move.compare(MoveList.struggle)) {
                        System.out.println(user.getName(true, true) + " has no moves left!");
                    }

                    if (user.getVolatileStatus(StatusConditionList.recharging_turn) == null &&
                        !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                        System.out.println(user.getName(true, true) + " used " + (move.isZPowered() ? "Z-" : "") + move.getName() + "!");
                    }

                    if (!move.getTemporaryProperties().contains(TemporaryProperty.FutureHit) &&
                        user.getAbility().shouldActivate(move, AbilityActivation.UseMove)) {
                        user.getAbility().activate(user, target, move, null, 0, null, null, 0, AbilityActivation.UseMove);
                    }

                    if (faintCheck(target, false)) {
                        System.out.println("But there was no target...");
                        moveSuccessful = false;
                    }

                    if (moveSuccessful) {
                        if (user != yourActivePokemon) {
                            for (StatusCondition condition : yourActivePokemon.getVolatileStatusList()) {
                                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTryUseMoveAny)) {
                                    moveSuccessful = (boolean) condition.activate(yourActivePokemon, opponentActivePokemon, move, 0, true, StatusActivation.OpponentTryUseMoveAny);
                                }
                            }
                        }
                        if (user != opponentActivePokemon) {
                            for (StatusCondition condition : opponentActivePokemon.getVolatileStatusList()) {
                                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTryUseMoveAny)) {
                                    moveSuccessful = (boolean) condition.activate(opponentActivePokemon, yourActivePokemon, move, 0, true, StatusActivation.OpponentTryUseMoveAny);
                                }
                            }
                        }
                    }

                    if (moveSuccessful) {
                        if (user.getAbility().shouldActivate(move, AbilityActivation.TryUseMove)) {
                            moveSuccessful = (boolean) user.getAbility().activate(user, user, move, null, 0, null, null, 0, AbilityActivation.TryUseMove);
                        }
                    }

                    if (moveSuccessful) {
                        if (move.getPrimaryEffect() != null &&
                            Arrays.asList(move.getConditions()).contains(MoveEffectActivation.TryUse)) {
                            moveSuccessful = (boolean) move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.TryUse);

                            if (!moveSuccessful) {
                                System.out.println("But it failed!");
                                user.setCurrentMoveFailed(true);
                            }
                        }
                    }

                    if (moveSuccessful) {
                        if (getWeather().shouldActivate(target, FieldActivation.TryUseMove)) {
                            moveSuccessful = (boolean) getWeather().activate(target, user, move, null, null, null, 0, false, true, FieldActivation.TryUseMove);
                        }
                    }

                    if (moveSuccessful) {
                        if (terrain.shouldActivate(target, FieldActivation.TryUseMove)) {
                            moveSuccessful = (boolean) terrain.activate(target, user, move, null, null, null, 0, false, true, FieldActivation.TryUseMove);
                        }
                    }

                    if (moveSuccessful) {
                        for (StatusCondition condition : target.getVolatileStatusList()) {
                            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTryUseMoveTargeted)) {
                                moveSuccessful = (boolean) condition.activate(target, user, move, 0, true, StatusActivation.OpponentTryUseMoveTargeted);
                            }
                        }
                    }

                    if (moveSuccessful) {
                        for (FieldCondition condition : teamFields.get(target.getTeam())) {
                            if (Arrays.asList(condition.getFieldActivation()).contains(FieldActivation.OpponentTryUseMove)) {
                                moveSuccessful = (boolean) condition.activate(target, user, move, null, null, null, 0, false, true, FieldActivation.OpponentTryUseMove);
                            }
                        }
                    }

                    if (moveSuccessful) {
                        if (target.getAbility().shouldActivate(move, AbilityActivation.TryHitUser)) {
                            moveSuccessful = (boolean) target.getAbility().activate(target, user, move, null, 0, null, null, 0, AbilityActivation.TryHitUser);
                        }
                    }

                    if (moveSuccessful &&
                        (
                            !((move.getCategory() != Category.Status || move.hasInherentProperty(InherentProperty.TypeChartAffected)) && Damage.ineffective(move, target)) &&
                            !(move.getCategory() == Category.Status && Damage.ineffectiveStatus(move, target)) ||
                            Arrays.asList(move.getMoveTypes()).contains(MoveType.Delayed) && !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)
                        )) {
                        boolean willHit = true;
                        double accuracy = move.getAccuracy();

                        if (move.getPrimaryEffect() != null &&
                            Arrays.asList(move.getConditions()).contains(MoveEffectActivation.HitGuarantee)) {
                            move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.HitGuarantee);
                        }
                        for (StatusCondition condition : target.getVolatileStatusList()) {
                            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentHitGuarantee)) {
                                condition.activate(target, user, move, 0, true, StatusActivation.OpponentHitGuarantee);
                            }
                        }

                        boolean charging = user.getVolatileStatus(StatusConditionList.charging_turn) != null || user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn) != null;
                        boolean recharging = user.getVolatileStatus(StatusConditionList.recharging_turn) != null;

                        if (!move.hasInherentProperty(InherentProperty.OneHitKO) &&
                            !move.getTemporaryProperties().contains(TemporaryProperty.CantMiss) &&
                            (!move.hasInherentProperty(InherentProperty.Charges) || charging) &&
                            (!move.hasInherentProperty(InherentProperty.Recharges) || !recharging)) {
                            if (target.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn) != null) {
                                StatusCondition charge = target.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);
                                if (Arrays.asList(charge.getActivation()).contains(StatusActivation.Invulnerability)) {
                                    willHit = (boolean) charge.activate(target, user, move, 0, true, StatusActivation.Invulnerability);
                                }
                            }

                            if (willHit) {
                                if (move.getPrimaryEffect() != null &&
                                    Arrays.asList(move.getConditions()).contains(MoveEffectActivation.AccuracyCalc)) {
                                    accuracy = (double) move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.AccuracyCalc);
                                }

                                if (accuracy != -1) {
                                    if (user.getAbility().shouldActivate(AbilityActivation.AccuracyCalc)) {
                                        accuracy *= (double) user.getAbility().activate(user, target, move, null, 0, null, null, 0, AbilityActivation.AccuracyCalc);
                                    }
                                    if (accuracy < 0) {
                                        accuracy = -1;
                                    }
                                }

                                if (accuracy != -1) {
                                    if (target.getAbility().shouldActivate(move, AbilityActivation.OpponentAccuracyCalc)) {
                                        accuracy *= (double) target.getAbility().activate(target, user, move, null, 0, null, null, 0, AbilityActivation.OpponentAccuracyCalc);
                                    }
                                    if (accuracy < 0) {
                                        accuracy = -1;
                                    }
                                }

                                if (accuracy != -1) {
                                    for (FieldCondition condition : generalField) {
                                        if (condition.shouldActivate(FieldActivation.AccuracyCalc)) {
                                            accuracy *= (double) condition.activate(user, target, move, null, null, null, 0, false, true, FieldActivation.AccuracyCalc);
                                        }
                                    }
                                    if (accuracy < 0) {
                                        accuracy = -1;
                                    }
                                }

                                if (accuracy != -1) {
                                    double modAccuracy = user.getStat(StatName.Acc).getStages(target, move);
                                    if (!move.hasInherentProperty(InherentProperty.IgnoresDefensiveAndEvasionStages)) {
                                        modAccuracy -= target.getStat(StatName.Eva).getStages(user, move);
                                    }

                                    if (modAccuracy > 6) {
                                        modAccuracy = 6;
                                    } else if (modAccuracy < -6) {
                                        modAccuracy = -6;
                                    }
                                    accuracy = modAccuracy >= 0 ? accuracy*((Math.abs(modAccuracy) + 3)/3) : accuracy/((Math.abs(modAccuracy) + 3)/3);

                                    willHit = Math.random() < accuracy/100.0;
                                } else {
                                    willHit = true;
                                }
                            }
                        } else if (move.hasInherentProperty(InherentProperty.OneHitKO) &&
                                   !move.getTemporaryProperties().contains(TemporaryProperty.CantMiss)) {
                            willHit = (boolean) move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.OneHitKOAccuracy);
                        }

                        if (willHit) {
                            Damage.directDamage(user, target, move, false);
                        } else {
                            System.out.println(target.getName(true, true) + " avoided the attack!");

                            if (move.getPrimaryEffect() != null &&
                                Arrays.asList(move.getConditions()).contains(MoveEffectActivation.Miss)) {
                                move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.Miss);
                            }

                            user.setReadiedMove(null);
                            move.setConsecutiveUses(-1);
                            user.setCurrentMoveFailed(true);

                            StatusCondition charging_turn = user.getVolatileStatus(StatusConditionList.charging_turn);
                            StatusCondition semi_invulnerable_charging_turn = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);
                            StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
                            StatusCondition locked = user.getVolatileStatus(StatusConditionList.locked);
                            if (charging_turn != null) {
                                user.endVolatileStatus(charging_turn, true);
                            }
                            if (semi_invulnerable_charging_turn != null) {
                                user.endVolatileStatus(semi_invulnerable_charging_turn, true);
                            }
                            if (rampage != null) {
                                if (rampage.getCounter() > 0) {
                                    user.endVolatileStatus(rampage, true);
                                } else {
                                    rampage.activate(user, target, move, 0, true, StatusActivation.UseMove);
                                }
                            }
                            if (locked != null) {
                                user.endVolatileStatus(locked, true);
                            }
                        }
                    } else {
                        if (moveSuccessful) {
                            System.out.println("It doesn't affect " + target.getName(true, false) + "...");
                        }

                        if (move.getPrimaryEffect() != null &&
                            Arrays.asList(move.getConditions()).contains(MoveEffectActivation.Miss)) {
                            move.activatePrimaryEffect(user, target, null, 0, 0, true, MoveEffectActivation.Miss);
                        }
                        faintCheck(user, true);

                        user.setReadiedMove(null);
                        move.setConsecutiveUses(-1);
                        if (moveSuccessful) {
                            user.setCurrentMoveFailed(true);
                        }

                        StatusCondition charging_turn = user.getVolatileStatus(StatusConditionList.charging_turn);
                        StatusCondition semi_invulnerable_charging_turn = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);
                        StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
                        StatusCondition locked = user.getVolatileStatus(StatusConditionList.locked);
                        if (charging_turn != null) {
                            user.endVolatileStatus(charging_turn, true);
                        }
                        if (semi_invulnerable_charging_turn != null) {
                            user.endVolatileStatus(semi_invulnerable_charging_turn, true);
                        }
                        if (rampage != null) {
                            if (rampage.getCounter() > 0) {
                                user.endVolatileStatus(rampage, true);
                            } else {
                                rampage.activate(user, target, move, 0, true, StatusActivation.UseMove);
                            }
                        }
                        if (locked != null) {
                            user.endVolatileStatus(locked, true);
                        }
                    }

                    if (user != yourActivePokemon) {
                        for (StatusCondition condition : yourActivePokemon.getVolatileStatusList()) {
                            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentMove)) {
                                condition.activate(yourActivePokemon, opponentActivePokemon, move, 0, true, StatusActivation.OpponentMove);
                            }
                        }
                    }
                    if (user != opponentActivePokemon) {
                        for (StatusCondition condition : opponentActivePokemon.getVolatileStatusList()) {
                            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentMove)) {
                                condition.activate(opponentActivePokemon, yourActivePokemon, move, 0, true, StatusActivation.OpponentMove);
                            }
                        }
                    }

                    if (user.getVolatileStatus(StatusConditionList.charging_turn) == null &&
                        user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn) == null &&
                        user.getVolatileStatus(StatusConditionList.rampage) == null &&
                        user.getVolatileStatus(StatusConditionList.locked) == null &&
                        !called &&
                        !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                        if (!move.compare(MoveList.struggle)) {
                            int ppConsumption = 1;
                            if (user.getTeam() != target.getTeam() &&
                                target.getAbility().shouldActivate(AbilityActivation.PPConsumption)) {
                                ppConsumption = (int) target.getAbility().activate(target, user, move, null, 0, null, null, 0, AbilityActivation.PPConsumption);
                            }
                            move.setCurrentPP(move.getCurrentPP()-ppConsumption);
                        }

                        if (move != user.getLastUsedMove() &&
                            (move.getMoveOrigin() == null || move.getMoveOrigin() != user.getLastUsedMove())) {
                            if (user.getLastUsedMove() != null) {
                                move.setConsecutiveUses(0);
                                user.getLastUsedMove().setConsecutiveUses(0);
                            }
                            user.setLastUsedMove(!move.compare(MoveList.struggle) ? move : null);
                        }
                        move.addUse();
                    }

                    lastUsedMove = move.getMoveOrigin() == null ? move : move.getMoveOrigin();
                    if (move.isZMove() || move.isZPowered()) {
                        zMoveUsed[user.getTeam()] = true;
                    }
                } else {
                    user.setReadiedMove(null);
                    move.setConsecutiveUses(0);
                    user.setCurrentMoveFailed(true);

                    StatusCondition charging_turn = user.getVolatileStatus(StatusConditionList.charging_turn);
                    StatusCondition semi_invulnerable_charging_turn = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);
                    StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
                    StatusCondition locked = user.getVolatileStatus(StatusConditionList.locked);
                    if (charging_turn != null) {
                        user.endVolatileStatus(charging_turn, true);
                    }
                    if (semi_invulnerable_charging_turn != null) {
                        user.endVolatileStatus(semi_invulnerable_charging_turn, true);
                    }
                    if (rampage != null) {
                        if (rampage.getCounter() > 0) {
                            user.endVolatileStatus(rampage, true);
                        } else {
                            rampage.activate(user, target, move, 0, true, StatusActivation.UseMove);
                        }
                    }
                    if (locked != null) {
                        user.endVolatileStatus(locked, true);
                    }
                }
            } else {
                user.setReadiedMove(null);
                move.setConsecutiveUses(0);
                user.setCurrentMoveFailed(true);

                StatusCondition charging_turn = user.getVolatileStatus(StatusConditionList.charging_turn);
                StatusCondition semi_invulnerable_charging_turn = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);
                StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
                StatusCondition locked = user.getVolatileStatus(StatusConditionList.locked);
                if (charging_turn != null) {
                    user.endVolatileStatus(charging_turn, true);
                }
                if (semi_invulnerable_charging_turn != null) {
                    user.endVolatileStatus(semi_invulnerable_charging_turn, true);
                }
                if (rampage != null) {
                    if (rampage.getCounter() > 0) {
                        user.endVolatileStatus(rampage, true);
                    } else {
                        rampage.activate(user, target, move, 0, true, StatusActivation.UseMove);
                    }
                }
                if (locked != null) {
                    user.endVolatileStatus(locked, true);
                }
            }

            user.setLastMoveFailed(user.currentMoveFailed());

            if (move.getTemporaryProperties().contains(TemporaryProperty.PranksterBoosted)) {
                move.removeProperty(TemporaryProperty.PranksterBoosted);
            }
            if (called) {
                move.removeProperty(TemporaryProperty.Called);
            }
            if (move.getTemporaryProperties().contains(TemporaryProperty.CantMiss)) {
                move.removeProperty(TemporaryProperty.CantMiss);
            }
            if (move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                move.removeProperty(TemporaryProperty.FutureHit);
            }
            move.setZPowered(false);
        }

        user.orderVolatileStatusList();

        if (dividers) {
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
        }
    }

    private static void endOfTurnEffects(Pokemon[] activePokemon) {
        // clima
        weather.countDown();
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, false)) {
                Pokemon opponent;
                if (pokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                if (getWeather().shouldActivate(FieldActivation.EndOfTurn)) {
                    getWeather().activate(pokemon, opponent, null, null, null, null, 0, false, true, FieldActivation.EndOfTurn);

                    if (battleOver) {
                        return;
                    }
                }
            }
        }

        // terreno
        terrain.countDown();
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, false)) {
                Pokemon opponent;
                if (pokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                if (terrain.shouldActivate(pokemon, FieldActivation.EndOfTurn)) {
                    terrain.activate(pokemon, opponent, null, null, null, null, 0, false, true, FieldActivation.EndOfTurn);
                }
            }
        }

        // habilidades
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, false)) {
                Pokemon opponent;
                if (pokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                if (pokemon.getAbility().shouldActivate(AbilityActivation.TurnEnd)) {
                    pokemon.getAbility().activate(pokemon, opponent, null, null, 0, null, null, 0, AbilityActivation.TurnEnd);
                }
            }
        }

        // condições de status voláteis
        for (Pokemon pokemon : activePokemon) {
            Pokemon opponent;
            if (pokemon == yourActivePokemon) {
                opponent = opponentActivePokemon;
            } else {
                opponent = yourActivePokemon;
            }

            for (StatusCondition condition : pokemon.getVolatileStatusList()) {
                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.EndOfTurn)) {
                    condition.activate(pokemon, opponent, null, 0, true, StatusActivation.EndOfTurn);
                }

                if (battleOver) {
                    return;
                }

                if (faintCheck(pokemon, false)) {
                    break;
                }
            }
        }

        // condições de status não-voláteis
        for (Pokemon pokemon : activePokemon) {
            Pokemon opponent;
            if (pokemon == yourActivePokemon) {
                opponent = opponentActivePokemon;
            } else {
                opponent = yourActivePokemon;
            }

            if (Arrays.asList(pokemon.getNonVolatileStatus().getActivation()).contains(StatusActivation.EndOfTurn)) {
                pokemon.getNonVolatileStatus().activate(pokemon, opponent, null, 0, true, StatusActivation.EndOfTurn);
            }

            if (battleOver) {
                return;
            }
        }

        // outros efeitos de campo

        // campo geral
        for (FieldCondition condition : generalField) {
            condition.countDown();

            if (condition.shouldActivate(FieldActivation.EndOfTurn)) {
                for (Pokemon pokemon : activePokemon) {
                    if (!faintCheck(pokemon, false)) {
                        Pokemon opponent;
                        if (pokemon == yourActivePokemon) {
                            opponent = opponentActivePokemon;
                        } else {
                            opponent = yourActivePokemon;
                        }

                        condition.activate(pokemon, opponent, null, null, null, null, 0, false, true, FieldActivation.EndOfTurn);

                        if (battleOver) {
                            return;
                        }
                    }
                }
            }

            if (condition.shouldActivate(FieldActivation.EndOfTurnOnce)) {
                condition.activate(null, null, null, null, null, null, 0, false, true, FieldActivation.EndOfTurnOnce);
            }
        }
        for (int i = 0; i < generalField.size(); i++) {
            FieldCondition condition = generalField.get(i);
            if (condition.compare(FieldConditionList.placeholder)) {
                generalField.remove(condition);
                i--;
            }
        }

        // campos de equipes
        for (ArrayList<FieldCondition> field : teamFields) {
            for (FieldCondition condition : field) {
                condition.countDown(field);

                if (condition.shouldActivate(FieldActivation.EndOfTurn)) {
                    for (Pokemon pokemon : activePokemon) {
                        if (!faintCheck(pokemon, false)) {
                            Pokemon opponent;
                            if (pokemon == yourActivePokemon) {
                                opponent = opponentActivePokemon;
                            } else {
                                opponent = yourActivePokemon;
                            }

                            if (teamFields.indexOf(field) == pokemon.getTeam()) {
                                condition.activate(pokemon, opponent, null, null, null, null, 0, false, true, FieldActivation.EndOfTurn);

                                if (battleOver) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < field.size(); i++) {
                FieldCondition condition = field.get(i);
                if (condition.compare(FieldConditionList.placeholder)) {
                    field.remove(condition);
                    i--;
                }
            }
        }

        // movimentos atrasados
        for (ArrayList<Move> teamDelayedMoves : delayedMoves) {
            for (Move move : teamDelayedMoves) {
                move.primaryEffectCountDown();

                if (Arrays.asList(move.getConditions()).contains(MoveEffectActivation.DelayedTurnEnd)) {
                    for (Pokemon pokemon : activePokemon) {
                        if (delayedMoves.indexOf(teamDelayedMoves) == pokemon.getTeam()) {
                            Pokemon opponent;
                            if (pokemon == yourActivePokemon) {
                                opponent = opponentActivePokemon;
                            } else {
                                opponent = yourActivePokemon;
                            }

                            Pokemon target = move.getPrimaryEffectTarget() == EffectTarget.Target ? opponent : pokemon;

                            move.activatePrimaryEffect(move.getUser(), target, null, 0, 0, true, MoveEffectActivation.DelayedTurnEnd);

                            if (battleOver) {
                                return;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < teamDelayedMoves.size(); i++) {
                Move move = teamDelayedMoves.get(i);
                if (move.compare(MoveList._placeholder_)) {
                    teamDelayedMoves.remove(move);
                    i--;
                }
            }
        }

        // itens
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, false)) {
                Pokemon opponent;
                if (pokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                if (pokemon.getItem().shouldActivate(ItemActivation.EndOfTurn)) {
                    pokemon.getItem().activate(pokemon, pokemon, opponent, null, 0, ItemActivation.EndOfTurn);
                }
            }
        }
    }

    public static int pokemonToSwitchIn(int teamNumber, boolean mandatory) {
        int pokemonInTeam = 0;
        Pokemon[] team = teams[teamNumber];
        Pokemon pokemonInBattle = teamNumber == 0 ? yourActivePokemon : opponentActivePokemon;
        int pokemonInBattleIndex = teamNumber == 0 ? yourActivePokemonIndex : opponentActivePokemonIndex;
        int limit = mandatory ? 0 : -1;

        System.out.println("- Choose the next Pokémon " + (mandatory ? "" : "(0 to cancel) ") + "-\n");

        int index;
        for (int i = 1; i <= team.length; i++) {
            if (team[i-1] != null) {
                System.out.println(i + ". " + team[i-1].getTrueNameAndForm(false, false) +
                                   (faintCheck(team[i-1], false) ? " (Fainted)" : "") +
                                   (team[i-1] == pokemonInBattle && !faintCheck(team[i-1], false) ? " (in battle)" : ""));
                pokemonInTeam++;
            }
        }
        do {
            index = App.readOption("\n> ") - 1;

            if (index >= limit && index < pokemonInTeam) {
                if (!(!mandatory && index == -1)) {
                    if (!faintCheck(team[index], false)) {
                        if (index != pokemonInBattleIndex) {
                            if (teamNumber == 0) {
                                yourActivePokemonIndex = index;
                            } else {
                                opponentActivePokemonIndex = index;
                            }

                            return index;
                        } else {
                            System.out.println("!- " + team[index].getTrueName(false, false) + " is already in battle -!");
                            index = -2;
                        }
                    } else {
                        System.out.println("!- " + team[index].getTrueName(false, false) + " is unable to battle -!");
                        index = -2;
                    }
                }
            } else {
                System.out.println("!- There is no Pokémon with this index -!");
            }
        } while (index < limit || index >= pokemonInTeam);

        return index;
    }

    public static void orderActions(Move move1, Pokemon pokemon1, Move move2, Pokemon pokemon2) {
        ArrayList<Action> allActions = new ArrayList<>();

        Pokemon target1;
        if (move1.targetsOpponent()) {
            target1 = pokemon2;
        } else {
            target1 = pokemon1;
        }

        allActions.add(new Action(move1, pokemon1, target1));

        if (Arrays.asList(move1.getConditions()).contains(MoveEffectActivation.TurnStart)) {
            Move preMove1 = new Move(move1, pokemon1);
            preMove1.addProperty(TemporaryProperty.Readying);
            allActions.add(new Action(preMove1, pokemon1, target1));
        }

        if (pokemon1.getBattleAction() == 4) {
            allActions.add(new Action(new Move(MoveList._mega_evolve_, pokemon1), pokemon1, target1));
        }

        if (pokemon1.getBattleAction() == 5) {
            allActions.add(new Action(new Move(MoveList._ultra_burst_, pokemon1), pokemon1, target1));
        }

        if (pokemon1.getBattleAction() == 6) {
            allActions.add(new Action(new Move(MoveList._terastallize_, pokemon1), pokemon1, target1));
        }


        Pokemon target2;
        if (move2.targetsOpponent()) {
            target2 = pokemon1;
        } else {
            target2 = pokemon2;
        }

        allActions.add(new Action(move2, pokemon2, target2));

        if (Arrays.asList(move2.getConditions()).contains(MoveEffectActivation.TurnStart)) {
            Move preMove2 = new Move(move2, pokemon2);
            preMove2.addProperty(TemporaryProperty.Readying);
            allActions.add(new Action(preMove2, pokemon2, target2));
        }

        if (pokemon2.getBattleAction() == 4) {
            allActions.add(new Action(new Move(MoveList._mega_evolve_, pokemon2), pokemon2, target2));
        }

        if (pokemon2.getBattleAction() == 5) {
            allActions.add(new Action(new Move(MoveList._ultra_burst_, pokemon2), pokemon2, target2));
        }

        if (pokemon2.getBattleAction() == 6) {
            allActions.add(new Action(new Move(MoveList._terastallize_, pokemon2), pokemon2, target2));
        }


        int minPriority = Integer.MAX_VALUE;
        int maxPriority = Integer.MIN_VALUE;

        for (int i = 0; i < allActions.size(); i++) {
            int movePriority = allActions.get(i).move.getPriority();
            if (allActions.get(i).move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                movePriority = 6;
            }

            if (movePriority < minPriority) {
                minPriority = movePriority;
            }
            if (movePriority > maxPriority) {
                maxPriority = movePriority;
            }
        }

        ArrayList<PriorityBracket> actions = new ArrayList<>();

        for (int i = maxPriority; i >= minPriority; i--) {
            ArrayList<Action> bracketActions = new ArrayList<>();
            for (Action action : allActions) {
                int movePriority = action.move.getPriority();
                if (action.move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                    movePriority = 6;
                }

                if (movePriority == i) {
                    action.priorityBracket = movePriority;
                    bracketActions.add(action);
                }
            }

            PriorityBracket priorityBracket = new PriorityBracket(bracketActions, i);
            actions.add(priorityBracket);
        }

        for (PriorityBracket priorityBracket : actions) {
            for (int i = 0; i < priorityBracket.actions.size(); i++) {
                int max = i;
                for (int j = i; j < priorityBracket.actions.size(); j++) {
                    Move moveJ = priorityBracket.actions.get(j).move;
                    Pokemon pokemonJ = priorityBracket.actions.get(j).user;
                    Pokemon targetJ = priorityBracket.actions.get(j).target;

                    Move moveMax = priorityBracket.actions.get(max).move;
                    Pokemon pokemonMax = priorityBracket.actions.get(max).user;
                    Pokemon targetMax = priorityBracket.actions.get(max).target;

                    int speJ = pokemonJ.getStat(StatName.Spe).getEffectiveValue(targetJ, moveJ, false, null);
                    int speMax = pokemonMax.getStat(StatName.Spe).getEffectiveValue(targetMax, moveMax, false, null);

                    for (FieldCondition condition : generalField) {
                        if (condition.compare(FieldConditionList.trick_room)) {
                            speJ = 10000 - speJ;
                            speMax = 10000 - speMax;
                            break;
                        }
                    }

                    priorityBracket.actions.get(j).actionSpeed = speJ;

                    if (speJ > speMax) {
                        max = j;
                    }

                    if (speJ == speMax) {
                        if (Math.random() < 0.5) {
                            max = j;
                        }
                    }
                }

                Action temp = priorityBracket.actions.get(i);
                priorityBracket.actions.set(i, priorityBracket.actions.get(max));
                priorityBracket.actions.set(max, temp);
            }
        }

        actionOrder = actions;
    }

    public static void reorderActions() {
        for (int i = 0; i < actionOrder.size(); i++) {
            PriorityBracket priorityBracket = actionOrder.get(i);
            for (int j = 0; j < priorityBracket.actions.size(); j++) {
                Action action = priorityBracket.actions.get(j);

                if (action.executed) {
                    continue;
                }

                if (action.actionTetheredBefore != null) {
                    if (j > 0 && action.actionTetheredBefore == priorityBracket.actions.get(j-1)) {
                        continue;
                    }
                }

                if (action.move.getPriority() != priorityBracket.priority &&
                    !action.move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                    priorityBracket.removeAction(action);
                    action.priorityBracket = action.move.getPriority();

                    PriorityBracket correctBracket = null;

                    for (int k = 0; k < actionOrder.size(); k++) {
                        if (action.move.getPriority() == actionOrder.get(k).priority) {
                            correctBracket = actionOrder.get(k);
                            break;
                        }
                    }

                    if (correctBracket == null) {
                        correctBracket = new PriorityBracket(action.move.getPriority());
                        actionOrder.add(correctBracket);

                        for (int l = 0; l < actionOrder.size(); l++) {
                            int max = l;
                            for (int m = 0; m < actionOrder.size(); m++) {
                                if (actionOrder.get(l).priority < actionOrder.get(m).priority) {
                                    max = m;
                                }
                            }
                            PriorityBracket temp = actionOrder.get(l);
                            actionOrder.set(l, actionOrder.get(max));
                            actionOrder.set(max, temp);
                        }
                    }

                    correctBracket.addAction(action);
                }
            }

            if (priorityBracket.actions.isEmpty()) {
                actionOrder.remove(priorityBracket);
                i--;
            }
        }

        for (int i = 0; i < actionOrder.size(); i++) {
            PriorityBracket priorityBracket = actionOrder.get(i);

            for (Action action : priorityBracket.actions) {
                if (action.executed) {
                    continue;
                }

                if (Arrays.asList(action.move.getConditions()).contains(MoveEffectActivation.ChangeTarget)) {
                    action.target = (Pokemon) action.move.activatePrimaryEffect(action.user, action.target, null, 0, 0, true, MoveEffectActivation.ChangeTarget);
                }

                if (action.actionTetheredBefore == null) {
                    action.actionSpeed = action.user.getStat(StatName.Spe).getEffectiveValue(action.target, action.move, false, null);

                    for (FieldCondition condition : generalField) {
                        if (condition.compare(FieldConditionList.trick_room)) {
                            action.actionSpeed = 10000 - action.actionSpeed;
                            break;
                        }
                    }
                }
            }

            for (int j = 0; j < priorityBracket.actions.size(); j++) {
                Action action = priorityBracket.actions.get(j);

                if (action.executed) {
                    continue;
                }

                for (Action otherAction : priorityBracket.actions) {
                    if (otherAction.actionTetheredBefore == action) {
                        otherAction.actionSpeed = action.actionSpeed;
                    }
                }

                int max = j;
                for (int k = j; k < priorityBracket.actions.size(); k++) {
                    Action actionK = priorityBracket.actions.get(k);
                    Action actionMax = priorityBracket.actions.get(max);

                    int speK = actionK.actionSpeed;
                    int speMax = actionMax.actionSpeed;

                    boolean kAtEnd = actionK.lockedAtEndOfBracket && actionK.actionTetheredBefore == null;
                    boolean maxAtEnd = actionMax.lockedAtEndOfBracket && actionMax.actionTetheredBefore == null;

                    if (speK > speMax && !(kAtEnd && !maxAtEnd)) {
                        max = k;
                    }

                    if (speK == speMax &&
                        actionMax.actionTetheredBefore == actionK) {
                        max = k;
                    }
                }

                Action temp = priorityBracket.actions.get(j);
                priorityBracket.actions.set(j, priorityBracket.actions.get(max));
                priorityBracket.actions.set(max, temp);
            }
        }
    }

    public static void addAction(Action action, Action actionBefore) {
        int priorityBracket = actionOrder.get(0).priority;

        for (int i = 0; i < actionOrder.size(); i++) {
            int j = i + 1;

            int currentBracket = actionOrder.get(i).priority;

            if (currentBracket == actionBefore.priorityBracket) {
                priorityBracket = i;
                break;
            }

            if (currentBracket > actionBefore.priorityBracket) {
                if (j >= actionOrder.size() || actionOrder.get(j).priority < actionBefore.priorityBracket) {
                    actionOrder.add(j, new PriorityBracket(actionBefore.priorityBracket));
                    priorityBracket = j;
                    break;
                }
            }
        }

        for (int i = 0; i < actionOrder.get(priorityBracket).actions.size(); i++) {
            if (actionOrder.get(priorityBracket).actions.get(i) == actionBefore) {
                actionOrder.get(priorityBracket).actions.add(i + 1, action);
                action.tether(actionBefore);
                action.priorityBracket = actionBefore.priorityBracket;
                action.actionSpeed = actionBefore.actionSpeed;
                break;
            }
        }
    }

    public static void removeAction(Action action) {
        for (int i = 0; i < actionOrder.size(); i++) {
            ArrayList<Action> actionsInBracket = actionOrder.get(i).actions;
            for (Action actionInBracket : actionsInBracket) {
                if (actionInBracket == action) {
                    actionOrder.get(i).actions.remove(actionInBracket);
                    return;
                }
            }
        }
    }

    public static void moveAction(Action action, Action actionBefore) {
        removeAction(action);
        addAction(action, actionBefore);
    }

    public static Pokemon[] orderPokemon(Pokemon pokemon1, Pokemon pokemon2) {
        Pokemon[] effectOrder = {pokemon1, pokemon2};

        for (int i = 0; i < effectOrder.length; i++) {
            int max = i;
            for (int j = i; j < effectOrder.length; j++) {
                Pokemon opponentJ = j == 0 ? effectOrder[1] : effectOrder[0];
                Pokemon opponentMax = max == 0 ? effectOrder[1] : effectOrder[0];

                int speJ = effectOrder[j].getStat(StatName.Spe).getEffectiveValue(opponentJ, null, false, null);
                int speMax = effectOrder[max].getStat(StatName.Spe).getEffectiveValue(opponentMax, null, false, null);

                for (FieldCondition condition : generalField) {
                    if (condition.compare(FieldConditionList.trick_room)) {
                        speJ = 10000 - speJ;
                        speMax = 10000 - speMax;
                        break;
                    }
                }

                if (speJ > speMax) {
                    max = j;
                }

                if (speJ == speMax) {
                    if (Math.random() < 0.5) {
                        max = j;
                    }
                }
            }

            Pokemon temp = effectOrder[i];
            effectOrder[i] = effectOrder[max];
            effectOrder[max] = temp;
        }

        return effectOrder;
    }

    public static void switchOut(Pokemon switchedPokemon, Pokemon incomingPokemon, Move switchMove) {
        int player;
        Pokemon opponent;

        if (switchedPokemon == yourActivePokemon) {
            player = 1;
            opponent = opponentActivePokemon;
        } else {
            player = 2;
            opponent = yourActivePokemon;
        }

        if (switchedPokemon.getVolatileStatus(StatusConditionList.readying_switch) == null) {
            boolean teamFainted = true;
            for (Pokemon pokemon : teams[switchedPokemon.getTeam()]) {
                if (pokemon != null &&
                    pokemon != yourActivePokemon &&
                    !faintCheck(pokemon, false)) {
                    teamFainted = false;
                }
            }

            if (!teamFainted) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            if (switchMove.getTemporaryProperties().contains(TemporaryProperty._Pivot_)) {
                if (teamFainted) {
                    return;
                }
                System.out.println(switchedPokemon.getName(true, true) + " went back to Player " + player + "!");
            } else if (switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                if (teamFainted) {
                    return;
                }
                System.out.println(switchedPokemon.getName(true, true) + " returned to Player " + player + "!");
            } else {
                System.out.println("Player " + player + " withdrew " + switchedPokemon.getName(false, false) + "!");
            }
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

            switchedPokemon.addVolatileStatus(StatusConditionList.readying_switch);

            Action switchAction = findAction(switchMove, switchedPokemon);

            addAction(new Action(switchMove, switchedPokemon, switchedPokemon), switchAction);

            if (!switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                for (int i = actionOrder.indexOf(currentPriorityBracket); i < actionOrder.size(); i++) {
                    PriorityBracket priorityBracket = actionOrder.get(i);
                    int currentJ = priorityBracket == currentPriorityBracket ? priorityBracket.actions.indexOf(currentAction) : 0;
                    for (int j = currentJ; j < priorityBracket.actions.size(); j++) {
                        Action action = priorityBracket.actions.get(j);

                        if (action.move != null &&
                            Arrays.asList(action.move.getConditions()).contains(MoveEffectActivation.OpponentSwitch) &&
                            action.target == switchedPokemon) {
                            action.move.activatePrimaryEffect(action.user, switchedPokemon, null, 0, 0, true, MoveEffectActivation.OpponentSwitch);
                            j--;
                            // TODO ajustar pra doubles
                        }
                    }
                }
            }
        } else {
            for (PriorityBracket priorityBracket : actionOrder) {
                for (int i = 0; i < priorityBracket.actions.size(); i++) {
                    Action action = priorityBracket.actions.get(i);
                    if (action.user == switchedPokemon) {
                        action.user = incomingPokemon;
                    } else if (action.target == switchedPokemon) {
                        action.target = incomingPokemon;
                    }
                }
            }

            for (StatusCondition condition : opponent.getVolatileStatusList()) {
                if (condition.getCauser() == switchedPokemon &&
                    Arrays.asList(condition.getActivation()).contains(StatusActivation.CauserLeaveField)) {
                    condition.activate(opponent, switchedPokemon, null, 0, true, StatusActivation.CauserLeaveField);
                }
            }

            if (player == 1) {
                yourActivePokemon = incomingPokemon;
            } else {
                opponentActivePokemon = incomingPokemon;
            }

            if (switchedPokemon.getAbility().shouldActivate(AbilityActivation.SwitchOut)) {
                switchedPokemon.getAbility().activate(switchedPokemon, opponent, null, null, 0, null, null, 0, AbilityActivation.SwitchOut);
            }

            if (switchMove.getTemporaryProperties().contains(TemporaryProperty._TransferValues_)) {
                switchedPokemon.transferValues(incomingPokemon);
            }
            switchedPokemon.restoreDefaultValues();

            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            if (!switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                System.out.println("Player " + player + " sent out " + incomingPokemon.getNameAndForm(false, false) + "!");
            } else {
                System.out.println(incomingPokemon.getNameAndForm(false, false) + " was dragged out!");
            }
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

            entryEffects(incomingPokemon, opponent);
        }
    }

    public static void faintReplacement() {
        ArrayList<Pokemon> replacingPokemon = new ArrayList<>();

        if (faintCheck(yourActivePokemon, false)) {
            switchInFaint(0);
            replacingPokemon.add(yourActivePokemon);
        }
        if (faintCheck(opponentActivePokemon, false)) {
            switchInFaint(1);
            replacingPokemon.add(opponentActivePokemon);
        }

        if (replacingPokemon.size() > 1) {
            for (Pokemon incomingPokemon : orderPokemon(replacingPokemon.get(0), replacingPokemon.get(1))) {
                Pokemon opposingPokemon = incomingPokemon.getTeam() == 0 ? opponentActivePokemon : yourActivePokemon;
                entryEffects(incomingPokemon, opposingPokemon);
            }
        } else if (replacingPokemon.size() == 1) {
            Pokemon opposingPokemon = replacingPokemon.get(0).getTeam() == 0 ? opponentActivePokemon : yourActivePokemon;
            entryEffects(replacingPokemon.get(0), opposingPokemon);
        }

        if (!battleOver &&
            (faintCheck(yourActivePokemon, false) || faintCheck(opponentActivePokemon, false))) {
            faintReplacement();
        }
    }

    public static void switchInFaint(int team) {
        System.out.println("\n------------------------------------------\n");

        Pokemon incomingPokemon = teams[team][pokemonToSwitchIn(team, true)];
        if (team == 0) {
            yourActivePokemon = incomingPokemon;
        } else {
            opponentActivePokemon = incomingPokemon;
        }

        System.out.println("\nPlayer " + (team + 1) + " sent out " + incomingPokemon.getNameAndForm(false, false) + "!");
        System.out.println("\n------------------------------------------\n");
    }

    public static void entryEffects(Pokemon incomingPokemon, Pokemon opponentPokemon) {
        for (Move move : delayedMoves.get(incomingPokemon.getTeam())) {
            if (Arrays.asList(move.getConditions()).contains(MoveEffectActivation.DelayedSwitch)) {
                move.activatePrimaryEffect(move.getUser(), incomingPokemon, null, 0, 0, true, MoveEffectActivation.DelayedSwitch);
            }
            if (Arrays.asList(move.getZConditions()).contains(MoveEffectActivation.DelayedSwitch)) {
                move.activateZEffect(move.getUser(), incomingPokemon, null, 0, 0, true, MoveEffectActivation.DelayedSwitch);
            }
        }
        for (int i = 0; i < delayedMoves.get(incomingPokemon.getTeam()).size(); i++) {
            Move move = delayedMoves.get(incomingPokemon.getTeam()).get(i);
            if (move.compare(MoveList._placeholder_)) {
                delayedMoves.get(incomingPokemon.getTeam()).remove(move);
                i--;
            }
        }

        for (FieldCondition condition : teamFields.get(incomingPokemon.getTeam())) {
            if (condition.shouldActivate(FieldActivation.Entry)) {
                condition.activate(incomingPokemon, opponentPokemon, null, null, null, null, 0, false, true, FieldActivation.Entry);

                if (battleOver) {
                    return;
                }
            }
        }

        if (incomingPokemon.getAbility().shouldActivate(AbilityActivation.Entry)) {
            incomingPokemon.getAbility().activate(incomingPokemon, opponentPokemon, null, null, 0, null, null, 0, AbilityActivation.Entry);
        }

        if (incomingPokemon.getItem().shouldActivate(ItemActivation.Entry)) {
            incomingPokemon.getItem().activate(incomingPokemon, incomingPokemon, opponentPokemon, null, 0, ItemActivation.Entry);
        }
    }

    public static Action findAction(Move move) {
        if (move == null) {
            return null;
        }

        for (PriorityBracket priorityBracket : actionOrder) {
            ArrayList<Action> actionsInBracket = priorityBracket.actions;
            for (Action action : actionsInBracket) {
                if (action.move.compare(move)) {
                    if (action.move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                        continue;
                    }
                    return action;
                }
            }
        }
        return null;
    }

    public static Action findAction(Move move, Pokemon user) {
        if (move == null) {
            return null;
        }

        for (PriorityBracket priorityBracket : actionOrder) {
            ArrayList<Action> actionsInBracket = priorityBracket.actions;
            for (Action action : actionsInBracket) {
                if (action.move.compare(move) &&
                    action.user == user) {
                    if (action.move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                        continue;
                    }
                    return action;
                }
            }
        }
        return null;
    }

    public static Action findAction(Pokemon user, boolean notSwitch) {
        for (PriorityBracket priorityBracket : actionOrder) {
            ArrayList<Action> actionsInBracket = priorityBracket.actions;
            for (Action action : actionsInBracket) {
                if (action.user == user) {
                    if (action.move.getCategory() == null &&
                        (!action.move.compare(MoveList._switch_) || notSwitch)) {
                        continue;
                    }
                    if (action.move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                        continue;
                    }
                    return action;
                }
            }
        }
        return null;
    }

    public static Action nextMove(Action action) {
        for (int i = 0; i < actionOrder.size(); i++) {
            PriorityBracket priorityBracket = actionOrder.get(i);
            for (int j = 0; j < priorityBracket.actions.size(); j++) {
                Action actionInBracket = priorityBracket.actions.get(j);
                if (actionInBracket == action) {
                    if (j < priorityBracket.actions.size()-1) {
                        return priorityBracket.actions.get(j+1);
                    } else if (i < actionOrder.size()-1) {
                        for (int k = 1; i+k < actionOrder.size(); k++) {
                            if (!actionOrder.get(i+k).actions.isEmpty()) {
                                return actionOrder.get(i+k).actions.getFirst();
                            }
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return null;
    }

    public static boolean actionIsAfterOther(Action action, Action other) {
        if (action == null || other == null || action == other) {
            return false;
        }

        boolean foundOther = false;

        for (PriorityBracket priorityBracket : actionOrder) {
            for (Action actionInBracket : priorityBracket.actions) {
                if (actionInBracket == action) {
                    if (foundOther) {
                        return true;
                    } else {
                        return false;
                    }
                }
                if (actionInBracket == other && !actionInBracket.move.getTemporaryProperties().contains(TemporaryProperty.Readying)) {
                    foundOther = true;
                }
            }
        }

        return false;
    }

    public static boolean faintCheck(Pokemon pokemon, boolean sayMessage) {
        if (pokemon.getCurrentHP() == 0) {
            if (sayMessage) {
                System.out.println("\n" + pokemon.getName(true, true) + " fainted!");
                pokemon.endNonVolatileStatus(false);

                Pokemon opponent;
                if (pokemon == yourActivePokemon) {
                    opponent = opponentActivePokemon;
                } else {
                    opponent = yourActivePokemon;
                }

                for (StatusCondition condition : opponent.getVolatileStatusList()) {
                    if (condition.getCauser() == pokemon &&
                        Arrays.asList(condition.getActivation()).contains(StatusActivation.CauserLeaveField)) {
                        condition.activate(opponent, pokemon, null, 0, true, StatusActivation.CauserLeaveField);
                    }
                }

                ArrayList<StatusCondition> conditionsToActivate = new ArrayList<>();

                for (StatusCondition condition : pokemon.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.Faint)) {
                        conditionsToActivate.add(condition);
                    }
                }

                pokemon.restoreDefaultValues();
                pokemonFaintedLastTurn[pokemon.getTeam()] = 2;

                if (!battleOverCheck()) {
                    if (pokemon.getAbility().shouldActivate(AbilityActivation.FaintUser)) {
                        pokemon.getAbility().activate(pokemon, opponent, null, null, 0, null, null, 0, AbilityActivation.FaintUser);
                    }

                    for (StatusCondition condition : conditionsToActivate) {
                        condition.activate(pokemon, opponent, null, 0, true, StatusActivation.Faint);
                    }

                    // TODO ajustar pra doubles
                    if (!Battle.faintCheck(opponent, false) &&
                        opponent.getAbility().shouldActivate(AbilityActivation.AnyFaint)) {
                        opponent.getAbility().activate(opponent, pokemon, null, null, 0, null, null, 0, AbilityActivation.AnyFaint);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean battleOverCheck() {
        boolean team0fainted = true;
        boolean team1fainted = true;

        for (Pokemon pokemon : teams[0]) {
            if (pokemon != null &&
                !faintCheck(pokemon, false)) {
                team0fainted = false;
            }
        }
        if (team0fainted && !battleOver) {
            losingTeam = 0;
        }

        for (Pokemon pokemon : teams[1]) {
            if (pokemon != null &&
                !faintCheck(pokemon, false)) {
                team1fainted = false;
            }
        }
        if (team1fainted && !battleOver) {
            losingTeam = 1;
        }

        if (team0fainted || team1fainted) {
            battleOver = true;
            return true;
        } else {
            return false;
        }
    }
}