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

// Java SE Libraries (IO, Lists)
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// ==========================================
// === MULTIFILE PROJECT SPECIFIC METHODS ===
// ==========================================

public class MultiFile {
    // ===================================
    // === MULTIFILE PROJECT COMPILING ===
    // ===================================
    public static List<String> compileCFiles(List<Path> cFiles, String buildDir, String CC, String flags) throws IOException, InterruptedException {
        List<String> objectFiles = new ArrayList<>();
        Files.createDirectories(Paths.get(buildDir));

        for (Path cFile : cFiles) {
            String cFileName = cFile.getFileName().toString();
            String objName = cFileName.replace(".c", ".o");
            Path objPath = Paths.get(buildDir, objName);

            List<String> cmd = new ArrayList<>(List.of(CC.split(" ")));
            cmd.add("-c");
            cmd.add(cFile.toString());
            cmd.add("-o");
            cmd.add(objPath.toString());
            if (!flags.isBlank()) {
                cmd.addAll(List.of(flags.trim().split("\\s+")));
            }
            System.out.println("Compiling: " + String.join(" ", cmd));
            Process p = new ProcessBuilder(cmd).inheritIO().start();
            int exit = p.waitFor();
            if (exit != 0) throw new RuntimeException("Compilation failed for: " + cFile);
            objectFiles.add(objPath.toString());
        }
        return objectFiles;
    }
    // =================================
    // === MULTIFILE PROJECT LINKING ===
    // =================================
    public static void linkObjects(List<String> objectFiles, String outputBinary, String CC, String flags) throws IOException, InterruptedException {
        List<String> cmd = new ArrayList<>(List.of(CC.split(" ")));
        cmd.addAll(objectFiles);
        cmd.add("-o");
        cmd.add(outputBinary);
        if (!flags.isBlank()) {
            cmd.addAll(List.of(flags.trim().split("\\s+")));
        }

        System.out.println("Linking: " + String.join(" ", cmd));
        Process p = new ProcessBuilder(cmd).inheritIO().start();
        int exit = p.waitFor();
        if (exit != 0) throw new RuntimeException("Linking failed.");
    }
    // =====================================================
    // === RUNNING COMPILER & LINKER - MULTIFILE PROJECT ===
    // =====================================================
    public static void MFrunCC(String CC, String SRCDir, String OUTF, String PLATFORM, String GLOBFLAGSL, String GLOBFLAGS, String LANG){
        try {
            List<Path> cFiles = BetterMake.findCFiles(SRCDir, LANG);
            List<String> objectFiles = compileCFiles(cFiles, "build/"+PLATFORM, CC, GLOBFLAGS);
            linkObjects(objectFiles, OUTF, CC, GLOBFLAGSL);

            System.out.println("Build complete! Output: " + OUTF);
        } catch (IOException | InterruptedException e){
            System.out.println("Build Failed");
            e.printStackTrace();
        }
    }
    // ==========================================
    // === MULTITHREADING - MULTIFILE PROJECT ===
    // ==========================================
    public static void MF(HashMap<String, String> data) throws IOException, InterruptedException{
        Thread LINARM = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("LINARMCC")!=null && !data.get("LINARMCC").isEmpty()) MFrunCC(
                    data.get("LINARMCC"),
                    data.get("SRCF"),
                    "rls/linARM/"+data.get("OUTF"),
                    "LINARM",
                    data.get("GLOBFLAGSL"),
                    data.get("GLOBFLAGS"), data.get("LANG"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("LINARM ran for " + durationSeconds + " Seconds");
        });
        Thread LIN = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("LIN64CC")!=null && !data.get("LIN64CC").isEmpty()) MFrunCC(
                    data.get("LIN64CC"), data.get("SRCF"),
                    "rls/lin/"+data.get("OUTF"),
                    "LIN64",
                    data.get("GLOBFLAGSL"),
                    data.get("GLOBFLAGS"),
                    data.get("LANG"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("LIN64 ran for " + durationSeconds + " Seconds");
        });
        Thread MACARM = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("MACARMCC")!=null && !data.get("MACARMCC").isEmpty()) MFrunCC(
                    data.get("MACARMCC"),
                    data.get("SRCF"),
                    "rls/macARM/"+data.get("OUTF"),
                    "MACARM",
                    data.get("GLOBFLAGSL"),
                    data.get("GLOBFLAGS"),
                    data.get("LANG"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("MACARM ran for " + durationSeconds + " Seconds");
        });
        Thread MAC = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("MAC64CC")!=null && !data.get("MAC64CC").isEmpty())MFrunCC(
                    data.get("MAC64CC"),
                    data.get("SRCF"),
                    "rls/mac/"+data.get("OUTF"),
                    "MAC64",
                    data.get("GLOBFLAGSL"),
                    data.get("GLOBFLAGS"),
                    data.get("LANG"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("MAC64 ran for " + durationSeconds + " Seconds");
        });
        Thread WIN = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("WIN64CC")!=null && !data.get("WIN64CC").isEmpty())MFrunCC(
                    data.get("WIN64CC"),
                    data.get("SRCF"),
                    "rls/win/"+data.get("OUTF"),
                    "WIN64",
                    data.get("GLOBFLAGSL"),
                    data.get("GLOBFLAGS"),
                    data.get("LANG"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("WIN64 ran for " + durationSeconds + " Seconds");
        });
        Thread WINARM = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("WINARMCC")!=null && !data.get("WINARMCC").isEmpty())MFrunCC(
                    data.get("WINARMCC"),
                    data.get("SRCF"),
                    "rls/winARM/"+data.get("OUTF"),
                    "WINARM", data.get("GLOBFLAGSL"),
                    data.get("GLOBFLAGS"),
                    data.get("LANG"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("WINARM ran for " + durationSeconds + " Seconds");
        });
        LINARM.start();
        LIN.start();
        MACARM.start();
        MAC.start();
        WIN.start();
        WINARM.start();

        LINARM.join();
        LIN.join();
        MACARM.join();
        MAC.join();
        WIN.join();
        WINARM.join();
    }
}
