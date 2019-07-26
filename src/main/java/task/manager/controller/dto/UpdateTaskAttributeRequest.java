package task.manager.controller.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "Parameters for updating an existing task.")
public class UpdateTaskAttributeRequest {

  private String name;
  private String description;
  private Integer status;
  private Integer priority;
  private LocalDateTime deadline;
}
