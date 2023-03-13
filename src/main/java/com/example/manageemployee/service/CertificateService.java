package com.example.manageemployee.service;

import com.example.manageemployee.domain.Certificate;
import com.example.manageemployee.dto.CertificateDto;
import com.example.manageemployee.request.SearchCertificateRequest;
import com.example.manageemployee.response.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CertificateService extends GenericService<Certificate, UUID>{
    ResponseResult add(CertificateDto dto);

    List<CertificateDto> getAll();


    ResponseEntity<CertificateDto> getById(UUID id);

    ResponseResult deleteById(UUID id);

    List<CertificateDto> search(SearchCertificateRequest searchCertificateRequest);

    ResponseResult add2(CertificateDto dto);

    ResponseResult update(CertificateDto dto);
}
