#!/bin/bash

# BetterMake - A Build Tool for C, C++, ObjC, ObjC++
# Copyright (C) 2025  Wdboyes13
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see https://www.gnu.org/licenses/.

#TODO: Make script interactive

if [ -z "$1" ]; then
    echo "Usage: $0 <project-name> <source-file.c / source-dir> <project-type> <Lang> <profile-name> [-i for interactive]"
    exit 1
fi

if [ "$1" == "-i" ]; then
    interactive
    exit 0
fi

# Continue with non-interactive mode
if [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ] || [ -z "$5" ]; then
    echo "Usage: $0 <project-name> <source-file.c / source-dir> <project-type> <Lang> <profile-name>"
    exit 1
fi

PROJ_NAME="$1"
SOURCE="$2"
PROJ_TYPE="$3"
LANG="$4"
curl -sU
mkdir "$1"
cd "$1" || exit 1
curl -sL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/target/BetterMake.jar -o BetterMake.jar
if [ -n "$5" ]; then
    PROF="$5"
    if [ -f "$HOME/.bmf-profs/$PROF" ]; then
        source "$HOME/.bmf-profs/$PROF"
    else
        echo "Profile $PROF not found. Using default settings."
    fi
fi
interactive() {
  echo -n "(No Space String) Enter project name: "
  read PROJ_NAME
  echo -n "(OF or MF) Enter Project type: "
  read PROJ_TYPE
  echo -n "(C CPP OBJC OBJCPP) Enter Project Language: "
  read LANG

  # Validate input
  if [[ "$PROJ_TYPE" != "OF" && "$PROJ_TYPE" != "MF" ]]; then
    echo "Invalid project type. Must be OF or MF."
    return 1
  fi

  if [[ "$LANG" != "C" && "$LANG" != "CPP" && "$LANG" != "OBJC" && "$LANG" != "OBJCPP" ]]; then
    echo "Invalid language. Must be C, CPP, OBJC, or OBJCPP."
    return 1
  fi

  if [ "$PROJ_TYPE" == "OF" ]; then
    echo -n "(Filename) Enter Source FileName: "
    read SOURCE
    OF "$PROJ_NAME" "$SOURCE" "$LANG"
  elif [ "$PROJ_TYPE" == "MF" ]; then
    echo -n "(Directory) Enter Source Directory: "
    read SOURCE_DIR
    MF "$PROJ_NAME" "$SOURCE_DIR" "$LANG"
  fi
}
MF() {
    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    mkdir -p build/{LINARM,LIN64,MAC64,MACARM,WIN64,WINARM}
    mkdir src
    touch src/$SOURCE
    cat <<EOF >> mk.xml
<?xml version="1.0" encoding="UTF-8"?>
<BMF xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:noNamespaceSchemaLocation="https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF.xsd">
    <Compilers>
        <Mac>
            <ARM>$MACARMCC</ARM>
            <SF>$MAC64CC</SF>
        </Mac>
        <Linux>
            <ARM>$LINARMCC</ARM>
            <SF>$LIN64CC</SF>
        </Linux>
        <Win>
            <ARM>$WINARMCC</ARM>
            <SF>$WIN64CC</SF>
        </Win>
        <flags>
            <globalLink>$LINKFLAGS</globalLink>
            <globalComp>$COMPFLAGS</globalComp>
        </flags>
    </Compilers>
    <FILE>$PROJ_NAME</FILE>
    <SRC>
        <LANG>$LANG</LANG>
        <Type>MultiFile</Type>
        <FILE>src</FILE>
    </SRC>
    <GIT>
        <REPO></REPO>
        <COMMSG></COMMSG>
    </GIT>
    <CMDS>
        <CMD></CMD>
    </CMDS>
</BMF>
EOF
}

OF() {
    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    touch "$SOURCE"

    cat <<EOF >> mk.xml
<?xml version="1.0" encoding="UTF-8"?>
<BMF xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:noNamespaceSchemaLocation="https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/BMF.xsd">
    <Compilers>
        <Mac>
            <ARM>$MACARMCC</ARM>
            <SF>$MAC64CC</SF>
        </Mac>
        <Linux>
            <ARM>$LINARMCC</ARM>
            <SF>$LIN64CC</SF>
        </Linux>
        <Win>
            <ARM>$WINARMCC</ARM>
            <SF>$WIN64CC</SF>
        </Win>
        <flags>
            <globalLink>$LINKFLAGS</globalLink>
            <globalComp>$COMPFLAGS</globalComp>
        </flags>
    </Compilers>
    <FILE>$PROJ_NAME</FILE>
    <SRC>
        <LANG>$LANG</LANG>
        <Type>OneFile</Type>
        <FILE>$SOURCE</FILE>
    </SRC>
    <GIT>
        <REPO></REPO>
        <COMMSG></COMMSG>
    </GIT>
    <CMDS>
        <CMD></CMD>
    </CMDS>
</BMF>
EOF
}

# Call correct function
if [ "$PROJ_TYPE" = "MF" ]; then
    MF
elif [ "$PROJ_TYPE" = "OF" ]; then
    OF
else
    echo "Unknown project type: $PROJ_TYPE (expected MF or OF)"
    exit 2
fi
