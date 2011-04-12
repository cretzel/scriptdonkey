package com.github.scriptdonkey.web.dataprovider;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.model.PropertyModel;

import com.github.scriptdonkey.model.ScriptTemplate;

public class SearchScriptTemplateDataProvider extends
        ScriptTemplateDataProvider {

    private static final long serialVersionUID = 1L;
    private final PropertyModel<String> inputModel;

    public SearchScriptTemplateDataProvider(
            final PropertyModel<String> inputModel) {
        this.inputModel = inputModel;
    }

    @Override
    public Iterator<? extends ScriptTemplate> iterator(final int first,
            final int count) {
        final List<ScriptTemplate> templates = ScriptTemplate.search(
                inputModel.getObject(), first, count);
        return templates.iterator();
    }

    @Override
    public int size() {
        return ScriptTemplate.searchSize(inputModel.getObject());
    }

}
