package task.manager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import task.manager.model.Task;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

  Page<Task> findByProjectId(long projectId, Pageable pageable);

  Task findByIdAndProjectId(long id, long projectId);

  Task findByNameAndProjectId(String name, long projectId);

  Page<Task> findByNameAndProjectId(String name, long projectId, Pageable p);

  void deleteByIdAndProjectId(long id, long projectId);
}
