package com.mss;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        FileFilterDateIntervalUtils filter = 
            new FileFilterDateIntervalUtils("2016-10-04", "2016-11-20");
        File folder =  new File("C://CRM_Interface//Reports//");
        File files[] = folder.listFiles(filter);
        for (File f : files) {
            System.out.println(f.getName());
        }
    }
}