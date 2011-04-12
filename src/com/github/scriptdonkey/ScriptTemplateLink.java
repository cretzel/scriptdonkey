package com.github.scriptdonkey;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import com.github.scriptdonkey.model.ScriptTemplate;

final class ScriptTemplateLink extends Link<ScriptTemplate> {
    private static final long serialVersionUID = 1L;

    ScriptTemplateLink(final String id, final IModel<ScriptTemplate> model) {
        super(id, model);
    }

    @Override
    public void onClick() {
        final SpotHomePage homePage = new SpotHomePage(getModel());
        setResponsePage(homePage);
    }
}