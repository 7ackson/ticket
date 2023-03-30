package com.ticket.common.apiresult.page;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @description: 分页请求格式
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public class PageRequest {
    /**
     * 当前页码
     */
    @NotNull
    @Min(value = 1, message = "pageNum不能小于1")
    private Integer pageNum;
    /**
     * 每页数量
     */
    @NotNull
    @Min(value = 1, message = "每页展示的条数不能为0")
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageRequest(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageRequest that = (PageRequest) o;
        return pageNum.equals(that.pageNum) &&
                pageSize.equals(that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, pageSize);
    }
}
