# BetterMake  
### A Build tool for C projects, Has Multi-Threading, Cross-Compilation, Git Integration, And is configure in XML
### Have something you'd like to see added, make an issue! or email [willdev2025@outlook.com](mailto:willdev2025@outlook.com)
## Required
> [java](https://adoptium.net/temurin/releases/?package=jdk&version=21)  
> [git (if using git)](https://git-scm.com/downloads)  
> [bash (if using git or project generator)](https://www.gnu.org/software/bash/)
  
## To run
To make a new MultiFile Project  (May request a proxy, hit enter)
```sh
curl --noproxy "*" -fsSL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF-ProjC.sh) | bash -s <project-name> x MF <OPTIONAL: profile-name>
```  
  
To make a new SingleFile Project  (May request a proxy, hit enter)
```sh
curl --noproxy "*" -fsSL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF-ProjC.sh) | bash -s <project-name> <file.c> OF <OPTIONAL: profile-name>
```  
  
To start build  
`java BetterMake.java`  
  
## Config Files  
Configuration files are stored in `~/.bmf-profs`  
To make a configuration folder, `mkdir ~/.bmf-profs`
  
Configuration Files can be named anything, When specifying your profile in project gen, use the full filname
Configuration file template
```sh
export MACARMCC='clang' # macOS AArch64 C Compiler
export MAC64CC='arch -x86_64 gcc' # macOS x86_64 C Compiler
export LINARMCC='aarch64-unknown-linux-gnu-gcc' # Linux AArch64 C Compiler
export LIN64CC='x86_64-unknown-linux-gnu-gcc' # Linux x86_64 C Compiler
export WIN64CC='x86_64-w64-mingw32-gcc' # Windows 64 C Compiler
export WINARMCC='' # Windows AArch64 C Compiler (Left blank since i couldn't find one)
```
  
## mk.xml
Here is an example of an `mk.xml`, All fields are required to be present, but dont have to be used
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
        <Type>MultiFile</Type> <!-- The project type, MultiFile or OneFile -->
        <FILE>src</FILE> <!-- The Source Directory or Source File -->
    </SRC>
    <GIT>
        <REPO></REPO> <!-- Git Commit Message -->
        <COM-MSG></COM-MSG> <!-- Git Commit Message -->
    </GIT>
</BMF>
```
