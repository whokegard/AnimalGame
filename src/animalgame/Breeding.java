package animalgame;

import animals.*;
import animals.models.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is our Breeding class, where the logic of the breeding brain is.
 *
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public class Breeding implements Serializable {
    private boolean nameAvailable = true;
    public String animalOffspringBreed;

    public Breeding() {
    }


    /**
     * This method will attempt to breed the animals selected by the player. The method also compares the players selected
     * animal and the remaining available animals to see if the animals are compatible to breed. If the chosen animals are
     * compatible to breed, the program will randomise a value from 0-100, and >50 will successfully breed the animal and
     * have the player name the new animal.
     *
     * @param player the instance of the current player
     */
    public void animalBreed(Player player) {
        Game.newScreen();
        Random random = new Random();
        int animal1 = 0;
        int animal2 = 0;
        int chanceOfBreed = random.nextInt(101);

        ArrayList<Animal> tempList = new ArrayList<>();
        if (player.checkWithPlayer(player.ableToBreed)) {
            if (player.animals.size() <= 1) {
                System.out.println(TEXT_RED+"You have 1 or less animal, not enough to breed. "+TEXT_RESET);
                return;
            } else {
                while (animal1 < 1 || animal1 > player.animals.size()) {
                    Game.newScreen();
                    player.getPlayerAnimal();
                    animal1 = firstAnimal();
                }
                if (animalsThatCanBreed(player, player.animals.get(animal1 - 1))) {
                    for (Animal animal : player.animals) {
                        if (!(checkAnimalsLeftForBreeding(player.animals.get(animal1 - 1), animal))) {
                            tempList.add(animal);
                        }
                    }
                } else {
                    System.out.println(TEXT_RED+"There is no suitable animal to breed for " +TEXT_RESET + player.animals.get(animal1 - 1).getName() + "!");
                    System.out.println("Press "+ WHITE_BOLD + "ENTER" +TEXT_RESET+" to continue. ");
                    Dialog.enterButton();
                    Game.newScreen();
                    return;
                }
                while (animal2 < 1 || animal2 > tempList.size()) {
                    System.out.println("You can pair " + player.animals.get(animal1 - 1).getName() + " with: ");
                    int count = 1;
                    for (Animal animal : tempList) {
                        System.out.println("[" + count + "] " + animal.getName() + " ─ " + animal.getAnimalBreed() + " | " + animal.getGender()
                                + " | " + animal.getHealth() + "% health left.");
                        animalOffspringBreed = animal.getAnimalBreed();
                        count++;
                    }
                    System.out.println("Choose your second animal to breed. \nEnter a number: ");
                    try {
                        animal2 = Dialog.intReturn();
                    } catch (Exception e) {
                        System.out.println("You must enter a number for an animal.");
                    }
                }
            }
            if (checkForBreed(player.animals.get(animal1 - 1), tempList.get(animal2 - 1))) {
                if (chanceOfBreed > 50) {
                    int counter;

                    if (player.animals.get(animal1 - 1).getAnimalBreed().equals("Bird")) {
                        counter = animalBirth(5);
                        for (int i = 0; i < counter; i++) newAnimal(player, new Bird());
                    }
                    if (player.animals.get(animal1 - 1).getAnimalBreed().equals("Cat")) {
                        counter = animalBirth(12);
                        for (int i = 0; i < counter; i++) newAnimal(player, new Cat());
                    }
                    if (player.animals.get(animal1 - 1).getAnimalBreed().equals("Dog")) {
                        counter = animalBirth(12);
                        for (int i = 0; i < counter; i++) newAnimal(player, new Dog());
                    }
                    if (player.animals.get(animal1 - 1).getAnimalBreed().equals("Goldfish")) {
                        counter = animalBirth(20);
                        for (int i = 0; i < counter; i++) newAnimal(player, new Goldfish());
                    }
                    if (player.animals.get(animal1 - 1).getAnimalBreed().equals("Hamster")) {
                        counter = animalBirth(3);
                        for (int i = 0; i < counter; i++) newAnimal(player, new Hamster());
                    }
                } else {
                    Game.newScreen();
                    System.out.println(TEXT_RED+"Breeding failed!"+TEXT_RESET);
                    System.out.println("Press "+ WHITE_BOLD + "ENTER" +TEXT_RESET+" to continue. ");
                    Dialog.enterButton();
                    Game.newScreen();
                    player.falseStatistics();
                }
            } else {
                System.out.println("The animals you choose are unable to breed with each other. Please pick a compatible pair");
                System.out.println("Press "+ WHITE_BOLD + "ENTER" +TEXT_RESET+" to continue. ");
                Dialog.enterButton();
                Game.newScreen();
            }
        }
        Game.newScreen();
    }


    /**
     * This method allows the player to name their new animal once the parents have mated.
     * The method also prevents the user from naming the offspring to a name that is already taken.
     *
     * @param player assigns the new name of the newborn animal to the current player
     * @param animal generates a gender and what animal type the newborn animal is
     */
    public void newAnimal(Player player, Animal animal) {

        String gender = Animal.MaleFemale.getRandomSelectGender().toString();
        System.out.println("You've got a " + animal.getAnimalBreed() + " that is " + gender + "!");
        System.out.println("Enter a name: ");

        while (nameAvailable) {
            String animalName = Dialog.stringReturn();

            for (int i = 0; i < player.animals.size(); i++) {
                if (!player.animals.get(i).getName().equalsIgnoreCase(animalName)) {
                    animal.setName(animalName);
                    nameAvailable = false;

                } else {
                    System.out.println(TEXT_RED+"Name is already taken! Please try again:"+TEXT_RESET);
                    nameAvailable = true;
                    break;
                }
                if (animalName == null) {
                    nameAvailable = true;
                }
            }
        }
        nameAvailable = true;



        animal.setGender(gender);
        player.animals.add(animal);
        player.falseStatistics();

    }


    /**
     * A method to check if two animals can breed.
     *
     * @param animal1 gets the information about animal breed and gender for animal1
     * @param animal2 gets the information about animal breed and gender for animal2
     * @return true if animal breed is the same and gender is the opposite. Otherwise, returns false
     */
    public boolean checkForBreed(Animal animal1, Animal animal2) {
        return animal1.getAnimalBreed().equals(animal2.getAnimalBreed()) && animal1.getGender() != animal2.getGender();
    }

    /**
     * Checks which animals that are compatible for breeding.
     *
     * @param animal1 grabs information such as: gender, name and breed about animal1
     * @param animal2 compares the current animal2 with animal1
     * @return returns the value of each variable for animal1 and animal2
     */
    public boolean checkAnimalsLeftForBreeding(Animal animal1, Animal animal2) {
        return animal1.getGender().equals(animal2.getGender()) || animal1.getName().equals(animal2.getName()) ||
                !animal1.getAnimalBreed().equals(animal2.getAnimalBreed());
    }


    /**
     * Checks which animals the player owns, and also if the animals are compatible to breed.
     *
     * @param player grabs information such as: Animals
     * @param animal1 grabs information such as: Gender
     * @return true or False value
     */
    public boolean animalsThatCanBreed(Player player, Animal animal1) {
        for (Animal animal : player.animals) {
            if (animal.getAnimalBreed().equals(animal1.getAnimalBreed()) && !animal.getGender().equals(animal1.getGender())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Randomizes an amount of babies to an animal once they've given birth with a max amount of babies an animal can have.
     * @param maxAmountOfOffsprings the max amount of babies an animal can breed at once
     * @return the amount of babies of a certain animal to add to the current player
     */
    public int animalBirth(int maxAmountOfOffsprings) {
        Random random = new Random();
        int counter = 1;
        Game.newScreen();
        System.out.println(TEXT_GREEN+"Breeding successful!"+TEXT_RESET);
        for (int i = 0; i < maxAmountOfOffsprings; i++) {
            int numberOfOffsprings = random.nextInt(101);
            if (numberOfOffsprings < 20) {
                counter++;
            }
        }
        System.out.println("You've got a total of " + counter + " " + animalOffspringBreed + " offsprings!");
        return counter;
    }

    /**
     * Allows the user to choose an animal for breeding.
     * @return returns the chosen animal
     */
    public int firstAnimal() {
        int first = Dialog.dialogWithoutMax("Choose your first animal to breed! Enter a number: ");
        return first;
    }
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String TEXT_RESET = "\u001B[0m";

}