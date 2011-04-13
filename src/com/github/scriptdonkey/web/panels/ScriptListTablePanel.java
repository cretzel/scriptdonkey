package com.github.scriptdonkey.web.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.github.scriptdonkey.web.dataprovider.MyScriptTemplateDataProvider;
import com.github.scriptdonkey.web.dataprovider.ScriptTemplateDataProvider;

public class ScriptListTablePanel extends Panel {

    private static final long serialVersionUID = 1L;

    private final class SwitchStateLink extends AjaxLink<Void> {
        private static final long serialVersionUID = 1L;
        private final State state;

        private SwitchStateLink(final String id, final State state) {
            super(id);
            this.state = state;
            setOutputMarkupId(true);
        }

        @Override
        public void onClick(final AjaxRequestTarget target) {
            ScriptListTablePanel.this.switchState(state, target);
        }

        @Override
        public boolean isEnabled() {
            return super.isEnabled()
                    && ScriptListTablePanel.this.state != this.state;
        }

    }

    private enum State {
        ALL, MINE, SEARCH_RESULTS, SEARCH_INPUT;
    }

    private State state = State.ALL;
    private final SwitchStateLink allScriptsLink;
    private final SwitchStateLink myScriptsLink;
    private final SwitchStateLink searchScriptLink;
    private final ScriptListTable table;

    public ScriptListTablePanel(final String id) {
        super(id);
        setOutputMarkupId(true);

        allScriptsLink = new SwitchStateLink("allscripts-link", State.ALL);
        add(allScriptsLink);
        myScriptsLink = new SwitchStateLink("myscripts-link", State.MINE);
        add(myScriptsLink);
        searchScriptLink = new SwitchStateLink("search-link",
                State.SEARCH_INPUT);
        add(searchScriptLink);

        table = new ScriptListTable("table", new ScriptTemplateDataProvider());
        table.setOutputMarkupId(true);
        add(table);
    }

    protected void switchState(final State newState,
            final AjaxRequestTarget target) {
        this.state = newState;
        switch (state) {
        case ALL:
            addOrReplace(new ScriptListTable("table",
                    new ScriptTemplateDataProvider()));
            break;
        case MINE:
            addOrReplace(new ScriptListTable("table",
                    new MyScriptTemplateDataProvider()));
            break;
        case SEARCH_INPUT:
            addOrReplace(new ScriptSearchPanel("table"));
            break;

        default:
            break;
        }
        target.add(this);
    }
}
