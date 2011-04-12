package com.github.scriptdonkey.web.util;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;

import com.github.scriptdonkey.model.Lang;

public final class LangConverter implements IConverter<Lang> {
    private static final long serialVersionUID = 1L;

    @Override
    public Lang convertToObject(final String value, final Locale locale) {
        return Lang.valueOf(value);
    }

    @Override
    public String convertToString(final Lang value, final Locale locale) {
        return Strings.capitalize(value.name().toLowerCase());
    }
}