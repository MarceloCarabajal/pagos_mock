package com.hasar.pagosprueba;

import java.util.HashMap;
import java.util.Map;

public class CardOptions {
    public static final Map<String, String[]> OPTIONS = new HashMap<>();

    static {
        OPTIONS.put("VISA", new String[]{"Debito VISA", "Credito VISA"});
        OPTIONS.put("MASTERCARD", new String[]{"Maestro", "MasterCard", "MasterDebit"});
        OPTIONS.put("AMEX", new String[]{"AMEX", "AMEX Corporativa"});
    }
}
