package inputListener;

import enums.InteractionType;
import eventListener.Listener;
import events.InteractionEvent;
import eventHandler.Handler;
import events.OwnerEvent;
import storageAdministration.StorageFacade;

public class CustomerEventListener implements Listener<OwnerEvent> {
    private StorageFacade facade;
    private Handler<InteractionEvent> interactionEventHandler;

    public CustomerEventListener(StorageFacade facade, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(OwnerEvent event) {
        facade.getStorage().addCustomer(event.getName());
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.addCustomer));
    }
}
