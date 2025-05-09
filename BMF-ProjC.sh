#!/bin/bash
# Check arguments first!
if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ]; then
    echo "Usage: $0 <project-name> <source-file.c / source-dir> <project-type>"
    exit 1
fi
PROJ_NAME="$1"
SOURCE="$2"
PROJ_TYPE="$3"
mkdir "$1"
cd "$1" || exit 1

MF() {
    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    mkdir src build

    cat <<EOF >> mk.xml
<BMF>
    <Compilers>
        <Mac>
            <ARM></ARM>
            <SF></SF>
        </Mac>
        <Linux>
            <ARM></ARM>
            <SF></SF>
        </Linux>
        <Win>
            <ARM></ARM>
            <SF></SF>
        </Win>
    </Compilers>
    <FILE>$PROJ_NAME</FILE>
    <SRC>
        <Type>MultiFile</Type>
        <FILE>src</FILE>
</BMF>
EOF
}

OF() {
    mkdir -p rls/{lin,linARM,mac,macARM,win,winARM}
    touch "$SOURCE"

    cat <<EOF >> mk.xml
<BMF>
    <Compilers>
        <Mac>
            <ARM></ARM>
            <SF></SF>
        </Mac>
        <Linux>
            <ARM></ARM>
            <SF></SF>
        </Linux>
        <Win>
            <ARM></ARM>
            <SF></SF>
        </Win>
    </Compilers>
    <FILE>$PROJ_NAME</FILE>
    <SRC>
        <Type>OneFile</Type>
        <FILE>$2</FILE>
    </SRC>
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