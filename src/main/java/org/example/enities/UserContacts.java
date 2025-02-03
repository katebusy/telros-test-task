package org.example.enities;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Table(schema = "users", name = "contacts")
public class UserContacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "email")
    private String email;
    @Column(name = "number")
    private String number;
}
