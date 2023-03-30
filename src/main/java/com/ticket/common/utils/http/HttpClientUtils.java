package com.ticket.common.utils.http;

import com.ticket.common.apiresult.CommonResultPo;
import com.ticket.common.apiresult.ResultCodeEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


/**
 * @description: 通用http发送方法
 * @author: imi
 * @date: 2022/7/26 14:26
 */
public class HttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    // utf-8字符编码
    private static final String CHARSET_UTF_8 = "UTF-8";
    // HTTP内容类型。
    //public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
    //HTTP内容类型。用xml的形式，提交数据
    //public static final String CONTENT_TYPE_TEXT_XML_UTF8 = "text/xml;charset=UTF-8";
    // HTTP内容类型。相当于form表单的形式，提交数据
    private static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
    // HTTP内容类型。相当于json的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=UTF-8";
    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;
    // 请求配置
    private static RequestConfig requestConfig;
    //用来释放不用的连接 closeExpiredConnections() closeIdleConnections(long idletime, TimeUnit tunit)
    //private static HttpClientConnectionMonitorThread thread;
    //保活连接策略
    private static ConnectionKeepAliveStrategy keepAliveStrategy;
    //用来释放不用的连接
    //private static ScheduledExecutorService monitorExecutor;
    //创建HttpClient
    private static CloseableHttpClient httpclient = null;

    /**
     * 初始化参数
     */
    //static {
    //    keepAliveStrategy = (response, context) -> {
    //        HeaderElementIterator it = new BasicHeaderElementIterator
    //                (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
    //        while (it.hasNext()) {
    //            HeaderElement he = it.nextElement();
    //            String param = he.getName();
    //            String value = he.getValue();
    //            if (value != null && "timeout".equalsIgnoreCase
    //                    (param)) {
    //                return Long.parseLong(value) * 1000;
    //            }
    //        }
    //        return 30 * 1000;
    //    };
    //}
    private static CloseableHttpClient getHttpClient() {
        if (null == httpclient) {
            synchronized (HttpClientUtils.class) {
                if (null == httpclient) {
                    httpclient = init();
                }
            }
        }
        return httpclient;
    }

    private static CloseableHttpClient init() {

        // 根据默认超时限制初始化requestConfig
        int socketTimeout = 20 * 1000;
        int connectTimeout = 20 * 1000;
        int connectionRequestTimeout = 20 * 1000;
        int maxTotal = 200;
        int defaultMaxPerRoute = 10;

        try {
            // System.out.println("初始化HttpClientTest~~~开始");
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(maxTotal);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(defaultMaxPerRoute);

            /** 管理 http连接池 */
            //// 开启监控线程,对异常和空闲线程进行关闭
            //monitorExecutor = Executors.newScheduledThreadPool(1);
            //monitorExecutor.scheduleAtFixedRate(new TimerTask() {
            //    @Override
            //    public void run() {
            //        //关闭异常连接
            //        pool.closeExpiredConnections();
            //        //关闭空闲的连接
            //        pool.closeIdleConnections(connectTimeout, TimeUnit.SECONDS);
            //        logger.info("runOnce:HttpClientConnectionMonitorThread");
            //    }
            //}, 30, 30, TimeUnit.SECONDS);

            // 设置请求超时时间
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            // System.out.println("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        if (null == requestConfig) {
            // 设置请求超时时间
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        }

        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                //设置连接时长
                //.setKeepAliveStrategy(keepAliveStrategy)
                .build();

        return httpClient;
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private static CommonResultPo doGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        //String responseContent = null;
        //Map<String, String> reMap = new HashMap<>(2);
        CommonResultPo commonResultPo = new CommonResultPo();
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }
            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            //if (response.getStatusLine().getStatusCode() >= 300) {
            //    throw new Exception("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            //}
            // 判断响应状态
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                String outputString = EntityUtils.toString(response.getEntity());
                //reMap.put("code", "200");
                //reMap.put("data", outputString);
                commonResultPo.setCode(ResultCodeEnum.SUCCESS.getCode());
                commonResultPo.setData(outputString);
            } else {
                //reMap.put("code", "400");
                //reMap.put("data", String.valueOf(statusCode));
                commonResultPo.setCode(ResultCodeEnum.FAILED.getCode());
                commonResultPo.setMessage(String.valueOf(statusCode));
            }
            EntityUtils.consume(entity);

        } catch (Exception e) {
            logger.error("Exception,{}", e);
            //reMap.put("code", "5000");
            //reMap.put("data", "" + e);
            commonResultPo.setCode(ResultCodeEnum.ERROR_500.getCode());
            commonResultPo.setMessage("" + e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("IOException,{}", e);
            }
        }
        return commonResultPo;
    }

    /**
     * 发送Post请求
     *
     * @return
     */
    private static CommonResultPo doPost(HttpPost httpPost) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        //String responseContent = null;
        //Map<String, String> reMap = new HashMap<>(2);
        CommonResultPo commonResultPo = new CommonResultPo();
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();
            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }
            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                String outputString = EntityUtils.toString(response.getEntity());
                //reMap.put("code", "200");
                //reMap.put("data", outputString);
                commonResultPo.setCode(ResultCodeEnum.SUCCESS.getCode());
                commonResultPo.setData(outputString);
            } else {
                //reMap.put("code", "400");
                //reMap.put("data", String.valueOf(statusCode));
                commonResultPo.setCode(ResultCodeEnum.FAILED.getCode());
                commonResultPo.setMessage(String.valueOf(statusCode));
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error("Exception,{}", e.getMessage());
            //reMap.put("code", "5000");
            //reMap.put("data", "" + e);
            commonResultPo.setCode(ResultCodeEnum.ERROR_500.getCode());
            commonResultPo.setMessage(e + "");
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("IOException,{}", e.getMessage());
                e.printStackTrace();
            }
        }
        return commonResultPo;
    }

    /**
     * Post,Form表单形式，
     *
     * @param httpUrl      请求地址
     * @param paramStr     请求参数
     * @param paramHeaders 请求头
     * @return
     */
    public static CommonResultPo sendHttpPost_Form(String httpUrl, String paramStr, Map<String, String> paramHeaders) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            //添加head值
            if (paramHeaders != null && paramHeaders.size() > 0) {
                for (String key : paramHeaders.keySet()) {
                    httpPost.setHeader(key, paramHeaders.get(key));
                }
            }
            // 设置参数
            if (paramStr != null && paramStr.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramStr, CHARSET_UTF_8);
                stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                httpPost.setEntity(stringEntity);
            } else {
                StringEntity stringEntity = new StringEntity("", CHARSET_UTF_8);
                stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            logger.error("Exception,{}", e.getMessage());
        }
        return doPost(httpPost);
    }


    public static CommonResultPo sendHttpPost_Json(String httpUrl, String paramStr, Map<String, String> paramHeaders) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            //添加head值
            if (paramHeaders != null && paramHeaders.size() > 0) {
                for (String key : paramHeaders.keySet()) {
                    httpPost.setHeader(key, paramHeaders.get(key));
                }
            }
            // 设置参数
            if (paramStr != null && paramStr.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramStr, CHARSET_UTF_8);
                stringEntity.setContentType(CONTENT_TYPE_JSON_URL);
                httpPost.setEntity(stringEntity);
            } else {
                StringEntity stringEntity = new StringEntity("", CHARSET_UTF_8);
                stringEntity.setContentType(CONTENT_TYPE_JSON_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            logger.error("Exception,{}", e.getMessage());
        }
        return doPost(httpPost);
    }


}
