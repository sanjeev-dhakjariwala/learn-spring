package com.sanjeev.learnsping.jpa.controller;

import com.sanjeev.learnsping.jpa.entity.Customer;
import com.sanjeev.learnsping.jpa.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Customer operations.
 * Demonstrates CRUD operations via HTTP endpoints.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String term) {
        return ResponseEntity.ok(customerService.searchCustomers(term));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Customer>> getCustomersByCity(@PathVariable String city) {
        return ResponseEntity.ok(customerService.getCustomersByCity(city));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Customer>> getCustomersByStatus(@PathVariable Customer.CustomerStatus status) {
        return ResponseEntity.ok(customerService.getCustomersByStatus(status));
    }

    @GetMapping("/born-after")
    public ResponseEntity<List<Customer>> getCustomersBornAfter(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(customerService.getCustomersBornAfter(localDate));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer created = customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updated = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Customer> activateCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.activateCustomer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<Customer> suspendCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.suspendCustomer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Customer> deactivateCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.deactivateCustomer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCustomers", customerService.getCustomerCount());
        stats.put("activeCustomers", customerService.getActiveCustomerCount());
        return ResponseEntity.ok(stats);
    }
}

