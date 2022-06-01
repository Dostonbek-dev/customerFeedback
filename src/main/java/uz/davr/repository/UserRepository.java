package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.davr.dto.response.BranchByFeedBackCount;
import uz.davr.dto.response.BranchByFeedBackLevel;
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

    @Query(value = "select us.id, us.username, sum(emp.excellent + emp.bad + emp.good) as summ\n" +
            "from employees emp,\n" +
            "     users us\n" +
            "where emp.user_id = us.id\n" +
            "group by us.username, us.id\n" +
            "order by summ DESC",nativeQuery = true)
    List<BranchByFeedBackCount>   getBranchByFeedBackCount();

    @Query(value = "select us.id as branchId, us.username as branchName, sum(emp.excellent) as exellent, sum(emp.good) as good, sum(emp.bad) as bad\n" +
            "from employees emp,\n" +
            "     users us\n" +
            "where emp.user_id = us.id\n" +
            "group by us.username, us.id",nativeQuery = true)
    List<BranchByFeedBackLevel> branchByFeedBackLevel();


}
