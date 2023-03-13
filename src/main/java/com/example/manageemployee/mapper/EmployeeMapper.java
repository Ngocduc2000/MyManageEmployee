package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.dto.EmployeeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    List<EmployeeDto> mapList(List<Employee> employees);
}
