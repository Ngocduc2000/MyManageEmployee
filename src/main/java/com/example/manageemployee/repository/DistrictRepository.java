package com.example.manageemployee.repository;

import com.example.manageemployee.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID>, JpaSpecificationExecutor<District> {
}
