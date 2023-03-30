package com.ticket.config.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 线程池配置类
 * @author: imi
 * @date: 2022/7/26 15:32
 */
@Slf4j
@EnableAsync
@Configuration
public class TaskThreadPool {

    @Autowired
    private TaskThreadPoolConfig taskThreadPoolConfig;

    @Bean("asyncServiceExecutor")
    public Executor asyncServiceExecutor() {

        /**
         * 同一类调用异步方法
         * EventInfoService bean = applicationContext.getBean(EventInfoService.class);
         * bean.handelEarlyWarningTask_Async(eventInfoId);
         */

        log.info("Bean 'asyncServiceExecutor' start");
        //ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(taskThreadPoolConfig.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(taskThreadPoolConfig.getMaxPoolSize());
        //配置队列大小
        executor.setQueueCapacity(taskThreadPoolConfig.getQueueCapacity());
        //配置
        executor.setKeepAliveSeconds(taskThreadPoolConfig.getKeepAliveSeconds());
        //配置线程池中的线程的名称前缀  也可以从yml配置读取
        executor.setThreadNamePrefix(taskThreadPoolConfig.getThreadNamePrefix());

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
