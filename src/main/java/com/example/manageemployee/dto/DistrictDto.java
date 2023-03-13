package com.example.manageemployee.dto;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.domain.District;
import com.example.manageemployee.domain.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto extends BaseObjectDto{
    private String name;
    private String code;
    private UUID provinceId;
    private String provinceName;

    private List<CommuneDto> communeDtosList;
    private UUID id;




    public DistrictDto(District entity) {
        if (entity != null) {
            District district = entity.getDistrict();
            Province province = district == null ? null : district.getProvince();
            this.setId(entity.getId());
            this.name = entity.getName();
            this.provinceId = province == null ? null : district.getId();
            this.provinceName = province == null ? null : district.getName();
            this.communeDtosList = new ArrayList<>();
            for (Commune p : entity.getCommunes()) {
                CommuneDto communeDto = new CommuneDto(p);
                this.communeDtosList.add(communeDto);
            }
        }
    }



    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public DistrictDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommuneDto> getCommuneDtosList() {
        return communeDtosList;
    }

    public void setCommuneDtosList(List<CommuneDto> communeDtosList) {
        this.communeDtosList = communeDtosList;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
