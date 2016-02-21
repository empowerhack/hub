package io.empowerhack.hub.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uid_idx", columnNames = {"uid"} ))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String uid;

    @Size(min = 5, max = 255)
    private String name;

    @Email
    private String email;

    @NotNull
    private boolean isPrivate = false;

    @Min(0)
    @Max(100)
    private Integer availability = 0;

    User() {
    }

    public User(String uid) {
        this.uid = uid;
    }
}
