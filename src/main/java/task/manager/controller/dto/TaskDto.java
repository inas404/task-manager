package task.manager.controller.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "Project resource definition ")
public class TaskDto {

  private long id;
  private String name;
  private String description;
  private int priority;
  private LocalDateTime deadline;
  private int status;
  private ProjectDto project;//TODO: should be dto or entity
}
