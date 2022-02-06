package com.pwr.psi.backend.interceptor;

import com.pwr.psi.backend.model.entity.ActivityLog;
import com.pwr.psi.backend.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Component
public class ServiceInterceptior implements HandlerInterceptor {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setAddr(request.getRemoteAddr());
        activityLog.setMethod(request.getMethod());
        activityLog.setUsername(request.getRemoteUser());
        activityLog.setUrl(request.getRequestURL().toString());
        activityLog.setHttpStatus(response.getStatus());
        activityLog.setActivityDate(Instant.now());
        activityLogRepository.save(activityLog);
    }
}
