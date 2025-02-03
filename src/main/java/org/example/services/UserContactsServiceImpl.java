package org.example.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.enities.UserContacts;
import org.example.enities.UserDetails;
import org.example.mappers.UserContactsMapper;
import org.example.models.UserContactsDTO;
import org.example.repositories.UserContactsRepository;
import org.example.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserContactsServiceImpl implements UserContactsService {
    @Autowired
    private UserContactsRepository contactsRepository;
    @Autowired
    private UserDetailsRepository detailsRepository;

    @Override
    public UserContactsDTO createUser(UserContactsDTO userContactsDTO) {
        UserContacts userContacts = UserContactsMapper.ToEntity(userContactsDTO);
        UserContacts saved = contactsRepository.save(userContacts);
        UserDetails userDetails = new UserDetails();
        userDetails.setId(userContacts.getId());
        detailsRepository.save(userDetails);
        return UserContactsMapper.ToDTO(saved);
    }

    @Override
    public UserContactsDTO getUser(UUID id) {
        return contactsRepository.findById(id)
                .map(UserContactsMapper::ToDTO)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with this id: %s".formatted(id)));
    }

    @Override
    public UserContactsDTO updateUser(UUID id, UserContactsDTO userContactsDTO) {
        if (!contactsRepository.existsById(id)) {
            throw new EntityNotFoundException("There is no user with this id: %s".formatted(id));
        }
        UserContacts userContacts = UserContactsMapper.ToEntity(userContactsDTO);
        userContacts.setId(id);
        UserContacts saved = contactsRepository.saveAndFlush(userContacts);
        return UserContactsMapper.ToDTO(saved);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!contactsRepository.existsById(id)) {
            throw new EntityNotFoundException("There is no user with this id: %s".formatted(id));
        }
        contactsRepository.deleteById(id);
    }
}
