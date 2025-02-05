package org.example.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.enities.UserDetails;
import org.example.mappers.UserDetailsMapper;
import org.example.models.UserDetailsDTO;
import org.example.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetailsDTO getUserDetails(UUID id) {
        return repository.findById(id)
                .map(UserDetailsMapper::ToDTO)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with this id: %s".formatted(id)));
    }

    @Override
    @Transactional
    public UserDetailsDTO updateUserDetails(UUID id, UserDetailsDTO userDetailsDTO) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("There is no user with this id: %s".formatted(id));
        }
        UserDetails userDetails = UserDetailsMapper.ToEntity(userDetailsDTO);
        userDetails.setId(id);
        UserDetails saved = repository.saveAndFlush(userDetails);
        return UserDetailsMapper.ToDTO(saved);
    }
}
