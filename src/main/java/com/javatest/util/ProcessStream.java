package com.javatest.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * java执行python的线程工具类
 */
public class ProcessStream extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ProcessStream.class);
    private InputStream inputStream;
    private String streamType;
    private StringBuffer buf;
    private String charset;
    private volatile boolean isStopped = false; // 用于判断线程是否执行完毕，用volatile保证线程安全

    public ProcessStream(InputStream inputStream, String streamType, String charset) {
        this.inputStream = inputStream;
        this.streamType = streamType;
        this.buf = new StringBuffer();
        this.charset = charset;
        this.isStopped = false;
    }

    @Override
    public void run() {
        try {
            // 默认编码为UTF-8，如果有传入编码，则按外部编码
            String exactCharset = StringUtils.isBlank(charset) ? "UTF-8" : charset;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, exactCharset));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                this.buf.append(line + "\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            logger.error("Failed to successfully consume and display the input stream of type " + streamType + ".", e);
//        } finally {
//            this.isStopped = true;
//            synchronized (this) {
//                notify();
//            }
        }
    }

    public String getContent() {
//        if (!this.isStopped) {
//            synchronized (this) {
//                try {
//                    wait();
//                } catch (InterruptedException ignore) {
//                    ignore.printStackTrace();
//                }
//            }
//        }
        return this.buf.toString();
    }
}
