package inputListener;

import enums.InteractionType;
import eventHandler.Handler;
import eventListener.Listener;
import events.CargoEvent;
import events.InteractionEvent;
import storageAdministration.StorageFacade;

public class CargoEventListener implements Listener<CargoEvent> {
    private StorageFacade facade;
    private Handler<InteractionEvent> interactionEventHandler;

    public CargoEventListener(StorageFacade facade, Handler<InteractionEvent> interactionEventHandler) {
        this.facade = facade;
        this.interactionEventHandler = interactionEventHandler;
    }

    @Override
    public void onEvent(CargoEvent event) {
        facade.getStorage().addCargo(event.getType(), event.getName(), event.getValue(), event.getHazards(), event.isFragile(), event.isPressurized(), event.getGrainSize());
        interactionEventHandler.handle(new InteractionEvent(this, InteractionType.addCargo));
    }
}
