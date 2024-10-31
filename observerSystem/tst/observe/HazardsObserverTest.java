package observe;

import cargo.Hazard;
import cargo.CargoType;
import storageAdministration.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class HazardsObserverTest {

    @org.junit.jupiter.api.Test
    void update_checkOutput_notNull() {
        String output;
        PrintStream originalOutput = System.out;
        Storage storage = new Storage(10);
        HazardsObserver testObserver = new HazardsObserver(storage);
        storage.addCustomer("Till");
        storage.addCargo(CargoType.DryBulkCargo, "Till", new BigDecimal(0), EnumSet.of(Hazard.explosive), false, false, 0);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);
            testObserver.update();
            output = outputStream.toString();
        } finally {
            System.setOut(originalOutput);
        }
        assertNotNull(output);
    }

    @org.junit.jupiter.api.Test
    void update_verify() {
        Storage storage = new Storage(10);
        storage.addCustomer("Till");
        HazardsObserver spyObserver = spy(new HazardsObserver(storage));
        storage.addObserver(spyObserver);
        storage.addCargo(CargoType.DryBulkCargo, "Till", new BigDecimal(0), EnumSet.of(Hazard.explosive), false, false, 0);
        verify(spyObserver).update();
    }
}