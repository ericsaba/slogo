package main;

import java.io.File;
import java.util.List;
import javafx.collections.ObservableMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.command.personal.GeneralPersonalCommand;
import model.data.DisplayData;
import model.data.FunctionData;
import model.data.VariableData;
import model.turtle.Pen;
import model.turtle.Turtle;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import controller.Controller;


public class WriteXML {
    private static Document myDoc;

    public static void writeXML(Controller controller, List<Turtle> turtles, File file) {
        try {
            DisplayData displayData =
                    (DisplayData)controller.getRegisteredObject(
                                                                Constants.DISPLAY_DATA,
                                                                controller.getModels());
            VariableData variableData =
                    (VariableData)controller.getRegisteredObject(
                                                                 Constants.VARIABLE_DATA,
                                                                 controller.getModels());
            FunctionData functionData =
                    (FunctionData)controller.getRegisteredObject(
                                                                 Constants.FUNCTION_DATA,
                                                                 controller.getModels());

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            myDoc = doc;
            Element rootElement = doc.createElement(Constants.XML_ROOT);
            doc.appendChild(rootElement);

            rootElement.appendChild(createDisplayElement(displayData, turtles));
            rootElement.appendChild(createFunctionElement(functionData));
            rootElement.appendChild(createVariablesElement(variableData));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private static Element createFunctionElement(FunctionData functionData) {
        Element functions = myDoc.createElement(Constants.XML_FUNCTIONS);
        ObservableMap<String, GeneralPersonalCommand> functionMap =
                functionData.getPersonalCommands();
        for (String key : functionMap.keySet()) {
            Element function = myDoc.createElement(Constants.XML_FUNCTION);
            function.appendChild(createLeafElement(Constants.XML_FUNCTION_NAME, key));
            GeneralPersonalCommand thisFunction = functionMap.get(key);
            function.appendChild(createLeafElement(Constants.XML_ARGUMENTS, thisFunction.getVars()));
            function.appendChild(createLeafElement(Constants.XML_COMMANDS, thisFunction.getText()));
            functions.appendChild(function);
        }
        return functions;
    }

    private static Element createVariablesElement(VariableData variableData) {
        Element variables = myDoc.createElement(Constants.XML_VARIABLES);
        ObservableMap<String, Double> variablesMap = variableData.getVars();

        for (String key : variablesMap.keySet()) {
            Element variable = myDoc.createElement(Constants.XML_VARIABLE);
            variable.appendChild(createLeafElement(Constants.XML_VARIABLE_NAME, key));
            variable.appendChild(createLeafElement(Constants.XML_VALUE,
                                                   String.format("%f", variablesMap.get(key))));
            variables.appendChild(variable);
        }

        return variables;
    }

    private static Element createDisplayElement(DisplayData displayData,
                                                List<Turtle> turtleList) {
        Element display = myDoc.createElement(Constants.XML_DISPLAY);
        Element turtles = myDoc.createElement(Constants.XML_TURTLES);

        for (Turtle turtle : turtleList) {
            turtles.appendChild(createTurtleElement(turtle));
        }

        display.appendChild(turtles);

        display.appendChild(createLeafElement(Constants.XML_BACKGROUND_COLOR, displayData
                .getBackgroundColor().toString()));
        display.appendChild(createLeafElement(Constants.XML_LANGUAGE, displayData.getLanguage()
                .toString()));

        display.appendChild(createPaletteElement(displayData));
        return display;
    }

    private static Element createPaletteElement(DisplayData displayData) {
        Element palette = myDoc.createElement(Constants.XML_PALETTE);
        for (int index : displayData.getPaletteMap().keySet()) {
            Element colorEle = myDoc.createElement(Constants.XML_PALETTE_COLOR);
            Attr colorIndex = myDoc.createAttribute(Constants.XML_COLOR_INDEX);
            colorIndex.setValue(String.format("%d", index));
            colorEle.setAttributeNode(colorIndex);
            colorEle.appendChild(myDoc
                    .createTextNode(displayData.getPaletteColor(index).toString()));
            palette.appendChild(colorEle);
        }

        return palette;
    }

    private static Element createTurtleElement(Turtle turtle) {
        Element turtleEle = myDoc.createElement(Constants.XML_TURTLE);

        Attr turtleName = myDoc.createAttribute(Constants.XML_TURTLE_ID);
        turtleName.setValue(String.format("%d", turtle.getName()));
        turtleEle.setAttributeNode(turtleName);

        addTurtleChildElements(turtleEle, turtle);

        turtleEle.appendChild(createPenElement(turtle.getPen()));

        return turtleEle;
    }

    private static void addTurtleChildElements(Element turtleEle, Turtle turtle) {
        turtleEle.appendChild(createLeafElement(Constants.XML_XCOOR,
                                                String.format("%f", turtle.getX())));
        turtleEle.appendChild(createLeafElement(Constants.XML_YCOOR,
                                                String.format("%f", turtle.getY())));
        turtleEle.appendChild(createLeafElement(Constants.XML_OLDX,
                                                String.format("%f", turtle.getOldX())));
        turtleEle.appendChild(createLeafElement(Constants.XML_OLDY,
                                                String.format("%f", turtle.getOldY())));
        turtleEle.appendChild(createLeafElement(Constants.XML_ANGLE,
                                                String.format("%f", turtle.getHeading())));
        turtleEle.appendChild(createLeafElement(Constants.XML_OLD_ANGLE,
                                                String.format("%f", turtle.getOldHeading())));
        turtleEle.appendChild(createLeafElement(Constants.XML_VISIBLE,
                                                String.format("%f", turtle.isShowing())));
    }

    private static Element createPenElement(Pen pen) {
        Element penEle = myDoc.createElement(Constants.XML_PEN);
        penEle.appendChild(createLeafElement(Constants.XML_PEN_DOWN,
                                             String.format("%f", pen.getDown())));
        penEle.appendChild(createLeafElement(Constants.XML_STYLE, pen.getStyle().toString()));
        penEle.appendChild(createLeafElement(Constants.XML_THICKNESS,
                                             String.format("%f", pen.getThickness())));
        penEle.appendChild(createLeafElement(Constants.XML_PEN_COLOR,
                                             String.format("%d", pen.getColorIndex())));
        return penEle;
    }

    private static Element createLeafElement(String name, String value) {
        Element ele = myDoc.createElement(name);
        ele.appendChild(myDoc.createTextNode(value));
        return ele;
    }
}
