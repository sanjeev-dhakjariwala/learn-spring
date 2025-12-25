# Topic 02: Bean Scopes and Lifecycle

## Overview
Spring manages the lifecycle of beans and provides different scopes that control how and when beans are created.

## Bean Scopes

### 1. **Singleton (Default)**
- Only one instance per Spring IoC container
- Bean is created when the application context starts
- Same instance is shared across all requests

### 2. **Prototype**
- New instance created every time the bean is requested
- Spring doesn't manage the complete lifecycle (no destruction callbacks)
- Useful for stateful beans

### 3. **Request** (Web-only)
- One instance per HTTP request

### 4. **Session** (Web-only)
- One instance per HTTP session

### 5. **Application** (Web-only)
- One instance per ServletContext

## Bean Lifecycle Callbacks

### @PostConstruct
- Executed after dependency injection is complete
- Used for initialization logic
- Called only once per bean instance

### @PreDestroy
- Executed before the bean is destroyed
- Used for cleanup logic (closing connections, releasing resources)
- **NOT called for prototype-scoped beans**

## Key Interview Points

1. **Singleton vs Prototype**: Understand when to use each scope
2. **Lifecycle hooks**: @PostConstruct runs after DI, @PreDestroy before destruction
3. **Prototype caveat**: Spring doesn't call @PreDestroy on prototype beans
4. **Stateless vs Stateful**: Singleton for stateless, Prototype for stateful beans
5. **Thread safety**: Singleton beans should be stateless or thread-safe

## This Example Demonstrates

- `SingletonService`: Default singleton scope with full lifecycle
- `PrototypeService`: Prototype scope creates new instances
- `PrototypeTrackerService`: Singleton that tracks prototype instances
- `LifecycleConfig`: Configuration class with lifecycle logs
- Lifecycle callbacks with @PostConstruct and @PreDestroy
- How prototype beans behave differently from singletons

## Testing

Run the application and observe:
1. When beans are created (startup vs on-demand)
2. How many instances exist (singleton vs prototype)
3. When lifecycle callbacks are triggered
4. That @PreDestroy is NOT called on prototype beans

