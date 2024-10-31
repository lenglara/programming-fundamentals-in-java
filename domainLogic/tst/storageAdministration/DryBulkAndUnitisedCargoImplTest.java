package storageAdministration;

import cargo.Hazard;
import cargo.CargoType;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DryBulkAndUnitisedCargoImplTest {


    @org.junit.jupiter.api.Test
    void getType_equals() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0, false);
        assertEquals(CargoType.DryBulkAndUnitisedCargo, testCargo.getType());
    }

    @org.junit.jupiter.api.Test
    void getOwner_equals() {
        CustomerImpl owner = new CustomerImpl("Berta");
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(owner, BigDecimal.valueOf(0), null, 0, false);
        assertEquals(owner, testCargo.getOwner());
    }

    @org.junit.jupiter.api.Test
    void getDurationOfStorage_notNull() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0, false);
        assertNotNull(testCargo.getDurationOfStorage());
    }

    @org.junit.jupiter.api.Test
    void getLastInspectionDate_notNull() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0, false);
        assertNotNull(testCargo.getLastInspectionDate());
    }

    @org.junit.jupiter.api.Test
    void setLastInspectionDate_intoTheFuture_true() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0, false);
        Date first = testCargo.getLastInspectionDate();
        Date later = new Date(first.getTime() + 100);
        testCargo.setLastInspectionDate(later);
        assertTrue(testCargo.getLastInspectionDate().after(first));
    }

    @org.junit.jupiter.api.Test
    void getStorageLocation_afterAddingToStorage_equals() {
        Storage storage = new Storage(10);
        storage.addCustomer("Fred");
        DryBulkAndUnitisedCargoImpl testCargo = Mockito.mock(DryBulkAndUnitisedCargoImpl.class);
        when(testCargo.getOwner()).thenReturn(storage.getCustomerList().stream().findFirst().get());
        storage.addCargo(testCargo);
        assertEquals(0, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void setStorageLocation_equals() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0, false);
        testCargo.setStorageLocation(5);
        assertEquals(5, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void getValue_equals() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), null, 0, false);
        assertEquals(new BigDecimal(42), testCargo.getValue());
    }

    @org.junit.jupiter.api.Test
    void getHazards_equals() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.toxic, Hazard.flammable), 0, false);
        assertEquals(EnumSet.of(Hazard.toxic, Hazard.flammable), testCargo.getHazards());
    }

    @org.junit.jupiter.api.Test
    void getGrainSize_equals() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.explosive), 7, false);
        assertEquals(7, testCargo.getGrainSize());
    }

    @org.junit.jupiter.api.Test
    void isFragile_true() {
        DryBulkAndUnitisedCargoImpl testCargo = new DryBulkAndUnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0, true);
        assertTrue(testCargo.isFragile());
    }
}