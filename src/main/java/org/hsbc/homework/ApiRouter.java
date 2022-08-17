package org.hsbc.homework;

import org.hsbc.homework.api.HttpApi;
import org.hsbc.homework.model.Router;
import org.hsbc.homework.utils.RouterUrl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApiRouter {

    private Map<String, Router> routers = new HashMap<>();

    public void register(HttpApi api) {
        Method[] methods = api.getClass().getDeclaredMethods();

        for (Method m : methods) {
            Class<?>[] argTypes = m.getParameterTypes();
            if (!m.isAnnotationPresent(RouterUrl.class)) continue;
            // 设定最多只有一个参数
            String url = m.getAnnotation(RouterUrl.class).value();
            Router router = new Router(api, m, argTypes.length == 0 ? null : argTypes[0]);

            routers.put(url, router);
        }
    }

    public Router getByUrl(String url) {
        return routers.get(url);
    }
}
