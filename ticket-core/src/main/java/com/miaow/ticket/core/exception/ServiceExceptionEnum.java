package com.miaow.ticket.core.exception;

/**
 * 服务异常抽象接口
 */
public interface ServiceExceptionEnum {

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();

}
