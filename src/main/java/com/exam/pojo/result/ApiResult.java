package com.exam.pojo.result;

import com.exam.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回接口
 *
 * @author hongjinhui
 * 2022/5/20
 */
@Data
@NoArgsConstructor
public class ApiResult<T> {
    private String code;
    private String errMsg;
    private String userMsg;
    private T data;

    public static ApiResult<?> success() {
        return new ApiResult<>(ResultCode.SUCCESS, "success");
    }

    public static <W> ApiResult<W> success(W data) {
        return new ApiResult<>(ResultCode.SUCCESS, "success", data);
    }

    public static <W> ApiResult<W> success(W data, String userMsg) {
        return new ApiResult<>(ResultCode.SUCCESS, "success", userMsg, data);
    }

    public static <W> ApiResult<W> fail(String code, String errMsg, String userMsg) {
        return new ApiResult<>(code, errMsg, userMsg, null);
    }

    public static <W> ApiResult<W> fail(String code, String errMsg, String userMsg, W data) {
        return new ApiResult<>(code, errMsg, userMsg, data);
    }

    public ApiResult(String code, String errMsg) {
        this(code, errMsg, (T)null);
    }

    public ApiResult(String code, String errMsg, T data) {
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
        if (code.equals(ResultCode.SUCCESS)) {
            this.userMsg = "成功";
        } else {
            this.userMsg = "失败";
        }
    }

    public ApiResult(String code, String errMsg, String userMsg, T data) {
        this.code = code;
        this.errMsg = errMsg;
        this.userMsg = userMsg;
        this.data = data;
    }
}
