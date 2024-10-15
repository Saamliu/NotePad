package com.android.notepad;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //获取当前日期
    public static final String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
