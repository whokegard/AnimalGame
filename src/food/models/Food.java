package food.models;

import java.io.Serializable;

/**
 * This is our abstract Food class where we create a blueprint for the subclasses
 *
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public abstract class Food implements Serializable {

    protected String name;
    protected int price;
    private int kg = 1;

    public Food() {
        super();
    }

    public String getName() {
        return name;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = this.kg + kg;
    }

    public int getPrice() {
        return price;
    }
}
