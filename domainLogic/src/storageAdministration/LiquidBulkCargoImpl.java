package storageAdministration;

import cargo.Hazard;
import cargo.LiquidBulkCargo;
import composition.CargoDescription;
import composition.LiquidBulkCargoDescription;
import composition.StorableDescription;
import cargo.CargoType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkCargoImpl implements LiquidBulkCargo, CargoDoItAll, Serializable {

    private StorableDescription storableDescription;
    private CargoDescription cargoDescription;
    private LiquidBulkCargoDescription liquidBulkCargoDescription;

    public LiquidBulkCargoImpl(CustomerImpl owner, BigDecimal value, Collection<Hazard> hazards, boolean pressurized) {
        this.storableDescription = new StorableDescription(owner);
        this.cargoDescription = new CargoDescription(value, hazards);
        this.liquidBulkCargoDescription = new LiquidBulkCargoDescription(pressurized);
    }

    public CargoType getType() {
        return CargoType.LiquidBulkCargo;
    }

    @Override
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
    public void setLastInspectionDate(Date lastInspectionDate) {
        storableDescription.setLastInspectionDate(lastInspectionDate);
    }

    @Override
    public int getStorageLocation() {
        return storableDescription.getStorageLocation();
    }

    @Override
    public boolean isPressurized() {
        return liquidBulkCargoDescription.isPressurized();
    }

    @Override
    public BigDecimal getValue() {
        return cargoDescription.getValue();
    }

    @Override
    public Collection<Hazard> getHazards() {
        return cargoDescription.getHazards();
    }

    @Override
    public void setStorageLocation(int storageLocation) {
        storableDescription.setStorageLocation(storageLocation);
    }
}
