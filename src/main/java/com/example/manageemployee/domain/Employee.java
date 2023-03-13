package com.example.manageemployee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "A_employee")
@XmlRootElement
@Getter
@Setter
public class Employee extends BaseObject {
    @NotBlank
    @Size(min=6, max=10, message="6 < Code's key < 10")
    @Pattern(regexp="^[^\\s]+$", message="Code must not have space")
    @Column(name = "code")
    private String code;

    @NotBlank(message="Name is required")
    @Column(name = "name")
    private String name;

    @Email(message="Email must match the format")
    @Column(name = "email")
    private String email;

    @NotBlank(message="phone number is required")
    @Size(max=11, message="phone number is less than 11 numbers")
    @Column(name = "phone")
    private String phone;

    @NotNull(message="Age > 0")
    @Column(name = "age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;

    public List<Certificate> getCertificate() {
        List<Certificate> certificate = null;
        return certificate;
    }

    public List<Certificate> getCertificates() {
        return getCertificate();
    }

    public Employee() {
        super();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UUID getId() {
        return super.getId();
    }

    public void setId(UUID id) {
        super.setId(id);
    }

}
