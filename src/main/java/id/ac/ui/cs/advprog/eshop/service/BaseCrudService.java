package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseCrudService<T> implements CrudService<T> {
    private final CrudRepository<T> repository;

    protected BaseCrudService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T item) {
        return repository.create(item);
    }

    @Override
    public List<T> findAll() {
        Iterator<T> iterator = repository.findAll();
        List<T> allItems = new ArrayList<>();
        iterator.forEachRemaining(allItems::add);
        return allItems;
    }

    @Override
    public T findById(String id) {
        return repository.findById(id);
    }

    @Override
    public T update(String id, T item) {
        return repository.update(id, item);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
