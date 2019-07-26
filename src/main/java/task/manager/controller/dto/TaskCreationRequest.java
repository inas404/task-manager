package task.manager.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "Parameters for creating new task.")
public class TaskCreationRequest {

  @NotNull
  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotNull
  @Size(max = 250)
  private String description;

  @ApiModelProperty(value = "Status of progress of the task", allowableValues = "DONE, IN_PROGRESS, BLOCKED")
  private int status;

  private int priority;
  private LocalDateTime deadline;
}
