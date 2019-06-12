package task.manager.repository;

import org.springframework.stereotype.Repository;
import task.manager.model.Project;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

  Project findByName(String name);
}
