package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.davr.entity.Employees;
import uz.davr.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {
    public static final Logger LOG = LoggerFactory.getLogger(PositionService.class);
    private final EmployeeRepository employeeRepository;

    public Employees saveEmp(Employees employees) {
        Employees newEmp = new Employees();
        newEmp.setFirstname(employees.getFirstname());
        newEmp.setLastname(employees.getLastname());
        Employees save = employeeRepository.save(newEmp);
        LOG.info("Employee Successfully  saved employee");
        return save;
    }

    public String deleteEmp(Long id) {
        employeeRepository.deleteById(id);
        LOG.info("Successfully deleted Employee By Id");
        return "Successfully deleted by id !";
    }

    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
