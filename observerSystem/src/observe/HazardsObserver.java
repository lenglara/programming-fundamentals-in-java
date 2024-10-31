package observe;

import cargo.CargoStorable;
import cargo.Hazard;
import observerInterfaces.Observer;
import storageAdministration.CargoDoItAll;
import storageAdministration.Storage;

import java.io.Serializable;
import java.util.HashSet;

public class HazardsObserver implements Observer, Serializable {
    private Storage storage;
    private HashSet<Hazard> hazards;

    public HazardsObserver(Storage storage) {
        this.storage = storage;
        this.hazards = getActualizedHazards();
    }

    @Override
    public void update() {
        if (!hazards.equals(getActualizedHazards())) {
            System.out.println("Change in hazards.");
            this.hazards = getActualizedHazards();
        }
    }

    private HashSet<Hazard> getActualizedHazards() {
        HashSet<Hazard> actualizedHazards = new HashSet<>();
        for (CargoStorable cargo : storage.getCargoList().values()) {
            actualizedHazards.addAll(cargo.getHazards());
        }
        return actualizedHazards;
    }
}
