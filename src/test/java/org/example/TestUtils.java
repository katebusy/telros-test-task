package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.example.enities.UserPhotos;
import org.example.models.UserContactsDTO;
import org.example.models.UserDetailsDTO;
import org.example.models.UserPhotosDTO;

import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {
    public static UserContactsDTO buildUserContactsDTO() {
        return UserContactsDTO.builder()
                .email("any@mail.ru")
                .number("9(999)99-99-99")
                .build();
    }

    public static UserDetailsDTO buildUserDetailsDTO() {
        return UserDetailsDTO.builder()
                .surname("Иванов")
                .name("Иван")
                .secondName("Иванович")
                .birthDate(LocalDate.of(1999, 1, 1))
                .build();
    }

    public static UserPhotos buildUserPhoto() {
        return UserPhotos.builder()
                .id(UUID.randomUUID())
                .filename("/path")
                .build();
    }

    public static UserPhotosDTO buildUserPhotoDTO() {
        return UserPhotosDTO.builder()
                .id(UUID.randomUUID())
                .filename("/path")
                .build();
    }

    @SneakyThrows
    public static String jsonStringify(Object obj) {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(obj);
    }
}
