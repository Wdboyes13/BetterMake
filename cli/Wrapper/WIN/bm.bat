@echo off
setlocal enabledelayedexpansion

rem Function substitute - batch doesn't have real functions, so use labels and call
:run
java -jar "%USERPROFILE%\.bm\bm-cli.jar"
goto :eof

rem Check if bm-cli.jar exists
if exist "%USERPROFILE%\.bm\bm-cli.jar" (
    echo bm.jar exists
    call :run
) else (
    echo bm.jar doesn't exist
    mkdir "%USERPROFILE%\.bm" 2>nul

    rem Save current directory
    set "curr=%cd%"
    cd /d "%USERPROFILE%\.bm"

    rem Use curl to download if available, else fallback to bitsadmin (old way)
    curl -O https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/cli/cli/target/bm-cli.jar 2>nul
    if errorlevel 1 (
        echo curl failed, trying bitsadmin...
        bitsadmin /transfer downloadJob /download /priority normal https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/cli/cli/target/bm-cli.jar bm-cli.jar
    )

    cd /d "%curr%"
    call :run
)