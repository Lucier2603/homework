package org.hsbc.homework.service;

import org.hsbc.homework.model.Role;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RoleService implements BaseService {

    private MemKVStorage<String, Role> tableRole = StorageManager.getGlobal().getMem(Constants.TABLE_ROLE);

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void save(String roleName) {
        rwLock.writeLock().lock();
        try {
            if (tableRole.hasKey(roleName)) {
                throw new BusinessException(ErrorCode.ROLE_ALREADY_EXIST);
            }
            Role role = new Role();
            role.setName(roleName);
            tableRole.putIfAbsent(roleName, role);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void delete(String roleName) {
        rwLock.writeLock().lock();
        try {
            if (!tableRole.hasKey(roleName)) {
                throw new BusinessException(ErrorCode.ROLE_NOT_EXIST);
            }
            tableRole.remove(roleName);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public boolean roleExist(String roleName) {
        return tableRole.hasKey(roleName);
    }
}
