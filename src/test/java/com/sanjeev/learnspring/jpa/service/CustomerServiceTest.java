package com.sanjeev.learnspring.jpa.service;

import com.sanjeev.learnspring.jpa.entity.Customer;
import com.sanjeev.learnspring.jpa.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for CustomerService.
 * Tests service layer with actual database interactions.
 */
@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Note: DataInitializer will run, so we have seeded data
    }

    @Test
    void contextLoads() {
        assertThat(customerService).isNotNull();
    }

    @Test
    void createCustomer_shouldSaveNewCustomer() {
        Customer newCustomer = new Customer("New", "Customer", "new.unique@example.com");
        newCustomer.setCity("TestCity");

        Customer created = customerService.createCustomer(newCustomer);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getEmail()).isEqualTo("new.unique@example.com");
        assertThat(created.getStatus()).isEqualTo(Customer.CustomerStatus.ACTIVE);
    }

    @Test
    void createCustomer_shouldThrowExceptionForDuplicateEmail() {
        // Use an email that exists from DataInitializer
        Customer duplicate = new Customer("Duplicate", "User", "john.doe@example.com");

        assertThatThrownBy(() -> customerService.createCustomer(duplicate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void getAllCustomers_shouldReturnAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers).isNotEmpty();
        // DataInitializer seeds 8 customers
        assertThat(customers.size()).isGreaterThanOrEqualTo(8);
    }

    @Test
    void getCustomerById_shouldReturnCustomer() {
        List<Customer> all = customerService.getAllCustomers();
        Long existingId = all.get(0).getId();

        Optional<Customer> found = customerService.getCustomerById(existingId);

        assertThat(found).isPresent();
    }

    @Test
    void getCustomerByEmail_shouldReturnCustomer() {
        Optional<Customer> found = customerService.getCustomerByEmail("john.doe@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("John");
    }

    @Test
    void updateCustomer_shouldModifyExistingCustomer() {
        Customer existing = customerService.getCustomerByEmail("john.doe@example.com").orElseThrow();
        Long id = existing.getId();

        existing.setCity("UpdatedCity");
        Customer updated = customerService.updateCustomer(id, existing);

        assertThat(updated.getCity()).isEqualTo("UpdatedCity");
    }

    @Test
    void updateCustomer_shouldThrowExceptionForNonExistentId() {
        Customer customer = new Customer("Test", "Test", "test@test.com");

        assertThatThrownBy(() -> customerService.updateCustomer(99999L, customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void deleteCustomer_shouldRemoveCustomer() {
        Customer toDelete = new Customer("Delete", "Me", "delete.me@example.com");
        toDelete = customerRepository.save(toDelete);
        Long id = toDelete.getId();

        customerService.deleteCustomer(id);

        Optional<Customer> deleted = customerService.getCustomerById(id);
        assertThat(deleted).isEmpty();
    }

    @Test
    void getCustomersByCity_shouldReturnCustomersInCity() {
        List<Customer> nyCustomers = customerService.getCustomersByCity("New York");

        assertThat(nyCustomers).isNotEmpty();
        assertThat(nyCustomers).allMatch(c -> c.getCity().equals("New York"));
    }

    @Test
    void getCustomersByStatus_shouldReturnFilteredCustomers() {
        List<Customer> activeCustomers = customerService.getCustomersByStatus(Customer.CustomerStatus.ACTIVE);

        assertThat(activeCustomers).isNotEmpty();
        assertThat(activeCustomers).allMatch(c -> c.getStatus() == Customer.CustomerStatus.ACTIVE);
    }

    @Test
    void searchCustomers_shouldFindMatches() {
        List<Customer> results = customerService.searchCustomers("john");

        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(c -> c.getEmail().contains("john") ||
                                           c.getFirstName().toLowerCase().contains("john"));
    }

    @Test
    void getCustomersBornAfter_shouldReturnYoungerCustomers() {
        List<Customer> younger = customerService.getCustomersBornAfter(LocalDate.of(1990, 1, 1));

        assertThat(younger).isNotEmpty();
        assertThat(younger).allMatch(c -> c.getDateOfBirth().isAfter(LocalDate.of(1990, 1, 1)));
    }

    @Test
    void activateCustomer_shouldChangeStatus() {
        // Find an inactive customer from DataInitializer
        Customer inactive = customerService.getCustomerByEmail("alice.williams@example.com").orElseThrow();
        assertThat(inactive.getStatus()).isEqualTo(Customer.CustomerStatus.INACTIVE);

        Customer activated = customerService.activateCustomer(inactive.getId());

        assertThat(activated.getStatus()).isEqualTo(Customer.CustomerStatus.ACTIVE);
    }

    @Test
    void suspendCustomer_shouldChangeStatus() {
        Customer active = customerService.getCustomerByEmail("john.doe@example.com").orElseThrow();

        Customer suspended = customerService.suspendCustomer(active.getId());

        assertThat(suspended.getStatus()).isEqualTo(Customer.CustomerStatus.SUSPENDED);
    }

    @Test
    void deactivateCustomer_shouldChangeStatus() {
        Customer active = customerService.getCustomerByEmail("jane.smith@example.com").orElseThrow();

        Customer deactivated = customerService.deactivateCustomer(active.getId());

        assertThat(deactivated.getStatus()).isEqualTo(Customer.CustomerStatus.INACTIVE);
    }

    @Test
    void getCustomerCount_shouldReturnTotalCount() {
        long count = customerService.getCustomerCount();

        assertThat(count).isGreaterThanOrEqualTo(8);
    }

    @Test
    void getActiveCustomerCount_shouldReturnActiveCount() {
        long activeCount = customerService.getActiveCustomerCount();

        assertThat(activeCount).isPositive();
    }
}

