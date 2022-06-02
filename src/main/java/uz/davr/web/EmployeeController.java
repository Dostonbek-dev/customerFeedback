package uz.davr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.EmpDto;
import uz.davr.dto.response.EmployeeList;
import uz.davr.service.EmployeesService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Oybek Karimjanov
 * Date : 5.27.2022
 * Project Name : customerFeedback
 */
@RestController
@RequestMapping("/api/employee")
@CrossOrigin
public class EmployeeController {

    private final EmployeesService employeesService;


    public EmployeeController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmp(@RequestParam String lastname,
                                       @RequestParam String firstname,
                                       @RequestParam String parentName,
                                       @RequestParam Long positionId,
                                       @RequestParam MultipartFile file,
                                       @RequestParam String phone,
                                       Principal principal) throws IOException {
        return ResponseEntity.ok(employeesService.saveEmp(firstname, lastname, parentName, positionId, file, phone, principal));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployee() {
        return ResponseEntity.ok(employeesService.getAllEmployees());
    }

    @GetMapping("/byPosition/{positionId}/{branch}")
    public List<EmployeeList> getEmployeeByUserBranchAndPositionId(@PathVariable Long positionId,
                                                                   @PathVariable String branch,
                                                                   Principal principal) {
        return employeesService.getEmployeesByBranchAndPositionID(branch, positionId, principal);
    }

    @PostMapping("customerFeedback")
    public ResponseEntity<?> feedbackFromCustomer(@RequestParam String ball, @RequestParam Long employeeID) {
        return ResponseEntity.ok(employeesService.feedbackFromCustomer(ball, employeeID));
    }

    @GetMapping("/getEmpByPosition/{id}")
    public ResponseEntity<?> getEmpByPosition(@PathVariable Long id) {
        return ResponseEntity.ok(employeesService.getAllEmployeesByPosition(id));
    }

    @GetMapping("/get-count-emp")
    public ResponseEntity<?> getCountEmp() {
        return ResponseEntity.ok(employeesService.getCountOfEmp());
    }

    @GetMapping("/sum-excellent")
    public ResponseEntity<?> getSumExcellent() {
        return ResponseEntity.ok(employeesService.sumExAmount());
    }

    @GetMapping("/sum-good")
    public ResponseEntity<?> getSumGood() {
        return ResponseEntity.ok(employeesService.sumGoodAmount());
    }

    @GetMapping("/sum-bad")
    public ResponseEntity<?> getSumBad() {
        return ResponseEntity.ok(employeesService.sumBadAmount());
    }

    @GetMapping("/sum-ex-user")
    public ResponseEntity<?> getSumExByUser(Principal principal) {
        return ResponseEntity.ok(employeesService.sumExByUser(principal));
    }

    @GetMapping("/sum-good-user")
    public ResponseEntity<?> getSumGoodByUser(Principal principal) {
        return ResponseEntity.ok(employeesService.sumGoodByUser(principal));
    }

    @GetMapping("/sum-bad-user")
    public ResponseEntity<?> getSumBadByUser(Principal principal) {
        return ResponseEntity.ok(employeesService.sumBadByUser(principal));
    }

    @GetMapping("/employeeById/{empId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String empId) {
        return ResponseEntity.ok(employeesService.getById(Long.parseLong(empId)));
    }

    @PutMapping("/updateEmployee/{empId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long empId,
                                            @RequestParam String firstname,
                                            @RequestParam String lastname,
                                            @RequestParam String parentName,
                                            @RequestParam String positions,
                                            @RequestParam String phone
    ) {
        EmpDto empDto = new EmpDto();
        empDto.setFirstname(firstname);
        empDto.setLastname(lastname);
        empDto.setPhone(phone);
        empDto.setParentName(parentName);
        return ResponseEntity.ok(employeesService.updateEmployee(empId, empDto));

    }

    @GetMapping("/get-fio-result")
    public ResponseEntity<?> getFIOAndResult() {
        return ResponseEntity.ok(employeesService.getFIOAndResult());
    }

}
