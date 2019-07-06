package task.manager.service;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.model.Project;
import task.manager.model.Task;
import task.manager.repository.ProjectRepository;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  public long addProject(String name, String description) {
    Project newProject = new Project();
    newProject.setName(name);
    newProject.setDescription(description);
    newProject = projectRepository.save(newProject);
    return newProject.getId();
  }

  public long addProject(Project newProject) {
    newProject = projectRepository.save(newProject);
    return newProject.getId();
  }

  public void updateProject(String name, String description) {
    Project projectToBeUpdated = projectRepository.findByName(name);
    projectToBeUpdated.setName(name);
    projectToBeUpdated.setDescription(description);
    projectRepository.save(projectToBeUpdated);
  }

  public void updateProject(Project project) {
    Project projectToBeUpdated = projectRepository.findById(project.getId())
        .orElseThrow(() -> new RuntimeException(String.format("Project with id[%d] not found", project.getId())));
    projectToBeUpdated.setName(project.getName());
    projectToBeUpdated.setDescription(project.getDescription());
    projectRepository.save(projectToBeUpdated);
  }

  public void deleteProject(long id) {
    projectRepository.deleteById(id);
  }

  public Project getProject(long id) {
    return projectRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Project with id[%d] not found", id)));
  }

}
