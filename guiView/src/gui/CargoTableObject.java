package gui;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class CargoTableObject {
    private StringProperty type;
    private IntegerProperty storageLocation;
    private StringProperty customer;
    private StringProperty value;
    private StringProperty hazardous;
    private StringProperty inspectionDate;
    private StringProperty duration;


    public CargoTableObject(String type, int storageLocation, String customer, BigDecimal value, String hazardous, String inspectionDate, String duration) {
        this.type = new SimpleStringProperty(type);
        this.storageLocation = new SimpleIntegerProperty(storageLocation);
        this.customer = new SimpleStringProperty(customer);
        this.value = new SimpleStringProperty(value.toString());
        this.hazardous = new SimpleStringProperty(hazardous);
        this.inspectionDate = new SimpleStringProperty(inspectionDate);
        this.duration = new SimpleStringProperty(duration);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public IntegerProperty storageLocationProperty() {
        return storageLocation;
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public StringProperty hazardousProperty() {
        return hazardous;
    }

    public StringProperty inspectionDateProperty() {
        return inspectionDate;
    }

    public StringProperty durationProperty() {
        return duration;
    }
    
}