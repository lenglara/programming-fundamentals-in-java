package inputListener;

import dataStoring.SerializationMethods;
import enums.InteractionType;
import eventHandler.Handler;
import eventListener.Listener;
import events.InteractionEvent;
import events.PersistenceEvent;
import storageAdministration.StorageFacade;

public class SaveEventListener implements Listener<PersistenceEvent> {
    private SerializationMethods serialization;
    private Handler<InteractionEvent> interactionEventHandler;
    private StorageFacade facade;

    public SaveEventListener(StorageFacade facade, SerializationMethods serialization, Handler<InteractionEvent> interactionEventHandler) {
        this.serialization = serialization;
        this.interactionEventHandler = interactionEventHandler;
        this.facade = facade;
    }

    @Override
    public void onEvent(PersistenceEvent event) {
        // JOS
        serialization.serialize(facade.getStorage());
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.save));
    }
}
