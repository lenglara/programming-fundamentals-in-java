package inputListener;

import enums.InteractionType;
import eventListener.Listener;
import events.InteractionEvent;
import eventHandler.Handler;
import events.IntegerEvent;
import storageAdministration.StorageFacade;

public class InspectionEventListener implements Listener<IntegerEvent> {

    private StorageFacade facade;
    private Handler<InteractionEvent> interactionEventHandler;

    public InspectionEventListener(StorageFacade facade, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(IntegerEvent integerEvent) {
        facade.getStorage().inspectCargo(integerEvent.getNum());
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.inspectCargo));
    }
}
