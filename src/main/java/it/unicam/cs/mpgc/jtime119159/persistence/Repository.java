package it.unicam.cs.mpgc.jtime119159.persistence;

import java.util.List;

public interface Repository<T> {
    void save(T entity);

    void update(T entity);

    void delete(T entity);

    T findById(Long id);

    List<T> findAll();
}
