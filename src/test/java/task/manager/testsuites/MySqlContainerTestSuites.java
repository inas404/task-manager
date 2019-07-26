package task.manager.testsuites;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import task.manager.controller.dto.DtoMapper;
import task.manager.repository.ProjectRepositoryIntegrationTest;
import task.manager.repository.TaskRepositoryIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ProjectRepositoryIntegrationTest.class, TaskRepositoryIntegrationTest.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {MySqlContainerTestSuites.Initializer.class}, classes= DtoMapper.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MySqlContainerTestSuites {

  @ClassRule
  public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:5.7")
      .withDatabaseName("accounting_api")
      .withUsername("root")
      .withPassword("")
      .withInitScript("schema.sql");

  public static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
          "spring.datasource.username=" + mySQLContainer.getUsername(),
          "spring.datasource.password=" + mySQLContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }
}
