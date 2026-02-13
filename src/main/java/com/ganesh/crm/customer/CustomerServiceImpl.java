package com.ganesh.crm.customer;

import com.ganesh.crm.user.User;
import com.ganesh.crm.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        if (customerRepository.existsById(customerDTO.getPhoneNumber())) {
            throw new RuntimeException("Customer already exists");
        }

        User user = userRepository.findById(customerDTO.getUserPhone())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Customer customer = modelMapper.map(customerDTO, Customer.class);

        customer.setUser(user);
        customer.setStatus(Customer.Status.ACTIVE);

        Customer saved = customerRepository.save(customer);

        CustomerDTO response =
                modelMapper.map(saved, CustomerDTO.class);

        response.setStatus(saved.getStatus().name());
        response.setUserPhone(saved.getUser().getPhoneNumber());

        return response;
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(sortBy).ascending());

        Page<Customer> page1 = customerRepository.findAll(pageRequest);
        return page1.map(customer -> {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            customerDTO.setStatus(customer.getStatus().name());
            customerDTO.setUserPhone(customer.getUser().getPhoneNumber());
            return customerDTO;
        });
    }

    @Override
    public CustomerDTO getCustomerByPhone(String phoneNumber) {
        Customer customer = customerRepository.findById(phoneNumber).orElseThrow(() -> new EntityNotFoundException("Customer Not Found!"));
        CustomerDTO customerDTO1 = modelMapper.map(customer, CustomerDTO.class);
        return customerDTO1;
    }
}
