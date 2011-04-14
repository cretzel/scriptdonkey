package com.github.scriptdonkey.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.wicket.util.io.IOUtils;
import org.wicketstuff.console.engine.Engines;
import org.wicketstuff.console.engine.IScriptEngine;
import org.wicketstuff.console.engine.IScriptExecutionResult;
import org.wicketstuff.console.engine.Lang;

import com.github.scriptdonkey.model.ScriptTemplate;
import com.google.appengine.repackaged.com.google.common.collect.Maps;

/**
 * API-Servlet that returns a single ScriptTemplate as XML.
 * <p>
 * URL: /templates/{id}
 * <p>
 * Caution: Uses GroovyEngine, which may lead to PermGenSpace issues.
 * 
 * @author nwi
 * 
 */
public class ApiSingleScriptServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IScriptEngine engine;
    private String groovy;
    private Logger logger;

    @Override
    public void init() throws ServletException {
        super.init();

        engine = Engines.create(Lang.GROOVY);
        final URL resource = getClass().getClassLoader().getResource(
                "com/github/scriptdonkey/api/SingleScriptXmlBuilder.groovy");
        try {
            final BufferedInputStream bin = (BufferedInputStream) resource
                    .getContent();
            groovy = new String(IOUtils.toByteArray(bin));
        } catch (final IOException e) {
            throw new ServletException("Could not read groovy resource");
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {
        resp.setContentType("application/xml");

        Long templateKeyLong;
        try {
            templateKeyLong = extractTemplateKey(req, resp);
        } catch (final Exception e) {
            getLogger().warn("Could not extract templateKey", e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final ScriptTemplate template = ScriptTemplate.get(templateKeyLong);
        if (template == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        final Map<String, Object> bindings = Maps.newHashMap();
        bindings.put("template", template);

        final IScriptExecutionResult result = engine.execute(groovy, bindings);
        final Object returnValue = result.getReturnValue();

        resp.getOutputStream().write(returnValue.toString().getBytes());
    }

    private Long extractTemplateKey(final HttpServletRequest req,
            final HttpServletResponse resp) throws Exception {

        final StringBuffer requestURL = req.getRequestURL();
        final int lastSlash = requestURL.lastIndexOf("/");
        if (lastSlash == requestURL.length() - 1) {
            throw new Exception("no template key");
        }

        final String templateKey = requestURL.substring(lastSlash + 1);
        try {
            return Long.valueOf(templateKey);
        } catch (final Exception e) {
            throw new Exception("could not parse template key");
        }

    }

    public Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(ApiSingleScriptServlet.class);
        }
        return logger;
    }

}
