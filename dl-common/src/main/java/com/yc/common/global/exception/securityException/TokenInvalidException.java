package com.yc.common.global.exception.securityException;

import com.yc.common.global.exception.ApiException;
import com.yc.common.global.response.RestResult;

/**
 * 功能描述：Token无效异常
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2020-03-23
 * @Version: 1.0.0
 */
public class TokenInvalidException extends ApiException {

    public TokenInvalidException() {
        super(10503, "登录信息失效,请重新登录！");
    }

    public TokenInvalidException(String msg, Object... params) {
        super(10503, RestResult.formatMsg(msg, params));
    }
}
