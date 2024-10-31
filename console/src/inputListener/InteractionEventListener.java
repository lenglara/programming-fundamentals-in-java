package inputListener;

import eventListener.Listener;
import events.InteractionEvent;
import logAdministration.LogLogic;

public class InteractionEventListener implements Listener<InteractionEvent> {

    private LogLogic logLogic;

    public InteractionEventListener(LogLogic logLogic) {
        this.logLogic = logLogic;
    }

    @Override
    public void onEvent(InteractionEvent event) {
        switch (event.getInteraction()) {
            case addCustomer:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[0]);
                break;
            case addCargo:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[1]);
                break;
            case deleteCustomer:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[2]);
                break;
            case deleteCargo:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[3]);
                break;
            case printCargos:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[4]);
                break;
            case printCustomers:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[5]);
                break;
            case printHazards:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[6]);
                break;
            case inspectCargo:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[7]);
                break;
            case save:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[8]);
                break;
            case load:
                logLogic.writeToLogFile(logLogic.getInteractionDescriptions()[9]);
                break;
        }
    }
}
