package modi;

import cli.CLI;
import eventHandler.Handler;
import events.PersistenceEvent;

import static enums.Persistence.*;

public class PModus {
    private CLI cli;
    private Handler<PersistenceEvent> saveEventHandler, loadEventHandler;

    public PModus(CLI cli) {
        this.cli = cli;
        this.saveEventHandler = cli.getSaveEventHandler();
        this.loadEventHandler = cli.getLoadEventHandler();
    }

    public void execute() {
        String input = cli.readString();
        cli.checkForModusChange(input);
        if (cli.isRunning()) {
            switch (input) {
                case "saveJOS":
                    saveEventHandler.handle(new PersistenceEvent(this, saveJOS));
                    break;
                case "saveJBP":
                    saveEventHandler.handle(new PersistenceEvent(this, saveJBP));
                    break;
                case "loadJOS":
                    loadEventHandler.handle(new PersistenceEvent(this, loadJOS));
                    break;
                case "loadJBP":
                    loadEventHandler.handle(new PersistenceEvent(this, loadJBP));
                    break;
                default:
                    // Input doesn't match the enums
                    break;
            }
        }
    }
}
