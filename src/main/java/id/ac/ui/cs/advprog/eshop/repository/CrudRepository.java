package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

public interface CrudRepository<T> {
    T create(T item);
    Iterator<T> findAll();
    T findById(String id);
    T update(String id, T item);
    void delete(String id);
}
