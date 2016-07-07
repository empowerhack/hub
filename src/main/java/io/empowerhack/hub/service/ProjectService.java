package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Project.
 */
public interface ProjectService {

    /**
     * Save a project.
     * 
     * @param project the entity to save
     * @return the persisted entity
     */
    Project save(Project project);

    /**
     *  Get all the projects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Project> findAll(Pageable pageable);

    /**
     *  Get the "id" project.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Project findOne(Long id);

    /**
     *  Delete the "id" project.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the project corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Project> search(String query, Pageable pageable);
}
