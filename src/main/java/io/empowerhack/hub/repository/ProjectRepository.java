package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select distinct project from Project project left join fetch project.partners left join fetch project.tags")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.partners left join fetch project.tags where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

}
