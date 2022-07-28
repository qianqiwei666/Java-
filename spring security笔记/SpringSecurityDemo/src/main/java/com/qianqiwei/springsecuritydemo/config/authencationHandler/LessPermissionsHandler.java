package com.qianqiwei.springsecuritydemo.config.authencationHandler;

import com.qianqiwei.springsecuritydemo.utils.HttpStatusForProject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LessPermissionsHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("用户权限不够");
        log.info("返回错误码给用户.....");
        response.sendError(HttpStatusForProject.UserRolePermissions,"用户权限不够");
    }
}
