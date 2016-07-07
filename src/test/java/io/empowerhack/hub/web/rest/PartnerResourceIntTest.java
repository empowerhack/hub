package io.empowerhack.hub.web.rest;

import io.empowerhack.hub.HubApp;
import io.empowerhack.hub.domain.Partner;
import io.empowerhack.hub.repository.PartnerRepository;
import io.empowerhack.hub.service.PartnerService;
import io.empowerhack.hub.repository.search.PartnerSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PartnerResource REST controller.
 *
 * @see PartnerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HubApp.class)
@WebAppConfiguration
@IntegrationTest
public class PartnerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private PartnerService partnerService;

    @Inject
    private PartnerSearchRepository partnerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartnerResource partnerResource = new PartnerResource();
        ReflectionTestUtils.setField(partnerResource, "partnerService", partnerService);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        partnerSearchRepository.deleteAll();
        partner = new Partner();
        partner.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partners.get(partners.size() - 1);
        assertThat(testPartner.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Partner in ElasticSearch
        Partner partnerEs = partnerSearchRepository.findOne(testPartner.getId());
        assertThat(partnerEs).isEqualToComparingFieldByField(testPartner);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setName(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partners
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = new Partner();
        updatedPartner.setId(partner.getId());
        updatedPartner.setName(UPDATED_NAME);

        restPartnerMockMvc.perform(put("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPartner)))
                .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partners.get(partners.size() - 1);
        assertThat(testPartner.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Partner in ElasticSearch
        Partner partnerEs = partnerSearchRepository.findOne(testPartner.getId());
        assertThat(partnerEs).isEqualToComparingFieldByField(testPartner);
    }

    @Test
    @Transactional
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Get the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean partnerExistsInEs = partnerSearchRepository.exists(partner.getId());
        assertThat(partnerExistsInEs).isFalse();

        // Validate the database is empty
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        // Search the partner
        restPartnerMockMvc.perform(get("/api/_search/partners?query=id:" + partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
