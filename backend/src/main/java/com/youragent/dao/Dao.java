package com.youragent.dao;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T> {

    Long save(T t);

    T get(long id);

    List<T> getAll();

    void update(long id, String column, Object arg);

    void delete(long id);
}
