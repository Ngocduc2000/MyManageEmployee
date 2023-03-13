package com.example.manageemployee.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCommuneRequest {
    private UUID id;
    private String name;
}
