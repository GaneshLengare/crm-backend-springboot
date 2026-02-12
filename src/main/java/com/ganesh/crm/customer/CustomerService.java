package com.ganesh.crm.customer;

import org.springframework.stereotype.Service;


public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO, String phoneNumber);
}
