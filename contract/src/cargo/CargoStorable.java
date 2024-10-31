package cargo;

import administration.Storable;

public interface CargoStorable extends Cargo, Storable {
    CargoType getType();
}
