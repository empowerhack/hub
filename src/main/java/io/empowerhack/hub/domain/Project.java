package io.empowerhack.hub.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "name_idx", columnNames = "name"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String uid = UUID.randomUUID().toString();

    @NotNull
    @Size(min = 6, max = 255)
    private String name;

    @NotNull
    @Size(min = 6, max = 255)
    private String description;

    private String github;

    private String website;

    private Date createdOn;
    private Date updatedOn = new Date();

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }
}
