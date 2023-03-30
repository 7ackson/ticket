package com.ticket.pojo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: 下拉框数据类
 * @author: imi
 * @date: 2022/7/19 16:16
 */
@Getter
@Setter
@NoArgsConstructor
public class SelectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

}
