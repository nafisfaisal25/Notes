package com.example.notes.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static String getTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
        String timestamp = dateFormat.format(new Date());
        return timestamp;
    }
}
