package composition;

import static org.junit.jupiter.api.Assertions.*;

class UnitisedCargoDescriptionTest {

    @org.junit.jupiter.api.Test
    void isFragile_true() {
        UnitisedCargoDescription testDescription = new UnitisedCargoDescription(true);
        assertTrue(testDescription.isFragile());
    }
}