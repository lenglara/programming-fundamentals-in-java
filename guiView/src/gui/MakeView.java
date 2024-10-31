package gui;

import cargo.CargoStorable;
import dataStoring.SerializationMethods;
import cargo.CargoType;
import enums.InteractionType;
import enums.Persistence;
import inputListener.*;
import logAdministration.LogLogic;
import storageAdministration.CustomerImpl;
import storageAdministration.Storage;
import cargo.Hazard;
import cli.CLI;
import eventHandler.Handler;
import events.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import modi.CModus;
import storageAdministration.StorageFacade;
import observe.StorageChangeObserver;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


public class MakeView implements Initializable {

    private StorageFacade facade;
    private CModus cModus;
    private Handler<OwnerEvent> customerEventHandler;
    private Handler<CargoEvent> cargoEventHandler;
    private Handler<PersistenceEvent> saveEventHandler, loadEventHandler;
    private Handler<InteractionEvent> interactionEventHandler;

    @FXML
    private TableView<CargoTableObject> cargoTable;
    private ObservableList<CargoTableObject> cargoTableObjects;
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    @FXML
    private TableColumn<CargoTableObject, String> typeColumn;
    @FXML
    private TableColumn<CargoTableObject, Number> locationColumn;
    @FXML
    private TableColumn<CargoTableObject, String> customerColumn;
    @FXML
    private TableColumn<CargoTableObject, String> valueColumn;
    @FXML
    private TableColumn<CargoTableObject, String> hazardousColumn;

    @FXML
    private TableColumn<CargoTableObject, String> inspectionColumn;
    @FXML
    private TableColumn<CargoTableObject, String> durationColumn;
    @FXML
    private TableView<CustomerTableObject> customerTable;
    private ObservableList<CustomerTableObject> customerTableObjects;
    @FXML
    private TableColumn<CustomerTableObject, String> nameColumn;
    @FXML
    private TableColumn<CustomerTableObject, Number> numberColumn;

    @FXML
    private TextField textField;
    @FXML
    private RadioButton flammable;
    @FXML
    private RadioButton explosive;
    @FXML
    private RadioButton toxic;
    @FXML
    private RadioButton radioactive;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Storage storage = new Storage(700);
        this.facade = new StorageFacade(storage);

        SerializationMethods serialization = new SerializationMethods("storage.ser");
        CLI cli = new CLI();
        LogLogic logLogic = new LogLogic("log.txt");

        StorageChangeObserver changeObserver = new StorageChangeObserver(storage, logLogic);
        storage.addObserver(changeObserver);

        interactionEventHandler = new Handler<>();
        interactionEventHandler.add(new InteractionEventListener(logLogic));

        this.cargoEventHandler = new Handler<>();
        cargoEventHandler.add(new CargoEventListener(facade, interactionEventHandler));
        cli.setCargoEventHandler(cargoEventHandler);

        this.customerEventHandler = new Handler<>();
        customerEventHandler.add(new CustomerEventListener(facade, interactionEventHandler));
        cli.setCustomerEventHandler(customerEventHandler);

        this.saveEventHandler = new Handler<>();
        saveEventHandler.add(new SaveEventListener(facade, serialization, interactionEventHandler));

        this.loadEventHandler = new Handler<>();
        loadEventHandler.add(new LoadEventListener(facade, serialization, interactionEventHandler));

        this.cModus = new CModus(cli);

        cargoTableObjects = FXCollections.observableArrayList();
        cargoTable.setEditable(true);

        customerTableObjects = FXCollections.observableArrayList();
        customerTable.setEditable(true);

        cargoTable.setRowFactory(tv -> {TableRow<CargoTableObject> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });


            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    if (row.isEmpty()) {
                        event.consume();
                        return;
                    }
                    int dropIndex = row.getIndex();

                    int draggedStorageLocation = cargoTableObjects.get(draggedIndex).storageLocationProperty().get();
                    int dropStorageLocation = cargoTableObjects.get(dropIndex).storageLocationProperty().get();

                    // Austausch der Speicherorte im Storage
                    CargoStorable draggedCargo = facade.getStorage().getCargoList().get(draggedStorageLocation);
                    CargoStorable dropCargo = facade.getStorage().getCargoList().get(dropStorageLocation);

                    facade.getStorage().deleteCargo(draggedStorageLocation);
                    facade.getStorage().deleteCargo(dropStorageLocation);
                    facade.getStorage().placeCargo(draggedStorageLocation, dropCargo);
                    facade.getStorage().placeCargo(dropStorageLocation, draggedCargo);

                    updateItems();

                    event.setDropCompleted(true);
                    event.consume();
                }
            });
            return row ;
        });

        // source: https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows & ChatGPT


        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().storageLocationProperty());
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        hazardousColumn.setCellValueFactory(cellData -> cellData.getValue().hazardousProperty());
        inspectionColumn.setCellValueFactory(cellData -> cellData.getValue().inspectionDateProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        durationColumn.setComparator((durationString1, durationString2) -> {
            Duration duration1 = Duration.parse(durationString1);
            Duration duration2 = Duration.parse(durationString2);
            return duration1.compareTo(duration2);
        });

        cargoTable.setItems(cargoTableObjects);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numCargosProperty());

        customerTable.setItems(customerTableObjects);


        flammable.setDisable(true);
        explosive.setDisable(true);
        toxic.setDisable(true);
        radioactive.setDisable(true);
    }

    @FXML
    public void clearButtonClick(ActionEvent actionEvent) {
        textField.clear();
    }
    public void addButtonClick(ActionEvent actionEvent) {
        String input = textField.getText();
        String[] inputElements = input.split(" ");
        if (inputElements.length == 1 && !inputElements[0].equals("")) {
            OwnerEvent ownerEvent = new OwnerEvent(this, inputElements[0]);
            customerEventHandler.handle(ownerEvent);
        } else if (inputElements.length > 4) {
            CargoEvent cargoEvent = cModus.stringToCargoEvent(inputElements);
            CargoTask cargoTask = new CargoTask(cargoEvent.getType(), cargoEvent.getName(), cargoEvent.getValue(), cargoEvent.getHazards(), cargoEvent.isFragile(), cargoEvent.isPressurized(), cargoEvent.getGrainSize(), facade);
            cargoTask.setOnSucceeded(e -> {
                if(cargoTask.getValue()){
                    interactionEventHandler.handle(new InteractionEvent(this, InteractionType.addCargo));        }
            });
            cargoTask.setOnFailed(e -> {
                System.err.println(cargoTask.getException().getMessage());
            });
            Thread thread = new Thread(cargoTask);
            thread.start();
        }
        updateItems();
    }

    public void deleteButtonClick(ActionEvent actionEvent) {
        String input = textField.getText();
        if (checkIfInteger(input)) {
            facade.getStorage().deleteCargo(Integer.parseInt(input));
        }
        else {
            facade.getStorage().deleteCustomer(input);
        }
        updateItems();
    }

    public void inspectButtonClick(ActionEvent actionEvent) {
        String input = textField.getText();
        if (checkIfInteger(input)) {
            facade.getStorage().inspectCargo(Integer.parseInt(input));
            updateItems();
        }
        else {
            textField.setText("Input is not a positive Integer.");
        }
    }

    public void saveButtonClick(ActionEvent actionEvent) {
        saveEventHandler.handle(new PersistenceEvent(this, Persistence.saveJOS));
        updateItems();
    }

    public void loadButtonClick(ActionEvent actionEvent) {
        loadEventHandler.handle(new PersistenceEvent(this, Persistence.loadJOS));
        updateItems();
    }

    public void dryBulkCargoButtonClick(ActionEvent actionEvent) {
        updateItems(CargoType.DryBulkCargo);
    }

    public void liquidBulkCargoButtonClick(ActionEvent actionEvent) {
        updateItems(CargoType.LiquidBulkCargo);
    }
    public void unitisedCargoButtonClick(ActionEvent actionEvent) {
        updateItems(CargoType.UnitisedCargo);
    }
    public void liquidBulkAndUnitisedCargoButtonClick(ActionEvent actionEvent) {
        updateItems(CargoType.LiquidBulkAndUnitisedCargo);
    }

    public void liquidAndDryBulkCargoButtonClick(ActionEvent actionEvent) {
        updateItems(CargoType.LiquidAndDryBulkCargo);
    }

    public void dryBulkAndUnitisedCargoButtonClick(ActionEvent actionEvent) {
        updateItems(CargoType.DryBulkAndUnitisedCargo);
    }

    public void updateItems() {
        cargoTableObjects.clear();
        for (int i = 0, k = 0; i < facade.getStorage().getCargoList().size(); k++) {
            if (facade.getStorage().getCargoList().get(k) != null) {
                CargoTableObject cargoTableObject = createCargoTableObject(facade.getStorage().getCargoList().get(k));
                cargoTableObjects.add(cargoTableObject);
                i++;
            }
        }
        customerTableObjects.clear();
        facade.getStorage().getCustomerList().forEach(c -> {customerTableObjects.add(createCustomerTableObject(c));});
        updateHazards();
    }

    public void updateItems(CargoType type) {
        cargoTableObjects.clear();
        for (CargoStorable cargo : facade.getStorage().getCargoList(type).values()) {
                CargoTableObject cargoTableObject = createCargoTableObject(cargo);
                cargoTableObjects.add(cargoTableObject);
        }
    }

    public void updateHazards() {
        List<RadioButton> radioButtons = Arrays.asList(flammable, explosive, toxic, radioactive);
        radioButtons.forEach(radioButton -> radioButton.setSelected(false));
        Set<Hazard> hazards = facade.getStorage().getCargoList().values()
                .stream()
                .flatMap(cargo -> cargo.getHazards().stream())
                .collect(Collectors.toSet());
        if (hazards.contains(Hazard.flammable)) {
            flammable.setSelected(true);
        }
        if (hazards.contains(Hazard.explosive)) {
            explosive.setSelected(true);
        }
        if (hazards.contains(Hazard.toxic)) {
            toxic.setSelected(true);
        }
        if (hazards.contains(Hazard.radioactive)) {
            radioactive.setSelected(true);
        }
    }

    public CargoTableObject createCargoTableObject(CargoStorable cargo) {
        String type = cargo.getType().toString();
        int location = cargo.getStorageLocation();
        String customer = cargo.getOwner().getName();
        BigDecimal value = cargo.getValue();
        StringBuilder haz = new StringBuilder();
        int i = 0;
        for (Hazard hazard : cargo.getHazards()) {
            if (i < cargo.getHazards().size() - 1) {
                haz.append(hazard.toString()).append(",");
                i++;
            }
            else {
                haz.append(hazard.toString());
            }
        }
        String hazardous = haz.toString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(cargo.getLastInspectionDate());
        return new CargoTableObject(type, location, customer, value, hazardous, date, cargo.getDurationOfStorage().toString());
    }

    public CustomerTableObject createCustomerTableObject(CustomerImpl customer) {
        return new CustomerTableObject(customer.getName(), customer.getNumCargos());
    }

    public boolean checkIfInteger (String input) {
        if (input.matches("\\d+")) {
            return true;
        }
        return false;
    }
}
