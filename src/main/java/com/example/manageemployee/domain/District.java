package com.example.manageemployee.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "A_district")
public class District extends BaseObject {
    @Column(name = "name")
    private String name;

    @Column(name ="Code")
    private String code;

    //qh vs xã 1-n
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)//xóa dl liên quan ở bảng district
    private List<Commune> communes;
    //qh vs tỉnh n-1
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Employee> employees;
    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;


    public String getCode() {
        return code;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public District getDistrict() {
        if (this.getProvince() == null) {
            // Nếu huyện hiện tại chưa thuộc tỉnh nào thì trả về chính nó
            return this;
        } else {
            // Ngược lại, lấy tỉnh liên kết và gọi phương thức getDistrict() trên tỉnh
            return this.getProvince().getDistrict();
        }
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }
}
