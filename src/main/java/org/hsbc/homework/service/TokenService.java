package org.hsbc.homework.service;

import org.hsbc.homework.model.Token;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.Utils;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

import java.util.Random;

import static org.hsbc.homework.utils.Constants.TOKEN_EXPIRE_TIME;

public class TokenService implements BaseService {

    private MemKVStorage<String, Token> tableToken = StorageManager.getGlobal().getMem(Constants.TABLE_TOKEN);

    public void setToken(String userName, String token) {
        Token t = new Token(userName, token, System.currentTimeMillis());
        tableToken.put(token, t);
    }

    public void removeToken(String token) {
        tableToken.remove(token);
    }

    public String validToken(String token) {
        Token t = tableToken.get(token);

        long now = System.currentTimeMillis();

        if (t == null || now - t.getCreateTime() > TOKEN_EXPIRE_TIME) {
            tableToken.remove(token);
            throw new BusinessException(ErrorCode.LOGIN_EXPIRED);
        }
        return t.getUserName();
    }

    public static String genToken(String userName) {
        String payload1 = Utils.md5(userName);
        String payload2 = Utils.md5(String.valueOf(System.currentTimeMillis()));
        String payload3 = Utils.md5(String.valueOf(new Random().nextLong()));

        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < payload1.length();i++) {
            sb.append(payload1.charAt(i));
            sb.append(payload2.charAt(i));
            sb.append(payload3.charAt(i));
        }

        return sb.toString();
    }
}
