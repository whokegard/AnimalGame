package animals;

import animals.models.Animal;
import food.DryFoodCat;
import food.models.Food;


/**
 * This is our Cat class were we give our
 * cat a name, age limit and start price.
 *
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public class Cat extends Animal {

    /**
     * Our constructor that contains maxAge, animalBreed and startPrice of the animal
     */
    public Cat() {
        maxAge = 9;
        animalBreed = "Cat";
        startPrice = 600;
    }

    /**
     * This overrides the canEat method in the Animal class.
     *
     * @param food lets us get information from the Food class
     * @return the instance of dryfood
     */
    @Override
    public boolean canEat(Food food) {
        return food instanceof DryFoodCat;
    }

    /**
     * The eatFood method will add health to the animal.
     * If the animal has 100% health, it would not be able to eat.
     *
     * @param food lets us get information from the Food class
     */
    @Override
    public void eatFood(Food food) {
        if (canEat(food)) {
            if (this.health >= 100) {
                this.health = 100;
                System.out.println("This animal has full health");
            }
            if (this.health < 100) {
                if (this.health >= 50)
                    this.health = this.getHealth() + 10;
                if (this.health < 50)
                    this.health = this.getHealth() + 10;
                if (this.health > 100) {
                    this.health = 100;
                    System.out.println(getName() + " is at full health: " + getHealth());
                }
            }
        }
    }
}
