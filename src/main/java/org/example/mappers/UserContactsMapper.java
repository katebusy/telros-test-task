package org.example.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.enities.UserContacts;
import org.example.models.UserContactsDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContactsMapper {

    public static UserContacts ToEntity(UserContactsDTO dto) {
        if (dto == null) {
            return null;
        }
        return UserContacts.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .number(dto.getNumber())
                .build();
    }

    public static UserContactsDTO ToDTO(UserContacts entity) {
        if (entity == null) {
            return null;
        }
        return UserContactsDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .number(entity.getNumber())
                .build();
    }
}
