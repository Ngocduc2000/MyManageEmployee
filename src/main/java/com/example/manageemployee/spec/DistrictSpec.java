package com.example.manageemployee.spec;

import com.example.manageemployee.domain.District;
import com.example.manageemployee.request.SearchDistrictRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DistrictSpec implements Specification<District> {
    private SearchDistrictRequest searchDistrictRequest;

    @Override
    public Predicate toPredicate(Root<District> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(searchDistrictRequest.getId())) {
            predicates.add(criteriaBuilder.equal(root.get("id"), searchDistrictRequest.getId()));
        }

        if (!StringUtils.isEmpty(searchDistrictRequest.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchDistrictRequest.getName() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
