package uz.pdp.appnewssite.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {


    @NotBlank(message = "fullName bo'sh bo'lmasligi kerak")
    private String fullName;

    @NotBlank(message = "username bo'sh bo'lmasligi kerak")
    private String username;

    @NotBlank(message = "password bo'sh bo'lmasligi kerak")
    private String password;

    @NotBlank(message = "roleId bo'sh bo'lmasligi kerak")
    private Long roleId;
}
