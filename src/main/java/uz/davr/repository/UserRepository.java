package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davr.entity.User;

import java.util.Optional;

/**
 * Created by Oybek Karimjanov
 * Date : 5.27.2022
 * Project Name : customerFeedback
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long id);

}
