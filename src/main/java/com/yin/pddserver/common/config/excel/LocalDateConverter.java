package com.yin.pddserver.common.config.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.utils.DateUtils;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws MessageException {
        if (cellData.getNumberValue() == null) {
            return DateUtils.parseLocalDateDefault(cellData.getStringValue());
        } else {
            Calendar c = new GregorianCalendar(1900, 0, -1);
            c.add(Calendar.DAY_OF_MONTH, cellData.getNumberValue().intValue());
            return LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        }
    }

    @Override
    public CellData<String> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new CellData(value.toString());
    }

}
