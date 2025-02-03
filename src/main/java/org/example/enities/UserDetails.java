package org.example.enities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Table(schema = "users", name = "details")
public class UserDetails {
    @Id
    private UUID id;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToOne
    @JoinColumn(name = "photo_id")
    private UserPhotos userPhotos;
}
