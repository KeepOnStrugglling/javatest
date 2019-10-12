package com.javatest.filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@WebFilter(urlPatterns = "*pythontest.html",filterName = "pythonfilter")
public class PythonFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String script = (String) request.getAttribute("script");
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/config/blacklist.properties")));
        if ((line = br.readLine()) != null) {
            if (!script.contains(line)) {
                filterChain.doFilter(request,response);
            } else {
                java.io.PrintWriter printWriter = response.getWriter();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 4404); //自定义
                printWriter.append(jsonObject.toString());
                return;
            }
        }
    }

    @Override
    public void destroy() {

    }
}
