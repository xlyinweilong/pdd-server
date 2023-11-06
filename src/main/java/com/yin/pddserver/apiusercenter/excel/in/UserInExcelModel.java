package com.yin.pddserver.apiusercenter.excel.in;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 导入用户资料
 */
@Getter
@Setter
public class UserInExcelModel {

    @ExcelProperty(value = "账号")
    @HeadStyle(fillForegroundColor = 10)
    @ColumnWidth(12)
    @NotBlank
    @Length(max = 50)
    private String username;

    @ExcelProperty(value = "密码")
    @HeadStyle(fillForegroundColor = 10)
    @NotBlank
    @ColumnWidth(12)
    @Length(max = 200)
    private String password;

    @ExcelProperty(value = "工号")
    @HeadStyle(fillForegroundColor = 10)
    @NotBlank
    @ColumnWidth(12)
    @Length(max = 200)
    private String jobnumber;

    @ExcelProperty(value = "姓名")
    @HeadStyle(fillForegroundColor = 10)
    @NotBlank
    @ColumnWidth(12)
    @Length(max = 200)
    private String name;

    @ExcelProperty(value = "入职时间")
    @ColumnWidth(12)
    @Length(max = 200)
    private Date hiredDate;

    @ExcelProperty(value = "手机")
    @HeadStyle(fillForegroundColor = 10)
    @NotBlank
    @ColumnWidth(12)
    @Length(max = 200)
    private String mobile;

    @ExcelProperty(value = "超管（填是否）")
    @ColumnWidth(12)
    @Length(max = 200)
    private String isSuper;

    @ExcelProperty(value = "离职（填是否）")
    @ColumnWidth(12)
    @Length(max = 200)
    private String isLeave;

    @ExcelProperty(value = "离职时间")
    @ColumnWidth(12)
    @Length(max = 200)
    private Date leaveDate;

    @ExcelProperty(value = "禁用（填是否）")
    @ColumnWidth(12)
    @Length(max = 200)
    private String disabled;

    @ExcelProperty(value = "禁用原因")
    @ColumnWidth(12)
    @Length(max = 200)
    private String disabledReason;
}
