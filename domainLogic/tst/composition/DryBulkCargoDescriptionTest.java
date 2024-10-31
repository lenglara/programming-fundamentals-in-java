package composition;

import static org.junit.jupiter.api.Assertions.*;

class DryBulkCargoDescriptionTest {

    @org.junit.jupiter.api.Test
    void getGrainSize_equals() {
        DryBulkCargoDescription testDescription = new DryBulkCargoDescription(7);
        assertEquals(7, testDescription.getGrainSize());
    }
}