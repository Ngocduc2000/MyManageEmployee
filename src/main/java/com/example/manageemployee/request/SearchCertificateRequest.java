package com.example.manageemployee.request;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCertificateRequest {
    private String name;
    private String code;
    private String type;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
