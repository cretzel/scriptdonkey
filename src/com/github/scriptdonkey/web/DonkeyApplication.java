package com.github.scriptdonkey.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.util.lang.WicketObjects;

import com.github.scriptdonkey.model.Lang;
import com.github.scriptdonkey.web.pages.AccessDeniedPage;
import com.github.scriptdonkey.web.pages.ConsolePage;
import com.github.scriptdonkey.web.pages.DonkeyHomePage;
import com.github.scriptdonkey.web.pages.InternalErrorPage;
import com.github.scriptdonkey.web.pages.PageExpiredPage;
import com.github.scriptdonkey.web.util.LangConverter;

public class DonkeyApplication extends WebApplication {

    private DonkeyModificationWatcher watcher;

    @Override
    protected void init() {
        super.init();

        setPageManagerProvider(new HttpSessionPageManagerProvider(this));

        watcher = new DonkeyModificationWatcher();
        getResourceSettings().setResourceWatcher(watcher);

        WicketObjects.setObjectStreamFactory(new DonkeyObjectStreamFactory());

        final ConverterLocator converterLocator = (ConverterLocator) getConverterLocator();
        converterLocator.set(Lang.class, new LangConverter());

        getExceptionSettings().setUnexpectedExceptionDisplay(
                IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
        getApplicationSettings().setInternalErrorPage(InternalErrorPage.class);
        getApplicationSettings().setPageExpiredErrorPage(PageExpiredPage.class);
        getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);

        mountPage("console", ConsolePage.class);
    }

    @Override
    public Session newSession(final Request request, final Response response) {
        final WebSession webSession = new WebSession(request);
        webSession.bind();
        return webSession;
    }

    @Override
    protected WebRequest newWebRequest(final HttpServletRequest servletRequest,
            final String filterPath) {
        if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
            watcher.check();
        }
        return super.newWebRequest(servletRequest, filterPath);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEPLOYMENT;
        // return RuntimeConfigurationType.DEVELOPMENT;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return DonkeyHomePage.class;
    }

}
