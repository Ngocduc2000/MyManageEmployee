package com.example.manageemployee.service;

import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.dto.EmployeeDto;
import com.example.manageemployee.request.SearchEmployeeRequest;
import com.example.manageemployee.response.ResponseResult;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService extends GenericService<Employee, UUID> {
    ResponseResult add(EmployeeDto dto);

    List<EmployeeDto> getAllEmployee();

    ResponseEntity<EmployeeDto> getById(UUID id);



    ResponseResult deleteById(UUID id);

    ResponseResult update(EmployeeDto dto);

    ResponseResult add2(EmployeeDto dto);

    byte[] export();


    ResponseResult importFromExcel(String path);

    List<EmployeeDto> search(SearchEmployeeRequest searchEmployeeRequest);
}
