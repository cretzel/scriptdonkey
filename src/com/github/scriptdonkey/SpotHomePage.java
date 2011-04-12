package com.github.scriptdonkey;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.google.appengine.api.users.User;

public class SpotHomePage extends SpotBasePage {

    private static final long serialVersionUID = 1L;

    public SpotHomePage() {
        super(new CompoundPropertyModel<ScriptTemplate>(new ScriptTemplate()));

        init();
    }

    public SpotHomePage(final IModel<ScriptTemplate> model) {
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
