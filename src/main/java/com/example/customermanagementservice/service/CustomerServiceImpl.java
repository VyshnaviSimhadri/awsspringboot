package com.example.customermanagementservice.service;

import com.example.customermanagementservice.model.Customer;
import com.example.customermanagementservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long customerId) {
    	
        Optional<Customer> customer = customerRepository.findById(customerId);
        
        if(customer == null) {
        	
        	throw new ExceptionHandler("Customer with ID " + customerId + " does not exist.");

        	
        }else {
        	
        	return customer;
        	

        }
             
    }
    
    @PutMapping("/{customerId}") 
    public Customer updateCustomer(Long customerId, Customer customerDetails) {
    	
        	Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        	
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setFirstName(customerDetails.getFirstName());
            existingCustomer.setLastName(customerDetails.getLastName());
            existingCustomer.setEmail(customerDetails.getEmail());
            existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        
            return customerRepository.save(existingCustomer);
        
    }

    public void deleteCustomer(Long customerId) {
    	    		
    		customerRepository.deleteById(customerId);
        
    }
}
