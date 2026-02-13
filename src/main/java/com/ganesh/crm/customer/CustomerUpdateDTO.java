package com.ganesh.crm.customer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateDTO {

    private String firstName;
    private String lastName;
    private String email ;
    private String address;
    private String status;
    private String userPhone;
}
