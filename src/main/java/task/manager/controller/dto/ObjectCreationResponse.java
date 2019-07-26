package task.manager.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Generic response after resource creation ")
public class ObjectCreationResponse {

  @ApiModelProperty(value = "Generated id")
  private long id;

  @ApiModelProperty(value = "Type of created object", allowableValues = "Project, Task")
  private String objectType;
}
