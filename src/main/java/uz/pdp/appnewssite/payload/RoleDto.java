package uz.pdp.appnewssite.payload;

import lombok.Data;
import uz.pdp.appnewssite.entity.enums.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Data
public class RoleDto {

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<Permission> permissions;
}
