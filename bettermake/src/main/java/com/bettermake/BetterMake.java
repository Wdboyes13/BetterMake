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

import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.IOException;


// =================
// === MAIN FILE ===
// =================

public class BetterMake {
        // =======================
        // === FINDING C FILES ===
        // =======================
        public static List<Path> findCFiles(String rootDir, String lang) throws IOException {
                String langext;
                if (lang.equals("C")) langext = ".c";
                else if (lang.equals("CPP")) langext = ".cpp";
                else if (lang.equals("OBJC")) langext = ".m";
                else if (lang.equals("OBJCPP")) langext = ".mm";
                else {
                    langext = new String();
                }
            return Files.walk(Paths.get(rootDir))
                        .filter(p -> p.toString().endsWith(langext))
                        .collect(Collectors.toList());
        }

        // ====================
        // === GIT UPDATING ===
        // ====================
        public static void gitUpdate(HashMap<String, String> data ) throws IOException, InterruptedException{
            String msg = new String();
            if (data.get("REPO")!=null && !data.get("REPO").isEmpty()) {
                System.out.println(("Running git"));
                String repo = data.get("REPO");
                if (data.get("MSG")!=null && !data.get("MSG").isEmpty()) msg=data.get("MSG"); else msg="Updated";
                if (msg.equals("PROMPT")){
                    Scanner inp = new Scanner(System.in);
                    System.out.print("Enter Commit Message: ");
                    msg = inp.nextLine();
                    inp.close();
                }
                String[] cmd = {"bash", "-c", "git add . && git commit -m \"" + msg + "\" && git push " + repo + " main"};
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.inheritIO();
                Process proc = pb.start();
                proc.waitFor();
            }
        }
        // ===================
        // === MAIN METHOD ===
        // ===================
        public static void main(String[] args) throws Exception {
            HashMap<String, String> data = XMLParser.parse(); // Run's initial parsing
            if (data!=null) { // Makes Sure data isn't null
                String SRCT = data.get("SRCT"); // Gets Source Type
                if (SRCT.equals("OneFile")) OneFile.OF(data); // Run's Appropriate Method for OneFile
                if (SRCT.equals("MultiFile")) MultiFile.MF(data); // Run's Appropriate Method for MultiFile
                gitUpdate(data); // Updates Git Repo
                CustCmds.runCmds(); // Run's custom commands
            }
        }
}
