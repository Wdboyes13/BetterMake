package com.bettermake;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
public class OFgen {
    public static void main(String[] args, String prjName, String lang) throws IOException {
        Files.createDirectory(Paths.get(prjName+"/rls"));
        // Create rls dirs
        {
            Files.createDirectory(Paths.get(prjName+"/rls/lin"));
            Files.createDirectory(Paths.get(prjName+"/rls/linARM"));
            Files.createDirectory(Paths.get(prjName+"/rls/mac"));
            Files.createDirectory(Paths.get(prjName+"/rls/macARM"));
            Files.createDirectory(Paths.get(prjName+"/rls/win"));
            Files.createDirectory(Paths.get(prjName+"/rls/winARM"));

        }
        File mkxml = new File(prjName+"/mk.xml");
        mkxml.createNewFile();
        FileOutputStream fos = new FileOutputStream(mkxml);
        String src = new String();
        if (lang.equals("C")) {
            src = "main.c";
            File srcF = new File(prjName+"/"+src);
            FileOutputStream srcW = new FileOutputStream(srcF);
            srcW.write("""
                    #include <stdio.h>
                    int main(){
                        printf("Hello, world!");
                        return 0;
                    }
                    """.getBytes());
        }
        if (lang.equals("CPP")) {
            src = "main.cpp";
            File srcF = new File(prjName+"/"+src);
            FileOutputStream srcW = new FileOutputStream(srcF);
            srcW.write("""
                    #include <iostream>
                    int main(){
                        std::cout << "Hello, world!" << std::endl;
                        return 0;
                    }
                    """.getBytes());
        }
        if (lang.equals("OBJC")) {
            src = "main.m";
            File srcF = new File(prjName+"/"+src);
            FileOutputStream srcW = new FileOutputStream(srcF);
            srcW.write("""
                    #import <Foundation/Foundation.h>
                    
                    int main(int argc, const char * argv[]) {
                        @autoreleasepool {
                            NSLog(@"Hello, World!");
                        }
                        return 0;
                    }
                    """.getBytes());
        }
        if (lang.equals("OBJCPP")) {
            src = "main.mm";
            File srcF = new File(prjName+"/"+src);
            FileOutputStream srcW = new FileOutputStream(srcF);
            srcW.write("""
                    #import <Foundation/Foundation.h>
                    #include <iostream>
                    int main(int argc, const char * argv[]) {
                        @autoreleasepool {
                            NSLog(@"Hello, World! (OBJC)");
                            std::cout << "Hello, world! (CPP)" << std::endl;
                        }
                        return 0;
                    }
                    """.getBytes());
        }
        fos.write(String.format("""
                <?xml version="1.0" encoding="UTF-8"?>
                <BMF xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:noNamespaceSchemaLocation="https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF.xsd">
                    <Compilers>
                        <Mac>
                            <ARM></ARM> <!-- macOS AArch64 Compiler -->
                            <SF></SF> <!-- macOS x86_64 Compiler -->
                        </Mac>
                        <Linux>
                            <ARM></ARM> <!-- Linux AArch64 Compiler -->
                            <SF></SF> <!-- Linux x86_64 Compiler -->
                        </Linux>
                        <Win>
                            <ARM></ARM> <!-- Windows AArch64 Compiler -->
                            <SF></SF> <!-- Windows x86_64 Compiler -->
                        </Win>
                        <flags>
                            <globalLink></globalLink> <!-- Global Linker Flags -->
                            <globalComp></globalComp> <!-- Global Compiler Flags -->
                        </flags>
                    </Compilers>
                    <FILE>%s</FILE> <!-- Executable Output Filename (By default, the folder name)-->
                    <SRC>
                        <LANG>%s</LANG> <!-- The project language (C, CPP, OBJC, OBJCPP)-->
                        <Type>MultiFile</Type> <!-- The project type, MultiFile or OneFile -->
                        <FILE>%s</FILE> <!-- The Source Directory or Source File -->
                    </SRC>
                    <GIT> <!-- Git Config (Requires BASH & GIT) -->
                        <REPO></REPO> <!-- Git Repository URL -->
                        <COMMSG></COMMSG> <!-- Git Commit Message -->
                    </GIT>
                    <CMDS>
                        <CMD></CMD> <!-- Your Custom Command (Add as many of these tags as you want) -->
                    </CMDS>
                </BMF>
                """, prjName, lang, src).getBytes());
        fos.close();
    }
}
