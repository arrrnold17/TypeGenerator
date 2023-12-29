import org.apache.xerces.parsers.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Generator {
    private static final String OUTPUT_DIR= "src/main/resources/";
    private Logger logger = Logger.getLogger(Generator.class.getName());
    public void XMLParser(String path) {
        // read xml file
        try {
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
        } catch (Exception e) {
            logger.severe("Failed to parse xml file" + e.getMessage());
        }
    }

    public void generateClass(String className, List<String> attrs) {
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

        String classContent = sb.toString();
        createFile(className, classContent);
    }

    public void createFile(String className, String content) {
        Path path = Path.of(OUTPUT_DIR + className + ".java");
        try {
            if (Files.exists(path)) {
                logger.info("File already exists");
                return;
            }
            Files.createFile(path);
            Files.writeString(path, content);
        } catch (Exception e) {
            logger.severe("Failed to create file" + e.getMessage());
        }
    }
}
