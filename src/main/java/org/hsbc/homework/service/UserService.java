package org.hsbc.homework.service;

import org.hsbc.homework.model.User;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.Utils;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserService implements BaseService {

    private MemKVStorage<String, User> tableUser = StorageManager.getGlobal().getMem(Constants.TABLE_USER);

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();


    public void save(String userName, String passWd) {
        String passmd5 = Utils.md5(passWd);

        rwLock.writeLock().lock();
        try {
            if (tableUser.hasKey(userName)) {
                throw new BusinessException(ErrorCode.USER_ALREADY_EXIST);
            }
            User user = new User(userName, passmd5);
            tableUser.putIfAbsent(userName, user);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void delete(String userName) {
        rwLock.writeLock().lock();
        try {
            if (!tableUser.hasKey(userName)) {
                throw new BusinessException(ErrorCode.USER_NOT_EXIST);
            }
            tableUser.remove(userName);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public boolean checkPasswd(String userName, String passWd) {
        return userExist(userName) && tableUser.get(userName).getPasswdMd5().equals(Utils.md5(passWd));
    }

    public boolean userExist(String userName) {
        rwLock.readLock().lock();
        try {
            return tableUser.hasKey(userName);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
