package org.example.mappers;

import org.example.enities.UserContacts;
import org.example.models.UserContactsDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserContactsMapperTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String EMAIL = "any@email.com";
    private static final String NUMBER = "9(999)-999-99-99";

    @Test
    void toEntity() {
        UserContactsDTO dto = UserContactsDTO.builder()
                .id(ID)
                .email(EMAIL)
                .number(NUMBER)
                .build();
        UserContacts expectedEntity = UserContacts.builder()
                .id(ID)
                .email(EMAIL)
                .number(NUMBER)
                .build();
        UserContacts actualEntity = UserContactsMapper.ToEntity(dto);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void toDTO() {
        UserContacts entity = UserContacts.builder()
                .id(ID)
                .email(EMAIL)
                .number(NUMBER)
                .build();
        UserContactsDTO expectedDTO = UserContactsDTO.builder()
                .id(ID)
                .email(EMAIL)
                .number(NUMBER)
                .build();
        UserContactsDTO actualDTO = UserContactsMapper.ToDTO(entity);
        assertEquals(expectedDTO, actualDTO);
    }
}