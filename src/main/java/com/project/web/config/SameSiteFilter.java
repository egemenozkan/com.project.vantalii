package com.project.web.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

public class SameSiteFilter extends GenericFilterBean {
    private Logger LOG = LoggerFactory.getLogger(SameSiteFilter.class);

    @Override
    public void doFilter(ServletRequest request,  ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse)response;
        response = addSameSiteCookieAttribute((HttpServletResponse) response);
        chain.doFilter(request, response);
    }    

    private HttpServletResponse addSameSiteCookieAttribute(HttpServletResponse response) {
        Collection<String> header = response.getHeaders(HttpHeaders.SET_COOKIE);
        response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None; Secure"));

        return response;
    }
}
