package org.hsbc.homework;

import com.sun.net.httpserver.HttpServer;
import org.hsbc.homework.manager.LoginManager;
import org.hsbc.homework.manager.RoleManager;
import org.hsbc.homework.manager.UserManager;
import org.hsbc.homework.manager.UserRoleRelManager;
import org.hsbc.homework.model.Role;
import org.hsbc.homework.model.Token;
import org.hsbc.homework.model.User;
import org.hsbc.homework.model.UserRoleRel;
import org.hsbc.homework.service.*;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.hsbc.homework.utils.Constants.*;

public class Start {

    public static void main(String[] args) throws IOException {
        // 注册存储
        StorageManager storageManager = StorageManager.init();
        StorageManager.getGlobal().registerMem(TABLE_USER, new MemKVStorage<String, User>(MAX_TABLE_SIZE));
        StorageManager.getGlobal().registerMem(TABLE_TOKEN, new MemKVStorage<String, Token>(MAX_TABLE_SIZE));
        StorageManager.getGlobal().registerMem(TABLE_ROLE, new MemKVStorage<String, Role>(MAX_TABLE_SIZE));
        StorageManager.getGlobal().registerMem(TABLE_USER_ROLE_REL, new MemKVStorage<String, UserRoleRel>(MAX_TABLE_SIZE));

        // 注册service
        ServiceManager serviceManager = ServiceManager.init();
        ServiceManager.getGlobal().register(new UserService());
        ServiceManager.getGlobal().register(new RoleService());
        ServiceManager.getGlobal().register(new TokenService());
        ServiceManager.getGlobal().register(new UserRoleRelService());

        // 注册api
        ApiRouter apiRouter = new ApiRouter();
        apiRouter.register(new LoginManager());
        apiRouter.register(new UserManager());
        apiRouter.register(new RoleManager());
        apiRouter.register(new UserRoleRelManager());

        ApiHttpHandler httpHandler = new ApiHttpHandler();
        httpHandler.setApiRouter(apiRouter);

        System.out.println("start...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/api", httpHandler);
        server.start();
    }
}
