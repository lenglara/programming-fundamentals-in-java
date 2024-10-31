package printListener;

import eventListener.Listener;
import events.PrintHazardsEvent;

public class PrintHazardsListener implements Listener<PrintHazardsEvent> {
    @Override
    public void onEvent(PrintHazardsEvent event) {
        event.getHazards().forEach(h -> System.out.println(h.toString()));
    }
}
