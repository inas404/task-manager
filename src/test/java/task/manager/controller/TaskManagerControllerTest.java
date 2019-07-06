package task.manager.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import task.manager.model.Project;
import task.manager.model.Task;
import task.manager.service.ProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskManagerController.class)
public class TaskManagerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProjectService projectService;

  private Project mockedProject = new Project();

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockedProject.setId(0L);
    mockedProject.setName("First Project");
    mockedProject.setDescription("Description of first Project");
  }

  @Test
  public void showProjectShouldReturnProject() throws Exception {
    when(projectService.getProject(0)).thenReturn(mockedProject);
    MvcResult mvcResult = this.mockMvc.perform(get("/projects/0")).andDo(print()).andExpect(status().isOk())
        .andReturn();
    Assert.assertEquals("0", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void createProjectShouldCreateProject() throws Exception {
    when(projectService.addProject(mockedProject)).thenReturn(mockedProject.getId());
    this.mockMvc.perform(post("/projects/add")).andDo(print()).andExpect(status().isCreated())
        .andExpect(content().string(containsString("0")));
  }

  @Test
  public void updateProjectShouldReturnProject() throws Exception {
    this.mockMvc.perform(post("projects/0/update")).andDo(print())
        .andExpect(content().string(containsString("0")));
  }

  @Test
  public void deleteProjectShouldReturnProject() throws Exception {
    this.mockMvc.perform(get("/projects/0/delete")).andDo(print())
        .andExpect(content().string(containsString("0")));
  }
}
