/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibray;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Thales
 */
public class XML {
    
    public Document doc;
    public Element rootElement;
            
    public XML(String name) throws ParserConfigurationException{
        //new document
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        rootElement = doc.createElement(name);
    }
    
    private XML(){    }
    
    public void addAttribute(String name, String value){
        rootElement.setAttribute(name, value);
    }
    
    public String getAttribute(String name){
        return rootElement.getAttribute(name);
    }
    
    
    public void setData(double[] data){
        doc.appendChild(rootElement);
        Element root = (Element) doc.getFirstChild();
        for (Double d : data){
            Element codon = doc.createElement("data");
            codon.setTextContent(Double.toHexString(d));
            root.appendChild(codon);
        }
    }
    
    public void save(String directory) throws TransformerConfigurationException, TransformerException{
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(directory));
        transformer.transform(source, result);
    }
    
    public static XML loadXML(String directory) throws ParserConfigurationException, SAXException, IOException{
        XML temp = new XML();
        File fXmlFile = new File(directory);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        temp.doc = dBuilder.parse(fXmlFile);
        temp.doc.getDocumentElement().normalize();
        temp.rootElement = temp.doc.getDocumentElement();
        return temp;
    }
    
    public double[] getData(){
        NodeList ndata = doc.getElementsByTagName("data");
        double[] data =  new double[ndata.getLength()];
        for (int i = 0; i < data.length; i++){
            data[i] = Double.valueOf(ndata.item(i).getTextContent());
        }
        return data;
    }
}
