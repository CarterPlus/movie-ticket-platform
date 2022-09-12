package com.miaow.ticket.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装ticket的异常实体
 */
@Getter
@Setter
public class TicketException extends RuntimeException {

    private Integer code;
    private String message;

    public TicketException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

}
