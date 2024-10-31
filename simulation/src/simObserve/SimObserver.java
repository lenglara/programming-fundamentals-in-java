package simObserve;

import observerInterfaces.Observer;
import storageAdministration.Storage;

public class SimObserver implements Observer {
    private Storage storage;
    private int storageSize;

    public SimObserver(Storage storage) {
        this.storage = storage;
        this.storageSize = 0;
    }
    @Override
    public void update() {
        if (storage.getCargoList().size() > storageSize) {
            System.out.println("Cargo successfully added.");
            storageSize = storage.getCargoList().size();
            if ((storage.getCargoList().size()/(float)storage.getMaxSize()) > 0.9) {
                int freeCapacity = storage.getMaxSize() - storage.getCargoList().size();
                System.out.println("Over 90 % of the storage is occupied. Free capacity left: " + freeCapacity);
            }
        }
        else if (storage.getCargoList().size() < storageSize) {
            System.out.println("Cargo has been removed.");
            storageSize = storage.getCargoList().size();
        }
    }
}
