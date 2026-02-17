package com.ganesh.crm.customer;

import com.ganesh.crm.customer.CustomerDTO;
import com.ganesh.crm.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {

        CustomerDTO customerDTO1 = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(customerDTO1,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping("/")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "createdAt") String sortBy) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(page, size, sortBy);
        return ResponseEntity.ok(customers);
    }


    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<CustomerDTO> getSingleCustomer(@PathVariable String phoneNumber) {
        CustomerDTO customerDTO = customerService.getCustomerByPhone(phoneNumber);
        return ResponseEntity.ok(customerDTO);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam String keyword) {
        List<CustomerDTO> searchedCustomers = customerService.searchCustomers(keyword);
        return ResponseEntity.ok(searchedCustomers);
    }



    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping("/{phoneNumber}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String phoneNumber, @RequestBody  CustomerUpdateDTO customerDTO){
        CustomerDTO customerDTO1 = customerService.updateCustomer(phoneNumber, customerDTO);
        return  ResponseEntity.ok(customerDTO1);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @DeleteMapping("/{phoneNumber}")
    public void deleteCustomer(@PathVariable String phoneNumber) {
        customerService.deleteCustomer(phoneNumber);
    }

    //bulk update of customer information
    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping("/bulk-update")
    public ResponseEntity<String> bulkUpdateCustomers(
            @RequestBody List<CustomerUpdateDTO> customers) {

        customerService.bulkUpdate(customers);
        return ResponseEntity.ok("Customers updated successfully");
    }


    //Customers under user
    @GetMapping("/users/{phoneNumber}/customers")
    public ResponseEntity<List<CustomerDTO>> getCustomersByUser(
            @PathVariable String phoneNumber) {

        List<CustomerDTO> customers =
                customerService.getCustomersByUser(phoneNumber);

        return ResponseEntity.ok(customers);
    }



}
