package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.davr.dto.request.SignupRequest;
import uz.davr.dto.request.UserDto;
import uz.davr.dto.response.BranchByFeedBackCount;
import uz.davr.dto.response.BranchByFeedBackLevel;
import uz.davr.dto.response.CountStatus;
import uz.davr.entity.User;
import uz.davr.entity.enums.Roles;
import uz.davr.exeptions.UserExistException;
import uz.davr.facade.UserFacade;
import uz.davr.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public User createUser(SignupRequest userIn) {
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

    public User updateUser(UserDto userDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setUsername(userDto.getUsername());
        user.setBranchCode(userDto.getBranchCode());

        return userRepository.save(user);

    }


    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String getByBranchCode(String branchName) {
        User user = userRepository.findUserByUsername(branchName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found !" + branchName));
        return user.getBranchCode();
    }

    public List<UserDto> getAllBranches() {
        List<User> all = userRepository.findAll();
        return all
                .stream()
                .map(UserFacade::userToUserDTO)
                .collect(Collectors.toList());
    }

    public CountStatus getCountOfUsers() {
        return userRepository.getCountOfUsers();
    }

    public String getUserRole(Long id) {
        return userRepository.getUserRole(id);
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.orElse(null);
    }


    public List<BranchByFeedBackCount> getBranchByFeedBackCount() {
      return   userRepository.getBranchByFeedBackCount();
    }
    public List<BranchByFeedBackLevel> branchByFeedBackLevel(){
        return userRepository.branchByFeedBackLevel();
    }

}
