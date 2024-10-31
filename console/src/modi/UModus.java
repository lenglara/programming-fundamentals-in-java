package modi;

import cli.CLI;
import eventHandler.Handler;
import events.IntegerEvent;

public class UModus {

    private CLI cli;
    private Handler<IntegerEvent> inspectionEventHandler;

    public UModus(CLI cli) {
        this.cli = cli;
        this.inspectionEventHandler = cli.getInspectionEventHandler();
    }

    public void execute() {
        String input = cli.readString();
        cli.checkForModusChange(input);
        if (cli.isRunning() && input.matches("-?\\d+")) {
            int num = Integer.parseInt(input);
            IntegerEvent intEvent = new IntegerEvent(this, num);
            inspectionEventHandler.handle(intEvent);
        }
    }
}
