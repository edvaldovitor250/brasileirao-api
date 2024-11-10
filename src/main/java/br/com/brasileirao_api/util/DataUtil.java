package br.com.brasileirao_api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataUtil {

    public static String formatarDateEmString(Date data, String mask) {
        if (data == null) {
            return "";
        }
        try {
            DateFormat formatter = new SimpleDateFormat(mask, new Locale("pt", "BR"));
            return formatter.format(data);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Máscara de formatação inválida: " + mask, e);
        }
    }

    public static String formatarLocalDateTimeEmString(LocalDateTime dataHora, String mask) {
        if (dataHora == null) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mask, new Locale("pt", "BR"));
            return dataHora.format(formatter);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Máscara de formatação inválida: " + mask, e);
        }
    }
}
