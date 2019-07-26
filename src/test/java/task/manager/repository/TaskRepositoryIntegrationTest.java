package task.manager.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import task.manager.model.Project;
import task.manager.model.Task;
import task.manager.service.Status;
import task.manager.testsuites.MySqlContainerTestSuites;

@RunWith(SpringRunner.class)
public class TaskRepositoryIntegrationTest extends MySqlContainerTestSuites {

  @Autowired
  protected TaskRepository taskRepository;

  @Autowired
  protected ProjectRepository projectRepository;

  private Project project1;
  private Task task1;

  @Before
  public void setUp() {
    project1 = new Project();
    project1.setName("First");
    project1.setDescription("blabla");
    projectRepository.save(project1);

    Project project2 = new Project();
    project2.setName("Second");
    project2.setDescription("blabla");
    projectRepository.save(project2);

    task1 = new Task();
    task1.setName("task1");
    task1.setDescription("blabla");
    task1.setPriority(1);
    task1.setDeadline(LocalDateTime.now());
    task1.setStatus(Status.IN_PROGRESS.ordinal());
    task1.setProject(project1);
    taskRepository.save(task1);

    Task t2 = new Task();
    t2.setName("t2");
    t2.setDescription("blabla");
    t2.setPriority(1);
    t2.setDeadline(LocalDateTime.now());
    t2.setStatus(Status.IN_PROGRESS.ordinal());
    t2.setProject(project2);
    taskRepository.save(t2);

    Task t3 = new Task();
    t3.setName("t3");
    t3.setDescription("blabla");
    t3.setPriority(1);
    t3.setDeadline(LocalDateTime.now());
    t3.setStatus(Status.IN_PROGRESS.ordinal());
    t3.setProject(project1);
    taskRepository.save(t3);
  }

  @Test
  public void testTaskRepositoryShouldNotBeNull() {
    Assert.assertNotNull(taskRepository);
  }

  @Test
  public void shouldFindTaskByProjectId() {
    Page<Task> taskPage = taskRepository.findByProjectId(project1.getId(), PageRequest.of(0, 1));
    List<Task> tasks = taskPage.getContent();
    Assert.assertEquals(1, tasks.size());
    Assert.assertEquals(task1, tasks.get(0));
  }

  @Test
  public void shouldFindTaskByIdAndProjectId() {

  }

  @Test
  public void shouldFindTaskByNameAndProjectId() {

  }

  @Test
  public void shouldDeleteTaskByIdAndProjectId() {

  }
}
