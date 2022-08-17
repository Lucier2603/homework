package org.hsbc.homework.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {

    private Map<String, MemKVStorage> memMap = new HashMap();


    private static StorageManager instance;

    public static synchronized StorageManager init() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    // 仅供单测使用
    public static synchronized void reset() {
        instance = null;
    }

    public static StorageManager getGlobal() {
        return instance;
    }

    public void registerMem(String name, MemKVStorage mem) {
        memMap.putIfAbsent(name, mem);
    }
    public MemKVStorage getMem(String name) {
        return memMap.get(name);
    }
}
