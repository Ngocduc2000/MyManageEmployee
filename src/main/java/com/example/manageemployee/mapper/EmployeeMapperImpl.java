package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapperImpl implements EmployeeMapper{
    private final ModelMapper modelMapper;

    public EmployeeMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public List<EmployeeDto> mapList(List<Employee> employees) {
        return employees.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setCommuneId(employee.getCommune() == null ? null : employee.getCommune().getId());
        employeeDto.setDistrictId(employee.getDistrict() == null ? null : employee.getDistrict().getId());
        employeeDto.setProvinceId(employee.getProvince() == null ? null : employee.getProvince().getId());

        return employeeDto;
    }
}
