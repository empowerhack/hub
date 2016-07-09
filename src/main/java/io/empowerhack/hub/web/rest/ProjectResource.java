package io.empowerhack.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.empowerhack.hub.domain.Project;
import io.empowerhack.hub.security.AuthoritiesConstants;
import io.empowerhack.hub.service.ProjectService;
import io.empowerhack.hub.web.rest.util.HeaderUtil;
import io.empowerhack.hub.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    @Inject
    private ProjectService projectService;

    /**
     * POST  /projects : Create a new project.
     *
     * @param project the project to create
     * @return the ResponseEntity with status 201 (Created) and with body the new project, or with status 400 (Bad Request) if the project has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to save Project : {}", project);
        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("project", "idexists", "A new project cannot already have an ID")).body(null);
        }
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("project", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing project.
     *
     * @param project the project to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated project,
     * or with status 400 (Bad Request) if the project is not valid,
     * or with status 500 (Internal Server Error) if the project couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        if (project.getId() == null) {
            return createProject(project);
        }
        Project result = projectService.save(project);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("project", project.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({AuthoritiesConstants.ANONYMOUS, AuthoritiesConstants.USER})
    public ResponseEntity<List<Project>> getAllProjects(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Projects");
        Page<Project> page = projectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" project.
     *
     * @param id the id of the project to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the project, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({AuthoritiesConstants.ANONYMOUS, AuthoritiesConstants.USER})
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectService.findOne(id);
        return Optional.ofNullable(project)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projects/:id : delete the "id" project.
     *
     * @param id the id of the project to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("project", id.toString())).build();
    }

    /**
     * SEARCH  /_search/projects?query=:query : search for the project corresponding
     * to the query.
     *
     * @param query the query of the project search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({AuthoritiesConstants.ANONYMOUS, AuthoritiesConstants.USER})
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Projects for query {}", query);
        Page<Project> page = projectService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
