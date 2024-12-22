package org.drift.common.api;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:58
 */
public interface IErrorCode {
    /**
     * 返回错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 返回错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
