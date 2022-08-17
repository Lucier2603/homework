package org.hsbc.homework.storage;

import java.util.Map;

public interface KVStorage<String,V> {
    boolean hasKey(String key);

    V get(String key);

    V put(String key, V value);

    V putIfAbsent(String key, V value);

    V remove(String key);

    int size();
}
