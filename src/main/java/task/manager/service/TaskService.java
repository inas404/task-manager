package task.manager.service;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import task.manager.controller.dto.TaskCreationRequest;
import task.manager.controller.dto.UpdateTaskAttributeRequest;
import task.manager.model.Project;
import task.manager.model.Task;
import task.manager.repository.ProjectRepository;
import task.manager.repository.TaskRepository;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ProjectRepository projectRepository;

  public long addTask(long projectId, @Valid TaskCreationRequest request) {
    Task newTask = new Task();
    newTask.setName(request.getName());
    newTask.setDescription(request.getDescription());
    newTask.setStatus(request.getStatus());
    newTask.setDeadline(request.getDeadline());
    newTask.setPriority(request.getPriority());
    Optional<Project> project = projectRepository.findById(projectId);
    newTask.setProject(
        project.orElseThrow(() -> new RuntimeException(String.format("Project with id[%d] not found", projectId))));
    Task newTaskId = taskRepository.save(newTask);
    return newTaskId.getId();
  }

  public void updateTask(long projectId, long taskId, @Valid UpdateTaskAttributeRequest request) {
    Task taskToBeUpdated = taskRepository.findByIdAndProjectId(taskId, projectId);
    String name = request.getName();
    if (name != null) {
      taskToBeUpdated.setName(name);
    }
    String description = request.getDescription();
    if (description != null) {
      taskToBeUpdated.setDescription(description);
    }
    LocalDateTime deadline = request.getDeadline();
    if (deadline != null) {
      taskToBeUpdated.setDeadline(deadline);
    }
    Integer priority = request.getPriority();
    if (priority != null) {
      taskToBeUpdated.setPriority(priority);
    }
    Integer status = request.getStatus();
    if (status != null) {
      taskToBeUpdated.setStatus(status);
    }
    taskRepository.save(taskToBeUpdated);
  }

  public void deleteTask(long projectId, long id) {
    taskRepository.deleteByIdAndProjectId(id, projectId);
  }

  public Task getTask(long projectId, long id) {
    return taskRepository.findByIdAndProjectId(id, projectId);
  }

  public Page<Task> getTaskByName(long projectId, String name, Pageable p) {
    return taskRepository.findByNameAndProjectId(name, projectId, p);
  }

  public Page<Task> getAllTasksWithProjectID(long projectId, Pageable p) {
    return taskRepository.findByProjectId(projectId, p);
  }
}
