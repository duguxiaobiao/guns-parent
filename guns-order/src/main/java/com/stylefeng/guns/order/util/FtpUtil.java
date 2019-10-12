package com.stylefeng.guns.order.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ztkj-hzb
 * @Date 2019/9/26 15:43
 * @Description ftp工具类
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "ftp")
@Data
public class FtpUtil {

    /**
     * ip地址
     */
    private String hostName;

    /**
     * 端口
     */
    private Integer port = 21;

    /**
     * ftp登录用户名 没有设值用户名时 需要使用默认的用户名 ftp，否则无法获取数据
     */
    private String userName;

    /**
     * ftp登录密码
     */
    private String password;

    /**
     * ftp客户端
     */
    private FTPClient ftpClient;


    public void init() {
        try {
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("utf-8");
            ftpClient.setControlKeepAliveTimeout(0);
            ftpClient.connect(hostName, port);
            ftpClient.login(userName, password);
        } catch (IOException e) {
            log.error("创建ftp客户端对象异常，异常原因：{}", e.getMessage());
        }
    }


    /**
     * 获取指定ftp路径下的文件内容字符串
     *
     * @param seatPath
     * @return
     */
    public String getFileContentByAddress(String seatPath) {

        if (ftpClient == null) {
            this.init();
        }

        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = ftpClient.retrieveFileStream(seatPath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                content.append(readLine);
            }
        } catch (Exception e) {
            log.error("读取指定路径：{}下的座位分布文件出现异常，异常原因：{}", seatPath, ExceptionUtils.getStackTrace(e));
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("关闭BufferedReader出现异常，异常原因：{}", ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return content.toString();
    }


}
