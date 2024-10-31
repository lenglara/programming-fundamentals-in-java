package cli;

import eventHandler.Handler;
import events.*;
import modi.*;

import java.util.Objects;
import java.util.Scanner;

public class CLI {

    private Scanner scanner;
    private boolean running;

    private Handler<CargoEvent> cargoEventHandler;
    private Handler<IntegerEvent> deleteCargoHandler, inspectionEventHandler;
    private Handler<PrintChoiceEvent> printChoiceHandler;
    private Handler<OwnerEvent> deleteOwnerHandler, customerEventHandler;
    private Handler<PersistenceEvent> saveEventHandler, loadEventHandler;
    private Handler<InteractionEvent> interactionEventHandler;

    public CLI() {
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void startCLI() {
        do {
            String command = readString();
            checkForModusChange(command);
        } while (running);
    }

    public void checkForModusChange(String command) {
        if (!Objects.equals(command, "")) {
            if (command.charAt(0) == ':') {
                changeModus(command);
            }
        }
    }

    public void changeModus(String command) {
        switch (command) {
            case ":c":
                CModus cModus = new CModus(this);
                while (running) {
                    cModus.execute();
                }
                break;
            case ":d":
                DModus dModus = new DModus(this);
                while (running) {
                    dModus.execute();
                }
                break;
            case ":r":
                RModus rModus = new RModus(this);
                while (running) {
                    rModus.execute();
                }
                break;
            case ":u":
                UModus uModus = new UModus(this);
                while (running) {
                    uModus.execute();
                }
                break;
            case ":p":
                PModus pModus = new PModus(this);
                while (running) {
                    pModus.execute();
                }
                break;
            default:
                running = false;
                break;
        }
    }

    public String readString() {
        String in = "";
        try {
            in = scanner.nextLine();
        } catch (Exception e) {}
        return in;
    }

    public boolean isRunning() {
        return running;
    }

    public Handler<CargoEvent> getCargoEventHandler() {
        return cargoEventHandler;
    }

    public Handler<IntegerEvent> getDeleteCargoHandler() {
        return deleteCargoHandler;
    }

    public Handler<OwnerEvent> getDeleteOwnerHandler() {
        return deleteOwnerHandler;
    }

    public Handler<PrintChoiceEvent> getPrintChoiceHandler() {
        return printChoiceHandler;
    }

    public Handler<IntegerEvent> getInspectionEventHandler() {
        return inspectionEventHandler;
    }

    public Handler<OwnerEvent> getCustomerEventHandler() {
        return customerEventHandler;
    }

    public Handler<PersistenceEvent> getSaveEventHandler() {
        return saveEventHandler;
    }

    public Handler<PersistenceEvent> getLoadEventHandler() {
        return loadEventHandler;
    }

    public Handler<InteractionEvent> getInteractionEventHandler() {
        return interactionEventHandler;
    }

    public void setCargoEventHandler(Handler<CargoEvent> cargoEventHandler) {
        this.cargoEventHandler = cargoEventHandler;
    }

    public void setDeleteCargoHandler(Handler<IntegerEvent> deleteCargoHandler) {
        this.deleteCargoHandler = deleteCargoHandler;
    }

    public void setDeleteOwnerHandler(Handler<OwnerEvent> deleteOwnerHandler) {
        this.deleteOwnerHandler = deleteOwnerHandler;
    }

    public void setPrintChoiceHandler(Handler<PrintChoiceEvent> printChoiceHandler) {
        this.printChoiceHandler = printChoiceHandler;
    }

    public void setInspectionEventHandler(Handler<IntegerEvent> inspectionEventHandler) {
        this.inspectionEventHandler = inspectionEventHandler;
    }

    public void setCustomerEventHandler(Handler<OwnerEvent> customerEventHandler) {
        this.customerEventHandler = customerEventHandler;
    }

    public void setSaveEventHandler(Handler<PersistenceEvent> saveEventHandler) {
        this.saveEventHandler = saveEventHandler;
    }

    public void setLoadEventHandler(Handler<PersistenceEvent> loadEventHandler) {
        this.loadEventHandler = loadEventHandler;
    }

    public void setInteractionEventHandler(Handler<InteractionEvent> interactionEventHandler) {
        this.interactionEventHandler = interactionEventHandler;
    }
}
