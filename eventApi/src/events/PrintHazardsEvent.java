package events;

import cargo.Hazard;

import java.io.Serializable;
import java.util.Collection;
import java.util.EventObject;

public class PrintHazardsEvent extends EventObject {
    private Collection<Hazard> hazards;

    public PrintHazardsEvent(Object source, Collection<Hazard> hazards) {
        super(source);
        this.hazards = hazards;
    }

    public Collection<Hazard> getHazards() {
        return hazards;
    }
}
