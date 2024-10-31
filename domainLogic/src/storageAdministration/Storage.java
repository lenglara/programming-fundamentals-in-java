package storageAdministration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import administration.Customer;
import cargo.CargoStorable;
import cargo.Hazard;
import cargo.CargoType;
import observerInterfaces.Observable;
import observerInterfaces.Observer;


public class Storage implements Observable, Serializable {

    private HashMap<Integer, CargoDoItAll> cargoHashMap; //Wrapper nutzen
    private Set<CustomerImpl> customerList;
    private int maxSize;
    private List<observerInterfaces.Observer> observerList;
    private final CustomerImpl monitor;


    public Storage(int maxsize) {
        this.cargoHashMap = new HashMap<Integer, CargoDoItAll>();
        this.maxSize = maxsize;
        this.customerList = new HashSet<CustomerImpl>();
        this.observerList = new ArrayList<observerInterfaces.Observer>();
        this.monitor = new CustomerImpl("Monitor");
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean addCustomer(String name) {
        synchronized (monitor) {
            for (Customer c : customerList) {
                if (c.getName().equals(name)) {
                    return false;
                }
            }
            CustomerImpl newCustomer = new CustomerImpl(name);
            customerList.add(newCustomer);
            notifyObservers();
            return true;
        }
    }

    public boolean addCargo(CargoType type, String customerName, BigDecimal value, Collection<Hazard> hazards, boolean fragile, boolean pressurized, int grainSize) {
        synchronized (monitor) {
            Optional<CustomerImpl> optionalCustomer = customerList.stream()
                    .filter(customer -> customer.getName().equals(customerName))
                    .findFirst();
            boolean addSuccess = false;
            if (optionalCustomer.isPresent()) {
                CustomerImpl customer = optionalCustomer.get();
                switch (type) {
                    case DryBulkCargo:
                        DryBulkCargoImpl dCargo = new DryBulkCargoImpl(customer, value, hazards, grainSize);
                        addSuccess = addCargo(dCargo);
                        break;
                    case LiquidBulkCargo:
                        LiquidBulkCargoImpl lCargo = new LiquidBulkCargoImpl(customer, value, hazards, pressurized);
                        addSuccess = addCargo(lCargo);
                        break;
                    case UnitisedCargo:
                        UnitisedCargoImpl uCargo = new UnitisedCargoImpl(customer, value, hazards, fragile);
                        addSuccess = addCargo(uCargo);
                        break;
                    case LiquidAndDryBulkCargo:
                        LiquidAndDryBulkCargoImpl ldCargo = new LiquidAndDryBulkCargoImpl(customer, value, hazards, grainSize, pressurized);
                        addSuccess = addCargo(ldCargo);
                        break;
                    case DryBulkAndUnitisedCargo:
                        DryBulkAndUnitisedCargoImpl duCargo = new DryBulkAndUnitisedCargoImpl(customer, value, hazards, grainSize, fragile);
                        addSuccess = addCargo(duCargo);
                        break;
                    case LiquidBulkAndUnitisedCargo:
                        LiquidBulkAndUnitisedCargoImpl luCargo = new LiquidBulkAndUnitisedCargoImpl(customer, value, hazards, pressurized, fragile);
                        addSuccess = addCargo(luCargo);
                        break;
                }
                return addSuccess;
            }
            return false;
        }
    }

    boolean addCargo(CargoDoItAll cargo) {
        synchronized (monitor) {
            Optional<CustomerImpl> optionalCustomer = customerList.stream()
                    .filter(customer -> customer.getName().equals(cargo.getOwner().getName()))
                    .findFirst();
            if (optionalCustomer.isPresent() && maxSize - cargoHashMap.size() > 0) {
                for (int i = 0; i <= cargoHashMap.size(); i++) {
                    if (cargoHashMap.get(i) == null) {
                        cargoHashMap.put(i, cargo);
                        cargo.setStorageLocation(i);
                        optionalCustomer.get().updateNumCargos('+');
                        notifyObservers();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean placeCargo(int key, CargoStorable cargo) {
        synchronized (monitor) {
            Optional<CustomerImpl> optionalCustomer = customerList.stream()
                    .filter(customer -> customer.getName().equals(cargo.getOwner().getName()))
                    .findFirst();
            if (optionalCustomer.isPresent() && cargoHashMap.get(key) == null && maxSize - cargoHashMap.size() > 0) {
                cargoHashMap.put(key, (CargoDoItAll) cargo);
                ((CargoDoItAll) cargo).setStorageLocation(key);
                optionalCustomer.get().updateNumCargos('+');
                notifyObservers();
                return true;
            }
            return false;
        }
    }

    public HashMap<Integer, CargoStorable> getCargoList() {
        HashMap<Integer, CargoStorable> cargoListForView = new HashMap<>();
        for (CargoDoItAll cargo : cargoHashMap.values()) {
            cargoListForView.put(cargo.getStorageLocation(), cargo);
        }
        return cargoListForView;
    }

    public HashMap<Integer, CargoStorable> getCargoList(CargoType type) {
        HashMap<Integer, CargoStorable> cargoListForView = new HashMap<>();
        for (CargoDoItAll cargo : cargoHashMap.values()) {
            if (type.equals(cargo.getType())) {
                cargoListForView.put(cargo.getStorageLocation(), cargo);
            }
        }
        return cargoListForView;
    }

    public Set<CustomerImpl> getCustomerList() {
        return new HashSet<>(customerList);
    }

    public boolean deleteCargo(Integer key) {
        synchronized (monitor) {
            CargoDoItAll cargo = cargoHashMap.get(key);
            if (cargo != null) {
                String ownerName = cargo.getOwner().getName();
                customerList.stream()
                        .filter(c -> c.getName().equals(ownerName))
                        .findFirst()
                        .ifPresent(c -> c.updateNumCargos('-'));

                cargoHashMap.remove(key);
                notifyObservers();
                return true;
            }
            return false;
        }
    }

    public boolean deleteCustomer(String name) {
        synchronized (monitor) {
            Optional<CustomerImpl> optionalCustomer = customerList.stream()
                    .filter(customer -> customer.getName().equals(name))
                    .findFirst();
            if (optionalCustomer.isPresent()) {
                cargoHashMap.entrySet().removeIf(entry -> entry.getValue().getOwner().getName().equals(name));
                customerList.removeIf(customer -> customer.getName().equals(name));
                notifyObservers();
                return true;
            }
            return false;
        }
    }

    public Date inspectCargo(int key) {
        synchronized (monitor) {
            if (cargoHashMap.get(key) != null) {
                Date date = new Date();
                cargoHashMap.get(key).setLastInspectionDate(date);
                return date;
            }
            return null;
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }

}
