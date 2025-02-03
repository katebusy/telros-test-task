package org.example.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserPhotosDTO {
    private UUID id;
    private String filename;
}
