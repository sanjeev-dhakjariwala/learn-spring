package com.sanjeev.learnspring.jpa.repository;

import com.sanjeev.learnspring.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Customer entity.
 * Demonstrates various query methods and custom queries.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Derived query methods - Spring generates implementation automatically

    /**
     * Find customer by email (unique)
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Find customers by first name
     */
    List<Customer> findByFirstName(String firstName);

    /**
     * Find customers by last name
     */
    List<Customer> findByLastName(String lastName);

    /**
     * Find customers by first name and last name
     */
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find customers by city
     */
    List<Customer> findByCity(String city);

    /**
     * Find customers by status
     */
    List<Customer> findByStatus(Customer.CustomerStatus status);

    /**
     * Find customers by email containing (partial match)
     */
    List<Customer> findByEmailContaining(String emailPart);

    /**
     * Find customers by first name starting with
     */
    List<Customer> findByFirstNameStartingWith(String prefix);

    /**
     * Find customers by date of birth after
     */
    List<Customer> findByDateOfBirthAfter(LocalDate date);

    /**
     * Find customers by city and status
     */
    List<Customer> findByCityAndStatus(String city, Customer.CustomerStatus status);

    /**
     * Find customers ordered by last name
     */
    List<Customer> findByStatusOrderByLastNameAsc(Customer.CustomerStatus status);

    /**
     * Check if customer exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Count customers by status
     */
    long countByStatus(Customer.CustomerStatus status);

    /**
     * Delete customers by status
     */
    void deleteByStatus(Customer.CustomerStatus status);

    // Custom JPQL queries using @Query annotation

    /**
     * Find customers by full name (custom JPQL query)
     */
    @Query("SELECT c FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName) = :fullName")
    List<Customer> findByFullName(@Param("fullName") String fullName);

    /**
     * Find customers by city with case-insensitive search
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.city) = LOWER(:city)")
    List<Customer> findByCityIgnoreCase(@Param("city") String city);

    /**
     * Find customers created after a specific date
     */
    @Query("SELECT c FROM Customer c WHERE c.createdAt > :date")
    List<Customer> findCustomersCreatedAfter(@Param("date") java.time.LocalDateTime date);

    /**
     * Search customers by name or email (custom query)
     */
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Customer> searchCustomers(@Param("searchTerm") String searchTerm);

    /**
     * Native SQL query example
     */
    @Query(value = "SELECT * FROM customers WHERE status = :status ORDER BY created_at DESC LIMIT :limit",
           nativeQuery = true)
    List<Customer> findRecentCustomersByStatus(@Param("status") String status, @Param("limit") int limit);
}

