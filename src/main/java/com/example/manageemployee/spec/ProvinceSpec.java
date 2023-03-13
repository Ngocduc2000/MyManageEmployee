package com.example.manageemployee.spec;

import com.example.manageemployee.domain.Province;
import com.example.manageemployee.request.SearchProvinceRequest;
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
public class ProvinceSpec implements Specification<Province> {
    private SearchProvinceRequest searchProvinceRequest;

    @Override
    public Predicate toPredicate(Root<Province> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(searchProvinceRequest.getId())) {
            predicates.add(criteriaBuilder.equal(root.get("id"), searchProvinceRequest.getId()));
        }

        if (!StringUtils.isEmpty(searchProvinceRequest.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchProvinceRequest.getName() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
