package com.example.manageemployee.spec;

import com.example.manageemployee.domain.Certificate;
import com.example.manageemployee.request.SearchCertificateRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CertificateSpec implements Specification<Certificate> {
    private SearchCertificateRequest searchCertificateRequest;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(searchCertificateRequest.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchCertificateRequest.getName() + "%"));
        }

        if (!StringUtils.isEmpty(searchCertificateRequest.getCode())) {
            predicates.add(criteriaBuilder.equal(root.get("code"), searchCertificateRequest.getCode()));
        }

        if (!StringUtils.isEmpty(searchCertificateRequest.getType())) {
            predicates.add(criteriaBuilder.equal(root.get("type"), searchCertificateRequest.getType()));
        }

        if (searchCertificateRequest.getStartDay() != null || searchCertificateRequest.getEndDay() != null) {
            LocalDateTime startDayToSpec = searchCertificateRequest.getStartDay() != null ? searchCertificateRequest.getStartDay() : LocalDateTime.MIN;
            LocalDateTime endDayToSpec = searchCertificateRequest.getEndDay() != null ? searchCertificateRequest.getEndDay() : LocalDateTime.MAX;
            predicates.add(criteriaBuilder.between(root.get("issuedDate"), startDayToSpec, endDayToSpec));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
