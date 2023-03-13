package com.example.manageemployee.spec;

import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.request.SearchEmployeeRequest;
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
public class EmployeeSpec implements Specification<Employee> {
    private SearchEmployeeRequest searchEmployeeRequest;


    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(searchEmployeeRequest.getCode())) {
            predicates.add(criteriaBuilder.equal(root.get("code"), searchEmployeeRequest.getCode()));
        }

        if (!StringUtils.isEmpty(searchEmployeeRequest.getEmail())) {
            predicates.add(criteriaBuilder.equal(root.get("email"), searchEmployeeRequest.getEmail()));
        }

        if (!StringUtils.isEmpty(searchEmployeeRequest.getPhone())) {
            predicates.add(criteriaBuilder.equal(root.get("phone"), searchEmployeeRequest.getPhone()));
        }

        if (searchEmployeeRequest.getMinAge() != null || searchEmployeeRequest.getMaxAge() != null) {
            int minAgeToSpec = searchEmployeeRequest.getMinAge() != null ? searchEmployeeRequest.getMinAge() : 0;
            int maxAgeToSpec = searchEmployeeRequest.getMaxAge() != null ? searchEmployeeRequest.getMaxAge() : Integer.MAX_VALUE;
            predicates.add(criteriaBuilder.between(root.get("age"), minAgeToSpec, maxAgeToSpec));
        }

        if (!StringUtils.isEmpty(searchEmployeeRequest.getName())) {
            String name = searchEmployeeRequest.getName().toLowerCase();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
