package gameSave;

import animalgame.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * In this class we handle all the saving and loading for the Animal game.
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public class SaveGame implements Serializable {
    Game game;

    /**
     * We select the current game when we start the game.
     * @param game the current game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Here we name our game that we want to save
     * using the SaveGameName method.
     * @return the name of the save that the player chose
     */
    public String saveGameName() {
        Game.newScreen();
        String answer = Dialog.dialogString("Name your game save: ");
        Game.newScreen();
        return answer;

    }

    /**
     * This method checks if there's an existing game save with the chosen name from saveGameName method.
     * If there is an existing game save with an already chosen name, the player will get the option to either
     * overwrite a save or enter a new name.
     * @return the input from the user
     */
    public int inputSaveGame() {
        int inputAnswer = Dialog.dialog("1. Overwrite existing file 2. Enter a new name", 1, 2);
        return inputAnswer;
    }

    /**
     * This method shows a list of saved games. The player can either load a save, or go back to the
     * previous menu.
     * @return the input from the user
     */
    public int inputLoadGame() {
        int inputLoad = Dialog.dialogWithoutMax("|0| - back");
        return inputLoad;
    }

    /**
     * This method will create a new directory (SavedGames) where we store all our saved games.
     * If the user doesn't have a SavedGames directory, the method will create one. If the user saves the game
     * the method will call on the serialize method
     * in the Serializer class to serialize the program.
     * @param game the instance created at the start of the game
     */
    public void saveGame(Game game) {

        File gameFile = new File("SavedGames/");
        boolean running = true;
        while (running) {
            String savedGameFileName = saveGameName() + ".ser";

            if (!Files.exists(Paths.get("SavedGames/" + savedGameFileName))) {
                if (!gameFile.exists()) {
                    gameFile.mkdir(); // checks if it can create a directory / package where the pathname is specified ("SavedGames/")
                }
                Serializer.serialize("SavedGames/" + savedGameFileName, game);
                running = false;
            } else {
                System.out.println("Filename already exists.");
                int choiceSave = inputSaveGame();
                if (choiceSave == 1) { // Try catch metod
                    Serializer.serialize("SavedGames/" + savedGameFileName, game);
                    running = false;
                }
            }
        }
    }

    /**
     * Loads a saved game if a game save exists in the SavedGames directory.
     * If a save exists, the method will call on the deserialize method in the Serializer class.
     * If no saved game exists in SavedGames, the user will get a message that no saves exist.
     */
    public void loadGame() {
        File[] savedGames;
        File f = new File("SavedGames/");
        FilenameFilter filter = (dir, name) -> name.endsWith(".ser");

        savedGames = f.listFiles(filter);

        if (savedGames == null) {
            System.out.println("You have no saved games");
        } else {
            System.out.println("Choose a game to load!\n");
            int counter = 1;
            for (File file : savedGames) {
                System.out.println("|" + counter + "| - " + file.getName());
                counter++;
            }
            int choice = inputLoadGame();
            if (choice == 0) {
                return;
            }
            String gameFile = savedGames[choice - 1].toString();
            try {
                this.game = (Game) Serializer.deserialize(gameFile);
                game.gameMenu();
            } catch (Exception error) {
                System.out.println(error);
            }
        }
    }
}