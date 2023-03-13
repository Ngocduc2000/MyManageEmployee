package com.example.manageemployee.controller;

import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.dto.EmployeeDto;
import com.example.manageemployee.request.SearchEmployeeRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.EmployeeService;
import com.example.manageemployee.validator.EmployeeValidator;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseResult add(@RequestBody EmployeeDto dto){
        return employeeService.add(dto);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> result = employeeService.getAllEmployee();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<EmployeeDto> getById(@RequestParam(value = "id") UUID id) {
        return employeeService.getById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> search(@RequestParam(name = "min_age", required = false) Integer minAge,
                                                    @RequestParam(name = "max_age", required = false) Integer maxAge,
                                                    @RequestParam(name = "code", required = false) String code,
                                                    @RequestParam(name = "name", required = false) String name,
                                                    @RequestParam(name = "email", required = false) String email,
                                                    @RequestParam(name = "phone", required = false) String phone) {
        SearchEmployeeRequest searchEmployeeRequest = SearchEmployeeRequest.builder()
                .code(code)
                .email(email)
                .maxAge(maxAge)
                .minAge(minAge)
                .name(name)
                .phone(phone)
                .build();
        List<EmployeeDto> list1 = employeeService.search(searchEmployeeRequest);
        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    @PostMapping("/valid")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
        Set<ConstraintViolation<Employee>> violations = EmployeeValidator.validateEmployee(employee);
        if (violations.isEmpty()) {
            // lưu nhân viên vào cơ sở dữ liệu
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            StringBuilder messageBuilder = new StringBuilder();
            for (ConstraintViolation<Employee> violation : violations) {
                messageBuilder.append(violation.getMessage()).append("\n");
            }
            return new ResponseEntity<>(messageBuilder.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseResult deleteById(@RequestParam(value = "id")UUID id){
        return employeeService.deleteById(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody EmployeeDto dto) {
        return employeeService.update(dto);
    }

    @PostMapping("/add2")
    public ResponseResult add2(@RequestBody EmployeeDto dto) {
        return employeeService.add2(dto);
    }

    @GetMapping("/export")
    public void exportEmployeeData(HttpServletResponse response) {
        byte[] excelFile = employeeService.export();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        try {
            response.getOutputStream().write(excelFile);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping( "/importFromExcel")
    public ResponseResult importFromExcel(@RequestParam String path) {
        return employeeService.importFromExcel(path);
    }
}
