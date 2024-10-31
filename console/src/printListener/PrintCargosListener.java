package printListener;

import eventListener.Listener;
import events.PrintCargosEvent;

public class PrintCargosListener implements Listener<PrintCargosEvent> {

    @Override
    public void onEvent(PrintCargosEvent event) {
        event.getCargoHashMap().forEach((key, value) -> System.out.printf("%-5s %-25s %-15s %-25s %-35s %-13s\n", key + ":", value.getType(), value.getValue(), value.getOwner().getName(), value.getLastInspectionDate(), value.getDurationOfStorage()));
    }
}
