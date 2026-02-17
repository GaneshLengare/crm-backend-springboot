package com.ganesh.crm.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);


    Page<CustomerDTO> getAllCustomers(int page, int size, String sortBy);


    CustomerDTO getCustomerByPhone(String phoneNumber);




    List<CustomerDTO> searchCustomers(String keyword);


    CustomerDTO updateCustomer(String phoneNumber, CustomerUpdateDTO customerDTO);

    void deleteCustomer(String phoneNumber);

    List<CustomerDTO> getCustomersByUser(String phoneNumber);

    //bulk
    void bulkUpdate(List<CustomerUpdateDTO> customers);


}
