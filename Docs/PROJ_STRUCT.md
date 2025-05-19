# Project Structure (ALL Files/Dirs are REQUIRED)  
## SingleFile Project  
```
ROOT  
    |- mk.xml (Your Project Config)    
    |- main.c (Source File)    
    |- rls/ (Your projects binaries)  
        |- lin/ (Linux x86_64)  
        |- linARM/ (Linux AArch64)  
        |- mac/ (macOS x86_64)  
        |- macARM/ (macOS AArch64)  
        |- win/ (Windows x86_64)  
        |- winARM/ (Windows AArch64)  
```
## MultiFile Project 
```
ROOT    
    |- mk.xml (Your Project Config)     
    |- src/ (Source Folder)      
    |- rls/ (Your projects binaries)     
        |- lin/ (Linux x86_64)    
        |- linARM/ (Linux AArch64)   
        |- mac/ (macOS x86_64)   
        |- macARM/ (macOS AArch64)   
        |- win/ (Windows x86_64)   
        |- winARM/ (Windows AArch64)   
    |- build/ (Object files, from building)  
        |- LIN64/   
        |- LINARM/   
        |- MAC64/   
        |- MACARM/   
        |- WIN64/   
        |- WINARM/    
```