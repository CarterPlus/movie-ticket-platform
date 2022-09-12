package com.miaow.ticket.core.exception;

public enum TicketExceptionEnum implements ServiceExceptionEnum {
    INVALID_DATE_STRING(400, "输入日期格式不对"),
    WRITE_ERROR(500, "渲染界面错误"),
    FILE_READING_ERROR(400, "文件读取错误"),
    FILE_NOT_FOUND(400, "找不到文件"),
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器异常");

    TicketExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
