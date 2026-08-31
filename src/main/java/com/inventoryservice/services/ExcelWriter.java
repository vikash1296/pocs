package com.inventoryservice.services;

import com.inventoryservice.entity.InventoryTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;



@Component
public class ExcelWriter {


    public ByteArrayInputStream write(List<InventoryTable> report) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Install Base");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("depot Code");
        header.createCell(1).setCellValue("item");
        header.createCell(2).setCellValue("Total");
        int rowNum = 1;
        for(InventoryTable r : report){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getDepot_code());
            row.createCell(1).setCellValue(r.getItem());
            row.createCell(2).setCellValue(r.getTotal());
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(
                out.toByteArray()
        );

    }

}
