package com.example.customermanagementservice.service;

import com.example.customermanagementservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            if (!isValidCustomer(customer)) {
                throw new IllegalArgumentException("Mandatory fields are Missing. Please Provide all fields (firstName, lastName, email, and phoneNumber).");
            } else {
                Customer savedCustomer = customerService.addCustomer(customer);
                return ResponseEntity.ok(savedCustomer);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private boolean isValidCustomer(Customer customer) {
        return customer.getFirstName() != null
                && customer.getLastName() != null
                && customer.getEmail() != null
                && customer.getPhoneNumber() != null;
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {
        try {
            Optional<Customer> customer = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(customer.orElseThrow(() -> new NoSuchElementException("Customer with id " + customerId + " not found.")));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customerDetails) {
        try {
            Optional<Customer> customer = customerService.getCustomerById(customerId);
            if (!customer.isPresent()) {
                return ResponseEntity.badRequest().body("Customer with id " + customerId + " not found.");
            }
            if (!isValidCustomer(customerDetails)) {
                throw new IllegalArgumentException("Mandatory fields are missing. Provide all fields (firstName, lastName, email, and phoneNumber).");
            }
            Customer updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Customer with id " + customerId + " not found.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        try {
            Optional<Customer> customer = customerService.getCustomerById(customerId);
            if (!customer.isPresent()) {
                return ResponseEntity.badRequest().body("Customer with id " + customerId + " not found.");
            }
            restTemplate.delete("http://localhost:8070/accounts/del/{customerId}", customerId);
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Customer Deleted Successfully along with the Account");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Customer with id " + customerId + " not found.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
    }
}
