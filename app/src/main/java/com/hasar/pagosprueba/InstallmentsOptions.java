package com.hasar.pagosprueba;

import java.util.HashMap;
import java.util.Map;

public class InstallmentsOptions {
    public static final Map<String, String[]> OPTIONS = new HashMap<>();

    static {
        OPTIONS.put("-", new String[]{"-"});
        OPTIONS.put("Debito VISA", new String[]{"1 Cuota"});
        OPTIONS.put("Credito VISA", new String[]{"1 Cuota", "2 Cuotas", "3 Cuotas", "6 Cuotas", "12 Cuotas", "24 Cuotas", "36 Cuotas"});
        OPTIONS.put("Maestro", new String[]{"1 Cuota"});
        OPTIONS.put("MasterCard", new String[]{"1 Cuota", "2 Cuotas", "3 Cuotas", "6 Cuotas", "12 Cuotas", "24 Cuotas", "36 Cuotas"});
        OPTIONS.put("MasterDebit", new String[]{"1 Cuota"});
        OPTIONS.put("AMEX", new String[]{"1 Cuota", "2 Cuotas", "3 Cuotas", "6 Cuotas", "12 Cuotas", "24 Cuotas", "36 Cuotas"});
        OPTIONS.put("AMEX Corporativa", new String[]{"1 Cuota", "2 Cuotas", "3 Cuotas", "6 Cuotas", "12 Cuotas", "24 Cuotas", "36 Cuotas"});

    }
}
