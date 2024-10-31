package events;

import cargo.CargoStorable;
import java.util.EventObject;
import java.util.HashMap;

public class PrintCargosEvent extends EventObject {
    private HashMap<Integer, CargoStorable> cargoHashMap;

    public PrintCargosEvent(Object source, HashMap<Integer, CargoStorable> cargoHashMap) {
        super(source);
        this.cargoHashMap = cargoHashMap;
    }

    public HashMap<Integer, CargoStorable> getCargoHashMap() {
        return cargoHashMap;
    }
}
