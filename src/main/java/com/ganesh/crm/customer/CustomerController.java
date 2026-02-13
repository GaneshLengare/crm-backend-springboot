package com.ganesh.crm.customer;

import com.ganesh.crm.customer.CustomerDTO;
import com.ganesh.crm.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aepi/customrs")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {

        CustomerDTO customerDTO1 = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(customerDTO1,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size, @RequestParam(defaultValue = "createdAt") String sortBy) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(page, size, sortBy);
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/{phoneNumber}")
    public ResponseEntity<CustomerDTO> getSingleCustomer(@PathVariable String phoneNumber) {
        CustomerDTO customerDTO = customerService.getCustomerByPhone(phoneNumber);
        return ResponseEntity.ok(customerDTO);
    }

}
