package composition;

import administration.Customer;
import storageAdministration.CustomerImpl;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

public class StorableDescription implements Serializable {
    private CustomerImpl owner;
    private Date startOfStorage;
    private Date lastInspectionDate;
    private int storageLocation;

    public StorableDescription(CustomerImpl owner) {
        this.owner = owner;
        this.startOfStorage = new Date();
        this.lastInspectionDate = new Date();
    }

    public CustomerImpl getOwner() {
        return owner;
    }

    public Duration getDurationOfStorage() {
        return Duration.between(startOfStorage.toInstant(), new Date().toInstant());
    }

    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public int getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(int storageLocation) {
        this.storageLocation = storageLocation;
    }
}
