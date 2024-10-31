package runnables;

import storageAdministration.Storage;
import cli.CLI;
import events.IntegerEvent;
import modi.DModus;

import java.util.Random;

public class RunnableDelete implements Runnable {
    private Storage storage;
    private CLI cli;
    private DModus dModus;
    private Random rn = new Random();
    private final Object monitor;

    public RunnableDelete(Storage storage, CLI cli, DModus dModus, Object monitor) {
        this.storage = storage;
        this.cli = cli;
        this.dModus = dModus;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("RunnableDelete started.");
            if (storage.getCargoList().size() > 0) {
                int rand = rn.nextInt(storage.getCargoList().size());
                if (storage.getCargoList().get(rand) != null) {
                    IntegerEvent event = new IntegerEvent(this, rand);
                    synchronized (monitor) {
                        dModus.eventToHandler(event);
                    }
                }
            }
        }
    }
}
