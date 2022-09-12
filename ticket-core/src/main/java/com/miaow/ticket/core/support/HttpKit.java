package com.miaow.ticket.core.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class HttpKit {

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获取请求IP地址
     */
    public static String getIp() {
        return HttpKit.getRequest().getRemoteHost();
    }

    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>();
        HttpServletRequest request = HttpKit.getRequest();
        Enumeration<String> enums = request.getParameterNames();

        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }

        return values;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;

        try {
            StringBuffer query = new StringBuffer();

            for (Map.Entry<String, String> kv : param.entrySet()) {
                query.append(URLEncoder.encode(kv.getKey(), "UTF-8")).append("=");
                query.append(URLEncoder.encode(kv.getValue(), "UTF-8")).append("&");
            }

            if (query.lastIndexOf("&") > 0) {
                query.deleteCharAt(query.length() - 1);
            }

            String urlNameString = url + "?" + query;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headerFields.keySet()) {
                log.debug("doSend getResponse: key = {}, value = {}", key, headerFields.get(key));
            }

            // 定义 BufferedReader 输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！", e);
        } finally {
            // 关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("向指定URL发送GET方法的请求关闭输入流异常", e2);
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
            StringBuilder subParam = new StringBuilder();
            for (String key : param.keySet()) {
                subParam.append(key).append("=").append(param.get(key)).append("&");
            }
            if (subParam.lastIndexOf("&") > 0) {
                subParam.deleteCharAt(subParam.length() - 1);
            }

            String urlNameString = url + "?" + subParam;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 获取URLConnection对象对应的输入流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            log.debug("向指定URL发送POST方法的请求参数：{}", param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("向指定URL发送POST方法的请求 关闭输入输出流异常", ex);
            }
        }

        return result.toString();
    }
}
