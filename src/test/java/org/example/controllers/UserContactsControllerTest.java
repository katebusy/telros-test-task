package org.example.controllers;


import lombok.SneakyThrows;
import org.example.TestUtils;
import org.example.models.UserContactsDTO;
import org.example.services.UserContactsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig({
        UserContactsController.class
})
@MockBean(UserContactsService.class)
@WebMvcTest
class UserContactsControllerTest {
    @Autowired
    private UserContactsService userContactsService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createUser() {
        UserContactsDTO request = TestUtils.buildUserContactsDTO();
        UserContactsDTO response = TestUtils.buildUserContactsDTO();
        response.setId(UUID.randomUUID());
        when(userContactsService.createUser(any())).thenReturn(response);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/user-contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.jsonStringify(request)))
                .andExpect(status().isCreated())
                // нужно проверить хедер content-type: application-json
                .andExpectAll(
                        jsonPath("$.id", Matchers.is(response.getId().toString())),
                        jsonPath("$.email", Matchers.is(response.getEmail())),
                        jsonPath("$.number", Matchers.is(response.getNumber()))
                );
        verify(userContactsService).createUser(request);
        verifyNoMoreInteractions(userContactsService);
    }

    @Test
    @SneakyThrows
    void getUser() {
        UUID requestUUID = UUID.randomUUID();
        UserContactsDTO response = TestUtils.buildUserContactsDTO();
        response.setId(requestUUID);
        when(userContactsService.getUser(any())).thenReturn(response);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user-contacts/" + requestUUID)
                        .param("id", requestUUID.toString()))
                        .andExpect(status().isOk())
                        .andExpectAll(
                                jsonPath("$.id", Matchers.is(response.getId().toString())),
                                jsonPath("$.email", Matchers.is(response.getEmail())),
                                jsonPath("$.number", Matchers.is(response.getNumber()))
                        );
        assertEquals(requestUUID, response.getId());
        verify(userContactsService).getUser(requestUUID);
        verifyNoMoreInteractions(userContactsService);

    }

    @Test
    @SneakyThrows
    void updateUser() {
        UUID requestUUID = UUID.randomUUID();
        UserContactsDTO request = TestUtils.buildUserContactsDTO();
        UserContactsDTO response = TestUtils.buildUserContactsDTO();
        response.setId(requestUUID);
        when(userContactsService.updateUser(any(), any())).thenReturn(response);
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/user-contacts/" + requestUUID)
                        .param("id", requestUUID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.jsonStringify(request)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", Matchers.is(response.getId().toString())),
                        jsonPath("$.email", Matchers.is(response.getEmail())),
                        jsonPath("$.number", Matchers.is(response.getNumber()))
                );
        assertEquals(requestUUID, response.getId());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getNumber(), response.getNumber());
        verify(userContactsService).updateUser(requestUUID, request);
        verifyNoMoreInteractions(userContactsService);
    }

    @Test
    @SneakyThrows
    void deleteUser() {
        UUID requestUUID = UUID.randomUUID();
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/user-contacts/" + requestUUID)
                        .param("id", requestUUID.toString()))
                .andExpect(status().isOk());
        verify(userContactsService).deleteUser(requestUUID);
        verifyNoMoreInteractions(userContactsService);
    }
}