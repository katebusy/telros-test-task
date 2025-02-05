package org.example.services;


import lombok.RequiredArgsConstructor;
import org.example.enities.UserDetails;
import org.example.enities.UserPhotos;
import org.example.mappers.UserPhotoMapper;
import org.example.minio_services.MinioStorageService;
import org.example.models.UserPhotosDTO;
import org.example.repositories.UserDetailsRepository;
import org.example.repositories.UserPhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPhotosServiceImpl implements UserPhotosService {
    @Autowired
    private MinioStorageService minioStorageService;
    @Autowired
    private UserPhotosRepository photosRepository;
    @Autowired
    private UserDetailsRepository detailsRepository;

    @Override
    @Transactional
    public UserPhotosDTO saveUserPhoto(MultipartFile photoFile, UUID userId) {
        if (photoFile == null || photoFile.isEmpty()) {
            throw new UnsupportedOperationException("File is empty!");
        }
        if (!detailsRepository.existsById(userId)) {
            throw new UnsupportedOperationException(String.format("There is no user with %s id", userId));
        }

        String filePath = generatePath(photoFile);
        saveToMinio(photoFile, filePath);

        UserPhotos photo = UserPhotos.builder()
                .filename(filePath)
                .build();

        UserPhotos savedToPostgresql = saveToPostgresql(photo, userId);

        return UserPhotoMapper.ToDTO(savedToPostgresql);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPhotosDTO getUserPhotoInto(UUID id) {
        UserPhotos photo = photosRepository.getReferenceById(id);
        return UserPhotoMapper.ToDTO(photo);
    }

    @Override
    @Transactional(readOnly = true)
    public String getPhotoLink(UUID photoId) {
        String path = photosRepository.getReferenceById(photoId).getFilename();
        try {
            return minioStorageService.getPresignedLink(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public UserPhotosDTO updateUserPhoto(MultipartFile newPhoto, UUID userId) {
        if (newPhoto == null || newPhoto.isEmpty()) {
            throw new UnsupportedOperationException("File is empty!");
        }
        UserPhotos userPhoto = detailsRepository.getReferenceById(userId).getUserPhotos();
        String path = generatePath(newPhoto);

        deleteFromMinio(userPhoto.getFilename());
        saveToMinio(newPhoto, path);
        userPhoto.setFilename(path);
        UserPhotos updatedPhoto = saveToPostgresql(userPhoto, userId);

        return UserPhotoMapper.ToDTO(updatedPhoto);
    }

    @Override
    @Transactional
    public void deleteUserPhoto(UUID photoId) {
        String path = photosRepository.getReferenceById(photoId).getFilename();
        deleteFromMinio(path);
        photosRepository.deleteById(photoId);
    }

    private void saveToMinio(MultipartFile file, String path) {
        try {
            minioStorageService.save(file, path);
        } catch (Exception e) {
            System.out.println("Exception in saving photo to Minio");
            throw new RuntimeException(e);
        }
    }

    private UserPhotos saveToPostgresql(UserPhotos photo, UUID userId) {
        UserPhotos saved = photosRepository.saveAndFlush(photo);
        photo.setId(saved.getId());
        UserDetails existingDetails = detailsRepository.getReferenceById(userId);
        existingDetails.setUserPhotos(photo);
        detailsRepository.save(existingDetails);
        return saved;
    }

    private String generatePath(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String subFolder = String.format("/%s/", fileType.substring(1));
        return subFolder + UUID.randomUUID() + fileType;
    }

    private void deleteFromMinio(String path) {
        try {
            minioStorageService.delete(path);
        } catch (Exception e) {
            System.out.println("Exception in deleting photo from Minio");
            throw new RuntimeException(e);
        }
    }

}
