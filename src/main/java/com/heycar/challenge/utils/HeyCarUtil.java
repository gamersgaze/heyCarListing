package com.heycar.challenge.utils;

import org.apache.commons.lang3.StringUtils;

public class HeyCarUtil {

    public static final String RESPONSE_DATE_TIME_FORMAT = "dd/MMM/yyyy HH:mm";

    public static String escapeSql(String sqlString) {

        if (sqlString == null) {
            return null;
        }

        if (StringUtils.isBlank(sqlString)) {
            return sqlString;
        }

        String enscapeString = sqlString.trim()
                .replace("\\\\", "\\\\\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_")
                .replace("'", "\\'")
                .replace("\"", "\\\"");

        return enscapeString;
    }

    public static Long parseLong(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return null;
        }
    }
}
