package org.hsbc.homework.service;

import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {

    private Map<String, BaseService> services = new HashMap();

    private static ServiceManager instance;

    public static synchronized ServiceManager init() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    // 仅供单测使用
    public static synchronized void reset() {
        instance = null;
    }

    public static ServiceManager getGlobal() {
        return instance;
    }

    public void register(BaseService service) {
        String key = service.getClass().getName();
        services.putIfAbsent(key, service);
    }
    public BaseService get(String name) {
        return services.get(name);
    }
}
