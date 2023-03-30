package com.ticket.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: imi
 * @date: 2022/7/11 15:22
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件 3.5.X
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setMaxLimit(-1L);
        paginationInterceptor.setDbType(DbType.MYSQL);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setOptimizeJoin(true);
        return paginationInterceptor;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        ///**
        // * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
        // */
        //mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
        //    @Override
        //    public Expression getTenantId() {
        //        // 设置当前租户ID，实际情况你可以从cookie、或者缓存中拿都行
        //        return new LongValue(SecurityUtils.convertServerAreaId(null));
        //    }
        //    @Override
        //    public String getTenantIdColumn() {
        //        // 对应数据库租户ID的列名
        //        return "h_server_area_id";
        //    }
        //    // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
        //    @Override
        //    public boolean ignoreTable(String tableName) {
        //        // 控制某些表
        //        return "h_server_area".equalsIgnoreCase(tableName);
        //    }
        //}));

        //mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor());
        //防全表更新与删除插件，攻击SQL阻断解析器、加入解析链
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return mybatisPlusInterceptor;
    }


}
