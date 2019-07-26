package task.manager.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "Parameters for updating an existing project.")
public class UpdateProjectAttributeRequest {

  private String name;
  private String description;
}