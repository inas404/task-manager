package task.manager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static task.manager.controller.Util.JsonUtil.asJsonString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task.manager.controller.dto.DtoMapper;
import task.manager.controller.dto.ProjectCreationRequest;
import task.manager.controller.dto.ProjectDto;
import task.manager.controller.dto.UpdateProjectAttributeRequest;
import task.manager.model.Project;
import task.manager.service.ProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest({ProjectController.class, DtoMapper.class})
public class ProjectControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProjectService projectService;

  @Mock
  private DtoMapper mapper;

  private Project mockedProject = new Project();

  @Before
  public void init() {
    mockedProject.setId(0L);
    mockedProject.setName("First Project");
    mockedProject.setDescription("Description of first Project");

    ProjectDto projectDto = new ProjectDto();
    projectDto.setName(mockedProject.getName());
    projectDto.setDescription(mockedProject.getDescription());
    when(mapper.convertProjectToDto(eq(mockedProject))).thenReturn(projectDto);
  }

  @Test
  public void showProjectShouldReturnProject() throws Exception {
    ProjectDto projectDto = new ProjectDto();
    projectDto.setName(mockedProject.getName());
    projectDto.setDescription(mockedProject.getDescription());

    when(projectService.getProject(0)).thenReturn(mockedProject);

    this.mockMvc.perform(get("/projects/0"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().json(asJsonString(projectDto)));
  }

  @Test
  public void createProjectShouldCreateProject() throws Exception {
    ProjectCreationRequest request = ProjectCreationRequest.builder()
        .name("First Project")
        .description("Description of first Project")
        .build();
    when(projectService.addProject(request)).thenReturn(mockedProject.getId());
    this.mockMvc.perform(post("/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(request)))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json("{\"id\":0,\"objectType\":\"Project\"}"));
  }

  @Test
  public void updateProjectShouldReturnObjectUpdateResponse() throws Exception {
    UpdateProjectAttributeRequest request = UpdateProjectAttributeRequest.builder()
        .description("Updated Description")
        .build();

    this.mockMvc.perform(patch("/projects/0")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(request)))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().json("{\"status\":true}"));

  }

  @Test
  public void deleteProjectShouldBeOk() throws Exception {
    this.mockMvc.perform(delete("/projects/0")).andDo(print())
        .andExpect(status().isOk());
  }
}
