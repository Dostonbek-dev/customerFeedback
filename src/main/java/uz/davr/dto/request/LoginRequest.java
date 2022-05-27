package uz.davr.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@Data
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty!")
    private String username;
    @NotEmpty(message = "Password cannot be empty!")
    private String password;

}
