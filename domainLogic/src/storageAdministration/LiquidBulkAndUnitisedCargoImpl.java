package storageAdministration;

import cargo.Hazard;
import cargo.LiquidBulkAndUnitisedCargo;
import composition.CargoDescription;
import composition.LiquidBulkCargoDescription;
import composition.StorableDescription;
import composition.UnitisedCargoDescription;
import cargo.CargoType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkAndUnitisedCargoImpl implements LiquidBulkAndUnitisedCargo, CargoDoItAll, Serializable {
    private StorableDescription storableDescription;
    private CargoDescription cargoDescription;
    private LiquidBulkCargoDescription liquidBulkCargoDescription;
    private UnitisedCargoDescription unitisedCargoDescription;

    public LiquidBulkAndUnitisedCargoImpl(CustomerImpl owner, BigDecimal value, Collection<Hazard> hazards, boolean pressurized, boolean fragile) {
        this.storableDescription = new StorableDescription(owner);
        this.cargoDescription = new CargoDescription(value, hazards);
        this.liquidBulkCargoDescription = new LiquidBulkCargoDescription(pressurized);
        this.unitisedCargoDescription = new UnitisedCargoDescription(fragile);
    }

    public CargoType getType() {
        return CargoType.LiquidBulkAndUnitisedCargo;
    }

    public boolean isFragile() {
        return unitisedCargoDescription.isFragile();
    }

    public boolean isPressurized() {
        return liquidBulkCargoDescription.isPressurized();
    }

    public BigDecimal getValue() {
        return cargoDescription.getValue();
    }

    public Collection<Hazard> getHazards() {
        return cargoDescription.getHazards();
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
}
