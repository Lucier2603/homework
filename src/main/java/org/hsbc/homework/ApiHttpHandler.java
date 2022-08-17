package org.hsbc.homework;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;
import org.hsbc.homework.model.Router;
import org.hsbc.homework.msg.BaseRequest;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CreateUserReq;
import org.hsbc.homework.utils.JsonUtil;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class ApiHttpHandler implements HttpHandler {

    private ApiRouter apiRouter;

    public void setApiRouter(ApiRouter router) {
        this.apiRouter = router;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream reqStream = exchange.getRequestBody();
        String req = IOUtils.toString(reqStream, "UTF-8");

        String resp = doHandle(exchange.getRequestURI().toString(), req);

        exchange.sendResponseHeaders(200, 0);
        OutputStream os = exchange.getResponseBody();
        os.write(resp.getBytes("UTF-8"));
        os.close();
    }

    private String doHandle(String url, String req) {

        try {
            Router router = apiRouter.getByUrl(url);

            BaseRequest request = (BaseRequest) JsonUtil.toBean(req, router.getArgType());
            BaseResponse response = (BaseResponse) router.getMethod().invoke(router.getApiManager(), request);

            return JsonUtil.toJson(response);

        } catch (InvocationTargetException ie) {
            Throwable te = ie.getTargetException();
            if (te.getClass() == BusinessException.class) {
                BusinessException be = (BusinessException) te;
                return JsonUtil.toJson(new BaseResponse().fail(be.getErrorCode()));
            }
            ie.getTargetException().printStackTrace();
            return JsonUtil.toJson(new BaseResponse().fail(ErrorCode.SYSTEM_ERROR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonUtil.toJson(new BaseResponse().fail(ErrorCode.SYSTEM_ERROR));
    }
}