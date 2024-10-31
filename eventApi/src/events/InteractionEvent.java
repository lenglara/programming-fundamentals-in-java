package events;

import enums.InteractionType;

import java.util.EventObject;

public class InteractionEvent extends EventObject {
    private InteractionType interaction;

    public InteractionEvent(Object source, InteractionType interaction) {
        super(source);
        this.interaction = interaction;
    }

    public InteractionType getInteraction() {
        return interaction;
    }
}
