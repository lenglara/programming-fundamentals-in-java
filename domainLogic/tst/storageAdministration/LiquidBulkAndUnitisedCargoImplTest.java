package storageAdministration;

import cargo.Hazard;
import cargo.LiquidBulkAndUnitisedCargo;
import cargo.CargoType;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LiquidBulkAndUnitisedCargoImplTest {

    @org.junit.jupiter.api.Test
    void getType_equals() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false, false);
        assertEquals(CargoType.LiquidBulkAndUnitisedCargo, testCargo.getType());
    }

    @org.junit.jupiter.api.Test
    void getOwner_equals() {
        CustomerImpl owner = new CustomerImpl("Berta");
        LiquidBulkAndUnitisedCargo testCargo = new LiquidBulkAndUnitisedCargoImpl(owner, BigDecimal.valueOf(0), null, false, false);
        assertEquals(owner, testCargo.getOwner());
    }

    @org.junit.jupiter.api.Test
    void getDurationOfStorage_notNull() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false, false);
        assertNotNull(testCargo.getDurationOfStorage());
    }

    @org.junit.jupiter.api.Test
    void getLastInspectionDate_notNull() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false, false);
        assertNotNull(testCargo.getLastInspectionDate());
    }

    @org.junit.jupiter.api.Test
    void setLastInspectionDate_intoTheFuture_true() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false, false);
        Date first = testCargo.getLastInspectionDate();
        Date later = new Date(first.getTime() + 100);
        testCargo.setLastInspectionDate(later);
        assertTrue(testCargo.getLastInspectionDate().after(first));
    }

    @org.junit.jupiter.api.Test
    void getStorageLocation_afterAddingToStorage_equals() {
        Storage storage = new Storage(10);
        storage.addCustomer("Fred");
        LiquidBulkAndUnitisedCargoImpl testCargo = Mockito.mock(LiquidBulkAndUnitisedCargoImpl.class);
        when(testCargo.getOwner()).thenReturn(storage.getCustomerList().stream().findFirst().get());
        storage.addCargo(testCargo);
        assertEquals(0, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void setStorageLocation_equals() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false, false);
        testCargo.setStorageLocation(5);
        assertEquals(5, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void getValue_equals() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), null, false, false);
        assertEquals(new BigDecimal(42), testCargo.getValue());
    }

    @org.junit.jupiter.api.Test
    void getHazards_equals() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.toxic, Hazard.flammable), false, false);
        assertEquals(EnumSet.of(Hazard.toxic, Hazard.flammable), testCargo.getHazards());
    }

    @org.junit.jupiter.api.Test
    void isFragile_true() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.explosive), false, true);
        assertTrue(testCargo.isFragile());
    }

    @org.junit.jupiter.api.Test
    void isPressurized_true() {
        LiquidBulkAndUnitisedCargoImpl testCargo = new LiquidBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, true, false);
        assertTrue(testCargo.isPressurized());
    }

}