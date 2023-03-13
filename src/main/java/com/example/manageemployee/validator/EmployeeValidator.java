package com.example.manageemployee.validator;

import com.example.manageemployee.domain.Employee;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class EmployeeValidator {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static Set<ConstraintViolation<Employee>> validateEmployee(Employee employee) {
        return VALIDATOR.validate(employee);
    }
}
