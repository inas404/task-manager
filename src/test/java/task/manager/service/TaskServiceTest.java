package task.manager.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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
import task.manager.repository.TaskRepository;

public class TaskServiceTest {

  @InjectMocks
  TaskService taskService;

  @Mock
  TaskRepository taskRepository;

  @Mock
  ProjectRepository projectRepository;

  private Project mockedProject = new Project();
  private Task mockedTask = new Task();

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockedProject.setId(0L);
    mockedProject.setName("First Project");
    mockedProject.setDescription("Description of first Project");
    mockedProject.setTasks(Collections.singleton(new Task()));
    when(projectRepository.findById(0L)).thenReturn(Optional.of(mockedProject));
    when(projectRepository.save(any())).thenReturn(mockedProject);

    mockedTask.setId(0L);
    mockedTask.setName("First Task");
    mockedTask.setDescription("Description of first task");
    mockedTask.setPriority(1);
    mockedTask.setDeadline(LocalDateTime.now());
    mockedTask.setDone(false);
    when(taskRepository.findById(0L)).thenReturn(Optional.of(mockedTask));
    when(taskRepository.save(any())).thenReturn(mockedTask);
  }

  @Test
  public void shouldAddTaskToProject() {
    long taskId = taskService.addTask(mockedProject.getId(), mockedTask);
    Mockito.verify(projectRepository, Mockito.times(1)).save(mockedProject);
    Mockito.verify(taskRepository, Mockito.times(1)).save(mockedTask);
  }

  @Test
  public void shouldUpdateTaskInProject() {
    taskService.updateTask(mockedProject.getId(), mockedTask);
    Mockito.verify(projectRepository, Mockito.times(1)).save(mockedProject);
    Mockito.verify(taskRepository, Mockito.times(1)).save(mockedTask);
  }

  @Test
  public void shouldDeleteTaskFromProject() {
    taskService.deleteTask(mockedProject.getId(), mockedTask.getId());
    Mockito.verify(taskRepository, Mockito.times(1)).deleteById(mockedTask.getId());
  }

  @Test
  public void shouldGetTaskFromProject() {
    Task task = taskService.getTask(mockedProject.getId(), mockedTask.getId());
    Mockito.verify(taskRepository, Mockito.times(1)).findById(mockedTask.getId());
  }
}
