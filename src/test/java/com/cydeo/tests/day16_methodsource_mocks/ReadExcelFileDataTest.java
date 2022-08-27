package com.cydeo.tests.day16_methodsource_mocks;

import com.cydeo.utils.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ReadExcelFileDataTest {
    @Test
    public void readBookItUsersTest(){
        ExcelUtil excelUtil = new ExcelUtil("src/test/resources/BookItQa3.xlsx","QA3");
        System.out.println("ColumnsNames = " + excelUtil.getColumnsNames());

        int rowCount = excelUtil.rowCount();
        System.out.println("Row Count = " + rowCount);

        System.out.println("getCellData(1,1) = " + excelUtil.getCellData(1, 0));

        List<Map<String,String>> data = excelUtil.getDataList();
        System.out.println("Data = " + data);
    }
}
