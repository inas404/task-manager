package task.manager.controller.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import task.manager.model.Project;
import task.manager.model.Task;

@Component
public class DtoMapper {

  private ModelMapper modelMapper = new ModelMapper();

  public ProjectDto convertProjectToDto(Project project) {
    return modelMapper.map(project, ProjectDto.class);
  }

  public Project convertDtoToProject(ProjectDto projectDto) {
    return modelMapper.map(projectDto, Project.class);
  }

  public TaskDto convertTaskToDto(Task task) {
    return modelMapper.map(task, TaskDto.class);
  }

  public Task convertDtoToTask(TaskDto taskDto) {
    return modelMapper.map(taskDto, Task.class);
  }
}
