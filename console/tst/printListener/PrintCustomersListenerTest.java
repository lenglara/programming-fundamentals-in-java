package printListener;

import eventHandler.Handler;
import events.*;

import storageAdministration.CustomerImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PrintCustomersListenerTest {

    @org.junit.jupiter.api.Test
    void onEvent_getCustomerSet_verify() {
        PrintCustomersListener testListener = new PrintCustomersListener();
        PrintCustomersEvent spyEvent = spy(new PrintCustomersEvent(this, new HashSet<CustomerImpl>()));
        testListener.onEvent(spyEvent);
        verify(spyEvent).getCustomerSet();
    }

    @org.junit.jupiter.api.Test
    void execute_notAnInteger_true() {
        String output;
        PrintStream originalOutput = System.out;

        HashSet<CustomerImpl> customerSet = new HashSet<>();
        customerSet.add(new CustomerImpl("Tunichtgut"));

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            Handler<PrintCustomersEvent> printCustomersHandler = new Handler<>();
            printCustomersHandler.add(new PrintCustomersListener());

            printCustomersHandler.handle(new PrintCustomersEvent(this, customerSet));
            output = outputStream.toString();
        } finally {
            System.setOut(originalOutput);
        }
        assertTrue(output.length() > 5);
    }
}