package org.hsbc.homework.model;

import org.hsbc.homework.api.HttpApi;

import java.lang.reflect.Method;

public class Router {
    private HttpApi apiManager;
    private Method method;
    private Class<?> argType;

    public Router(HttpApi apiManager, Method method, Class<?> argType) {
        this.apiManager = apiManager;
        this.method = method;
        this.argType = argType;
    }

    public HttpApi getApiManager() {
        return apiManager;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getArgType() {
        return argType;
    }
}
