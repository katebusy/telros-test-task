package org.example.repositories;


import org.example.enities.UserPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPhotosRepository extends JpaRepository<UserPhotos, UUID> {
}
