package com.sse.g4.proj;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.ArrayList;
import java.util.List;

public class MemberConverter implements Converter<ArrayList<String>> {

    @Override
    public Class supportJavaTypeKey() {
        return ArrayList.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public ArrayList<String> convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(ArrayList<String> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        return new CellData(value.toString());
    }
}
