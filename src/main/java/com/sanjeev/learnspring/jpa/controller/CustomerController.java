package com.sanjeev.learnspring.jpa.controller;

import com.sanjeev.learnspring.jpa.dto.CustomerRequestDTO;
import com.sanjeev.learnspring.jpa.dto.CustomerResponseDTO;
import com.sanjeev.learnspring.jpa.dto.CustomerUpdateDTO;
import com.sanjeev.learnspring.jpa.entity.Customer;
import com.sanjeev.learnspring.jpa.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Customer operations.
 * Demonstrates REST best practices:
 * - Proper HTTP methods (GET, POST, PUT, PATCH, DELETE)
 * - Appropriate status codes (200, 201, 204, 400, 404, 409)
 * - DTOs for request/response
 * - Validation with @Valid
 * - URI location header for created resources
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * GET /api/customers - Get all customers
     * Returns: 200 OK with list of customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * GET /api/customers/{id} - Get customer by ID
     * Returns: 200 OK if found, 404 Not Found if not exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        CustomerResponseDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * GET /api/customers/email/{email} - Get customer by email
     * Returns: 200 OK if found, 404 Not Found if not exists
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByEmail(@PathVariable String email) {
        CustomerResponseDTO customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }

    /**
     * GET /api/customers/search?term={term} - Search customers
     * Returns: 200 OK with list of matching customers
     */
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomers(
            @RequestParam String term) {
        List<CustomerResponseDTO> customers = customerService.searchCustomers(term);
        return ResponseEntity.ok(customers);
    }

    /**
     * GET /api/customers/city/{city} - Get customers by city
     * Returns: 200 OK with list of customers
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersByCity(@PathVariable String city) {
        List<CustomerResponseDTO> customers = customerService.getCustomersByCity(city);
        return ResponseEntity.ok(customers);
    }

    /**
     * GET /api/customers/status/{status} - Get customers by status
     * Returns: 200 OK with list of customers
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersByStatus(
            @PathVariable Customer.CustomerStatus status) {
        List<CustomerResponseDTO> customers = customerService.getCustomersByStatus(status);
        return ResponseEntity.ok(customers);
    }

    /**
     * GET /api/customers/born-after?date={date} - Get customers born after date
     * Returns: 200 OK with list of customers
     */
    @GetMapping("/born-after")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersBornAfter(
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<CustomerResponseDTO> customers = customerService.getCustomersBornAfter(localDate);
        return ResponseEntity.ok(customers);
    }

    /**
     * POST /api/customers - Create a new customer
     * Returns: 201 Created with location header and created customer
     * Validates: All required fields using @Valid
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO requestDTO) {
        CustomerResponseDTO created = customerService.createCustomer(requestDTO);

        // Create location URI for the created resource
        URI location = URI.create("/api/customers/" + created.getId());

        return ResponseEntity
                .created(location)
                .body(created);
    }

    /**
     * PUT /api/customers/{id} - Update customer (partial update)
     * Returns: 200 OK with updated customer, 404 Not Found if not exists
     * Validates: Fields using @Valid
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateDTO updateDTO) {
        CustomerResponseDTO updated = customerService.updateCustomer(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/customers/{id} - Delete customer
     * Returns: 204 No Content if successful, 404 Not Found if not exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/customers/{id}/activate - Activate customer
     * Returns: 200 OK with updated customer
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<CustomerResponseDTO> activateCustomer(@PathVariable Long id) {
        CustomerResponseDTO activated = customerService.activateCustomer(id);
        return ResponseEntity.ok(activated);
    }

    /**
     * PATCH /api/customers/{id}/suspend - Suspend customer
     * Returns: 200 OK with updated customer
     */
    @PatchMapping("/{id}/suspend")
    public ResponseEntity<CustomerResponseDTO> suspendCustomer(@PathVariable Long id) {
        CustomerResponseDTO suspended = customerService.suspendCustomer(id);
        return ResponseEntity.ok(suspended);
    }

    /**
     * PATCH /api/customers/{id}/deactivate - Deactivate customer
     * Returns: 200 OK with updated customer
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CustomerResponseDTO> deactivateCustomer(@PathVariable Long id) {
        CustomerResponseDTO deactivated = customerService.deactivateCustomer(id);
        return ResponseEntity.ok(deactivated);
    }

    /**
     * GET /api/customers/stats/count - Get total customer count
     * Returns: 200 OK with count
     */
    @GetMapping("/stats/count")
    public ResponseEntity<Long> getCustomerCount() {
        long count = customerService.getCustomerCount();
        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/customers/stats/active-count - Get active customer count
     * Returns: 200 OK with count
     */
    @GetMapping("/stats/active-count")
    public ResponseEntity<Long> getActiveCustomerCount() {
        long count = customerService.getActiveCustomerCount();
        return ResponseEntity.ok(count);
    }
}

