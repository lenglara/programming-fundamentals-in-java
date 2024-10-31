package storageAdministration;

import cargo.Hazard;
import cargo.CargoType;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DryBulkCargoImplTest {

    @org.junit.jupiter.api.Test
    void getType_equals() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0);
        assertEquals(CargoType.DryBulkCargo, testCargo.getType());
    }

    @org.junit.jupiter.api.Test
    void getOwner_equals() {
        CustomerImpl owner = new CustomerImpl("Berta");
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(owner, BigDecimal.valueOf(230.76), EnumSet.of(Hazard.toxic, Hazard.flammable), 11);
        assertEquals(owner, testCargo.getOwner());
    }

    @org.junit.jupiter.api.Test
    void getDurationOfStorage_notNull() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0);
        assertNotNull(testCargo.getDurationOfStorage());
    }

    @org.junit.jupiter.api.Test
    void getLastInspectionDate_notNull() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0);
        assertNotNull(testCargo.getLastInspectionDate());
    }

    @org.junit.jupiter.api.Test
    void setLastInspectionDate_intoTheFuture_true() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, 0);
        Date first = testCargo.getLastInspectionDate();
        Date later = new Date(first.getTime() + 100);
        testCargo.setLastInspectionDate(later);
        assertTrue(testCargo.getLastInspectionDate().after(first));
    }

    @org.junit.jupiter.api.Test
    void getStorageLocation_afterAddingToStorage_equals() {
        Storage storage = new Storage(10);
        storage.addCustomer("Fred");
        DryBulkCargoImpl testCargo = Mockito.mock(DryBulkCargoImpl.class);
        when(testCargo.getOwner()).thenReturn(storage.getCustomerList().stream().findFirst().get());
        storage.addCargo(testCargo);
        assertEquals(0, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void setStorageLocation_equals() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), EnumSet.of(Hazard.toxic, Hazard.flammable), 0);
        testCargo.setStorageLocation(5);
        assertEquals(5, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void getValue_equals() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), null, 0);
        assertEquals(new BigDecimal(42), testCargo.getValue());
    }

    @org.junit.jupiter.api.Test
    void getHazards_equals() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.toxic, Hazard.flammable), 0);
        assertEquals(EnumSet.of(Hazard.toxic, Hazard.flammable), testCargo.getHazards());
    }

    @org.junit.jupiter.api.Test
    void getGrainSize_equals() {
        DryBulkCargoImpl testCargo = new DryBulkCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.explosive), 7);
        assertEquals(7, testCargo.getGrainSize());
    }
}