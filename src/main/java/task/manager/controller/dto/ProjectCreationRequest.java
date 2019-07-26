package task.manager.controller.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "Parameters for creating new project.")
public class ProjectCreationRequest {
  @NotNull
  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotNull
  @Size(max = 250)
  private String description;
}
