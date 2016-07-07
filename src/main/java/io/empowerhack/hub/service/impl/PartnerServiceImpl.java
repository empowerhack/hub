package io.empowerhack.hub.service.impl;

import io.empowerhack.hub.service.PartnerService;
import io.empowerhack.hub.domain.Partner;
import io.empowerhack.hub.repository.PartnerRepository;
import io.empowerhack.hub.repository.search.PartnerSearchRepository;
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
 * Service Implementation for managing Partner.
 */
@Service
@Transactional
public class PartnerServiceImpl implements PartnerService{

    private final Logger log = LoggerFactory.getLogger(PartnerServiceImpl.class);
    
    @Inject
    private PartnerRepository partnerRepository;
    
    @Inject
    private PartnerSearchRepository partnerSearchRepository;
    
    /**
     * Save a partner.
     * 
     * @param partner the entity to save
     * @return the persisted entity
     */
    public Partner save(Partner partner) {
        log.debug("Request to save Partner : {}", partner);
        Partner result = partnerRepository.save(partner);
        partnerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the partners.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Partner> findAll(Pageable pageable) {
        log.debug("Request to get all Partners");
        Page<Partner> result = partnerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one partner by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Partner findOne(Long id) {
        log.debug("Request to get Partner : {}", id);
        Partner partner = partnerRepository.findOne(id);
        return partner;
    }

    /**
     *  Delete the  partner by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Partner : {}", id);
        partnerRepository.delete(id);
        partnerSearchRepository.delete(id);
    }

    /**
     * Search for the partner corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Partner> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Partners for query {}", query);
        return partnerSearchRepository.search(queryStringQuery(query), pageable);
    }
}
