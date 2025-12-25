# Topic 03: Configuration and Property Management

## Overview
Spring Boot provides powerful mechanisms to externalize configuration and manage application properties across different environments.

## Key Concepts

### 1. **@Value Annotation**
- Inject values from properties files directly into fields
- Supports SpEL (Spring Expression Language)
- Can provide default values

### 2. **@ConfigurationProperties**
- Type-safe configuration binding
- Maps properties to POJO classes
- Validation support with JSR-303
- Better for grouped properties

### 3. **application.properties / application.yml**
- Default configuration files
- Can have profile-specific variants (application-dev.properties, etc.)

### 4. **@Profile**
- Environment-specific bean configuration
- Activate with `spring.profiles.active`
- Multiple profiles can be active simultaneously

### 5. **Property Sources**
- External configuration (command line, environment variables, etc.)
- Property hierarchy and precedence

## @Value vs @ConfigurationProperties

| Feature | @Value | @ConfigurationProperties |
|---------|--------|-------------------------|
| Type Safety | No | Yes |
| Validation | Limited | Full JSR-303 support |
| Relaxed Binding | No | Yes (kebab-case, camelCase) |
| Metadata Support | No | Yes (IDE autocomplete) |
| Use Case | Single properties | Grouped properties |

## Key Interview Points

1. **Property Precedence**: Command line > Environment variables > application.properties
2. **@ConfigurationProperties** requires `@EnableConfigurationProperties` or `@Component`
3. **Profiles**: Used for environment-specific configuration (dev, test, prod)
4. **Relaxed Binding**: my-property, myProperty, MY_PROPERTY all map to same field
5. **Validation**: Use `@Validated` with `@ConfigurationProperties`
6. **SpEL in @Value**: `@Value("#{systemProperties['user.name']}")`

## This Example Demonstrates

- `@Value` for simple property injection
- `@ConfigurationProperties` for type-safe configuration
- Custom configuration classes with validation
- Profile-specific beans (@Profile annotation)
- Environment abstraction for accessing properties
- Different property sources and their precedence

## Testing

Run with different profiles to see different configurations:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

