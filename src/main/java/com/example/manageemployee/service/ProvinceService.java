package com.example.manageemployee.service;

import com.example.manageemployee.domain.Province;
import com.example.manageemployee.dto.ProvinceDto;
import com.example.manageemployee.request.SearchProvinceRequest;
import com.example.manageemployee.response.ResponseResult;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProvinceService extends GenericService<Province, UUID>{
    ResponseResult add(ProvinceDto dto);

    ResponseEntity<ProvinceDto> getById(UUID id);

    List<ProvinceDto> getAllProvince();

    ResponseResult deleteById(UUID id);


    List<ProvinceDto> search(SearchProvinceRequest searchProvinceRequest);


    ResponseResult addProDisCom(ProvinceDto dto);

    ResponseResult update(ProvinceDto dto);

    ResponseResult add2(ProvinceDto dto);
}
