package org.example.repositories;

import org.example.enities.UserContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserContactsRepository extends JpaRepository<UserContacts, UUID> {
}
