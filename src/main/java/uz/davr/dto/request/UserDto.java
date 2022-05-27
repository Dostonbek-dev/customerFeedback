package uz.davr.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@Data
public class UserDto {

    private Long id;
    @NotEmpty
    private String lastname;
    private String username;
    private String bio;
}
