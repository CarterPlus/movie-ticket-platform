package com.miaow.ticket.core.aop;

import com.miaow.ticket.core.base.tips.ErrorTip;
import com.miaow.ticket.core.exception.TicketException;
import com.miaow.ticket.core.exception.TicketExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的异常拦截器（拦截所有的控制器，带有@RequestMapping注解的方法上都会被拦截）
 */
@Slf4j
public class BaseControllerExceptionHandler {

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(TicketException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(TicketException e) {
        log.error("业务异常：", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        log.error("运行时异常：", e);
        return new ErrorTip(TicketExceptionEnum.SERVER_ERROR.getCode(), TicketExceptionEnum.SERVER_ERROR.getMessage());
    }

}
