package modi;

import cli.CLI;
import cargo.CargoType;
import enums.PrintChoice;
import eventHandler.Handler;
import events.PrintChoiceEvent;

public class RModus {

    private CLI cli;
    private Handler<PrintChoiceEvent> printEventHandler;

    public RModus(CLI cli) {
        this.cli = cli;
        this.printEventHandler = cli.getPrintChoiceHandler();
    }

    public void execute() {
        String input = cli.readString();
        cli.checkForModusChange(input);
        String[] inputElements = input.split(" ");
        PrintChoice choice = null;
        CargoType type = null;
        if (cli.isRunning()) {
            try {
                choice = PrintChoice.valueOf(inputElements[0]);
                type = CargoType.valueOf(inputElements[1]);
            } catch (Exception e) {
                // Not matching input or no second element
            }
            if (choice != null) {
                PrintChoiceEvent event = new PrintChoiceEvent(this, choice, type);
                eventToHandler(event);
            }
        }
    }

    public void eventToHandler(PrintChoiceEvent event) {
        printEventHandler.handle(event);
    }
}
