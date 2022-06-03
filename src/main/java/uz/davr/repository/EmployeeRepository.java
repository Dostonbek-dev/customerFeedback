package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.davr.dto.response.*;
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

    @Query(value = "select e.first_name, e.last_name, e.parent_name, p.name as position_name, img.name as image_name  " +
            "from employees e join position p on p.id = e.positions_id " +
            "join image_model img  on img.employee_id = e.id where p.id = :id", nativeQuery = true)
    List<EmployeeList> getAllEmployeesByPosition(@Param("id") Long id);

    @Query(value = "select count(*)  as counter from employees", nativeQuery = true)
    CountStatus getEmployeesCount();

    @Query(value = "select sum(excellent) as counter  from employees", nativeQuery = true)
    CountStatus sumExcellentAmount();

    @Query(value = "select sum(good) as counter  from employees", nativeQuery = true)
    CountStatus sumGoodAmount();

    @Query(value = "select sum(bad)  as counter from employees", nativeQuery = true)
    CountStatus sumBadAmount();

    @Query(value = "select sum(excellent) as counter  from employees where user_id = :userId ", nativeQuery = true)
    CountStatus sumExByUser(@Param(value = "userId") Long userId);

    @Query(value = "select sum(good) as counter  from employees where user_id = :userId", nativeQuery = true)
    CountStatus sumGoodByUser(@Param(value = "userId") Long userId);

    @Query(value = "select sum(bad)  as counter from employees where user_id = :userId", nativeQuery = true)
    CountStatus sumBadByUser(@Param(value = "userId") Long userId);

    @Query(value = "select first_name as firstname, " +
            "last_name as lastname, " +
            "parent_name as parentName, excellent, good, bad" +
            " from employees", nativeQuery = true)
    List<EmpFIOAndResult> getFIOAndResult();

    @Query(value = "select count(id) as counter from employees where user_id=:userId", nativeQuery = true)
    CountStatus getAllEmployeeByBranch(@Param(value = "userId") Long userId);

    @Query(value = "select sum (excellent) as counter from employees where user_id=:userId", nativeQuery = true)
    CountStatus getAllExcellentBranch(@Param(value = "userId") Long userId);

    @Query(value = "select sum (bad) as counter from employees where user_id=:userId", nativeQuery = true)
    CountStatus getAllBadBranch(@Param(value = "userId") Long userId);

    @Query(value = "select emp.excellent as excellent,\n" +
            "       emp.bad  as bad,\n" +
            "       emp.good as good,\n" +
            "       emp.first_name as firstname,\n" +
            "       emp.last_name as lastname,\n" +
            "       emp.parent_name as parentName,\n" +
            "       emp.phone as phone,\n" +
            "       pos.name as positions\n" +
            "from employees emp,\n" +
            "    position pos\n" +
            "where pos.id = emp.positions_id and emp.user_id=:user_id",nativeQuery = true)
    List<EmployeeListByBranch> employeeListByBranch(@Param("user_id")  Long user_id);

    @Query(value = "select   sum(emp.excellent) as countEx,\n" +
            "       emp.excellent as excellent,\n" +
            "                   emp.bad  as bad,\n" +
            "                   emp.good as good,\n" +
            "                   emp.first_name as firstname,\n" +
            "                   emp.last_name as lastname,\n" +
            "                   emp.parent_name as parentName,\n" +
            "                   emp.phone as phone,\n" +
            "                   pos.name as positions\n" +
            "from employees emp,\n" +
            "                position pos\n" +
            "            where pos.id = emp.positions_id and emp.user_id=:user_id\n" +
            "group by emp.excellent, emp.bad, emp.good,\n" +
            "         emp.last_name, emp.first_name,\n" +
            "         emp.parent_name, emp.phone, pos.name order by countEx DESC",nativeQuery = true)
    List<EmployeeOrderBy> employeeOrderBy(@Param("user_id")  Long user_id);


}
