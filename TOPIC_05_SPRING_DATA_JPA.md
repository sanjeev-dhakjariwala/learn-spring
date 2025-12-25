# Topic 05: Spring Data JPA and Data Access

## Overview
Spring Data JPA simplifies database access by providing repository abstractions and eliminating boilerplate code. It builds on top of JPA (Java Persistence API) to provide a complete data access solution.

## Key Concepts

### 1. **JPA Entity**
- POJO class annotated with `@Entity`
- Maps to a database table
- Uses annotations: `@Id`, `@GeneratedValue`, `@Column`, `@Table`

### 2. **Spring Data Repository**
- Interface extending `JpaRepository` or `CrudRepository`
- No implementation needed - Spring generates it
- Provides CRUD operations out of the box
- Query methods by naming convention

### 3. **H2 Database**
- In-memory database for development/testing
- Web console available at `/h2-console`
- No installation required

### 4. **Repository Query Methods**
- Derived queries: `findByFirstName`, `findByAgeGreaterThan`
- `@Query` annotation for custom JPQL
- Native SQL queries
- Named parameters

### 5. **CommandLineRunner**
- Interface to run code on application startup
- Used for database initialization/seeding
- Bean method runs after application context loads

## Repository Method Patterns

| Method Pattern | Example | Query Generated |
|----------------|---------|-----------------|
| findBy... | findByEmail(String email) | WHERE email = ? |
| findBy...And... | findByFirstNameAndLastName | WHERE firstName = ? AND lastName = ? |
| findBy...Or... | findByEmailOrPhone | WHERE email = ? OR phone = ? |
| findBy...GreaterThan | findByAgeGreaterThan(int age) | WHERE age > ? |
| findBy...Like | findByNameLike(String pattern) | WHERE name LIKE ? |
| findBy...OrderBy | findByAgeOrderByNameAsc | WHERE age = ? ORDER BY name ASC |

## Key Interview Points

1. **JPA vs Hibernate**: JPA is specification, Hibernate is implementation
2. **Repository Hierarchy**: `Repository` → `CrudRepository` → `PagingAndSortingRepository` → `JpaRepository`
3. **Lazy vs Eager Loading**: `FetchType.LAZY` (default for collections) vs `FetchType.EAGER`
4. **Entity Lifecycle**: Transient → Managed → Detached → Removed
5. **@Transactional**: Ensures operations are atomic, handles connection management
6. **Query Methods**: Spring derives queries from method names
7. **Pagination**: `Pageable` interface for efficient large data handling

## This Example Demonstrates

- `@Entity` with various field types and relationships
- `JpaRepository` with CRUD operations
- Custom query methods by naming convention
- `@Query` annotation for complex queries
- H2 in-memory database configuration
- `CommandLineRunner` for data seeding
- Repository testing with test data
- REST API for database operations

## Testing

- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (empty)
- REST endpoints at `/api/customers`

