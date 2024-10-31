package storageAdministration;

import cargo.CargoStorable;
import observerInterfaces.Observer;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static cargo.CargoType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageTest {
    @org.junit.jupiter.api.Test
    void getMaxSize_notNull() {
        Storage storageUnderTest = new Storage(10);
        assertNotNull(storageUnderTest.getMaxSize());
    }

    @org.junit.jupiter.api.Test
    void getMaxSize_equals() {
        int maxSize = 10;
        Storage storageUnderTest = new Storage(maxSize);
        assertEquals(maxSize, storageUnderTest.getMaxSize());
    }

    @org.junit.jupiter.api.Test
    void addCustomer_notExisting_true() {
        Storage storageUnderTest = new Storage(10);
        boolean test = storageUnderTest.addCustomer("Buttercup");
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCustomer_alreadyExisting_false() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Henry");
        assertFalse(storageUnderTest.addCustomer("Henry"));
    }


    @org.junit.jupiter.api.Test
    void addCargo_toEmptyStorage_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Heinz");
        DryBulkCargoImpl cargo = Mockito.mock(DryBulkCargoImpl.class);
        when(cargo.getOwner()).thenReturn(storageUnderTest.getCustomerList().stream().findFirst().get());
        boolean test = storageUnderTest.addCargo(cargo);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_toFullStorage_false() {
        Storage storageUnderTest = new Storage(0);
        boolean test = storageUnderTest.addCargo(Mockito.mock(CargoDoItAll.class));
        assertFalse(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_customerNotExisting_false() {
        Storage storageUnderTest = new Storage(10);
        boolean test = storageUnderTest.addCargo(DryBulkCargo, "Heinz", new BigDecimal(780.90), null, false, false, 0);
        assertFalse(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_customerExisting_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Henry");
        boolean test = storageUnderTest.addCargo(DryBulkCargo, "Henry", new BigDecimal(780.90), null, false, false, 10);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_liquidBulkCargo_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Bernd");
        boolean test = storageUnderTest.addCargo(LiquidBulkCargo, "Bernd", new BigDecimal(780.90), null, false, true, 0);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_unitisedCargo_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Bernd");
        boolean test = storageUnderTest.addCargo(UnitisedCargo, "Bernd", new BigDecimal(780.90), null, true, false, 0);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_dryBulkAndUnitisedCargo_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Bernd");
        boolean test = storageUnderTest.addCargo(DryBulkAndUnitisedCargo, "Bernd", new BigDecimal(780.90), null, true, false, 10);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_liquidAndDryBulkCargo_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Bernd");
        boolean test = storageUnderTest.addCargo(LiquidAndDryBulkCargo, "Bernd", new BigDecimal(780.90), null, false, false, 10);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_liquidBulkAndUnitisedCargo_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Bernd");
        boolean test = storageUnderTest.addCargo(LiquidBulkAndUnitisedCargo, "Bernd", new BigDecimal(780.90), null, true, true, 0);
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    void addCargo_checkNumCargos_true() {
        Storage storageUnderTest = new Storage(10);
        DryBulkCargoImpl cargo = new DryBulkCargoImpl(new CustomerImpl("Heinz"), new BigDecimal(500.00), null, 10);
        storageUnderTest.addCustomer("Heinz");
        storageUnderTest.addCargo(cargo);
        Optional<CustomerImpl> optionalCustomer = storageUnderTest.getCustomerList().stream()
                        .findFirst();
        assertEquals(1, optionalCustomer.get().getNumCargos());
    }

    @org.junit.jupiter.api.Test
    void addCargo_withStorageLocation_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Heinz");
        LiquidBulkCargoImpl cargo = Mockito.mock(LiquidBulkCargoImpl.class);
        when(cargo.getOwner()).thenReturn(storageUnderTest.getCustomerList().stream().findFirst().get());
        assertTrue(storageUnderTest.placeCargo(8, cargo));
    }

    @org.junit.jupiter.api.Test
    void addCargo_withStorageLocation_equals() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Heinz");
        CustomerImpl owner = storageUnderTest.getCustomerList().stream().findFirst().get();
        LiquidBulkCargoImpl cargo = new LiquidBulkCargoImpl(owner, BigDecimal.valueOf(12.00), null, true);
        storageUnderTest.placeCargo(8, cargo);
        assertEquals(8, cargo.getStorageLocation());
    }

    @org.junit.jupiter.api.Test
    void getCustomerList_length_equals() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Henry");
        storageUnderTest.addCustomer("Susi");
        assertEquals(2, storageUnderTest.getCustomerList().size());
    }

    @org.junit.jupiter.api.Test
    void getCustomerList_emptyStorage_equals() {
        Storage storageUnderTest = new Storage(10);
        assertEquals(0, storageUnderTest.getCustomerList().size());
    }

    @org.junit.jupiter.api.Test
    void getCargoList_emptyStorage_null() {
        Storage storageUnderTest = new Storage(10);
        assertNull(storageUnderTest.getCargoList().get(0));
    }

    @org.junit.jupiter.api.Test
    void getCargoList_filledStorage_notNull() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Heinz");
        DryBulkCargoImpl cargo = Mockito.mock(DryBulkCargoImpl.class);
        when(cargo.getOwner()).thenReturn(storageUnderTest.getCustomerList().stream().findFirst().get());
        storageUnderTest.addCargo(cargo);
        assertNotNull(storageUnderTest.getCargoList().get(0));
    }

    @org.junit.jupiter.api.Test
    void getCargoList_length_equals() {
        Storage storageUnderTest = new Storage(100);
        storageUnderTest.addCustomer("Susi");
        for (int i = 0; i < 65; i++) {
            storageUnderTest.addCargo(UnitisedCargo, "Susi", new BigDecimal(780.90), null, true, false, 0);
        }
        assertEquals(65, storageUnderTest.getCargoList().size());
    }

    @org.junit.jupiter.api.Test
    void getCargoList_withType_equals() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Mausi");
        storageUnderTest.addCargo(new LiquidBulkCargoImpl(new CustomerImpl("Mausi"), BigDecimal.valueOf(120), null, true));
        storageUnderTest.addCargo(UnitisedCargo, "Mausi", new BigDecimal(780.90), null, true, false, 0);
        HashMap<Integer, CargoStorable> filteredCargoList = storageUnderTest.getCargoList(UnitisedCargo);
        assertEquals(1, filteredCargoList.size());
    }

    @org.junit.jupiter.api.Test
    void deleteCargo_notExisting_false() {
        Storage storageUnderTest = new Storage(10);
        assertFalse(storageUnderTest.deleteCargo(0));
    }

    @org.junit.jupiter.api.Test
    void deleteCargo_success_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Weihnachtsmann");
        DryBulkCargoImpl cargo = Mockito.mock(DryBulkCargoImpl.class);
        when(cargo.getOwner()).thenReturn(storageUnderTest.getCustomerList().stream().findFirst().get());
        storageUnderTest.addCargo(cargo);
        assertTrue(storageUnderTest.deleteCargo(0));
    }

    @org.junit.jupiter.api.Test
    void deleteCargo_listEmpty_null() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Weihnachtsmann");
        DryBulkCargoImpl cargo = Mockito.mock(DryBulkCargoImpl.class);
        when(cargo.getOwner()).thenReturn(storageUnderTest.getCustomerList().stream().findFirst().get());
        storageUnderTest.addCargo(cargo);
        storageUnderTest.deleteCargo(0);
        assertNull(storageUnderTest.getCargoList().get(0));
    }

    @org.junit.jupiter.api.Test
    void deleteCustomer_notExisting_false() {
        Storage storageUnderTest = new Storage(10);
        assertFalse(storageUnderTest.deleteCustomer("Niemand"));
    }

    @org.junit.jupiter.api.Test
    void deleteCustomer_existing_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Lili");
        assertTrue(storageUnderTest.deleteCustomer("Lili"));
    }

    @org.junit.jupiter.api.Test
    void deleteCustomer_tryToAddCustomerAgain_true() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Lili");
        storageUnderTest.deleteCustomer("Lili");
        assertTrue(storageUnderTest.addCustomer("Lili"));
    }

    @org.junit.jupiter.api.Test
    void deleteCustomer_checkIfCargoWasDeleted_false() {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Lili");
        storageUnderTest.addCargo(DryBulkCargo, "Lili", new BigDecimal(780.90), null, false, false, 0);
        storageUnderTest.deleteCustomer("Lili");
        assertFalse(storageUnderTest.deleteCargo(0));
    }

    @org.junit.jupiter.api.Test
    void inspection_correctOrder_true() throws InterruptedException {
        Storage storageUnderTest = new Storage(10);
        storageUnderTest.addCustomer("Lena");
        DryBulkCargoImpl cargo = Mockito.mock(DryBulkCargoImpl.class);
        when(cargo.getOwner()).thenReturn(storageUnderTest.getCustomerList().stream().findFirst().get());
        storageUnderTest.addCargo(cargo);
        Date dateToCompare = new Date();
        Date inspectionDate = storageUnderTest.inspectCargo(0);
        assertTrue(inspectionDate.after(dateToCompare) || inspectionDate.equals(dateToCompare));
    }

    @org.junit.jupiter.api.Test
    void inspection_noElement_null()  {
        Storage storageUnderTest = new Storage(10);
        assertNull(storageUnderTest.inspectCargo(0));
    }

    @org.junit.jupiter.api.Test
    void addObserver_spyUpdate_verify() {
        Storage storageUnderTest = new Storage(10);
        Observer spyObserver = Mockito.mock(Observer.class);
        storageUnderTest.addObserver(spyObserver);
        storageUnderTest.notifyObservers();
        verify(spyObserver).update();
    }

    @org.junit.jupiter.api.Test
    void addCargo_spyNotify_verify() {
        Storage spyStorage = spy(new Storage(10));
        spyStorage.addCustomer("Bibi");
        LiquidBulkCargoImpl cargo = new LiquidBulkCargoImpl(spyStorage.getCustomerList().stream().findFirst().get(), BigDecimal.valueOf(120), null, true);
        spyStorage.placeCargo(8, cargo);
        verify(spyStorage, times(2)).notifyObservers();
    }

    @org.junit.jupiter.api.Test
    void addCargo_addingUnsuccessful_verify() {
        Storage spyStorage = spy(new Storage(10));
        spyStorage.addCustomer("Max");
        LiquidBulkCargoImpl cargo = new LiquidBulkCargoImpl(new CustomerImpl("Moritz"), BigDecimal.valueOf(120), null, true);
        spyStorage.placeCargo(8, cargo);
        verify(spyStorage).notifyObservers();
    }
}