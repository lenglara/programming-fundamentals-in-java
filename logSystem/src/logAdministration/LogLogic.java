package logAdministration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogLogic {
    // descriptions for the following interactions: addCustomer, addCargo, deleteCustomer, deleteCargo, printCargos, printCustomers, printHazards, inspectCargo, save, load
    private String[] interactionDescriptions;

    // description for changes in the storage:
    private String changeDescription;
    private String fileName;

    public LogLogic(String fileName) {
        this.fileName = fileName;
        setGerman();
    }

    public synchronized void writeToLogFile(String text) {
        try (BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter(fileName, true))){
            bufferedWriter.write(text);
            bufferedWriter.newLine();
        } catch (IOException e) {
        }
    }

    public void setGerman() {
        this.interactionDescriptions = new String[]{"Es wird versucht, einen Kunden hinzuzufügen.", "Es wird versucht, ein Cargo Objekt hinzuzufügen.", "Es wird versucht, einen Kunden zu löschen.", "Es wird versucht, ein Cargo Objekt zu löschen.", "Es wird versucht, die Cargoliste aufzurufen.", "Es wird versucht, die Kundenliste aufzurufen.", "Es wird versucht, die Hazards aufzurufen.", "Es wird versucht, ein Cargoobjekt zu inspizieren.", "Es wird versucht, den Zustand der Geschäftslogik zu speichern.", "Es wird versucht, einen alten Zustand der Geschäftslogik zu laden."};
        this.changeDescription = "Änderung in der Geschäftslogik.";
    }

    public void setEnglish() {
        this.interactionDescriptions = new String[]{"An attempt is being made to add a customer.", "An attempt is being made to add a cargo object.", "An attempt is being made to delete a customer.", "An attempt is being made to delete a cargo object.", "An attempt is being made to call up the cargo list.", "An attempt is being made to call up the customer list.", "An attempt is being made to call up the hazards.", "An attempt is being made to inspect an cargo object.", "An attempt is being made to save the storage.", "An attempt is being made to load the storage."};
        this.changeDescription = "Change in business logic.";
    }

    // returns String array in the following order: addCustomer, addCargo, deleteCustomer, deleteCargo, printCargos, printCustomers, printHazards, inspectCargo, save, load
    public String[] getInteractionDescriptions() {
        return interactionDescriptions;
    }

    public String getChangeDescription() {
        return changeDescription;
    }
}
