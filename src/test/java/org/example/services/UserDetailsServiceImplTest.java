package org.example.services;


import org.example.TestUtils;
import org.example.enities.UserDetails;
import org.example.enities.UserPhotos;
import org.example.models.UserDetailsDTO;
import org.example.repositories.UserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig({
        UserDetailsServiceImpl.class
})
@MockBean(UserDetailsRepository.class)
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsService service;
    @Autowired
    private UserDetailsRepository detailsRepository;
    private static final UUID ID = UUID.randomUUID();
    private static final String SURNAME = "Иванов";
    private static final String NAME = "Иван";
    private static final String SECOND_NAME = "Иванович";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1999, 1, 1);
    private static final UserPhotos PHOTO = TestUtils.buildUserPhoto();

    @Test
    void getUser() {
        UserDetails userDetails = UserDetails.builder()
                .id(ID)
                .surname(SURNAME)
                .name(NAME)
                .secondName(SECOND_NAME)
                .birthDate(BIRTH_DATE)
                .userPhotos(PHOTO)
                .build();
        when(detailsRepository.findById(any())).thenReturn(Optional.of(userDetails));

        UserDetailsDTO userDetailsDTO = service.getUserDetails(ID);

        assertNotNull(userDetailsDTO);
        assertEquals(userDetailsDTO.getId(), userDetails.getId());
        assertEquals(userDetailsDTO.getSurname(), userDetails.getSurname());
        assertEquals(userDetailsDTO.getName(), userDetails.getName());
        assertEquals(userDetailsDTO.getSecondName(), userDetails.getSecondName());
        assertEquals(userDetailsDTO.getBirthDate(), userDetails.getBirthDate());
        assertEquals(userDetailsDTO.getUserPhotos(), userDetails.getUserPhotos());

        verify(detailsRepository).findById(ID);
        verifyNoMoreInteractions(detailsRepository);
    }

    @Test
    void updateUser() {
        when(detailsRepository.saveAndFlush(any())).thenAnswer(invocation -> {
            UserDetails userDetails = invocation.getArgument(0, UserDetails.class);
            return userDetails;
        });
        when(detailsRepository.existsById(any())).thenReturn(true);

        UserDetailsDTO userDetails = UserDetailsDTO.builder()
                .id(ID)
                .surname(SURNAME)
                .name(NAME)
                .secondName(SECOND_NAME)
                .birthDate(BIRTH_DATE)
                .userPhotos(PHOTO)
                .build();
        UserDetailsDTO updated = service.updateUserDetails(ID, userDetails);
        assertNotNull(updated);
        assertEquals(ID, updated.getId());
        assertEquals(SURNAME, updated.getSurname());
        assertEquals(NAME, updated.getName());
        assertEquals(SECOND_NAME, updated.getSecondName());
        assertEquals(BIRTH_DATE, updated.getBirthDate());
        assertEquals(PHOTO, updated.getUserPhotos());


        verify(detailsRepository).existsById(ID);
        verify(detailsRepository).saveAndFlush(any());
        verifyNoMoreInteractions(detailsRepository);
    }
}