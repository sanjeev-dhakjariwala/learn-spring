package com.sanjeev.learnspring.jpa.repository;

import com.sanjeev.learnspring.jpa.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CustomerRepository using @DataJpaTest.
 * @DataJpaTest provides a test slice for JPA components.
 */
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Clean database before each test
        customerRepository.deleteAll();

        // Create test customer
        testCustomer = new Customer("Test", "User", "test.user@example.com");
        testCustomer.setPhone("+1-555-9999");
        testCustomer.setCity("TestCity");
        testCustomer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testCustomer.setStatus(Customer.CustomerStatus.ACTIVE);
        testCustomer = customerRepository.save(testCustomer);
    }

    @Test
    void contextLoads() {
        assertThat(customerRepository).isNotNull();
    }

    @Test
    void save_shouldPersistCustomer() {
        Customer customer = new Customer("New", "Customer", "new.customer@example.com");
        Customer saved = customerRepository.save(customer);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("New");
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    void findById_shouldReturnCustomer() {
        Optional<Customer> found = customerRepository.findById(testCustomer.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test.user@example.com");
    }

    @Test
    void findByEmail_shouldReturnCustomer() {
        Optional<Customer> found = customerRepository.findByEmail("test.user@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Test");
    }

    @Test
    void findByFirstName_shouldReturnCustomers() {
        Customer another = new Customer("Test", "Another", "test.another@example.com");
        customerRepository.save(another);

        List<Customer> customers = customerRepository.findByFirstName("Test");

        assertThat(customers).hasSize(2);
        assertThat(customers).allMatch(c -> c.getFirstName().equals("Test"));
    }

    @Test
    void findByCity_shouldReturnCustomersInCity() {
        Customer nyCustomer = new Customer("NY", "User", "ny.user@example.com");
        nyCustomer.setCity("TestCity");
        customerRepository.save(nyCustomer);

        List<Customer> customers = customerRepository.findByCity("TestCity");

        assertThat(customers).hasSize(2);
        assertThat(customers).allMatch(c -> c.getCity().equals("TestCity"));
    }

    @Test
    void findByStatus_shouldReturnCustomersByStatus() {
        Customer inactive = new Customer("Inactive", "User", "inactive@example.com");
        inactive.setStatus(Customer.CustomerStatus.INACTIVE);
        customerRepository.save(inactive);

        List<Customer> activeCustomers = customerRepository.findByStatus(Customer.CustomerStatus.ACTIVE);
        List<Customer> inactiveCustomers = customerRepository.findByStatus(Customer.CustomerStatus.INACTIVE);

        assertThat(activeCustomers).hasSize(1);
        assertThat(inactiveCustomers).hasSize(1);
    }

    @Test
    void findByEmailContaining_shouldReturnMatchingCustomers() {
        Customer another = new Customer("Another", "Test", "another.test@example.com");
        customerRepository.save(another);

        List<Customer> customers = customerRepository.findByEmailContaining("test");

        assertThat(customers).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void findByFirstNameStartingWith_shouldReturnCustomers() {
        List<Customer> customers = customerRepository.findByFirstNameStartingWith("Tes");

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getFirstName()).startsWith("Tes");
    }

    @Test
    void findByDateOfBirthAfter_shouldReturnYoungerCustomers() {
        Customer older = new Customer("Old", "Customer", "old@example.com");
        older.setDateOfBirth(LocalDate.of(1970, 1, 1));
        customerRepository.save(older);

        List<Customer> younger = customerRepository.findByDateOfBirthAfter(LocalDate.of(1980, 1, 1));

        assertThat(younger).hasSize(1);
        assertThat(younger.get(0).getEmail()).isEqualTo("test.user@example.com");
    }

    @Test
    void existsByEmail_shouldReturnTrueForExistingEmail() {
        boolean exists = customerRepository.existsByEmail("test.user@example.com");
        boolean notExists = customerRepository.existsByEmail("nonexistent@example.com");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    void countByStatus_shouldReturnCorrectCount() {
        Customer active1 = new Customer("Active1", "User", "active1@example.com");
        active1.setStatus(Customer.CustomerStatus.ACTIVE);
        customerRepository.save(active1);

        long activeCount = customerRepository.countByStatus(Customer.CustomerStatus.ACTIVE);

        assertThat(activeCount).isEqualTo(2);
    }

    @Test
    void searchCustomers_shouldFindByNameOrEmail() {
        List<Customer> results = customerRepository.searchCustomers("test");

        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(c -> c.getFirstName().equalsIgnoreCase("test") ||
                                           c.getEmail().contains("test"));
    }

    @Test
    void update_shouldModifyCustomer() {
        testCustomer.setCity("UpdatedCity");
        Customer updated = customerRepository.save(testCustomer);

        assertThat(updated.getCity()).isEqualTo("UpdatedCity");
        assertThat(updated.getUpdatedAt()).isNotNull();
    }

    @Test
    void delete_shouldRemoveCustomer() {
        Long id = testCustomer.getId();
        customerRepository.deleteById(id);

        Optional<Customer> deleted = customerRepository.findById(id);
        assertThat(deleted).isEmpty();
    }
}

