package com.pipiolo.getinline.repository;

import com.pipiolo.getinline.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface EventReadOnlyRepository<T, ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);
    Iterable<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
}
