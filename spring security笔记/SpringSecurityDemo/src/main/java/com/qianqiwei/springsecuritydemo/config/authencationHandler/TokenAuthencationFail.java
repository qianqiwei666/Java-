package com.qianqiwei.springsecuritydemo.config.authencationHandler;

import com.qianqiwei.springsecuritydemo.utils.HttpStatusForProject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthencationFail{

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        log.info("返回错误码到客户端.....");
        response.sendError(HttpStatusForProject.AuthenticationFail,message);
    }
}
