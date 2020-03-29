package com.yc.common.global.exception.parameterException;

import com.yc.common.global.exception.ApiException;
import com.yc.common.global.response.ResponseCode;
import com.yc.common.global.response.RestResult;

/**
 * 功能描述：参数异常
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2019-09-25
 * @Version: 1.0.0
 */
public class ParameterException extends ApiException {

    public ParameterException() {
        super(ResponseCode.PARAMETER_EXCEPTION, "参数错误");
    }

    public ParameterException(String msg, Object... params) {
        super(ResponseCode.PARAMETER_EXCEPTION, RestResult.formatMsg(msg, params));
    }

}
