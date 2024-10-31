package inputListener;

import enums.InteractionType;
import eventHandler.Handler;
import eventListener.Listener;
import events.InteractionEvent;
import events.OwnerEvent;
import storageAdministration.StorageFacade;

public class DeleteOwnerListener implements Listener<OwnerEvent> {
    private StorageFacade facade;
    private Handler<InteractionEvent> interactionEventHandler;

    public DeleteOwnerListener(StorageFacade facade, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(OwnerEvent event) {
        facade.getStorage().deleteCustomer(event.getName());
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.deleteCustomer));
    }
}
