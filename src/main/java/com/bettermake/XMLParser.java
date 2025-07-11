/*
BetterMake - A Build Tool for C, C++, ObjC, ObjC++
Copyright (C) 2025  Wdboyes13

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see https://www.gnu.org/licenses/.
*/

package com.bettermake;

// W3C DOM Parsing Libraries
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// JAVA EE XML Libraries (Schema and File parsing)
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

// Java SE Libraries (IO, Network, Lists)
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
    // ===================
    // === XML PARSING ===
    // ===================
    public static HashMap<String, String> parse(){
        HashMap<String, String> data = new HashMap<>();
        try {
            File inputFile = new File("mk.xml");
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

                // Now safely try to get ARM and SF nodes under Linux Mac and Windows
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

            String fileName = doc.getElementsByTagName("FILE").item(0).getTextContent(); // 1nd <FILE>
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

// =======================
// === XML Definitions ===
// =======================

// REPO - Git Repository URL
// COMMSG - Git Commit Message (String or PROMPT)

// LANG - Project Language (C, CPP, OBJC, OBJCPP)
// SRCT - Project Type (OneFile or MultiFile)
// SRCF - The Source file or directory
// OUTF - The Final Binary Name

// LINARMCC - Linux AArch64 Compiler
// LIN64CC - Linux x86_64 Compiler
// MACARMCC - macOS AArch64 Compiler
// MAC64CC - macOS x86_64 Compiler
// WIN64CC - Windows x86_64 Compiler
// WINARMCC - Windows AArch64 Compiler

// GLOBFLAGSL - Global Linker Flags
// GLOBFLAGS - Global Compiler Flags

// CMD - Custom Command to Run