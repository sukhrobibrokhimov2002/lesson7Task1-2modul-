package uz.sukhrob.lesson7task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.sukhrob.lesson7task1.entity.enums.Permissions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @NotBlank(message = "Role name must not be empty")
    private String name;
    @NotBlank(message = "Role description must not be empty")
    private String description;
    @NotEmpty(message = "Role permission must not be empty")
    private List<Permissions> permissions;

}
