package org.example.services;

import org.example.models.UserDetailsDTO;

import java.util.UUID;

public interface UserDetailsService {
    UserDetailsDTO getUserDetails(UUID id);
    UserDetailsDTO updateUserDetails(UUID id, UserDetailsDTO userDetailsDTO);
}
