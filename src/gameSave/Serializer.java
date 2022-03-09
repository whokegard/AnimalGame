package gameSave;

import java.io.*;

/**
 * This class is responsible for serializing and deserializing a state in the program
 * by converting the current state into a byte stream.
 * @author Lukas L, Isabella S, Benjamin E, Carl M
 */
public class Serializer implements Serializable {

    /**
     * This method will write the current state of an object into a byte stream in order to save the game.
     * @param filePath the save game directory
     * @param data the game object
     */
    static public void serialize(String filePath, Object data) {
        try {
            var file = new FileOutputStream(filePath);
            var out = new ObjectOutputStream(file);
            out.writeObject(data);
            out.close();
            file.close();
            System.out.println("Game is saved!");
        } catch (Exception error) {
            System.out.println("Game was not saved.");
            System.out.println(error);
        }
    }

    /**
     * The deserialize method will do the reverse of what the serializer method does.
     * This method will read the byte stream from the serialized file and load the state of
     * the object when it was saved.
     * @param filePath the save game directory
     * @return the object read from the stream
     */
    static public Object deserialize(String filePath) {
        try {
            var file = new FileInputStream(filePath);
            var in = new ObjectInputStream(file);
            var data = in.readObject();
            in.close();
            file.close();
            return data;
        } catch (Exception error) {
            System.out.println(error);
            return false;
        }
    }
}


