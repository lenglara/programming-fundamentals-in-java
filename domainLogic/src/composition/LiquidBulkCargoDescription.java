package composition;

import java.io.Serializable;

public class LiquidBulkCargoDescription implements Serializable {
    private boolean pressurized;

    public LiquidBulkCargoDescription(boolean pressurized) {
        this.pressurized = pressurized;
    }

    public boolean isPressurized() {
        return pressurized;
    }
}
