package com.inventoryservice.controller;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CheckController {

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        System.out.println("Health check started");
        return ResponseEntity.ok("healthy");
    }
}
