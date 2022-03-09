package animalgame;

import gameSave.SaveGame;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is our Game class where we have information about the game.
 *
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public class Game implements Serializable {

    /**
     * The field variables keep track of the game settings chosen by the player, and provides
     * information about the current game to the player.
     */
    private int amountOfTurns;
    private int currentTurn;
    private Player currentPlayer;
    private int playerIndex;
    private int numberOfPlayers = 0;
    private boolean exit = false;
    SaveGame saveGame = new SaveGame();

    private final GameLogic logic = new GameLogic();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Player> loss = new ArrayList<>();

    /**
     * The constructor is providing a way for the user to navigate through the menus.
     *
     */
    public Game() {
        saveGame.setGame(this);

        boolean start = true;
        while (start) {

            players.clear();
            System.out.println("""
                    
                    
                    ╦ ╦┌─┐┬  ┌─┐┌─┐┌┬┐┌─┐                         \s
                    ║║║├┤ │  │  │ ││││├┤                          \s
                    ╚╩╝└─┘┴─┘└─┘└─┘┴ ┴└─┘                         \s
                    ┌┬┐┌─┐  ┌─┐┬ ┬┬─┐  ┌─┐┌┐┌┬┌┬┐┌─┐┬  ┌─┐┌─┐┌┬┐┌─┐
                     │ │ │  │ ││ │├┬┘  ├─┤│││││││├─┤│  │ ┬├─┤│││├┤\s
                     ┴ └─┘  └─┘└─┘┴└─  ┴ ┴┘└┘┴┴ ┴┴ ┴┴─┘└─┘┴ ┴┴ ┴└─┘
                     Creators: Lukas L, Isabella S, Benjamin E, Carl M""");
            System.out.println("─────────────────────");

            int first = Dialog.dialog(
                    "[1] Start game" +
                            "\n[2] Game Rules" +
                            "\n[3] Load Game" +
                            "\n[4] Exit Game" +
                            "\n─────────────────────",1, 4);
            switch (first) {
                case 1 -> startMenu();
                case 2 -> gameRules();
                case 3 -> saveGame.loadGame();
                case 4 -> {
                    System.out.println("Game is ending...");
                    System.exit(1);
                }
            }
        }
    }

    /**
     * Method for storing the amount of rounds and players.
     * The method also uses the Dialog class for the scanners with a minimum and a maximum value for the amount
     * of rounds and players.
     */
    public void startMenu() {
        newScreen();

        currentTurn = 1;
        exit = false;

        amountOfTurns = Dialog.dialog("How many rounds do you wanna play 5-30 rounds", 5, 30);
        System.out.println("─────────────────────");
        numberOfPlayers = Dialog.dialog("How many players do you want, between 2-4", 2, 4);
        System.out.println("─────────────────────");


        playerName();
        //information();
        gameMenu();
    }

    /**
     * In gameBrain we give access to the Store-class and Breeding-class.
     * We use a while-loop that runs with the game.
     * The switch-case receives the user input and lets the player choose an option.
     */
    public void gameBrain() {
        Store store = new Store();
        Breeding breeding = new Breeding();
        boolean game = true;
        Game.newScreen();
        information();

        while (game) {
            int pick = 0;
            while (pick < 1 || pick > 7) {
                System.out.println("─────────────────────");
                System.out.println("It is round " + currentTurn + " and it is: " + currentPlayer.getName() + "'s" + " turn.");
                currentPlayer.playerInv();
                pick = menuStore();
            }
            switch (pick) {
                case 1 -> store.buyMenu(currentPlayer);
                case 2 -> breeding.animalBreed(currentPlayer);
                case 3 -> currentPlayer.animalFeeding(currentPlayer);
                case 4 -> game = false;
                case 5 -> gameRules();
                case 6 -> saveGame.saveGame(this);
                case 7 -> {
                    game = false;
                    exit = true;
                    Game.newScreen();
                }
            }
        }
    }


    /**
     * Menu of options for the current player
     * @return returns the chosen menu option
     */
    public int menuStore() {
        int input = Dialog.dialog(
                "─────────────────────" +
                        "\n[1] Store" +
                        "\n[2] Breed" +
                        "\n[3] Feed Animal" +
                        "\n[4] End Turn" +
                        "\n[5] Game Rules" +
                        "\n[6] Save game" +
                        "\n[7] Exit game" +
                        "\n─────────────────────", 1, 7);
        return input;
    }

    /**
     * Allows the users to name their players.
     */
    public void playerName() {
        newScreen();
        System.out.println("You picked " + numberOfPlayers + " amount of players");
        for (int i = 1; i < numberOfPlayers + 1; i++) {
            System.out.println("─────────────────────");
            System.out.println("Player " + i + " pick your name: ");
            String name = Dialog.playerName();
            players.add(new Player(name));
        }
        newScreen();
    }

    /**
     * The method checks if the amount of turns has been exceeded,
     * if the amount of turns has been exceeded the game will pick a winner.
     *
     * This method will also check the current age of the animals, and if there are dead animals.
     * End round will check if any player has lost the game, the health of the animals,
     * and any dead animals.
     */
    public void gameMenu() {
        for (int pick = currentTurn; pick < amountOfTurns + 1; pick++) {
            for (Player player : players) {
                logic.checkDeadAnimals(player);
            }
            for (int pick1 = playerIndex; pick1 < players.size(); pick1++) {
                currentPlayer = players.get(pick1);
                currentPlayer.trueStatistics();




                logic.startRound(currentPlayer);
                gameBrain();
                logic.endRound(currentPlayer, this);
                if (exit)
                    break;
                playerIndex++;
            }
            if (exit)
                break;

            playerIndex = 0;
            currentTurn++;
            newScreen();
        }
        if (loss.size() >= 1){
            playerIndex -= 1;
        }
        logic.winnerPick(this);
    }

    /**
     * Reads the user input and navigates the user to read the Game summary, detailed description
     * or to return to the previous menu.
     */
    public void gameRules() {
        newScreen();
        int input = Dialog.dialog(
                "─────────────────────" +
                        "\n[1] Game Summary" +
                        "\n[2] Detailed description" +
                        "\n[3] Return to the previous menu" +
                        "\n─────────────────────", 1, 3);

        switch (input) {
            case 1 -> gameSummary();
            case 2 -> detailedGameInfo();
        }
    }

    /**
     * A brief summary on how the game works
     */
    public void gameSummary() {
        newScreen();
        System.out.println("""
                Summary: 
                We use coins to purchase animals, animal food, and we gain coins when we sell animals.
                Animals can also breed, and require you to feed them!
                Different animals have different values, depending on health and age. 
                After the selected amount of turns exceeds, 
                the game automatically sells every players animals and calculates the value. 
                The player with the most coins wins!""");
    }

    /**
     * A more detailed version of what the store provides
     */
    public void detailedStoreDescription() {
        System.out.println("""
                When you first start the game, you get to choose how many rounds you want to play (from 5 to 30).
                Afterwards, you get to choose how many participants that will play (from 2 to 4).
                The first turn allows the player to choose between different options:
                [Store] - Where you buy animals or food. Here you can also sell animals.
                [Breed] - A feature you unlock after buying two of the same animals with opposite genders.
                [Feed Animal] - Since the animals have health points, it's necessary to feed the animals, otherwise they die and you lose them.
                [End your turn] - Simply ends the current players turn, and puts the next player up.
                [Game Info] - Prints out the current game info, such as: Current round, current turn and current balance.
                [Save Game] - Allows the participants to save the current game for later progression.
                [Exit] - Exits the game.""");
    }

    /**
     * A switch case for an information-menu.
     * This menu allows the user to read more about how the game works.
     */
    public void detailedGameInfo() {
        newScreen();
        int input = Dialog.dialog(
                """
                        ─────────────────────
                        How does it work?
                        ─────────────────────
                        [1] Breeding
                        [2] Coins
                        [3] Food
                        [4] After a round ends
                        [5] Store
                        [6] Draw
                        [7] Return to the previous menu
                        ─────────────────────""", 1,7);
        switch (input) {
            case 1 -> detailedInfoBreeding();
            case 2 -> detailedInfoCoins();
            case 3 -> detailedInfoFood();
            case 4 -> detailedRoundInfo();
            case 5 -> detailedStoreDescription();
            case 6 -> detailedInfoDraw();
            case 7 -> gameRules();
        }
    }

    /**
     * In-depth on how Breeding works
     */
    public void detailedInfoBreeding() {
        System.out.println("""
                [Breeding] - After you've purchased two of the same animal with opposite gender, you unlock the feature to breed them.
                Breed has a 50% success rate and the amount of babies depends on the chosen animal.
                Different animals can breed different amounts of babies.
                You can't own two animal with the same name!
                        
                [Bird] max babies: 5
                [Cat] max babies: 12
                [Dog] max babies: 12
                [Goldfish] max babies: 10
                [Hamster] max babies: 3
                                        
                After a successful breeding, a random generated number of offsprings dependant on the chosen animal is generated, and
                has the age: 0, and health: 100.""");
    }

    /**
     * In-depth on how coins work
     */
    public void detailedInfoCoins() {
        System.out.println("""
                Every player starts with the same amount of coins: 4000.\s 
                You use coins to purchase animals and animal food. You gain coins by selling animals.
                At the end of the game, the player with the most amount of coins wins.""");
    }

    /**
     * In-depth on how food works
     */
    public void detailedInfoFood() {
        System.out.println("""
                [Food] Food is purchased from the store with coins and is bought in kilos. The kind of food your animal eats
                differs with the animal. Each animal can consume 1-3 different kind of food.
                Every kilo increases the health of the animal by 10%.
                You can't sell food back to the store nor to other players!""");
    }

    /**
     * In-depth on how a potential draw would occur
     */
    public void detailedInfoDraw(){
        System.out.println("""
                [Draw] If a potential draw would occur, the order in which you
                registered your playernames determines the winner.""");
    }

    /**
     * In-depth on how each turn impacts the game
     */
    public void detailedRoundInfo() {
        System.out.println("""
                After chosen amount of players has made their turn, a new round begins. With a new round comes
                a loss of health on ALL animals that you own. Health loss is randomly
                generated from 10% to 30%.
                A new turn allows each player to make a new action, such as: Buy, sell breed.""");
    }

    /**
     * Prints out the current game information
     */
    public void information() {
        System.out.println("Current game info: ");
        System.out.println("Amount of rounds : " + amountOfTurns);
        int amountOfPlayer = 1;
        for (Player player : players) {
            System.out.println("Player " + amountOfPlayer + ": " + player.getName());
            amountOfPlayer++;
        }
    }

    /**
     * Prints 50 lines to make it easier for the user to read the console
     */
    public static void newScreen() { //Static so we can reach outside Game class
        System.out.println("\n".repeat(50));
    }

}