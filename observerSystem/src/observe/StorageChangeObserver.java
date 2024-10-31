package observe;

import logAdministration.LogLogic;
import observerInterfaces.Observer;
import storageAdministration.Storage;

import java.io.Serializable;

public class StorageChangeObserver implements Observer, Serializable {
    private Storage storage;
    private LogLogic logLogic;

    public StorageChangeObserver(Storage storage, LogLogic logLogic) {
        this.storage = storage;
        this.logLogic = logLogic;
    }

    @Override
    public void update() {
        logLogic.writeToLogFile(logLogic.getChangeDescription());
    }
}
