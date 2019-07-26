package task.manager.service;

import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import task.manager.controller.dto.ProjectCreationRequest;
import task.manager.controller.dto.UpdateProjectAttributeRequest;
import task.manager.model.Project;
import task.manager.repository.ProjectRepository;

public class ProjectServiceTest {

  private Project mockedProject = new Project();

  @InjectMocks
  ProjectService projectService;

  @Mock
  ProjectRepository projectRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockedProject.setId(0L);
    mockedProject.setName("First Project");
    mockedProject.setDescription("Description of first Project");
    when(projectRepository.findById(0L)).thenReturn(Optional.of(mockedProject));
    when(projectRepository.save(any())).thenReturn(mockedProject);
  }

  @Test
  public void shouldAddProject() {
    Project newProject = new Project();
    newProject.setId(0L);
    newProject.setName("First Project");
    newProject.setDescription("Description of first Project");

    ProjectCreationRequest request = ProjectCreationRequest.builder()
        .name(newProject.getName())
        .description(newProject.getDescription())
        .build();

    long projectId = projectService.addProject(request);
    Assert.assertEquals(0L, projectId);
    Mockito.verify(projectRepository, Mockito.times(1)).save(any(Project.class));
  }

  @Test
  public void shouldUpdateProject() {
    UpdateProjectAttributeRequest request = UpdateProjectAttributeRequest.builder()
        .description("Updated Description")
        .build();

    Project updated = mockedProject;
    updated.setDescription("Updated Description");
    projectService.updateProject(0L, request);
    Mockito.verify(projectRepository, Mockito.times(1)).save(updated);
  }

  @Test
  public void shouldDeleteProject() {
    projectService.deleteProject(mockedProject.getId());
    Mockito.verify(projectRepository, Mockito.times(1)).deleteById(mockedProject.getId());
  }

  @Test
  public void shouldGetProject() {
    Project project = projectService.getProject(mockedProject.getId());
    Mockito.verify(projectRepository, Mockito.times(1)).findById(mockedProject.getId());
  }
}
