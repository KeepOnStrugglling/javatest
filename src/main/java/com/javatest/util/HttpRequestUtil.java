package com.javatest.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 转发请求工具类
 *
 * @author azure
 */
@Slf4j
@Component
public class HttpRequestUtil {

    //定义一个httpclient连接池
    private PoolingHttpClientConnectionManager connectionManager;
    // 连接配置
    private RequestConfig config;

    // 在初始化时创建资源
    @PostConstruct
    public void init(){
        if (connectionManager == null) {
            connectionManager = new PoolingHttpClientConnectionManager();
            // 将最大连接数增加到200
            connectionManager.setMaxTotal(200);
            // 将每个路由的默认最大连接数增加到20
            connectionManager.setDefaultMaxPerRoute(20);
            // 设置超时配置
            config = RequestConfig.custom()
                    .setSocketTimeout(10000)     // socket超时10s
                    .setConnectTimeout(20000)   // 连接超时20s
                    .setConnectionRequestTimeout(20000)  // 请求超时20s
                    .setCookieSpec(CookieSpecs.STANDARD_STRICT) // 设置cookie管理
                    .build();
        }
        log.info("==============httpclient池初始化完毕================");
    }


    /**
     * 请求转发
     * @param exactUrl 转发的目标地址
     * @param cookies  需要转发时附带的cookie的键值对，可以用map保存，也可以用list保存
     * @param req      request
     * @param res      response
     * @return 请求返回响应的字符串
     */
    public String dispatch(String exactUrl, Map<String,String> cookies, HttpServletRequest req, HttpServletResponse res) {
        String content = null;

        // HttpClients.createDefault()会创建一个httpclient连接池，线程数默认为5，所以不需要手动close
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 但每次调用都会创建httpclient连接池，请求结束后gc回收时也会耗资源，改用自定义的httpclient池中获取httpclient
        CloseableHttpClient httpClient = getHttpClient(exactUrl,cookies);
        CloseableHttpResponse response = null;
        HttpRequestBase httpRequest = null;

        String method = req.getMethod();
        if ("POST".equals(method)) {
            httpRequest = postRequest(exactUrl, req, res);
        } else if ("GET".equals(method)) {
            httpRequest = getRequest(exactUrl, req, res);
        }
        try {
            response = httpClient.execute(httpRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    /* 对于请求转发的返回值，有两种处理方式：
                     * 1.直接写入res中，这种适用在过滤器进行请求转发；
                     * 2.作为方法返回值返回，对其做进一步分析处理
                     */

                    // 1.直接写入res
                    //entity.writeTo(res.getOutputStream());

                    /* 如果不想写finally对response手动close，有两种方法：
                        1)String content = EntityUtils.toString(entity,Consts.UTF_8);	这个方法的返回值是entity里面的内容
                        2)EntityUtils.consume(entity);
                       但一旦调用上述的两种方法，则会关闭流，不能再从CloseableHttpResponse中获取数据
                    */
                    // 2.将请求转发的返回内容转为字符串作为方法返回值返回
                    content = EntityUtils.toString(entity, Consts.UTF_8);
                    EntityUtils.consume(entity);
                }
            }
        } catch (Exception e) {
            log.error("转发出错：", e);
        }
        return content;
    }

    /**
     * 提供一个获取httpclient的方法
     * @param exactUrl 转发地址
     * @param cookies  需要携带的cookies键值对
     */
    public CloseableHttpClient getHttpClient(String exactUrl, Map<String, String> cookies){
        // 转发时携带自定义cookies信息
        CookieStore cookieStore = new BasicCookieStore();
        if (cookies != null) {
            try {
                /* 如果要让转发后目标系统获取到该cookie，**必须指定cookie的domain为转发目标系统的域名**。*/
                URL url = new URL(exactUrl);
                String domain = url.getHost();
                for (Map.Entry<String, String> entry : cookies.entrySet()) {
                    cookieStore.addCookie(formBasicClientCookie(entry.getKey(),entry.getValue(),domain));
                }

            } catch (MalformedURLException e) {
                log.error("解析转发地址域名失败：",e);
            }
        }

        return HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(config).build();
    }

    /**
     * 构建BasicClientCookie
     * @param name cookie的键
     * @param value cookie的值
     * @param domain cookie的域，只有同域的cookie才能被使用
     */
    private BasicClientCookie formBasicClientCookie(String name, String value, String domain) {
        BasicClientCookie basicClientCookie = new BasicClientCookie(name,value);
        // 设置cookie的domain值
        basicClientCookie.setDomain(domain);
        return basicClientCookie;
    }

    /**
     * 构造get请求
     */
    private HttpRequestBase getRequest(String exactUrl, HttpServletRequest req, HttpServletResponse res) {
        return new HttpGet(exactUrl);
    }

    /**
     * 构造post请求
     */
    private HttpRequestBase postRequest(String exactUrl, HttpServletRequest req, HttpServletResponse res) {
        HttpPost httpPost = new HttpPost(exactUrl);
        String contentType = req.getHeader("Content-Type");
        if (StringUtils.isNotBlank(contentType)) {
            httpPost.setHeader("Content-Type", req.getHeader("Content-Type"));
            StringEntity entity = null;
            if (req.getContentType().contains("json")) {
                entity = jsonData(req);  //填充json数据
            } else {
                entity = formData(req);  //填充form数据
            }
            // todo 传输文件时的处理
            httpPost.setEntity(entity);
        }
        return httpPost;
    }

    /**
     * 构造json数据
     */
    private StringEntity jsonData(HttpServletRequest request) {
        try (InputStreamReader is = new InputStreamReader(request.getInputStream(), request.getCharacterEncoding())) {
            BufferedReader reader = new BufferedReader(is);
            //将json数据放到String中
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            //根据json数据创建请求体
            return new StringEntity(sb.toString(), request.getCharacterEncoding());
        } catch (IOException e) {
            log.error(String.format("json请求体构造出错，报错原因为：%s", e.getMessage()));
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造form数据
     */
    private UrlEncodedFormEntity formData(HttpServletRequest request) {
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            List<NameValuePair> pairs = new ArrayList<>();  //存储参数
            Enumeration<String> params = request.getParameterNames();  //获取前台传来的参数
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                pairs.add(new BasicNameValuePair(name, request.getParameter(name)));
            }
            //根据参数创建参数体，以便放到post方法中
            urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, request.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("form请求体构造出错，报错原因为：%s", e.getMessage()));
            e.printStackTrace();
        }
        return urlEncodedFormEntity;
    }
}
