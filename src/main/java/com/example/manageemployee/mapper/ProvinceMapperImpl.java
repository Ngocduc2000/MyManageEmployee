package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.Province;
import com.example.manageemployee.dto.ProvinceDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProvinceMapperImpl {
    private final ModelMapper modelMapper;

    public ProvinceMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<ProvinceDto> mapList(List<Province> list) {
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ProvinceDto map(Province province) {
        ProvinceDto provinceDto = modelMapper.map(province, ProvinceDto.class);
        return provinceDto;
    }
}
