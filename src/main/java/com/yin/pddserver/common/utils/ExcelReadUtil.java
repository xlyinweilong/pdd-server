package com.yin.pddserver.common.utils;

import com.yin.pddserver.common.exceptions.MessageException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

public class ExcelReadUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public ExcelReadUtil() {
    }

    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith("xls")) {
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith("xlsx")) {
            wb = new XSSFWorkbook(in);
        }

        return (Workbook)wb;
    }

    public static void checkExcelVaild(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        } else if (!file.isFile() || !file.getName().endsWith("xls") && !file.getName().endsWith("xlsx")) {
            throw new Exception("文件不是Excel");
        }
    }

    public static Object getValue(Cell cell) {
        Object obj = null;
        switch(cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
        }

        return obj;
    }

    public static Integer getInteger(Cell cell, Integer defaultValue) throws MessageException {
        Integer i = getInteger(cell);
        return i == null ? defaultValue : i;
    }

    public static Integer getInteger(Cell cell) throws MessageException {
        if (cell == null) {
            return null;
        } else {
            try {
                if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                    return StringUtils.isBlank(cell.getStringCellValue()) ? null : Integer.parseInt(cell.getStringCellValue());
                } else {
                    return (int)cell.getNumericCellValue();
                }
            } catch (Throwable var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static BigDecimal getBigDecimal(Cell cell, BigDecimal defaultValue) throws MessageException {
        BigDecimal i = getBigDecimal(cell);
        return i == null ? defaultValue : i;
    }

    public static BigDecimal getBigDecimal(Cell cell) throws MessageException {
        if (cell == null) {
            return null;
        } else {
            try {
                if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                    return StringUtils.isBlank(cell.getStringCellValue()) ? null : new BigDecimal(cell.getStringCellValue());
                } else {
                    return BigDecimal.valueOf(cell.getNumericCellValue());
                }
            } catch (Throwable var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static String getString(Cell cell) throws MessageException {
        if (cell == null) {
            return null;
        } else {
            cell.setCellType(CellType.STRING);
            return StringUtils.trimToNull(cell.getStringCellValue());
        }
    }

    public static Date getDate(Cell cell) throws MessageException {
        if (cell == null) {
            return null;
        } else {
            try {
                return cell.getDateCellValue();
            } catch (Throwable var2) {
                var2.printStackTrace();
                throw new MessageException("第" + cell.getRowIndex() + "行，日期解析失败");
            }
        }
    }

    public static void addErrorToRow(Row row, int errorCellNum, String message) {
        Cell cell = row.getCell(errorCellNum);
        if (cell == null) {
            row.createCell(errorCellNum).setCellValue(message);
        } else {
            row.createCell(errorCellNum).setCellValue(cell.getStringCellValue() + ";" + message);
        }

    }
}
