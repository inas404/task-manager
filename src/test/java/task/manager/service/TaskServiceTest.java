package task.manager.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import task.manager.controller.dto.TaskCreationRequest;
import task.manager.controller.dto.UpdateTaskAttributeRequest;
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
    when(projectRepository.findById(0L)).thenReturn(Optional.of(mockedProject));
    when(projectRepository.save(any())).thenReturn(mockedProject);

    mockedTask.setId(0L);
    mockedTask.setName("First Task");
    mockedTask.setDescription("Description of first task");
    mockedTask.setPriority(1);
    mockedTask.setDeadline(LocalDateTime.now());
    mockedTask.setStatus(Status.IN_PROGRESS.ordinal());
    when(taskRepository.findById(0L)).thenReturn(Optional.of(mockedTask));
    when(taskRepository.findById(0L)).thenReturn(Optional.of(mockedTask));
    when(taskRepository.save(any())).thenReturn(mockedTask);
    when(taskRepository.findByIdAndProjectId(mockedTask.getId(), mockedProject.getId())).thenReturn(mockedTask);
  }

  @Test
  public void shouldAddTaskToProject() {
    TaskCreationRequest taskCreationRequest = TaskCreationRequest.builder()
        .name("First Task")
        .description("Description of first Task")
        .priority(1)
        .deadline(LocalDateTime.now())
        .status(Status.IN_PROGRESS.ordinal())
        .build();

    long taskId = taskService.addTask(mockedProject.getId(), taskCreationRequest);
    Mockito.verify(taskRepository, Mockito.times(1)).save(any(Task.class));
  }

  @Test
  public void shouldUpdateTaskInProject() {
    UpdateTaskAttributeRequest request = UpdateTaskAttributeRequest.builder()
        .description("Updated Description")
        .build();

    taskService.updateTask(mockedProject.getId(), 0L, request);
    Mockito.verify(taskRepository, Mockito.times(1)).save(mockedTask);
  }

  @Test
  public void shouldDeleteTaskFromProject() {
    taskService.deleteTask(mockedProject.getId(), mockedTask.getId());
    Mockito.verify(taskRepository, Mockito.times(1)).deleteByIdAndProjectId(mockedTask.getId(), mockedProject.getId());
  }

  @Test
  public void shouldGetTaskFromProject() {
    Task task = taskService.getTask(mockedProject.getId(), mockedTask.getId());
    Mockito.verify(taskRepository, Mockito.times(1)).findByIdAndProjectId(mockedTask.getId(), mockedProject.getId());
  }
}
