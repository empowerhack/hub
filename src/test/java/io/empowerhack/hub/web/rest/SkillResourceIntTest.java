package io.empowerhack.hub.web.rest;

import io.empowerhack.hub.HubApp;
import io.empowerhack.hub.domain.Skill;
import io.empowerhack.hub.repository.SkillRepository;
import io.empowerhack.hub.service.SkillService;
import io.empowerhack.hub.repository.search.SkillSearchRepository;

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
 * Test class for the SkillResource REST controller.
 *
 * @see SkillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HubApp.class)
@WebAppConfiguration
@IntegrationTest
public class SkillResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";

    private static final Integer DEFAULT_LEVEL = 0;
    private static final Integer UPDATED_LEVEL = 1;

    @Inject
    private SkillRepository skillRepository;

    @Inject
    private SkillService skillService;

    @Inject
    private SkillSearchRepository skillSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSkillMockMvc;

    private Skill skill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SkillResource skillResource = new SkillResource();
        ReflectionTestUtils.setField(skillResource, "skillService", skillService);
        this.restSkillMockMvc = MockMvcBuilders.standaloneSetup(skillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        skillSearchRepository.deleteAll();
        skill = new Skill();
        skill.setName(DEFAULT_NAME);
        skill.setLevel(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill

        restSkillMockMvc.perform(post("/api/skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skill)))
                .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skills.get(skills.size() - 1);
        assertThat(testSkill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);

        // Validate the Skill in ElasticSearch
        Skill skillEs = skillSearchRepository.findOne(testSkill.getId());
        assertThat(skillEs).isEqualToComparingFieldByField(testSkill);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setName(null);

        // Create the Skill, which fails.

        restSkillMockMvc.perform(post("/api/skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skill)))
                .andExpect(status().isBadRequest());

        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setLevel(null);

        // Create the Skill, which fails.

        restSkillMockMvc.perform(post("/api/skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skill)))
                .andExpect(status().isBadRequest());

        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skills
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkill() throws Exception {
        // Initialize the database
        skillService.save(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill
        Skill updatedSkill = new Skill();
        updatedSkill.setId(skill.getId());
        updatedSkill.setName(UPDATED_NAME);
        updatedSkill.setLevel(UPDATED_LEVEL);

        restSkillMockMvc.perform(put("/api/skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSkill)))
                .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skills.get(skills.size() - 1);
        assertThat(testSkill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkill.getLevel()).isEqualTo(UPDATED_LEVEL);

        // Validate the Skill in ElasticSearch
        Skill skillEs = skillSearchRepository.findOne(testSkill.getId());
        assertThat(skillEs).isEqualToComparingFieldByField(testSkill);
    }

    @Test
    @Transactional
    public void deleteSkill() throws Exception {
        // Initialize the database
        skillService.save(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Get the skill
        restSkillMockMvc.perform(delete("/api/skills/{id}", skill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean skillExistsInEs = skillSearchRepository.exists(skill.getId());
        assertThat(skillExistsInEs).isFalse();

        // Validate the database is empty
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSkill() throws Exception {
        // Initialize the database
        skillService.save(skill);

        // Search the skill
        restSkillMockMvc.perform(get("/api/_search/skills?query=id:" + skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }
}
