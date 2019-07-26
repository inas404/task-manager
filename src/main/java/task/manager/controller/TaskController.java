package task.manager.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import task.manager.controller.dto.DtoMapper;
import task.manager.controller.dto.ObjectCreationResponse;
import task.manager.controller.dto.TaskCreationRequest;
import task.manager.controller.dto.TaskDto;
import task.manager.controller.dto.UpdateTaskAttributeRequest;
import task.manager.model.Task;
import task.manager.service.TaskService;

@Controller
@RequestMapping(path = "/projects/{project_id}")
public class TaskController {


  @Autowired
  private TaskService taskService;

  @Autowired
  private DtoMapper dtoMapper;

  private static final String TASK = "Task";

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<Task> showTasks(@PathVariable(value = "project_id") long projectId,
      @RequestParam(name = "name", required = false) String name, Pageable p) {
    if (name == null) {
      return taskService.getAllTasksWithProjectID(projectId, p);
    } else {
      return taskService.getTaskByName(projectId, name, p);
    }
  }

  @GetMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody TaskDto showTask(@PathVariable("project_id") long projectId, @PathVariable(value = "id") long id) {
    return dtoMapper.convertTaskToDto(taskService.getTask(projectId, id));
  }

  @PostMapping("/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody
  ObjectCreationResponse createTask(@PathVariable("project_id") long projectId,
      @RequestBody @Valid TaskCreationRequest request) {
    return new ObjectCreationResponse(taskService.addTask(projectId, request), TASK);
  }

  @PatchMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateTask(@PathVariable("project_id") long projectId, @PathVariable("id") long id,
      @Valid UpdateTaskAttributeRequest request) {
    taskService.updateTask(projectId, id, request);
  }

  @DeleteMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTask(@PathVariable("project_id") long projectId, @PathVariable("id") long id) {
    taskService.deleteTask(projectId, id);
  }
}
