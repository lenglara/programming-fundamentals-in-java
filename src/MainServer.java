import dataStoring.SerializationMethods;
import enums.Protocol;
import inputListener.*;
import logic.TCPServer;
import storageAdministration.Storage;
import eventHandler.Handler;
import events.*;
import listener.*;
import logic.ServerLogic;
import storageAdministration.StorageFacade;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventObject;

public class MainServer {

    public static void main(String[] args) throws IOException {

        int storageSize = 100;

        if (args.length > 1 && args[1].matches("-?\\d+")) {
            storageSize = Integer.parseInt(args[1]);
        }

        Storage storage = new Storage(storageSize);
        StorageFacade facade = new StorageFacade(storage);
        SerializationMethods serialization = new SerializationMethods("storage.ser");

        ServerLogic serverLogic = new ServerLogic();

        Handler<InteractionEvent> interactionEventHandler = new Handler<>();

        Handler<IntegerEvent> deleteCargoEventHandler = new Handler<>();
        deleteCargoEventHandler.add(new DeleteCargoListener(facade, interactionEventHandler));
        serverLogic.setDeleteCargoEventHandler(deleteCargoEventHandler);

        Handler<OwnerEvent> deleteOwnerEventHandler = new Handler<>();
        deleteOwnerEventHandler.add(new DeleteOwnerListener(facade, interactionEventHandler));
        serverLogic.setDeleteOwnerEventHandler(deleteOwnerEventHandler);

        Handler<IntegerEvent> inspectionEventHandler = new Handler<>();
        inspectionEventHandler.add(new InspectionEventListener(facade, interactionEventHandler));
        serverLogic.setInspectionEventHandler(inspectionEventHandler);

        Handler<OwnerEvent> customerEventHandler = new Handler<>();
        customerEventHandler.add(new CustomerEventListener(facade, interactionEventHandler));
        serverLogic.setOwnerEventHandler(customerEventHandler);

        Handler<CargoEvent> cargoEventHandler = new Handler<>();
        cargoEventHandler.add(new CargoEventListener(facade, interactionEventHandler));
        serverLogic.setCargoEventHandler(cargoEventHandler);

        Handler<PrintChoiceEvent> printEventHandler = new Handler<>();
        serverLogic.setPrintEventHandler(printEventHandler);

        Handler<PersistenceEvent> saveEventHandler = new Handler<>();
        saveEventHandler.add(new SaveEventListener(facade, serialization, interactionEventHandler));
        serverLogic.setSaveEventHandler(saveEventHandler);

        Handler<PersistenceEvent> loadEventHandler = new Handler<>();
        loadEventHandler.add(new LoadEventListener(facade, serialization, interactionEventHandler));
        serverLogic.setLoadEventHandler(loadEventHandler);

        if (args.length > 0) {
            if (args[0].equals("TCP")) {
                try (ServerSocket serverSocket = new ServerSocket(4599);) {

                    do {
                        Socket socket = serverSocket.accept();
                        TCPServer server = new TCPServer(socket, facade, serverLogic);
                        new Thread(server).start();
                    } while (true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (args[0].equals("UDP")) {
                try (DatagramSocket datagramSocket = new DatagramSocket(5000)) {
                    while (true) {
                        byte[] buffer = new byte[1024];
                        DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
                        datagramSocket.receive(packetIn);

                        System.out.println("Port: " + packetIn.getPort());
                        System.out.println("New client connected!");

                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        ByteArrayInputStream byteStream = new ByteArrayInputStream(packetIn.getData(), packetIn.getOffset(), packetIn.getLength());

                        ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut);
                        ObjectInputStream objectIn = new ObjectInputStream(byteStream);

                        DataOutputStream dataOut = new DataOutputStream(byteArrayOut);
                        DataInputStream dataIn = new DataInputStream(byteStream);

                        Handler<PrintCustomersEvent> printCustomersEventHandler = new Handler<>();
                        printCustomersEventHandler.add(new ServerPrintOwnersListener(objectOut, byteArrayOut, datagramSocket, packetIn.getAddress(), packetIn.getPort()));

                        Handler<PrintCargosEvent> printCargosEventHandler = new Handler<>();
                        printCargosEventHandler.add(new ServerPrintCargosListener(objectOut, byteArrayOut, datagramSocket, packetIn.getAddress(), packetIn.getPort()));

                        Handler<PrintHazardsEvent> printHazardsEventHandler = new Handler<>();
                        printHazardsEventHandler.add(new ServerPrintHazardsListener(objectOut, byteArrayOut, datagramSocket, packetIn.getAddress(), packetIn.getPort()));

                        EventObject receivedEvent = null;
                        Character command = null;

                        while (true) {
                            try {
                                command = dataIn.readChar();
                                System.out.println(command);
                                if (command == 'c' || command == 'o' || command == 'd' || command == 'u' || command == 'r' || command == 'p') {
                                    receivedEvent = (EventObject) objectIn.readObject();
                                }

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (EOFException e) {
                                System.out.println("Client disconnected.");
                                objectIn.close();
                                break;
                            }
                            if (command != null && receivedEvent != null) {
                                serverLogic.selectCommand(command, receivedEvent);
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
