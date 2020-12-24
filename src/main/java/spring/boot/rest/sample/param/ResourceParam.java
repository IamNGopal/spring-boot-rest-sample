package spring.boot.rest.sample.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.boot.rest.sample.annotation.NotNullField;

import org.springframework.http.HttpMethod;

/**
 * Param bean for {@link spring.boot.rest.sample.domain.ResourceDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceParam extends BaseParam {

  private static final long serialVersionUID = 8542867394907970893L;

  @ApiModelProperty(hidden = true)
  private Long id;

  @NotNullField(method = HttpMethod.POST, message = "name cannot be null.")
  private String name; // role's name
  private String description;
}
