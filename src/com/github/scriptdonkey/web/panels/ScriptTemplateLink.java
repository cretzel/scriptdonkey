package com.github.scriptdonkey.web.panels;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.github.scriptdonkey.web.pages.DonkeyHomePage;

final class ScriptTemplateLink extends Link<ScriptTemplate> {
    private static final long serialVersionUID = 1L;

    ScriptTemplateLink(final String id, final IModel<ScriptTemplate> model) {
        super(id, model);
    }

    @Override
    public void onClick() {
        final DonkeyHomePage homePage = new DonkeyHomePage(getModel());
        setResponsePage(homePage);
    }
}