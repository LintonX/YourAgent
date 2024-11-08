package com.youragent.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Long save(T t);

    Optional<T> get(long id);

    List<T> getAll();

    void update(T t, String[] params);

    void delete(T t);
}
