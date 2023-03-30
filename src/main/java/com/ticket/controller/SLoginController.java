package com.ticket.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ticket.common.apiresult.CommonResult;
import com.ticket.common.utils.SecurityUtils;
import com.ticket.common.utils.StringUtils;
import com.ticket.constant.SysConstants;
import com.ticket.entity.HServerArea;
import com.ticket.entity.SLoginAccount;
import com.ticket.pojo.bo.hloginaccount.LoginBo;
import com.ticket.pojo.bo.ReSetPasswordBo;
import com.ticket.pojo.vo.HLoginAccountVo;
import com.ticket.service.HServerAreaService;
import com.ticket.service.SLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author imi
 * @date 2023/2/7 09:22
 * @description 登录接口
 */
@RestController
@RequestMapping("/main")
@Tag(name = "001.登陆接口")
public class SLoginController {

    @Resource
    private SLoginService sLoginService;
    @Resource
    HServerAreaService hServerAreaService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @Operation(summary = "01.用户登陆")
    public CommonResult login(@RequestBody @Valid LoginBo loginBody) {
        // 生成令牌
        String token = sLoginService.login(loginBody.getUserName(), loginBody.getUserPassword());
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(SysConstants.TOKEN, token);
        return CommonResult.success(dataMap);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Operation(summary = "02.获取用户信息")
    @GetMapping("/getInfo")
    public CommonResult getInfo() {
        SLoginAccount user = SecurityUtils.getLoginUser().getUser();
        HLoginAccountVo hLoginAccountVo = BeanUtil.copyProperties(user, HLoginAccountVo.class);
        if (hLoginAccountVo.gethServerAreaId() != null && hLoginAccountVo.gethServerAreaId() != 0) {
            HServerArea hServerArea = hServerAreaService.getById(hLoginAccountVo.gethServerAreaId());
            hLoginAccountVo.setServerAreaName(StringUtils.isNull(hServerArea) ? "" : hServerArea.getServerAreaName());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userInfo", hLoginAccountVo);
        return CommonResult.success(dataMap);
    }

    /**
     * 重置登录账号密码
     */
    @Operation(summary = "03.重置密码")
    @PutMapping("/resetPwd")
    public CommonResult resetPwd(@RequestBody @Valid ReSetPasswordBo paramBo) {
        SLoginAccount user = SecurityUtils.getLoginUser().getUser();
        Long hLoginAccountId = user.getId();
        sLoginService.resetPassword(hLoginAccountId, paramBo.getPasswordOld(), paramBo.getPasswordNew());
        return CommonResult.success();
    }
}
