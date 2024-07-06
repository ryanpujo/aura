package com.praim.inventory.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.praim.inventory.user.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotNull(message = "first name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "last name is required")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "email is required")
    @Email(message = "email is not well formatted")
    private String email;

    @NotNull(message = "password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Builder.Default
    private Role role = Role.ROLE_USER;
}
