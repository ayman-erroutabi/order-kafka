package order.orderkafka.services.impl;

import order.orderkafka.entities.CustomerEntity;
import order.orderkafka.exceptions.CustomerNotFoundException;
import order.orderkafka.repositories.CustomerRepository;
import order.orderkafka.services.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity getCustomerById(long id) {
        return this.customerRepository.findById(id).orElseThrow(() ->  new CustomerNotFoundException(id));
    }
}
