package me.cuiyijie.trans;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 15:07
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransBasePageResponse extends TransBaseResponse {

    private Long pageNum;
    private Long pageSize;
    private Long total;


    public TransBasePageResponse(Page page) {
        this.setCode("0");
        this.setList(page.getRecords());
        this.setPageNum(page.getCurrent());
        this.setPageSize(page.getSize());
        this.setTotal(page.getTotal());
    }

}