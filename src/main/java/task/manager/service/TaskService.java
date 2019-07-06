package task.manager.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  public long addTask(long projectId, String name, String description, int priority, LocalDateTime time, boolean isDone){
    Optional<Project> maybeProject = projectRepository.findById(projectId);
    Project project = maybeProject.get();

    Task t = new Task();
    t.setName(name);
    t.setDescription(description);
    t.setPriority(priority);
    t.setDeadline(time);
    t.setDone(isDone);
    t.setProject(project);
    Task newTaskId = taskRepository.save(t);
    return newTaskId.getId();
  }

  public long addTask(long projectId, Task t){
    Optional<Project> maybeProject = projectRepository.findById(projectId);
    Project project = maybeProject.get();

    t.setProject(project);
    Task newTaskId = taskRepository.save(t);

    return newTaskId.getId();
  }

  public void updateTask(long projectId, String name, String description, int priority, LocalDateTime time, boolean isDone){
    Optional<Project> maybeProject = projectRepository.findById(projectId);
    Project project = maybeProject.get();

    //TODO find by id (composition)
    // project.getTasks().
    Task t = new Task();
    t.setName(name);
    t.setDescription(description);
    t.setPriority(priority);
    t.setDeadline(time);
    t.setDone(isDone);
    t.setProject(project);
    taskRepository.save(t);
  }

  public void updateTask(long projectId, Task t){
    Optional<Project> maybeProject = projectRepository.findById(projectId);
    Project project = maybeProject.get();

    //TODO find by id (composition)
    // project.getTasks().

    t.setProject(project);
    taskRepository.save(t);
    projectRepository.save(project);
  }

  public void deleteTask(long projectId, long id){
    taskRepository.deleteById(id);
  }

  public Task getTask(long projectId, long id){
    return taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Task with id[%d] not found", id)));
  }
}
