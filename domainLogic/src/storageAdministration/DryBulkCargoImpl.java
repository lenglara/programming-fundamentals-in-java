package storageAdministration;

import cargo.DryBulkCargo;
import cargo.Hazard;
import composition.CargoDescription;
import composition.DryBulkCargoDescription;
import composition.StorableDescription;
import cargo.CargoType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class DryBulkCargoImpl implements DryBulkCargo, CargoDoItAll, Serializable {

    private StorableDescription storableDescription;
    private CargoDescription cargoDescription;
    private DryBulkCargoDescription dryBulkCargoDescription;

    public DryBulkCargoImpl(CustomerImpl owner, BigDecimal value, Collection<Hazard> hazards, int grainSize) {
        this.storableDescription =  new StorableDescription(owner);
        this.cargoDescription = new CargoDescription(value, hazards);
        this.dryBulkCargoDescription = new DryBulkCargoDescription(grainSize);
    }

    public CargoType getType() {
        return CargoType.DryBulkCargo;
    }

    public CustomerImpl getOwner() {
        return storableDescription.getOwner();
    }

    @Override
    public Duration getDurationOfStorage() {
        return storableDescription.getDurationOfStorage();
    }

    @Override
    public Date getLastInspectionDate() {
        return storableDescription.getLastInspectionDate();
    }

    @Override
    public void setLastInspectionDate(Date date) {
        storableDescription.setLastInspectionDate(date);
    }

    @Override
    public int getStorageLocation() {
        return storableDescription.getStorageLocation();
    }

    public void setStorageLocation(int storageLocation) {
        storableDescription.setStorageLocation(storageLocation);
    }

    @Override
    public BigDecimal getValue() {
        return cargoDescription.getValue();
    }

    @Override
    public Collection<Hazard> getHazards() {
        return cargoDescription.getHazards();
    }

    public int getGrainSize() {
        return dryBulkCargoDescription.getGrainSize();
    }

}
