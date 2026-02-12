package com.ganesh.crm.customer;

import com.ganesh.crm.user.User;
import com.ganesh.crm.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(ModelMapper modelMapper,
                               CustomerRepository customerRepository,
                               UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO, String userPhone) {

        if (customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber())) {
            throw new EntityExistsException("Customer with this phone number already exists");
        }

        User user = userRepository.findByPhoneNumber(userPhone)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setUser(user);

        Customer saved = customerRepository.save(customer);

        return modelMapper.map(saved, CustomerDTO.class);
    }
}
