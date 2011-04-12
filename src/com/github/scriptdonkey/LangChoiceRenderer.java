package com.github.scriptdonkey;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.util.string.Strings;

import com.github.scriptdonkey.model.Lang;

final class LangChoiceRenderer implements IChoiceRenderer<Lang> {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getDisplayValue(final Lang lang) {
        return Strings.capitalize(lang.name().toLowerCase());
    }

    @Override
    public String getIdValue(final Lang lang, final int index) {
        return String.valueOf(lang.ordinal());
    }
}