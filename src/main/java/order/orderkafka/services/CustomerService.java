package order.orderkafka.services;

import order.orderkafka.entities.CustomerEntity;

public interface CustomerService {
    CustomerEntity getCustomerById(long id);
}
