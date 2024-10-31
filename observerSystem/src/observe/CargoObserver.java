package observe;

import observerInterfaces.Observer;
import storageAdministration.Storage;

import java.io.Serializable;

public class CargoObserver implements Observer, Serializable {
    private Storage storage;

    public CargoObserver(Storage storage) {
        this.storage = storage;
    }

    public void update() {
        if ((storage.getCargoList().size()/(float)storage.getMaxSize()) > 0.9) {
            int freeCapacity = storage.getMaxSize() - storage.getCargoList().size();
            System.out.println("Over 90 % of the storage is occupied. Free capacity left: " + freeCapacity);
        }
    }
}
