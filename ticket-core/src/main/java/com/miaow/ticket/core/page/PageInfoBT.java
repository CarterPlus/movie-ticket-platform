package com.miaow.ticket.core.page;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页参数类（for BootStrap Table）
 */
@Data
@AllArgsConstructor
public class PageInfoBT<T> {

    /**
     * 结果集
     */
    private List<T> rows;

    /**
     * 总数
     */
    private long total;

    public PageInfoBT(Page<T> page) {
        this.rows = page.getRecords();
        this.total = page.getTotal();
    }

}
