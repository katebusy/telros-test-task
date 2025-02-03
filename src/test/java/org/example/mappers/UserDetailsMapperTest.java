package org.example.mappers;

import org.example.TestUtils;
import org.example.enities.UserDetails;
import org.example.enities.UserPhotos;
import org.example.models.UserDetailsDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsMapperTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String SURNAME = "Иванов";
    private static final String NAME = "Иван";
    private static final String SECOND_NAME = "Иванович";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1999, 1, 1);
    private static final UserPhotos PHOTO = TestUtils.buildUserPhoto();

    @Test
    void toEntity() {
        UserDetailsDTO dto = UserDetailsDTO.builder()
                .id(ID)
                .surname(SURNAME)
                .name(NAME)
                .secondName(SECOND_NAME)
                .birthDate(BIRTH_DATE)
                .userPhotos(PHOTO)
                .build();
        UserDetails expectedEntity = UserDetails.builder()
                .id(ID)
                .surname(SURNAME)
                .name(NAME)
                .secondName(SECOND_NAME)
                .birthDate(BIRTH_DATE)
                .userPhotos(PHOTO)
                .build();
        UserDetails actualEntity = UserDetailsMapper.ToEntity(dto);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void toDTO() {
        UserDetails entity = UserDetails.builder()
                .id(ID)
                .surname(SURNAME)
                .name(NAME)
                .secondName(SECOND_NAME)
                .birthDate(BIRTH_DATE)
                .userPhotos(PHOTO)
                .build();
        UserDetailsDTO expectedDTO = UserDetailsDTO.builder()
                .id(ID)
                .surname(SURNAME)
                .name(NAME)
                .secondName(SECOND_NAME)
                .birthDate(BIRTH_DATE)
                .userPhotos(PHOTO)
                .build();
        UserDetailsDTO actualDTO = UserDetailsMapper.ToDTO(entity);
        assertEquals(expectedDTO, actualDTO);
    }
}