package task.manager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static task.manager.controller.Util.JsonUtil.asJsonString;

import java.time.LocalDateTime;
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
import task.manager.controller.dto.ProjectDto;
import task.manager.controller.dto.TaskCreationRequest;
import task.manager.controller.dto.TaskDto;
import task.manager.model.Project;
import task.manager.model.Task;
import task.manager.service.Status;
import task.manager.service.TaskService;

@RunWith(SpringRunner.class)
@WebMvcTest({TaskController.class, DtoMapper.class})
public class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskService taskService;

  @Mock
  private DtoMapper mapper;

  private Task mockedTask = new Task();
  private TaskDto taskDto = new TaskDto();

  @Before
  public void init() {
    Project mockedProject = new Project();
    mockedProject.setId(0L);
    mockedProject.setName("First Project");
    mockedProject.setDescription("Description of first Project");

    ProjectDto projectDto = new ProjectDto();
    projectDto.setName(mockedProject.getName());
    projectDto.setDescription(mockedProject.getDescription());

    mockedTask.setId(0L);
    mockedTask.setName("First Task");
    mockedTask.setDescription("Description of first Task");
    mockedTask.setStatus(Status.IN_PROGRESS.ordinal());
    mockedTask.setPriority(0);
//    mockedTask.setDeadline(LocalDateTime.now());
    mockedTask.setProject(mockedProject);

    taskDto.setName(mockedTask.getName());
    taskDto.setDescription(mockedTask.getDescription());
    taskDto.setStatus(mockedTask.getStatus());
    taskDto.setPriority(mockedTask.getPriority());
//    taskDto.setDeadline(mockedTask.getDeadline());
    taskDto.setProject(projectDto);

    when(mapper.convertTaskToDto(eq(mockedTask))).thenReturn(taskDto);
  }

  @Test
  public void showTaskShouldReturnTask() throws Exception {

    when(taskService.getTask(0L, mockedTask.getId())).thenReturn(mockedTask);

    this.mockMvc.perform(get("/projects/0/tasks/0"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().json(asJsonString(taskDto)));
  }

  @Test
  public void shouldCreateTask() throws Exception {
    TaskCreationRequest request = TaskCreationRequest.builder()
        .name("First Task")
        .description("Description of first Task")
//        .deadline(LocalDateTime.now())
        .status(Status.IN_PROGRESS.ordinal())
        .priority(0)
        .build();

    when(taskService.addTask(0L, request)).thenReturn(mockedTask.getId());

    this.mockMvc.perform(post("/projects/0/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(request)))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json("{\"id\":0,\"objectType\":\"Task\"}"));
  }

  @Test
  public void deleteTaskShouldBeOk() throws Exception {
    this.mockMvc.perform(delete("/projects/0/tasks/0")).andDo(print())
        .andExpect(status().isOk());
  }
}
