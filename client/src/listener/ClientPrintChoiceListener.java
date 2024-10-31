package listener;

import enums.PrintChoice;
import enums.Protocol;
import eventHandler.Handler;
import eventListener.Listener;
import events.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ClientPrintChoiceListener implements Listener<PrintChoiceEvent> {
    private Protocol protocol;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private DataOutputStream dataOut;
    private DatagramSocket datagramSocket;
    private InetAddress address;
    private int port;
    private Handler<PrintCustomersEvent> printCustomersHandler;
    private Handler<PrintCargosEvent> printCargosHandler;
    private Handler<PrintHazardsEvent> printHazardsHandler;

    public ClientPrintChoiceListener(ObjectOutputStream objectOut, ObjectInputStream objectIn, DataOutputStream dataOut, Handler<PrintCustomersEvent> printCustomersHandler, Handler<PrintCargosEvent> printCargosHandler, Handler<PrintHazardsEvent> printHazardsHandler) {
        this.protocol = Protocol.TCP;
        this.objectOut = objectOut;
        this.objectIn = objectIn;
        this.dataOut = dataOut;
        this.printCustomersHandler = printCustomersHandler;
        this.printCargosHandler = printCargosHandler;
        this.printHazardsHandler = printHazardsHandler;
    }

    public ClientPrintChoiceListener(DatagramSocket datagramSocket, InetAddress address, int port, Handler<PrintCustomersEvent> printCustomersHandler, Handler<PrintCargosEvent> printCargosHandler, Handler<PrintHazardsEvent> printHazardsHandler) {
        this.protocol = Protocol.UDP;
        this.datagramSocket = datagramSocket;
        this.address = address;
        this.port = port;
        this.printCustomersHandler = printCustomersHandler;
        this.printCargosHandler = printCargosHandler;
        this.printHazardsHandler = printHazardsHandler;
    }

    @Override
    public void onEvent(PrintChoiceEvent event) {
        try {
            if (protocol.equals(Protocol.TCP)) {
                dataOut.writeChar('r');
                objectOut.writeObject(event);
                if (event.getChoice().equals(PrintChoice.customers)) {
                    PrintCustomersEvent printOEvent = (PrintCustomersEvent) objectIn.readObject();
                    printCustomersHandler.handle(printOEvent);
                } else if (event.getChoice().equals(PrintChoice.cargos)) {
                    PrintCargosEvent printCEvent = (PrintCargosEvent) objectIn.readObject();
                    printCargosHandler.handle(printCEvent);
                } else if (event.getChoice().equals(PrintChoice.hazards)) {
                    PrintHazardsEvent printHEvent = (PrintHazardsEvent) objectIn.readObject();
                    printHazardsHandler.handle(printHEvent);
                }
            } else {
                // für UDP: ...
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
