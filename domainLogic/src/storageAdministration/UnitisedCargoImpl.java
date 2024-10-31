package storageAdministration;

import cargo.Hazard;
import cargo.UnitisedCargo;
import composition.CargoDescription;
import composition.StorableDescription;
import composition.UnitisedCargoDescription;
import cargo.CargoType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class UnitisedCargoImpl implements UnitisedCargo, CargoDoItAll, Serializable {
    private StorableDescription storableDescription;
    private CargoDescription cargoDescription;
    private UnitisedCargoDescription unitisedCargoDescription;

    public UnitisedCargoImpl(CustomerImpl owner, BigDecimal value, Collection<Hazard> hazards, boolean fragile) {
        this.storableDescription = new StorableDescription(owner);
        this.cargoDescription = new CargoDescription(value, hazards);
        this.unitisedCargoDescription = new UnitisedCargoDescription(fragile);
    }

    public CargoType getType() {
        return CargoType.UnitisedCargo;
    }

    public CustomerImpl getOwner() {
        return storableDescription.getOwner();
    }

    public Duration getDurationOfStorage() {
        return storableDescription.getDurationOfStorage();
    }

    public Date getLastInspectionDate() {
        return storableDescription.getLastInspectionDate();
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        storableDescription.setLastInspectionDate(lastInspectionDate);
    }

    public int getStorageLocation() {
        return storableDescription.getStorageLocation();
    }

    public void setStorageLocation(int storageLocation) {
        storableDescription.setStorageLocation(storageLocation);
    }

    public BigDecimal getValue() {
        return cargoDescription.getValue();
    }

    public Collection<Hazard> getHazards() {
        return cargoDescription.getHazards();
    }

    public boolean isFragile() {
        return unitisedCargoDescription.isFragile();
    }
}
