import events.*;
import inputListener.CargoEventListener;
import inputListener.CustomerEventListener;
import inputListener.DeleteCargoListener;
import observerInterfaces.Observer;
import runnables.RunnableCreate;
import runnables.RunnableDelete;
import storageAdministration.Storage;
import cli.CLI;
import eventHandler.Handler;
import modi.CModus;
import modi.DModus;
import simObserve.SimObserver;
import storageAdministration.StorageFacade;

import java.util.Scanner;

public class MainSimOne {

    private static final Object monitor = new Object();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI();
        int num = 0;
        while (true) {
            System.out.println("Enter the storage capacity: ");
            String input = cli.readString();
            if (input.matches("\\d+")) {
                num = Integer.parseInt(input);
                break;
            }
            else {
                System.out.println("Input is not a (positive) Integer.");
            }
        }
        Storage storage = new Storage(num);
        StorageFacade facade = new StorageFacade(storage);

        Observer observer = new SimObserver(storage);
        storage.addObserver(observer);

        Handler<InteractionEvent> interactionEventHandler = new Handler<>();

        Handler<CargoEvent> cargoEventHandler = new Handler<>();
        cargoEventHandler.add(new CargoEventListener(facade, interactionEventHandler));
        cli.setCargoEventHandler(cargoEventHandler);

        Handler<OwnerEvent> customerEventHandler = new Handler<>();
        customerEventHandler.add(new CustomerEventListener(facade, interactionEventHandler));
        cli.setCustomerEventHandler(customerEventHandler);

        Handler<IntegerEvent> deleteEventHandler = new Handler<>();
        deleteEventHandler.add(new DeleteCargoListener(facade, interactionEventHandler));
        cli.setDeleteCargoHandler(deleteEventHandler);

        RunnableCreate rc = new RunnableCreate(new CModus(cli), monitor);
        Thread threadCreate = new Thread(rc);
        threadCreate.start();

        RunnableDelete rd = new RunnableDelete(storage, cli, new DModus(cli), monitor);
        Thread threadDelete = new Thread(rd);
        threadDelete.start();
    }
}
