package com.ganesh.crm.customer;


import com.ganesh.crm.audit.CustomerAudit;
import com.ganesh.crm.audit.CustomerAuditLogRepository;
import com.ganesh.crm.user.User;
import com.ganesh.crm.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerAuditLogRepository customerAuditLogRepository;

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

   /* @Override
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
    }*/
   @Transactional
   @Override
   public CustomerDTO updateCustomer(
           String phoneNumber,
           CustomerUpdateDTO dto) {

       Customer existing = customerRepository.findById(phoneNumber)
               .orElseThrow(() ->
                       new RuntimeException("Customer not found"));

       // Logged-in user phone number
       String updatedBy = SecurityContextHolder
               .getContext()
               .getAuthentication()
               .getName();

       List<CustomerAudit> logs = new ArrayList<>();

       // first name
       if (!Objects.equals(existing.getFirstName(), dto.getFirstName())) {

           logs.add(new CustomerAudit(
                   null, phoneNumber, "firstName",
                   existing.getFirstName(),
                   dto.getFirstName(),
                   updatedBy,
                   LocalDateTime.now()
           ));

           existing.setFirstName(dto.getFirstName());
       }

       // for last name
       if (!Objects.equals(existing.getLastName(), dto.getLastName())) {

           logs.add(new CustomerAudit(
                   null, phoneNumber, "lastName",
                   existing.getLastName(),
                   dto.getLastName(),
                   updatedBy,
                   LocalDateTime.now()
           ));

           existing.setLastName(dto.getLastName());
       }

       // email update track
       if (!Objects.equals(existing.getEmail(), dto.getEmail())) {

           logs.add(new CustomerAudit(
                   null, phoneNumber, "email",
                   existing.getEmail(),
                   dto.getEmail(),
                   updatedBy,
                   LocalDateTime.now()
           ));

           existing.setEmail(dto.getEmail());
       }

       // for address track
       if (!Objects.equals(existing.getAddress(), dto.getAddress())) {

           logs.add(new CustomerAudit(
                   null, phoneNumber, "address",
                   existing.getAddress(),
                   dto.getAddress(),
                   updatedBy,
                   LocalDateTime.now()
           ));

           existing.setAddress(dto.getAddress());
       }

       // status
       if (dto.getStatus() != null &&
               !Objects.equals(existing.getStatus(), dto.getStatus())) {

           logs.add(new CustomerAudit(
                   null, phoneNumber, "status",
                   existing.getStatus().name(),
                   dto.getStatus().name(),
                   updatedBy,
                   LocalDateTime.now()
           ));

           existing.setStatus(dto.getStatus());
       }

       customerRepository.save(existing);

       if (!logs.isEmpty()) {
           customerAuditLogRepository.saveAll(logs);
       }

       return modelMapper.map(existing, CustomerDTO.class);
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
