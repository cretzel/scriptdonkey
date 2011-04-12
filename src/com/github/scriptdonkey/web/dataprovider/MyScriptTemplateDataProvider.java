package com.github.scriptdonkey.web.dataprovider;

import java.util.Iterator;
import java.util.List;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.github.scriptdonkey.web.util.Login;
import com.google.appengine.api.users.User;

public class MyScriptTemplateDataProvider extends ScriptTemplateDataProvider {

    private static final long serialVersionUID = 1L;

    @Override
    public Iterator<? extends ScriptTemplate> iterator(final int first,
            final int count) {
        final User user = Login.getCurrentUserSafe();
        final List<ScriptTemplate> templates = ScriptTemplate.all(user, first,
                count);
        return templates.iterator();
    }

    @Override
    public int size() {
        final User user = Login.getCurrentUserSafe();
        return ScriptTemplate.size(user);
    }

}
