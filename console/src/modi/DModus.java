package modi;

import cli.CLI;
import eventHandler.Handler;
import events.IntegerEvent;
import events.OwnerEvent;

public class DModus {

    private CLI cli;
    private Handler<IntegerEvent> deleteCargoHandler;
    private Handler<OwnerEvent> deleteOwnerHandler;

    public DModus(CLI cli) {
        this.cli = cli;
        this.deleteCargoHandler = cli.getDeleteCargoHandler();
        this.deleteOwnerHandler = cli.getDeleteOwnerHandler();
    }

    public void execute() {
        String input = cli.readString();
        cli.checkForModusChange(input);
        if (cli.isRunning()) {
            if (input.matches("-?\\d+")) {
                int num = Integer.parseInt(input);
                IntegerEvent intEvent = new IntegerEvent(this, num);
                eventToHandler(intEvent);
            } else {
                OwnerEvent oEvent = new OwnerEvent(this, input);
                eventToHandler(oEvent);
            }
        }
    }

    public void eventToHandler(IntegerEvent intEvent) {
        deleteCargoHandler.handle(intEvent);
    }

    public void eventToHandler(OwnerEvent oEvent) {
        deleteOwnerHandler.handle(oEvent);
    }
}
