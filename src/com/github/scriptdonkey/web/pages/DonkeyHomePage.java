package com.github.scriptdonkey.web.pages;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.github.scriptdonkey.web.panels.ScriptListTablePanel;
import com.github.scriptdonkey.web.panels.UploadPanel;
import com.github.scriptdonkey.web.panels.ViewScriptPanel;
import com.github.scriptdonkey.web.util.Login;
import com.google.appengine.api.users.User;

public class DonkeyHomePage extends DonkeyBasePage {

    private static final long serialVersionUID = 1L;

    public DonkeyHomePage() {
        super(new CompoundPropertyModel<ScriptTemplate>(new ScriptTemplate()));

        init();
    }

    public DonkeyHomePage(final IModel<ScriptTemplate> model) {
        super(model);

        init();
    }

    private void init() {

        @SuppressWarnings("unchecked")
        final IModel<ScriptTemplate> model = (IModel<ScriptTemplate>) getDefaultModel();
        final ScriptTemplate template = model.getObject();
        final boolean isUpdateAllowed = isUpdateAllowed(template);

        if (isUpdateAllowed) {
            add(new UploadPanel("uploadPanel", model));
        } else {
            add(new ViewScriptPanel("uploadPanel", model));
        }

        add(new ScriptListTablePanel("table"));
    }

    private boolean isUpdateAllowed(final ScriptTemplate template) {
        final User user = Login.getCurrentUserSafe();
        return template.isUpdateAllowed(user);
    }
}
