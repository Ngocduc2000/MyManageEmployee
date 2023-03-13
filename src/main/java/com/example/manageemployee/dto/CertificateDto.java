package com.example.manageemployee.dto;

import com.example.manageemployee.domain.Certificate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto extends BaseObjectDto{
    private String name;
    private String code;
    private String type;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
    private List<ProvinceDto> provinceDtolist;
    private List<EmployeeDto> employeeDTOList;

    private UUID provinceId;
    private UUID employeeId;

    public CertificateDto(Certificate entity) {
        if(entity != null){
            this.setId(entity.getId());
            this.name = entity.getName();
            this.code = entity.getCode();
            this.startDay = entity.getStartDay();
            this.endDay = entity.getEndDay();
            this.type = entity.getType();
            this.employeeId = entity.getEmployee().getId();
            this.provinceId = entity.getProvince().getId();
        }
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getProvinceId() {
        return provinceId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDateTime startDay) {
        this.startDay = startDay;
    }

    public LocalDateTime getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDateTime endDay) {
        this.endDay = endDay;
    }
}
