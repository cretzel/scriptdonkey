package com.github.scriptdonkey.web.pages;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.github.scriptdonkey.web.util.LoginLink;

public abstract class DonkeyBasePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public DonkeyBasePage() {
        super();
        init();
    }

    public DonkeyBasePage(final PageParameters parameters) {
        super(parameters);
        init();
    }

    public DonkeyBasePage(final IModel<?> model) {
        super(model);
        init();
    }

    private void init() {
        add(new LoginLink("login"));
        add(new FeedbackPanel("feedback"));
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
    }

}
