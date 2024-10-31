package inputListener;


import eventHandler.Handler;
import events.OwnerEvent;
import events.InteractionEvent;
import inputListener.CustomerEventListener;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerEventListenerTest {

    @org.junit.jupiter.api.Test
    void onEvent_spyName_equals() {
        Storage storage = Mockito.mock(Storage.class);
        CustomerEventListener testListener = new CustomerEventListener(new StorageFacade(storage), new Handler<InteractionEvent>());
        OwnerEvent mockEvent = Mockito.mock(OwnerEvent.class);
        when(mockEvent.getName()).thenReturn("Berta");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        testListener.onEvent(mockEvent);
        Mockito.verify(storage).addCustomer(captor.capture());
        assertEquals("Berta", captor.getValue());
    }

    @org.junit.jupiter.api.Test
    void onEvent_checkCustomerListSize_equals() {
        Storage storage = new Storage(10);
        CustomerEventListener testListener = new CustomerEventListener(new StorageFacade(storage), new Handler<InteractionEvent>());
        testListener.onEvent(new OwnerEvent(this, "Berta"));
        assertEquals(1, storage.getCustomerList().size());
    }
}