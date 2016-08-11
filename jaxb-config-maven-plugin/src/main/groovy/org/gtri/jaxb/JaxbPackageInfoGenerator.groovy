package org.gtri.jaxb

class JaxbPackageInfoGenerator {

    /**
     * Given the output file, package name, URI, and list of namespace/prefix mappings, this static method writes
     * a package-info.java file that JAXB can use to make namespaces "Pretty" on output.
     */
    static void writePackageInfo(File outputFile, String packageName, String uri, Map mappings){
        LogHolder.getLog().info("Generating ${outputFile.canonicalPath}")

        StringWriter writer = new StringWriter();
        writer.println("""

/**
 * This file was auto-generated by the GTRI JAXB Project, ${Calendar.getInstance().getTime().toString()}
 */

@javax.xml.bind.annotation.XmlSchema(
    namespace = "${uri}", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
    xmlns = {
${generateXmlNsData(mappings)}
    }
)
package ${packageName};

import javax.xml.bind.annotation.*;

""")

        outputFile << writer.toString();

    }

    static String generateXmlNsData(Map mappings){
        StringWriter writer = new StringWriter();
        for( String uri : mappings.keySet() ?: []){
            String prefix = mappings.get(uri);
            writer.append("        @XmlNs(prefix = \"${prefix}\", namespaceURI = \"${uri}\"), \n")
        }
        return writer.toString();
    }

}/* end JaxbPackageInfoGenerator */