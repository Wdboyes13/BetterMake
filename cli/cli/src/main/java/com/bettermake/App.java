package com.bettermake;

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

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import com.bettermake.excepts.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
public class App {
    public static void main(String[] args) throws Exception{
        System.out.println("Hey! Welcome to the BetterMake CLI");
        System.out.print("Enter 1 to generate project\n>> ");
        Scanner scanner = new Scanner(System.in);
        String inp = scanner.nextLine();
        if (inp.equals("1")){

            System.out.print("Enter Project Name: ");
            String name = scanner.nextLine();

            Files.createDirectory(Paths.get(name));

            InputStream in = new URL("https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/target/BetterMake.jar").openStream();
            Path output = Paths.get(name+"/BetterMake.jar");
            Files.copy(in, output, StandardCopyOption.REPLACE_EXISTING);
            System.out.print("Supported Language Options: C CPP OBJC OBJCPP\nEnter Lang: ");
            String lang = scanner.nextLine();

            if (lang.equals("C") || lang.equals("CPP") || lang.equals("OBJC") || lang.equals("OBJCPP")){
                System.out.print("Project Type: OF / MF\nEnter Project Type: ");
                String prjType = scanner.nextLine();
                if (prjType.equals("OF") || prjType.equals("MF")){
                    if (prjType.equals("OF")) OFgen.main(args, name, lang);
                    if (prjType.equals("MF")) MFgen.main(args, name, lang);
                } else {
                    throw new InvalidProjectTypeException();
                }
            } else {
                throw new InvalidLangException();
            }
        }
    }
}
