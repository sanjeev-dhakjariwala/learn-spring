package com.sanjeev.learnspring.aop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for UserService with AOP aspects.
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertThat(userService).isNotNull();
    }

    @Test
    void getAllUsers_shouldReturnUsers() {
        // @TrackExecutionTime will measure this
        List<String> users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users).contains("Alice", "Bob", "Charlie");
    }

    @Test
    void createUser_shouldAddNewUser() {
        // @Auditable will log this action
        int initialCount = userService.getUserCount();
        String result = userService.createUser("TestUser");

        assertThat(result).contains("User created: TestUser");
        assertThat(userService.getUserCount()).isEqualTo(initialCount + 1);
    }

    @Test
    void createUser_shouldThrowExceptionForEmptyUsername() {
        // @AfterThrowing will be triggered
        assertThatThrownBy(() -> userService.createUser(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be empty");
    }

    @Test
    void findUser_shouldReturnExistingUser() {
        // Both @Auditable and @TrackExecutionTime
        String user = userService.findUser("Alice");
        assertThat(user).isEqualTo("Alice");
    }

    @Test
    void findUser_shouldThrowExceptionForNonExistentUser() {
        assertThatThrownBy(() -> userService.findUser("NonExistent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void deleteUser_shouldRemoveUser() {
        // @Auditable will log deletion
        userService.createUser("ToDelete");
        boolean deleted = userService.deleteUser("ToDelete");

        assertThat(deleted).isTrue();
        assertThatThrownBy(() -> userService.findUser("ToDelete"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getUserCount_shouldReturnCorrectCount() {
        int count = userService.getUserCount();
        assertThat(count).isPositive();
    }
}

