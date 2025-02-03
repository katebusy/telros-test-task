package org.example.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserContactsDTO {
    private UUID id;
    private String email;
    private String number;
}
