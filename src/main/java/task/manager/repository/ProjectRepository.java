package task.manager.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import task.manager.model.Project;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

  Project findByName(String name);

}
