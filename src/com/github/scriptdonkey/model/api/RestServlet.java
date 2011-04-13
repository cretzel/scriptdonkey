package com.github.scriptdonkey.model.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wicketstuff.console.engine.Engines;
import org.wicketstuff.console.engine.IScriptEngine;
import org.wicketstuff.console.engine.IScriptExecutionResult;
import org.wicketstuff.console.engine.Lang;

public class RestServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getOutputStream().write("Hello World".getBytes());
        final IScriptEngine engine = Engines.create(Lang.GROOVY);
        final IScriptExecutionResult result = engine.execute("42");
        final Object returnValue = result.getReturnValue();
        resp.getOutputStream().write(returnValue.toString().getBytes());
    }

}
