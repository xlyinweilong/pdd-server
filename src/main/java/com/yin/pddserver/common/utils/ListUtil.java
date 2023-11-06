package com.yin.pddserver.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据工具
 *
 * @author yin.weilong
 * @date 2018.12.21
 */
public class ListUtil {

    /**
     * 分割数组
     *
     * @param list
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        // list的大小
        int listSize = list.size();
        // 页数
        int page = (listSize + (pageSize - 1)) / pageSize;
        // 创建list数组,用来保存分割后的list
        List<List<T>> listArray = new ArrayList<>();
        // 按照数组大小遍历
        for (int i = 0; i < page; i++) {
            // 数组每一位放入一个分割后的list
            List<T> subList = new ArrayList<T>();
            // 遍历待分割的list
            for (int j = 0; j < listSize; j++) {
                // 当前记录的页码(第几页)
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                // 当前记录的页码等于要放入的页码时
                if (pageIndex == (i + 1)) {
                    // 放入list中的元素到分割后的list(subList)
                    subList.add(list.get(j));
                }
                // 当放满一页时退出当前循环
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            // 将分割后的list放入对应的数组的位中
            listArray.add(subList);
        }
        return listArray;
    }

    public static List subList(List array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }
            if (endIndexExclusive > array.size()) {
                endIndexExclusive = array.size();
            }
            return array.subList(startIndexInclusive, endIndexExclusive);
        }
    }


}
