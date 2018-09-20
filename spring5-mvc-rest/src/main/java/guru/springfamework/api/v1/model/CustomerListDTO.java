//: guru.springfamework.api.v1.model.CustomerListDTO.java


package guru.springfamework.api.v1.model;


import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class CustomerListDTO {

    private final List<CustomerDTO> customers = new ArrayList<>();

    public CustomerListDTO(@NonNull List<CustomerDTO> customers) {
        this.customers.addAll(customers);
    }

    public final List<CustomerDTO> getCustomers() {
        return Collections.unmodifiableList(this.customers);
    }

}///:~