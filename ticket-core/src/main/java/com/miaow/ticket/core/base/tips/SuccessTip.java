package com.miaow.ticket.core.base.tips;

/**
 * 返回给前台的成功提示
 */
public class SuccessTip extends Tip {

    public SuccessTip() {
        super.status = 200;
        super.msg = "操作成功";
    }
}
