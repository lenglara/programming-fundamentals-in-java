package logic;

import eventHandler.Handler;
import events.PrintCargosEvent;
import events.PrintCustomersEvent;
import events.PrintHazardsEvent;
import inputListener.PrintChoiceListener;
import listener.ServerPrintCargosListener;
import listener.ServerPrintHazardsListener;
import listener.ServerPrintOwnersListener;
import storageAdministration.StorageFacade;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventObject;

public class TCPServer implements Runnable {
    Socket socket;
    StorageFacade facade;
    ServerLogic logic;

    public TCPServer(Socket socket, StorageFacade facade, ServerLogic logic) {
        this.socket = socket;
        this.facade = facade;
        this.logic = logic;
    }

    @Override
    public void run() {
        System.out.println("Port: " + socket.getPort());
        System.out.println("New client connected!");
        try {
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            DataOutputStream dataOut = new DataOutputStream(out);
            DataInputStream dataIn = new DataInputStream(in);

            ObjectOutputStream objectOut = new ObjectOutputStream(out);
            ObjectInputStream objectIn = new ObjectInputStream(in);

            Handler<PrintCustomersEvent> printCustomersHandler = new Handler<>();
            printCustomersHandler.add(new ServerPrintOwnersListener(objectOut));

            Handler<PrintCargosEvent> printCargosHandler = new Handler<>();
            printCargosHandler.add(new ServerPrintCargosListener(objectOut));

            Handler<PrintHazardsEvent> printHazardsHandler = new Handler<>();
            printHazardsHandler.add(new ServerPrintHazardsListener(objectOut));

            logic.getPrintEventHandler().add(new PrintChoiceListener(facade, printCustomersHandler, printCargosHandler, printHazardsHandler, new Handler<>()));

            EventObject returnEvent = null;
            Character command = null;

            while (true) {
                try {
                    command = dataIn.readChar();
                    returnEvent = (EventObject) objectIn.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (command != null && returnEvent != null) {
                    logic.selectCommand(command, returnEvent);
                }
            }
        } catch (IOException e) {
            // Operation failed or interrupted
        }
    }
}
