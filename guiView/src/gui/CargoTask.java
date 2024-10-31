package gui;

import cargo.Hazard;
import cargo.CargoType;
import javafx.concurrent.Task;
import storageAdministration.StorageFacade;

import java.math.BigDecimal;
import java.util.Collection;

public class CargoTask extends Task<Boolean> {
    private CargoType type;
    private String name;
    private BigDecimal value;
    private int grainSize;
    private Collection<Hazard> hazards;
    private boolean fragile;
    private boolean pressurized;
    private final StorageFacade facade;
    public CargoTask(CargoType type, String name, BigDecimal value, Collection<Hazard> hazards, boolean fragile, boolean pressurized, int grainSize, StorageFacade facade) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.grainSize = grainSize;
        this.hazards = hazards;
        this.fragile = fragile;
        this.pressurized = pressurized;
        this.facade = facade;    }

    @Override
    protected Boolean call() throws Exception {
        return facade.getStorage().addCargo(type, name, value, hazards, fragile, pressurized, grainSize);    }
}
