package events;

import cargo.CargoType;
import cargo.Hazard;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.EventObject;

public class CargoEvent extends EventObject {
    private CargoType type;
    private String name;
    private BigDecimal value;
    private Collection<Hazard> hazards;
    private boolean fragile;
    private boolean pressurized;
    private int grainSize;

    public CargoEvent(Object source, CargoType type, String name, BigDecimal value, Collection<Hazard> hazards, boolean fragile, boolean pressurized, int grainSize) {
        super(source);
        this.type = type;
        this.name = name;
        this.value = value;
        this.hazards = hazards;
        this.fragile = fragile;
        this.pressurized = pressurized;
        this.grainSize = grainSize;
    }

    public CargoType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Collection<Hazard> getHazards() {
        return hazards;
    }

    public boolean isFragile() {
        return fragile;
    }

    public boolean isPressurized() {
        return pressurized;
    }

    public int getGrainSize() {
        return grainSize;
    }
}
