package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class InMemoryCrudRepository<T> implements CrudRepository<T> {
    private final Function<T, String> idGetter;
    private final BiConsumer<T, String> idSetter;
    private final List<T> data = new ArrayList<>();

    public InMemoryCrudRepository(Function<T, String> idGetter, BiConsumer<T, String> idSetter) {
        this.idGetter = idGetter;
        this.idSetter = idSetter;
    }

    @Override
    public T create(T item) {
        if (idGetter.apply(item) == null) {
            idSetter.accept(item, UUID.randomUUID().toString());
        }
        data.add(item);
        return item;
    }

    @Override
    public Iterator<T> findAll() {
        return data.iterator();
    }

    @Override
    public T findById(String id) {
        for (T item : data) {
            if (idGetter.apply(item).equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public T update(String id, T updatedItem) {
        for (int i = 0; i < data.size(); i++) {
            T item = data.get(i);
            if (idGetter.apply(item).equals(id)) {
                idSetter.accept(updatedItem, id);
                data.set(i, updatedItem);
                return updatedItem;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        data.removeIf(item -> idGetter.apply(item).equals(id));
    }
}
