package com.github.scriptdonkey;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class SpotBasePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public SpotBasePage() {
        super();
        init();
    }

    public SpotBasePage(final PageParameters parameters) {
        super(parameters);
        init();
    }

    public SpotBasePage(final IModel<?> model) {
        super(model);
        init();
    }

    private void init() {
        add(new LoginLink("login"));
        add(new FeedbackPanel("feedback"));
    }

}
