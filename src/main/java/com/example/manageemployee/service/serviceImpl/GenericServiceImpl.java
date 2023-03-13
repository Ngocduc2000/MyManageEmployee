package com.example.manageemployee.service.serviceImpl;

import com.example.manageemployee.dto.EmployeeDto;
import com.example.manageemployee.request.SearchEmployeeRequest;
import com.example.manageemployee.service.GenericService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GenericServiceImpl <T, Idt extends Serializable> implements GenericService<T, Idt> {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public JpaRepository<T, Idt> repository;
    @Autowired
    public EntityManager manager;

    public GenericServiceImpl() {
    }

    public T delete(Idt id) {
        T result = this.repository.getOne(id);
        if (result != null) {
            this.repository.deleteById(id);
        }

        return result;
    }

    public T save(T t) {
        T result = this.repository.save(t);
        return result;
    }

    public Page<T> getList(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    public T findById(Idt id) {
        return this.repository.getOne(id);
    }


}
