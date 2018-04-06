package wang.crick.business.pharos.aop;

import wang.crick.business.pharos.annotation.Anonymous;
import wang.crick.business.pharos.directory.RestfulErrorCode;
import wang.crick.business.pharos.exception.RestfulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 白名单校验
 */
public class AuthorInterceptor extends HandlerInterceptorAdapter {

    @Value("#{'${white.list}'.split(',')}")
    private List<String> whiteList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getBeanType().isAnnotationPresent(Anonymous.class)) {
                return true;
            }
        }
        String clientIp = getIpAddress(request);
        if (!whiteList.contains(clientIp)) {
            throw new RestfulException("client ip: " + clientIp + " not in white list", RestfulErrorCode.AUTHOR_ERROR);
        }
        return super.preHandle(request, response, handler);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}