package com.ticket.common.apiresult;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 统一返回值
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public class CommonResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public CommonResult() {
    }

    @Override
    public CommonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static CommonResult generalResult(IResultCode generalCode, String message, Object data, String description) {
        CommonResult commonResult = new CommonResult();
        commonResult.put("code", generalCode.getCode());
        //如果message为空则显示枚举中的信息
        commonResult.put("message", StringUtils.isEmpty(message) ? generalCode.getMessage() : message);
        if (StringUtils.isNotEmpty(description)) {
            commonResult.put("description", description);
        }
        if (data != null) {
            commonResult.put("data", data);
        }
        return commonResult;
    }

    public static CommonResult generalMessageResult(IResultCode generalCode, String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.put("code", generalCode.getCode());
        commonResult.put("message", StringUtils.isEmpty(message) ? generalCode.getMessage() : message);
        return commonResult;
    }

    public static CommonResult CustomResultMap(IResultCode generalCode, String message, Map<String, Object> map) {
        CommonResult commonResult = new CommonResult();
        commonResult.put("code", generalCode.getCode());
        //如果message为空则显示枚举中的信息
        commonResult.put("message", StringUtils.isEmpty((String) message) ? generalCode.getMessage() : message);
        commonResult.putAll(map);
        return commonResult;
    }

    public static CommonResult CustomResultMap(CommonResultPo commonResultPo) {
        CommonResult commonResult = new CommonResult();
        commonResult.put("code", commonResultPo.getCode());
        if (commonResultPo.getData() != null) {
            commonResult.put("data", commonResultPo.getData());
        }
        commonResult.put("message", commonResultPo.getMessage());
        if (StringUtils.isNotEmpty(commonResultPo.getDescription())) {
            commonResult.put("description", commonResultPo.getDescription());
        }
        return commonResult;
    }

    public static CommonResult success() {
        return generalResult(ResultCodeEnum.SUCCESS, "", null, "");
    }

    public static CommonResult success(Object data) {
        return generalResult(ResultCodeEnum.SUCCESS, "", data, "");
    }

    public static CommonResult success(Object data, String message) {
        return generalResult(ResultCodeEnum.SUCCESS, message, data, "");
    }

    public static CommonResult fail() {
        return generalResult(ResultCodeEnum.FAILED, "", null, "");
    }

    public static CommonResult fail(String msg) {
        return generalResult(ResultCodeEnum.FAILED, msg, null, "");
    }

    /**
     * 无数据
     *
     * @return
     */
    public static CommonResult noContent(String msg) {
        return generalResult(ResultCodeEnum.NO_CONTENT, msg, null, "");
    }

    public static CommonResult error(String msg) {
        return generalResult(ResultCodeEnum.ERROR_500, msg, null, "");
    }


}
