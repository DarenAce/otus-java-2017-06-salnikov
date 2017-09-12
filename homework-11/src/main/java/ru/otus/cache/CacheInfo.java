package ru.otus.cache;

public interface CacheInfo {
    long getCacheHitCount();
    long getCacheMissCount();
}
