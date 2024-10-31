package modi;

import cargo.CargoType;
import cargo.Hazard;
import cli.CLI;
import eventHandler.Handler;
import events.CargoEvent;
import events.OwnerEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static cargo.CargoType.*;

public class CModus {

    private CLI cli;
    private Handler<CargoEvent> cargoEventHandler;
    private Handler<OwnerEvent> customerEventHandler;

    public CModus(CLI cli) {
        this.cli = cli;
        this.cargoEventHandler = cli.getCargoEventHandler();
        this.customerEventHandler = cli.getCustomerEventHandler();
    }

    public void execute() {
        // Example input: LiquidBulkCargo Alice 750 toxic false
        String input = cli.readString();
        cli.checkForModusChange(input);
        if (cli.isRunning()) {
            String[] inputElements = input.split(" ");
            if (inputElements.length == 1 && !inputElements[0].equals("")) {
                OwnerEvent ownerEvent = new OwnerEvent(this, inputElements[0]);
                customerEventHandler.handle(ownerEvent);
            } else if (inputElements.length > 4) {
                CargoEvent cargoEvent = stringToCargoEvent(inputElements);
                cargoEventHandler.handle(cargoEvent);
            }
        }
    }

    public CargoEvent stringToCargoEvent(String[] inputElements) {

        CargoType type = DryBulkCargo;
        try {
            type = valueOf(inputElements[0]);
        } catch (Exception e) {
        }

        String owner = inputElements[1];

        BigDecimal value = new BigDecimal(0);
        String valueStr = inputElements[2].replace(",",".");
        if (valueStr.matches("\\d+(\\.\\d+)?")) {
            value = new BigDecimal(valueStr);
        }

        Collection<Hazard> hazards = new ArrayList<>();
        String[] stringHazards = inputElements[3].split(",");
        try {
            for (String stringHazard : stringHazards) {
                hazards.add(Hazard.valueOf(stringHazard));
            }
        } catch (Exception e) {
        }

        boolean fragile = false;
        boolean pressurized = false;
        int grainSize = 0;

        switch (type) {
            case UnitisedCargo:
                if (inputElements[4].equals("true")) {
                    fragile = true;
                }
                break;
            case LiquidBulkCargo:
                if (inputElements[4].equals("true")) {
                    pressurized = true;
                }
                break;
            case DryBulkCargo:
                try {
                    grainSize = Integer.parseInt(inputElements[4]);
                } catch (Exception e) {
                    // Not an integer
                }
                break;
            case LiquidAndDryBulkCargo:
                if (inputElements[4].equals("true")) {
                    pressurized = true;
                }
                try {
                    grainSize = Integer.parseInt(inputElements[5]);
                } catch (Exception e) {
                    // Not an Integer
                }
                break;
            case LiquidBulkAndUnitisedCargo:
                if (inputElements[4].equals("true")) {
                    pressurized = true;
                }
                if (inputElements.length > 5 && inputElements[5].equals("true")) {
                    fragile = true;
                }
                break;
            case DryBulkAndUnitisedCargo:
                try {
                    grainSize = Integer.parseInt(inputElements[4]);
                } catch (Exception e) {
                    // Not an integer
                }
                if (inputElements.length > 5 && inputElements[5].equals("true")) {
                    fragile = true;
                }

                break;
        }
        return new CargoEvent(this, type, owner, value, hazards, fragile, pressurized, grainSize);
    }

    public Handler<CargoEvent> getCargoEventHandler() {
        return cargoEventHandler;
    }

    public Handler<OwnerEvent> getCustomerEventHandler() {
        return customerEventHandler;
    }
}
