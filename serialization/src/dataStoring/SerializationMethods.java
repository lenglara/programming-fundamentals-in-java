package dataStoring;

import storageAdministration.Storage;

import java.io.*;

public class SerializationMethods {

    String filename;

    public SerializationMethods(String filename) {
        this.filename = filename;
    }

    public void serialize(Storage storage) {
        File file = new File(filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            oos.writeObject(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Storage deserialize(){
        Storage loadedStorage = null;
        File file = new File(filename);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            loadedStorage = (Storage) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedStorage;
    }
}
