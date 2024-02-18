package ma.therightman.orderkafka.services.impl;

import ma.therightman.orderkafka.entities.CustomerEntity;
import ma.therightman.orderkafka.exceptions.CustomerNotFoundException;
import ma.therightman.orderkafka.repositories.CustomerRepository;
import ma.therightman.orderkafka.services.CustomerService;
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
