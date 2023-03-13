package com.example.manageemployee.dto;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.domain.District;
import com.example.manageemployee.domain.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommuneDto extends BaseObjectDto{
    private String code;
    private String name;
    private UUID id;
    private UUID districtId;
    private UUID provinceId;
    private String districtName;
    private String provinceName;

    public CommuneDto(Commune entity) {
        if (entity != null) {
            District district = entity.getDistrict();
            Province province = district == null ? null : district.getProvince();
            this.setId(entity.getId());
            this.name = entity.getName();
            this.districtId = district == null ? null : district.getId();
            this.districtName = district == null ? null : district.getName();
            this.provinceId = province == null ? null : province.getId();
            this.provinceName = province == null ? null : province.getName();
        }
    }

    public Commune toEntity() {
        Commune commune = new Commune();
        commune.setName(this.getName());
        commune.setCode(this.getCode());
        commune.setDistrictId(this.getDistrictId());
        return commune;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDistrictId(UUID districtId) {
        this.districtId = districtId;
    }

    public UUID getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public UUID getDistrictId() {
        return districtId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
