package com.ke.task.jsonClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ke.task.entities.UserEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean marketingConsent;

    public UserResponse(UserEntity user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.marketingConsent = user.getMarketingConsent();
        this.email = this.marketingConsent? user.getEmail() : null;
    }
}
