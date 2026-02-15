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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customer = customerRepository.searchCustomers(keyword);

        return customer.stream().map(customer1 -> {
            CustomerDTO customerDTO = modelMapper.map(customer1, CustomerDTO.class);
            customerDTO.setStatus(customer1.getStatus().name());
            customerDTO.setUserPhone(customer1.getUser().getPhoneNumber());
            return customerDTO;
        }).toList();
    }

    @Override
    public CustomerDTO updateCustomer(String phoneNumber, CustomerUpdateDTO customerDTO) {
       Customer customer = customerRepository.findById(phoneNumber).orElseThrow(() -> new EntityNotFoundException("Customer Not FOUND"));

      // customer.setPhoneNumber(customerDTO.getPhoneNumber());
       customer.setFirstName(customerDTO.getFirstName());
       customer.setLastName(customerDTO.getLastName());
       customer.setEmail(customerDTO.getEmail());
       customer.setAddress(customerDTO.getAddress());

        if (customerDTO.getUserPhone() != null) {
            User user = userRepository.findById(customerDTO.getUserPhone())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            customer.setUser(user);
        }

        Customer updateCustomer = customerRepository.save(customer);

        CustomerDTO customerDTO1 = modelMapper.map(customer, CustomerDTO.class);
        customerDTO1.setStatus(updateCustomer.getStatus().name());
        customerDTO1.setUserPhone(updateCustomer.getUser().getPhoneNumber());

        return customerDTO1;
    }

    @Override
    public void deleteCustomer(String phoneNumber) {
        customerRepository.deleteById(phoneNumber);
    }

    //Pending
    @Override
    public List<Customer> getCustomersByUser(String phoneNumber) {
        return List.of();
    }


}
