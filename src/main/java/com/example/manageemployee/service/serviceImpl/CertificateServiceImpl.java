package com.example.manageemployee.service.serviceImpl;

import com.example.manageemployee.domain.Certificate;
import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.domain.Province;
import com.example.manageemployee.dto.CertificateDto;
import com.example.manageemployee.mapper.CertificateMapperImpl;
import com.example.manageemployee.repository.CertificateRepository;
import com.example.manageemployee.repository.EmployeeRepository;
import com.example.manageemployee.repository.ProvinceRepository;
import com.example.manageemployee.request.SearchCertificateRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.CertificateService;
import com.example.manageemployee.spec.CertificateSpec;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl extends GenericServiceImpl<Certificate, UUID> implements CertificateService {
    private final CertificateRepository certificateRepository;

    private final ProvinceRepository provinceRepository;

    private final EmployeeRepository employeeRepository;

    private final CertificateMapperImpl certificateMapperImpl;

    private final ModelMapper modelMapper;

    public CertificateServiceImpl(CertificateRepository certificateRepository, ProvinceRepository provinceRepository, EmployeeRepository employeeRepository, CertificateMapperImpl certificateMapperImpl, ModelMapper modelMapper) {
        this.certificateRepository = certificateRepository;
        this.provinceRepository = provinceRepository;
        this.employeeRepository = employeeRepository;
        this.certificateMapperImpl = certificateMapperImpl;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseResult add(CertificateDto dto) {
        Optional<Province> provinceEntity = provinceRepository.findById(dto.getProvinceId());
        Optional<Employee> employeeEntity = employeeRepository.findById(dto.getEmployeeId());
        if (!provinceEntity.isPresent() || !employeeEntity.isPresent()) {
            return new ResponseResult(false, "Province or Employee not found");
        }
        Certificate entity = new Certificate(dto.getName(), dto.getCode(), dto.getType(), dto.getStartDay(), dto.getEndDay(), provinceEntity.get(), employeeEntity.get());
        certificateRepository.save(entity);
        return new ResponseResult(true, "Data added successfully");
    }

    @Override
    public List<CertificateDto> getAll() {
        return certificateRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, CertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CertificateDto> getById(UUID id) {
        Optional<Certificate> optionalEntity = certificateRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Certificate entity = optionalEntity.get();
            CertificateDto dto = new CertificateDto(entity);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseResult deleteById(UUID id) {
        if (certificateRepository.existsById(id)) {
            certificateRepository.deleteById(id);
            return new ResponseResult(true, "Delete successfully");
        } else {
            return new ResponseResult(false, "Certificate not found");
        }
    }

    @Override
    public List<CertificateDto> search(SearchCertificateRequest searchCertificateRequest) {
        List<Certificate> entities = certificateRepository.findAll(new CertificateSpec(searchCertificateRequest));

        return certificateMapperImpl.mapList(entities);
    }

    @Override
    public ResponseResult add2(CertificateDto dto) {
        ResponseResult result = checkValidAdd(dto);
        if (result.isValid()) {
            return add(dto);
        } else {
            return result;
        }
    }

    @Override
    public ResponseResult update(CertificateDto dto) {
        ResponseResult result = checkValidUpdate(dto);
        if (result.isValid()) {
            return edit(dto);
        } else {
            return result;
        }
    }

    private ResponseResult checkValidAdd(CertificateDto dto) {
        Objects.requireNonNull(dto.getType(), "type is required");
        Objects.requireNonNull(dto.getEmployeeId(), "employeeId is required");
        Objects.requireNonNull(dto.getProvinceId(), "byProvinceId is required");
        Objects.requireNonNull(dto.getStartDay(), "startDay is required");
        Objects.requireNonNull(dto.getEndDay(), "endDay is required");
        return checkValidAdd2(dto);
    }

    private ResponseResult checkValidAdd2(CertificateDto dto) {
        if (!employeeRepository.existsById(dto.getEmployeeId()) || !provinceRepository.existsById(dto.getProvinceId())) {
            return new ResponseResult(false, "employeeId or ProvinceId was not found");
        }
        return checkValidAdd3(dto);
    }

    private ResponseResult checkValidAdd3(CertificateDto dto) {
        int numberOfSameTypeCerti = 1;
        Employee employeeEntity = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
        if (employeeEntity != null && employeeEntity.getCertificates() != null) {
            for (Certificate p : employeeEntity.getCertificates()) {
                if (p.getProvince().getId().equals(dto.getProvinceId())
                        && p.getType().equals(dto.getType())
                        && p.getStartDay().isAfter(LocalDateTime.now())) {
                    return new ResponseResult(false, "Employee's certificate " + dto.getType() + " provided by that Province is still valid");
                }
                if (p.getType().equals(dto.getType())
                        && p.getEndDay().isAfter(LocalDateTime.now())) {
                    numberOfSameTypeCerti++;
                    if (numberOfSameTypeCerti > 3) {
                        return new ResponseResult(false, "not allowed to have 3 valid certificates of the same category");
                    }
                }
            }
        }
        return new ResponseResult(true);
    }

    private ResponseResult checkValidUpdate(CertificateDto dto) {
        if (dto.getId() == null) {
            return new ResponseResult(false, "Certificate's id is required");
        }
        if (!certificateRepository.existsById(dto.getId())) {
            return new ResponseResult(false, "Certificate's id was not found");
        }
        if (dto.getEmployeeId() != null || dto.getProvinceId() != null) {
            return new ResponseResult(false, "Not allowed to edit employeeId or byProvinceId");
        }
        return new ResponseResult(true);
    }

    private ResponseResult edit(CertificateDto dto) {
        Optional<Certificate> optionalEntity = certificateRepository.findById(dto.getId());
        if (optionalEntity.isPresent()) {
            Certificate entity = optionalEntity.get();
            if (dto.getType() != null) {
                entity.setType(dto.getType());
            }
            if (dto.getName() != null) {
                entity.setName(dto.getName());
            }
            if (dto.getCode() != null) {
                entity.setCode(dto.getCode());
            }
            if (dto.getStartDay() != null) {
                entity.setStartDay(dto.getStartDay());
            }
            if (dto.getEndDay() != null) {
                entity.setEndDay(dto.getEndDay());
            }
            certificateRepository.save(entity);
            return new ResponseResult(true, "Updated successfully");
        } else {
            throw new EntityNotFoundException("Certificate not found with id: " + dto.getId());
        }
    }
}
