package composition;

import java.io.Serializable;

public class UnitisedCargoDescription implements Serializable {
    private boolean fragile;

    public UnitisedCargoDescription(boolean fragile) {
        this.fragile = fragile;
    }

    public boolean isFragile() {
        return fragile;
    }
}
