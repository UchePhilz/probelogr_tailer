/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

/**
 *
 * @author uchephilz
 */
public class Meths {

    public static String getCurrentDateTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(new Date());
        String date = getCurrentDate();
        String dtime = date + " " + str;
        System.out.println(dtime);
        return dtime.substring(0, dtime.length() - 3);
    }

    public static String getCurrentYearAndMonth() {
        Calendar cl = Calendar.getInstance();
        String year = String.valueOf(cl.get(Calendar.YEAR));
        String month = String.valueOf(cl.get((Calendar.MONTH)) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String date = year + "-" + month;
        return (date);
    }

    public static String getCurrentDate() {
        Calendar cl = Calendar.getInstance();
        String year = String.valueOf(cl.get(Calendar.YEAR));
        String month = String.valueOf(cl.get((Calendar.MONTH)) + 1);
        String day = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        String date = year + "-" + month + "-" + day;
        return (date);
    }

    public static boolean isEmail(String value) {
        return notNull(value) && value.matches("[a-zA-Z0-9\\.\\-]+\\@[a-zA-Z0-9\\.\\-]+");
    }

    public static boolean isNumber(String str) {
        return notNull(str) && str.matches("[0-9]+");
    }

    public static boolean isHex(String str) {
        return notNull(str) && str.matches("[0-9A-Fa-f]+");
    }

    public static boolean notEmpty(String str) {
        return notNull(str) && str.length() > 0;
    }

    public static boolean notEmpty(List l) {
        return notNull(l) && !l.isEmpty();
    }

    public static boolean notNull(Object ob) {
        return ob != null;
    }

    public static boolean isMatch(String str, String str1) {
        return notEmpty(str) && str.equals(str1);
    }

    public static boolean isMinLength(String str, int len) {
        return notNull(str) && str.length() >= len;
    }

    public static boolean isPassword(String str, int len) {
        return notNull(str) && str.length() >= len && str.matches(".*[0-9].*");
    }

    public static boolean hasNumber(String str) {
        return notNull(str) && str.matches(".*[0-9].*");
    }

    public static boolean contains(String str, String[] strArr) {
        return notEmpty(str) && matches(str, strArr);
    }

    private static boolean matches(String str, String[] strArr) {
        for (String s : strArr) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static String pad(String str) {
        if (str.length() > 8) {
            return str;
        }
        return str.concat("000000");
    }

}
