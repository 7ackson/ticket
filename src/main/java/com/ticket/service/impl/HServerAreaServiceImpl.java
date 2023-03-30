package com.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticket.common.apiresult.page.PageRequest;
import com.ticket.common.utils.StringUtils;
import com.ticket.entity.HServerArea;
import com.ticket.mapper.HServerAreaMapper;
import com.ticket.pojo.bo.HServerAreaBo;
import com.ticket.service.HServerAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author imi
 * @description 针对表【h_server_area(院区表)】的数据库操作Service实现
 * @createDate 2023-02-06 10:59:49
 */
@Service
public class HServerAreaServiceImpl extends ServiceImpl<HServerAreaMapper, HServerArea> implements HServerAreaService {

    @Override
    public List<HServerArea> findAll(Long hServerAreaId) {
        if (hServerAreaId != null && hServerAreaId != 0) {
            LambdaQueryWrapper<HServerArea> lqw = new LambdaQueryWrapper<>();
            lqw.eq(HServerArea::getId, hServerAreaId);
            return baseMapper.selectList(lqw);
        }
        return baseMapper.selectList(null);
    }

    @Override
    public IPage<HServerArea> findPage(PageRequest pageRequest, HServerAreaBo hServerAreaBo) {
        Page<HServerArea> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        LambdaQueryWrapper<HServerArea> ew = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(hServerAreaBo.getServerAreaCode())) {
            ew.eq(HServerArea::getServerAreaCode, hServerAreaBo.getServerAreaCode());
        }
        if (StringUtils.isNotEmpty(hServerAreaBo.getServerAreaName())) {
            ew.likeRight(HServerArea::getServerAreaName, hServerAreaBo.getServerAreaName());
        }
        return baseMapper.selectPage(page, ew);
    }
}




