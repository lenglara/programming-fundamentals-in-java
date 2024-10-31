package composition;

import administration.Customer;
import org.mockito.Mockito;
import storageAdministration.CustomerImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StorableDescriptionTest {

    @org.junit.jupiter.api.Test
    void getOwner_equals() {
        CustomerImpl owner = new CustomerImpl("Hallodri");
        StorableDescription testDescription = new StorableDescription(owner);
        assertEquals(owner, testDescription.getOwner());
    }

    @org.junit.jupiter.api.Test
    void getDurationOfStorage_notNull() {
        StorableDescription testDescription = new StorableDescription(Mockito.mock(CustomerImpl.class));
        assertNotNull(testDescription.getDurationOfStorage());
    }

    @org.junit.jupiter.api.Test
    void getLastInspectionDate_notNull() {
        StorableDescription testDescription = new StorableDescription(Mockito.mock(CustomerImpl.class));
        assertNotNull(testDescription.getLastInspectionDate());
    }

    @org.junit.jupiter.api.Test
    void setLastInspectionDate_intoTheFuture_true() {
        StorableDescription testDescription = new StorableDescription(Mockito.mock(CustomerImpl.class));
        Date first = testDescription.getLastInspectionDate();
        Date later = new Date(first.getTime() + 100);
        testDescription.setLastInspectionDate(later);
        assertTrue(testDescription.getLastInspectionDate().after(first));
    }

    @org.junit.jupiter.api.Test
    void getStorageLocation_equals() {
        StorableDescription testDescription = new StorableDescription(new CustomerImpl("Hallodri"));
        testDescription.setStorageLocation(99);
        assertEquals(99, testDescription.getStorageLocation());
    }
}