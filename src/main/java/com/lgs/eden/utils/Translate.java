package com.lgs.eden.utils;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.config.Language;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        //noinspection SpellCheckingInspection
        return getDate(date, "d%% LLLL yyy");
    }

    /** news format character %% will be replaced by st, nd, th in english for instance **/
    public static String getDate(Date date, String dateFormat) {
        if (Config.getLanguage().equals(Language.FR)) { // hours from 0 to 24
            dateFormat = dateFormat.replace("h:mm", "k:mm");
        }
        DateFormat f = new SimpleDateFormat(dateFormat, Config.getLocale());
        String format = f.format(date);
        // if the user wants a 5th for instance
        if (dateFormat.contains("%%")) {
            String modifier = getDateModifier(f);
            format = format.replace("%%", modifier);
        }
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

        return Translate.getTranslation("date_day_modifier_" + key);
    }

    public static String getTranslation(APIResponseCode rc) {
        return Translate.getTranslation("code_" + rc.code);
    }
}
