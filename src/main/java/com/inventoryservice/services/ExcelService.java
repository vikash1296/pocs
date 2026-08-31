package com.inventoryservice.services;

import com.inventoryservice.dto.DepotItemKey;
import com.inventoryservice.dto.InventoryRecord;
import com.inventoryservice.entity.InventoryTable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    private final ExcelReader reader;
    private final ExcelWriter writer;

    public ExcelService(ExcelReader reader, ExcelWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public ByteArrayInputStream generateReport(MultipartFile file) throws Exception {
        System.out.println("Inside generateReport method..");
        List<InventoryRecord> records = reader.read(file);
        System.out.println("Total records: " + records.size());
        Map<DepotItemKey,Long> grouped = records.stream()
                        .collect(Collectors.groupingBy(
                                r -> new DepotItemKey(
                                                r.getDepot_code(),
                                                r.getItem()),
                                        Collectors.counting()
                                ));

        System.out.println("Total grouped size : " + grouped.size());
        List<InventoryTable> report =
                grouped.entrySet()
                        .stream()
                        .map(e ->
                                new InventoryTable(
                                        e.getKey().depotCode(),
                                        e.getKey().item(),
                                        Math.toIntExact(e.getValue())
                                )
                        ).sorted(Comparator.comparing(
                                                InventoryTable::getDepot_code,
                                                Comparator.nullsLast(String::compareTo))
                        ).toList();
       return writer.write(report);
    }
}
