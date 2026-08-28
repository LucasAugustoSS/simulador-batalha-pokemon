package com.github.lucasaugustoss.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.lucasaugustoss.App;
import com.github.lucasaugustoss.data.activationConditions.*;
import com.github.lucasaugustoss.data.classes.FieldCondition;
import com.github.lucasaugustoss.data.classes.Move;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.effects.MoveEffect;
import com.github.lucasaugustoss.data.objects.templates.MoveTemplate;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.data.properties.moves.Category;
import com.github.lucasaugustoss.data.properties.moves.EffectTarget;
import com.github.lucasaugustoss.data.properties.moves.InherentProperty;
import com.github.lucasaugustoss.data.properties.moves.MoveType;
import com.github.lucasaugustoss.data.properties.moves.TemporaryProperty;
import com.github.lucasaugustoss.data.properties.other.MessageType;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.simulator.actions.Action;
import com.github.lucasaugustoss.simulator.actions.PriorityBracket;

public class Battle {
    public static boolean battleStartedTrue = false;
    private static boolean battleOver = false;
    public static int losingTeam = -1;

    public static List<List<Pokemon>> teams;

    public static List<List<FieldCondition>> teamFields;
    public static List<FieldCondition> generalField;
    public static List<List<Move>> delayedMoves;

    private static Pokemon yourActivePokemon;
    private static Pokemon opponentActivePokemon;
    private static int yourActivePokemonIndex;
    private static int opponentActivePokemonIndex;
    private static List<Pokemon> activePokemon;
    private static List<Integer> remainingPokemon;

    public static List<PriorityBracket> actionOrder;
    public static PriorityBracket currentPriorityBracket;
    public static Action currentAction;
    public static Move lastUsedMove;
    public static int[] pokemonFaintedLastTurn;
    public static boolean[] megaEvolutionUsed;
    public static boolean[] zMoveUsed;
    public static boolean[] ultraBurstUsed;
    public static boolean[] terastallizationUsed;

    private static FieldCondition weather = Data.get().getFieldCondition("clear").cause(null, null, null);
    private static FieldCondition terrain = Data.get().getFieldCondition("no_terrain").cause(null, null, null);

    public static Pokemon getActivePokemon(int team) {
        return team == 0 ? yourActivePokemon : opponentActivePokemon;
    }

    public static Pokemon getOpposingPokemon(int team) {
        return team == 0 ? opponentActivePokemon : yourActivePokemon;
    }

    public static List<Pokemon> getActivePokemonList() {
        return activePokemon;
    }

    public static List<Pokemon> orderActivePokemonList() {
        return orderPokemon(activePokemon);
    }

    public static void changeActivePokemon(int team, Pokemon pokemon) {
        if (team == 0) {
            yourActivePokemon = pokemon;
        } else {
            opponentActivePokemon = pokemon;
        }
        activePokemon.set(team, pokemon);
    }

    public static FieldCondition getWeather(Move move) {
        if (move != null &&
            move.getUser().getAbility().shouldActivate(AbilityActivation.CallWeatherSelf)) {
            return (FieldCondition) move.getUser().getAbility().activate(move.getUser(), getOpposingPokemon(move.getUser().getTeam()), null, null, null, 0, null, null, 0, true, AbilityActivation.CallWeatherSelf);
        }

        if (yourActivePokemon.getAbility().shouldActivate(AbilityActivation.CallWeather)) {
            return (FieldCondition) yourActivePokemon.getAbility().activate(yourActivePokemon, opponentActivePokemon, null, null, null, 0, null, null, 0, true, AbilityActivation.CallWeather);
        }

        if (opponentActivePokemon.getAbility().shouldActivate(AbilityActivation.CallWeather)) {
            return (FieldCondition) opponentActivePokemon.getAbility().activate(opponentActivePokemon, yourActivePokemon, null, null, null, 0, null, null, 0, true, AbilityActivation.CallWeather);
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
            for (Pokemon activePokemon : orderActivePokemonList()) {
                Pokemon opponent = getOpposingPokemon(activePokemon.getTeam());

                if (activePokemon.getAbility().shouldActivate(AbilityActivation.WeatherChange)) {
                    activePokemon.getAbility().activate(activePokemon, opponent, null, null, null, 0, null, null, 0, true, AbilityActivation.WeatherChange);
                }
            }
        }
    }

    public static void setTerrain(FieldCondition newTerrain) {
        terrain = newTerrain;

        if (battleStartedTrue) {
            for (Pokemon activePokemon : orderActivePokemonList()) {
                Pokemon opponent = getOpposingPokemon(activePokemon.getTeam());

                if (activePokemon.getAbility().shouldActivate(AbilityActivation.TerrainChange)) {
                    activePokemon.getAbility().activate(activePokemon, opponent, null, null, null, 0, null, null, 0, true, AbilityActivation.TerrainChange);
                }
            }
        }
    }

    public static void removeGeneralFieldCondition(FieldCondition fieldCondition) {
        if (generalField.contains(fieldCondition)) {
            generalField.set(
                generalField.indexOf(fieldCondition),
                new FieldCondition(Data.get().getFieldCondition("placeholder"), 0, 0, null, null)
            );
        }
    }

    public static void removeTeamFieldCondition(FieldCondition fieldCondition, int fieldIndex) {
        List<FieldCondition> field = teamFields.get(fieldIndex);
        if (field.contains(fieldCondition)) {
            field.set(
                field.indexOf(fieldCondition),
                new FieldCondition(Data.get().getFieldCondition("placeholder"), 0, 0, null, null)
            );
        }
    }

    public static void start(List<Pokemon> team0, List<Pokemon> team1) {
        teams = new ArrayList<>();
        teams.add(team0);
        teams.add(team1);

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

        for (List<Pokemon> team : teams) {
            for (Pokemon pokemon : team) {
                if (pokemon != null) {
                    pokemon.getAbility().setActive(true);
                }
            }
        }

        makeCoolPokeball();

        System.out.println("\n\nYou are challenged by Pokémon Trainer Player 2!");

        yourActivePokemon = teams.get(0).get(0);
        opponentActivePokemon = teams.get(1).get(0);
        activePokemon = new ArrayList<>();
        activePokemon.add(yourActivePokemon);
        activePokemon.add(opponentActivePokemon);

        remainingPokemon = new ArrayList<>();
        remainingPokemon.add(teams.get(0).size());
        remainingPokemon.add(teams.get(1).size());

        yourActivePokemon.addTurnOnField();
        opponentActivePokemon.addTurnOnField();

        yourActivePokemonIndex = 0;
        opponentActivePokemonIndex = 0;

        for (Pokemon pokemon : orderActivePokemonList()) {
            MessageHandler.newGroup();
            entryEffects(pokemon, getOpposingPokemon(pokemon.getTeam()));
            MessageHandler.endGroup();
            MessageHandler.printStack();
        }

        weather.setActivatedThisTurn(false);
        terrain.setActivatedThisTurn(false);
        for (FieldCondition condition : generalField) {
            condition.setActivatedThisTurn(false);
        }
        for (List<FieldCondition> field : teamFields) {
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
                    action.user.setCurrentAction(action);

                    // teste de ordem
                    if (App.debug) {
                        System.out.println();
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

                    if (!faintCheck(action.user, null, false) || (
                            action.move.compare(Data.get().getMove("_switch_")) &&
                            action.user.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) != null
                        )) {
                        MessageHandler.newGroup();
                        MessageHandler.currentType = MessageType.M_START;
                        if (action.user.getBattleAction() == 1 ||
                            action.user.getBattleAction() == 4 ||
                            action.user.getBattleAction() == 5 ||
                            action.user.getBattleAction() == 6) {
                            useMove(
                                action.move, action.user, action.target,
                                action.move.getTemporaryProperties().contains(TemporaryProperty.Readying),
                                action.move.getTemporaryProperties().contains(TemporaryProperty.Called)
                            );
                        } else {
                            int teamIndex = (action.user).getTeam();
                            int pokemonIndex;
                            if (teamIndex == 0) {
                                pokemonIndex = yourActivePokemonIndex;
                            } else {
                                pokemonIndex = opponentActivePokemonIndex;
                            }
                            switchOut(action.user, teams.get(teamIndex).get(pokemonIndex), action.move);
                        }
                        MessageHandler.endGroup();
                    }
                    action.executed = true;
                    action.user.setCurrentAction(null);
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
                MessageHandler.newGroup();
                MessageHandler.currentType = MessageType.EOT_EFFECT;
                endOfTurnEffects(orderActivePokemonList());
                MessageHandler.endGroup();
            }

            MessageHandler.printStack();

            if (!battleOver) {
                faintReplacement();
            }

            for (Pokemon pokemon : activePokemon) {
                if (!faintCheck(pokemon, null, false)) {
                    pokemon.addTurnOnField();
                }
                pokemon.setDamagedThisTurn(false, null);
                pokemon.setItemConsumedThisTurn(false);
                pokemon.setCurrentMoveFailed(false);
                pokemon.setJustSwitchedIn(false);
                if (pokemon.getItem().getHolder() != pokemon.getItem().getOriginalHolder()) {
                    pokemon.getItem().setOriginalHolder(pokemon.getItem().getHolder());
                }
            }

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
            for (List<FieldCondition> field : teamFields) {
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
                userPokemon.compare(Data.get().getPokemon("rayquaza"), true) && userPokemon.getMove(Data.get().getMove("dragon_ascent")) != null && userPokemon.getItem().getType() != ItemType.ZCrystal) {
                extraOption = "Mega Evolve";
            }
            if (userPokemon.getItem().getType() == ItemType.ZCrystal && userPokemon.getItem().heldByValidForm(false)) {
                if (userPokemon.getItem().compare(Data.get().getItem("ultranecrozium_z")) &&
                    (userPokemon.compareWithForm(Data.get().getPokemon("necrozma_dusk_mane")) || userPokemon.compareWithForm(Data.get().getPokemon("necrozma_dawn_wings")))) {
                    if (userPokemon.getItem().heldByValidForm(true)) {
                        extraOption = "Ultra Burst";
                    }
                } else {
                    extraOption = "Z-Move";
                }
            }
            if (userPokemon.getItem().compare(Data.get().getItem("stellar_orb")) && userPokemon.getItem().heldByValidUser(true)) {
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
                                boolean abilityBlocked = false;
                                boolean itemBlocked = false;
                                boolean fieldBlocked = false;
                                boolean statusBlocked = false;

                                // sem PP
                                if (move.getCurrentPP() <= 0) {
                                    noPP = true;
                                }

                                // bloqueado por habilidade
                                if (userPokemon.getAbility().shouldActivate(AbilityActivation.TrySelectMove)) {
                                    abilityBlocked = !((boolean) userPokemon.getAbility().activate(userPokemon, opposingPokemon, move, null, null, 0, null, null, 0, false, AbilityActivation.TrySelectMove));
                                }

                                // bloqueado por item
                                if (userPokemon.getItem().shouldActivate(ItemActivation.TrySelectMove)) {
                                    itemBlocked = !((boolean) userPokemon.getItem().activate(userPokemon, userPokemon, opposingPokemon, move, null, false, ItemActivation.TrySelectMove));
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
                                        statusBlocked = !((boolean) condition.activate(userPokemon, opposingPokemon, move, null, false, StatusActivation.TrySelectMove));
                                        if (statusBlocked) {
                                            break;
                                        }
                                    }
                                }
                                if (!statusBlocked) {
                                    for (StatusCondition condition : opposingPokemon.getVolatileStatusList()) {
                                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTrySelectMove)) {
                                            statusBlocked = !((boolean) condition.activate(opposingPokemon, userPokemon, move, null, false, StatusActivation.OpponentTrySelectMove));
                                            if (statusBlocked) {
                                                break;
                                            }
                                        }
                                    }
                                }


                                if (noPP || abilityBlocked || itemBlocked || fieldBlocked || statusBlocked) {
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
                                            if (userPokemon.getAbility().shouldActivate(AbilityActivation.TrySelectMove)) {
                                                canUse = (boolean) userPokemon.getAbility().activate(userPokemon, opposingPokemon, userPokemon.getMoves()[option-1], null, null, 0, null, null, 0, true, AbilityActivation.TrySelectMove);
                                            }

                                            if (canUse) {
                                                if (userPokemon.getItem().shouldActivate(ItemActivation.TrySelectMove)) {
                                                    canUse = (boolean) userPokemon.getItem().activate(userPokemon, userPokemon, opposingPokemon, userPokemon.getMoves()[option-1], null, true, ItemActivation.TrySelectMove);
                                                }
                                            }

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
                                                        canUse = (boolean) condition.activate(userPokemon, opposingPokemon, userPokemon.getMoves()[option-1], null, true, StatusActivation.TrySelectMove);
                                                        if (!canUse) {
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if (canUse) {
                                                for (StatusCondition condition : opposingPokemon.getVolatileStatusList()) {
                                                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTrySelectMove)) {
                                                        canUse = (boolean) condition.activate(opposingPokemon, userPokemon, userPokemon.getMoves()[option-1], null, true, StatusActivation.OpponentTrySelectMove);
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
                                                MoveTemplate zMove = userPokemon.getItem().getZMove();
                                                MoveTemplate zMoveOrigin = userPokemon.getItem().getZMoveOrigin();

                                                if (zMoveOrigin == null && moveToUse.getTrueType().compare(zMove.getType())) {
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
                                            MoveTemplate zMove = userPokemon.getItem().getZMove();
                                            MoveTemplate zMoveOrigin = userPokemon.getItem().getZMoveOrigin();

                                            if (move != null) {
                                                boolean isTurned = false;
                                                count++;

                                                System.out.print(count + ". ");
                                                if (zMoveOrigin == null) {
                                                    if (move.getTrueType().compare(zMove.getType())) {
                                                        if (move.getCategory() != Category.Status) {
                                                            System.out.println(zMove.getName());
                                                        } else {
                                                            System.out.println("Z-" + move.getTrueName());
                                                        }
                                                        isTurned = true;
                                                    } else {
                                                        System.out.println("-");
                                                    }
                                                } else {
                                                    if (move.compare(zMoveOrigin)) {
                                                        System.out.println(zMove.getName());
                                                        isTurned = true;
                                                    } else {
                                                        System.out.println("-");
                                                    }
                                                }
                                                if (isTurned) {
                                                    System.out.println("Type: " + zMove.getType().getName() + "\t" +
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
                            return new Move(Data.get().getMove("struggle"), userPokemon);
                        }

                        break;

                    case 2:
                        boolean trapped = false;
                        boolean cantTrap = userPokemon.hasType(Data.get().getType("ghost"));
                        if (!cantTrap &&
                            userPokemon.getAbility().shouldActivate(AbilityActivation.BlockSwitch) &&
                            !((boolean) userPokemon.getAbility().activate(userPokemon, null, null, null, null, 0, null, null, 0, true, AbilityActivation.BlockSwitch))) {
                            cantTrap = true;
                        }

                        if (!cantTrap) {
                            if (opposingPokemon.getAbility().shouldActivate(AbilityActivation.OpponentTrySwitch) &&
                                (boolean) opposingPokemon.getAbility().activate(opposingPokemon, userPokemon, null, null, null, 0, null, null, 0, true, AbilityActivation.OpponentTrySwitch)) {
                                trapped = true;
                                break;
                            }

                            for (StatusCondition condition : userPokemon.getVolatileStatusList()) {
                                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TrySwitch) &&
                                    (boolean) condition.activate(userPokemon, opposingPokemon, condition.getCausingMove(), null, true, StatusActivation.TrySwitch)) {
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
                            return new Move(Data.get().getMove("_switch_"), userPokemon);
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

    public static void useMove(Move move, Pokemon user, Pokemon target, boolean readyingAtStart, boolean called) {
        if (move == null) {
            return;
        }

        move.setTarget(target);

        if (move.compare(Data.get().getMove("_switch_"))) {
            if (user.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) == null) {
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
                        incomingPokemon = teams.get(user.getTeam()).get(index);

                        if (user.getTeam() == 0) {
                            yourActivePokemonIndex = index;
                        } else {
                            opponentActivePokemonIndex = index;
                        }
                    } while (incomingPokemon == null ||
                             incomingPokemon == Battle.opponentActivePokemon ||
                             Battle.faintCheck(incomingPokemon, null, false));
                } else {
                    MessageHandler.endGroup();
                    MessageHandler.printStack();
                    System.out.println("\n------------------------------------------\n");
                    incomingPokemon = teams.get(user.getTeam()).get(pokemonToSwitchIn(user.getTeam(), true));
                    System.out.println("\n------------------------------------------\n");
                    MessageHandler.newGroup();
                    MessageHandler.currentType = MessageType.M_SUCCESS;
                }
                switchOut(user, incomingPokemon, move);
            }
        } else if (move.compare(Data.get().getMove("_mega_evolve_"))) {
            for (PokemonTemplate form : user.getForms()) {
                if (!user.compare(Data.get().getPokemon("rayquaza"), true) && form.compareWithForm(user.getItem().getTransformsInto()) ||
                    user.compare(Data.get().getPokemon("rayquaza"), true) && form.compareWithForm(Data.get().getPokemon("rayquaza_mega"))) {
                    String key = "mega" + (
                        user.compare(Data.get().getPokemon("rayquaza"), true) ?
                            " rayquaza" : ""
                    );
                    MessageHandler.add("gimmick", key, Map.of(
                        "Pokemon", user.getName(true, false),
                        "Item", user.getItem().getName(),
                        "Player", "Player " + (user.getTeam() + 1)
                    ));

                    MessageHandler.currentType = MessageType.M_SUCCESS;
                    MessageHandler.add("gimmick", "mega success", Map.of(
                        "Pokemon", user.getName(true, false),
                        "Mega Form", user.getSpecies() + form.getForm().replace("Mega", "")
                    ));

                    user.changeForm(form.getForm());
                    megaEvolutionUsed[user.getTeam()] = true;
                    break;
                }
            }
        } else if (move.compare(Data.get().getMove("_ultra_burst_"))) {
            for (PokemonTemplate form : user.getForms()) {
                if (form.compareWithForm(Data.get().getPokemon("necrozma_ultra"))) {
                    MessageHandler.add("gimmick", "ultra burst", Map.of(
                        "Pokemon", user.getName(true, false)
                    ));

                    MessageHandler.currentType = MessageType.M_SUCCESS;
                    MessageHandler.add("gimmick", "ultra burst success", Map.of(
                        "Pokemon", user.getName(true, false)
                    ));

                    user.changeForm(form.getForm());
                    ultraBurstUsed[user.getTeam()] = true;
                    break;
                }
            }
        } else if (move.compare(Data.get().getMove("_terastallize_"))) {
            for (PokemonTemplate form : user.getForms()) {
                if (form.compareWithForm(Data.get().getPokemon("terapagos_stellar"))) {
                    MessageHandler.add("gimmick", "tera", Map.of(
                        "Pokemon", user.getName(true, false),
                        "Item", user.getItem().getName()
                    ));

                    MessageHandler.currentType = MessageType.M_SUCCESS;
                    MessageHandler.add("gimmick", "tera success", Map.of(
                        "Pokemon", user.getName(true, false),
                        "Type", "Stellar"
                    ));

                    user.changeForm(form.getForm());
                    terastallizationUsed[user.getTeam()] = true;
                    break;
                }
            }
        } else if (readyingAtStart) {
            MessageHandler.currentType = MessageType.M_SUCCESS;
            if (move.primaryShouldActivate(MoveEffectActivation.TurnStart)) {
                move.activatePrimary(user, target, null, null, 0, null, true, MoveEffectActivation.TurnStart);
            }
        } else {
            if (move.primaryShouldActivate(MoveEffectActivation.CallTypeStart)) {
                move.activatePrimary(user, target, move.getTrueType(), null, 0, null, false, MoveEffectActivation.CallTypeStart);
            }

            if (user.getAbility().shouldActivate(move, AbilityActivation.StartMessage)) {
                user.getAbility().activate(user, target, move, null, null, 0, null, null, 0, true, AbilityActivation.StartMessage);
            }

            if (!move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                for (StatusCondition condition : user.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.ChangeMove)) {
                        move = (Move) condition.activate(user, target, move, null, true, StatusActivation.ChangeMove);
                    }
                }
            }

            boolean canMove = true;

            MessageHandler.currentType = MessageType.M_START;
            if (!called &&
                !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                // condições de status não-voláteis
                if (Arrays.asList(user.getNonVolatileStatus().getActivation()).contains(StatusActivation.TryAct)) {
                    canMove = (boolean) user.getNonVolatileStatus().activate(user, target, move, null, true, StatusActivation.TryAct);
                }

                // condições de status voláteis
                if (canMove && target != user) {
                    for (StatusCondition condition : target.getVolatileStatusList()) {
                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTryAct)) {
                            canMove = (boolean) condition.activate(target, user, move, null, true, StatusActivation.OpponentTryAct);
                        }
                    }
                }

                if (canMove) {
                    for (StatusCondition condition : user.getVolatileStatusList()) {
                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.TryAct)) {
                            canMove = (boolean) condition.activate(user, target, move, null, true, StatusActivation.TryAct);
                        }

                        if (battleOver) {
                            return;
                        }
                    }
                }

                // condições de campo (geral)
                if (canMove) {
                    for (FieldCondition condition : generalField) {
                        if (condition.shouldActivate(FieldActivation.TryAct)) {
                            canMove = (boolean) condition.activate(user, target, move, null, null, null, 0, false, true, FieldActivation.TryAct);
                        }
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
                            canMove2 = (boolean) condition.activate(user, target, move, null, true, StatusActivation.TryMove);
                        }

                        if (battleOver) {
                            return;
                        }
                    }

                    // condições de status não-voláteis
                    if (canMove2) {
                        if (Arrays.asList(user.getNonVolatileStatus().getActivation()).contains(StatusActivation.TryMove)) {
                            canMove2 = (boolean) user.getNonVolatileStatus().activate(user, target, move, null, true, StatusActivation.TryMove);
                        }
                    }
                }

                if (canMove2) {
                    if (move.isZMove() || move.isZPowered()) {
                        if (!called) {
                            boolean zEffectActivated = false;
                            if (move.isZPowered() &&
                                move.getZEffect() != null &&
                                move.zShouldActivate(MoveEffectActivation.ZNormal)) {
                                zEffectActivated = (boolean) move.activateZ(user, target, null, null, 0, null, true, MoveEffectActivation.ZNormal);
                            }

                            if (move.isZMove() || !zEffectActivated) {
                                MessageHandler.add("gimmick", "z-move", Map.of(
                                    "Pokemon", user.getName(true, false)
                                ));
                            }

                            MessageHandler.add("gimmick", "z-move success", Map.of(
                                "Pokemon", user.getName(true, false)
                            ));
                        }

                        if (move.isZMove() && !move.isSignatureZMove() && !move.compare(move.getMoveOrigin().turnZMove())) {
                            move = new Move(move.getMoveOrigin().turnZMove(), move.getMoveOrigin(), move.getUser());

                            if (move.getMoveOrigin().getMessages() != null) {
                                MessageHandler.add(move.getMessages().getName(), "z transform", Map.of(
                                    "Move", move.getName()
                                ));
                            }
                        }
                    }

                    if (move.compare(Data.get().getMove("struggle"))) {
                        MessageHandler.add("struggle", "use", Map.of(
                            "Pokemon", user.getName(true, false)
                        ));
                    }

                    if (user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn")) == null &&
                        !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                        MessageHandler.add("move", "use", Map.of(
                            "Pokemon", user.getName(true, false),
                            "Move", (move.isZPowered() ? "Z-" : "") + move.getName()
                        ));
                    }

                    if (!move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                        if (user.getAbility().shouldActivate(move, AbilityActivation.UseMove)) {
                            user.getAbility().activate(user, target, move, null, null, 0, null, null, 0, true, AbilityActivation.UseMove);
                        }

                        if (user.getItem().shouldActivate(ItemActivation.UseMove)) {
                            user.getItem().activate(user, user, target, move, null, true, ItemActivation.UseMove);
                        }
                    }

                    MessageHandler.currentType = MessageType.M_FAIL;

                    boolean[] moveSuccessful = successCheck(move, user, target);
                    boolean moveHit = false;
                    if (moveSuccessful[0]) {
                        moveHit = accuracyCheck(move, user, target);
                        if (moveHit) {
                            MessageHandler.currentType = MessageType.M_SUCCESS;
                            Damage.directDamage(user, target, move, false);

                            for (Pokemon pokemon : orderActivePokemonList()) {
                                if (pokemon != user) {
                                    if (pokemon.getAbility().shouldActivate(move, AbilityActivation.AnyMoveSuccess)) {
                                        pokemon.getAbility().activate(pokemon, getOpposingPokemon(pokemon.getTeam()), move, null, null, 0, null, null, 0, true, AbilityActivation.AnyMoveSuccess);
                                    }
                                }
                            }
                        } else {
                            MessageHandler.add("move", "miss", Map.of(
                                "Pokemon", target.getName(true, false)
                            ));
                        }
                    }

                    if (!moveHit) {
                        if (moveSuccessful[1]) {
                            if (move.primaryShouldActivate(MoveEffectActivation.Miss)) {
                                move.activatePrimary(user, target, null, null, 0, null, true, MoveEffectActivation.Miss);
                            }
                            faintCheck(user, null, true);

                            // o efeito de Round executa mesmo se o primeiro uso errar
                            for (int i = actionOrder.indexOf(currentPriorityBracket); i < actionOrder.size(); i++) {
                                PriorityBracket priorityBracket = actionOrder.get(i);
                                int currentJ = priorityBracket == currentPriorityBracket ? priorityBracket.actions.indexOf(currentAction) : 0;
                                for (int j = currentJ; j < priorityBracket.actions.size(); j++) {
                                    Action action = priorityBracket.actions.get(j);

                                    if (action.move != null &&
                                        Arrays.asList(action.move.getPrimaryConditions()).contains(MoveEffectActivation.OpponentMove) &&
                                        action.target == user) {
                                        action.move.activatePrimary(action.user, user, null, null, 0, null, true, MoveEffectActivation.OpponentMove);
                                        j--;
                                        // TODO ajustar pra doubles
                                    }
                                }
                            }
                        }

                        if (move.primaryShouldActivate(MoveEffectActivation.Fail)) {
                            move.activatePrimary(user, target, null, null, 0, null, true, MoveEffectActivation.Fail);
                        }

                        user.setReadiedMove(null);
                        move.setConsecutiveUses(-1);
                        user.setCurrentMoveFailed(true);

                        for (StatusCondition condition : user.getVolatileStatusList()) {
                            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.FailMove)) {
                                condition.activate(user, user, move, null, true, StatusActivation.FailMove);
                            }
                        }
                    }

                    Move affectedMove = move.getMoveOrigin() == null ? move : move.getMoveOrigin();
                    if (user.getVolatileStatus(Data.get().getStatusCondition("charging_turn")) == null &&
                        user.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn")) == null &&
                        user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn")) == null &&
                        user.getVolatileStatus(Data.get().getStatusCondition("rampage")) == null &&
                        user.getVolatileStatus(Data.get().getStatusCondition("locked")) == null &&
                        !called &&
                        !move.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                        if (!affectedMove.compare(Data.get().getMove("struggle"))) {
                            int ppConsumption = 1;

                            if (target != user &&
                                target.getAbility().shouldActivate(AbilityActivation.PPConsumption)) {
                                ppConsumption = (int) target.getAbility().activate(target, user, affectedMove, null, null, 0, null, null, 0, true, AbilityActivation.PPConsumption);
                            }

                            if (affectedMove.getCurrentPP()-ppConsumption < 0) {
                                affectedMove.setCurrentPP(0);
                            } else {
                                affectedMove.setCurrentPP(affectedMove.getCurrentPP()-ppConsumption);
                            }
                        }

                        if (affectedMove != user.getLastUsedMove()) {
                            if (user.getLastUsedMove() != null) {
                                affectedMove.setConsecutiveUses(0);
                                user.getLastUsedMove().setConsecutiveUses(0);
                            }
                            user.setLastUsedMove(!affectedMove.compare(Data.get().getMove("struggle")) ? affectedMove : null);
                        }
                        affectedMove.addUse();
                    }

                    lastUsedMove = affectedMove;
                    if (move.isZMove() || move.isZPowered()) {
                        zMoveUsed[user.getTeam()] = true;
                    }
                } else {
                    user.setReadiedMove(null);
                    move.setConsecutiveUses(0);
                    user.setCurrentMoveFailed(true);

                    for (StatusCondition condition : user.getVolatileStatusList()) {
                        if (Arrays.asList(condition.getActivation()).contains(StatusActivation.FailMove)) {
                            condition.activate(user, user, move, null, true, StatusActivation.FailMove);
                        }
                    }
                }
            } else {
                user.setReadiedMove(null);
                move.setConsecutiveUses(0);
                user.setCurrentMoveFailed(true);

                for (StatusCondition condition : user.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.FailMove)) {
                        condition.activate(user, user, move, null, true, StatusActivation.FailMove);
                    }
                }
            }

            user.setLastMoveFailed(user.currentMoveFailed());

            if (move.getTemporaryProperties().contains(TemporaryProperty.PranksterBoosted)) {
                move.removeProperty(TemporaryProperty.PranksterBoosted);
            }
            if (move.getTemporaryProperties().contains(TemporaryProperty.ParentalBondNerfed)) {
                move.removeProperty(TemporaryProperty.ParentalBondNerfed);
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

        move.revertType();
        move.setTarget(null);
        user.orderVolatileStatusList();
    }

    private static void endOfTurnEffects(List<Pokemon> activePokemon) {
        // clima
        weather.countDown();
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, null, false)) {
                Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

                if (getWeather(null).shouldActivate(FieldActivation.EndOfTurn)) {
                    getWeather(null).activate(pokemon, opponent, null, null, null, null, 0, false, true, FieldActivation.EndOfTurn);

                    if (battleOver) {
                        return;
                    }
                }
            }
        }

        // terreno
        terrain.countDown();
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, null, false)) {
                Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

                if (terrain.shouldActivate(pokemon, FieldActivation.EndOfTurn)) {
                    terrain.activate(pokemon, opponent, null, null, null, null, 0, false, true, FieldActivation.EndOfTurn);
                }
            }
        }

        // habilidades
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, null, false)) {
                Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

                if (pokemon.getAbility().shouldActivate(AbilityActivation.TurnEnd)) {
                    pokemon.getAbility().activate(pokemon, opponent, null, null, null, 0, null, null, 0, true, AbilityActivation.TurnEnd);
                }
            }
        }
        for (Pokemon pokemon : activePokemon) {
            Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

            if (pokemon.getAbility().shouldActivate(AbilityActivation.AfterTurnEnd)) {
                pokemon.getAbility().activate(pokemon, opponent, null, null, null, 0, null, null, 0, true, AbilityActivation.AfterTurnEnd);
            }
        }

        // condições de status voláteis
        for (Pokemon pokemon : activePokemon) {
            Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

            for (StatusCondition condition : pokemon.getVolatileStatusList()) {
                if (Arrays.asList(condition.getActivation()).contains(StatusActivation.EndOfTurn)) {
                    condition.activate(pokemon, opponent, null, null, true, StatusActivation.EndOfTurn);
                }

                if (battleOver) {
                    return;
                }

                if (faintCheck(pokemon, null, false)) {
                    break;
                }
            }
        }

        // condições de status não-voláteis
        for (Pokemon pokemon : activePokemon) {
            Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

            if (Arrays.asList(pokemon.getNonVolatileStatus().getActivation()).contains(StatusActivation.EndOfTurn)) {
                pokemon.getNonVolatileStatus().activate(pokemon, opponent, null, null, true, StatusActivation.EndOfTurn);
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
                    if (!faintCheck(pokemon, null, false)) {
                        Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

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
            if (condition.compare(Data.get().getFieldCondition("placeholder"))) {
                generalField.remove(condition);
                i--;
            }
        }

        // campos de equipes
        for (List<FieldCondition> field : teamFields) {
            for (FieldCondition condition : field) {
                condition.countDown(field);

                if (condition.shouldActivate(FieldActivation.EndOfTurn)) {
                    for (Pokemon pokemon : activePokemon) {
                        if (!faintCheck(pokemon, null, false)) {
                            Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

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
                if (condition.compare(Data.get().getFieldCondition("placeholder"))) {
                    field.remove(condition);
                    i--;
                }
            }
        }

        // movimentos atrasados
        for (List<Move> teamDelayedMoves : delayedMoves) {
            for (Move move : teamDelayedMoves) {
                for (MoveEffect effect : move.getPrimaryEffect()) {
                    effect.countDown();

                    if (move.primaryShouldActivate(MoveEffectActivation.DelayedTurnEnd)) {
                        for (Pokemon pokemon : activePokemon) {
                            if (delayedMoves.indexOf(teamDelayedMoves) == pokemon.getTeam()) {
                                Pokemon target = effect.getTarget() == EffectTarget.Target ? getOpposingPokemon(pokemon.getTeam()) : pokemon;
                                move.activatePrimarySingle(effect, move.getUser(), target, null, null, 0, null, true, MoveEffectActivation.DelayedTurnEnd);

                                if (battleOver) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < teamDelayedMoves.size(); i++) {
                Move move = teamDelayedMoves.get(i);
                if (move.compare(Data.get().getMove("_placeholder_"))) {
                    teamDelayedMoves.remove(move);
                    i--;
                }
            }
        }

        // itens
        for (Pokemon pokemon : activePokemon) {
            if (!faintCheck(pokemon, null, false)) {
                Pokemon opponent = getOpposingPokemon(pokemon.getTeam());

                if (pokemon.getItem().shouldActivate(ItemActivation.EndOfTurn)) {
                    pokemon.getItem().activate(pokemon, pokemon, opponent, null, null, true, ItemActivation.EndOfTurn);
                }
            }
        }
    }

    public static boolean[] successCheck( // 0: sucesso, 1: acerto
        Move move, Pokemon user, Pokemon target
    ) {
        // falha por falta de alvo
        if (faintCheck(target, null, false)) {
            MessageHandler.add("move", "fail no target", null);
            return new boolean[] {false, false};
        }

        // falha por condição de status de qualquer outro Pokémon
        // ex: Snatch e Hydrokinesis
        // TODO ajustar para doubles
        Pokemon opponent = getOpposingPokemon(user.getTeam());
        for (StatusCondition condition : opponent.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTryUseMoveAny)) {
                boolean[] success = (boolean[]) condition.activate(opponent, user, move, null, true, StatusActivation.OpponentTryUseMoveAny);
                if (!success[0]) {
                    return new boolean[] {false, success[1]};
                }
            }
        }

        // falta por habilidade do usuário
        // ex: Damp
        if (user.getAbility().shouldActivate(move, AbilityActivation.TryUseMove)) {
            boolean[] success = (boolean[]) user.getAbility().activate(user, user, move, null, null, 0, null, null, 0, true, AbilityActivation.TryUseMove);
            if (!success[0]) {
                return new boolean[] {false, success[1]};
            }
        }

        // falha por usuário errado
        // ex: Dark Void, Hyperspace Fury
        if (!move.getTemporaryProperties().contains(TemporaryProperty.Reflected)) {
            if (move.getExclusiveUser() != null) {
                if (!user.compare(move.getExclusiveUser(), false)) {
                    MessageHandler.add("move", "fail species", Map.of(
                        "Pokemon", user.getName(true, false)
                    ));
                    return new boolean[] {false, false};
                } else if (move.isExclusiveForm() &&
                           !user.compareWithForm(move.getExclusiveUser())) {
                    MessageHandler.add(move.getTemplate().getID(), "fail form", Map.of(
                        "Pokemon", user.getName(true, false)
                    ));
                    return new boolean[] {false, false};
                }
            }
        }

        // falha por imunidade
        // imunidade não aplica quando:
        // - o usuário está carregando ou recarregando (ex: tipo Fantasma usando Razor Wind)
        // - o movimento é atrasado e ainda não é hora de acertar
        boolean charging = user.getVolatileStatus(Data.get().getStatusCondition("charging_turn")) != null ||
                           user.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn")) != null;
        boolean recharging = user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn")) != null;

        boolean properHit = !move.isMoveType(MoveType.Delayed) ||
                            move.getTemporaryProperties().contains(TemporaryProperty.FutureHit);
        boolean immune = (move.getCategory() != Category.Status || move.hasInherentProperty(InherentProperty.TypeChartAffected)) && Damage.ineffective(move, target) ||
                          move.getCategory() == Category.Status && Damage.ineffectiveStatus(move, target);
        if (!charging && !recharging && properHit && immune) {
            MessageHandler.add("effectiveness", "ineffective", Map.of(
                "Pokemon", target.getName(true, false)
            ));
            return new boolean[] {false, true};
        }

        // falha por condição própria
        // ex: Fake Out
        List<boolean[]> effectSuccesses = new ArrayList<>();

        for (MoveEffect moveEffect : move.getPrimaryEffect()) {
            if (moveEffect.shouldActivate(MoveEffectActivation.TryUse)) {
                effectSuccesses.add((boolean[]) move.activatePrimarySingle(moveEffect, user, target, null, null, 0, null, true, MoveEffectActivation.TryUse));
            }
        }

        boolean printMessage = true;
        if (!effectSuccesses.isEmpty()) {
            boolean anySuccess = false;
            for (boolean[] success : effectSuccesses) {
                if (success[0] == true) {
                    anySuccess = true;
                    break;
                } else if (success[1] == false) {
                    printMessage = false;
                }
            }

            if (!anySuccess) {
                if (printMessage) {
                    MessageHandler.add("move", "fail", null);
                }
                return new boolean[] {false, false};
            }
        }

        // falha por clima e terreno
        // ex: Desolate Land, Psychic Terrain
        if (getWeather(move).shouldActivate(target, FieldActivation.TryUseMove)) {
            boolean[] success = (boolean[]) getWeather(move).activate(target, user, move, null, null, null, 0, false, true, FieldActivation.TryUseMove);
            if (!success[0]) {
                return new boolean[] {false, success[1]};
            }
        }

        if (terrain.shouldActivate(target, FieldActivation.TryUseMove)) {
            boolean[] success = (boolean[]) terrain.activate(target, user, move, null, null, null, 0, false, true, FieldActivation.TryUseMove);
            if (!success[0]) {
                return new boolean[] {false, success[1]};
            }
        }

        // falha por condição de status do alvo
        // ex: Protect, Magic Coat
        for (StatusCondition condition : target.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentTryUseMoveTargeted)) {
                boolean[] success = (boolean[]) condition.activate(target, user, move, null, true, StatusActivation.OpponentTryUseMoveTargeted);
                if (!success[0]) {
                    return new boolean[] {false, success[1]};
                }
            }
        }

        // falha por condição de campo do lado do alvo
        // ex: Wide Guard
        for (FieldCondition condition : teamFields.get(target.getTeam())) {
            if (Arrays.asList(condition.getFieldActivation()).contains(FieldActivation.OpponentTryUseMove)) {
                boolean[] success = (boolean[]) condition.activate(target, user, move, null, null, null, 0, false, true, FieldActivation.OpponentTryUseMove);
                if (!success[0]) {
                    return new boolean[] {false, success[1]};
                }
            }
        }

        // falha por habilidade do alvo
        if (target.getAbility().shouldActivate(move, AbilityActivation.TryHitUser)) {
            boolean[] success = (boolean[]) target.getAbility().activate(target, user, move, null, null, 0, null, null, 0, true, AbilityActivation.TryHitUser);
            if (!success[0]) {
                return new boolean[] {false, success[1]};
            }
        }

        return new boolean[] {true, true};
    }

    public static boolean accuracyCheck(Move move, Pokemon user, Pokemon target) {
        // movimento em carga ou recarga sempre deve executar
        boolean charging = user.getVolatileStatus(Data.get().getStatusCondition("charging_turn")) != null ||
                           user.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn")) != null;
        boolean recharging = user.getVolatileStatus(Data.get().getStatusCondition("recharging_turn")) != null;
        if (move.hasInherentProperty(InherentProperty.Charges) && !charging ||
            move.hasInherentProperty(InherentProperty.Recharges) && recharging) {
            return true;
        }

        if (move.primaryShouldActivate(MoveEffectActivation.HitGuarantee)) {
            move.activatePrimary(user, target, null, null, 0, null, true, MoveEffectActivation.HitGuarantee);
        }
        if (user.getAbility().shouldActivate(AbilityActivation.HitGuarantee)) {
            user.getAbility().activate(user, target, move, null, null, 0, null, null, 0, true, AbilityActivation.HitGuarantee);
        }
        if (target.getAbility().shouldActivate(AbilityActivation.OpponentHitGuarantee)) {
            target.getAbility().activate(target, user, move, null, null, 0, null, null, 0, true, AbilityActivation.OpponentHitGuarantee);
        }
        for (StatusCondition condition : target.getVolatileStatusList()) {
            if (Arrays.asList(condition.getActivation()).contains(StatusActivation.OpponentHitGuarantee)) {
                condition.activate(target, user, move, null, true, StatusActivation.OpponentHitGuarantee);
            }
        }

        // movimento com CantMiss nunca erra
        if (move.getTemporaryProperties().contains(TemporaryProperty.CantMiss)) {
            return true;
        }

        // semi-invulnerabilidade é ignorada por CantMiss
        StatusCondition charge = target.getVolatileStatus(Data.get().getStatusCondition("semi_invulnerable_charging_turn"));
        if (charge != null) {
            if (Arrays.asList(charge.getActivation()).contains(StatusActivation.Invulnerability) &&
                !((boolean) charge.activate(target, user, move, null, true, StatusActivation.Invulnerability))) {
                return false;
            }
        }

        // IgnoresAccuracy não ignora semi-invulnerabilidade
        if (move.getTemporaryProperties().contains(TemporaryProperty.IgnoresAccuracy)) {
            return true;
        }

        // movimento OHKO tem verificação diferente
        if (move.hasInherentProperty(InherentProperty.OneHitKO)) {
            return (boolean) move.activatePrimary(user, target, null, null, 0, null, true, MoveEffectActivation.OneHitKOAccuracy);
        }


        // verificação normal
        double accuracy = move.getAccuracy();

        for (MoveEffect moveEffect : move.getPrimaryEffect()) {
            if (moveEffect.shouldActivate(MoveEffectActivation.AccuracyCalc)) {
                double newAccuracy = (double) move.activatePrimarySingle(moveEffect, user, target, null, null, 0, null, true, MoveEffectActivation.AccuracyCalc);

                if (newAccuracy != accuracy) {
                    accuracy = newAccuracy;
                    break;
                }
            }
        }

        if (accuracy != -1) {
            if (user.getAbility().shouldActivate(AbilityActivation.AccuracyCalc)) {
                accuracy *= (double) user.getAbility().activate(user, target, move, null, null, 0, null, null, 0, true, AbilityActivation.AccuracyCalc);
            }
            if (accuracy < 0) {
                accuracy = -1;
            }
        }

        if (accuracy != -1) {
            if (target.getAbility().shouldActivate(move, AbilityActivation.OpponentAccuracyCalc)) {
                accuracy *= (double) target.getAbility().activate(target, user, move, null, null, 0, null, null, 0, true, AbilityActivation.OpponentAccuracyCalc);
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

            return Math.random() < accuracy/100.0;
        } else {
            return true;
        }
    }

    public static int pokemonToSwitchIn(int teamNumber, boolean mandatory) {
        int pokemonInTeam = 0;
        List<Pokemon> team = teams.get(teamNumber);
        Pokemon pokemonInBattle = teamNumber == 0 ? yourActivePokemon : opponentActivePokemon;
        int pokemonInBattleIndex = teamNumber == 0 ? yourActivePokemonIndex : opponentActivePokemonIndex;
        int limit = mandatory ? 0 : -1;

        System.out.println("- Choose the next Pokémon " + (mandatory ? "" : "(0 to cancel) ") + "-\n");

        int index;
        for (int i = 1; i <= team.size(); i++) {
            if (team.get(i-1) != null) {
                System.out.println(i + ". " + team.get(i-1).getTrueNameAndForm(false, false) +
                                   (faintCheck(team.get(i-1), null, false) ? " (Fainted)" : "") +
                                   (team.get(i-1) == pokemonInBattle && !faintCheck(team.get(i-1), null, false) ? " (in battle)" : ""));
                pokemonInTeam++;
            }
        }
        do {
            index = App.readOption("\n> ") - 1;

            if (index >= limit && index < pokemonInTeam) {
                if (!(!mandatory && index == -1)) {
                    if (!faintCheck(team.get(index), null, false)) {
                        if (index != pokemonInBattleIndex) {
                            if (teamNumber == 0) {
                                yourActivePokemonIndex = index;
                            } else {
                                opponentActivePokemonIndex = index;
                            }

                            return index;
                        } else {
                            System.out.println("!- " + team.get(index).getTrueName(false, false) + " is already in battle -!");
                            index = -2;
                        }
                    } else {
                        System.out.println("!- " + team.get(index).getTrueName(false, false) + " is unable to battle -!");
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
        List<Action> allActions = new ArrayList<>();

        Pokemon target1;
        if (move1.targetsOpponent()) {
            target1 = pokemon2;
        } else {
            target1 = pokemon1;
        }

        allActions.add(new Action(move1, pokemon1, target1));

        if (Arrays.asList(move1.getPrimaryConditions()).contains(MoveEffectActivation.TurnStart)) {
            Move preMove1 = new Move(move1, pokemon1);
            preMove1.addProperty(TemporaryProperty.Readying);
            allActions.add(new Action(preMove1, pokemon1, target1));
        }

        if (pokemon1.getBattleAction() == 4) {
            allActions.add(new Action(new Move(Data.get().getMove("_mega_evolve_"), pokemon1), pokemon1, target1));
        }

        if (pokemon1.getBattleAction() == 5) {
            allActions.add(new Action(new Move(Data.get().getMove("_ultra_burst_"), pokemon1), pokemon1, target1));
        }

        if (pokemon1.getBattleAction() == 6) {
            allActions.add(new Action(new Move(Data.get().getMove("_terastallize_"), pokemon1), pokemon1, target1));
        }


        Pokemon target2;
        if (move2.targetsOpponent()) {
            target2 = pokemon1;
        } else {
            target2 = pokemon2;
        }

        allActions.add(new Action(move2, pokemon2, target2));

        if (Arrays.asList(move2.getPrimaryConditions()).contains(MoveEffectActivation.TurnStart)) {
            Move preMove2 = new Move(move2, pokemon2);
            preMove2.addProperty(TemporaryProperty.Readying);
            allActions.add(new Action(preMove2, pokemon2, target2));
        }

        if (pokemon2.getBattleAction() == 4) {
            allActions.add(new Action(new Move(Data.get().getMove("_mega_evolve_"), pokemon2), pokemon2, target2));
        }

        if (pokemon2.getBattleAction() == 5) {
            allActions.add(new Action(new Move(Data.get().getMove("_ultra_burst_"), pokemon2), pokemon2, target2));
        }

        if (pokemon2.getBattleAction() == 6) {
            allActions.add(new Action(new Move(Data.get().getMove("_terastallize_"), pokemon2), pokemon2, target2));
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

        List<PriorityBracket> actions = new ArrayList<>();

        for (int i = maxPriority; i >= minPriority; i--) {
            List<Action> bracketActions = new ArrayList<>();
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

        actionOrder = actions;

        for (PriorityBracket priorityBracket : actionOrder) {
            for (int i = 0; i < priorityBracket.actions.size(); i++) {
                int max = i;
                for (int j = i; j < priorityBracket.actions.size(); j++) {
                    Action actionJ = priorityBracket.actions.get(j);
                    Action actionMax = priorityBracket.actions.get(max);

                    int speJ = actionJ.user.getStat(StatName.Spe).getEffectiveValue(actionJ.target, actionJ.move, false, null);
                    int speMax = actionMax.user.getStat(StatName.Spe).getEffectiveValue(actionMax.target, actionMax.move, false, null);

                    for (FieldCondition condition : generalField) {
                        if (condition.compare(Data.get().getFieldCondition("trick_room"))) {
                            speJ = 10000 - speJ;
                            speMax = 10000 - speMax;
                            break;
                        }
                    }

                    priorityBracket.actions.get(j).actionSpeed = speJ;
                    priorityBracket.actions.get(max).actionSpeed = speMax;

                    if (actionJ.user.getAbility().shouldActivate(AbilityActivation.ActionTethers)) {
                        actionJ.user.getAbility().activate(actionJ.user, actionJ.target, actionJ.move, null, null, 0, null, null, 0, true, AbilityActivation.ActionTethers);
                    }
                    if (actionMax.user.getAbility().shouldActivate(AbilityActivation.ActionTethers)) {
                        actionMax.user.getAbility().activate(actionMax.user, actionMax.target, actionMax.move, null, null, 0, null, null, 0, true, AbilityActivation.ActionTethers);
                    }

                    // uma ação fica presa no fim do bracket se o booleano for verdadeiro
                    // e não estiver presa a nenhuma outra ação antes (como por After You)
                    boolean jAtEnd = actionJ.lockedAtEndOfBracket;
                    boolean maxAtEnd = actionMax.lockedAtEndOfBracket;

                    // uma ação fica presa no começo do bracket se o booleano for verdadeiro
                    // e não estiver presa no fim do bracket (como por Quash)
                    boolean jAtStart = actionJ.lockedAtStartOfBracket && !jAtEnd;
                    boolean maxAtStart = actionMax.lockedAtStartOfBracket && !maxAtEnd;

                    // se j estiver preso no começo e max não, j vai antes (e o inverso)
                    // o mesmo se max estiver preso no fim e j não (e o inverso)
                    if (jAtStart && !maxAtStart ||
                             !jAtEnd && maxAtEnd) {
                        max = j;
                    } else if (jAtStart == maxAtStart && jAtEnd == maxAtEnd) {
                        // sem outras considerações de posição fixa, 
                        // a posição é determinada pela velocidade
                        if (speJ > speMax) {
                            max = j;
                        } else if (speJ == speMax &&
                                   Math.random() < 0.5) {
                            max = j;
                        }
                    }
                }

                Action temp = priorityBracket.actions.get(i);
                priorityBracket.actions.set(i, priorityBracket.actions.get(max));
                priorityBracket.actions.set(max, temp);
            }
        }
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

                if (Arrays.asList(action.move.getPrimaryConditions()).contains(MoveEffectActivation.ChangeTarget)) {
                    action.target = (Pokemon) action.move.activatePrimary(action.user, action.target, null, null, 0, null, true, MoveEffectActivation.ChangeTarget);
                }

                if (action.actionTetheredBefore == null) {
                    action.actionSpeed = action.user.getStat(StatName.Spe).getEffectiveValue(action.target, action.move, false, null);

                    for (FieldCondition condition : generalField) {
                        if (condition.compare(Data.get().getFieldCondition("trick_room"))) {
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

                    if (actionK.user.getAbility().shouldActivate(AbilityActivation.ActionTethers)) {
                        actionK.user.getAbility().activate(actionK.user, actionK.target, actionK.move, null, null, 0, null, null, 0, true, AbilityActivation.ActionTethers);
                    }
                    if (actionMax.user.getAbility().shouldActivate(AbilityActivation.ActionTethers)) {
                        actionMax.user.getAbility().activate(actionMax.user, actionMax.target, actionMax.move, null, null, 0, null, null, 0, true, AbilityActivation.ActionTethers);
                    }

                    // uma ação fica presa no fim do bracket se o booleano for verdadeiro
                    // e não estiver presa a nenhuma outra ação antes (como por After You)
                    boolean kAtEnd = actionK.lockedAtEndOfBracket && actionK.actionTetheredBefore == null;
                    boolean maxAtEnd = actionMax.lockedAtEndOfBracket && actionMax.actionTetheredBefore == null;

                    // uma ação fica presa no começo do bracket se o booleano for verdadeiro
                    // e não estiver presa no fim do bracket (como por Quash)
                    boolean kAtStart = actionK.lockedAtStartOfBracket && !kAtEnd;
                    boolean maxAtStart = actionMax.lockedAtStartOfBracket && !maxAtEnd;

                    // k vai antes se estiver preso antes de max
                    if (actionMax.actionTetheredBefore == actionK) {
                        max = k;
                    }

                    // se k estiver preso no começo e max não, k vai antes (e o inverso)
                    // o mesmo se max estiver preso no fim e k não (e o inverso)
                    else if (kAtStart && !maxAtStart ||
                             !kAtEnd && maxAtEnd) {
                        max = k;
                    } else if (kAtStart == maxAtStart && kAtEnd == maxAtEnd) {
                        // sem outras considerações de posição fixa, 
                        // a posição é determinada pela velocidade
                        if (speK > speMax) {
                            max = k;

                        // aleatoriedade não é usada em reordenação
                        } else {
                            max = k;
                        }
                    }

                    // se a velocidade de k for maior, k ainda vai depois se:
                    // - max estiver preso no começo e k não
                    // - k estiver preso no fim e max não

                    if (speK > speMax && !(!kAtStart && maxAtStart) && !(kAtEnd && !maxAtEnd)) {
                        max = k;
                    }

                    // se as velocidades forem iguais, max só vai depois se estiver preso depois k

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

        for (int i = 0; i < actionOrder.size(); i++) {
            PriorityBracket priorityBracket = actionOrder.get(i);
            for (int j = 0; j < priorityBracket.actions.size(); j++) {
                Action action = priorityBracket.actions.get(j);

                if (action.executed) {
                    continue;
                }

                if (action.actionTetheredBefore != null) {
                    int actionIndex = j;
                    int bracketIndex = i;

                    Action previousAction = null;
                    do {
                        if (actionIndex > 0) {
                            actionIndex--;
                        } else if (bracketIndex > 0) {
                            bracketIndex--;
                            actionIndex = actionOrder.get(bracketIndex).actions.size()-1;
                        } else {
                            previousAction = null;
                            break;
                        }

                        previousAction = actionOrder.get(bracketIndex).actions.get(actionIndex);
                    } while (!previousAction.executed &&
                             action.actionTetheredBefore != previousAction);

                    if (previousAction != null) {
                        moveAction(action, previousAction);
                        j--;
                    }
                }
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
            List<Action> actionsInBracket = actionOrder.get(i).actions;
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

    public static List<Pokemon> orderPokemon(List<Pokemon> pokemonList) {
        return orderPokemon(pokemonList.get(0), pokemonList.get(1));
    }

    public static List<Pokemon> orderPokemon(Pokemon pokemon1, Pokemon pokemon2) {
        List<Pokemon> pokemonOrder = new ArrayList<>();
        pokemonOrder.add(pokemon1);
        pokemonOrder.add(pokemon2);

        for (int i = 0; i < pokemonOrder.size(); i++) {
            int max = i;
            for (int j = i; j < pokemonOrder.size(); j++) {
                Pokemon opponentJ = getOpposingPokemon(j);
                Pokemon opponentMax = getOpposingPokemon(max);

                int speJ = pokemonOrder.get(j).getStat(StatName.Spe).getEffectiveValue(opponentJ, null, false, null);
                int speMax = pokemonOrder.get(max).getStat(StatName.Spe).getEffectiveValue(opponentMax, null, false, null);

                for (FieldCondition condition : generalField) {
                    if (condition.compare(Data.get().getFieldCondition("trick_room"))) {
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

            Pokemon temp = pokemonOrder.get(i);
            pokemonOrder.set(i, pokemonOrder.get(max));
            pokemonOrder.set(max, temp);
        }

        return pokemonOrder;
    }

    public static void switchOut(Pokemon switchedPokemon, Pokemon incomingPokemon, Move switchMove) {
        int player = switchedPokemon.getTeam() + 1;
        Pokemon opponent = getOpposingPokemon(switchedPokemon.getTeam());

        if (switchedPokemon.getVolatileStatus(Data.get().getStatusCondition("readying_switch")) == null) {
            boolean teamFainted = true;
            for (Pokemon pokemon : teams.get(switchedPokemon.getTeam())) {
                if (pokemon != null &&
                    pokemon != switchedPokemon &&
                    !faintCheck(pokemon, null, false)) {
                    teamFainted = false;
                }
            }

            if (teamFainted) {
                return;
            }

            String key = "switch out";

            if (switchMove.getTemporaryProperties().contains(TemporaryProperty._Pivot_)) {
                key += " pivot";
            } else if (switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                key += " forced";
            }

            MessageHandler.add("switch", key, Map.of(
                "Pokemon", switchedPokemon.getName(
                    switchMove.getTemporaryProperties().contains(TemporaryProperty._Pivot_) ||
                    switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_),
                    false
                ),
                "Player", "Player " + player
            ));

            switchedPokemon.addVolatileStatus(new StatusCondition(
                Data.get().getStatusCondition("readying_switch"), null, 0, null, null
            ));

            addAction(new Action(switchMove, switchedPokemon, switchedPokemon), switchedPokemon.getCurrentAction());

            if (!switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                for (int i = actionOrder.indexOf(currentPriorityBracket); i < actionOrder.size(); i++) {
                    PriorityBracket priorityBracket = actionOrder.get(i);
                    int currentJ = priorityBracket == currentPriorityBracket ? priorityBracket.actions.indexOf(currentAction) : 0;
                    for (int j = currentJ; j < priorityBracket.actions.size(); j++) {
                        Action action = priorityBracket.actions.get(j);

                        if (action.move != null &&
                            Arrays.asList(action.move.getPrimaryConditions()).contains(MoveEffectActivation.OpponentSwitch) &&
                            action.target == switchedPokemon) {
                            action.move.activatePrimary(action.user, switchedPokemon, null, null, 0, null, true, MoveEffectActivation.OpponentSwitch);
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
                    condition.activate(opponent, switchedPokemon, null, null, false, StatusActivation.CauserLeaveField);
                }
            }

            changeActivePokemon(player-1, incomingPokemon);

            if (switchedPokemon.getAbility().shouldActivate(AbilityActivation.SwitchOut)) {
                switchedPokemon.getAbility().activate(switchedPokemon, opponent, null, null, null, 0, null, null, 0, true, AbilityActivation.SwitchOut);
            }

            List<StatusCondition> conditionsToActivate = new ArrayList<>();
            if (switchMove.getTemporaryProperties().contains(TemporaryProperty._TransferValues_)) {
                switchedPokemon.transferValues(incomingPokemon);

                for (StatusCondition condition : incomingPokemon.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.Start)) {
                        conditionsToActivate.add(condition);
                    }
                }
            }
            switchedPokemon.restoreDefaultValues(false);

            String key = "switch in";

            if (switchMove.getTemporaryProperties().contains(TemporaryProperty._Forced_)) {
                key += " forced";
            }

            MessageHandler.add("switch", key, Map.of(
                "Pokemon", incomingPokemon.getName(false, false),
                "Player", "Player " + player
            ));

            incomingPokemon.setJustSwitchedIn(true);

            for (StatusCondition condition : conditionsToActivate) {
                condition.activate(incomingPokemon, opponent, null, null, true, StatusActivation.Start);
            }

            entryEffects(incomingPokemon, opponent);
        }
    }

    public static void faintReplacement() {
        List<Pokemon> replacingPokemon = new ArrayList<>();

        if (faintCheck(yourActivePokemon, null, false)) {
            switchInFaint(0);
            replacingPokemon.add(yourActivePokemon);
        }
        if (faintCheck(opponentActivePokemon, null, false)) {
            switchInFaint(1);
            replacingPokemon.add(opponentActivePokemon);
        }

        if (replacingPokemon.size() > 1) {
            for (Pokemon incomingPokemon : orderPokemon(replacingPokemon)) {
                Pokemon opposingPokemon = incomingPokemon.getTeam() == 0 ? opponentActivePokemon : yourActivePokemon;
                entryEffects(incomingPokemon, opposingPokemon);
            }
        } else if (replacingPokemon.size() == 1) {
            Pokemon opposingPokemon = replacingPokemon.get(0).getTeam() == 0 ? opponentActivePokemon : yourActivePokemon;
            entryEffects(replacingPokemon.get(0), opposingPokemon);
        }

        if (!battleOver &&
            (faintCheck(yourActivePokemon, null, false) || faintCheck(opponentActivePokemon, null, false))) {
            faintReplacement();
        }
    }

    public static void switchInFaint(int team) {
        System.out.println("\n------------------------------------------\n");

        Pokemon incomingPokemon = teams.get(team).get(pokemonToSwitchIn(team, true));
        changeActivePokemon(team, incomingPokemon);

        System.out.println();
        Data.get().getMessage("switch").getMessage("switch in", Map.of(
            "Pokemon", incomingPokemon.getName(false, false),
            "Player", "Player " + (team + 1)
        ));

        System.out.println("\n------------------------------------------\n");
    }

    public static void entryEffects(Pokemon incomingPokemon, Pokemon opponentPokemon) {
        MessageHandler.currentType = MessageType.ENTRY_EFFECT;

        for (Move move : delayedMoves.get(incomingPokemon.getTeam())) {
            if (move.primaryShouldActivate(MoveEffectActivation.DelayedSwitch)) {
                move.activatePrimary(move.getUser(), incomingPokemon, null, null, 0, null, true, MoveEffectActivation.DelayedSwitch);
            }
            if (move.zShouldActivate(MoveEffectActivation.ZDelayedSwitch)) {
                move.activateZ(move.getUser(), incomingPokemon, null, null, 0, null, true, MoveEffectActivation.ZDelayedSwitch);
            }
        }
        for (int i = 0; i < delayedMoves.get(incomingPokemon.getTeam()).size(); i++) {
            Move move = delayedMoves.get(incomingPokemon.getTeam()).get(i);
            if (move.compare(Data.get().getMove("_placeholder_"))) {
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
            incomingPokemon.getAbility().activate(incomingPokemon, opponentPokemon, null, null, null, 0, null, null, 0, true, AbilityActivation.Entry);
        }

        if (incomingPokemon.getItem().shouldActivate(ItemActivation.Entry)) {
            incomingPokemon.getItem().activate(incomingPokemon, incomingPokemon, opponentPokemon, null, null, true, ItemActivation.Entry);
        }
    }

    public static Action findAction(Action action) {
        if (action == null) {
            return null;
        }

        for (PriorityBracket priorityBracket : actionOrder) {
            List<Action> actionsInBracket = priorityBracket.actions;
            for (Action actionInBracket : actionsInBracket) {
                if (actionInBracket == action) {
                    return actionInBracket;
                }
            }
        }
        return null;
    }

    public static Action findAction(Move move) {
        if (move == null) {
            return null;
        }

        for (PriorityBracket priorityBracket : actionOrder) {
            List<Action> actionsInBracket = priorityBracket.actions;
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
            List<Action> actionsInBracket = priorityBracket.actions;
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

    public static Action findAction(MoveTemplate move) {
        if (move == null) {
            return null;
        }

        for (PriorityBracket priorityBracket : actionOrder) {
            List<Action> actionsInBracket = priorityBracket.actions;
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

    public static Action findAction(MoveTemplate move, Pokemon user) {
        if (move == null) {
            return null;
        }

        for (PriorityBracket priorityBracket : actionOrder) {
            List<Action> actionsInBracket = priorityBracket.actions;
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
            List<Action> actionsInBracket = priorityBracket.actions;
            for (Action action : actionsInBracket) {
                if (action.user == user) {
                    if (action.move.getCategory() == null &&
                        (!action.move.compare(Data.get().getMove("_switch_")) || notSwitch)) {
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

    public static boolean faintCheck(Pokemon pokemon, Move move, boolean sayMessage) {
        if (pokemon.getCurrentHP() == 0) {
            if (sayMessage) {
                int team = pokemon.getTeam();

                MessageHandler.add("modify_health", "faint", Map.of(
                    "Pokemon", pokemon.getName(true, false)
                ));

                int remaining = 0;
                for (Pokemon teamPokemon : teams.get(team)) {
                    if (!faintCheck(teamPokemon, null, false)) {
                        remaining++;
                    }
                }
                remainingPokemon.set(team, remaining);

                pokemon.endNonVolatileStatus(false);

                Pokemon opponent = getOpposingPokemon(team);

                for (StatusCondition condition : opponent.getVolatileStatusList()) {
                    if (condition.getCauser() == pokemon &&
                        Arrays.asList(condition.getActivation()).contains(StatusActivation.CauserLeaveField)) {
                        condition.activate(opponent, pokemon, null, null, false, StatusActivation.CauserLeaveField);
                    }
                }

                List<StatusCondition> conditionsToActivate = new ArrayList<>();

                for (StatusCondition condition : pokemon.getVolatileStatusList()) {
                    if (Arrays.asList(condition.getActivation()).contains(StatusActivation.Faint)) {
                        conditionsToActivate.add(condition);
                    }
                }

                pokemon.restoreDefaultValues(true);
                pokemonFaintedLastTurn[team] = 2;

                if (!battleOver) {
                    battleOverCheck();
                }

                if (!battleOver) {
                    if (pokemon.getAbility().shouldActivate(AbilityActivation.FaintUser)) {
                        pokemon.getAbility().activate(pokemon, opponent, move, null, null, 0, null, null, 0, true, AbilityActivation.FaintUser);
                    }

                    for (StatusCondition condition : conditionsToActivate) {
                        condition.activate(pokemon, opponent, move, null, true, StatusActivation.Faint);
                    }

                    // TODO ajustar pra doubles
                    if (!Battle.faintCheck(opponent, null, false) &&
                        opponent.getAbility().shouldActivate(AbilityActivation.AnyFaint)) {
                        opponent.getAbility().activate(opponent, pokemon, null, null, null, 0, null, null, 0, true, AbilityActivation.AnyFaint);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static void battleOverCheck() {
        for (Pokemon activePokemon : orderActivePokemonList()) {
            if (remainingPokemon.get(activePokemon.getTeam()) == 0) {
                losingTeam = activePokemon.getTeam();
                battleOver = true;
                return;
            }
        }
    }

    public static boolean battleIsOver() {
        return battleOver;
    }
}