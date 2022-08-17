package org.hsbc.homework.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemKVStorage<String,V> implements KVStorage<String,V> {

    private final ConcurrentHashMap<String, V> map;

    private final int maxSize;

    public MemKVStorage(int mazSize) {
        this.map = new ConcurrentHashMap<String, V>();
        this.maxSize = mazSize;
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public V get(String key) {
        return map.get(key);
    }

    public V getOrDefault(String key, V defaultV) {
        return map.getOrDefault(key, defaultV);
    }

    public V put(String key, V value) {
        if (ifSizeExceed()) {
            throw new RuntimeException("KV Storage size exceed!");
        }

        return map.put(key, value);
    }

    public V putIfAbsent(String key, V value) {
        if (ifSizeExceed()) {
            throw new RuntimeException("KV Storage size exceed!");
        }

        return map.putIfAbsent(key, value);
    }

    public V remove(String key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }


    private boolean ifSizeExceed() {
        return map.size() >= maxSize;
    }
}
