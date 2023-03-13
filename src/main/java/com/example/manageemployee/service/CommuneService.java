package com.example.manageemployee.service;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.dto.CommuneDto;
import com.example.manageemployee.request.SearchCommuneRequest;
import com.example.manageemployee.response.ResponseResult;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CommuneService extends GenericService<Commune, UUID>{
    ResponseResult add(CommuneDto dto);

    ResponseEntity<CommuneDto> getById(UUID id);

    List<CommuneDto> getAllCommune();


    ResponseResult add2(CommuneDto dto);

    ResponseResult deleteById(UUID id);

    List<CommuneDto> search(SearchCommuneRequest searchCommuneRequest);

    ResponseResult update(CommuneDto dto);
}
