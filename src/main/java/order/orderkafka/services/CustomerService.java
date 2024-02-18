package ma.therightman.orderkafka.services;

import ma.therightman.orderkafka.entities.CustomerEntity;

public interface CustomerService {
    CustomerEntity getCustomerById(long id);
}
