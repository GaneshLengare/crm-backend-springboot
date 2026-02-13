package com.ganesh.crm.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    @NotBlank
    @Size(min = 10, max = 12)
    private String phoneNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String status;

    @NotBlank
    private String userPhone;


}
