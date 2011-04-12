package com.github.scriptdonkey;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class ScriptSearchPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private String input;

    private final ScriptListTable table;

    public ScriptSearchPanel(final String id) {
        super(id);

        final Form<Void> form = new Form<Void>("form");
        add(form);
        form.add(new TextField<String>("input", new PropertyModel<String>(this,
                "input")));
        form.add(new AjaxButton("submit", form) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target,
                    final Form<?> form) {
                target.add(table);
            }

            @Override
            protected void onError(final AjaxRequestTarget target,
                    final Form<?> form) {

            }
        });

        table = new ScriptListTable("table",
                new SearchScriptTemplateDataProvider(new PropertyModel<String>(
                        this, "input")));
        table.setOutputMarkupId(true);
        add(table);

    }

    public String getInput() {
        return input;
    }

    public void setInput(final String input) {
        this.input = input;
    }

}
