package org.example;

import java.util.List;

public interface Dao<T> extends AutoCloseable {
    T read(String name) throws DaoException;

    void write(String name, T object) throws DaoException;

    List<String> names() throws DaoException;
}
