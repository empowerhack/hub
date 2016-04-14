package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.Skill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Skill entity.
 */
public interface SkillRepository extends JpaRepository<Skill,Long> {

    @Query("select skill from Skill skill where skill.user.login = ?#{principal.username}")
    List<Skill> findByUserIsCurrentUser();

}
