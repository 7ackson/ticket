package com.ticket.controller;


import com.ticket.common.apiresult.CommonResult;
import com.ticket.common.apiresult.page.PageRequest;
import com.ticket.common.exception.ServiceException;
import com.ticket.common.utils.StringUtils;
import com.ticket.entity.SLoginAccount;
import com.ticket.enums.LoginAccountRoleEnum;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountAddBo;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountQueryBo;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountUpBo;
import com.ticket.service.HLoginAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 系统-账户表(HLoginAccount)表控制层
 *
 * @author makejava
 * @since 2023-02-06 15:01:10
 */
@RestController
@RequestMapping("hLoginAccount")
@Tag(name = "002.账号管理")
public class HLoginAccountController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private HLoginAccountService hLoginAccountService;

    /**
     * 分页查询所有数据
     *
     * @param pageRequest          分页对象
     * @param hLoginAccountQueryBo 查询实体
     * @return 所有数据
     */
    @Operation(summary = "01.获取分页数据")
    @GetMapping("/listPage")
    public CommonResult selectAll(@Valid PageRequest pageRequest, @Valid HLoginAccountQueryBo hLoginAccountQueryBo) {
        Integer role = getLoginUser().getUser().getRole();
        if (role == LoginAccountRoleEnum.YUAN_QU_GUAN_LI_YUAN.getCode()) {
            hLoginAccountQueryBo.sethServerAreaId(gethServerAreaId());
        }
        return CommonResult.success(getPageTable(this.hLoginAccountService.listPage(pageRequest, hLoginAccountQueryBo)));
    }

    /**
     * 新增数据
     *
     * @param paramBo 实体对象
     * @return 新增结果
     */
    @Operation(summary = "02.新增数据")
    @PostMapping("/add")
    public CommonResult insert(@RequestBody @Valid HLoginAccountAddBo paramBo) {
        if (paramBo.getRole() == LoginAccountRoleEnum.CHAO_JI_GUAN_LI_YUAN.getCode()) {
            hLoginAccountService.checkUserIsSuperAdmin(getUserId(), "无权限添加超级管理员");
            paramBo.sethServerAreaId(0L);
        }

        if (hLoginAccountService.checkUserNameUnique(paramBo.getUsername(), 0L) > 0) {
            return CommonResult.fail("新增用户'" + paramBo.getUsername() + "'失败，登录账号已存在");
        }
        hLoginAccountService.createUser(paramBo);
        return CommonResult.success();
    }

    /**
     * 修改数据
     *
     * @param paramBo 实体对象
     * @return 修改结果
     */
    @Operation(summary = "03.修改数据")
    @PutMapping("/update")
    public CommonResult update(@RequestBody @Valid HLoginAccountUpBo paramBo) {
        hLoginAccountService.checkUserAllowed(paramBo.gethLoginAccountId());
        if (hLoginAccountService.checkUserNameUnique(paramBo.getUsername(), paramBo.gethLoginAccountId()) > 0) {
            return CommonResult.fail("修改用户'" + paramBo.getUsername() + "'失败，登录账号已存在");
        }

        if (paramBo.getRole() == LoginAccountRoleEnum.CHAO_JI_GUAN_LI_YUAN.getCode()) {
            hLoginAccountService.checkUserIsSuperAdmin(getUserId(), "无权限添加超级管理员");
            paramBo.sethServerAreaId(0L);
        }

        hLoginAccountService.editUser(paramBo);
        return CommonResult.success();
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @Operation(summary = "04.删除数据")
    @DeleteMapping("/del")
    public CommonResult delete(@RequestBody List<Long> idList) {
        if (idList.contains(getUserId())) {
            return CommonResult.fail("当前用户不能删除");
        }

        Integer currentRole = getLoginUser().getUser().getRole();

        //记录有问题的行数
        StringBuilder idSize = new StringBuilder();
        for (int i = 0; i < idList.size(); i++) {
            //超级管理员
            hLoginAccountService.checkUserAllowed(idList.get(i));

            SLoginAccount byId = hLoginAccountService.getById(idList.get(i));
            if (StringUtils.isNull(byId)) {
                idSize.append(i + 1).append(",");
            }
            if (byId.getRole() != currentRole) {
                throw new ServiceException(idSize + "权限不足，删除失败");
            }
        }
        if (StringUtils.isNotEmpty(idSize)) {
            idSize.delete(idSize.length() - 1, idSize.length());
            throw new ServiceException(idSize + "条数据不存在");
        }

        hLoginAccountService.deleteUsers(idList);
        return CommonResult.success();
    }

    @Operation(summary = "05.修改用户状态")
    @PutMapping("/changeStatus/{id}")
    public CommonResult edit(@PathVariable("id") Long id) {
        hLoginAccountService.checkUserAllowed(id);
        //修改状态
        hLoginAccountService.changeStatus(id);
        return CommonResult.success();
    }
}

