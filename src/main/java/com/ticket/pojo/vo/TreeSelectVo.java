package com.ticket.pojo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 树状
 * @author: imi
 * @date: 2022/7/19 16:16
 */
@Getter
@Setter
@NoArgsConstructor
public class TreeSelectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * name
     */
    private String name;

    /**
     * 下级目录
     */
    private List<TreeSelectVo> children;


}
