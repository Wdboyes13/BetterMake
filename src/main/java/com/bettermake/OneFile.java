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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// ========================================
// === ONEFILE PROJECT SPECIFIC METHODS ===
// ========================================

public class OneFile {
    // =====================================================
    // === RUNNING COMPILER & LINKER - ONEFILE PROJECT ===
    // =====================================================
    public static void OFrunCC(String CC, String SRC, String OUTF, String FLAGS){
        try {
            List<String> cmdList = new ArrayList<>(List.of(CC.split(" ")));
            cmdList.add(SRC);
            cmdList.add("-o");
            cmdList.add(OUTF);
            if (!FLAGS.isBlank()) {
                cmdList.addAll(List.of(FLAGS.trim().split("\\s+")));
            }
            System.out.println("Running: " + String.join(" ", cmdList));
            ProcessBuilder CCPB = new ProcessBuilder(cmdList);
            Process CCProc = CCPB.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(CCProc.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println("[ERROR] " + line);
                }
            }
            CCProc.waitFor();

            System.out.println("CC: " + CC + " PID: " + CCProc.pid() + " EXIT CODE: " + CCProc.exitValue());
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
    // ========================================
    // === MULTITHREADING - ONEFILE PROJECT ===
    // ========================================
    public static void OF(HashMap<String, String> data) throws IOException, InterruptedException{
        Thread LINARM = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("LINARMCC")!=null && !data.get("LINARMCC").isEmpty()) OFrunCC(
                    data.get("LINARMCC"),
                    data.get("SRCF"),
                    "rls/linARM/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("LINARM ran for " + durationSeconds + " Seconds");
        });
        Thread LIN = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("LIN64CC")!=null && !data.get("LIN64CC").isEmpty()) OFrunCC(
                    data.get("LIN64CC"),
                    data.get("SRCF"),
                    "rls/lin/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("LIN64 ran for " + durationSeconds + " Seconds");
        });
        Thread MACARM = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("MACARMCC")!=null && !data.get("MACARMCC").isEmpty()) OFrunCC(
                    data.get("MACARMCC"),
                    data.get("SRCF"), "rls/macARM/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("MACARM ran for " + durationSeconds + " Seconds");
        });
        Thread MAC = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("MAC64CC")!=null && !data.get("MAC64CC").isEmpty())OFrunCC(
                    data.get("MAC64CC"),
                    data.get("SRCF"),
                    "rls/mac/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("MAC64 ran for " + durationSeconds + " Seconds");
        });
        Thread WIN = new Thread(() ->{

            long startTime = System.nanoTime();
            if (data.get("WIN64CC")!=null && !data.get("WIN64CC").isEmpty())OFrunCC(
                    data.get("WIN64CC"),
                    data.get("SRCF"),
                    "rls/win/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("WIN64 ran for " + durationSeconds + " Seconds");
        });
        Thread WINARM = new Thread(() ->{
            long startTime = System.nanoTime();
            if (data.get("WINARMCC")!=null && !data.get("WINARMCC").isEmpty())OFrunCC(
                    data.get("WINARMCC"),
                    data.get("SRCF"),
                    "rls/winARM/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
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
