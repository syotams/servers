package com.opal.cache.server;

public interface IStorage {

    void put(String key, String value);

    String get(String key);
}
