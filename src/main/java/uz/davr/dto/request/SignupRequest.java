package uz.davr.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@Data
public class SignupRequest {

    @NotEmpty(message = "Please enter your branch name!")
    private String branchName;
    @NotEmpty(message = "Please enter your branch code!")
    private String branchCode;
    @NotEmpty(message = "Password is required!")
    @Size(min = 6)
    private String password;
    private String confirmPassword;


}
