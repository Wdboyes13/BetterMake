# mk.xml
### Here is an example of an `mk.xml`, All fields are required to be present, but dont have to be used
```xml
<?xml version="1.0" encoding="UTF-8"?>
<BMF xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:noNamespaceSchemaLocation="https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF.xsd">
    <Compilers>
        <Mac>
            <ARM>clang</ARM> <!-- macOS AArch64 C Compiler -->
            <SF>arch -x86_64 gcc</SF> <!-- macOS x86_64 C Compiler -->
        </Mac>
        <Linux>
            <ARM>aarch64-unknown-linux-gnu-gcc</ARM> <!-- Linux AArch64 C Compiler -->
            <SF>x86_64-unknown-linux-gnu-gcc</SF> <!-- Linux x86_64 C Compiler -->
        </Linux>
        <Win>
            <ARM></ARM> <!-- Windows AArch64 C Compiler -->
            <SF>x86_64-w64-mingw32-gcc</SF> <!-- Windows x86_64 C Compiler -->
        </Win>
        <flags>
            <globalLink></globalLink> <!-- Global Linker Flags -->
            <globalComp></globalComp> <!-- Global Compiler Flags -->
        </flags>
    </Compilers>
    <FILE>MathProj</FILE> <!-- Executable Output Filename (By default, the folder name)-->
    <SRC>
        <LANG>C</LANG> <!-- The project language (C, CPP, OBJC, OBJCPP)-->
        <Type>MultiFile</Type> <!-- The project type, MultiFile or OneFile -->
        <FILE>src</FILE> <!-- The Source Directory or Source File -->
    </SRC>
    <GIT> <!-- Git Config (Requires BASH & GIT) -->
        <REPO></REPO> <!-- Git Repository URL -->
        <COMMSG></COMMSG> <!-- Git Commit Message -->
    </GIT>
    <CMDS>
        <CMD></CMD> <!-- Your Custom Command (Add as many of these tags as you want) -->
    </CMDS>
</BMF>
```
