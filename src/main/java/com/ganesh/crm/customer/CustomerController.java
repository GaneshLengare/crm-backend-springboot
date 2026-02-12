package com.ganesh.crm.customer;

import com.ganesh.crm.customer.CustomerDTO;
import com.ganesh.crm.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @RequestBody CustomerDTO customerDTO,
            Authentication authentication) {

        String phoneNumber = authentication.getName();

        CustomerDTO response =
                customerService.createCustomer(customerDTO, phoneNumber);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
