package storageAdministration;

import administration.Customer;

import java.io.Serializable;

public class CustomerImpl implements Customer, Serializable {
    private String name;
    private int numCargos;

    public CustomerImpl(String name) {
        this.name = name;
        this.numCargos = 0;
    }
    @Override
    public String getName() {
        return name;
    }

    public void updateNumCargos(char plusOrMinus) {
        if (plusOrMinus == '+') {
            this.numCargos += 1;
        } else if (plusOrMinus == '-') {
            this.numCargos -= 1;
        }
    }

    public int getNumCargos() {
        return numCargos;
    }

}
