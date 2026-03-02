package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface CrudService<T> {
    T create(T item);
    List<T> findAll();
    T findById(String id);
    T update(String id, T item);
    void delete(String id);
}
