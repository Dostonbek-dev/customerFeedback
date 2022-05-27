package uz.davr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.EmployeeDto;
import uz.davr.entity.Employees;
import uz.davr.service.EmployeesService;

import java.security.Principal;

/**
 * Created by Oybek Karimjanov
 * Date : 5.27.2022
 * Project Name : customerFeedback
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeesService employeesService;


    public EmployeeController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmp(@RequestBody EmployeeDto employeeDto, Principal principal){
        return ResponseEntity.ok(employeesService.saveEmp(employeeDto, principal));
    }
}
