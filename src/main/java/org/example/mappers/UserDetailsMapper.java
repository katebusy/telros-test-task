package org.example.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.enities.UserDetails;
import org.example.models.UserDetailsDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailsMapper {
    public static UserDetails ToEntity(UserDetailsDTO dto) {
        if (dto == null) {
            return null;
        }
        return UserDetails.builder()
                .id(dto.getId())
                .surname(dto.getSurname())
                .name(dto.getName())
                .secondName(dto.getSecondName())
                .birthDate(dto.getBirthDate())
                .userPhotos(dto.getUserPhotos())
                .build();
    }

    public static UserDetailsDTO ToDTO(UserDetails entity) {
        if (entity == null) {
            return null;
        }
        return UserDetailsDTO.builder()
                .id(entity.getId())
                .surname(entity.getSurname())
                .name(entity.getName())
                .secondName(entity.getSecondName())
                .birthDate(entity.getBirthDate())
                .userPhotos(entity.getUserPhotos())
                .build();
    }
}
