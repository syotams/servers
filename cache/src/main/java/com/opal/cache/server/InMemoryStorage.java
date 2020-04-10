package com.opal.cache.server;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage implements StorageInterface {

    private Map<String, String> items = new HashMap<>();


    @Override
    public void put(String key, String value) {
        items.put(key, value);
    }

    @Override
    public String get(String key) {
        return items.get(key);
    }
}
