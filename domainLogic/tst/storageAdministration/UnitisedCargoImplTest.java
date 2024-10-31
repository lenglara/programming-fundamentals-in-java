package storageAdministration;

import cargo.Hazard;
import cargo.CargoType;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UnitisedCargoImplTest {

    @org.junit.jupiter.api.Test
    void getType_equals() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false);
        assertEquals(CargoType.UnitisedCargo, testCargo.getType());
    }

    @org.junit.jupiter.api.Test
    void getOwner_equals() {
        CustomerImpl owner = new CustomerImpl("Berta");
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(owner, BigDecimal.valueOf(0), null, false);
        assertEquals(owner, testCargo.getOwner());
    }

    @org.junit.jupiter.api.Test
    void getDurationOfStorage_notNull() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false);
        assertNotNull(testCargo.getDurationOfStorage());
    }

    @org.junit.jupiter.api.Test
    void getLastInspectionDate_notNull() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false);
        assertNotNull(testCargo.getLastInspectionDate());
    }

    @org.junit.jupiter.api.Test
    void setLastInspectionDate_intoTheFuture_true() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false);
        Date first = testCargo.getLastInspectionDate();
        Date later = new Date(first.getTime() + 100);
        testCargo.setLastInspectionDate(later);
        assertTrue(testCargo.getLastInspectionDate().after(first));
    }

    @org.junit.jupiter.api.Test
    void getStorageLocation_afterAddingToStorage_equals() {
        Storage storage = new Storage(10);
        storage.addCustomer("Fred");
        UnitisedCargoImpl testCargo = Mockito.mock(UnitisedCargoImpl.class);
        when(testCargo.getOwner()).thenReturn(storage.getCustomerList().stream().findFirst().get());
        storage.addCargo(testCargo);
        assertEquals(0, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void setStorageLocation_equals() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, false);
        testCargo.setStorageLocation(5);
        assertEquals(5, testCargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void getValue_equals() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), null, false);
        assertEquals(new BigDecimal(42), testCargo.getValue());
    }

    @org.junit.jupiter.api.Test
    void getHazards_equals() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(42), EnumSet.of(Hazard.toxic, Hazard.flammable), false);
        assertEquals(EnumSet.of(Hazard.toxic, Hazard.flammable), testCargo.getHazards());
    }

    @org.junit.jupiter.api.Test
    void isFragile_true() {
        UnitisedCargoImpl testCargo = new UnitisedCargoImpl(Mockito.mock(CustomerImpl.class), new BigDecimal(0), null, true);
        assertTrue(testCargo.isFragile());
    }
}