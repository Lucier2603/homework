package org.hsbc.homework.msg;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    private static final long serialVersionUID = -3439632613092969991L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
