package com.inventoryservice.dto;

import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class InventoryRecord {

    private String depot_code;
    private String item;

    public InventoryRecord() {
    }

    public InventoryRecord(String depot_code, String item) {
        this.depot_code = depot_code;
        this.item = item;
    }

    public String getDepot_code() {
        return depot_code;
    }

    public void setDepot_code(String depot_code) {
        this.depot_code = depot_code;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InventoryRecord that = (InventoryRecord) o;
        return Objects.equals(depot_code, that.depot_code) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depot_code, item);
    }

    @Override
    public String toString() {
        return "InventoryRecord{" +
                "depot_code='" + depot_code + '\'' +
                ", item='" + item + '\'' +
                '}';
    }
}
