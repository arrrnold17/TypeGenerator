import org.apache.xerces.parsers.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void XMLParser(String path) throws Exception {
        // read xml file
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File(path));
        doc.getDocumentElement().normalize();

        // get root node
        Node root = doc.getDocumentElement();
        NamedNodeMap attributes = root.getAttributes();

        String className = attributes.item(0).getNodeValue();
        List<String> attr = new ArrayList<>();

        // get child nodes
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (!childNode.getNodeName().equals("#text")) {
                attr.add(childNode.getNodeName());
            }
        }
        generateClass(className, attr);
    }

    public static void generateClass(String className, List<String> attrs) {
        // generate class
        StringBuilder sb = new StringBuilder();
        sb.append("public class " + className + " {\n");

        for (String attr : attrs) {
            sb.append("    private " + "String " + attr + ";\n");
        }
        sb.append("\n");

        for (String attr : attrs) {
            sb.append("    public String get" + attr.substring(0, 1).toUpperCase() + attr.substring(1) + "() {\n");
            sb.append("        return " + attr + ";\n");
            sb.append("    }\n\n");
            sb.append("    public void set" + attr.substring(0, 1).toUpperCase() + attr.substring(1) + "(String " + attr + ") {\n");
            sb.append("        this." + attr + " = " + attr + ";\n");
            sb.append("    }\n\n");
        }
        sb.append("}");
        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        Generator.XMLParser("src/main/resources/test.xml");
    }
}
