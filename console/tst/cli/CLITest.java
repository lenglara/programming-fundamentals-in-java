package cli;

import eventHandler.Handler;
import inputListener.CustomerEventListener;
import inputListener.PrintChoiceListener;
import events.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import printListener.PrintCargosListener;
import printListener.PrintCustomersListener;
import printListener.PrintHazardsListener;
import storageAdministration.CustomerImpl;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CLITest {

    @org.junit.jupiter.api.Test
    void readString_equals() {
        String input = "Test";
        String received;
        InputStream originalInput = System.in;
        CLI cli;
        try {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            cli = new CLI();
            received = cli.readString();

        } finally {
            System.setIn(originalInput);
        }
        assertEquals(input, received);
    }

    @org.junit.jupiter.api.Test
    void checkForModusChange_verify() {
        CLI spyCli = spy(new CLI());
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        spyCli.checkForModusChange(":k");
        verify(spyCli).changeModus(captor.capture());
        assertEquals(":k", captor.getValue());
    }

    @org.junit.jupiter.api.Test
    void changeModus_verify() {
        CLI cli = new CLI();
        cli.changeModus(":q");
        assertFalse(cli.isRunning());
    }

    @org.junit.jupiter.api.Test
    void startCLI_addCustomer_equals() {
        String input = "Test" + System.getProperty("line.separator") + ":h" + System.getProperty("line.separator");
        InputStream originalInput = System.in;
        CLI cli;
        try {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            cli = spy(new CLI());
            cli.startCLI();
        } finally {
            System.setIn(originalInput);
        }
        verify(cli, times(2)).readString();
    }

    @org.junit.jupiter.api.Test
    void startCLI_printCustomers_true() {

        String input = ":r" + System.getProperty("line.separator") + "customers" + System.getProperty("line.separator") + ":q" + System.getProperty("line.separator");
        String output;
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;

        StorageFacade facadeMock = mock(StorageFacade.class);
        Storage storageMock = mock(Storage.class);
        when(facadeMock.getStorage()).thenReturn(storageMock);
        when(storageMock.getCustomerList()).thenReturn(new HashSet<>(Collections.singleton(new CustomerImpl("Lissi"))));

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
            printEventHandler.add(new PrintChoiceListener(facadeMock, printCustomersHandler, mock(Handler.class), mock(Handler.class), mock(Handler.class)));
            cli.setPrintChoiceHandler(printEventHandler);

            cli.startCLI();

            output = outputStream.toString();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
        assertTrue(output.length() > 5);
    }


    @org.junit.jupiter.api.Test
    void getCustomerEventHandler_null() {
        CLI cli = new CLI();
        assertNull(cli.getCustomerEventHandler());
    }

    @org.junit.jupiter.api.Test
    void getCustomerEventHandler_notNull() {
        CLI cli = new CLI();
        cli.setCustomerEventHandler(new Handler<>());
        assertNotNull(cli.getCustomerEventHandler());
    }

    @org.junit.jupiter.api.Test
    void setCustomerEventHandler_equals() {
        CLI cli = new CLI();
        Handler<OwnerEvent> handlerToCompare = new Handler<>();
        cli.setCustomerEventHandler(handlerToCompare);
        assertEquals(handlerToCompare, cli.getCustomerEventHandler());
    }

    @org.junit.jupiter.api.Test
    void getPrintChoiceHandler_null() {
        CLI cli = new CLI();
        assertNull(cli.getPrintChoiceHandler());
    }

    @org.junit.jupiter.api.Test
    void getPrintChoiceHandler_notNull() {
        CLI cli = new CLI();
        cli.setPrintChoiceHandler(new Handler<>());
        assertNotNull(cli.getPrintChoiceHandler());
    }

    @org.junit.jupiter.api.Test
    void setPrintChoiceHandler_equals() {
        CLI cli = new CLI();
        Handler<PrintChoiceEvent> handlerToCompare = new Handler<>();
        cli.setPrintChoiceHandler(handlerToCompare);
        assertEquals(handlerToCompare, cli.getPrintChoiceHandler());
    }
}