package com.example.customermanagementservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.customermanagementservice.model.Customer;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long customerId);

    Customer updateCustomer(Long customerId, Customer customerDetails);

    void deleteCustomer(Long customerId);
}


