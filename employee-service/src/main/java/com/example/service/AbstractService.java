package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@SuppressWarnings("squid:S00119")
public class AbstractService<T, ID> {
    @Autowired protected JpaRepository<T, ID> repository;

    public T create(T entity) {
        log.info("Creating " + entity.getClass() + " : " + entity);
        return repository.save(entity);
    }

    public Optional<T> findById(ID id) {
        log.info("Find by id: " + id);
        return repository.findById(id);
    }

    public List<T> getAll() {
        log.info("Getting all ");
        return repository.findAll();
    }

    public Page<T> getAll(Pageable pageable) {
        log.info("Getting all");
        return repository.findAll(pageable);
    }
}
