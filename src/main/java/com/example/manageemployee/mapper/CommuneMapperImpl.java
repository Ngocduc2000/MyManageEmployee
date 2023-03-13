package com.example.manageemployee.mapper;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.dto.CommuneDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommuneMapperImpl {
    private final ModelMapper modelMapper;

    public CommuneMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<CommuneDto> mapList(List<Commune> list) {
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public CommuneDto map(Commune commune) {
        CommuneDto communeDto = modelMapper.map(commune, CommuneDto.class);
        return communeDto;
    }
}
