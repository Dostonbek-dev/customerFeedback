package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.davr.entity.Employees;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees,Long> {
}
