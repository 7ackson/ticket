package com.ticket.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @description: 读取项目相关配置
 * @author: imi
 * @date: 2022/7/12 14:01
 */
@Getter
@Setter
@NoArgsConstructor
@Component
//@ConfigurationProperties(prefix = "regal")
public class RegalConfig {

    /**
     * 获取地址开关
     */
//    @Value("${regal.addressEnabled}")
//    private boolean addressEnabled;

    ///**
    // * 事件文件存储的位置
    // */
    //@Value("${regal.eventInfoFile.http-prefix}")
    //private String eventInfoFileHttpPrefix;


}
