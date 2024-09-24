package work.skymoyo.mock.core.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import work.skymoyo.mock.core.admin.utils.DateUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            Cookie[] cookies = request.getCookies();

            if (cookies == null) {
                response.sendRedirect("login");
                return false;
            }

            String token = null;
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "token")) {
                    token = cookie.getValue();
                }
            }

            if (!StringUtils.hasLength(token)) {
                response.sendRedirect("login");
                return false;
            }

            String[] split = token.split("·");
            String expiration = SecurityUtil.decrypt(split[1]);
            if (DateUtils.dateTime(DateUtils.YYYYMMDDHHMMSS, expiration).compareTo(new Date()) <= 0) {
                response.sendRedirect("login");
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.warn("登录校验异常:{}", e.getMessage(), e);
            response.sendRedirect("login");
            return false;
        }
    }
}