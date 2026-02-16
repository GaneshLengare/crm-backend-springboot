package com.ganesh.crm.customer;

import com.ganesh.crm.customer.Customer.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDTO {

    @Size(min = 2, max = 50, message = "first name is least 2 character and most 50")
    private String firstName;

    @Size(min = 2, max = 50, message = "last name is least 2 character and most 50")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    private Status status;

    @Size(max = 255, message = "Maximum Address size 255 characters")
    private String address;
}