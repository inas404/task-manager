package task.manager.service;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import task.manager.model.Project;
import task.manager.model.Task;
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
    long projectId = projectService.addProject(mockedProject);
    Mockito.verify(projectRepository, Mockito.times(1)).save(mockedProject);
  }

  @Test
  public void shouldUpdateProject() {
    Project updated = mockedProject;
    updated.setDescription("Updated Description");

    projectService.updateProject(updated);
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
