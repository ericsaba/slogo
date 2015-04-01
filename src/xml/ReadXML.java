package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import main.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


// Broken
public class ReadXML {

    private NodeList myNodeList;

    public ReadXML(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        myNodeList = doc.getDocumentElement().getChildNodes();
    }

    public Map<String, List<String>> makeMap() {
        Map<String, List<String>> myMap = new HashMap<String, List<String>>();
        populateMap(myNodeList, myMap);
        return myMap;
    }

    private void populateMap(NodeList list, Map<String, List<String>> myMap) {
        @SuppressWarnings("unused")
        NodeList theList = list;
        for (int i = 0; i < list.getLength(); i++) {
            Node currentNode = list.item(i);
            if (currentNode.getChildNodes().getLength() == Constants.ZERO) {
                String currentName = currentNode.getNodeName();
                String currentValue = currentNode.getTextContent();
                if (myMap.containsKey(currentName)) {
                    myMap.get(currentName).add(currentValue);
                }
                else {
                    List<String> myValue = new ArrayList<String>();
                    myValue.add(currentValue);
                    myMap.put(currentName, myValue);
                }
            }
            else {
                populateMap(currentNode.getChildNodes(), myMap);
            }
        }
    }

}
