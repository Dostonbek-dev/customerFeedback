package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.davr.dto.request.EmployeeDto;
import uz.davr.entity.Employees;
import uz.davr.entity.Positions;
import uz.davr.entity.User;
import uz.davr.repository.EmployeeRepository;
import uz.davr.repository.PositionRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeesService {
    public static final Logger LOG = LoggerFactory.getLogger(PositionService.class);
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final PositionRepository positionRepository;

    public Employees saveEmp(EmployeeDto employeeDto, Principal principal) {
        User user = userService.getCurrentUser(principal);
        Employees newEmp = new Employees();
        newEmp.setFirstname(employeeDto.getFirstname());
        newEmp.setLastname(employeeDto.getLastname());
        newEmp.setUser(user);
        Optional<Positions> optionalPosition = positionRepository.findById(employeeDto.getPositionId());
        if (optionalPosition.isPresent()){
            Positions position = optionalPosition.get();
            newEmp.setPositions(position);
        }
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
