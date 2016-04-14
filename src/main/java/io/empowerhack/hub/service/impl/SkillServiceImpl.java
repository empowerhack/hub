package io.empowerhack.hub.service.impl;

import io.empowerhack.hub.service.SkillService;
import io.empowerhack.hub.domain.Skill;
import io.empowerhack.hub.repository.SkillRepository;
import io.empowerhack.hub.repository.search.SkillSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Skill.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService{

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    @Inject
    private SkillRepository skillRepository;

    @Inject
    private SkillSearchRepository skillSearchRepository;

    /**
     * Save a skill.
     *
     * @param skill the entity to save
     * @return the persisted entity
     */
    public Skill save(Skill skill) {
        log.debug("Request to save Skill : {}", skill);
        Skill result = skillRepository.save(skill);
        skillSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the skills.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        log.debug("Request to get all Skills");
        List<Skill> result = skillRepository.findAll();
        return result;
    }

    /**
     *  Get one skill by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Skill findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        Skill skill = skillRepository.findOne(id);
        return skill;
    }

    /**
     *  Delete the  skill by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.delete(id);
        skillSearchRepository.delete(id);
    }

    /**
     * Search for the skill corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Skill> search(String query) {
        log.debug("Request to search Skills for query {}", query);
        return StreamSupport
            .stream(skillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the skills.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Skill> findByUserIsCurrentUser() {
        log.debug("Request to get all Skills");
        List<Skill> result = skillRepository.findByUserIsCurrentUser();
        return result;
    }
}
