package composition;

import java.io.Serializable;

public class DryBulkCargoDescription implements Serializable {
    private int grainSize;

    public DryBulkCargoDescription(int grainSize) {
        this.grainSize = grainSize;
    }

    public int getGrainSize() {
        return grainSize;
    }
}
