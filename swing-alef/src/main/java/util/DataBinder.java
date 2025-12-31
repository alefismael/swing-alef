package util;

import fields.CampoTexto;
import fields.CampoNumeroSpinner;
import java.util.function.Function;
import java.util.function.BiConsumer;

/**
 * Utilitário para binding simples entre campos e DTOs.
 * Fornece binding unidirecional (DTO -> campo) para popular formulários.
 */
public final class DataBinder {
    private DataBinder() {}

    /**
     * Popula um CampoTexto com valor do DTO usando getter.
     */
    public static <T> void bind(CampoTexto campo, T dto, Function<T, String> getter) {
        if (campo == null || dto == null || getter == null) return;
        campo.setValue(getter.apply(dto));
    }

    /**
     * Popula um CampoNumeroSpinner com valor do DTO usando getter.
     */
    public static <T> void bind(CampoNumeroSpinner campo, T dto, Function<T, Integer> getter) {
        if (campo == null || dto == null || getter == null) return;
        campo.setValue(getter.apply(dto));
    }
}
