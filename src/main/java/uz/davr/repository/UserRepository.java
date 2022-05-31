package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.davr.dto.response.CountStatus;
import uz.davr.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Oybek Karimjanov
 * Date : 5.27.2022
 * Project Name : customerFeedback
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long id);

    @Query(value = "select count(*) as counter from users", nativeQuery = true)
    CountStatus getCountOfUsers();

    @Query(value = "select roles from user_role where user_id = :id", nativeQuery = true)
    String getUserRole(@Param(value = "id") Long id);

}
