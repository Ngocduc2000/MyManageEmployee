package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.Certificate;
import com.example.manageemployee.dto.CertificateDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CertificateMapperImpl implements CertificateMapper{
    private final ModelMapper modelMapper;

    public CertificateMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CertificateDto> mapList(List<Certificate> certificates) {
        return certificates.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CertificateDto mapToDto(Certificate certificate) {
        CertificateDto certificateDto = modelMapper.map(certificate, CertificateDto.class);
        certificateDto.setEmployeeId(certificate.getEmployee() == null ? null : certificate.getEmployee().getId());
        certificateDto.setProvinceId(certificate.getProvince() == null ? null : certificate.getProvince().getId());

        return certificateDto;
    }
}
