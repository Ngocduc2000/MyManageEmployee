package com.example.manageemployee.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchEmployeeRequest {
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer minAge;
    private Integer maxAge;
}
