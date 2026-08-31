package com.inventoryservice;

public class Test {
    public static void main(String[] args) {
        /*// Strip 0x prefix first
        //String hexStr = "0xb765696d".replace("0x", ""); // "b765696d"
         String hexStr = "b7656afd";
       // Parse as unsigned long
        long value1 = Long.parseLong(hexStr, 16); // 3076876653
        System.out.println(value1);

       // String hexStr1 = "0xb77cbc68".replace("0x", "");
        String hexStr1 = "b77cbc68";
        long value2 = Long.parseLong(hexStr1, 16);
        System.out.println(value2);

        System.out.println(value2-value1);

        String hexStr2 = "b77e39a9";
        long value3 = Long.parseLong(hexStr2, 16);
        System.out.println(value3);

        System.out.println(value3-value2);

        String hexStr3 = "b784a9e7";
        long value4 = Long.parseLong(hexStr3, 16);
        System.out.println(value4);

        System.out.println(value4-value3);*/

        String hexStr5 = "331390e9";
        long value5 = Long.parseLong(hexStr5, 16);
        System.out.println("hexStr4 "+ value5);

        String hexStr6 = "331390e9";
        double value6 = Double.parseDouble(hexStr6);
        System.out.println("hexStr6 "+ value6);




    }
}
