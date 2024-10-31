package storageAdministration;

import cargo.Hazard;
import cargo.LiquidAndDryBulkCargo;
import composition.CargoDescription;
import composition.DryBulkCargoDescription;
import composition.LiquidBulkCargoDescription;
import composition.StorableDescription;
import cargo.CargoType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class LiquidAndDryBulkCargoImpl implements LiquidAndDryBulkCargo, CargoDoItAll, Serializable {
    private StorableDescription storableDescription;
    private CargoDescription cargoDescription;
    private DryBulkCargoDescription dryBulkCargoDescription;
    private LiquidBulkCargoDescription liquidBulkCargoDescription;

    public LiquidAndDryBulkCargoImpl(CustomerImpl owner, BigDecimal value, Collection<Hazard> hazards, int grainSize, boolean pressurized) {
        this.storableDescription = new StorableDescription(owner);
        this.cargoDescription = new CargoDescription(value, hazards);
        this.dryBulkCargoDescription = new DryBulkCargoDescription(grainSize);
        this.liquidBulkCargoDescription = new LiquidBulkCargoDescription(pressurized);
    }

    public CargoType getType() {
        return CargoType.LiquidAndDryBulkCargo;
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

    public boolean isPressurized() {
        return liquidBulkCargoDescription.isPressurized();
    }

    public int getGrainSize() {
        return dryBulkCargoDescription.getGrainSize();
    }
}
