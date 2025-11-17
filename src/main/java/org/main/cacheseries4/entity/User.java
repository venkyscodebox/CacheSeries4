package org.main.cacheseries4.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String firstname;

    @NotNull
    @NotEmpty
    @NotBlank
    private String lastname;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    @Indexed(unique=true)
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min=8, max=30)
    private String password;

    @NotNull
    private boolean enabled;

    @NotNull
    private List<Role> roles;

    @NotNull
    @CreatedDate
    private Instant createdDate;

    @NotNull
    @LastModifiedDate
    private Instant lastModifiedDate;

    private User(String emailid, String password, List<Role> roles)
    {
        this.email=emailid;
        this.password=password;
        this.roles=roles;
    }
}
