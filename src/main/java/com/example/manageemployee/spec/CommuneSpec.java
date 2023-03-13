package com.example.manageemployee.spec;

import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.request.SearchCommuneRequest;
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
public class CommuneSpec implements Specification<Commune> {
    private SearchCommuneRequest searchCommuneRequest;

    @Override
    public Predicate toPredicate(Root<Commune> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(searchCommuneRequest.getId())) {
            predicates.add(criteriaBuilder.equal(root.get("id"), searchCommuneRequest.getId()));
        }

        if (!StringUtils.isEmpty(searchCommuneRequest.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchCommuneRequest.getName() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
