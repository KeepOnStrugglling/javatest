package com.javatest.util.ftutl;

import org.apache.commons.codec.Charsets;

import java.nio.charset.Charset;

public class SFtpUtil implements FtUtil {

    private String ip;
    private Integer port;
    private String username;
    private String password;
    private Charset charset = Charsets.UTF_8;

    public SFtpUtil(String ip, Integer port, String username, String password, Charset charset) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        if (charset != null) {
            this.charset = charset;
        }
    }
}
