package modi;

import cli.CLI;
import eventHandler.Handler;
import inputListener.CustomerEventListener;
import events.CargoEvent;
import events.OwnerEvent;
import events.InteractionEvent;
import org.mockito.ArgumentCaptor;
import storageAdministration.CustomerImpl;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class CModusTest {

    @org.junit.jupiter.api.Test
    void execute_insertCustomer_true() {
        String input = "Lissi" + System.getProperty("line.separator") + ":q" + System.getProperty("line.separator");
        InputStream originalInput = System.in;

        StorageFacade facade = new StorageFacade(new Storage(10));
        Handler<OwnerEvent> spyHandler = spy(new Handler<>());
        spyHandler.add(new CustomerEventListener(facade, new Handler<>()));

        try {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            CLI cli = new CLI();
            cli.setCustomerEventHandler(spyHandler);
            cli.setCargoEventHandler(new Handler<>());

            CModus cModusUnderTest = new CModus(cli);
            cModusUnderTest.execute();

        } finally {
            System.setIn(originalInput);
        }
        ArgumentCaptor<OwnerEvent> captor = ArgumentCaptor.forClass(OwnerEvent.class);
        verify(spyHandler).handle(captor.capture());
        assertEquals("Lissi", captor.getValue().getName());
    }

    @org.junit.jupiter.api.Test
    void getCustomerEventHandler_equals() {
        CLI cli = new CLI();

        Handler<OwnerEvent> customerEventHandler = new Handler<>();
        cli.setCustomerEventHandler(customerEventHandler);

        Handler<CargoEvent> cargoEventHandler = new Handler<>();
        cli.setCargoEventHandler(cargoEventHandler);

        CModus cModusUnderTest = new CModus(cli);

        assertEquals(customerEventHandler, cModusUnderTest.getCustomerEventHandler());
    }
}