package com.github.scriptdonkey.web.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.github.scriptdonkey.model.ScriptTemplate;

public class ScriptListTable extends Panel {

    private static final long serialVersionUID = 1L;

    public final class TitleColumn extends PropertyColumn<ScriptTemplate> {
        private static final long serialVersionUID = 1L;

        TitleColumn(final IModel<String> displayModel,
                final String sortProperty, final String propertyExpression) {
            super(displayModel, sortProperty, propertyExpression);
        }

        @Override
        public void populateItem(
                final Item<ICellPopulator<ScriptTemplate>> item,
                final String componentId, final IModel<ScriptTemplate> rowModel) {

            final Fragment frag = new Fragment(componentId, "titleFrag",
                    ScriptListTable.this);
            final Link<ScriptTemplate> link = new ScriptTemplateLink("link",
                    rowModel);
            link.add(new Label("title", new PropertyModel<String>(rowModel,
                    "title")));

            frag.add(link);
            item.add(frag);

            item.add(new AttributeAppender("class", Model.of("title-col"), " "));
            item.add(new AttributeAppender("title", new PropertyModel<String>(
                    rowModel, "title"), " "));

        }
    }

    public ScriptListTable(final String id,
            final IDataProvider<ScriptTemplate> dataProvider) {
        super(id);

        final List<IColumn<ScriptTemplate>> columns = new ArrayList<IColumn<ScriptTemplate>>();
        final IColumn<ScriptTemplate> titleColumn = new TitleColumn(
                Model.of("Title"), "title", "title");
        columns.add(titleColumn);
        final IColumn<ScriptTemplate> langColumn = new PropertyColumn<ScriptTemplate>(
                Model.of("Lang"), "lang", "lang");
        columns.add(langColumn);

        final int rowsPerPage = 12;
        final DataTable<ScriptTemplate> table = new DataTable<ScriptTemplate>(
                "table", columns, dataProvider, rowsPerPage);
        table.setOutputMarkupId(true);
        add(table);

        final AjaxPagingNavigator navigator = new AjaxPagingNavigator("paging",
                table);
        add(navigator);

    }
}
