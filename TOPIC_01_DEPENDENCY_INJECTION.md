# Topic 1: Dependency Injection (DI) in Spring

## Concepts Covered

### 1. **Interface-based Programming**
- `GreetingService` interface defines the contract
- Multiple implementations can exist

### 2. **@Service Annotation**
- Marks classes as Spring-managed beans
- Part of Spring's stereotype annotations
- Enables component scanning

### 3. **@Primary Annotation**
- `FriendlyGreetingService` is marked with `@Primary`
- When multiple beans of the same type exist, `@Primary` bean is injected by default
- Resolves ambiguity in dependency injection

### 4. **@Qualifier Annotation**
- `FormalGreetingService` uses `@Qualifier("formalGreetingService")`
- Allows explicit bean selection when multiple candidates exist
- Used in constructor injection: `@Qualifier("formalGreetingService")`

### 5. **Constructor Injection (Best Practice)**
- Dependencies injected via constructor
- Immutable fields (final)
- Clear dependencies visible in constructor signature
- Enables easier testing

## Files Created

```
src/main/java/com/sanjeev/learnsping/greeting/
├── GreetingService.java              # Interface
├── FriendlyGreetingService.java      # @Primary implementation
├── FormalGreetingService.java        # @Qualifier implementation
└── GreetingController.java           # REST controller with DI

src/test/java/com/sanjeev/learnsping/greeting/
└── GreetingControllerTest.java       # Integration tests
```

## API Endpoints

### 1. Default Greeting (uses @Primary bean)
```bash
GET http://localhost:8080/api/greetings?name=Sanjeev
Response: "Hey Sanjeev!"
```

### 2. Formal Greeting (uses @Qualifier bean)
```bash
GET http://localhost:8080/api/greetings/formal?name=Sanjeev
Response: "Good day, Sanjeev."
```

## Interview Questions to Prepare

1. **What is Dependency Injection?**
   - A design pattern where objects receive their dependencies from external sources rather than creating them

2. **What are the types of DI in Spring?**
   - Constructor Injection (recommended)
   - Setter Injection
   - Field Injection (not recommended)

3. **Why is Constructor Injection preferred?**
   - Immutability (final fields)
   - Required dependencies are clear
   - Better for testing
   - Prevents NullPointerException

4. **What is @Primary used for?**
   - Marks a bean as the default when multiple beans of same type exist

5. **What is @Qualifier used for?**
   - Explicitly selects a specific bean when multiple candidates exist

6. **What is the difference between @Component, @Service, @Repository, and @Controller?**
   - All are stereotype annotations
   - @Service: Business logic layer
   - @Repository: Data access layer (adds persistence exception translation)
   - @Controller/@RestController: Presentation layer
   - @Component: Generic stereotype

## How to Run

```bash
# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Test endpoints
curl "http://localhost:8080/api/greetings?name=Sanjeev"
curl "http://localhost:8080/api/greetings/formal?name=Sanjeev"
```

## Key Takeaways

✅ Spring manages object lifecycle and dependencies  
✅ Constructor injection is the best practice  
✅ @Primary resolves default bean selection  
✅ @Qualifier allows explicit bean selection  
✅ Interface-based programming enables flexibility and testability

