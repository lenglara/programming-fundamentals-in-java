package events;

import enums.Persistence;

import java.util.EventObject;

public class PersistenceEvent extends EventObject {

    private Persistence choice;

    public PersistenceEvent(Object source, Persistence choice) {
        super(source);
        this.choice = choice;
    }

    public Persistence getChoice() {
        return choice;
    }
}
