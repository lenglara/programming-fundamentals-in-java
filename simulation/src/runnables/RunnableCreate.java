package runnables;

import cargo.Hazard;
import events.CargoEvent;
import modi.CModus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.SplittableRandom;

import static cargo.CargoType.DryBulkCargo;
import static cargo.Hazard.*;


public class RunnableCreate implements Runnable {

    private CModus cModus;
    private Random rn = new Random();
    private final Object monitor;

    public RunnableCreate(CModus cModus, Object monitor) {
        this.cModus = cModus;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            String[] ownerNames = {"Katja", "Baerchen", "Pusteblume", "Tatu", "HTW", "Peach", "Hallodri", "BitterGlitter", "Tattergreis", "Wunderbaum", "Susan", "Nigel", "Lorelei", "Kaesefuss", "Insektenfreund"};
            String name = ownerNames[rn.nextInt(ownerNames.length)];
            BigDecimal value = BigDecimal.valueOf(new SplittableRandom().nextDouble(50000));
            Hazard[] allHazards = {explosive, flammable, toxic, radioactive};
            Collection<Hazard> hazards = new ArrayList<>();
            for (Hazard hazard : allHazards) {
                if (rn.nextBoolean()) {
                    hazards.add(hazard);
                }
            }
            boolean fragile = rn.nextBoolean();
            boolean pressurized = rn.nextBoolean();
            int grainSize = rn.nextInt(50);
            CargoEvent event = new CargoEvent(this, DryBulkCargo, name, value, hazards, fragile, pressurized, grainSize);
            synchronized (monitor) {
                cModus.getCargoEventHandler().handle(event);
            }
        }
    }
}
