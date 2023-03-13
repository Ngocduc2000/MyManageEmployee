package com.example.manageemployee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {
    private String name;
    private String code;
    private String type;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
