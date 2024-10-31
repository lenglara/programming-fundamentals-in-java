package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerTableObject {

    private StringProperty name;
    private IntegerProperty numCargos;

    public CustomerTableObject(String name, Integer numCargos) {
        this.name = new SimpleStringProperty(name);
        this.numCargos = new SimpleIntegerProperty(numCargos);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty numCargosProperty() {
        return numCargos;
    }
}
