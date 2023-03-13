package com.example.manageemployee.service;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface GenericService<T, Idt extends Serializable> {
    T delete(Idt id);

    T save(T t);

    Page<T> getList(int pageIndex, int pageSize);

    T findById(Idt id);
}
