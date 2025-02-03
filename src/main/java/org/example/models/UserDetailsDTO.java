package org.example.models;


import lombok.*;
import org.example.enities.UserPhotos;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserDetailsDTO {
    private UUID id;
    private String surname;
    private String name;
    private String secondName;
    private LocalDate birthDate;
    private UserPhotos userPhotos;
}
