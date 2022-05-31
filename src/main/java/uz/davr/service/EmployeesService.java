package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.EmployeeDto;
import uz.davr.dto.response.EmployeeList;
import uz.davr.entity.Employees;
import uz.davr.entity.ImageModel;
import uz.davr.entity.Positions;
import uz.davr.entity.User;
import uz.davr.repository.EmployeeRepository;
import uz.davr.repository.ImageRepository;
import uz.davr.repository.PositionRepository;
import java.io.IOException;
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
    private final ImageRepository imageRepository;

    public EmployeeDto saveEmp(String firstname,
                               String lastname,
                               String parentName,
                               Long positionId,
                               MultipartFile file,
                               Principal principal) throws IOException {
        User user = userService.getCurrentUser(principal);
        Employees employees = new Employees();
        employees.setLastname(lastname);
        employees.setFirstname(firstname);
        employees.setParentName(parentName);
        Optional<Positions> byId = positionRepository.findById(positionId);
        if (byId.isPresent()) {
            Positions positions = byId.get();
            employees.setPositions(positions);
        }
        employees.setUser(user);
        employeeRepository.save(employees);
        ImageModel imageModel = new ImageModel();
        imageModel.setName(file.getOriginalFilename());
        imageModel.setImageBytes(file.getBytes());
        imageModel.setEmployeeId(employees.getId());
        imageRepository.save(imageModel);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstname(employees.getFirstname());
        employeeDto.setLastname(employees.getLastname());
        employeeDto.setParentName(employees.getParentName());
        employeeDto.setBranch(user.getBranchCode());
        employeeDto.setPositionId(employees.getPositions().getId());
        employeeDto.setImageModel(imageModel);
        return employeeDto;
    }

    public String deleteEmp(Long id) {
        employeeRepository.deleteById(id);
        LOG.info("Employee is successfully deleted by Id! ");
        return "Employee is successfully deleted by Id! ";
    }

    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeList> getEmployeesByBranchAndPositionID(String branch, Long positionId,Principal principal){
        User currentUser = userService.getCurrentUser(principal);
        return employeeRepository.getEmployeesByBranchAndPositionID(currentUser.getId(), positionId);
    }

    public boolean feedbackFromCustomer(String ball,Long employeeID){
        Optional<Employees> employeeById = employeeRepository.findById(employeeID);
        if (employeeById.isPresent()){
            Employees employees = employeeById.get();
            switch (ball) {
                case "excellent": {
                    int excellent = employees.getExcellent() + 1;
                    employees.setExcellent(excellent);
                    break;
                }
                case "good": {
                    int excellent = employees.getGood() + 1;
                    employees.setGood(excellent);
                    break;
                }
                case "bad": {
                    int excellent = employees.getBad() + 1;
                    employees.setBad(excellent);
                    break;
                }
            }
            employeeRepository.save(employees);
            return true;
        }else {
            return  false;
        }

    }
}
