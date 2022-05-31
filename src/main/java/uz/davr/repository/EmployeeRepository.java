package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import uz.davr.dto.response.EmployeeList;
import uz.davr.entity.Employees;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long> {


    @Query(value = "select img.employee_id  as employee_id,\n" +
            "       img.image_bytes as image_bytes,\n" +
            "       img.name as image_name ,\n" +
            "       emp.last_name as last_name ,img.id as image_id,\n" +
            "       emp.first_name as first_name,\n" +
            "       emp.parent_name as parent_name,pos.name as position_name,\n" +
            "       pos.id as position_id\n" +
            "from image_model img ,employees emp, position as pos\n" +
            "where img.employee_id=emp.id and pos.id=emp.positions_id\n" +
            "  and emp.user_id=:user_id and emp.positions_id=:position_id",
            nativeQuery = true)
    List<EmployeeList> getEmployeesByBranchAndPositionID(@Param("user_id") Long user_id,
                                                         @Param("position_id") Long position_id);

    @Query(value = "select * from employees e join position p on p.id = e.positions_id where p.id = :id", nativeQuery = true)
    List<EmployeeList> getAllEmployeesByPosition(@Param("id") Long id);

}
