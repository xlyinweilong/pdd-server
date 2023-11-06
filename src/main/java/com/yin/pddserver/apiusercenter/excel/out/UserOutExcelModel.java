package com.yin.pddserver.apiusercenter.excel.out;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 用户资料导出
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutExcelModel {

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(12)
    private Date createDate;

    @ExcelProperty(value = "名称")
    @ColumnWidth(12)
    private String username;

    @ExcelProperty(value = "工号")
    @ColumnWidth(12)
    private String jobnumber;

    @ExcelProperty(value = "姓名")
    @ColumnWidth(12)
    private String name;

    @ExcelProperty(value = "入职时间")
    @ColumnWidth(12)
    private Date hiredDate;

    @ExcelProperty(value = "手机")
    @ColumnWidth(12)
    private String mobile;

    @ExcelProperty(value = "超管")
    @ColumnWidth(12)
    private String isSuper;

    @ExcelProperty(value = "离职")
    @ColumnWidth(12)
    private String isLeave;

    @ExcelProperty(value = "离职时间")
    @ColumnWidth(12)
    private Date leaveDate;

    @ExcelProperty(value = "禁用")
    @ColumnWidth(12)
    private String disabled;

    @ExcelProperty(value = "禁用原因")
    @ColumnWidth(12)
    private String disableReason;

}
