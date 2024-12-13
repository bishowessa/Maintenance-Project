package com.lms.persistence.repositories;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Repository<T> {
    private final List<T> store = new ArrayList<>();

    public boolean add(T entity) {
        return store.add(entity);
    }

    public boolean removeById(int id) {
        return store.removeIf(e -> e.hashCode() == id);
    }

    public T findById(int id) {
        return store.stream().filter(e -> e.hashCode() == id).findFirst().orElse(null);
    }

    public List<T> findAll() {
        return new ArrayList<>(store);
    }
}
