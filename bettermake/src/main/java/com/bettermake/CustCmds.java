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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// ==============================
// === CUSTOM COMMAND METHODS ===
// ==============================

public class CustCmds {
    // ==============================
    // === CUSTOM COMMAND PARSING ===
    // ==============================
    public static String[] getCmds() {
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
            NodeList CMDS = doc.getElementsByTagName("CMDS");

            if (CMDS.getLength() > 0) {
                Element cmdsElement = (Element) CMDS.item(0);
                NodeList cmdList = cmdsElement.getElementsByTagName("CMD");

                String[] commands = new String[cmdList.getLength()];
                for (int i = 0; i < cmdList.getLength(); i++) {
                    commands[i] = cmdList.item(i).getTextContent().trim();
                }
                return commands;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
    // ====================================
    // === CUSTOM COMMAND REGEX PARSING ===
    // ====================================
    public static List<String> splitCommand(String command) {
        List<String> args = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|'([^']*)'|([^\\s]+)").matcher(command);
        while (m.find()) {
            if (m.group(1) != null) {
                args.add(m.group(1)); // double-quoted
            } else if (m.group(2) != null) {
                args.add(m.group(2)); // single-quoted
            } else {
                args.add(m.group(3)); // bare words
            }
        }
        return args;
    }
    // ==============================
    // === CUSTOM COMMAND RUNNING ===
    // ==============================
    public static void runCmds() throws Exception{
        String[] cmds = getCmds();
        for (String cmd : cmds){
            if (cmd != null && !cmd.trim().isEmpty()) {
                String[] SplCmd = splitCommand(cmd).toArray(new String[0]);
                ProcessBuilder CmdPB = new ProcessBuilder(SplCmd);
                CmdPB.inheritIO();
                Process Proc = CmdPB.start();
                Proc.waitFor();
                System.out.println("Command: " + Arrays.toString(SplCmd) + " PID: " + Proc.pid() + " EXIT CODE: " + Proc.exitValue());
            }
        }
    }
}
