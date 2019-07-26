package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import task.manager.controller.dto.ProjectCreationRequest;
import task.manager.controller.dto.UpdateProjectAttributeRequest;
import task.manager.exception.NotFoundException;
import task.manager.model.Project;
import task.manager.repository.ProjectRepository;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  public long addProject(ProjectCreationRequest request) {
    Project newProject = new Project();
    newProject.setName(request.getName());
    newProject.setDescription(request.getDescription());
    newProject = projectRepository.save(newProject);
    return newProject.getId();
  }

  public void updateProject(long projectId, UpdateProjectAttributeRequest request) {
    Project projectToBeUpdated = projectRepository.findById(projectId)
        .orElseThrow(() -> new NotFoundException(String.format("Project with id[%d] not found", projectId)));
    String newName = request.getName();
    if (newName != null) {
      projectToBeUpdated.setName(newName);
    }
    String newDescription = request.getDescription();
    if (newDescription != null) {
      projectToBeUpdated.setDescription(newDescription);
    }
    projectRepository.save(projectToBeUpdated);
  }

  public void deleteProject(long id) {
    projectRepository.deleteById(id);
  }

  public Project getProject(long id) {
    return projectRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Project with id[%d] not found", id)));
  }

  public Project getProject(String name) {
    return projectRepository.findByName(name);
  }

  public Page<Project> getAllProjects(Pageable p) {
    return projectRepository.findAll(p);
  }

}
