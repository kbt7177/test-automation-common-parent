package com.springer.quality.utilities;

import com.springer.quality.utilities.exceptions.ReadExcelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class ReadExcel {

    private ArrayList<ArrayList<ArrayList<Object>>> excelWorkbookData = null;
    private ArrayList<ArrayList<Object>> excelSheetData = null;
    private Workbook workbook = null;
    private int totalSheets = 0;
    private int currentSheet = 0;
    private int totalRowsInSheet = 0;
    private int totalColumnsInRow = 0;
    private static Map<String, ReadExcel> excelFileObject = new HashMap<>();
    private static final String ENV_PROPERTY = System.getProperty("env", "local");
    private String userDir = System.getProperty("user.dir");
    private String resourcesFolder = "/src/main/resources/";
    private String filePath = null;
    private String excelFilePath = null;
    private Map<Integer, Integer> columnCountInSheet = new HashMap<>();

    public static ReadExcel getExcelData(String excelFilePath) throws IOException {
        if (!excelFileObject.containsKey(excelFilePath)) {
            excelFileObject.put(excelFilePath, new ReadExcel(excelFilePath));
        }
        return excelFileObject.get(excelFilePath);
    }

    private ReadExcel(String excelFilePath) throws IOException {
        this.excelFilePath = excelFilePath;
        filePath = userDir + resourcesFolder + ENV_PROPERTY + "/" + excelFilePath;
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        log.info("Excel file reading started for : " + ENV_PROPERTY + "/" + excelFilePath);
        workbook = new XSSFWorkbook(inputStream);
        totalSheets = workbook.getNumberOfSheets();
        getAllData();
        inputStream.close();
    }

    private int getMaxColumnCount(Workbook workbook, int sheetNumber) {
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        int i = 0;
        int maxCount = 0;
        while (i <= sheet.getLastRowNum()) {
            if (sheet.getRow(i) != null) {
                if (maxCount < sheet.getRow(i).getLastCellNum()) {
                    maxCount = sheet.getRow(i).getLastCellNum();
                }
            }
            i++;
        }
        return maxCount;
    }

    private ArrayList<ArrayList<ArrayList<Object>>> getAllData() {
        excelWorkbookData = new ArrayList<ArrayList<ArrayList<Object>>>();
        for (int i = 0; i < totalSheets; i++) {
            columnCountInSheet.put(i, 0);
            excelSheetData = new ArrayList<ArrayList<Object>>();
            Sheet sheet = workbook.getSheetAt(i);
            currentSheet = i;
            totalRowsInSheet = sheet.getLastRowNum() + 1;
            totalColumnsInRow = getMaxColumnCount(workbook, i);
            for (int j = 0; j < totalRowsInSheet; j++) {
                Row row = sheet.getRow(j);
                ArrayList<Object> columnData = new ArrayList<>();
                if (row == null) {
                    excelSheetData.add(new ArrayList<Object>(Stream.iterate("", n -> n).limit(totalColumnsInRow).collect(Collectors.toList())));
                    continue;
                }
                for (int k = 0; k < totalColumnsInRow; k++) {
                    Cell cell = row.getCell(k);
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        columnData.add("");
                        continue;
                    } else if (cell.getCellType().equals(CellType.STRING)) {
                        columnData.add(cell.getStringCellValue());
                    } else if (cell.getCellType().equals(CellType.NUMERIC)) {
                        columnData.add(cell.getNumericCellValue());
                    } else if (cell.getCellType().equals(CellType.BOOLEAN)) {
                        columnData.add(cell.getBooleanCellValue());
                    }
                }
                excelSheetData.add(columnData);
            }
            excelWorkbookData.add(excelSheetData);
        }
        currentSheet = 0;
        excelSheetData = excelWorkbookData.get(currentSheet);
        totalRowsInSheet = excelSheetData.size();
        totalColumnsInRow = excelSheetData.get(0).size();
        return excelWorkbookData;
    }


    @Override
    protected void finalize() throws Throwable {
        workbook.close();
        super.finalize();
    }

    public ArrayList<ArrayList<Object>> getAllDataForSheet(String sheetName) throws ReadExcelException {
        try {
            if (workbook.getSheetIndex(sheetName) != -1) {
                currentSheet = workbook.getSheetIndex(sheetName) + 1;
                verifySheet(currentSheet);
            } else {
                log.error("Given sheet name \"" + sheetName + "\" is not valid or matching with any sheet name");
                throw new ReadExcelException("Given sheet name \"" + sheetName + "\" is not valid or matching with any sheet name");
            }
        } catch (ReadExcelException e) {
            log.error("Exception in reading excel sheet");
            throw e;
        }
        return excelSheetData;
    }

    public ArrayList<ArrayList<Object>> getAllDataForSheet(int sheetNumber) throws ReadExcelException {
        try {
            verifySheet(currentSheet);
            return excelSheetData;
        } catch (ReadExcelException e) {
            log.error("Exception in reading excel sheet");
            throw e;
        }
    }

    private boolean verifySheet(int sheetNumber) throws ReadExcelException {
        if (0 < sheetNumber && sheetNumber <= totalSheets) {
            currentSheet = sheetNumber - 1;
            excelSheetData = excelWorkbookData.get(currentSheet);
            totalRowsInSheet = excelSheetData.size();
            totalColumnsInRow = excelSheetData.get(0).size();
            return true;
        } else {
            log.error("Sheet number should be between 1 and " + totalSheets);
            throw new ReadExcelException("Sheet number should be between 1 and " + totalSheets);
        }
    }

    private boolean verifyRow(int rowNumber) throws ReadExcelException {
        if (0 < rowNumber && rowNumber <= totalRowsInSheet) {
            return true;
        } else {
            log.error("Row number should be between 1 and " + totalRowsInSheet);
            throw new ReadExcelException("Row number should be between 1 and " + totalRowsInSheet);
        }
    }

    private boolean verifyColumn(int columnNumber) throws ReadExcelException {
        if (0 < columnNumber && columnNumber <= totalColumnsInRow) {
            return true;
        } else {
            log.error("Column number should be between 1 and " + totalColumnsInRow);
            throw new ReadExcelException("Column number should be between 1 and " + totalColumnsInRow);
        }
    }

    public String getSingleData(int rowNum, int columnNum) throws ReadExcelException {
        try {
            verifyRow(rowNum);
            verifyColumn(columnNum);
            log.debug("Reading data from excel : " + excelFilePath + ", sheetName : " + workbook.getSheetName(currentSheet) + ", rowNumber : " + rowNum + ", columnNumber : " + columnNum);
            String value = String.valueOf(excelSheetData.get(rowNum - 1).get(columnNum - 1));
            log.debug("Returned Value = " + value);
            return value;
        } catch (ReadExcelException e) {
            throw e;
        }
    }

    public String getSingleData(int sheetNumber, int rowNum, int columnNum) throws ReadExcelException {
        try {
            verifySheet(sheetNumber);
            return getSingleData(rowNum, columnNum);
        } catch (ReadExcelException e) {
            throw e;
        }
    }

    public String getSingleData(String sheetName, int rowNum, int columnNum) throws ReadExcelException {
        try {
            getAllDataForSheet(sheetName);
            return getSingleData(rowNum, columnNum);
        } catch (ReadExcelException e) {
            throw e;
        }
    }


}
