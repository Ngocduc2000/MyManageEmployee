package com.example.manageemployee.service;

import com.example.manageemployee.domain.District;
import com.example.manageemployee.dto.DistrictDto;
import com.example.manageemployee.request.SearchDistrictRequest;
import com.example.manageemployee.response.ResponseResult;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DistrictService extends GenericService<District, UUID>{
    ResponseResult add(DistrictDto dto);

    List<DistrictDto> getAllDistrict();


    ResponseEntity<DistrictDto> getById(UUID id);

    ResponseResult deleteById(UUID id);

    List<DistrictDto> search(SearchDistrictRequest searchDistrictRequest);

    ResponseResult update(DistrictDto dto);

    ResponseResult add2(DistrictDto dto);
}
