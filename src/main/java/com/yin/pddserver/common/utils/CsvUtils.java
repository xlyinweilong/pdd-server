package com.yin.pddserver.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出csv工具
 *
 * @author yin.weilong
 * @date 2019.09.15
 */
@Slf4j
public class CsvUtils {

    public static void export(String[] headers, List<String[]> list, String fileName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        try (OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8")) {
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setHeader("filename", fileName);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            CSVFormat formatter = CSVFormat.DEFAULT.withRecordSeparator("\n");
            osw.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
            osw.write('\ufeff');
            try (CSVPrinter printer = new CSVPrinter(osw, formatter)) {
                printer.printRecord(headers);
                for (String[] s : list) {
                    for (int i = 0; i < s.length; i++) {
                        if (s[i] != null) {
                            s[i] = s[i] + "\t";
                        }
                    }
                    printer.printRecord(s);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void exportHeader(String[] headers, String fileName) {
        export(headers, new ArrayList<>(), fileName);
    }

    public static void export(String[] headers, List<String[]> list) {
        export(headers, list, "export.csv");
    }


    public static String isOrNo(Boolean b) {
        if (b == null) {
            return null;
        }
        return b ? "是" : "否";
    }
}
