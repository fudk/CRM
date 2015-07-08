package com.greatwall.platform.domain;

/**
 * 分页参数类
 * 
 */
public class PageParameter {

    /** 
    * @Fields DEFAULT_PAGE_SIZE : 默认每页显示数量 
    */ 
    public static final int DEFAULT_PAGE_SIZE = 10;

    /** 
    * @Fields pageSize : 每页显示数量 
    */ 
    private int pageSize;
    /** 
    * @Fields currentPage : 当前页 
    */ 
    private int currentPage;
    /** 
    * @Fields prePage : 前一页 
    */ 
    private int prePage;
    /** 
    * @Fields nextPage : 下一页 
    */ 
    private int nextPage;
    /** 
    * @Fields totalPage : 总页数 
    */ 
    private int totalPage;
    /** 
    * @Fields totalCount : 总条数 
    */ 
    private int totalCount;

    public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
