import cli.CLI;
import dataStoring.SerializationMethods;
import eventHandler.Handler;
import events.*;
import inputListener.*;
import logAdministration.LogLogic;
import observe.CargoObserver;
import observerInterfaces.Observer;
import printListener.PrintCargosListener;
import printListener.PrintCustomersListener;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

public class MainAlternativeCLI {

    // Die Funktionalitäten Löschen von Kund*innen und Auflisten der Gefahrenstoffe sind deaktiviert.
    // Ebenso der storageChangeObserver für das Schreiben der Änderungen in der Geschäftslogik in der Log Datei und der Hazards Observer.

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

        if (args.length > 1) {
            LogLogic logLogic = new LogLogic("log.txt");
            interactionEventHandler.add(new InteractionEventListener(logLogic));
            // Observer storageChangeObserver = new StorageChangeObserver(storage, logLogic);
            // storage.addObserver(storageChangeObserver);
            if (args[1].equals("EN")) {
                logLogic.setEnglish();
            } else if (args[1].equals("DE")) {
                logLogic.setGerman();
            }
        }

        Observer cargoObserver = new CargoObserver(storage);
        // Observer hazardsObserver = new HazardsObserver(storage);
        storage.addObserver(cargoObserver);
        // storage.addObserver(hazardsObserver);

        Handler<OwnerEvent> customerEventHandler = new Handler<>();
        customerEventHandler.add(new CustomerEventListener(facade, interactionEventHandler));
        cli.setCustomerEventHandler(customerEventHandler);

        Handler<CargoEvent> cargoEventHandler = new Handler<>();
        cargoEventHandler.add(new CargoEventListener(facade, interactionEventHandler));
        cli.setCargoEventHandler(cargoEventHandler);

        Handler<IntegerEvent> deleteCargoHandler = new Handler<>();
        deleteCargoHandler.add(new DeleteCargoListener(facade, interactionEventHandler));
        cli.setDeleteCargoHandler(deleteCargoHandler);

        Handler<OwnerEvent> deleteOwnerHandler = new Handler<>();
        // deleteOwnerHandler.add(new DeleteOwnerListener(facade, interactionEventHandler));
        cli.setDeleteOwnerHandler(deleteOwnerHandler);

        Handler<IntegerEvent> inspectionEventHandler = new Handler<>();
        inspectionEventHandler.add(new InspectionEventListener(facade, interactionEventHandler));
        cli.setInspectionEventHandler(inspectionEventHandler);

        Handler<PrintCustomersEvent> printCustomersHandler = new Handler<>();
        printCustomersHandler.add(new PrintCustomersListener());

        Handler<PrintCargosEvent> printCargosHandler = new Handler<>();
        printCargosHandler.add(new PrintCargosListener());

        Handler<PrintHazardsEvent> printHazardsHandler = new Handler<>();
        // printHazardsHandler.add(new PrintHazardsListener());

        Handler<PrintChoiceEvent> printEventHandler = new Handler<>();
        printEventHandler.add(new PrintChoiceListener(facade, printCustomersHandler, printCargosHandler, printHazardsHandler, interactionEventHandler));
        cli.setPrintChoiceHandler(printEventHandler);

        Handler<PersistenceEvent> saveEventHandler = new Handler<>();
        saveEventHandler.add(new SaveEventListener(facade, serialization, interactionEventHandler));
        cli.setSaveEventHandler(saveEventHandler);

        Handler<PersistenceEvent> loadEventHandler = new Handler<>();
        loadEventHandler.add(new LoadEventListener(facade, serialization, interactionEventHandler));
        cli.setLoadEventHandler(loadEventHandler);

        cli.startCLI();
    }
}
