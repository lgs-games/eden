package com.lgs.eden.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

/**
 * Handle translations-related utilities
 */
public final class Translate {

    /** return a translation from a key, don't start with a % like in JavaFX **/
    public static String getTranslation(String key){
        try {
            ResourceBundle i18n = ResourceBundle.getBundle("i18n", Config.getLocale());
            return i18n.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    /** return the date as day (int) month (string) year (ex: 1 June 2021 in english) **/
    public static String getDate(Date date) {
        DateFormat f = new SimpleDateFormat("d%% LLLL yyy", Config.getLocale());
        String format = f.format(date);
        String modifier = getDateModifier(f);
        format = format.replace("%%", modifier);
        return format;
    }

    private static String getDateModifier(DateFormat f) {
        // calendar in order to get the day
        Calendar calendar = f.getCalendar();
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        // get key
        String key;
        if (i > 3) key = "n";
        else key = String.valueOf(i);

        return Translate.getTranslation("date_day_modifier_"+key);
    }
}
