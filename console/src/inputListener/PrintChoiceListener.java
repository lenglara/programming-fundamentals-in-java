package inputListener;

import cargo.CargoStorable;
import enums.InteractionType;
import eventListener.Listener;
import events.*;
import cargo.Hazard;
import eventHandler.Handler;
import storageAdministration.StorageFacade;

import java.util.HashSet;
import java.util.Set;

public class PrintChoiceListener implements Listener<PrintChoiceEvent> {
    private StorageFacade facade;
    private Handler<PrintCustomersEvent> printCustomersHandler;
    private Handler<PrintCargosEvent> printCargosHandler;
    private Handler<PrintHazardsEvent> printHazardsHandler;
    private Handler<InteractionEvent> interactionEventHandler;

    public PrintChoiceListener(StorageFacade facade, Handler<PrintCustomersEvent> printCustomersHandler, Handler<PrintCargosEvent> printCargosHandler, Handler<PrintHazardsEvent> printHazardsHandler, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.printCustomersHandler = printCustomersHandler;
        this.printCargosHandler = printCargosHandler;
        this.printHazardsHandler = printHazardsHandler;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(PrintChoiceEvent event) {
        InteractionEvent interactionEvent = null;
        if (event.getChoice() != null) {
            switch (event.getChoice()) {
                case customers:
                    PrintCustomersEvent oEvent = new PrintCustomersEvent(this, facade.getStorage().getCustomerList());
                    printCustomersHandler.handle(oEvent);
                    interactionEvent = new InteractionEvent(this, InteractionType.printCustomers);
                    break;
                case cargos:
                    if (event.getType() == null) {
                        PrintCargosEvent cEvent = new PrintCargosEvent(this, facade.getStorage().getCargoList());
                        printCargosHandler.handle(cEvent);
                    } else {
                        PrintCargosEvent cEvent = new PrintCargosEvent(this, facade.getStorage().getCargoList(event.getType()));
                        printCargosHandler.handle(cEvent);
                    }
                    interactionEvent = new InteractionEvent(this, InteractionType.printCargos);
                    break;
                case hazards:
                    Set<Hazard> hazardSet = new HashSet<>();
                    for (CargoStorable cargo : facade.getStorage().getCargoList().values()) {
                        hazardSet.addAll(cargo.getHazards());
                    }
                    PrintHazardsEvent hEvent = new PrintHazardsEvent(this, hazardSet);
                    printHazardsHandler.handle(hEvent);
                    interactionEvent = new InteractionEvent(this, InteractionType.printHazards);
                    break;
                default:
                    // Number is not within range
                    break;
            }
        }
        if (interactionEvent != null) {
            interactionEventHandler.handle(interactionEvent);
        }
    }
}
