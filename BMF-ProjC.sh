#!/bin/bash
# Check arguments first!
if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ]; then
    echo "Usage: $0 <project-name> <source-file.c / source-dir> <project-type> <profile-name>"
    exit 1
fi
PROJ_NAME="$1"
SOURCE="$2"
PROJ_TYPE="$3"
curl -sU
mkdir "$1"
cd "$1" || exit 1
curl -sL https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/bettermake/target/BetterMake.jar -o BetterMake.jar
if [ -n "$4" ]; then
    PROF="$4"
    if [ -f "$HOME/.bmf-profs/$PROF" ]; then
        source $HOME/.bmf-profs/$PROF
    else
        echo "Profile $PROF not found. Using default settings."
    fi
fi

MF() {
    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    mkdir -p build/{LINARM,LIN64,MAC64,MACARM,WIN64,WINARM}
    mkdir src

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
            <globalLink></globalLink>
            <globalComp></globalComp>
        </flags>
    </Compilers>
    <FILE>$PROJ_NAME</FILE>
    <SRC>
        <LANG>C</LANG>
        <Type>MultiFile</Type>
        <FILE>src</FILE>
    </SRC>
    <GIT>
        <REPO></REPO>
        <COMMSG></COMMSG>
    </GIT>
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
            <globalLink></globalLink>
            <globalComp></globalComp>
        </flags>
    </Compilers>
    <FILE>$PROJ_NAME</FILE>
    <SRC>
        <LANG>C</LANG>
        <Type>OneFile</Type>
        <FILE>$SOURCE</FILE>
    </SRC>
    <GIT>
        <REPO></REPO>
        <COMMSG></COMMSG>
    </GIT>
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
