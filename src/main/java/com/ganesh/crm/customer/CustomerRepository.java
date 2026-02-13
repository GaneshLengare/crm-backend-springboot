package com.ganesh.crm.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM Customer c " +
            "WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR c.phoneNumber LIKE CONCAT('%', :keyword, '%')")
    List<Customer> searchCustomers(@Param("keyword") String keyword);
}
