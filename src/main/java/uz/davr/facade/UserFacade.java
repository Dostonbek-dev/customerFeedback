package uz.davr.facade;

import org.springframework.stereotype.Component;
import uz.davr.dto.request.UserDto;
import uz.davr.entity.User;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@Component
public class UserFacade {

    public UserDto userToUserDTO(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLastname(user.getBranchCode());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}