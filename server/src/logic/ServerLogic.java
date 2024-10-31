package logic;

import enums.Persistence;
import eventHandler.Handler;
import events.*;


import java.util.EventObject;

public class ServerLogic {
    private Handler<CargoEvent> cargoEventHandler;
    private Handler<IntegerEvent> deleteCargoEventHandler, inspectionEventHandler;
    private Handler<PrintChoiceEvent> printEventHandler;
    private Handler<OwnerEvent> ownerEventHandler, deleteOwnerEventHandler;
    private Handler<PersistenceEvent> saveEventHandler, loadEventHandler;


    public void selectCommand (char command, EventObject event) {
        switch (command) {
            case 'c':   // add cargo
                CargoEvent cEvent = (CargoEvent) event;
                cargoEventHandler.handle(cEvent);
                break;
            case 'o':   // add owner
                OwnerEvent oEvent = (OwnerEvent) event;
                ownerEventHandler.handle(oEvent);
                break;
            case 'r':   // read
                PrintChoiceEvent rEvent = (PrintChoiceEvent) event;
                printEventHandler.handle(rEvent);
                break;
            case 'u':   // inspect
                IntegerEvent uEvent = (IntegerEvent) event;
                inspectionEventHandler.handle(uEvent);
                break;
            case 'd':   // delete
                if (event.getClass().equals(IntegerEvent.class)) {
                    IntegerEvent diEvent = (IntegerEvent) event;
                    deleteCargoEventHandler.handle(diEvent);
                } else {
                    OwnerEvent doEvent = (OwnerEvent) event;
                    deleteOwnerEventHandler.handle(doEvent);
                }
                break;
            case 'p':   // save & load
                PersistenceEvent pEvent = (PersistenceEvent) event;
                if (pEvent.getChoice().equals(Persistence.saveJBP) || pEvent.getChoice().equals(Persistence.saveJOS)) {
                    saveEventHandler.handle(pEvent);
                } else {
                    loadEventHandler.handle(pEvent);
                }
                break;
        }
    }

    public void setCargoEventHandler(Handler<CargoEvent> cargoEventHandler) {
        this.cargoEventHandler = cargoEventHandler;
    }

    public void setDeleteCargoEventHandler(Handler<IntegerEvent> deleteEventHandler) {
        this.deleteCargoEventHandler = deleteEventHandler;
    }

    public void setDeleteOwnerEventHandler(Handler<OwnerEvent> deleteOwnerEventHandler) {
        this.deleteOwnerEventHandler = deleteOwnerEventHandler;
    }

    public void setPrintEventHandler(Handler<PrintChoiceEvent> printEventHandler) {
        this.printEventHandler = printEventHandler;
    }

    public Handler<PrintChoiceEvent> getPrintEventHandler() {
        return printEventHandler;
    }

    public void setInspectionEventHandler(Handler<IntegerEvent> inspectionEventHandler) {
        this.inspectionEventHandler = inspectionEventHandler;
    }

    public void setOwnerEventHandler(Handler<OwnerEvent> ownerEventHandler) {
        this.ownerEventHandler = ownerEventHandler;
    }

    public void setSaveEventHandler(Handler<PersistenceEvent> saveEventHandler) {
        this.saveEventHandler = saveEventHandler;
    }

    public void setLoadEventHandler(Handler<PersistenceEvent> loadEventHandler) {
        this.loadEventHandler = loadEventHandler;
    }
}
