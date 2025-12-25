# Spring MVC with Thymeleaf - Implementation Guide

## What Has Been Implemented

### 1. **Thymeleaf Dependency**
Added `spring-boot-starter-thymeleaf` to `pom.xml` to enable server-side template rendering.

### 2. **GreetingViewController** (@Controller)
**File:** `src/main/java/com/sanjeev/learnspring/greeting/controller/GreetingViewController.java`

- Uses `@Controller` (not `@RestController`) to return view names instead of JSON
- Injects both `GreetingService` implementations using constructor injection
- Uses `Model` to pass data to Thymeleaf templates

**Endpoints:**
- `GET /greetings` → Returns greeting.html (main page)
- `GET /greetings/friendly?name=John` → Friendly greeting with result page
- `GET /greetings/formal?name=John` → Formal greeting with result page

### 3. **Thymeleaf Templates**
**File:** `src/main/resources/templates/greeting.html`
- Main landing page with a form
- Styled with modern CSS (gradient background, responsive design)
- Buttons to choose friendly or formal greeting
- Shows Spring MVC concepts being demonstrated

**File:** `src/main/resources/templates/greeting-result.html`
- Displays the greeting result dynamically
- Shows which service was used (@Primary vs @Qualifier)
- Includes technical information about Spring concepts
- Provides navigation back to main page

### 4. **REST API vs MVC Comparison**

#### REST Controller (`GreetingController` - @RestController)
- Returns JSON data (GreetingResponse DTO)
- Used for APIs consumed by JavaScript, mobile apps, etc.
- Endpoints: `/api/greetings/*`
- Content-Type: `application/json`

#### MVC Controller (`GreetingViewController` - @Controller)
- Returns HTML pages rendered by Thymeleaf
- Used for traditional web applications
- Endpoints: `/greetings/*`
- Content-Type: `text/html`

## Spring MVC Concepts Demonstrated

### 1. **@Controller vs @RestController**
```java
@Controller  // Returns view names (Thymeleaf templates)
@RequestMapping("/greetings")
public class GreetingViewController { ... }

@RestController  // Returns data (JSON/XML)
@RequestMapping("/api/greetings")
public class GreetingController { ... }
```

### 2. **Model Object**
```java
@GetMapping("/friendly")
public String friendlyGreeting(@RequestParam String name, Model model) {
    model.addAttribute("message", message);  // Pass data to view
    model.addAttribute("type", "Friendly");
    return "greeting-result";  // View name (greeting-result.html)
}
```

### 3. **Thymeleaf Template Syntax**
```html
<!-- Variable expression -->
<div th:text="${message}">Greeting Message</div>

<!-- Conditional rendering -->
<span th:if="${type == 'Friendly'}">FriendlyGreetingService</span>

<!-- Concatenation -->
<code th:text="'/greetings/' + ${type.toLowerCase()}">endpoint</code>
```

### 4. **View Resolution**
- Spring Boot auto-configures Thymeleaf
- Templates are in `src/main/resources/templates/`
- File extension `.html` is automatically appended
- Return value `"greeting"` → resolves to `templates/greeting.html`

## How to Test

### 1. Start the Application
```bash
mvn spring-boot:run
```

### 2. Access the Web UI
Open browser: `http://localhost:8080/greetings`

### 3. Try the Endpoints
**Friendly Greeting:**
- Click "😊 Friendly Greeting" button
- Uses `FriendlyGreetingService` (@Primary bean)

**Formal Greeting:**
- Click "🎩 Formal Greeting" button  
- Uses `FormalGreetingService` (@Qualifier bean)

### 4. Compare with REST API
**REST Endpoints (return JSON):**
```bash
curl http://localhost:8080/api/greetings?name=John
# {"message":"Hey John!","type":"friendly","timestamp":1234567890}

curl http://localhost:8080/api/greetings/formal?name=John
# {"message":"Good day, John.","type":"formal","timestamp":1234567890}
```

**MVC Endpoints (return HTML):**
- Browser: `http://localhost:8080/greetings/friendly?name=John`
- Browser: `http://localhost:8080/greetings/formal?name=John`

## Interview-Ready Concepts

### Key Points to Remember

1. **@Controller vs @RestController**
   - `@Controller` → returns view names (HTML)
   - `@RestController` = `@Controller` + `@ResponseBody` → returns data (JSON)

2. **Model Object**
   - Used to pass data from controller to view
   - Alternative: `ModelAndView`, `@ModelAttribute`

3. **Thymeleaf Advantages**
   - Server-side rendering (SEO-friendly)
   - Security (no XSS vulnerabilities from server)
   - Natural templates (valid HTML)
   - Spring integration (@{} for URLs, ${} for variables)

4. **View Resolver**
   - Thymeleaf auto-configuration in Spring Boot
   - Prefix: `classpath:/templates/`
   - Suffix: `.html`
   - `"greeting"` → `/templates/greeting.html`

5. **When to Use What**
   - **MVC (@Controller)**: Traditional web apps, server-side rendering
   - **REST (@RestController)**: APIs, SPAs, mobile apps, microservices

6. **Dependency Injection in Controllers**
   - Both controllers use constructor injection
   - Both inject the same service interfaces
   - Same business logic, different presentation layer

## Architecture

```
┌─────────────────────────────────────────────┐
│              Browser / Client               │
└─────────────┬───────────────────────────────┘
              │
              ├─ /greetings         (HTML request)
              │  ↓
              │  ┌──────────────────────────────┐
              │  │   GreetingViewController     │
              │  │         @Controller           │
              │  │   returns view name: String  │
              │  └────────────┬─────────────────┘
              │               │
              │               ↓
              │  ┌──────────────────────────────┐
              │  │    Thymeleaf Template        │
              │  │    greeting-result.html      │
              │  │    (Server-side rendering)   │
              │  └────────────┬─────────────────┘
              │               │
              │               ↓ HTML response
              │
              └─ /api/greetings    (JSON request)
                 ↓
                 ┌──────────────────────────────┐
                 │    GreetingController        │
                 │      @RestController         │
                 │   returns DTO: JSON object   │
                 └────────────┬─────────────────┘
                              │
                              ↓ JSON response
```

## Summary

✅ **REST API** (`@RestController`) - Already existed
- Returns JSON (GreetingResponse DTO)
- For API consumers

✅ **Spring MVC** (`@Controller`) - **NEWLY ADDED**
- Returns HTML (Thymeleaf templates)
- For web browser users
- Same business logic, different presentation

This demonstrates the flexibility of Spring to serve both traditional web apps (MVC) and modern APIs (REST) using the same service layer! 🚀

