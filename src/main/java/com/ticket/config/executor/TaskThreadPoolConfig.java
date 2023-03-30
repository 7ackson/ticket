package com.ticket.config.executor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: imi
 * @date: 2022/7/26 15:34
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class TaskThreadPoolConfig {

    @Value("${spring.task.execution.thread-name-prefix}")
    private String threadNamePrefix;

    @Value("${spring.task.execution.pool.core-size}")
    private int corePoolSize;

    @Value("${spring.task.execution.pool.max-size}")
    private int maxPoolSize;

    @Value("${spring.task.execution.pool.queue-capacity}")
    private int queueCapacity;

    @Value("${spring.task.execution.pool.keep-alive}")
    private int keepAliveSeconds;


}
