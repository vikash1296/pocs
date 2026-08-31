package com.inventoryservice.entity;

import java.util.Objects;

public class InventoryTable {
    private String depot_code;
    private String item;
    private long total;

    public InventoryTable() {
    }

    public InventoryTable(String depot_code, String item, long total) {
        this.depot_code = depot_code;
        this.item = item;
        this.total = total;
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InventoryTable that = (InventoryTable) o;
        return total == that.total && Objects.equals(depot_code, that.depot_code) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depot_code, item, total);
    }

    @Override
    public String toString() {
        return "InventoryTable{" +
                "depot_code='" + depot_code + '\'' +
                ", item='" + item + '\'' +
                ", total=" + total +
                '}';
    }
}
