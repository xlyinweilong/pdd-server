package com.yin.pddserver.common.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 导出excel工具
 *
 * @author yin.weilong
 * @date 2019.09.15
 */
@Slf4j
public class ExportExcelUtils {

    public static void export(HttpServletResponse response, Class clazz, List list) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-disposition", "attachment; filename=" + "export.xlsx");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            //实例化 ExcelWriter
            ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
            //实例化表单
            Sheet sheet = new Sheet(1, 0, clazz);
            sheet.setSheetName("sheet1");
            //输出
            writer.write(list, sheet);
            writer.finish();
            outputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
