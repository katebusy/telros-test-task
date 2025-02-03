package org.example.services;

import org.example.models.UserContactsDTO;

import java.util.UUID;

public interface UserContactsService {
    UserContactsDTO createUser(UserContactsDTO userContactsDTO);

    UserContactsDTO getUser(UUID id);

    UserContactsDTO updateUser(UUID id, UserContactsDTO userContactsDTO);

    void deleteUser(UUID id);
}
