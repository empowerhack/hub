package io.empowerhack.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.empowerhack.hub.domain.Skill;
import io.empowerhack.hub.service.SkillService;
import io.empowerhack.hub.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);
        
    @Inject
    private SkillService skillService;
    
    /**
     * POST  /skills : Create a new skill.
     *
     * @param skill the skill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("skill", "idexists", "A new skill cannot already have an ID")).body(null);
        }
        Skill result = skillService.save(skill);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("skill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills : Updates an existing skill.
     *
     * @param skill the skill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skill,
     * or with status 400 (Bad Request) if the skill is not valid,
     * or with status 500 (Internal Server Error) if the skill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skill);
        if (skill.getId() == null) {
            return createSkill(skill);
        }
        Skill result = skillService.save(skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("skill", skill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills : get all the skills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skills in body
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Skill> getAllSkills() {
        log.debug("REST request to get all Skills");
        return skillService.findAll();
    }

    /**
     * GET  /skills/:id : get the "id" skill.
     *
     * @param id the id of the skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skill, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/skills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Skill skill = skillService.findOne(id);
        return Optional.ofNullable(skill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /skills/:id : delete the "id" skill.
     *
     * @param id the id of the skill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/skills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("skill", id.toString())).build();
    }

    /**
     * SEARCH  /_search/skills?query=:query : search for the skill corresponding
     * to the query.
     *
     * @param query the query of the skill search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Skill> searchSkills(@RequestParam String query) {
        log.debug("REST request to search Skills for query {}", query);
        return skillService.search(query);
    }

}
