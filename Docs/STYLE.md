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
4. `###` (Avoid - Because looks too close to ##)
5. Backtick ` Code Blocks
6. Triple Backtick ``` Code Blocks (MUST Include language tag where possible)

## Java
  
### General
  
Please do not use packages not part of Java 21  
  
Don't forget to add the [GNU GPL File Header](HEADER.MD)  
  
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
  
Please add a description about what set your imports and from, and their general purpose.  
Examples:  
```java
// Java SE Libraries (IO, Network, Regex, Lists)
// JAVA EE XML Libraries (Schema and File parsing)
// W3C DOM Parsing Libraries
```  
  
GENERAL FILE STRUCTURE
```
imports

TODO's


File Explanation

public class yourfile {

}
```
  
### XML Tag Addition
Please add your XML Tag to the Schema (BMF.xsd)  
Please verify that the Tag gets recognized  
Please define what your tag is in the Return Map for XMLParser.parse() at the end of XMLParser  
  
## Shell
Keep it Compatible with POSIX Compliant OSes  
Do NOT to install anything   
Do NOT include OS Specific Stuff (Like almost anything windows)  
Windows specific stuff MUST NOT be used, this includes PowerShell/Command Prompt  