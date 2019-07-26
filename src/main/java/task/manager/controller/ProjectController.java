package task.manager.controller;

import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import task.manager.controller.dto.ObjectUpdateResponse;
import task.manager.controller.dto.ProjectCreationRequest;
import task.manager.controller.dto.ProjectDto;
import task.manager.controller.dto.UpdateProjectAttributeRequest;
import task.manager.service.ProjectService;

@Controller
@RequestMapping(path = "/projects")
public class ProjectController {

  private static final String PROJECT = "Project";

  @Autowired
  private ProjectService projectService;

  @Autowired
  private DtoMapper dtoMapper;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<ProjectDto> showProjects(@RequestParam(name = "name", required = false) String name, Pageable p) {
    if (name == null || name.isEmpty()) {
      return projectService.getAllProjects(p).map(dtoMapper::convertProjectToDto);
    } else {
      return new PageImpl<>(Collections.singletonList(dtoMapper.convertProjectToDto(projectService.getProject(name))));
    }
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ProjectDto showProject(@PathVariable("id") long id) {
    return dtoMapper.convertProjectToDto(projectService.getProject(id));
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody
  ObjectCreationResponse createProject(@RequestBody @Valid ProjectCreationRequest request) {
    return new ObjectCreationResponse(projectService.addProject(request), PROJECT);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ObjectUpdateResponse updateProject(@PathVariable("id") long id,
      @RequestBody @Valid UpdateProjectAttributeRequest request) {
    projectService.updateProject(id, request);
    return new ObjectUpdateResponse();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteProject(@PathVariable("id") long id) {
    projectService.deleteProject(id);
  }
}
