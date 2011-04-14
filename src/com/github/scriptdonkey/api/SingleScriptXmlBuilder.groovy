
def builder = new groovy.xml.StreamingMarkupBuilder()
builder.encoding = "UTF-8"
def xml = {
  mkp.xmlDeclaration()
  script(id:template.key.id){
    title(template.title)
    script {
	    mkp.yieldUnescaped("<![CDATA[${template.script}]]>")
	}
  }
}
def writer = new StringWriter()
writer << builder.bind(xml)
writer.toString()
