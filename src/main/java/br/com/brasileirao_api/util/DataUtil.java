package br.com.brasileirao_api.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DataUtil {

    private DataUtil() {
    }

    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    public static String formatarDateEmString(LocalDate data, String mask) {
        if (data == null) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mask, LOCALE_PT_BR);
            return data.format(formatter);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Máscara de formatação inválida: " + mask, e);
        }
    }
}
