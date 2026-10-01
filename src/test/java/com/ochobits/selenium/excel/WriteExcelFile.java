package com.ochobits.selenium.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;

public class WriteExcelFile {

    public WriteExcelFile() {
    }

    public void writeExcel(String filePath, String sheetName, String[] dataToWrite) throws IOException {

        InputStream inputStream = null;

        //File file = new File(filePath);
        //FileInputStream fileInputStream = new FileInputStream(file);
        inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            File file = new File(filePath);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                throw new IOException("No se encontro el archivo em resources ni en la rura: " + filePath);
            }
        }

        //PARA TRABAJAR CON LA HOJA DE EXCEL
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        XSSFRow row = sheet.getRow(0);
        XSSFRow newRow = sheet.createRow(rowCount + 1);

        for (int i = 0; i < row.getLastCellNum(); i++) {
            XSSFCell cell = newRow.createCell(i);
            cell.setCellValue(dataToWrite[i]);
        }

        inputStream.close();
        FileOutputStream outputStrem = new FileOutputStream(filePath);
        workbook.write(outputStrem);

        outputStrem.close();
    }

    //METODO PARA ESCRIBIR EN LA CELDA
    public void writeCellValue(String filePath, String sheetName, int rowNumber, int cellNumber, String resultText) throws IOException {

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        //PARA TRABAJAR CON LA HOJA DE EXCEL
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        XSSFRow row = sheet.getRow(0);

        XSSFCell firstCell = row.getCell(cellNumber - 1);
        System.out.println("Primer valor de la celda es: " + firstCell.getStringCellValue());

        XSSFCell nextCell = row.createCell(cellNumber);
        nextCell.setCellValue(resultText);
        System.out.println("Siguiente celda es: " + nextCell.getStringCellValue());
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }

    //METODO PARA ESCRIBIR EN LA CELDA
    public void writeCellValueResources(String filePath, String sheetName, int rowNumber, int cellNumber, String resultText) throws IOException {

        InputStream inputStream = null;
        File file = null;

        String cleanPath = filePath.startsWith("/") ? filePath.substring(1) : filePath;

        // 1. Intentar localizar la URL física del recurso en la carpeta de compilación (target)
        URL resourceUrl = getClass().getClassLoader().getResource(cleanPath);

        if (resourceUrl != null) {
            // El recurso fue localizado en el Classpath (target/test-classes/...)
            file = new File(resourceUrl.getFile());
        } else {
            // 2. Si no es un recurso, buscar en la ruta del sistema de archivos del SO
            file = new File(filePath);
        }


        if (!file.exists()) {
            throw new IOException("No se encontró el archivo ni en resources ni en la ruta: " + filePath);
        }

        // 3. Abrir, modificar y guardar usando try-with-resources
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("La hoja '" + sheetName + "' no existe.");
            }

            // Obtener o crear la fila si no existe
            Row row = sheet.getRow(rowNumber);
            if (row == null) {
                row = sheet.createRow(rowNumber);
            }

            // Obtener o crear la celda de destino
            Cell cell = row.getCell(cellNumber);
            if (cell == null) {
                cell = row.createCell(cellNumber);
            }

            // Asignar el nuevo valor
            cell.setCellValue(resultText);

            // Guardar los cambios en el archivo físico
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }

            System.out.println("Valor escrito correctamente en la celda [" + rowNumber + ", " + cellNumber + "]: " + resultText);
        }
    }


}
