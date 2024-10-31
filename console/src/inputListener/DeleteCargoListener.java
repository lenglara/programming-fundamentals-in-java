package inputListener;

import enums.InteractionType;
import eventListener.Listener;
import events.InteractionEvent;
import eventHandler.Handler;
import events.IntegerEvent;
import storageAdministration.StorageFacade;

public class DeleteCargoListener implements Listener<IntegerEvent> {
    private StorageFacade facade;
    private Handler<InteractionEvent> interactionEventHandler;

    public DeleteCargoListener(StorageFacade facade, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(IntegerEvent integerEvent) {
        facade.getStorage().deleteCargo(integerEvent.getNum());
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.deleteCargo));
    }
}
