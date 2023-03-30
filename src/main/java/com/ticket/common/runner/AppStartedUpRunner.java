package com.ticket.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: imi
 * @date: 2022/7/13 15:39
 */
@Slf4j
@Component
public class AppStartedUpRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(" ___ _   _  ___ ___ ___  ___ ___ ");
        log.info("/ __| | | |/ __/ __/ _ \\/ __/ __|");
        log.info("\\__ \\ |_| | (_| (_|  __/\\__ \\__ \\");
        log.info("|___/\\__,_|\\___\\___\\___||___/___/  ticket-manage Run Success");
    }
}
