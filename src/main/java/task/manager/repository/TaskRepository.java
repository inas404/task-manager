package task.manager.repository;

import org.springframework.stereotype.Repository;
import task.manager.model.Task;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
  Task findByProjectId(Long postId);
}
