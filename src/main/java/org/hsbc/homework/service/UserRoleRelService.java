package org.hsbc.homework.service;

import org.hsbc.homework.model.UserRoleRel;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserRoleRelService implements BaseService {

    private MemKVStorage<String, UserRoleRel> tableUserRoleRel = StorageManager.getGlobal().getMem(Constants.TABLE_USER_ROLE_REL);

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();


    public void save(String userName, String roleName) {
        rwLock.writeLock().lock();
        try {
            UserRoleRel rel = tableUserRoleRel.getOrDefault(userName, new UserRoleRel(userName));
            rel.addRole(roleName);
            tableUserRoleRel.put(userName, rel);
        } finally {
            rwLock.writeLock().unlock();
        }


    }

    public UserRoleRel getByUser(String userName) {
        return tableUserRoleRel.get(userName);
    }

    public boolean ifUserHasRole(String userName, String roleName) {
        UserRoleRel rel = getByUser(userName);
        return rel != null && rel.hasRole(roleName);
    }

    public Set<String> getAllRoles(String userName) {
        UserRoleRel rel = getByUser(userName);
        return rel == null ? new HashSet<>() : rel.getRoles();
    }
}
