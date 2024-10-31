package events;

import java.util.EventObject;

public class IntegerEvent extends EventObject {

    private int num;

    public IntegerEvent(Object source, int num) {
        super(source);
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
