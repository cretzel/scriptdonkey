package com.github.scriptdonkey.web.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.github.scriptdonkey.model.ScriptTemplate;

public class ViewScriptPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private final Label title;
    private final Label lang;
    private final TextArea<String> scriptTa;

    public ViewScriptPanel(final String id, final IModel<ScriptTemplate> model) {
        super(id, new CompoundPropertyModel<ScriptTemplate>(model));

        title = new Label("title");
        add(title);

        lang = new Label("lang");
        add(lang);

        scriptTa = new TextArea<String>("script");
        add(scriptTa);

    }

}
