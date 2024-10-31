package storageAdministration;

public class StorageFacade {
    private Storage storage;

    public StorageFacade(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
