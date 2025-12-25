package com.sanjeev.learnspring.jpa.service;

import com.sanjeev.learnspring.jpa.dto.CustomerRequestDTO;
import com.sanjeev.learnspring.jpa.dto.CustomerResponseDTO;
import com.sanjeev.learnspring.jpa.dto.CustomerUpdateDTO;
import com.sanjeev.learnspring.jpa.entity.Customer;
import com.sanjeev.learnspring.jpa.exception.CustomerNotFoundException;
import com.sanjeev.learnspring.jpa.exception.DuplicateEmailException;
import com.sanjeev.learnspring.jpa.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Customer operations.
 * Demonstrates @Transactional, DTO mapping, and business logic.
 */
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CRUD operations with DTOs

    /**
     * Create a new customer from DTO
     */
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        // Check for duplicate email
        if (customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateEmailException(requestDTO.getEmail());
        }

        // Convert DTO to entity
        Customer customer = new Customer();
        customer.setFirstName(requestDTO.getFirstName());
        customer.setLastName(requestDTO.getLastName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPhone(requestDTO.getPhone());
        customer.setDateOfBirth(requestDTO.getDateOfBirth());
        customer.setAddress(requestDTO.getAddress());
        customer.setCity(requestDTO.getCity());
        customer.setZipCode(requestDTO.getZipCode());
        customer.setStatus(Customer.CustomerStatus.ACTIVE);

        // Save and return DTO
        Customer saved = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(saved);
    }

    /**
     * Get all customers as DTOs
     */
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get customer by ID as DTO
     */
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return CustomerResponseDTO.fromEntity(customer);
    }

    /**
     * Get customer by email as DTO
     */
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));
        return CustomerResponseDTO.fromEntity(customer);
    }

    /**
     * Update customer with DTO (partial update)
     */
    public CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO updateDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        // Check for duplicate email if email is being changed
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(updateDTO.getEmail())) {
                throw new DuplicateEmailException(updateDTO.getEmail());
            }
            customer.setEmail(updateDTO.getEmail());
        }

        // Update only provided fields
        if (updateDTO.getFirstName() != null) {
            customer.setFirstName(updateDTO.getFirstName());
        }
        if (updateDTO.getLastName() != null) {
            customer.setLastName(updateDTO.getLastName());
        }
        if (updateDTO.getPhone() != null) {
            customer.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getDateOfBirth() != null) {
            customer.setDateOfBirth(updateDTO.getDateOfBirth());
        }
        if (updateDTO.getAddress() != null) {
            customer.setAddress(updateDTO.getAddress());
        }
        if (updateDTO.getCity() != null) {
            customer.setCity(updateDTO.getCity());
        }
        if (updateDTO.getZipCode() != null) {
            customer.setZipCode(updateDTO.getZipCode());
        }

        Customer updated = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(updated);
    }

    /**
     * Delete customer by ID
     */
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }

    // Business logic methods

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getCustomersByCity(String city) {
        return customerRepository.findByCity(city).stream()
                .map(CustomerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getCustomersByStatus(Customer.CustomerStatus status) {
        return customerRepository.findByStatus(status).stream()
                .map(CustomerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> searchCustomers(String searchTerm) {
        return customerRepository.searchCustomers(searchTerm).stream()
                .map(CustomerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getCustomersByName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(CustomerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getCustomersBornAfter(LocalDate date) {
        return customerRepository.findByDateOfBirthAfter(date).stream()
                .map(CustomerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CustomerResponseDTO activateCustomer(Long id) {
        return updateCustomerStatus(id, Customer.CustomerStatus.ACTIVE);
    }

    public CustomerResponseDTO suspendCustomer(Long id) {
        return updateCustomerStatus(id, Customer.CustomerStatus.SUSPENDED);
    }

    public CustomerResponseDTO deactivateCustomer(Long id) {
        return updateCustomerStatus(id, Customer.CustomerStatus.INACTIVE);
    }

    private CustomerResponseDTO updateCustomerStatus(Long id, Customer.CustomerStatus status) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setStatus(status);
        Customer updated = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(updated);
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

