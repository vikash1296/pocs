package com.inventoryservice;

public class TestDemo {
    public static void main(String[] args) {
        String hexStr1 = "330e0d70";
        // Parse as unsigned long
        long value1 = Long.parseLong(hexStr1, 16); // 3076876653

        String hexStr2 = "331390e9";
        long value2 = Long.parseLong(hexStr2, 16);
        System.out.println(value2);
        System.out.println(value2-value1);


        /*String hexStr3 = "5e709dfb";
        long value3 = Long.parseLong(hexStr3, 16);
        System.out.println(value2);
        System.out.println(value3-value2);

        String hexStr4 = "25d4ac81";
        long value4 = Long.parseLong(hexStr4, 16);
        System.out.println(value4);
        System.out.println(value4-value3);

        String hexStr5 = "4f6ae418";
        long value5 = Long.parseLong(hexStr5, 16);
        System.out.println(value5);
        System.out.println(value5-value4);

        String hexStr6 = "1f1793d8";
        long value6 = Long.parseLong(hexStr6, 16);
        System.out.println(value6);
        System.out.println(value6-value5);

        String hexStr7 = "331390e9";
        long value7 = Long.parseLong(hexStr7, 16);
        System.out.println(value7);
        System.out.println(value7-value6);

        String hexStr8 = "28096d38";
        long value8 = Long.parseLong(hexStr8, 16);
        System.out.println(value8);
        System.out.println(value8-value7);*/

    }
}
