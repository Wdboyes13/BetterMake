# Config Files (Requires BASH)
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
  
