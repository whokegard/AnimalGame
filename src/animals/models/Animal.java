package animals.models;

import food.models.Food;

import java.io.Serializable;

/**
 * This is our Animal class where we have information about the animals.
 *
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public abstract class Animal implements Serializable {

    protected String name;
    protected String animalBreed;
    protected MaleFemale gender;
    protected int startPrice;
    protected int health = 100;
    protected int age = 0;
    protected int maxAge;
    protected int healthDifference;


    public Animal() {
        super();
    }

    public enum MaleFemale {
        FEMALE, MALE;

        public static MaleFemale getRandomSelectGender() {  //Random select for male or female
            return values()[(int) (Math.random() * values().length)];
        }
    }


    public boolean eatTrue(Food food) {
        return true;
    }

    /**
     * Method eatFood will check with the animals if they can eat the food we are giving to it.
     * If the animal have 100% health then it won't be able to eat anything.
     *
     * @param food lets us get information from the food class
     */
    public void eatFood(Food food) {
        if (eatTrue(food)) {
            if (this.health >= 100) {
                this.health = 100;
                System.out.println("Animal is at full health"); // First if statement is if the animal has 100 health at the start,
                // and the second if statement is if the animal is being fed to 100%
            }
            if (this.health < 100) {
                this.health = this.getHealth() + 10;

                if (this.health > 100) {
                    this.health = 100;
                    System.out.println(getName() + "animal is at full health " + getHealth() + "%");
                }
            }
        }
    }

    /**
     * This method declares the current price of the animal
     * based on the age.
     *
     * @return the current price of the animal
     */
    public int currentPriceAnimal() {
        double currentPrice = ((this.getHealth() / 100.0) * this.startPrice);
        currentPrice = currentPrice - (this.age * 2);
        return (int) currentPrice;
    }

    /**
     * The healthDifference is the method that shows how much health the animal has lost,
     * this will show each rounds if the animal is alive.
     *
     * @param healthDifference will show the amount health that the animal has lost
     */
    public void setHealthDifference(int healthDifference) {
        this.healthDifference = healthDifference;
    }

    public int getHealthDifference() {
        return healthDifference;
    }

    public boolean canEat(Food food) {
        return true;
    }

    public MaleFemale getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = MaleFemale.valueOf(gender.toUpperCase());
    }

    public String getAnimalBreed() {
        return animalBreed;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = this.age + age;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
