package com.javatest.util.ftutl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.net.ftp.FTPClient;

import java.nio.charset.Charset;

@Slf4j
public class FtpUtil implements FtUtil {

    private FTPClient ftpClient;

    private String ip;
    private Integer port;
    private String username;
    private String password;
    private Charset charset = Charsets.UTF_8;

    public FtpUtil(String ip, Integer port, String username, String password, Charset charset) {
        this.ip = ip;
        this.port = port <= 0 ? 21 : port;
        this.username = username;
        this.password = password;
        if (charset != null) {
            this.charset = charset;
        }
    }
}
