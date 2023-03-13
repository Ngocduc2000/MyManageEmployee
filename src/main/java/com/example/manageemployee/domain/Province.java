package com.example.manageemployee.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "A_province")
public class Province extends BaseObject {
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;



    // tỉnh qh với huyện 1-n

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)//xóa dl liên quan ở bảng district
    private List<District> districts;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    public Province(String name, String code) {
        this.name = name;
        this.code = code;
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

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }


    public List<UUID> getDistrictsIds() {
        return null;
    }

    public District getDistrict() {
        return null;
    }
}
