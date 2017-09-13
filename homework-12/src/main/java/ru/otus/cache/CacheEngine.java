package ru.otus.cache;

import java.util.List;

public interface CacheEngine<K, V> {

    void put(CacheElement<K, V> element);

    V get(K key);

    List<V> getAll();

    int getHitCount();

    int getMissCount();

    void dispose();
}
