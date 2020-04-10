package com.opal.cache.server;

public interface StorageInterface {

    void put(String key, String value);

    String get(String key);
}
