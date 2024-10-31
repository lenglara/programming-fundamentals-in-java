package events;

import java.io.Serializable;
import java.util.EventObject;

public class OwnerEvent extends EventObject {
    private String name;

    public OwnerEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
