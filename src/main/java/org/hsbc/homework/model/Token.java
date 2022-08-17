package org.hsbc.homework.model;

public class Token {
    private String userName;
    private String token;
    private Long createTime;

    public Token(String userName, String token, Long createTime) {
        this.userName = userName;
        this.token = token;
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
