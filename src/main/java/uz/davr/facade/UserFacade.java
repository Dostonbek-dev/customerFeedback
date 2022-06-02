package uz.davr.facade;

import org.springframework.stereotype.Component;
import uz.davr.dto.request.UserDto;
import uz.davr.entity.User;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
public class UserFacade {

    public static UserDto userToUserDTO(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setBranchCode(user.getBranchCode());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
