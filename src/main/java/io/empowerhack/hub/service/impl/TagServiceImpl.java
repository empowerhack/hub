package io.empowerhack.hub.service.impl;

import io.empowerhack.hub.service.TagService;
import io.empowerhack.hub.domain.Tag;
import io.empowerhack.hub.repository.TagRepository;
import io.empowerhack.hub.repository.search.TagSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tag.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService{

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);
    
    @Inject
    private TagRepository tagRepository;
    
    @Inject
    private TagSearchRepository tagSearchRepository;
    
    /**
     * Save a tag.
     * 
     * @param tag the entity to save
     * @return the persisted entity
     */
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        Tag result = tagRepository.save(tag);
        tagSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tags.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tag> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        Page<Tag> result = tagRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tag findOne(Long id) {
        log.debug("Request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        return tag;
    }

    /**
     *  Delete the  tag by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.delete(id);
        tagSearchRepository.delete(id);
    }

    /**
     * Search for the tag corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tag> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tags for query {}", query);
        return tagSearchRepository.search(queryStringQuery(query), pageable);
    }
}
