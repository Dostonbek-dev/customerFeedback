package uz.davr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.UserDto;
import uz.davr.entity.User;
import uz.davr.facade.UserFacade;
import uz.davr.repository.validations.ResponseErrorValidation;
import uz.davr.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ResponseErrorValidation errorValidation;

    @GetMapping("/")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDto userDto = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDto userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);

        UserDto userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }



}
