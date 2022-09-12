package com.miaow.ticket.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页参数类（for BootStrap Table）
 */
@Data
@AllArgsConstructor
public class PageBT {
    /**
     * 每页显示个数
     */
    private int limit;

    /**
     * 查询的偏移量（查询的页数 = offset/limit + 1）
     */
    private int offset;

    /**
     * 排序方式
     */
    private String order;
}
