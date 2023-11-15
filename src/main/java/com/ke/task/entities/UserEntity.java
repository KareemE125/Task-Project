package com.ke.task.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user_tb")
public class UserEntity {

    // Unique identifier for the user
    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    // Email address is unique
    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "marketing_consent")
    private Boolean marketingConsent;

}
