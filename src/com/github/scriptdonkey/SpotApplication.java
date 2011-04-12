package com.github.scriptdonkey;


import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.lang.WicketObjects;

import com.github.scriptdonkey.model.Lang;

public class SpotApplication extends WebApplication {

    private SpotModificationWatcher watcher;

    @Override
    protected void init() {
        super.init();

        setPageManagerProvider(new HttpSessionPageManagerProvider(this));

        watcher = new SpotModificationWatcher();
        getResourceSettings().setResourceWatcher(watcher);

        WicketObjects.setObjectStreamFactory(new SpotObjectStreamFactory());

        final ConverterLocator converterLocator = (ConverterLocator) getConverterLocator();
        converterLocator.set(Lang.class, new LangConverter());
    }

    @Override
    protected WebRequest newWebRequest(final HttpServletRequest servletRequest,
            final String filterPath) {
        watcher.check();
        return super.newWebRequest(servletRequest, filterPath);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        // return RuntimeConfigurationType.DEPLOYMENT;
        return RuntimeConfigurationType.DEVELOPMENT;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return SpotHomePage.class;
    }


}
