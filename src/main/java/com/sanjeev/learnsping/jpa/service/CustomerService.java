package com.sanjeev.learnsping.jpa.service;

import com.sanjeev.learnsping.jpa.entity.Customer;
import com.sanjeev.learnsping.jpa.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Customer operations.
 * Demonstrates @Transactional and business logic.
 */
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CRUD operations

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Customer with email " + customer.getEmail() + " already exists");
        }
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(updatedCustomer.getFirstName());
                    customer.setLastName(updatedCustomer.getLastName());
                    customer.setEmail(updatedCustomer.getEmail());
                    customer.setPhone(updatedCustomer.getPhone());
                    customer.setDateOfBirth(updatedCustomer.getDateOfBirth());
                    customer.setAddress(updatedCustomer.getAddress());
                    customer.setCity(updatedCustomer.getCity());
                    customer.setZipCode(updatedCustomer.getZipCode());
                    customer.setStatus(updatedCustomer.getStatus());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    // Business logic methods

    @Transactional(readOnly = true)
    public List<Customer> getCustomersByCity(String city) {
        return customerRepository.findByCity(city);
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomersByStatus(Customer.CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Customer> searchCustomers(String searchTerm) {
        return customerRepository.searchCustomers(searchTerm);
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomersByName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomersBornAfter(LocalDate date) {
        return customerRepository.findByDateOfBirthAfter(date);
    }

    public Customer activateCustomer(Long id) {
        return updateCustomerStatus(id, Customer.CustomerStatus.ACTIVE);
    }

    public Customer suspendCustomer(Long id) {
        return updateCustomerStatus(id, Customer.CustomerStatus.SUSPENDED);
    }

    public Customer deactivateCustomer(Long id) {
        return updateCustomerStatus(id, Customer.CustomerStatus.INACTIVE);
    }

    private Customer updateCustomerStatus(Long id, Customer.CustomerStatus status) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setStatus(status);
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public long getCustomerCount() {
        return customerRepository.count();
    }

    @Transactional(readOnly = true)
    public long getActiveCustomerCount() {
        return customerRepository.countByStatus(Customer.CustomerStatus.ACTIVE);
    }
}

