package com.example.manageemployee.service.serviceImpl;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.domain.District;
import com.example.manageemployee.domain.Province;
import com.example.manageemployee.dto.DistrictDto;
import com.example.manageemployee.dto.ProvinceDto;
import com.example.manageemployee.mapper.ProvinceMapperImpl;
import com.example.manageemployee.repository.CommuneRepository;
import com.example.manageemployee.repository.DistrictRepository;
import com.example.manageemployee.repository.ProvinceRepository;
import com.example.manageemployee.request.SearchProvinceRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.ProvinceService;
import com.example.manageemployee.spec.ProvinceSpec;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl extends GenericServiceImpl<Province, UUID> implements ProvinceService{

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    private final CommuneRepository communeRepository;

    private final ProvinceMapperImpl provinceMapperImpl;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, DistrictRepository districtRepository, CommuneRepository communeRepository, ProvinceMapperImpl provinceMapperImpl) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
        this.provinceMapperImpl = provinceMapperImpl;
    }

    @Override
    public ResponseResult add(ProvinceDto dto) {
        Province province = new Province();
        province.setName(dto.getName());

        List<District> districts = new ArrayList<>();
        dto.getDistrictDtosList().forEach(districtDto -> {
            Optional.ofNullable(districtDto.getName())
                    .ifPresent(name -> {
                        District district = new District();
                        district.setName(name);
                        district.setProvince(province);
                        districts.add(district);
                    });
        });

        province.setDistricts(districts);
        provinceRepository.save(province);
        return new ResponseResult(true, "Data added successfully");
    }

    @Override
    public ResponseEntity<ProvinceDto> getById(UUID id) {
        Optional<Province> optionalEntity = provinceRepository.findById(id);
        if (optionalEntity.isPresent()) {
            ProvinceDto dto = new ProvinceDto(optionalEntity.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProvinceDto> getAllProvince() {
        return provinceRepository.findAll().stream()
                .map(ProvinceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseResult deleteById(UUID id) {
        provinceRepository.deleteById(id);
        return new ResponseResult(true, "Delete successfully");
    }

    @Override
    public List<ProvinceDto> search(SearchProvinceRequest searchProvinceRequest) {
        List<Province> entities = provinceRepository.findAll(new ProvinceSpec(searchProvinceRequest));

        return provinceMapperImpl.mapList(entities);
    }

    @Override
    public ResponseResult addProDisCom(ProvinceDto dto) {
        if (dto == null) {
            return new ResponseResult(false, "dto is null");
        }

        if (dto.getName() == null) {
            return new ResponseResult(false, "name is null");
        }

        Province province = new Province();
        province.setName(dto.getName());

        List<District> districts = new ArrayList<>();

        if (dto.getDistrictDtosList() == null) {
            return new ResponseResult(false, "districtDtosList is null");
        }

        for (DistrictDto p : dto.getDistrictDtosList()) {
            if (p == null || p.getName() == null) {
                return new ResponseResult(false, "district is null or district name is null");
            }

            District district = new District();
            district.setName(p.getName());
            district.setProvince(province);

            List<Commune> communes = new ArrayList<>();
            if (p.getCommuneDtosList() != null) {
                p.getCommuneDtosList().stream()
                        .filter(Objects::nonNull)
                        .filter(c -> c.getName() != null)
                        .forEach(c -> {
                            Commune commune = new Commune();
                            commune.setName(c.getName());
                            commune.setDistrict(district);
                            communes.add(commune);
                        });
            }

            district.setCommunes(communes);
            districts.add(district);
        }

        province.setDistricts(districts);
        provinceRepository.save(province);

        return new ResponseResult(true, "Successfully saved data to database");
    }

    @Override
    public ResponseResult update(ProvinceDto dto) {
        ResponseResult result = checkValidUpdate(dto);
        if (result.isValid()) {
            return edit(dto);
        } else {
            return result;
        }
    }

    @Override
    public ResponseResult add2(ProvinceDto dto) {
        ResponseResult result = checkValidAdd(dto);
        if (result.isValid()) {
            return add(dto);
        } else {
            return result;
        }
    }

    private ResponseResult checkValidAdd(ProvinceDto dto) {
        if (dto.getName() == null) {
            return new ResponseResult(false, "name is required");
        }
        return new ResponseResult(true);
    }

    private ResponseResult checkValidUpdate(ProvinceDto dto) {
        if (dto.getId() == null || !provinceRepository.existsById(dto.getId())) {
            return new ResponseResult(false, "Province's id was not found");
        }
        return new ResponseResult(true);
    }

    private ResponseResult edit(ProvinceDto dto) {
        Province province = provinceRepository.getOne(dto.getId());
        List<DistrictDto> districtDtosList = dto.getDistrictDtosList();
        List<UUID> districtIds = districtDtosList.stream().map(DistrictDto::getId).collect(Collectors.toList());
        List<District> districts = districtRepository.findAllById(districtIds);

        for (District district : districts) {
            for (DistrictDto districtDto : districtDtosList) {
                if (district.getId().equals(districtDto.getId())) {
                    if (districtDto.getName() != null) {
                        district.setName(districtDto.getName());
                    }
                    break;
                }
            }
        }

        districtRepository.saveAll(districts);

        if (dto.getName() != null) {
            province.setName(dto.getName());
            provinceRepository.save(province);
        }

        return new ResponseResult(true, "Updated successfully");
    }
}
