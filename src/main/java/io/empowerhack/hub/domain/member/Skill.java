package io.empowerhack.hub.domain.member;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String uid = UUID.randomUUID().toString();

    @NotNull
    @Size(min = 3, max = 32)
    private String name;

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }
}
