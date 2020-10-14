package com.javatest.util.ftutl;

import java.nio.charset.Charset;

public interface FtUtil {

    /**
     * 根据协议创建对应的工具类
     * @param protocol   协议
     * @param ip        文件服务器ip
     * @param port      文件服务器端口
     * @param username  用户名
     * @param password  密码
     * @param charset   码表
     * @return          对应的工具类
     */
    public static FtUtil ftFactory(String protocol, String ip, Integer port, String username, String password, Charset charset){
        if ("ftp".equals(protocol)) {
            return new FtpUtil(ip, port, username, password, charset);
        } else if ("sftp".equals(protocol)) {
            return new SFtpUtil(ip, port, username, password, charset);
        }
        return null;
    }

    /**
     * 获取连接
     */
    //boolean connet() throws Exception;

    // 1、判断是否在连接状态
    // 2、获取指定路径下的指定文件
    // 3、获取指定路径下的所有文件
    // 4、上传文件到指定路径

}
