package storageAdministration;

import administration.Storable;
import cargo.Cargo;
import cargo.CargoStorable;
import cargo.CargoType;

import java.io.Serializable;
import java.util.Date;

interface CargoDoItAll extends Serializable, Cargo, Storable, CargoStorable {
    void setLastInspectionDate(Date date);
    void setStorageLocation(int location);
}
