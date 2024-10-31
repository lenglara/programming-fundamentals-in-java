package composition;

import cargo.Hazard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

public class CargoDescription implements Serializable {
    private BigDecimal value;
    private Collection<Hazard> hazards;

    public CargoDescription(BigDecimal value, Collection<Hazard> hazards) {
        this.value = value;
        this.hazards = hazards;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Collection<Hazard> getHazards() {
        return hazards;
    }
}
