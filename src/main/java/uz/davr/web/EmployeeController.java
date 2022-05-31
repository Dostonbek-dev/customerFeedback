package uz.davr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.EmployeeList;
import uz.davr.entity.Employees;
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
                                       Principal principal) throws IOException {
        return ResponseEntity.ok(employeesService.saveEmp(firstname, lastname, parentName, positionId, file, principal));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployee() {
        return ResponseEntity.ok(employeesService.getAllEmployees());
    }

    @GetMapping("byPosition/{positionId}/{branch}")
    public List<EmployeeList> getEmployeeByUserBranchAndPositionId(@PathVariable Long positionId,
                                                                   @PathVariable String branch,
                                                                   Principal principal){
        return  employeesService.getEmployeesByBranchAndPositionID(branch,positionId,principal);
    }

    @PostMapping("customerFeedback")
    public ResponseEntity<?> feedbackFromCustomer(@RequestParam String ball,@RequestParam Long employeeID){
        return ResponseEntity.ok(employeesService.feedbackFromCustomer(ball, employeeID));
    }

    @GetMapping("/getEmpByPosition/{id}")
    public ResponseEntity<?> getEmpByPosition(@PathVariable Long id){
        return ResponseEntity.ok(employeesService.getAllEmployeesByPosition(id));
    }

    @GetMapping("/get-count-emp")
    public ResponseEntity<?> getCountEmp(){
        return ResponseEntity.ok(employeesService.getCountOfEmp());
    }

    @GetMapping("/sum-excellent")
    public ResponseEntity<?> getSumExcellent(){
        return ResponseEntity.ok(employeesService.sumExAmount());
    }

    @GetMapping("/sum-good")
    public ResponseEntity<?> getSumGood(){
        return ResponseEntity.ok(employeesService.sumGoodAmount());
    }

    @GetMapping("/sum-bad")
    public ResponseEntity<?> getSumBad(){
        return ResponseEntity.ok(employeesService.sumBadAmount());
    }

    @GetMapping("/sum-ex-user/{userId}")
    public ResponseEntity<?> getSumExByUser(@PathVariable Long userId){
        return ResponseEntity.ok(employeesService.sumExByUser(userId));
    }

    @GetMapping("/sum-good-user/{userId}")
    public ResponseEntity<?> getSumGoodByUser(@PathVariable Long userId){
        return ResponseEntity.ok(employeesService.sumGoodByUser(userId));
    }

    @GetMapping("/sum-bad-user/{userId}")
    public ResponseEntity<?> getSumBadByUser(@PathVariable Long userId){
        return ResponseEntity.ok(employeesService.sumBadByUser(userId));
    }


}
