package com.example.manageemployee.dto;

import com.example.manageemployee.domain.Certificate;
import com.example.manageemployee.domain.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EmployeeDto extends  BaseObjectDto{
    private String name;
    private String code;
    private String email;
    private String phone;

    private Integer age;

    private String provinceName;
    private UUID provinceId;
    private UUID districtId;

    private String districtName;
    private UUID communeId;
    private String communeName;

    private List<ProvinceDto> provinceDtoList;
    private List<UUID> UUIDList;
    private List<CommuneDto> communeDtoList;
    private List<CertificateDto> certificateDtoList;

    private ProvinceDto provinceEntity;
    private DistrictDto districtEntity;
    private CommuneDto communeEntity;

    public EmployeeDto() {

    }

    public ProvinceDto getProvinceEntity() {
        return provinceEntity;
    }

    public void setProvinceEntity(ProvinceDto provinceEntity) {
        this.provinceEntity = provinceEntity;
    }

    public DistrictDto getDistrictEntity() {
        return districtEntity;
    }

    public void setDistrictEntity(DistrictDto districtEntity) {
        this.districtEntity = districtEntity;
    }

    public CommuneDto getCommuneEntity() {
        return communeEntity;
    }

    public void setCommuneEntity(CommuneDto communeEntity) {
        this.communeEntity = communeEntity;
    }

    public EmployeeDto(Employee entity) {
        if (entity != null) {
            this.setId(entity.getId());
            this.code = entity.getCode();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.age = entity.getAge();
            if (entity.getProvince() != null) {
                this.provinceId = entity.getProvince().getId();
                this.provinceName = entity.getProvince().getName();
            }
            if (entity.getDistrict() != null) {
                this.districtId = entity.getDistrict().getId();
                this.districtName = entity.getDistrict().getName();
            }
            if (entity.getCommune() != null) {
                this.communeId = entity.getCommune().getId();
                this.communeName = entity.getCommune().getName();
            }
            this.certificateDtoList = new ArrayList<>();
            if (entity.getCertificate() != null) {
                for (Certificate p : entity.getCertificate()) {
                    CertificateDto certificateDto = new CertificateDto(p);
                    this.certificateDtoList.add(certificateDto);
                }
            }

        }
    }

    public EmployeeDto(UUID provinceId, UUID districtId, UUID communeId) {
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.communeId = communeId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public UUID getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public UUID getDistrictId() {
        return districtId;
    }

    public void setDistrictId(UUID districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public UUID getCommuneId() {
        return communeId;
    }

    public void setCommuneId(UUID communeId) {
        this.communeId = communeId;
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public List<ProvinceDto> getProvinceDtoList() {
        return provinceDtoList;
    }

    public void setProvinceDtoList(List<ProvinceDto> provinceDtoList) {
        this.provinceDtoList = provinceDtoList;
    }

    public List<UUID> getUUIDList() {
        return UUIDList;
    }

    public void setUUIDList(List<UUID> UUIDList) {
        this.UUIDList = UUIDList;
    }

    public List<CommuneDto> getCommuneDtoList() {
        return communeDtoList;
    }

    public void setCommuneDtoList(List<CommuneDto> communeDtoList) {
        this.communeDtoList = communeDtoList;
    }

    public List<CertificateDto> getCertificateDtoList() {
        return certificateDtoList;
    }

    public void setCertificateDtoList(List<CertificateDto> certificateDtoList) {
        this.certificateDtoList = certificateDtoList;
    }
}
