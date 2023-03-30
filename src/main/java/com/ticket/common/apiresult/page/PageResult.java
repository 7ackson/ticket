package com.ticket.common.apiresult.page;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @description: 分页结果
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public class PageResult {
    private static final long serialVersionUID = 1L;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总记录数
     */
    private long totalSize;
    /**
     * 每页记录数
     */
    private long pageSize;

    /**
     * 当前页数
     */
    private long currPage;
    /**
     * 列表数据
     */
    private List<?> rows;

    /**
     * 分页
     *
     * @param rows      列表数据
     * @param totalPage 总页数
     * @param totalSize 总记录数
     * @param currPage  当前页
     * @param pageSize  当前页记录数量
     */
    public PageResult(List<?> rows, long totalPage, long totalSize, long currPage, long pageSize) {
        this.rows = rows;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.currPage = currPage;
        //this.totalPage = (long) Math.ceil((double) totalCount / pageSize);
        this.totalPage = totalPage;
    }

    /**
     * 分页
     */
    public PageResult(IPage<?> page) {
        this.rows = page.getRecords();
        this.totalSize =  page.getTotal();
        this.pageSize =  page.getSize();
        this.currPage =  page.getCurrent();
        this.totalPage = page.getPages();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getCurrPage() {
        return currPage;
    }

    public void setCurrPage(long currPage) {
        this.currPage = currPage;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
