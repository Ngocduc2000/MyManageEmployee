package com.example.manageemployee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private UUID id;
    private UUID uuidKey;
    private Boolean voided;
    private String code;
    private String name;
    private String email;
    private String phone;
    private int age;
}
