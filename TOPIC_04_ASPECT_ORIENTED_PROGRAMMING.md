# Topic 04: Aspect-Oriented Programming (AOP)

## Overview
Spring AOP provides a way to add cross-cutting concerns (like logging, security, transactions) to your application without modifying the business logic code.

## Key Concepts

### 1. **Aspect**
- A modularization of a concern that cuts across multiple classes
- Defined using `@Aspect` annotation
- Contains advice and pointcuts

### 2. **Join Point**
- A point during program execution (method execution, exception handling, etc.)
- In Spring AOP, always represents a method execution

### 3. **Advice**
- Action taken by an aspect at a particular join point
- Types: `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`, `@Around`

### 4. **Pointcut**
- Predicate that matches join points
- Defined using expressions: `execution()`, `within()`, `@annotation()`, etc.

### 5. **Weaving**
- Process of linking aspects with other application types
- Spring AOP uses runtime weaving (proxy-based)

## Advice Types

| Advice | Description | Use Case |
|--------|-------------|----------|
| @Before | Runs before method execution | Validation, logging |
| @After | Runs after method (finally) | Cleanup, logging |
| @AfterReturning | Runs after successful return | Result processing |
| @AfterThrowing | Runs after exception thrown | Error handling |
| @Around | Wraps method execution | Performance monitoring, transactions |

## Pointcut Expressions

```java
execution(modifiers? return-type declaring-type? method-name(params) throws?)
@annotation(com.example.CustomAnnotation)
within(com.example.service..*)
args(String, ..)
```

## Key Interview Points

1. **Proxy Pattern**: Spring AOP uses JDK dynamic proxies or CGLIB
2. **Limitations**: Only works with Spring-managed beans, only method-level
3. **@Around vs Others**: @Around is most powerful, can control method execution
4. **ProceedingJoinPoint**: Only available in @Around advice
5. **Order**: Use `@Order` to control aspect execution order
6. **Performance**: AOP adds overhead, use judiciously

## This Example Demonstrates

- `@Before` for pre-method logging and validation
- `@AfterReturning` for result logging and processing
- `@AfterThrowing` for exception handling and logging
- `@Around` for performance monitoring and method interception
- Custom annotations for targeted pointcuts
- Multiple aspects with `@Order`
- Method execution tracking and metrics

## Testing

All AOP aspects are automatically applied to Spring-managed beans.
Check logs to see aspects in action when calling service methods.

