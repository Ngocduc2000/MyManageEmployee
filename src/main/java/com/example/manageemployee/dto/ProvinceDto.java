package com.example.manageemployee.dto;

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
public class ProvinceDto extends BaseObjectDto{
    private String name;
    private String code;
    private List<DistrictDto> districtDtosList;
    private List<EmployeeDto> employeeDtosList;
    private List<CertificateDto> certificateDtoslist;
    private UUID certificateId;

    public ProvinceDto(Province provinces) {
        if (provinces != null) {
            this.setId(provinces.getId());
            this.code = provinces.getCode();
            this.name = provinces.getName();
            this.districtDtosList = new ArrayList<>();
            for (District district : provinces.getDistricts()) {
                DistrictDto districtDto = new DistrictDto(district);
                this.districtDtosList.add(districtDto);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDistrictDtosList(List<DistrictDto> districtDtosList) {
        this.districtDtosList = districtDtosList;
    }

    public List<EmployeeDto> getEmployeeDtosList() {
        return employeeDtosList;
    }

    public void setEmployeeDtosList(List<EmployeeDto> employeeDtosList) {
        this.employeeDtosList = employeeDtosList;
    }

    public List<CertificateDto> getCertificateDtoslist() {
        return certificateDtoslist;
    }

    public void setCertificateDtoslist(List<CertificateDto> certificateDtoslist) {
        this.certificateDtoslist = certificateDtoslist;
    }

    public UUID getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(UUID certificateId) {
        this.certificateId = certificateId;
    }

    public List<DistrictDto> getDistrictDtosList() {
        return this.districtDtosList;
    }

}
