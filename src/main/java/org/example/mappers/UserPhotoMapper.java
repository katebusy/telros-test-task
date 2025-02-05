package org.example.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.enities.UserPhotos;
import org.example.models.UserPhotosDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPhotoMapper {
    public static UserPhotos ToEntity(UserPhotosDTO dto) {
        if (dto == null) {
            return null;
        }
        return UserPhotos.builder()
                .id(dto.getId())
                .filename(dto.getFilename())
                .build();
    }

    public static UserPhotosDTO ToDTO(UserPhotos entity) {
        if (entity == null) {
            return null;
        }
        return UserPhotosDTO.builder()
                .id(entity.getId())
                .filename(entity.getFilename())
                .build();
    }
}
