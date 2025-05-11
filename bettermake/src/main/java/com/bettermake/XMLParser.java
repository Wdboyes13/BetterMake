package com.bettermake;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
// ===========================
// === XML PARSING METHODS ===
// ===========================

public class XMLParser {
    // ===========================================
    // === XML NESTING OPTIMIZATION - NESTED 3 ===
    // ===========================================
    private static String getTagContent(Element parent, String childTag, String tag) {
        NodeList childNodes = parent.getElementsByTagName(childTag);
        if (childNodes.getLength() > 0) {
            Element childElement = (Element) childNodes.item(0);
            NodeList tagList = childElement.getElementsByTagName(tag);
            if (tagList.getLength() > 0) {
                return tagList.item(0).getTextContent();
            }
        }
        return null;  // Return null if tag is not found
    }
    // =====================
    // === XML PARSING ===
    // =====================
    public static HashMap<String, String> parse(){
        HashMap<String, String> data = new HashMap<>();
        try {
            File inputFile = new File("mk.xml");  // Your XML filename here
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaLocation = new URI("https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF.xsd?v=2").toURL();
            Schema schema = factory.newSchema(schemaLocation);

            // Create validator
            Validator validator = schema.newValidator();

            // Validate the XML file
            validator.validate(new StreamSource(inputFile));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList CCs = doc.getElementsByTagName("Compilers");
            NodeList SRC = doc.getElementsByTagName("SRC");
            NodeList GIT = doc.getElementsByTagName("GIT");

            // Ensure the Compilers node exists
            if (CCs.getLength() > 0) {
                Element compilersElement = (Element) CCs.item(0);

                // Now safely try to get ARM and SF nodes under Linux and Mac
                String linuxArm = getTagContent(compilersElement, "Linux", "ARM");
                String linux64 = getTagContent(compilersElement, "Linux", "SF");
                String macArm = getTagContent(compilersElement, "Mac", "ARM");
                String mac64 = getTagContent(compilersElement, "Mac", "SF");
                String winArm = getTagContent(compilersElement, "Win", "ARM");
                String win64 = getTagContent(compilersElement, "Win", "SF");
                System.out.println("Linux ARM: " + linuxArm);
                System.out.println("Linux 64: " + linux64);
                System.out.println("Mac ARM: " + macArm);
                System.out.println("Mac 64: " + mac64);
                System.out.println("Win ARM: " + winArm);
                System.out.println("Win 64: " + win64);

                data.put("LINARMCC", linuxArm);
                data.put("LIN64CC", linux64);
                data.put("MACARMCC", macArm);
                data.put("MAC64CC", mac64);
                data.put("WIN64CC", win64);
                data.put("WINARMCC", winArm);

                String globLinks = getTagContent(compilersElement, "flags", "globalLink");
                String globComps = getTagContent(compilersElement, "flags", "globalComp");
                System.out.println("Global Linker Flags: " + globLinks);
                data.put("GLOBFLAGSL", globLinks);
                System.out.println("Global Compiler Flags: " + globComps);
                data.put("GLOBFLAGS", globComps);
            } else {System.out.println("Compiler Tags Invalid"); System.exit(1);}

            String fileName = doc.getElementsByTagName("FILE").item(0).getTextContent(); // 2nd <FILE>
            if (SRC.getLength() > 0){
                Element srcElement = (Element) SRC.item(0);
                String lang = srcElement.getElementsByTagName("LANG").item(0).getTextContent();
                String srcType = srcElement.getElementsByTagName("Type").item(0).getTextContent();
                String srcFile = srcElement.getElementsByTagName("FILE").item(0).getTextContent(); // 3rd <FILE>
                System.out.println("Source Language: " + lang);
                System.out.println("Source Type: " + srcType);
                System.out.println("Source File: " + srcFile);
                data.put("LANG", lang);
                data.put("SRCT", srcType);
                data.put("SRCF", srcFile);
            } else {System.out.println("Project Data Tags Invalid"); System.exit(1);}
            if (GIT.getLength() > 0){
                String repo = doc.getElementsByTagName("REPO").item(0).getTextContent();
                String msg = doc.getElementsByTagName("COMMSG").item(0).getTextContent();
                System.out.println("Git Repo: " + repo);
                System.out.println("Git Commit Message: " + msg);
                data.put("REPO", repo);
                data.put("MSG", msg);
            }
            System.out.println("Main Output File: " + fileName);

            data.put("OUTF", fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
