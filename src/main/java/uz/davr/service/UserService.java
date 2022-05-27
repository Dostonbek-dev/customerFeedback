package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.davr.dto.request.SignupRequest;
import uz.davr.dto.request.UserDto;
import uz.davr.entity.User;
import uz.davr.entity.enums.Roles;
import uz.davr.exeptions.UserExistException;
import uz.davr.repository.UserRepository;

import javax.management.relation.Role;
import java.security.Principal;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@Service
@RequiredArgsConstructor
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest userIn){
        User user = new User();
        user.setBranchCode(userIn.getBranchCode());
        user.setUsername(userIn.getBranchName());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Roles.USER);

        try {
            LOG.info("Saving User {}", userIn.getBranchName());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + "already exist. Please check credentials");
        }
    }

    public User updateUser(UserDto userDto, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setBranchCode(userDto.getLastname());

        return userRepository.save(user);

    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


    public User getUserById(Long id) {
       return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String getByBranchCode(String branchName){
        User user = userRepository.findUserByUsername(branchName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found !" + branchName));
        return user.getBranchCode();
    }

}