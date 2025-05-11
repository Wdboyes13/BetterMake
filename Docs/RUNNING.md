# To run
## To see info on Project Profiles go to [PROFILES.md](PROFILES.md)
To make a new MultiFile Project  (May request a proxy, hit enter, ignore error)
Replace <Lang> with OBJC, OBJCPP, C, or CPP
Replace <project-name> With your project name (Name of project folder & final binary)
Replace <file.c> with what you want your first file to be named
```sh
curl --noproxy "*" -fsSL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF-ProjC.sh | bash -s <project-name> <file.c> MF <Lang> <OPTIONAL: profile-name>
```  

To make a new SingleFile Project  (May request a proxy, hit enter, ignore error)
Replace <Lang> with OBJC, OBJCPP, C, or CPP
Replace <project-name> With your project name (Name of project folder & final binary)
Replace <file.c> with what you want your source file to be named
```sh
curl --noproxy "*" -fsSL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF-ProjC.sh | bash -s <project-name> <file.c> OF <OPTIONAL: profile-name>
```  

Download: [BetterMake.jar](https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/bettermake/target/BetterMake.jar)

To start build  
`java -jar BetterMake.jar`  
