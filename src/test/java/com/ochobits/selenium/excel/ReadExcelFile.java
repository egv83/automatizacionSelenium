package com.ochobits.selenium.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ReadExcelFile {

    //LINUX
//    private final String EXCEL_FILE_PATH = "/home/esteban/Documents/SeleniumTestDownload";

    //WINDOWS
//    private final String EXCEL_FILE_PATH = "H:\\PROYECTOS\\QA TEST\\SeleniumTestDownload";

    public ReadExcelFile() {
    }

    public void readExcel(String filePath, String sheetName) throws IOException {
        InputStream inputStream = null;

        //        File file = new File(filePath);
        //        FileInputStream inputStream = new FileInputStream(file);

        inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if(inputStream == null){
            File file = new File(filePath);
            if(file.exists()){
                inputStream = new FileInputStream(file);
            }else {
                throw new IOException("No se encontro el archivo em resources ni en la rura: "+filePath);
            }
        }


        //PARA TRABAJAR CON LA HOJA DE EXCEL
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        //VA A OBTENER LOS VALORES DE LA COLUMNA
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

        for(int i = 0; i<=rowCount; i++){
            XSSFRow row = sheet.getRow(i);
            for(int j = 0; j<row.getLastCellNum(); j++){
                System.out.println("ROW: "+row.getCell(j).getStringCellValue() + "||");
            }
        }
    }

    public String getCellValues(String filePath, String sheetname, int rowNumber, int cellNumber) throws IOException {

        InputStream inputStream = null;

        //File file = new File(filePath);
        //FileInputStream fileInputStream = new FileInputStream(file);
        inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if(inputStream == null){
            File file = new File(filePath);
            if(file.exists()){
                inputStream = new FileInputStream(file);
            }else {
                throw new IOException("No se encontro el archivo em resources ni en la rura: "+filePath);
            }
        }


        //PARA TRABAJAR CON LA HOJA DE EXCEL
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetname);

        //PARA OBTENER EL VALOR DE LA COLUMNA
        XSSFRow row = sheet.getRow(rowNumber);

        //PARA OBTENER EL CALOR DE LA CELDA
        XSSFCell cell = row.getCell(cellNumber);
        return cell.getStringCellValue();
    }
}
