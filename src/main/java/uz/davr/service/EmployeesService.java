package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.*;
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
                               String phone,
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
        employees.setPhone(phone);
        employees.setUser(user);
        Employees save = employeeRepository.save(employees);
        ImageModel imageModel = new ImageModel();
        imageModel.setName(file.getOriginalFilename());
        imageModel.setImageBytes(file.getBytes());
        imageModel.setEmployeeId(employees.getId());
        imageRepository.save(imageModel);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(save.getId());
        employeeDto.setPhone(employees.getPhone());
        employeeDto.setFirstname(employees.getFirstname());
        employeeDto.setLastname(employees.getLastname());
        employeeDto.setParentName(employees.getParentName());
        employeeDto.setBranch(user.getBranchCode());
        employeeDto.setPositionId(employees.getPositions().getId());
        employeeDto.setImageModel(imageModel);
        return employeeDto;
    }

    public MessageResponse deleteEmp(Long id) {
        Optional<Employees> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            Optional<ImageModel> byEmployeeId = imageRepository.findByEmployeeId(byId.get().getId());
            byEmployeeId.ifPresent(imageModel -> imageRepository.deleteById(imageModel.getId()));
            employeeRepository.deleteById(byId.get().getId());
            LOG.info("Employee is successfully deleted by Id! ");
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Employee is successfully deleted by Id! ");
            return messageResponse;
        }else {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Employee not found  by Id! ");
            return messageResponse;
        }
    }

    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeList> getEmployeesByBranchAndPositionID(String branch, Long positionId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        return employeeRepository.getEmployeesByBranchAndPositionID(currentUser.getId(), positionId);
    }

    public boolean feedbackFromCustomer(String ball, Long employeeID) {
        Optional<Employees> employeeById = employeeRepository.findById(employeeID);
        if (employeeById.isPresent()) {
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
        } else {
            return false;
        }
    }

    public List<EmployeeList> getAllEmployeesByPosition(Long id) {
        return employeeRepository.getAllEmployeesByPosition(id);
    }

    public CountStatus getCountOfEmp() {
        return employeeRepository.getEmployeesCount();
    }

    public CountStatus sumExAmount() {
        return employeeRepository.sumExcellentAmount();
    }

    public CountStatus sumGoodAmount() {
        return employeeRepository.sumGoodAmount();
    }

    public CountStatus sumBadAmount() {
        return employeeRepository.sumBadAmount();
    }

    public CountStatus sumExByUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return employeeRepository.sumExByUser(user.getId());
    }

    public CountStatus sumGoodByUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return employeeRepository.sumGoodByUser(user.getId());
    }

    public CountStatus sumBadByUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return employeeRepository.sumBadByUser(user.getId());
    }

    public Employees getById(Long id) {
        Optional<Employees> byId = employeeRepository.findById(id);
        return byId.orElse(null);

    }

    public Employees updateEmployee(Long empId, EmpDto empDto) {
        Optional<Employees> byId = employeeRepository.findById(empId);
        if (byId.isPresent()) {
            Employees employees = byId.get();
            employees.setPhone(empDto.getPhone());
            employees.setParentName(empDto.getParentName());
            employees.setFirstname(empDto.getFirstname());
            employees.setLastname(empDto.getLastname());
            employeeRepository.save(employees);
            return employees;
        } else return null;
    }

    public List<EmpFIOAndResult> getFIOAndResult() {
        return employeeRepository.getFIOAndResult();
    }

    public CountStatus getAllEmployeeByBranch(Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
       return employeeRepository.getAllEmployeeByBranch(currentUser.getId());
    }
    public CountStatus getAllExcellentByBranch(Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        return employeeRepository.getAllExcellentBranch(currentUser.getId());
    }
    public CountStatus getAllBadByBranch(Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        return employeeRepository.getAllBadBranch(currentUser.getId());
    }

    public List<EmployeeListByBranch>  employeeListByBranch(Principal principal){
        User currentUser=userService.getCurrentUser(principal);
        return employeeRepository.employeeListByBranch(currentUser.getId());
    }

    public List<EmployeeOrderBy>  employeeOrderBy(Principal principal){
        User currentUser=userService.getCurrentUser(principal);
        return employeeRepository.employeeOrderBy(currentUser.getId());
    }

}
