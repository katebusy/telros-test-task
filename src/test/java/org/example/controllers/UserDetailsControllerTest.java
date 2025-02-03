package org.example.controllers;

import lombok.SneakyThrows;
import org.example.TestUtils;
import org.example.enities.UserPhotos;
import org.example.models.UserDetailsDTO;
import org.example.services.UserDetailsService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig({
        UserDetailsController.class
})
@MockBean(UserDetailsService.class)
@WebMvcTest
class UserDetailsControllerTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void getDetails() {
        UUID requestUUID = UUID.randomUUID();
        UserDetailsDTO response = TestUtils.buildUserDetailsDTO();
        response.setId(requestUUID);
        when(userDetailsService.getUserDetails(any())).thenReturn(response);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user-details/" + requestUUID)
                        .param("id", requestUUID.toString()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", Matchers.is(response.getId().toString())),
                        jsonPath("$.surname", Matchers.is(response.getSurname())),
                        jsonPath("$.name", Matchers.is(response.getName())),
                        jsonPath("$.secondName", Matchers.is(response.getSecondName())),
                        jsonPath("$.birthDate", Matchers.is(response.getBirthDate().toString())),
                        jsonPath("$.userPhotos", Matchers.is(response.getUserPhotos()))
                );
        assertEquals(requestUUID, response.getId());
        verify(userDetailsService).getUserDetails(requestUUID);
        verifyNoMoreInteractions(userDetailsService);
    }

    @Test
    @SneakyThrows
    void updateDetails() {
        UUID requestUUID = UUID.randomUUID();
        UserPhotos photo = TestUtils.buildUserPhoto();
        UserDetailsDTO request = TestUtils.buildUserDetailsDTO();
        request.setUserPhotos(photo);
        UserDetailsDTO response = TestUtils.buildUserDetailsDTO();
        response.setId(requestUUID);
        response.setUserPhotos(photo);
        when(userDetailsService.updateUserDetails(any(), any())).thenReturn(response);
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/user-details/" + requestUUID)
                        .param("id", requestUUID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.jsonStringify(request)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", Matchers.is(response.getId().toString())),
                        jsonPath("$.surname", Matchers.is(response.getSurname())),
                        jsonPath("$.name", Matchers.is(response.getName())),
                        jsonPath("$.secondName", Matchers.is(response.getSecondName())),
                        jsonPath("$.birthDate", Matchers.is(response.getBirthDate().toString())),
                        jsonPath("$.userPhotos.id", Matchers.is(response.getUserPhotos().getId().toString())),
                        jsonPath("$.userPhotos.filename", Matchers.is(response.getUserPhotos().getFilename()))
                );
        assertEquals(requestUUID, response.getId());
        assertEquals(request.getSurname(), response.getSurname());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getSecondName(), response.getSecondName());
        assertEquals(request.getBirthDate(), response.getBirthDate());
        assertEquals(request.getUserPhotos(), response.getUserPhotos());
        verify(userDetailsService).updateUserDetails(requestUUID, request);
        verifyNoMoreInteractions(userDetailsService);
    }
}