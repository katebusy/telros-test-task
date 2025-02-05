package org.example.services;


import org.example.models.UserPhotosDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserPhotosService {
    UserPhotosDTO saveUserPhoto(MultipartFile file, UUID userId);

    UserPhotosDTO getUserPhotoInto(UUID id);
    String getPhotoLink(UUID photoId);

    UserPhotosDTO updateUserPhoto(MultipartFile newPhoto, UUID userId);

    void deleteUserPhoto(UUID id);
}
