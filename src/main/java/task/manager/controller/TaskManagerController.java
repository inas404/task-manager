package task.manager.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import task.manager.model.Project;
import task.manager.service.ProjectService;

@Controller
@RequestMapping(path = "/projects")
public class TaskManagerController {

  @Autowired
  private ProjectService projectService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Project showProject(@PathVariable("id") long id) {
    return projectService.getProject(id);
  }

  @PostMapping(path = "/add")
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody
  String createProject(@RequestParam @Valid Project project) {
    projectService.addProject(project);
    return "Created";
  }

  @PostMapping("/{id}/update")
  public String updateProject(@PathVariable("id") long id, @Valid Project project) {
    projectService.updateProject(project);
    return "update-project";
  }

  @GetMapping("/{id}/delete")
  public String deleteUser(@PathVariable("id") long id, Model model) {
    projectService.deleteProject(id);
    return "deleted";
  }
}
