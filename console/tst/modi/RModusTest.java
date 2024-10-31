package modi;

import cli.CLI;
import enums.PrintChoice;
import eventHandler.Handler;
import inputListener.PrintChoiceListener;
import events.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import printListener.PrintCustomersListener;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class RModusTest {

    @org.junit.jupiter.api.Test
    void execute_printCustomers_true() {

        String input = "customers" + System.getProperty("line.separator") + ":q" + System.getProperty("line.separator");
        String output;
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;

        Storage storage = new Storage(10);
        storage.addCustomer("Liesl");
        StorageFacade facade = new StorageFacade(storage);

        try {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            CLI cli = new CLI();

            Handler<PrintCustomersEvent> printCustomersHandler = new Handler<>();
            printCustomersHandler.add(new PrintCustomersListener());

            Handler<PrintChoiceEvent> printEventHandler = new Handler<>();
            printEventHandler.add(new PrintChoiceListener(facade, printCustomersHandler, new Handler<>(), new Handler<>(), new Handler<>()));
            cli.setPrintChoiceHandler(printEventHandler);

            RModus rModusUnderTest = new RModus(cli);
            rModusUnderTest.execute();

            output = outputStream.toString();

        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertTrue(output.length() > 5);
    }

    @org.junit.jupiter.api.Test
    void execute_notAnInteger_true() {

        String input = "f" + System.getProperty("line.separator") + ":q" + System.getProperty("line.separator");
        String output;
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;

        Storage storage = new Storage(10);
        storage.addCustomer("Liesl");
        StorageFacade facade = new StorageFacade(storage);

        try {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            CLI cli = new CLI();

            Handler<PrintCustomersEvent> printCustomersHandler = new Handler<>();
            printCustomersHandler.add(new PrintCustomersListener());

            Handler<PrintChoiceEvent> printEventHandler = new Handler<>();
            printEventHandler.add(new PrintChoiceListener(facade, printCustomersHandler, new Handler<>(), new Handler<>(), new Handler<>()));
            cli.setPrintChoiceHandler(printEventHandler);

            RModus rModusUnderTest = new RModus(cli);
            rModusUnderTest.execute();

            output = outputStream.toString();

        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals(0, output.length());
    }

    @org.junit.jupiter.api.Test
    void eventToHandler_spy_equals() {
        CLI cli = new CLI();
        Handler<PrintChoiceEvent> mockHandler = Mockito.mock(Handler.class);
        cli.setPrintChoiceHandler(mockHandler);
        RModus rModusUnderTest = new RModus(cli);
        PrintChoiceEvent event = new PrintChoiceEvent(this, PrintChoice.customers, null);
        ArgumentCaptor<PrintChoiceEvent> captor = ArgumentCaptor.forClass(PrintChoiceEvent.class);
        rModusUnderTest.eventToHandler(event);
        verify(mockHandler).handle(captor.capture());
        assertEquals(event, captor.getValue());
    }
}