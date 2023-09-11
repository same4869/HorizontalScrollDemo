package com.example.table_group_lib.base;

/**
 * @author：WangJianFeng
 * @date：2022/11/3
 * @description：
 */
public interface ITableHeader {
    /***
     * 头部名称
     * @return
     */
    String getHeaderTitle();

    /***
     * 是否需要排序
     * @return
     */
    boolean needSort();

    /***
     * 排序字段
     * @return
     */
    int sortId();

    /***
     * 排序类型,0降序,1升序
     * @return
     */
    int sortType();
}
