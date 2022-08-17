package org.hsbc.homework.model;

import java.util.*;

public class UserRoleRel {
    private String userName;
    private Set<String> roles;

    public UserRoleRel(String userName) {
        this.userName = userName;
        this.roles = new HashSet<>();
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void addRole(String roleName) {
        roles.add(roleName);
    }

    public boolean hasRole(String roleName) {
        return roles.contains(roleName);
    }
}
