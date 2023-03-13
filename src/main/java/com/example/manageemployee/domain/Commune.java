package com.example.manageemployee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "A_commune")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commune extends BaseObject {


    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;


    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL)
    private List<Employee> employees;
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Province getProvince() {
        return this.district.getProvince();
    }

    public void setDistrictId(UUID districtId) {

    }
}
