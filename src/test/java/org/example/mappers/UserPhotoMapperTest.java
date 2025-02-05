package org.example.mappers;


import org.example.enities.UserPhotos;
import org.example.models.UserPhotosDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserPhotoMapperTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String FILENAME = "/folder/file.extention";

    @Test
    void toEntity() {
        UserPhotosDTO dto = UserPhotosDTO.builder()
                .id(ID)
                .filename(FILENAME)
                .build();
        UserPhotos expectedEntity = UserPhotos.builder()
                .id(ID)
                .filename(FILENAME)
                .build();
        UserPhotos actualEntity = UserPhotoMapper.ToEntity(dto);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void toDTO() {
        UserPhotos entity = UserPhotos.builder()
                .id(ID)
                .filename(FILENAME)
                .build();
        UserPhotosDTO expectedDTO = UserPhotosDTO.builder()
                .id(ID)
                .filename(FILENAME)
                .build();
        UserPhotosDTO actualDTO = UserPhotoMapper.ToDTO(entity);
        assertEquals(expectedDTO, actualDTO);
    }
}