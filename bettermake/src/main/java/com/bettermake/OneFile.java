package com.bettermake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Add Custom Compiler Flags
// TODO: Add Custom Linker Flags


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
            if (data.get("LINARMCC")!=null && !data.get("LINARMCC").isEmpty()) OFrunCC(
                    data.get("LINARMCC"),
                    data.get("SRCF"),
                    "rls/linARM/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
        });
        Thread LIN = new Thread(() ->{
            if (data.get("LIN64CC")!=null && !data.get("LIN64CC").isEmpty()) OFrunCC(
                    data.get("LIN64CC"),
                    data.get("SRCF"),
                    "rls/lin/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
        });
        Thread MACARM = new Thread(() ->{
            if (data.get("MACARMCC")!=null && !data.get("MACARMCC").isEmpty()) OFrunCC(
                    data.get("MACARMCC"),
                    data.get("SRCF"), "rls/macARM/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
        });
        Thread MAC = new Thread(() ->{
            if (data.get("MAC64CC")!=null && !data.get("MAC64CC").isEmpty())OFrunCC(
                    data.get("MAC64CC"),
                    data.get("SRCF"),
                    "rls/mac/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
        });
        Thread WIN = new Thread(() ->{
            if (data.get("WIN64CC")!=null && !data.get("WIN64CC").isEmpty())OFrunCC(
                    data.get("WIN64CC"),
                    data.get("SRCF"),
                    "rls/win/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
        });
        Thread WINARM = new Thread(() ->{
            if (data.get("WINARMCC")!=null && !data.get("WINARMCC").isEmpty())OFrunCC(
                    data.get("WINARMCC"),
                    data.get("SRCF"),
                    "rls/winARM/"+data.get("OUTF"),
                    data.get("GLOBFLAGSL") + " " + data.get("GLOBFLAGS"));
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
