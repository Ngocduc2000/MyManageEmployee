package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.Certificate;
import com.example.manageemployee.dto.CertificateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    List<CertificateDto> mapList(List<Certificate> certificates);
}
