package inputListener;


import dataStoring.SerializationMethods;
import enums.InteractionType;
import eventHandler.Handler;
import eventListener.Listener;
import events.InteractionEvent;
import events.PersistenceEvent;
import storageAdministration.Storage;
import storageAdministration.StorageFacade;

public class LoadEventListener implements Listener<PersistenceEvent> {
    private SerializationMethods serialization;
    private Handler<InteractionEvent> interactionEventHandler;
    private StorageFacade facade;

    public LoadEventListener(StorageFacade facade, SerializationMethods serialization, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.serialization = serialization;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(PersistenceEvent event) {
        Storage loaded = serialization.deserialize();
        facade.setStorage(loaded);
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.load));
    }
}
