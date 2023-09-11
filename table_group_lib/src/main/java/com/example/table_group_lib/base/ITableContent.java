package com.example.table_group_lib.base;

import java.util.List;

/**
 * @author：WangJianFeng
 * @date：2022/11/4
 * @description：
 */
public interface ITableContent {
    /***
     * 获取每行数据
     * @return
     */
    List<ITableContent> getRowList();

    /***
     * 每个tab数据
     * @return
     */
    String getValue();
}
