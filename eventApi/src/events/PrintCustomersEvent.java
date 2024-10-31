package events;

import storageAdministration.CustomerImpl;

import java.util.EventObject;
import java.util.Set;

public class PrintCustomersEvent extends EventObject {
    private Set<CustomerImpl> customerSet;

    public PrintCustomersEvent(Object source, Set<CustomerImpl> customerSet) {
        super(source);
        this.customerSet = customerSet;
    }

    public Set<CustomerImpl> getCustomerSet() {
        return customerSet;
    }
}
