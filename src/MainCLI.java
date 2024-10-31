
import dataStoring.SerializationMethods;
import inputListener.*;
import listener.*;
import logAdministration.LogLogic;
import observe.HazardsObserver;
import observe.StorageChangeObserver;
import storageAdministration.Storage;
import events.*;
import printListener.PrintCargosListener;
import printListener.PrintCustomersListener;
import printListener.PrintHazardsListener;
import cli.CLI;
import eventHandler.Handler;
import observe.CargoObserver;
import observerInterfaces.*;
import storageAdministration.StorageFacade;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class MainCLI {

    public static void main(String[] args) {

        int storageSize = 100;
        if (args.length > 0 && args[0].matches("-?\\d+")) {
            storageSize = Integer.parseInt(args[0]);
        }

        Storage storage = new Storage(storageSize);
        StorageFacade facade = new StorageFacade(storage);
        SerializationMethods serialization = new SerializationMethods("storage.ser");
        CLI cli = new CLI();

        Handler<InteractionEvent> interactionEventHandler = new Handler<>();

        Handler<PrintCustomersEvent> printCustomersHandler = new Handler<>();
        printCustomersHandler.add(new PrintCustomersListener());

        Handler<PrintCargosEvent> printCargosHandler = new Handler<>();
        printCargosHandler.add(new PrintCargosListener());

        Handler<PrintHazardsEvent> printHazardsHandler = new Handler<>();
        printHazardsHandler.add(new PrintHazardsListener());

        Handler<OwnerEvent> customerEventHandler = new Handler<>();
        cli.setCustomerEventHandler(customerEventHandler);

        Handler<CargoEvent> cargoEventHandler = new Handler<>();
        cli.setCargoEventHandler(cargoEventHandler);

        Handler<PrintChoiceEvent> printEventHandler = new Handler<>();
        cli.setPrintChoiceHandler(printEventHandler);

        Handler<IntegerEvent> deleteCargoEventHandler = new Handler<>();
        cli.setDeleteCargoHandler(deleteCargoEventHandler);

        Handler<OwnerEvent> deleteOwnerEventHandler = new Handler<>();
        cli.setDeleteOwnerHandler(deleteOwnerEventHandler);

        Handler<IntegerEvent> inspectionEventHandler = new Handler<>();
        cli.setInspectionEventHandler(inspectionEventHandler);

        Handler<PersistenceEvent> saveEventHandler = new Handler<>();
        cli.setSaveEventHandler(saveEventHandler);

        Handler<PersistenceEvent> loadEventHandler = new Handler<>();
        cli.setLoadEventHandler(loadEventHandler);

        if (args.length > 1) {
            LogLogic logLogic = new LogLogic("log.txt");
            interactionEventHandler.add(new InteractionEventListener(logLogic));
            Observer storageChangeObserver = new StorageChangeObserver(storage, logLogic);
            storage.addObserver(storageChangeObserver);
            if (args[1].equals("EN")) {
                logLogic.setEnglish();
            } else if (args[1].equals("DE")) {
                logLogic.setGerman();
            }
        }

        if (args.length > 2) {
            if (args[2].equals("TCP")) {
                try (Socket socket = new Socket("localhost", 4599);
                     OutputStream out = socket.getOutputStream();
                     InputStream in = socket.getInputStream();

                     DataOutputStream dataOut=new DataOutputStream(out);
                     DataInputStream dataIn = new DataInputStream(in);

                     ObjectOutputStream objectOut = new ObjectOutputStream(out);
                     ObjectInputStream objectIn = new ObjectInputStream(in);) {

                    customerEventHandler.add(new ClientCustomerEventListener(objectOut, dataOut));

                    cargoEventHandler.add(new ClientCargoEventListener(objectOut, dataOut));

                    printEventHandler.add(new ClientPrintChoiceListener(objectOut, objectIn, dataOut, printCustomersHandler, printCargosHandler, printHazardsHandler));

                    deleteCargoEventHandler.add(new ClientDeleteCargoListener(objectOut, dataOut));

                    deleteOwnerEventHandler.add(new ClientDeleteOwnerListener(objectOut, dataOut));

                    inspectionEventHandler.add(new ClientInspectionEventListener(objectOut, dataOut));

                    saveEventHandler.add(new ClientSaveListener(objectOut, dataOut));

                    loadEventHandler.add(new ClientLoadListener(objectOut, dataOut));

                    cli.startCLI();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (args[2].equals("UDP")) {
                try (DatagramSocket datagramSocket = new DatagramSocket()) {
                    InetAddress serverAddress = InetAddress.getByName("localhost");
                    int serverPort = 5000;

                    customerEventHandler.add(new ClientCustomerEventListener(datagramSocket, serverAddress, serverPort));

                    cargoEventHandler.add(new ClientCargoEventListener(datagramSocket, serverAddress, serverPort));

                    printEventHandler.add(new ClientPrintChoiceListener(datagramSocket, serverAddress, serverPort, printCustomersHandler, printCargosHandler, printHazardsHandler));

                    deleteCargoEventHandler.add(new ClientDeleteCargoListener(datagramSocket, serverAddress, serverPort));

                    deleteOwnerEventHandler.add(new ClientDeleteOwnerListener(datagramSocket, serverAddress, serverPort));

                    inspectionEventHandler.add(new ClientInspectionEventListener(datagramSocket, serverAddress, serverPort));

                    saveEventHandler.add(new ClientSaveListener(datagramSocket, serverAddress, serverPort));

                    loadEventHandler.add(new ClientLoadListener(datagramSocket, serverAddress, serverPort));

                    cli.startCLI();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {

            Observer cargoObserver = new CargoObserver(storage);
            Observer hazardsObserver = new HazardsObserver(storage);
            storage.addObserver(cargoObserver);
            storage.addObserver(hazardsObserver);

            customerEventHandler.add(new CustomerEventListener(facade, interactionEventHandler));

            cargoEventHandler.add(new CargoEventListener(facade, interactionEventHandler));

            deleteCargoEventHandler.add(new DeleteCargoListener(facade, interactionEventHandler));

            deleteOwnerEventHandler.add(new DeleteOwnerListener(facade, interactionEventHandler));

            inspectionEventHandler.add(new InspectionEventListener(facade, interactionEventHandler));

            printEventHandler.add(new PrintChoiceListener(facade, printCustomersHandler, printCargosHandler, printHazardsHandler, interactionEventHandler));

            saveEventHandler.add(new SaveEventListener(facade, serialization, interactionEventHandler));

            loadEventHandler.add(new LoadEventListener(facade, serialization, interactionEventHandler));

            cli.startCLI();
        }
    }
}
