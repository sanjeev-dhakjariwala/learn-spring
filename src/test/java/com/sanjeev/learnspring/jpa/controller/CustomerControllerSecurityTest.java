package com.sanjeev.learnspring.jpa.controller;

import com.sanjeev.learnspring.jpa.dto.CustomerResponseDTO;
import com.sanjeev.learnspring.jpa.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void userRoleCanAccessList() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void anonymousCannotAccessApi() throws Exception {
        mockMvc.perform(get("/api/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void userRoleForbiddenOnAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/customers/email/test@example.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminCanAccessAdminEndpoint() throws Exception {
        CustomerResponseDTO response = new CustomerResponseDTO();
        Mockito.when(customerService.getCustomerByEmail("test@example.com"))
                .thenReturn(response);

        mockMvc.perform(get("/api/customers/email/test@example.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

