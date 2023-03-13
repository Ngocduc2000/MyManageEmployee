package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.District;
import com.example.manageemployee.dto.DistrictDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DistrictMapperImpl {
    private final ModelMapper modelMapper;

    public DistrictMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<DistrictDto> mapList(List<District> list) {
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public DistrictDto map(District district) {
        DistrictDto districtDto = modelMapper.map(district, DistrictDto.class);
        return districtDto;
    }
}
