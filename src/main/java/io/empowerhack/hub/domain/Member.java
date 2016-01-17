package io.empowerhack.hub.domain;

import io.empowerhack.hub.domain.member.Interest;
import io.empowerhack.hub.domain.member.Skill;
import io.empowerhack.hub.domain.member.Social;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "username_idx", columnNames = "username"))
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String uid = UUID.randomUUID().toString(); // @TODO: replace with GitHub Uid

    @Size(min = 6, max = 255)
    private String name;

    @Email
    @Size(min = 6, max = 128)
    private String email;

    @Size(min = 1, max = 255)
    private String username;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer availability = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Skill> skills;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Interest> interests;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Social> socials;

    private boolean coreMember = false;
    private boolean keepPrivate = false;
    private String avatarUrl;

    private Date createdOn;
    private Date updatedOn = new Date();

    Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
