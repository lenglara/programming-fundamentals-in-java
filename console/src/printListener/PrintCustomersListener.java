package printListener;

import eventListener.Listener;
import events.PrintCustomersEvent;

public class PrintCustomersListener implements Listener<PrintCustomersEvent> {

    @Override
    public void onEvent(PrintCustomersEvent event) {
        event.getCustomerSet().forEach(c -> System.out.printf("%-2s %-10s %-25s %-20s %-10s\n", " ", "Name: ", c.getName(), "Number of cargo items: ", c.getNumCargos()));
    }
}
