package com.pu.fansystem.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;

public class ExcelUtil {

    public static void main(String[] args) {
        try {
            readExcel("E:/work/doc/公交接口对接/安龙路线、站点/1.xls",0,null,0,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 低版本excel读取 .xls
     * @param filePath      读取的excel文件路径
     * @param sheetIndex    读取的excel页索引（名称和索引二选一）
     * @param sheetName     读取的excel页名称
     * @param rowIndex      开始读取的行索引
     * @param cellIndex     开始读取的列索引
     * @throws Exception
     */
    public static ArrayList<String[]> readExcel(String filePath, Integer sheetIndex,
                                 String sheetName, int rowIndex, int cellIndex) throws Exception{
        ArrayList<String[]> resList = new ArrayList<>();
        // 读取excel
        File file = new File(filePath);
        // 创建excel
        HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
        // 读取sheet页
        HSSFSheet sheet;
        if(StringUtils.hasLength(sheetName)){
            sheet = workbook.getSheet(sheetName);
        } else {
            sheet = workbook.getSheetAt(sheetIndex);
        }
        if(null == sheet)return resList;
        // 获取sheet最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = rowIndex; i < lastRowNum; ++i) {
            // 获取当前行
            HSSFRow row = sheet.getRow(i);
            // 获取当前行的最后一列
            short lastCellNum = row.getLastCellNum();
            String[] cellArr = new String[lastCellNum];
            int arrIndex = 0;
            for (int j = cellIndex; j < lastCellNum; j++) {
                HSSFCell cell = row.getCell(j);
                // 获取当前单元格的值
                cellArr[arrIndex++] = cell.getStringCellValue();
            }
            resList.add(cellArr);
        }
        return resList;
    }

    /**
     * 高版本excel读取 .xlsx
     * @param filePath      读取的excel文件路径
     * @param sheetIndex    读取的excel页索引（名称和索引二选一）
     * @param sheetName     读取的excel页名称
     * @param rowIndex      开始读取的行索引
     * @param cellIndex     开始读取的列索引
     * @throws Exception
     */
    public static ArrayList<String[]> readHighExcel(String filePath, Integer sheetIndex,
                                     String sheetName, int rowIndex, int cellIndex) throws Exception{
        ArrayList<String[]> resList = new ArrayList<>();
        // 读取excel
        File file = new File(filePath);
        // 创建excel
        XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(file));
        // 读取sheet页
        Sheet sheet;
        if(StringUtils.hasLength(sheetName)){
            sheet = workbook.getSheet(sheetName);
        } else {
            sheet = workbook.getSheetAt(sheetIndex);
        }
        if(null == sheet)return resList;
        // 获取sheet最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = rowIndex; i < lastRowNum; ++i) {
            // 获取当前行
            Row row = sheet.getRow(i);
            // 获取当前行的最后一列
            short lastCellNum = row.getLastCellNum();
            String[] cellArr = new String[lastCellNum];
            int arrIndex = 0;
            for (int j = cellIndex; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                // 获取当前单元格的值(IllegalStateException)
                DataFormatter dataFormatter = new DataFormatter();
                cellArr[arrIndex++]  = dataFormatter.formatCellValue(cell);
            }
            resList.add(cellArr);
        }
        return resList;
    }
}
