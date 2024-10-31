package composition;

import static org.junit.jupiter.api.Assertions.*;

class LiquidBulkCargoDescriptionTest {

    @org.junit.jupiter.api.Test
    void isPressurized_true() {
        LiquidBulkCargoDescription testDescription = new LiquidBulkCargoDescription(true);
        assertTrue(testDescription.isPressurized());
    }
}