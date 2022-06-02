package uz.davr.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.UserDto;
import uz.davr.dto.response.BranchByFeedBackCount;
import uz.davr.dto.response.BranchByFeedBackLevel;
import uz.davr.entity.User;
import uz.davr.facade.UserFacade;
import uz.davr.repository.validations.ResponseErrorValidation;
import uz.davr.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {


    private final UserService userService;

    private final ResponseErrorValidation errorValidation;

    public UserController(UserService userService, ResponseErrorValidation errorValidation) {
        this.userService = userService;
        this.errorValidation = errorValidation;
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDto userDto = UserFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllBranches());
    }

    @GetMapping("/get-count")
    public ResponseEntity<?> getBranches(){
        return ResponseEntity.ok(userService.getCountOfUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDto userDTO = UserFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);

        UserDto userUpdated = UserFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @GetMapping("feedBackCountByBranch")
    public List<BranchByFeedBackCount>  getBranchByFeedBackCount(){
        return userService.getBranchByFeedBackCount();
    }
    @GetMapping("/branchByLevel")
    public List<BranchByFeedBackLevel> branchByFeedBackLevel(){
        return userService.branchByFeedBackLevel();
    }



}
