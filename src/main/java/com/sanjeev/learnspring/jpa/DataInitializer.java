package com.sanjeev.learnspring.jpa;

import com.sanjeev.learnspring.jpa.entity.Customer;
import com.sanjeev.learnspring.jpa.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * CommandLineRunner to seed the H2 database with initial data.
 * Runs automatically when the application starts.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final CustomerRepository customerRepository;

    public DataInitializer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("===== Starting Database Initialization =====");

        // Clear existing data (optional, since we use create-drop)
        customerRepository.deleteAll();
        log.info("Cleared existing customer data");

        // Seed customers
        Customer customer1 = new Customer("John", "Doe", "john.doe@example.com");
        customer1.setPhone("+1-555-0101");
        customer1.setDateOfBirth(LocalDate.of(1985, 6, 15));
        customer1.setAddress("123 Main Street");
        customer1.setCity("New York");
        customer1.setZipCode("10001");
        customer1.setStatus(Customer.CustomerStatus.ACTIVE);

        Customer customer2 = new Customer("Jane", "Smith", "jane.smith@example.com");
        customer2.setPhone("+1-555-0102");
        customer2.setDateOfBirth(LocalDate.of(1990, 3, 22));
        customer2.setAddress("456 Oak Avenue");
        customer2.setCity("Los Angeles");
        customer2.setZipCode("90001");
        customer2.setStatus(Customer.CustomerStatus.ACTIVE);

        Customer customer3 = new Customer("Bob", "Johnson", "bob.johnson@example.com");
        customer3.setPhone("+1-555-0103");
        customer3.setDateOfBirth(LocalDate.of(1978, 11, 8));
        customer3.setAddress("789 Pine Road");
        customer3.setCity("Chicago");
        customer3.setZipCode("60601");
        customer3.setStatus(Customer.CustomerStatus.ACTIVE);

        Customer customer4 = new Customer("Alice", "Williams", "alice.williams@example.com");
        customer4.setPhone("+1-555-0104");
        customer4.setDateOfBirth(LocalDate.of(1992, 7, 30));
        customer4.setAddress("321 Elm Street");
        customer4.setCity("Houston");
        customer4.setZipCode("77001");
        customer4.setStatus(Customer.CustomerStatus.INACTIVE);

        Customer customer5 = new Customer("Charlie", "Brown", "charlie.brown@example.com");
        customer5.setPhone("+1-555-0105");
        customer5.setDateOfBirth(LocalDate.of(1988, 2, 14));
        customer5.setAddress("654 Maple Drive");
        customer5.setCity("Phoenix");
        customer5.setZipCode("85001");
        customer5.setStatus(Customer.CustomerStatus.ACTIVE);

        Customer customer6 = new Customer("Diana", "Davis", "diana.davis@example.com");
        customer6.setPhone("+1-555-0106");
        customer6.setDateOfBirth(LocalDate.of(1995, 9, 5));
        customer6.setAddress("987 Cedar Lane");
        customer6.setCity("New York");
        customer6.setZipCode("10002");
        customer6.setStatus(Customer.CustomerStatus.SUSPENDED);

        Customer customer7 = new Customer("Eve", "Miller", "eve.miller@example.com");
        customer7.setPhone("+1-555-0107");
        customer7.setDateOfBirth(LocalDate.of(1983, 12, 25));
        customer7.setAddress("147 Birch Court");
        customer7.setCity("Los Angeles");
        customer7.setZipCode("90002");
        customer7.setStatus(Customer.CustomerStatus.ACTIVE);

        Customer customer8 = new Customer("Frank", "Wilson", "frank.wilson@example.com");
        customer8.setPhone("+1-555-0108");
        customer8.setDateOfBirth(LocalDate.of(1980, 5, 18));
        customer8.setAddress("258 Spruce Avenue");
        customer8.setCity("Chicago");
        customer8.setZipCode("60602");
        customer8.setStatus(Customer.CustomerStatus.ACTIVE);

        // Save all customers
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);
        customerRepository.save(customer6);
        customerRepository.save(customer7);
        customerRepository.save(customer8);

        log.info("Saved {} customers to database", customerRepository.count());

        // Display summary
        log.info("===== Database Initialization Complete =====");
        log.info("Total customers: {}", customerRepository.count());
        log.info("Active customers: {}", customerRepository.countByStatus(Customer.CustomerStatus.ACTIVE));
        log.info("Inactive customers: {}", customerRepository.countByStatus(Customer.CustomerStatus.INACTIVE));
        log.info("Suspended customers: {}", customerRepository.countByStatus(Customer.CustomerStatus.SUSPENDED));

        // Log some sample queries
        log.info("Customers in New York: {}",
                customerRepository.findByCity("New York").stream()
                        .map(Customer::getFullName)
                        .toList());

        log.info("Customers with last name 'Smith': {}",
                customerRepository.findByLastName("Smith").stream()
                        .map(Customer::getFullName)
                        .toList());
    }
}

