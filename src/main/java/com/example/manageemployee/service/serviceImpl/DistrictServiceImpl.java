package com.example.manageemployee.service.serviceImpl;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.domain.District;
import com.example.manageemployee.domain.Province;
import com.example.manageemployee.dto.CommuneDto;
import com.example.manageemployee.dto.DistrictDto;
import com.example.manageemployee.mapper.DistrictMapperImpl;
import com.example.manageemployee.repository.CommuneRepository;
import com.example.manageemployee.repository.DistrictRepository;
import com.example.manageemployee.repository.ProvinceRepository;
import com.example.manageemployee.request.SearchDistrictRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.DistrictService;
import com.example.manageemployee.spec.DistrictSpec;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
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
public class DistrictServiceImpl extends GenericServiceImpl<District, UUID> implements DistrictService {
    private final DistrictRepository districtRepository;

    private final ProvinceRepository provinceRepository;


    private final CommuneRepository communeRepository;

    private final DistrictMapperImpl districtMapperImpl;

    public DistrictServiceImpl(DistrictRepository districtRepository, ProvinceRepository provinceRepository, CommuneRepository communeRepository, DistrictMapperImpl districtMapperImpl) {
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
        this.communeRepository = communeRepository;
        this.districtMapperImpl = districtMapperImpl;
    }

    @Override
    @Transactional
    public ResponseResult add(DistrictDto dto) {
        District district = new District();
        district.setName(dto.getName());
        district.setCode(dto.getCode());

        List<Commune> communes = dto.getCommuneDtosList().stream()
                .map(communeDto -> {
                    String communeName = Optional.ofNullable(communeDto.getName()).orElse("");
                    Commune commune = new Commune();
                    commune.setName(communeName);
                    commune.setDistrict(district);
                    return commune;
                })
                .collect(Collectors.toList());

        if (communes.isEmpty()) {
            return new ResponseResult(false, "Commune list is empty");
        }

        district.setCommunes(communes);
        districtRepository.save(district);
        return new ResponseResult(true, "Data added successfully");
    }


    @Override
    public List<DistrictDto> getAllDistrict() {
        List<District> entities = districtRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, DistrictDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<DistrictDto> getById(UUID id) {
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isPresent()) {
            DistrictDto districtDto = new DistrictDto(optionalDistrict.get());
            return ResponseEntity.ok(districtDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseResult deleteById(UUID id) {
        Optional<District> districtOptional = districtRepository.findById(id);
        if (!districtOptional.isPresent()) {
            throw new EntityNotFoundException("District with id " + id + " does not exist");
        }

        districtRepository.deleteById(id);
        return new ResponseResult(true, "Delete successfully");
    }

    @Override
    public List<DistrictDto> search(SearchDistrictRequest searchDistrictRequest) {
        List<District> entities = districtRepository.findAll(new DistrictSpec(searchDistrictRequest));

        return districtMapperImpl.mapList(entities);
    }

    @Override
    public ResponseResult update(DistrictDto dto) {
        ResponseResult result = checkValidUpdate(dto);
        if (result.isValid()) {
            return edit(dto);
        }
        else {
            return result;
        }
    }

    @Override
    public ResponseResult add2(DistrictDto dto) {
        ResponseResult result = checkValidAdd(dto);
        if (result.isValid()) {
            return add(dto);
        } else {
            return result;
        }
    }

    private ResponseResult checkValidAdd(DistrictDto dto) {
        return Optional.of(dto)
                .filter(d -> d.getName() != null && d.getProvinceId() != null && provinceRepository.existsById(d.getProvinceId()))
                .map(d -> new ResponseResult(true))
                .orElse(new ResponseResult(false, "Invalid input"));
    }

    private ResponseResult checkValidUpdate(DistrictDto dto) {
        return Optional.of(dto)
                .filter(d -> d.getId() != null && districtRepository.existsById(d.getId()))
                .map(d -> new ResponseResult(true))
                .orElse(new ResponseResult(false, "Invalid input"));
    }

    private ResponseResult edit(DistrictDto dto) {
        District district = districtRepository.findById(dto.getId()).orElse(null);
        if (district == null) {
            return new ResponseResult(false, "District not found");
        }
        if (dto.getName() != null) {
            district.setName(dto.getName());
        }
        if (dto.getProvinceId() != null) {
            Province province = provinceRepository.findById(dto.getProvinceId()).orElse(null);
            if (province == null) {
                return new ResponseResult(false, "Province not found");
            }
            district.setProvince(province);
        }
        if (dto.getCommuneDtosList() != null) {
            List<Commune> communes = dto.getCommuneDtosList().stream()
                    .filter(c -> c.getId() != null) //loại bỏ các đối tượng CommuneDto có giá trị id là null.
                    .map(c -> communeRepository.findById(c.getId()).orElse(null))//chuyển đổi từ danh sách CommuneDto sang danh sách Commune bằng cách sử dụng id của từng CommuneDto
                    // và gọi hàm communeRepository.findById() để lấy đối tượng Commune tương ứng.
                    //Nếu không tìm thấy Commune, phương thức orElse(null) sẽ trả về giá trị null.
                    .filter(c -> c != null) //loại bỏ các đối tượng Commune có giá trị null.
                    .peek(c -> {                        //cập nhật tên cho từng đối tượng Commune trước khi lưu vào cơ sở dữ liệu.
                        // Nếu tên của đối tượng Commune bị thay đổi, phương thức setName() sẽ được gọi để cập nhật lại tên.
                        if (c.getName() != null) {
                            c.setName(c.getName());
                        }
                    })
                    .collect(Collectors.toList()); //thu thập các đối tượng Commune vào một danh sách và lưu vào đối tượng District bằng phương thức district.setCommunes(communes).
            district.setCommunes(communes);
        }
        districtRepository.save(district);
        return new ResponseResult(true, "Updated successfully");
    }
}
