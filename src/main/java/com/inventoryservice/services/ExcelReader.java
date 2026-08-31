package com.inventoryservice.services;

import com.inventoryservice.dto.InventoryRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Component
public class ExcelReader {

    public List<InventoryRecord> read(MultipartFile file) throws Exception {
        System.out.println("Inside read method..");
        List<InventoryRecord> records = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Map<String,Integer> headers = readHeaders(sheet);
        Integer depotIndex = headers.get("DEPOT_CODE");
        Integer itemIndex = headers.get("ITEM");
        Integer stsCode = headers.get("STS_CODE");
        Integer serviceItem = headers.get("SERVICE_ITEM");
        if(depotIndex == null || itemIndex == null  || stsCode == null || serviceItem == null){
            throw new RuntimeException(
                    "Required columns missing"
            );
        }
        for(Row row : sheet){
            //skipping the header
            if(row.getRowNum()==0)
                continue;
            String depotCode = row.getCell(depotIndex).getStringCellValue();
            if(depotCode == null || depotCode.isEmpty()){
                continue;
            }
            // adding filter to check for STS_CODE
            String status = getValue(row.getCell(stsCode)).trim();
            if (status.equalsIgnoreCase("Expired")) {
                //System.out.println("Expired");
                continue;
            }

         // adding filter for service item check
            String service = getValue(row.getCell(serviceItem)).trim().toUpperCase();
            if (!(service.startsWith("GOLD")
                    || service.startsWith("PLATINUM")
                    || service.startsWith("LEVEL"))) {
                //System.out.println("Gold or Platinum or Level required");
                continue;
            }
           // System.out.println("service: "+service);
            InventoryRecord record = new InventoryRecord();
            record.setDepot_code(getValue(row.getCell(depotIndex)));
            record.setItem(getValue(row.getCell(itemIndex)));
            records.add(record);
        }
        workbook.close();
        return records;
    }

    private Map<String,Integer> readHeaders(
            Sheet sheet){
        Map<String,Integer> map = new HashMap<>();
        Row row = sheet.getRow(0);
        for(Cell cell : row){
            map.put(cell.getStringCellValue()
                            .trim()
                            .toUpperCase(),
                    cell.getColumnIndex());
        }
        return map;
    }

    private String getValue(Cell cell){
        if(cell == null)
            return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

}
