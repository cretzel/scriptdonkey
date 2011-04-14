import org.apache.wicket.ajax.AjaxRequestTarget;

def templateId = 46
def host = "http://localhost:8888"
def url = new java.net.URL("${host}/api/templates/${templateId}")
def xml = new XmlSlurper().parse(url.newReader())
component.setInput(xml.script.text())
AjaxRequestTarget.get().add(component.getInputTf())
