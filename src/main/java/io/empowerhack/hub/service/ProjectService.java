package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Project;
import io.empowerhack.hub.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findAll() {
        List<Project> project = new ArrayList<>();
        projectRepository.findAll().forEach(item->{
            project.add(item);
        });

        return project;
    }

    public Project save(Project project) {

        Project existingProject = this.findByUid(project.getUid());
        if (existingProject != null) {
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            existingProject.setWebsite(project.getWebsite());
            existingProject.setGithub(project.getGithub());

            return projectRepository.save(existingProject);
        }

        project.setCreatedOn(new Date());

        return projectRepository.save(project);
    }

    public Project findByUid(String uid) {
        return projectRepository.findByUid(uid);
    }
}
