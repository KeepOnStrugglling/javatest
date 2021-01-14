package com.javatest.util;

import lombok.Getter;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author azure
 * @description 用byte数组缓存request的inputStream。可以通过该类的getBody方法获取缓存的数组，从而获取request的入参
 * @attention 当入参为application/json时才需要复写，form类型的入参可以用request.getParameterMap()获取，该方法不会触发inputStream被消费的情况
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    @Getter
    private byte[] body;
    private BufferedReader reader;
    private ServletInputStream is;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        reloadRequest(request);
    }

    private void reloadRequest(HttpServletRequest request) throws IOException {
        // 将request的inputStream转为数组
        body = IOUtils.toByteArray(request.getInputStream());
        // 利用数组重构可重复使用的inputStream
        is = new RequestCachingInputStream(body);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (is != null) {
            return is;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(is, getCharacterEncoding()));
        }
        return reader;
    }


    /**
     * 根据byte数组重构ServletInputStream
     */
    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] body) {
            inputStream = new ByteArrayInputStream(body);
        }
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }
}
