package task.manager.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import task.manager.model.Project;
import task.manager.testsuites.MySqlContainerTestSuites;

@RunWith(SpringRunner.class)
public class ProjectRepositoryIntegrationTest extends MySqlContainerTestSuites {

  @Autowired
  protected ProjectRepository projectRepository;

  @Test
  public void testProjectRepositoryShouldNotBeNull() {
    Assert.assertNotNull(projectRepository);
  }

  @Test
  public void shouldFindProjectByName() {
    Project project1 = new Project();
    project1.setName("First");
    project1.setDescription("blabla");

    Project project2 = new Project();
    project2.setName("Second");
    project2.setDescription("blabla");

    Project project3 = new Project();
    project3.setName("Third");
    project3.setDescription("blabla");

    projectRepository.save(project1);
    projectRepository.save(project2);
    projectRepository.save(project3);

    Project first = projectRepository.findByName("First");
    Assert.assertEquals("First", first.getName());
  }
}
