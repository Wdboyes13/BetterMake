# Project Basics  (Creation & Building)   
> [!TIP]  
> To see info on Project Profiles go to [PROFILES.md](PROFILES.md)  
  
## To make a new Project Interactively
> [!IMPORTANT]  
> Requirements  
> [bash](https://www.gnu.org/software/bash/)  
> [Curl](https://curl.se/)  
> [GNU Coreutils](https://www.gnu.org/software/coreutils/)  
  
Run 
```sh
bash <(curl -fsSL https://tinyurl.com/BMAutoConf) -i
```

## To make a new MultiFile Project  
> [!WARNING]    
> May request a proxy, hit enter, ignore error    
  
> [!IMPORTANT]  
> Requirements  
> [bash](https://www.gnu.org/software/bash/)  
> [Curl](https://curl.se/)  
> [GNU Coreutils](https://www.gnu.org/software/coreutils/)  
  
Replace <Lang> with OBJC, OBJCPP, C, or CPP
Replace <project-name> With your project name (Name of project folder & final binary)
Replace <file.c> with what you want your first file to be named
```sh
bash <(curl -fsSL https://tinyurl.com/BMAutoConf) <project-name> <file.c> MF <Lang> <OPTIONAL: profile-name>
```  

## To make a new SingleFile Project  
> [!WARNING]  
> May request a proxy, hit enter, ignore error   
  
> [!IMPORTANT]  
> Requirements  
> [bash](https://www.gnu.org/software/bash/)   
> [Curl](https://curl.se/)  
> [GNU Coreutils](https://www.gnu.org/software/coreutils/)  
  
Replace <Lang> with OBJC, OBJCPP, C, or CPP
Replace <project-name> With your project name (Name of project folder & final binary)
Replace <file.c> with what you want your source file to be named
```sh
bash <(curl -fsSL https://tinyurl.com/BMAutoConf) <project-name> <file.c> OF <Lang> <OPTIONAL: profile-name>
```    
## To start build  
`java -jar BetterMake.jar`  

## To Update
> [!WARNING]  
> May request a proxy, hit enter, ignore error
   
> [!IMPORTANT]  
> Requirements  
> [Curl](https://curl.se/)  
> [GNU Coreutils](https://www.gnu.org/software/coreutils/)  
In your project folder  
```sh
rm BetterMake.jar && curl -sL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/target/BetterMake.jar
```
  
__Download: [BetterMake.jar](https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/target/BetterMake.jar)__ 
  
## VS Code Extension
> [!WARNING]  
> May request a proxy, hit enter, ignore error
  
> [!IMPORTANT]  
> Requirements  
> [bash](https://www.gnu.org/software/bash/)  
> [Curl](https://curl.se/)  
> [GNU Coreutils](https://www.gnu.org/software/coreutils/)  
In the top-right of the VS Code Extensions Menu, click the 3 Dots, and Click "Install from VSIX" and select the File  
__[bettermake-1.0.0.vsix](https://github.com/Wdboyes13/BetterMake/raw/refs/heads/main/vsix/bettermake/bettermake-1.0.0.vsix)__
