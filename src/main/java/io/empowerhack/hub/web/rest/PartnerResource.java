package io.empowerhack.hub.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.empowerhack.hub.domain.Partner;
import io.empowerhack.hub.service.PartnerService;
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
 * REST controller for managing Partner.
 */
@RestController
@RequestMapping("/api")
public class PartnerResource {

    private final Logger log = LoggerFactory.getLogger(PartnerResource.class);
        
    @Inject
    private PartnerService partnerService;
    
    /**
     * POST  /partners : Create a new partner.
     *
     * @param partner the partner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partner, or with status 400 (Bad Request) if the partner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner> createPartner(@Valid @RequestBody Partner partner) throws URISyntaxException {
        log.debug("REST request to save Partner : {}", partner);
        if (partner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("partner", "idexists", "A new partner cannot already have an ID")).body(null);
        }
        Partner result = partnerService.save(partner);
        return ResponseEntity.created(new URI("/api/partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partners : Updates an existing partner.
     *
     * @param partner the partner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partner,
     * or with status 400 (Bad Request) if the partner is not valid,
     * or with status 500 (Internal Server Error) if the partner couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner> updatePartner(@Valid @RequestBody Partner partner) throws URISyntaxException {
        log.debug("REST request to update Partner : {}", partner);
        if (partner.getId() == null) {
            return createPartner(partner);
        }
        Partner result = partnerService.save(partner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partner", partner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partners : get all the partners.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of partners in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Partner>> getAllPartners(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Partners");
        Page<Partner> page = partnerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /partners/:id : get the "id" partner.
     *
     * @param id the id of the partner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partner, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/partners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner> getPartner(@PathVariable Long id) {
        log.debug("REST request to get Partner : {}", id);
        Partner partner = partnerService.findOne(id);
        return Optional.ofNullable(partner)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partners/:id : delete the "id" partner.
     *
     * @param id the id of the partner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/partners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        log.debug("REST request to delete Partner : {}", id);
        partnerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partner", id.toString())).build();
    }

    /**
     * SEARCH  /_search/partners?query=:query : search for the partner corresponding
     * to the query.
     *
     * @param query the query of the partner search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/partners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Partner>> searchPartners(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Partners for query {}", query);
        Page<Partner> page = partnerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/partners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
