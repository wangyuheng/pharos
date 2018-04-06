package wang.crick.business.pharos.aop;

import wang.crick.business.pharos.annotation.Anonymous;
import wang.crick.business.pharos.directory.RestfulErrorCode;
import wang.crick.business.pharos.exception.RestfulException;
import wang.crick.business.pharos.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * 接口参数签名校验
 */
public class SignInterceptor extends HandlerInterceptorAdapter {

    @Value("${secret.key}")
    public String secretKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getBeanType().isAnnotationPresent(Anonymous.class)) {
                return true;
            }
        }

        String sign = request.getParameter("sign");
        if (null == sign || "".equals(sign)) {
            throw new RestfulException("must have a sign param!", RestfulErrorCode.SIGN_ERROR);
        } else {
            Map<String, String[]> parameters = request.getParameterMap();
            if (parameters.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String key : parameters.keySet()) {
                    if ("sign".equals(key)) {
                        continue;
                    }
                    sb.append(key).append("-").append(Arrays.toString(parameters.get(key))).append("-");
                }
                sb.append("token").append("-").append(secretKey);
                if (!sign.equals(EncryptUtil.sha1(sb.toString()))) {
                    throw new RestfulException("sign check fail!", RestfulErrorCode.SIGN_ERROR);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

}