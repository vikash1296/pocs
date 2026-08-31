package com.inventoryservice.controller;

import com.inventoryservice.services.ShowTechOptimizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/showtechs")
public class ShowTechOptimizerController {

    @Autowired
    private ShowTechOptimizerService  showTechOptimizerService;

    @PostMapping("/optimizer")
    public ResponseEntity<?> showTechOptimizer(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(defaultValue = "false") boolean download) throws IOException {
        System.out.println("Inside Show Tech Optimizer.....");
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please upload an correct file.");
        }
        Path tempInput = Files.createTempFile("upload_", ".txt");
        file.transferTo(tempInput);
        File optimizedFile = showTechOptimizerService.optimizeFile(tempInput.toFile());
        System.out.println("optimizeFile File size : " + optimizedFile.length());
        File optimizeFWResource = showTechOptimizerService.optimizeFWResource(optimizedFile);
        System.out.println("optimizeFWResource File size : " + optimizeFWResource.length());
        if (!download) {
            return ResponseEntity.ok(Map.of("fileName", optimizeFWResource.getName(),
                    "size", optimizeFWResource.length(), "message", "File optimized successfully"));
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + optimizeFWResource.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(new FileInputStream(optimizeFWResource)));
    }
}
