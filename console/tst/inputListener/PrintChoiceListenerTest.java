package inputListener;

import enums.PrintChoice;
import eventHandler.Handler;
import events.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrintChoiceListenerTest {

    @org.junit.jupiter.api.Test
    void onEvent_getNum_verify() {
        PrintChoiceListener testListener = new PrintChoiceListener(new StorageFacade(new Storage(10)), Mockito.mock(Handler.class), Mockito.mock(Handler.class), Mockito.mock(Handler.class), Mockito.mock(Handler.class));
        PrintChoiceEvent spyEvent = Mockito.mock(PrintChoiceEvent.class);
        testListener.onEvent(spyEvent);
        verify(spyEvent).getChoice();
    }

    @org.junit.jupiter.api.Test
    void onEvent_spyPrintEvent_equals() {
        Storage storage = new Storage(10);
        storage.addCustomer("Heinz");
        Handler<PrintCustomersEvent> spyHandler = Mockito.mock(Handler.class);
        PrintChoiceListener testListener = new PrintChoiceListener(new StorageFacade(storage), spyHandler, Mockito.mock(Handler.class), Mockito.mock(Handler.class), Mockito.mock(Handler.class));
        ArgumentCaptor<PrintCustomersEvent> captor = ArgumentCaptor.forClass(PrintCustomersEvent.class);
        testListener.onEvent(new PrintChoiceEvent(this, PrintChoice.customers, null));
        verify(spyHandler).handle(captor.capture());
        assertEquals("Heinz", captor.getValue().getCustomerSet().stream().findFirst().get().getName());
    }

}