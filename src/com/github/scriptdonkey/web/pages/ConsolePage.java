package com.github.scriptdonkey.web.pages;

import org.wicketstuff.console.GroovyScriptEnginePanel;

import com.github.scriptdonkey.web.util.Login;

public class ConsolePage extends DonkeyBasePage {

    private static final long serialVersionUID = 1L;

    public ConsolePage() {
        if (!Login.isUserAdmin()) {
            setResponsePage(AccessDeniedPage.class);
        }

        final GroovyScriptEnginePanel enginePanel = new GroovyScriptEnginePanel(
                "console");
        enginePanel
                .setInput("import org.apache.wicket.ajax.AjaxRequestTarget;\n"
                        + "\n"
                        + "def templateId = 46\n"
                        + "def host = \"http://localhost:8888\"\n"
                        + "def url = new java.net.URL(\"${host}/api/templates/${templateId}\")\n"
                        + "def xml = new XmlSlurper().parse(url.newReader())\n"
                        + "component.setInput(xml.script.text())\n"
                        + "AjaxRequestTarget.get().add(component.getInputTf())\n");

        add(enginePanel);

    }
}
