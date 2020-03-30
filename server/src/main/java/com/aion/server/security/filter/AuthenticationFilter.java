package com.aion.server.security.filter;

import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.LoginRequestHandler;
import com.aion.server.handler.dto.InputUserInfos;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private DBClient dbClient;

    public AuthenticationFilter(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        final InputUserInfos inputUserInfos = new InputUserInfos(
                httpRequest.getHeader("username"),
                httpRequest.getHeader(("password"))
        );

        final LoginRequestHandler loginHandler = new LoginRequestHandler(dbClient, inputUserInfos);

        if (loginHandler.checkRegistered()) {
            chain.doFilter(request, response);
        }
        // User is not authentified in database, we don't go further
    }
}
