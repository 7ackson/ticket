package com.ticket.config;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


/**
 * @description: 自定义 p6spy sql输出格式
 * @author: imi
 * @date: 2022/7/11 16:11
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {
    /**
     * 打印SQL语句 过滤掉定时任务的SQL
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        //return StringUtils.isNotBlank(sql) ? DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)
        //+ " | 耗时 " + elapsed + " ms | SQL 语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : StringUtils.EMPTY;
        return StringUtils.isNotBlank(sql) ?
                DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)
                        + " | 耗时 " + elapsed + " ms | SQL语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : "SQL语句结束";
    }
}
