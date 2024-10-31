package composition;

import cargo.Hazard;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CargoDescriptionTest {

    @org.junit.jupiter.api.Test
    void getValue_equals() {
        CargoDescription testDescription = new CargoDescription(BigDecimal.valueOf(42), null);
        assertEquals(BigDecimal.valueOf(42), testDescription.getValue());
    }

    @org.junit.jupiter.api.Test
    void getHazards_equals() {
        HashSet<Hazard> hazards = new HashSet<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.radioactive);
        CargoDescription testDescription = new CargoDescription(BigDecimal.valueOf(42), hazards);
        assertEquals(hazards, testDescription.getHazards());
    }
}