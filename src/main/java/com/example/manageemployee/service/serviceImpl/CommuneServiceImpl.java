package com.example.manageemployee.service.serviceImpl;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.domain.District;
import com.example.manageemployee.dto.CommuneDto;
import com.example.manageemployee.mapper.CommuneMapperImpl;
import com.example.manageemployee.repository.CommuneRepository;
import com.example.manageemployee.repository.DistrictRepository;
import com.example.manageemployee.request.SearchCommuneRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.CommuneService;
import com.example.manageemployee.spec.CommuneSpec;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommuneServiceImpl extends GenericServiceImpl<Commune, UUID> implements CommuneService {
    private final CommuneRepository communeRepository;


    private final DistrictRepository districtRepository;

    private final CommuneMapperImpl communeMapperImpl;

    public CommuneServiceImpl(CommuneRepository communeRepository, DistrictRepository districtRepository, CommuneMapperImpl communeMapperImpl) {
        this.communeRepository = communeRepository;
        this.districtRepository = districtRepository;
        this.communeMapperImpl = communeMapperImpl;
    }

    @Override
    @Transactional
    public ResponseResult add(CommuneDto dto) {
        UUID districtId = dto.getDistrictId();
        if (districtId == null) {
            throw new IllegalArgumentException("District ID is required");
        }

        Commune commune = new Commune();
        commune.setName(dto.getName());

        Optional<District> districtOptional = districtRepository.findById(districtId);
        if (districtOptional.isPresent()) {
            commune.setDistrict(districtOptional.get());
        } else {
            throw new IllegalArgumentException("District not found");
        }

        communeRepository.save(commune);

        return new ResponseResult(true, "Data added successfully");
    }

    @Override
    public ResponseEntity<CommuneDto> getById(UUID id) {
        Optional<Commune> optionalCommune = communeRepository.findById(id);

        if (optionalCommune.isPresent()) {
            CommuneDto dto = new CommuneDto(optionalCommune.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<CommuneDto> getAllCommune() {
        List<Commune> entities = communeRepository.findAll();
        return entities.stream()
                .map(entity -> new CommuneDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseResult add2(CommuneDto dto) {
        ResponseResult result = checkValid(dto);

        if (result.isValid()) {
            return add(dto);
        } else {
            return result;
        }
    }

    @Override
    public ResponseResult deleteById(UUID id) {
        Optional<Commune> optionalCommune = communeRepository.findById(id);

        if (optionalCommune.isPresent()) {
            communeRepository.deleteById(id);
            return new ResponseResult(true, "Data deleted successfully");
        } else {
            return new ResponseResult(false, "Data not found");
        }
    }

    @Override
    public List<CommuneDto> search(SearchCommuneRequest searchCommuneRequest) {
        Specification<Commune> communeSpecification = new CommuneSpec(searchCommuneRequest);
        List<Commune> entities = communeRepository.findAll(communeSpecification);
        return communeMapperImpl.mapList(entities);
    }

    @Override
    public ResponseResult update(CommuneDto dto) {
        ResponseResult result = checkValidUpdate(dto);
        if (result.isValid()) {
            return edit(dto);
        }
        else {
            return result;
        }
    }

    private ResponseResult checkValid(CommuneDto dto) {
        UUID districtId = Optional.ofNullable(dto.getDistrictId())
                .orElseThrow(() -> new IllegalArgumentException("districtId is required"));

        if (!districtRepository.existsById(districtId)) {
            return new ResponseResult(false, "districtId was not found");
        }

        String name = Optional.ofNullable(dto.getName())
                .orElseThrow(() -> new IllegalArgumentException("name is required"));

        return new ResponseResult(true);
    }

    private ResponseResult checkValidUpdate(CommuneDto dto) {
        UUID id = dto.getId();
        if (id == null) {
            return new ResponseResult(false, "Commune's id is required");
        }

        communeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commune's id was not found"));

        return new ResponseResult(true);
    }

    private ResponseResult edit(CommuneDto dto) {
        UUID id = dto.getId();
        Commune commune = communeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commune's id was not found"));

        String name = dto.getName();
        if (name != null) {
            commune.setName(name);
        }

        UUID districtId = dto.getDistrictId();
        if (districtId != null) {
            District district = districtRepository.findById(districtId)
                    .orElseThrow(() -> new IllegalArgumentException("District's id was not found"));

            commune.setDistrict(district);
        }

        communeRepository.save(commune);
        return new ResponseResult(true, "Updated successfully");
    }
}
