package com.github.scriptdonkey.web.panels;

import java.util.Arrays;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.StringValidator.LengthBetweenValidator;

import com.github.scriptdonkey.model.Lang;
import com.github.scriptdonkey.model.ScriptTemplate;
import com.github.scriptdonkey.web.model.ScriptTemplateLoadableDetachableModel;
import com.github.scriptdonkey.web.pages.DonkeyHomePage;
import com.github.scriptdonkey.web.util.LangChoiceRenderer;
import com.github.scriptdonkey.web.util.Login;
import com.google.appengine.api.users.User;

public class UploadPanel extends Panel {

    private final class SubmitButton extends Button {
        private static final long serialVersionUID = 1L;

        private SubmitButton(final String id) {
            super(id);
        }

        @Override
        public boolean isVisible() {
            return super.isVisible() && isUpdateAllowed();
        }
    }

    private final class DeleteButton extends Button {
        private static final long serialVersionUID = 1L;

        private DeleteButton(final String id) {
            super(id);
            setDefaultFormProcessing(false);
        }

        @Override
        public void onSubmit() {
            UploadPanel.this.onDelete();
        }

        @Override
        public boolean isVisible() {
            final boolean isAllowed = isDeleteAllowed();
            return super.isVisible() && isAllowed;
        }

    }

    private static final long serialVersionUID = 1L;
    private final TextField<String> titleTf;
    private final Form<ScriptTemplate> form;
    private final DropDownChoice<Lang> langDd;
    private final TextArea<String> scriptTa;

    public UploadPanel(final String id, final IModel<ScriptTemplate> model) {
        super(id, new CompoundPropertyModel<ScriptTemplate>(model));

        form = new Form<ScriptTemplate>("form") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                UploadPanel.this.onSubmit();
            }

        };
        add(form);

        titleTf = new TextField<String>("title");
        titleTf.setEnabled(isUpdateAllowed());
        titleTf.setLabel(Model.of("Script Title"));
        titleTf.setRequired(true);
        titleTf.add(LengthBetweenValidator.lengthBetween(3, 42));
        form.add(titleTf);

        langDd = new DropDownChoice<Lang>("lang", Arrays.asList(Lang.values()),
                new LangChoiceRenderer());
        langDd.setEnabled(isUpdateAllowed());
        form.add(langDd);

        scriptTa = new TextArea<String>("script");
        scriptTa.setEnabled(isUpdateAllowed());
        form.add(scriptTa);

        form.add(new SubmitButton("submit"));
        form.add(new DeleteButton("delete"));

        final IModel<String> infoModel = new ResourceModel(
                Login.isUserLoggedIn() ? "info.loggedin" : "info.notloggedin");
        final Label info = new Label("info", infoModel);
        form.add(info);

    }

    private boolean isUpdateAllowed() {
        final ScriptTemplate t = getScriptTemplate();
        final User user = Login.getCurrentUserSafe();
        return t.isUpdateAllowed(user);
    }

    private boolean isDeleteAllowed() {
        final ScriptTemplate t = getScriptTemplate();
        final User user = Login.getCurrentUserSafe();
        final boolean isAllowed = t.isDeleteAllowed(user);
        return isAllowed;
    }

    protected void onSubmit() {
        final ScriptTemplate template = getScriptTemplate();

        if (isUpdateAllowed()) {
            template.save(Login.getCurrentUserSafe());
            getSession().info("Script was saved");
        } else {
            getSession().error("Not allowed");
        }
        setResponsePage(new DonkeyHomePage(
                new ScriptTemplateLoadableDetachableModel(template)));
    }

    protected void onDelete() {
        final ScriptTemplate template = getScriptTemplate();
        template.delete(Login.getCurrentUserSafe());
        getSession().info("Script was deleted");
        setResponsePage(DonkeyHomePage.class);
    }

    private ScriptTemplate getScriptTemplate() {
        return (ScriptTemplate) getDefaultModelObject();
    }

}
