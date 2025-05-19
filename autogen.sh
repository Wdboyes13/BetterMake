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

PROJ_NAME="$1"
SOURCE="$2"
PROJ_TYPE="$3"
LANG="$4"

if [ -n "$5" ]; then
    PROF="$5"
    if [ -f "$HOME/.bmf-profs/$PROF" ]; then
        source "$HOME/.bmf-profs/$PROF"
    else
        echo "Profile $PROF not found. Using default settings."
    fi
fi

MF() {
    local proj_name="$1"
    local source_dir="src"
    local lang="$3"
    local source_file="$2"
    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    mkdir -p build/{LINARM,LIN64,MAC64,MACARM,WIN64,WINARM}
    mkdir -p "$source_dir"
    touch "$source_dir/$source_file" # or just keep touch of one main file

    cat <<EOF > mk.xml
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
    <FILE>$proj_name</FILE>
    <SRC>
        <LANG>$lang</LANG>
        <Type>MF</Type>
        <FILE>$source_dir</FILE>
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
    local proj_name="$1"
    local source_file="$2"
    local lang="$3"

    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    touch "$source_file"

    cat <<EOF > mk.xml
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
    <FILE>$proj_name</FILE>
    <SRC>
        <LANG>$lang</LANG>
        <Type>OF</Type>
        <FILE>$source_file</FILE>
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
    bash <(curl -fsSL https://tinyurl.com/BMAutoConf) "$PROJ_NAME" main.c OF "$LANG"
  elif [ "$PROJ_TYPE" == "MF" ]; then
    bash <(curl -fsSL https://tinyurl.com/BMAutoConf) "$PROJ_NAME" main.c MF "$LANG"
  fi

  echo "Project '$PROJ_NAME' ready"
}

# Main entry point
if [ "$1" == "-i" ]; then
    interactive
    exit 0
fi

if [ -z "$1" ]; then
    echo "Usage:"
    echo "  $0 -i                # interactive mode"
    echo "  $0 <project-name> <source-file> <project-type> <lang> [profile-name]"
    exit 1
fi

if [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ]; then
    echo "Usage: $0 <project-name> <source-file> <project-type> <lang> [profile-name]"
    exit 1
fi

mkdir -p "$PROJ_NAME"
cd "$PROJ_NAME" || exit 1
curl -sL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/target/BetterMake.jar -o BetterMake.jar

if [ "$PROJ_TYPE" = "MF" ]; then
    MF "$PROJ_NAME" "$SOURCE" "$LANG"
elif [ "$PROJ_TYPE" = "OF" ]; then
    OF "$PROJ_NAME" "$SOURCE" "$LANG"
else
    echo "Unknown project type: $PROJ_TYPE (expected MF or OF)"
    exit 2
fi

echo "Project '$PROJ_NAME' setup complete!"