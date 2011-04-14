import org.apache.wicket.ajax.AjaxRequestTarget;

def templateId = 1
def host = "http://scriptdonkey.appspot.com"
def url = new java.net.URL("${host}/api/templates/${templateId}")
def con = url.openConnection()
con.setConnectTimeout(10000)

def xml = new XmlSlurper().parse(con.getContent())
component.setInput(xml.script.text())
AjaxRequestTarget.get().add(component.getInputTf())
