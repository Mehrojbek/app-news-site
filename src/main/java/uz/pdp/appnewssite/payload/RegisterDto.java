package uz.pdp.appnewssite.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDto {

    @NotNull(message = "fullName bo'sh bo'lmasligi kerak")
    private String fullName;

    @NotBlank(message = "username bo'sh bo'lmasligi kerak")
    private String username;

    @NotBlank(message = "password bo'sh bo'lmasligi kerak")
    private String password;

    @NotBlank(message = "prePassword bo'sh bo'lmasligi kerak")
    private String prePassword;
}
