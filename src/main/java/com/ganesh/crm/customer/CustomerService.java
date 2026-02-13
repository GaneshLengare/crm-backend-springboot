package com.ganesh.crm.customer;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);


    Page<CustomerDTO> getAllCustomers(int page, int size, String sortBy);
}
