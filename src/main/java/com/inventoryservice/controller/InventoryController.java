package com.inventoryservice.controller;

import com.inventoryservice.services.ExcelService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/inventoryTables")
public class InventoryController {

    private final ExcelService excelService;

    public InventoryController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/upload")
    public ResponseEntity<InputStreamResource> uploadExcel(
            @RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please upload an Excel file.");
        }
        try {
            ByteArrayInputStream inputStream = excelService.generateReport(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=install-base.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            System.out.println("Error while uploading Excel file.");
            return ResponseEntity.internalServerError().build();
        }
    }
}
