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
public class Calendar {

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

    @NotNull
    @Size(min = 3, max = 255)
    private String location;

    @NotNull
    private Date date = new Date();

    @NotNull
    private Integer duration = 60; // minutes

    private Date createdOn;
    private Date updatedOn = new Date();

    public Calendar() {
    }

    public Calendar(String name) {
        this.name = name;
    }
}
