package events;

import cargo.CargoType;
import enums.PrintChoice;

import java.util.EventObject;

public class PrintChoiceEvent extends EventObject {
    private PrintChoice choice;
    private CargoType type;

    public PrintChoiceEvent(Object source, PrintChoice choice, CargoType type) {
        super(source);
        this.choice = choice;
        this.type = type;
    }

    public PrintChoice getChoice() {
        return choice;
    }

    public CargoType getType() {
        return type;
    }
}
