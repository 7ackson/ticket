package com.ticket.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticket.common.apiresult.page.PageRequest;
import com.ticket.entity.HServerArea;
import com.ticket.pojo.bo.HServerAreaBo;

import java.util.List;

/**
* @author imi
* @description 针对表【h_server_area(院区表)】的数据库操作Service
* @createDate 2023-02-06 10:59:49
*/
public interface HServerAreaService extends IService<HServerArea> {

    List<HServerArea> findAll(Long hServerAreaId);

    IPage<HServerArea> findPage(PageRequest pageRequest, HServerAreaBo hServerAreaBo);
}
