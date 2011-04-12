package com.github.scriptdonkey.web.dataprovider;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.github.scriptdonkey.web.model.ScriptTemplateLoadableDetachableModel;

public class ScriptTemplateDataProvider implements
        IDataProvider<ScriptTemplate> {
    private static final long serialVersionUID = 1L;

    @Override
    public Iterator<? extends ScriptTemplate> iterator(final int first,
            final int count) {
        final List<ScriptTemplate> templates = ScriptTemplate.all(first, count);
        return templates.iterator();
    }

    @Override
    public IModel<ScriptTemplate> model(final ScriptTemplate template) {
        return new ScriptTemplateLoadableDetachableModel(template);
    }

    @Override
    public int size() {
        return ScriptTemplate.size();
    }

    @Override
    public void detach() {
    }
}