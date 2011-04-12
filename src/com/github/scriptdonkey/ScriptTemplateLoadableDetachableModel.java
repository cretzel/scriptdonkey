package com.github.scriptdonkey;

import org.apache.wicket.model.LoadableDetachableModel;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.google.appengine.api.datastore.Key;

public class ScriptTemplateLoadableDetachableModel extends
        LoadableDetachableModel<ScriptTemplate> {
    private static final long serialVersionUID = 1L;
    private final Key key;

    public ScriptTemplateLoadableDetachableModel(final ScriptTemplate template) {
        super(template);
        key = template.getKey();
    }

    @Override
    protected ScriptTemplate load() {
        return ScriptTemplate.get(key);
    }
}