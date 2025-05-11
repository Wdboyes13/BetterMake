# Style Guide
  
## If your code is NOT READABLE or does not follow this, it will NOT be added in 
  
## [MarkDown Styles](#markdown)
## [Java Styles](#java)
## [SH Styles](#shell)
  
## MarkDown 
Try to keep things using only
1. `[]()`
2. `##`
3. `#'
4. `###`
5. Backtick ` Code Blocks
6. Triple Backtick ``` Code Blocks (MUST Include language tag where possible)

## Java
  
### General
  
Please do not use packages not part of Java 21  
  
At the top of your Method/File include a block showing what it does with
```java
// =====================================
// === Short Description (1-5 Words) ===
// =====================================
```
  
If there is something that needs to be done, include a notice at the top of your file
```java
// TODO: Write what needs to be done
```
  
GENERAL FILE STRUCTURE
```
imports

TODO's


File Explanation

public class yourfile {

}
```

## Shell
Keep it POSIX Compliant, try not to install anything, and dont include OS Specific Stuff  
Windows is excluded from this