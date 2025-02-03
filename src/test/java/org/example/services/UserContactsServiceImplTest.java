package org.example.services;

import org.example.enities.UserContacts;
import org.example.models.UserContactsDTO;
import org.example.repositories.UserContactsRepository;
import org.example.repositories.UserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig({
        UserContactsServiceImpl.class
})
@MockBean(UserContactsRepository.class)
@MockBean(UserDetailsRepository.class)
class UserContactsServiceImplTest {
    @Autowired
    private UserContactsRepository contactsRepository;
    @Autowired
    private UserDetailsRepository detailsRepository;
    @Autowired
    private UserContactsService service;
    private static final UUID ID = UUID.randomUUID();
    private static final String EMAIL = "any@email.com";
    private static final String NUMBER = "9(999)-999-99-99";


    @Test
    void createUser() {
        when(contactsRepository.save(any())).thenAnswer(invocation -> {
            UserContacts userContacts = invocation.getArgument(0, UserContacts.class);
            userContacts.setId(ID);
            return userContacts;
        });

        UserContactsDTO userContacts = UserContactsDTO.builder()
                .email(EMAIL)
                .number(NUMBER)
                .build();
        UserContactsDTO saved = service.createUser(userContacts);
        assertNotNull(saved);
        assertEquals(ID, saved.getId());
        assertEquals(EMAIL, saved.getEmail());
        assertEquals(NUMBER, saved.getNumber());

        verify(contactsRepository).save(any());
        verifyNoMoreInteractions(contactsRepository);
        verify(detailsRepository).save(any());
        verifyNoMoreInteractions(detailsRepository);
    }

    @Test
    void getUser() {
        UserContacts userContacts = UserContacts.builder()
                .id(ID)
                .email(EMAIL)
                .number(NUMBER)
                .build();
        when(contactsRepository.findById(any())).thenReturn(Optional.of(userContacts));

        UserContactsDTO userContactsDTO = service.getUser(ID);

        assertNotNull(userContactsDTO);
        assertEquals(userContactsDTO.getId(), userContacts.getId());
        assertEquals(userContactsDTO.getEmail(), userContacts.getEmail());
        assertEquals(userContactsDTO.getNumber(), userContacts.getNumber());

        verify(contactsRepository).findById(ID);
        verifyNoMoreInteractions(contactsRepository);
    }

    @Test
    void updateUser() {

        when(contactsRepository.saveAndFlush(any())).thenAnswer(invocation -> {
            UserContacts userContacts = invocation.getArgument(0, UserContacts.class);
            return userContacts;
        });
        when(contactsRepository.existsById(any())).thenReturn(true);

        UserContactsDTO userContacts = UserContactsDTO.builder()
                .id(ID)
                .email(EMAIL)
                .number(NUMBER)
                .build();
        UserContactsDTO updated = service.updateUser(ID, userContacts);
        assertNotNull(updated);
        assertEquals(ID, updated.getId());
        assertEquals(EMAIL, updated.getEmail());
        assertEquals(NUMBER, updated.getNumber());

        verify(contactsRepository).existsById(ID);
        verify(contactsRepository).saveAndFlush(any());
        verifyNoMoreInteractions(contactsRepository);
    }

    @Test
    void deleteUser() {
        when(contactsRepository.existsById(any())).thenReturn(true);
        doNothing().when(contactsRepository).deleteById(any());

        service.deleteUser(ID);

        verify(contactsRepository).existsById(ID);
        verify(contactsRepository).deleteById(ID);
        verifyNoMoreInteractions(contactsRepository);
    }
}